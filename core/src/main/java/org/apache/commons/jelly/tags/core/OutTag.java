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
package org.apache.commons.jelly.tags.core;

import org.apache.commons.jelly.JellyTagException;
import org.apache.commons.jelly.TagSupport;
import org.apache.commons.jelly.XMLOutput;
import org.apache.commons.jelly.expression.Expression;
import org.xml.sax.SAXException;

/**
 * Cancels the effect of &lt;?jelly escape-by-default='true'?&gt; and allow expressions to produce mark up.
 */
public class OutTag extends TagSupport {

    /** The expression to evaluate. */
    private Expression value;

    public OutTag() {
    }

    public void doTag(XMLOutput output) throws JellyTagException {
        if (value != null) {
            String text = value.evaluateAsString(context);
            if (text != null) {

                try {
                    output.write(text);
                }
                catch (SAXException e) {
                    throw new JellyTagException("could not write the XMLOutput",e);
                }
            }
        }
    }

    /**
     * The value that should be escaped.
     *
     * @param value required
     */
    public void setValue(Expression value) {
        this.value = value;
    }
}
