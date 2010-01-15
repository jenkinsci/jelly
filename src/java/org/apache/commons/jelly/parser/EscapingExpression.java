package org.apache.commons.jelly.parser;

import org.apache.commons.jelly.JellyContext;
import org.apache.commons.jelly.expression.Expression;
import org.apache.commons.jelly.expression.ExpressionSupport;

/**
 * {@link Expression} that escapes output so that the text can appear in the PCDATA portion of XML.
 *
 * @author Kohsuke Kawaguchi
 */
public class EscapingExpression extends ExpressionSupport {
    private final Expression base;

    public EscapingExpression(Expression base) {
        this.base = base;
    }

    public String getExpressionText() {
        return base.getExpressionText();
    }

    public Object evaluate(JellyContext context) {
        Object r = base.evaluate(context);
        if (r==null)    return null;
        return escape(r.toString());
    }

    private String escape(String text) {
        int len = text.length();
        StringBuilder buf = new StringBuilder(len);
        boolean escaped = false;

        for (int i=0; i< len; i++) {
            char ch = text.charAt(i);
            switch (ch) {
                case '<':
                    buf.append("&lt;");
                    escaped = true;
                    continue;
                case '&':
                    buf.append("&amp;");
                    escaped = true;
                    continue;
                default:
                    buf.append(ch);
            }
        }

        if (!escaped)   return text;    // nothing to escape. no need to create a new string

        return buf.toString();

    }
}
