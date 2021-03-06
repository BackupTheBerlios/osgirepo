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
package org.objectstyle.cayenne.query;

import java.util.ArrayList;
import java.util.List;

import org.objectstyle.cayenne.map.DbEntity;

/**
 * BatchQuery and its descendants allow to group similar data for the
 * batch database modifications, including inserts, updates and deletes. 
 * Single BatchQuery corresponds to a parameterized PreparedStatement 
 * and a matrix of values.
 *
 * @author Andriy Shapochka
 */
public abstract class BatchQuery extends AbstractQuery {

    public BatchQuery(DbEntity dbEntity) {
        setRoot(dbEntity);
    }

    /**
     * Returns a DbEntity associated with this batch.
     */
    public DbEntity getDbEntity() {
        return (DbEntity) getRoot();
    }

    /**
     * Returns a List of values for the current batch iteration, 
     * in the order they are bound to the query. Used mainly for 
     * logging.
     */
    public List getValuesForUpdateParameters() {
        int len = getDbAttributes().size();
        List values = new ArrayList(len);
        for (int i = 0; i < len; i++) {
            values.add(getObject(i));
        }
        return values;
    }

    /**
     * Returns <code>true</code> if this batch query has no parameter rows.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns a list of DbAttributes describing batch parameters.
     */
    public abstract List getDbAttributes();

    /**
     * Rewinds batch to the first parameter row.
     */
    public abstract void reset();

    /**
     * Repositions batch to the next object, so that subsequent calls to
     * getObject(int) would return the values of the next batch object. Returns
     * <code>true</code> if batch has more objects to iterate over,
     * <code>false</code> otherwise.
     */
    public abstract boolean next();

    /**
     * Returns a value at a given index for the current batch iteration.
     */
    public abstract Object getObject(int valueIndex);

    /**
     * Returns the number of parameter rows in this batch.
     */
    public abstract int size();
}