package seguedev.srceditor.generators;

import de.gulden.util.javasource.*;
import de.gulden.util.javasource.sourclet.*;
import de.gulden.util.javasource.sourclet.standard.*;
import java.util.*;
import java.io.*;

public class SourceFileMgr {
        
    public static boolean checkAndModify(de.gulden.util.javasource.Class clazz, boolean overwrite) throws java.lang.Exception {
        
        boolean shouldContinue = false;
        Enumeration e = clazz.getInterfaceNames();
        while ( e.hasMoreElements() ) {
            String name = e.nextElement().toString();
            if ( name.equals("java.io.Serializable") ) {
                shouldContinue = true;
                break;
            } else if ( name.equals("org.kobjects.serialization.KvmSerializable") ) {
                shouldContinue = true;
                break;
            }
        }
        
        if ( ! shouldContinue ) {
            return false;
        }
        /*
         *  Get all Fields
         */
        HashMap fields = new HashMap();
        NamedIterator i = clazz.getAllMembers();
        while ( i.hasMore() ) {
            Named named = (Named) i.next();
            
            if ( named.getClass() == de.gulden.util.javasource.Field.class ) {
                de.gulden.util.javasource.Field field = (de.gulden.util.javasource.Field) named;
                fields.put(field.getUnqualifiedName(), field);  
            }
        }
                
        /*
         *  Now create/re-create KVM Fields and Method
         */
        Field[] xfields = (Field[]) fields.values().toArray(new Field[0]);
        ClassBuilder builder = new ClassBuilder(clazz, xfields, overwrite);
        
        builder.implementInterface();
        builder.reconstructFields();
        builder.reconstructMethods();
                
        return true;
    }
    
    public static de.gulden.util.javasource.Class loadSourceFile(File file) throws java.lang.Exception {
        de.gulden.util.javasource.Package pkg = SourceParser.parse( file, new ProgressTrackerImpl() );
        return (de.gulden.util.javasource.Class) pkg.getAllClasses().next();
    }
    
    public static String reconstituteSourceFile(de.gulden.util.javasource.Class clazz) throws java.lang.Exception {
        StandardSourclet sourceLet = new StandardSourclet();
        sourceLet.setOptions( new Options() );
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        sourceLet.buildSource(out, clazz );
        
        return new String(out.toByteArray()).toString();
    }
    
    
    /*
     *
     */
    public static class ProgressTrackerImpl implements ProgressTracker {
        
        public ProgressTrackerImpl() {
        }
        
        public void done(int param) {
        }
        
        public void todo(int param) {
        } 
    }
    
    public static class Options implements de.gulden.util.javasource.sourclet.SourcletOptions {
        
        HashMap options = new HashMap();
        
        protected Options() {
            options.put("code.indent.spaces", "5");
            options.put("doc.exception.text", "");
            options.put("code.qualify", "true");
            
        }
        
        public int getIntOption(String option) {
            if ( options.containsKey( option ) ) {
                String s = (String) options.get(option);
                return Integer.parseInt( s );
            } else {
                return -1;
            }
        }
        
        public String getOption(String option) {
            if ( options.containsKey( option ) ) {
                String s = (String) options.get(option);
                return s;
            } else {
                return null;
            }
        }
        
        public boolean isOption(String option) {
           if ( options.containsKey( option ) ) {
                return true;
            } else {
                return false;
            }
        }
        
    }
}
