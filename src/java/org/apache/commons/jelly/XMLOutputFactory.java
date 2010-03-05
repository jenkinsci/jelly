package org.apache.commons.jelly;

import java.io.Writer;

/**
 * Factory to create an XMLOutput for a given Writer.
 * An instance may be placed in the JellyContext (variable called
 * org.apache.commons.jelly.XMLOutputFactory) so that TagScript will use
 * a custom output instead of the default XMLOutput.createXMLOutput().
 *
 * @author Alan.Harder@sun.com
 */
public interface XMLOutputFactory {

    /**
     * @param Write output to this writer
     * @param escapeText is whether or not text output will be escaped. This must be true
     *   if the underlying output is XML or could be false if the underlying output is textual.
     * @return XMLOutput that will write to given writer
     */
    public XMLOutput createXMLOutput(Writer writer, boolean escapeText);
}
