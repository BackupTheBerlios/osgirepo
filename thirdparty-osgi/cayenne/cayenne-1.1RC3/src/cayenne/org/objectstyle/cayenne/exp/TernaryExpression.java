/* ====================================================================
 * 
 * The ObjectStyle Group Software License, version 1.1
 * ObjectStyle Group - http://objectstyle.org/
 * 
 * Copyright (c) 2002-2004, Andrei (Andrus) Adamchik and individual authors
 * of the software. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 * 
 * 3. The end-user documentation included with the redistribution, if any,
 *    must include the following acknowlegement:
 *    "This product includes software developed by independent contributors
 *    and hosted on ObjectStyle Group web site (http://objectstyle.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 * 
 * 4. The names "ObjectStyle Group" and "Cayenne" must not be used to endorse
 *    or promote products derived from this software without prior written
 *    permission. For written permission, email
 *    "andrus at objectstyle dot org".
 * 
 * 5. Products derived from this software may not be called "ObjectStyle"
 *    or "Cayenne", nor may "ObjectStyle" or "Cayenne" appear in their
 *    names without prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE OBJECTSTYLE GROUP OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 * 
 * This software consists of voluntary contributions made by many
 * individuals and hosted on ObjectStyle Group web site.  For more
 * information on the ObjectStyle Group, please see
 * <http://objectstyle.org/>.
 */
package org.objectstyle.cayenne.exp;

import java.io.PrintWriter;

/** 
 * Generic ternary expression. 
 * Describes expression in a form: "<tt>doSomething(operand1, operand2, operand3)</tt>". 
 * SQL example of ternary expression is BETWEEN expression. 
 */
public class TernaryExpression extends Expression {
    protected Object operand0;
    protected Object operand1;
    protected Object operand2;

    public TernaryExpression() {
    }

    public TernaryExpression(int type) {
        this.type = type;
    }

    protected void flattenTree() {

    }

    protected boolean pruneNodeForPrunedChild(Object prunedChild) {
        return true;
    }

    public final int getOperandCount() {
        return 3;
    }

    /**
     * Creates a copy of this expression node, without copying children.
     * 
     * @since 1.1
     */
    public Expression shallowCopy() {
        return new TernaryExpression(type);
    }

    public Object getOperand(int index) {
        if (index == 0)
            return operand0;
        else if (index == 1)
            return operand1;
        else if (index == 2)
            return operand2;

        throw new IllegalArgumentException(
            "Invalid operand index for TernaryExpression: " + index);
    }

    public void setOperand(int index, Object value) {
        if (index == 0) {
            operand0 = value;
            return;
        }
        else if (index == 1) {
            operand1 = value;
            return;
        }
        else if (index == 2) {
            operand2 = value;
            return;
        }

        throw new IllegalArgumentException(
            "Invalid operand index for TernaryExpression: " + index);
    }

    /**
     * @since 1.1
     */
    public void encodeAsString(PrintWriter pw) {
        // this class will be deprecated soon, so using
        // a deprecated "toStringBuffer" is OK
        StringBuffer buffer = new StringBuffer();
        toStringBuffer(buffer);
        pw.print(buffer.toString());
    }
}