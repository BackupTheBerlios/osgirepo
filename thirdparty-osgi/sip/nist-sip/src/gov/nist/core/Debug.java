/*******************************************************************************
* Product of NIST/ITL Advanced Networking Technologies Division (ANTD)         *
*******************************************************************************/
package gov.nist.core;

/**
 *   A class to do debug printfs
 */
public class Debug {

	public static final boolean debug = false;
	public static final boolean parserDebug = false;

	public static void println(String s) {
		if (debug)
			System.out.println(s + "\n");
	}
	public static void printStackTrace(Exception ex) {
		if (debug) {
			ex.printStackTrace();
		}
	}

}
/*
 * $Log: Debug.java,v $
 * Revision 1.1  2004/11/15 14:24:53  afrei
 * initial checkin to osgirepo
 *
 * Revision 1.1  2004/11/14 14:30:07  andfrei
 * checkin of sip, btapi, axis
 *
 * Revision 1.7  2004/01/22 13:26:27  sverker
 * Issue number:
 * Obtained from:
 * Submitted by:  sverker
 * Reviewed by:   mranga
 *
 * Major reformat of code to conform with style guide. Resolved compiler and javadoc warnings. Added CVS tags.
 *
 * CVS: ----------------------------------------------------------------------
 * CVS: Issue number:
 * CVS:   If this change addresses one or more issues,
 * CVS:   then enter the issue number(s) here.
 * CVS: Obtained from:
 * CVS:   If this change has been taken from another system,
 * CVS:   then name the system in this line, otherwise delete it.
 * CVS: Submitted by:
 * CVS:   If this code has been contributed to the project by someone else; i.e.,
 * CVS:   they sent us a patch or a set of diffs, then include their name/email
 * CVS:   address here. If this is your work then delete this line.
 * CVS: Reviewed by:
 * CVS:   If we are doing pre-commit code reviews and someone else has
 * CVS:   reviewed your changes, include their name(s) here.
 * CVS:   If you have not had it reviewed then delete this line.
 *
 */
