package seguedev.srceditor.generators;

import de.gulden.util.javasource.*;
import org.kobjects.serialization.*;
import java.util.*;
import java.io.*;

public class ClassBuilder {
    
    static String typeNamePropertyInfo = "org.kobjects.serialization.PropertyInfo";
    static HashMap typeMap = new HashMap();
    static String PROP_PREFIX = "KSOAP_";
    static String PROP_PROPERTY_ARRAY_NAME = "PROP_ARRAY";
    
    Field origFields[] = null;
    de.gulden.util.javasource.Class clazz = null;
    HashMap nativeToKsoapMap = new HashMap();
    boolean overwrite = true;
    
    static {
        typeMap.put("long", "LONG_CLASS");
        typeMap.put("int", "INTEGER_CLASS");
        typeMap.put("boolean", "BOOLEAN_CLASS");
        typeMap.put("java.lang.Object", "OBJECT_CLASS");
        typeMap.put("java.lang.String", "STRING_CLASS");    
    }
    
    
    public ClassBuilder(de.gulden.util.javasource.Class _clazz, Field _fields[], boolean _overwrite) {
        clazz = _clazz;
        origFields = _fields;
        this.overwrite = _overwrite;
    }
    
    public boolean doesMethodExist(Method m) {
        Iterator i = clazz.myMember.iterator();
        while ( i.hasNext() ) {
            Object o = i.next();
            if ( o instanceof Method ) {
                Method mm = (Method) o;
                if ( m.getUnqualifiedName().equals( mm.getUnqualifiedName() ) ) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean doesFieldExist(Field f) {
        Iterator i = clazz.myMember.iterator();
        while ( i.hasNext() ) {
            Object o = i.next();
            if ( o instanceof Field ) {
                Field ff = (Field) o;
                if ( f.getUnqualifiedName().equals( ff.getUnqualifiedName() ) ) {
                    return true;
                }
            }
        }
        return false;
    }
        
    public void removeMethod(Method m) {
        Method remove = null;
        Iterator i = clazz.myMember.iterator();
        while ( i.hasNext() ) {
            Object o = i.next();
            if ( o instanceof Method ) {
                Method mm = (Method) o;
                if ( m.getUnqualifiedName().equals( mm.getUnqualifiedName() ) ) {
                    remove = mm;
                }
            }
        }
        
        if ( remove != null ) {
            clazz.myMember.remove( remove );
        }
    }
    
    public void removeField(Field f) {
        
        Field remove = null;
        Iterator i = clazz.myMember.iterator();
        while ( i.hasNext() ) {
            Object o = i.next();
            if ( o instanceof Field ) {
                Field ff = (Field) o;
                if ( f.getUnqualifiedName().equals( ff.getUnqualifiedName() ) ) {
                    remove = ff;
                }
            }
        }
        
        if ( remove != null ) {
            clazz.myMember.remove( remove );
        }
    }
        
    public void implementInterface() {
        
        boolean usesKVMSer = false;
        ArrayList list = new ArrayList();
        Enumeration e = clazz.getInterfaceNames();
        while ( e.hasMoreElements() ) {
            String name = (String) e.nextElement();
            list.add(name);
            if ( name.equals("org.kobjects.serialization.KvmSerializable") || name.equals("KvmSerializable") ) {
                usesKVMSer = true;
            }
        }
        
        if ( ! usesKVMSer ) {
            list.add("org.kobjects.serialization.KvmSerializable");
            String newNames[] = (String[])list.toArray(new String[0]);
            clazz.setInterfaceNames( newNames ); 
        }
        
    }
    
    public void reconstructMethods() {
        Method getPropertyInfo = getMethod_getPropertyInfo();
        if ( doesMethodExist( getPropertyInfo ) ) {
            if ( overwrite ) {
                removeMethod( getPropertyInfo );
                clazz.myMember.add( getPropertyInfo );
            }
        } else {
            clazz.myMember.add( getPropertyInfo );
        }
        
        Method getPropertyCount = getMethod_getPropertyCount();
        if ( doesMethodExist( getPropertyCount ) ) {
            if ( overwrite ) {
                removeMethod( getPropertyCount );
                clazz.myMember.add( getPropertyCount );
            }
        } else {
            clazz.myMember.add( getPropertyCount );
        }
        
        Method setProperty = getMethod_setProperty();
        if ( doesMethodExist( setProperty ) ) {
            if ( overwrite ) {
                removeMethod( setProperty );
                clazz.myMember.add( setProperty );
            }
        } else {
            clazz.myMember.add( setProperty );
        }
        
        Method getProperty = getMethod_getProperty();
        if ( doesMethodExist( getProperty ) ) {
            if ( overwrite ) {
                removeMethod( getProperty );
                clazz.myMember.add( getProperty );
            }
        } else {
            clazz.myMember.add( getProperty );
        }
    }
    
    public Method getMethod_getProperty() {
        
        Method method = new Method(clazz);
        method.setName("getProperty");
        method.setModifier(java.lang.reflect.Modifier.PUBLIC);
        
        Type rtrn = new Type( method );
        rtrn.setDimension(0);
        rtrn.setTypeName("java.lang.Object");
        method.setType( rtrn );
       
        Parameter p1 = new Parameter( method );
        Type type1 = new Type(method);
        type1.setDimension(0);
        type1.setTypeName("int");
        p1.setType(type1);
        p1.setName("param");
        method.myParameter.add( p1 );
        
        Code code = new Code();
        code.setRaw( generateGetterBlock() );
        method.setCode( code );
        
        return method;
        
    }
    
    public Method getMethod_setProperty() {
        
        Method method = new Method(clazz);
        method.setName("setProperty");
        method.setModifier(java.lang.reflect.Modifier.PUBLIC);
        
        Type rtrn = new Type( method );
        rtrn.setDimension(0);
        rtrn.setTypeName("void");
        method.setType( rtrn );
       
        Parameter p1 = new Parameter( method );
        Type type1 = new Type(method);
        type1.setDimension(0);
        type1.setTypeName("int");
        p1.setType(type1);
        p1.setName("param");
        method.myParameter.add( p1 );
        
        Parameter p2 = new Parameter( method );
        Type type2 = new Type(method);
        type2.setDimension(0);
        type2.setTypeName("java.lang.Object");
        p2.setType(type2);
        p2.setName("obj");
        method.myParameter.add( p2 );
        
        Code code = new Code();
        code.setRaw( generateSetterBlock() );
        method.setCode( code );
        
        return method;
        
    }
    
    
    public Method getMethod_getPropertyCount() {
        
        Method method = new Method(clazz);
        method.setName("getPropertyCount");
        method.setModifier(java.lang.reflect.Modifier.PUBLIC);

        Type rtrn = new Type( method );
        rtrn.setDimension(0);
        rtrn.setTypeName("int");
        method.setType( rtrn );
        
        Code code = new Code();
        code.setRaw( "return " + PROP_PREFIX + PROP_PROPERTY_ARRAY_NAME + ".length;");
        
        method.setCode( code );
        
        return method;

    }
    
    public Method getMethod_getPropertyInfo() {
        
        Method method = new Method(clazz);
        method.setName("getPropertyInfo");
        method.setModifier(java.lang.reflect.Modifier.PUBLIC);
        
        Type rtrn = new Type( method );
        rtrn.setDimension(0);
        rtrn.setTypeName("void");
        method.setType( rtrn );
       
        Parameter p1 = new Parameter( method );
        Type type1 = new Type(method);
        type1.setDimension(0);
        type1.setTypeName("int");
        p1.setType(type1);
        p1.setName("param");
        method.myParameter.add( p1 );
        
        Parameter p2 = new Parameter( method );
        Type type2 = new Type(method);
        type2.setDimension(0);
        type2.setTypeName(typeNamePropertyInfo);
        p2.setType(type2);
        p2.setName("propertyInfo");
        method.myParameter.add( p2 );
        
        StringWriter buff = new StringWriter();
        PrintWriter out = new PrintWriter(buff);
        
        out.println("propertyInfo.name = " + PROP_PREFIX + PROP_PROPERTY_ARRAY_NAME + "[param].name;");
        out.println("propertyInfo.nonpermanent = " + PROP_PREFIX + PROP_PROPERTY_ARRAY_NAME + "[param].nonpermanent;");
        out.println("propertyInfo.copy(" + PROP_PREFIX + PROP_PROPERTY_ARRAY_NAME + "[param]);");

        Code code = new Code();
        code.setRaw( buff.toString() );
        
        method.setCode( code );
        
        return method;
    }
    
    public void reconstructFields() {
        
        StringBuffer temp = new StringBuffer();
        
        for (int i=0; i < origFields.length; i++ ) {
            Field sfield = new Field(clazz);
            Type type = new Type( sfield );
            Code code = new Code();
            code.setRaw( getRawCodeForField(origFields[i]) );
            type.setDimension(0);
            type.setTypeName(typeNamePropertyInfo);
            sfield.setType( type );
            sfield.setCode(code);
            if ( ! origFields[i].getUnqualifiedName().startsWith(PROP_PREFIX) ) {
                sfield.setName(PROP_PREFIX + origFields[i].getUnqualifiedName() );
                temp.append(sfield.getUnqualifiedName());
                if ( i < (origFields.length-1) ) {
                    temp.append(",");
                }
            } else {
                // we could obviously not forget about checking again (below) but do it anyway
                sfield.setName(origFields[i].getUnqualifiedName() );
            }
                        
            if ( doesFieldExist(sfield) ) {
                if ( overwrite ) {
                    removeField( sfield );
                    clazz.myMember.add( sfield );       
                    nativeToKsoapMap.put(origFields[i], sfield); 
                }
            } else {
                clazz.myMember.add( sfield );       
                //temp.append(sfield.getUnqualifiedName());
                //if ( i < (origFields.length-1) ) {
                //    temp.append(",");
                //}
                nativeToKsoapMap.put(origFields[i], sfield);                 
            }
        }
        
        Field nField = new Field(clazz);
        nField.setName(PROP_PREFIX + PROP_PROPERTY_ARRAY_NAME );
        
        Type type = new Type( nField );
        Code code = new Code();
        if ( temp.length() > 0 ) {
            code.setRaw(" {" + temp.toString() + "}");
        } else {
            code.setRaw(" new " + typeNamePropertyInfo + "[0]");
        }
        type.setDimension(1);
        type.setTypeName(typeNamePropertyInfo);
        nField.setType( type );
        nField.setCode(code);
                
        if ( doesFieldExist( nField ) ) {
            if (overwrite ) {
                removeField( nField );
                clazz.myMember.add( nField );
            }
        } else {
            clazz.myMember.add( nField );
        } 
    }
    
    //"new PropertyInfo(\"" + field.getUnqualifiedName() + "\",ProperyInfo.OBJECT_CLASS)");   
    private String getRawCodeForField(Field f) {
        StringBuffer buff = new StringBuffer();
        buff.append(" new " + typeNamePropertyInfo + "(\"");
        buff.append( f.getUnqualifiedName() );
        buff.append( "\"," + typeNamePropertyInfo + "." );
        Type fieldType = f.getType();
        String typeName = fieldType.getTypeName();
        Object val = typeMap.get( typeName );
        if ( val == null ) {
            val = typeMap.get( "java.lang.Object" );
        }
        buff.append( val.toString() );
        buff.append( ")" );
        
        return buff.toString();
    }

    private Field[] filterNativeField(Field[] f) {
        ArrayList list = new ArrayList();
        for (int i=0;i< f.length; i++ ) {
            if ( !f[i].getUnqualifiedName().startsWith(PROP_PREFIX) ) {
                list.add( f[i] );
            }
        }
        return (Field[]) list.toArray(new Field[0]);
    }
    private String generateGetterBlock() {
        StringWriter buff = new StringWriter();
        PrintWriter out = new PrintWriter(buff);
        Field[] oFields = filterNativeField( origFields );
        for (int i=0; i < oFields.length; i++ ) {
            Field sField = (Field) nativeToKsoapMap.get(oFields[i]);
            String block = getGetterBlockForField(oFields[i]);
        
            if ( i == 0 ) {
                out.println("if (param == 0 ) {");
            } else {
                out.println(" else if (param == " + i + ") {");
            }
            
            out.println( block );
            out.println("}");
            
        }
        
        out.println(" else { ");
        out.println(" return null; ");
        out.println(" } ");
        
        return buff.toString();    
    }
    
    private String generateSetterBlock() {
        StringWriter buff = new StringWriter();
        PrintWriter out = new PrintWriter(buff);
        Field[] oFields = filterNativeField( origFields );
        for (int i=0; i < oFields.length; i++ ) {
            Field sField = (Field) nativeToKsoapMap.get(oFields[i]);
            String block = getSetterBlockForField(oFields[i]);
        
            if ( i == 0 ) {
                out.println("if (param == 0 ) {");
            } else {
                out.println(" else if (param == " + i + ") {");
            }
            
            out.println( block );
            out.println("}");
            
        }
        return buff.toString();
        
    }
    private String getSetterBlockForField(Field nativeField) {
        Type nativeType = nativeField.getType();
        if ( nativeType.getTypeName().equals("int") ) {
            return nativeField.getUnqualifiedName() + " = ((Integer) obj).intValue();";
        } else if (nativeType.getTypeName().equals("long")) {
            return nativeField.getUnqualifiedName() + " = ((Long) obj).longValue();";
        } else if (nativeType.getTypeName().equals("java.lang.String")) {
            return nativeField.getUnqualifiedName() + " = (String) obj;";
        } else {
            return nativeField.getUnqualifiedName() + " = (" + nativeType.getTypeName() + ") obj;";
        }
    }
    private String getGetterBlockForField(Field nativeField) {
        Type nativeType = nativeField.getType();
        if ( nativeType.getTypeName().equals("int") ) {
            return "return new Integer(" + nativeField.getUnqualifiedName() + ");";
        } else if (nativeType.getTypeName().equals("long")) {
            return "return new Long(" + nativeField.getUnqualifiedName() + ");";
        } else if (nativeType.getTypeName().equals("java.lang.String")) {
            return "return new String(" + nativeField.getUnqualifiedName() + ");";
        } else {
            return "return " + nativeField.getUnqualifiedName() + ";";
        }
    }
}
