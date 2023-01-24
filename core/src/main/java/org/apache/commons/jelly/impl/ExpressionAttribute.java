package org.apache.commons.jelly.impl;

import org.apache.commons.jelly.expression.Expression;

/**
 * Attribute as an expression of {@link TagScript}.
 *
 * @see TagScript
 */
public class ExpressionAttribute {
    public ExpressionAttribute(String name, Expression exp) {
        this(name,"","",exp);
    }
    public ExpressionAttribute(String name, String prefix, String nsURI, Expression exp) {
        this.name = name;
        this.prefix = prefix;
        this.nsURI = nsURI;
        this.exp = exp;
    }

    /**
     * Local name of this attribute.
     */
    public String name;
    public String prefix;
    public String nsURI;
    public Expression exp;

    public String qname() {
        if (nsURI.length()>0)
            return prefix+':'+name;
        else
            return name;
    }
}
