package seguedev.srceditor.ant;

import seguedev.srceditor.generators.*;

import java.io.*;
import java.util.*;
import de.gulden.util.javasource.*;
import de.gulden.util.javasource.sourclet.*;
import de.gulden.util.javasource.sourclet.standard.*;

import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;

public class KSOAPSerialTask extends Task {
    
    private boolean replaceExisting = true;
    private boolean backupOriginal = true;
    private List filesets = new LinkedList(); // List<FileSet>
    
    public KSOAPSerialTask() {
    }
    
    public void init() {
    }
    
    public void addFileset(FileSet fs) {
        filesets.add(fs);
    }
    
    public void examineFile(File dir, String name) throws BuildException {
        try {
            File file = new File(dir, name );
            de.gulden.util.javasource.Class clazz = SourceFileMgr.loadSourceFile( file );
            boolean result = SourceFileMgr.checkAndModify( clazz, isReplaceExisting() );
            if ( result ) {
                String src = SourceFileMgr.reconstituteSourceFile( clazz );
                if ( isBackupOriginal() ) {
                    File backup = new File( file.getAbsolutePath() + ".sav" );
                    file.renameTo( backup );
                }
                FileWriter out = new FileWriter( new File(dir, name) );
                out.write( src );
                out.close();
            }
        } catch(java.lang.Exception e) {
            throw new BuildException( e );
        }
    }
    
    public void execute() throws BuildException {
        Iterator it = filesets.iterator();
        while (it.hasNext()) {
            FileSet fs = (FileSet)it.next();
            DirectoryScanner ds = fs.getDirectoryScanner(project);
            File basedir = ds.getBasedir();
            String[] files = ds.getIncludedFiles();
            for (int x=0; x < files.length; x++ ) {
                examineFile( basedir, files[x] );
            }
        }
    }
    
    public boolean isBackupOriginal() {
        return backupOriginal;
    }
    
    public void setBackupOriginal(boolean backupOriginal) {
        this.backupOriginal = backupOriginal;
    }
    
    public boolean isReplaceExisting() {
        return replaceExisting;
    }
    
    public void setReplaceExisting(boolean replaceExisting) {
        this.replaceExisting = replaceExisting;
    }
    
}
