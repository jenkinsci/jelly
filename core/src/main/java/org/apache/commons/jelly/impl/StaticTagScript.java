/*
 * Copyright 2002,2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.jelly.impl;

import org.apache.commons.jelly.DynaTag;
import org.apache.commons.jelly.JellyContext;
import org.apache.commons.jelly.JellyException;
import org.apache.commons.jelly.JellyTagException;
import org.apache.commons.jelly.Tag;
import org.apache.commons.jelly.TagLibrary;
import org.apache.commons.jelly.XMLOutput;
import org.apache.commons.jelly.expression.Expression;
import org.xml.sax.SAXException;

import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * <p><code>StaticTagScript</code> is a script that evaluates a StaticTag, a piece of static XML
 * though its attributes or element content may contain dynamic expressions.
 * The first time this tag evaluates, it may have become a dynamic tag, so it will check that
 * a new dynamic tag has not been generated.</p>
 *
 * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
 * @version $Revision: 239434 $
 */
public class StaticTagScript extends TagScript {

    public StaticTagScript() {
    }

    /**
     * @deprecated
     *      The tag name is inferred automatically, so no need to set a tag factory.
     *      Use the default constructor.
     */
    public StaticTagScript(TagFactory tagFactory) {
        super(tagFactory);
    }

    @Override
    protected Tag createTag() throws JellyException {
        return new StaticTag(getNsUri(),getLocalName(),getElementName());
    }

    // Script interface
    //-------------------------------------------------------------------------
    public void run(JellyContext context, XMLOutput output) throws JellyTagException {
        try {
            startNamespacePrefixes(output);
        } catch (SAXException e) {
            throw new JellyTagException("could not start namespace prefixes",e);
        }

        Tag tag;
        try {
            tag = getTag(context);

            // lets see if we have a dynamic tag
            if (tag instanceof StaticTag) {
                tag = findDynamicTag(context, (StaticTag) tag);
            }

            setTag(tag,context);
        } catch (JellyException e) {
            throw new JellyTagException(e);
        }

        URL rootURL = context.getRootURL();
        URL currentURL = context.getCurrentURL();
        // no parent tag lookup for StaticTagScript.
        // This makes it impossible for Tag to find its ancestor StaticTags, but
        // in practice no one needs to look them up so far, so this corner-cutting is OK for now.
        try {
            if ( tag == null ) {
                return;
            }
            tag.setContext(context);
            setContextURLs(context);

            DynaTag dynaTag = (DynaTag) tag;

            // ### probably compiling this to 2 arrays might be quicker and smaller
            for (Iterator iter = attributes.entrySet().iterator(); iter.hasNext();) {
                Map.Entry entry = (Map.Entry) iter.next();
                String name = (String) entry.getKey();
                if(name.indexOf(':')!=-1)
                    name = name.substring(name.indexOf(':')+1);
                ExpressionAttribute expat = (ExpressionAttribute) entry.getValue();
                Expression expression = expat.exp;

                Object value;

                if ( Expression.class.isAssignableFrom( dynaTag.getAttributeType(name) ) ) {
                    value = expression;
                } else {
                    value = expression.evaluate(context);
                }

                if( expat.prefix != null && expat.prefix.length() > 0 && tag instanceof StaticTag )
                {
                    ((StaticTag) dynaTag).setAttribute(name,expat.prefix, expat.nsURI,value);
                }
                else
                {
                    dynaTag.setAttribute(name, value);
                }
            }

            tag.doTag(output);
        }
        catch (JellyTagException e) {
            handleException(e);
        }
        catch (RuntimeException e) {
            handleException(e);
        } finally {
            context.setCurrentURL(currentURL);
            context.setRootURL(rootURL);
        }

        try {
            endNamespacePrefixes(output);
        } catch (SAXException e) {
            throw new JellyTagException("could not end namespace prefixes",e);
        }
    }

    /**
     * Attempts to find a dynamically created tag that has been created since this
     * script was compiled
     */
    protected Tag findDynamicTag(JellyContext context, StaticTag tag) throws JellyException {
        // lets see if there's a tag library for this URI...
        TagLibrary taglib = context.getTagLibrary( tag.getUri() );
        if ( taglib != null ) {
            Tag newTag = taglib.createTag( tag.getLocalName(), getSaxAttributes() );
            if ( newTag != null ) {
                newTag.setParent( tag.getParent() );
                newTag.setBody( tag.getBody() );
                return newTag;
            }
        }
        return tag;
    }
}
