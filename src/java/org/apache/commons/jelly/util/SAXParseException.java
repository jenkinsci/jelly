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
package org.apache.commons.jelly.util;

import org.xml.sax.Locator;

/**
 * <tt>SAXParseException</tt> with a bug fix to support
 * exception nesting.
 *
 * @author Kohsuke Kawaguchi
 */
public class SAXParseException extends org.xml.sax.SAXParseException {
    public SAXParseException(String message, Locator locator) {
        super(message, locator);
    }

    public SAXParseException(String message, Locator locator, Exception e) {
        super(message, locator, e);
    }

    public SAXParseException(String message, String publicId, String systemId, int lineNumber, int columnNumber) {
        super(message, publicId, systemId, lineNumber, columnNumber);
    }

    public SAXParseException(String message, String publicId, String systemId, int lineNumber, int columnNumber, Exception e) {
        super(message, publicId, systemId, lineNumber, columnNumber, e);
    }

    // defining this method allows nested exception to be displayed on Java 1.4 and later
    public Throwable getCause() {
        return getException();
    }

    // show SAXParseException's message, as oppsoed to the message of the nested exception
    public String toString() {
        String s = getClass().getName();
        String message = getLocalizedMessage();
        return (message != null) ? (s + ": " + message) : s;
    }
}
