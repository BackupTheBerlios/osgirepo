// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KSOAPSerialTask.java

package seguedev.srceditor.ant;

import java.io.*;
import java.util.*;
import org.apache.tools.ant.*;
import org.apache.tools.ant.types.FileSet;
import seguedev.srceditor.generators.SourceFileMgr;

public class KSOAPSerialTask extends Task
{

    public KSOAPSerialTask()
    {
        replaceExisting = true;
        backupOriginal = true;
        filesets = new LinkedList();
    }

    public void init()
    {
        System.out.println("task init");
    }

    public void addFileset(FileSet fs)
    {
        filesets.add(fs);
    }

    public void examineFile(File dir, String name)
        throws BuildException
    {
        try
        {
            File file = new File(dir, name);
            de.gulden.util.javasource.Class clazz = SourceFileMgr.loadSourceFile(file);
            boolean result = SourceFileMgr.checkAndModify(clazz, isReplaceExisting());
            if(result)
            {
                String src = SourceFileMgr.reconstituteSourceFile(clazz);
                if(isBackupOriginal())
                {
                    File backup = new File(file.getAbsolutePath() + ".sav");
                    file.renameTo(backup);
                }
                FileWriter out = new FileWriter(new File(dir, name));
                out.write(src);
                out.close();
            }
        }
        catch(Exception e)
        {
            throw new BuildException(e);
        }
    }

    public void execute()
        throws BuildException
    {
        for(Iterator it = filesets.iterator(); it.hasNext();)
        {
            FileSet fs = (FileSet)it.next();
            DirectoryScanner ds = fs.getDirectoryScanner(project);
            File basedir = ds.getBasedir();
            String files[] = ds.getIncludedFiles();
            for(int x = 0; x < files.length; x++)
            {
                examineFile(basedir, files[x]);
                System.out.println("I see: " + files[x]);
            }

        }

    }

    public boolean isBackupOriginal()
    {
        return backupOriginal;
    }

    public void setBackupOriginal(boolean backupOriginal)
    {
        this.backupOriginal = backupOriginal;
    }

    public boolean isReplaceExisting()
    {
        return replaceExisting;
    }

    public void setReplaceExisting(boolean replaceExisting)
    {
        this.replaceExisting = replaceExisting;
    }

    boolean replaceExisting;
    boolean backupOriginal;
    private List filesets;
}
