The purpose of this Ant Task is to help modify any Classes intending to be
serialized via the KSOAP framework. As the KSOAP framework dictates, any
class being sent from to or from a MIDP/CLDC device needs to implements the
KvmSerializable interface.

This script helps by adding ALL the neccessary methods (and their implementation logic!) for you.
It assumes all private/protected/public instance variables are to be serialized.
It assumes that only there are no nested classes.
It assumes that there are no arrays. (Next thing to be implemented)


The Ant Task is a intended to run during design/compile time.
It should be run before any Javac is called as the source code is modified.

The Task takes two parameters: 

1. replaceExisting, if set to 'true', will overwrite the the KvmSerializable methods already exist.
2. backupOriginal, if set to 'true', will rename the file to a *.sav file within the same directory.


This Ant Task relies on a package called BeautyJ for the Source Code manipulation.
See: http://beautyj.berlios.de/

I have had modify the code slightly (I changed one class: Method) in order to use it within
my code. Please be sure to use the 'beautyj.jar' file included in this zip.

Make sure that all the *.jar within this zip are included with the Ant runtime execution, in
order to work properly.


To use the Ant Taskm follow the below template.

    <taskdef name="KSOAPSerialize" classpath="[add jars here]" classname="seguedev.srceditor.ant.KSOAPSerialTask"/>


    <target depends="init" name="generateKvmCode">
        <KSOAPSerialize backupOriginal="true" replaceExisting="true">
            <fileset dir="[basedir of file candidates]" >
                <include name="SystemAlert.java"/>
            </fileset>
        </KSOAPSerialize>
    </target>



Good luck, hope this helps.
