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

import org.apache.commons.jelly.JellyContext;
import org.apache.commons.jelly.JellyTagException;
import org.apache.commons.jelly.Script;
import org.apache.commons.jelly.XMLOutput;

import org.xml.sax.SAXException;

/** <p><code>TextScript</code> outputs some static text.</p>
  *
  * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
  * @version $Revision: 155420 $
  */
public class TextScript implements Script {

    /** the text output by this script */
    private String text;

    public TextScript() {
    }

    public TextScript(String text) {
        this.text = text;
    }

    public String toString() {
        return super.toString() + "[text=" + text + "]";
    }

    /**
     * Trims whitespace if this is ignorable whitespace.
     */
    public void trimWhitespace() {
        if(text.trim().length()==0)
            this.text = "";
    }

    /** @return the text output by this script */
    public String getText() {
        return text;
    }

    /** Sets the text output by this script */
    public void setText(String text) {
        this.text = text;
    }

    // Script interface
    //-------------------------------------------------------------------------
    public Script compile() {
        return this;
    }

    /** Evaluates the body of a tag */
    public void run(JellyContext context, XMLOutput output) throws JellyTagException {
        if ( text != null ) {
            try {
              output.write(text);
            } catch (SAXException e) {
                throw new JellyTagException("could not write to XMLOutput",e);
            }
        }
    }
}
