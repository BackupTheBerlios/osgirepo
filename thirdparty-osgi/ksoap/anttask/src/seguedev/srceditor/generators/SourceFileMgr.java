// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SourceFileMgr.java

package seguedev.srceditor.generators;

import de.gulden.util.javasource.*;
import de.gulden.util.javasource.sourclet.SourcletOptions;
import de.gulden.util.javasource.sourclet.standard.StandardSourclet;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.*;

// Referenced classes of package seguedev.srceditor.generators:
//            ClassBuilder

public class SourceFileMgr
{
    public static class Options
        implements SourcletOptions
    {

        public int getIntOption(String option)
        {
            if(options.containsKey(option))
            {
                String s = (String)options.get(option);
                return Integer.parseInt(s);
            } else
            {
                return -1;
            }
        }

        public String getOption(String option)
        {
            if(options.containsKey(option))
            {
                String s = (String)options.get(option);
                return s;
            } else
            {
                return null;
            }
        }

        public boolean isOption(String option)
        {
            return options.containsKey(option);
        }

        HashMap options;

        protected Options()
        {
            options = new HashMap();
            options.put("code.indent.spaces", "5");
            options.put("doc.exception.text", "");
            options.put("code.qualify", "true");
        }
    }

    public static class ProgressTrackerImpl
        implements ProgressTracker
    {

        public void done(int i)
        {
        }

        public void todo(int i)
        {
        }

        public ProgressTrackerImpl()
        {
        }
    }


    public SourceFileMgr()
    {
    }

    public static boolean checkAndModify(Class clazz, boolean overwrite)
        throws Exception
    {
        boolean shouldContinue = false;
        for(Enumeration e = clazz.getInterfaceNames(); e.hasMoreElements();)
        {
            String name = e.nextElement().toString();
            if(name.equals("java.io.Serializable"))
            {
                shouldContinue = true;
                break;
            }
            if(name.equals("org.kobjects.serialization.KvmSerializable"))
            {
                shouldContinue = true;
                break;
            }
        }

        if(!shouldContinue)
            return false;
        HashMap fields = new HashMap();
        for(NamedIterator i = clazz.getAllMembers(); i.hasMore();)
        {
            de.gulden.util.javasource.Named named = i.next();
            if(named.getClass() == (de.gulden.util.javasource.Field.class))
            {
                Field field = (Field)named;
                fields.put(field.getUnqualifiedName(), field);
            }
        }

        Field xfields[] = (Field[])fields.values().toArray(new Field[0]);
        ClassBuilder builder = new ClassBuilder(clazz, xfields, overwrite);
        builder.implementInterface();
        builder.reconstructFields();
        builder.reconstructMethods();
        return true;
    }

    public static Class loadSourceFile(File file)
        throws Exception
    {
        Package pkg = SourceParser.parse(file, new ProgressTrackerImpl());
        return (Class)pkg.getAllClasses().next();
    }

    public static String reconstituteSourceFile(Class clazz)
        throws Exception
    {
        StandardSourclet sourceLet = new StandardSourclet();
        sourceLet.setOptions(new Options());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        sourceLet.buildSource(out, clazz);
        return (new String(out.toByteArray())).toString();
    }
}
