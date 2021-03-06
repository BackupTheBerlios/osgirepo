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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.objectstyle.cayenne.exp.Expression;
import org.objectstyle.cayenne.map.DbAttribute;
import org.objectstyle.cayenne.map.DbEntity;
import org.objectstyle.cayenne.map.ObjEntity;
import org.objectstyle.cayenne.map.Procedure;
import org.objectstyle.cayenne.map.QueryBuilder;
import org.objectstyle.cayenne.util.XMLEncoder;
import org.objectstyle.cayenne.util.XMLSerializable;

/**
 * SelectQuery is a Query object describing what rows to retrieve from the database and
 * how to convert them to objects. SelectQuery is defined in terms of object mapping.
 * During execution Cayenne transaclates it to SQL dialect of the target database.
 * SelectQuery defines a set of parameters for the fetch. Most important parameters are
 * the "root", "qualifier", and "ordering".
 * 
 * @author Andrei Adamchik
 */
public class SelectQuery extends QualifiedQuery implements GenericSelectQuery,
        ParameterizedQuery, XMLSerializable {

    public static final String DISTINCT_PROPERTY = "cayenne.SelectQuery.distinct";
    public static final boolean DISTINCT_DEFAULT = false;

    protected List customDbAttributes = new ArrayList();
    protected List orderings = new ArrayList();
    protected Set prefetches = new HashSet();
    protected boolean distinct;

    protected Expression parentQualifier;
    protected String parentObjEntityName;
    protected SelectExecutionProperties selectProperties = new SelectExecutionProperties();

    /** Creates empty SelectQuery. */
    public SelectQuery() {
        super();
    }

    /**
     * Creates a SelectQuery with null qualifier, for the specifed ObjEntity
     * 
     * @param root the ObjEntity this SelectQuery is for.
     */
    public SelectQuery(ObjEntity root) {
        this(root, null);
    }

    /**
     * Creates a SelectQuery for the specifed ObjEntity with the given qualifier
     * 
     * @param root the ObjEntity this SelectQuery is for.
     * @param qualifier an Expression indicating which objects should be fetched
     */
    public SelectQuery(ObjEntity root, Expression qualifier) {
        this();
        this.init(root, qualifier);
    }

    /**
     * Creates a SelectQuery that selects all objects of a given persistent class.
     * 
     * @param rootClass the Class of objects fetched by this query.
     */
    public SelectQuery(Class rootClass) {
        this(rootClass, null);
    }

    /**
     * Creates a SelectQuery that selects objects of a given persistent class that match
     * supplied qualifier.
     * 
     * @param rootClass the Class of objects fetched by this query.
     */
    public SelectQuery(Class rootClass, Expression qualifier) {
        init(rootClass, qualifier);
    }

    /**
     * Creates a SelectQuery for the specifed DbEntity.
     * 
     * @param root the DbEntity this SelectQuery is for.
     * @since 1.1
     */
    public SelectQuery(DbEntity root) {
        this(root, null);
    }

    /**
     * Creates a SelectQuery for the specifed DbEntity with the given qualifier.
     * 
     * @param root the DbEntity this SelectQuery is for.
     * @param qualifier an Expression indicating which objects should be fetched
     * @since 1.1
     */
    public SelectQuery(DbEntity root, Expression qualifier) {
        this();
        this.init(root, qualifier);
    }

    /**
     * Creates SelectQuery with <code>objEntityName</code> parameter.
     */
    public SelectQuery(String objEntityName) {
        this(objEntityName, null);
    }

    /**
     * Creates SelectQuery with <code>objEntityName</code> and <code>qualifier</code>
     * parameters.
     */
    public SelectQuery(String objEntityName, Expression qualifier) {
        init(objEntityName, qualifier);
    }

    private void init(Object root, Expression qualifier) {
        this.setRoot(root);
        this.setQualifier(qualifier);
    }

    /**
     * Initializes query parameters using a set of properties.
     * 
     * @since 1.1
     */
    public void initWithProperties(Map properties) {

        // must init defaults even if properties are empty
        if (properties == null) {
            properties = Collections.EMPTY_MAP;
        }

        Object distinct = properties.get(DISTINCT_PROPERTY);

        // init ivars from properties
        this.distinct = (distinct != null)
                ? "true".equalsIgnoreCase(distinct.toString())
                : DISTINCT_DEFAULT;

        selectProperties.initWithProperties(properties);
    }

    /**
     * Prints itself as XML to the provided PrintWriter.
     * 
     * @since 1.1
     */
    public void encodeAsXML(XMLEncoder encoder) {
        encoder.print("<query name=\"");
        encoder.print(getName());
        encoder.print("\" factory=\"");
        encoder.print("org.objectstyle.cayenne.map.SelectQueryBuilder");

        String rootString = null;
        String rootType = null;

        if (root instanceof String) {
            rootType = QueryBuilder.OBJ_ENTITY_ROOT;
            rootString = root.toString();
        }
        else if (root instanceof ObjEntity) {
            rootType = QueryBuilder.OBJ_ENTITY_ROOT;
            rootString = ((ObjEntity) root).getName();
        }
        else if (root instanceof DbEntity) {
            rootType = QueryBuilder.DB_ENTITY_ROOT;
            rootString = ((DbEntity) root).getName();
        }
        else if (root instanceof Procedure) {
            rootType = QueryBuilder.PROCEDURE_ROOT;
            rootString = ((Procedure) root).getName();
        }
        else if (root instanceof Class) {
            rootType = QueryBuilder.JAVA_CLASS_ROOT;
            rootString = ((Class) root).getName();
        }

        if (rootType != null) {
            encoder.print("\" root=\"");
            encoder.print(rootType);
            encoder.print("\" root-name=\"");
            encoder.print(rootString);
        }

        encoder.println("\">");

        encoder.indent(1);

        // print properties
        if (distinct != DISTINCT_DEFAULT) {
            encoder.printProperty(DISTINCT_PROPERTY, distinct);
        }

        selectProperties.encodeAsXML(encoder);

        // encode qualifier
        if (qualifier != null) {
            encoder.print("<qualifier>");
            qualifier.encodeAsXML(encoder);
            encoder.println("</qualifier>");
        }

        // encode orderings
        if (orderings != null && !orderings.isEmpty()) {
            Iterator it = orderings.iterator();
            while (it.hasNext()) {
                Ordering ordering = (Ordering) it.next();
                ordering.encodeAsXML(encoder);
            }
        }

        // encode prefecthes
        if (prefetches != null && !prefetches.isEmpty()) {
            Iterator it = prefetches.iterator();
            while (it.hasNext()) {
                String prefetch = (String) it.next();

                // currently prefetch is a String, but DTD
                // treats it as a path expression... I guess for now
                // it will be an overkill to wrap it in "<![CDATA[.."
                encoder.print("<prefetch>");
                encoder.print(prefetch);
                encoder.println("</prefetch>");
            }
        }

        encoder.indent(-1);
        encoder.println("</query>");
    }

    /**
     * A shortcut for {@link #queryWithParameters(Map, boolean)}that prunes parts of
     * qualifier that have no parameter value set.
     */
    public SelectQuery queryWithParameters(Map parameters) {
        return queryWithParameters(parameters, true);
    }

    /**
     * Returns a query built using this query as a prototype, using a set of parameters to
     * build the qualifier.
     * 
     * @see org.objectstyle.cayenne.exp.Expression#expWithParameters(java.util.Map,
     *      boolean) parameter substitution.
     */
    public SelectQuery queryWithParameters(Map parameters, boolean pruneMissing) {
        // create a query replica
        SelectQuery query = new SelectQuery();
        query.setDistinct(distinct);

        this.selectProperties.copyToProperties(query.selectProperties);

        query.setLoggingLevel(logLevel);
        query.setParentObjEntityName(parentObjEntityName);
        query.setParentQualifier(parentQualifier);
        query.setRoot(root);

        query.addPrefetches(prefetches);
        query.addOrderings(orderings);
        query.addCustomDbAttributes(customDbAttributes);

        // substitute qualifier parameters
        if (qualifier != null) {
            query.setQualifier(qualifier.expWithParameters(parameters, pruneMissing));
        }
        
        // TODO: implement algorithm for building the name based on the original name and
        // the hashcode of the map of parameters. This way query clone can take advantage
        // of caching.

        return query;
    }

    /**
     * Creates and returns a new SelectQuery built using this query as a prototype and
     * substituting qualifier parameters with the values from the map.
     * 
     * @since 1.1
     */
    public Query createQuery(Map parameters) {
        return queryWithParameters(parameters);
    }

    /**
     * Adds ordering specification to this query orderings.
     */
    public void addOrdering(Ordering ordering) {
        orderings.add(ordering);
    }

    /**
     * Adds a list of orderings.
     */
    public void addOrderings(List orderings) {
        this.orderings.addAll(orderings);
    }

    /** Adds ordering specification to this query orderings. */
    public void addOrdering(String sortPathSpec, boolean isAscending) {
        this.addOrdering(new Ordering(sortPathSpec, isAscending));
    }

    /** Adds ordering specification to this query orderings. */
    public void addOrdering(String sortPathSpec, boolean isAscending, boolean ignoreCase) {
        this.addOrdering(new Ordering(sortPathSpec, isAscending, ignoreCase));
    }

    /**
     * Removes ordering.
     * 
     * @since 1.1
     */
    public void removeOrdering(Ordering ordering) {
        this.orderings.remove(ordering);
    }

    /**
     * Returns a list of orderings used by this query.
     */
    public List getOrderings() {
        return orderings;
    }

    public void clearOrderings() {
        orderings.clear();
    }

    /** Returns true if this query returns distinct rows. */
    public boolean isDistinct() {
        return distinct;
    }

    /** Sets <code>distinct</code> property. */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * Returns a list of attributes that will be included in the results of this query.
     */
    public List getCustomDbAttributes() {
        // if query root is DbEntity, and no custom attributes
        // are defined, return DbEntity attributes.
        if (customDbAttributes.size() == 0 && (getRoot() instanceof DbEntity)) {
            Collection attributes = ((DbEntity) getRoot()).getAttributes();
            List attributeNames = new ArrayList(attributes.size());
            Iterator it = attributes.iterator();
            while (it.hasNext()) {
                DbAttribute attribute = (DbAttribute) it.next();
                attributeNames.add(attribute.getName());
            }
            return attributeNames;
        }
        else {
            return customDbAttributes;
        }
    }

    /**
     * Adds a path to the DbAttribute that should be included in the results of this
     * query. Valid paths would look like <code>ARTIST_NAME</code>,
     * <code>PAINTING_ARRAY.PAINTING_ID</code>, etc.
     */
    public void addCustomDbAttribute(String attributePath) {
        customDbAttributes.add(attributePath);
    }

    public void addCustomDbAttributes(List attrPaths) {
        customDbAttributes.addAll(attrPaths);
    }

    /**
     * Returns <code>true</code> if there is at least one custom query attribute
     * specified, otherwise returns <code>false</code> for the case when the query
     * results will contain only the root entity attributes.
     * <p>
     * Note that queries that are fetching custom attributes always return data rows
     * instead of DataObjects.
     * </p>
     */
    public boolean isFetchingCustomAttributes() {
        return (getRoot() instanceof DbEntity) || customDbAttributes.size() > 0;
    }

    /**
     * Returns a list of relationships that must be prefetched as a part of this query.
     */
    public Collection getPrefetches() {
        return prefetches;
    }

    /**
     * Adds a relationship path. ObjRelationship names are separated by ".". to the list
     * of relationship paths that should be prefetched when the query is executed.
     */
    public void addPrefetch(String relPath) {
        prefetches.add(relPath);
    }

    public void addPrefetches(Collection relPaths) {
        prefetches.addAll(relPaths);
    }

    public void clearPrefetches() {
        prefetches.clear();
    }

    /**
     * Removes prefetch.
     * 
     * @since 1.1
     */
    public void removePrefetch(String prefetch) {
        this.prefetches.remove(prefetch);
    }

    /**
     * Returns <code>true</code> if this query should produce a list of data rows as
     * opposed to DataObjects, <code>false</code> for DataObjects. This is a hint to
     * QueryEngine executing this query.
     */
    public boolean isFetchingDataRows() {
        return this.isFetchingCustomAttributes() || selectProperties.isFetchingDataRows();
    }

    /**
     * Sets query result type. If <code>flag</code> parameter is <code>true</code>,
     * then results will be in the form of data rows.
     * <p>
     * <i>Note that if <code>isFetchingCustAttributes()</code> returns <code>true</code>,
     * this setting has no effect, and data rows are always fetched. </i>
     * </p>
     */
    public void setFetchingDataRows(boolean flag) {
        selectProperties.setFetchingDataRows(flag);
    }

    /**
     * Returns refresh policy of this query. Default is <code>true</code>.
     * 
     * @since 1.1
     */
    public boolean isRefreshingObjects() {
        return selectProperties.isRefreshingObjects();
    }

    /**
     * @since 1.1
     */
    public void setRefreshingObjects(boolean flag) {
        selectProperties.setRefreshingObjects(flag);
    }

    /**
     * @since 1.1
     */
    public String getCachePolicy() {
        return selectProperties.getCachePolicy();
    }

    /**
     * @since 1.1
     */
    public void setCachePolicy(String policy) {
        this.selectProperties.setCachePolicy(policy);
    }

    /**
     * Returns the fetchLimit.
     * 
     * @return int
     */
    public int getFetchLimit() {
        return selectProperties.getFetchLimit();
    }

    /**
     * Sets the fetchLimit.
     * 
     * @param fetchLimit The fetchLimit to set
     */
    public void setFetchLimit(int fetchLimit) {
        this.selectProperties.setFetchLimit(fetchLimit);
    }

    /** Setter for query's parent entity qualifier. */
    public void setParentQualifier(Expression parentQualifier) {
        this.parentQualifier = parentQualifier;
    }

    /** Getter for query parent entity qualifier. */
    public Expression getParentQualifier() {
        return parentQualifier;
    }

    /**
     * Adds specified parent entity qualifier to the existing parent entity qualifier
     * joining it using "AND".
     */
    public void andParentQualifier(Expression e) {
        parentQualifier = (parentQualifier != null) ? parentQualifier.andExp(e) : e;
    }

    /**
     * Adds specified parent entity qualifier to the existing qualifier joining it using
     * "OR".
     */
    public void orParentQualifier(Expression e) {
        parentQualifier = (parentQualifier != null) ? parentQualifier.orExp(e) : e;
    }

    /**
     * Returns the name of parent ObjEntity.
     * 
     * @return String
     */
    public String getParentObjEntityName() {
        return parentObjEntityName;
    }

    /**
     * Sets the name of parent ObjEntity. If query's root ObjEntity maps to a derived
     * entity in the DataMap, this query qualifier will resolve to a HAVING clause of an
     * SQL statement. To allow fine tuning the query before applying GROUP BY and HAVING,
     * callers can setup the name of parent ObjEntity and parent qualifier that will be
     * used to create WHERE clause preceeding GROUP BY.
     * <p>
     * For instance this is helpful to qualify the fetch on a related entity attributes,
     * since HAVING does not allow joins.
     * </p>
     * 
     * @param parentObjEntityName The parentObjEntityName to set
     */
    public void setParentObjEntityName(String parentObjEntityName) {
        this.parentObjEntityName = parentObjEntityName;
    }

    /**
     * Returns <code>true</code> if this query has an extra qualifier that uses a parent
     * entity of the query root entity for additional result filtering.
     */
    public boolean isQualifiedOnParent() {
        return getParentObjEntityName() != null && parentQualifier != null;
    }

    /**
     * Returns <code>pageSize</code> property. Page size is a hint telling Cayenne
     * QueryEngine that query result should use paging instead of reading the whole result
     * in the memory.
     * 
     * @return int
     */
    public int getPageSize() {
        return selectProperties.getPageSize();
    }

    /**
     * Sets <code>pageSize</code> property.
     * 
     * @param pageSize The pageSize to set
     */
    public void setPageSize(int pageSize) {
        selectProperties.setPageSize(pageSize);
    }

    /**
     * Returns true if objects fetched via this query should be fully resolved according
     * to the inheritance hierarchy.
     * 
     * @since 1.1
     */
    public boolean isResolvingInherited() {
        return selectProperties.isResolvingInherited();
    }

    /**
     * Sets whether the objects fetched via this query should be fully resolved according
     * to the inheritance hierarchy.
     * 
     * @since 1.1
     */
    public void setResolvingInherited(boolean b) {
        selectProperties.setResolvingInherited(b);
    }
}