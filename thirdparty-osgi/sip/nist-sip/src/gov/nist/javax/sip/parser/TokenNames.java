package gov.nist.javax.sip.parser;
import javax.sip.message.Request;
import gov.nist.javax.sip.address.*;
import gov.nist.javax.sip.header.*;

/**
 * A grab bag of SIP Token names.
 *
 * @version JAIN-SIP-1.1 $Revision: 1.1 $ $Date: 2004/11/15 14:24:53 $
 * 
 * @author M. Ranganathan <mranga@nist.gov>  <br/>
 *
 * <a href="{@docRoot}/uncopyright.html">This code is in the public domain.</a>
 */
public interface TokenNames
	extends
		gov.nist.javax.sip.header.ParameterNames,
		gov.nist.javax.sip.address.ParameterNames {
	// And now dreaded short forms....
	public static final String INVITE = Request.INVITE;
	public static final String ACK = Request.ACK;
	public static final String BYE = Request.BYE;
	public static final String SUBSCRIBE = Request.SUBSCRIBE;
	public static final String NOTIFY = Request.NOTIFY;
	public static final String OPTIONS = Request.OPTIONS;
	public static final String REGISTER = Request.REGISTER;
	public static final String MESSAGE = Request.MESSAGE;
	public static final String SIP = GenericURI.SIP;
	public static final String SIPS = GenericURI.SIPS;
	public static final String TEL = GenericURI.TEL;
	public static final String GMT = SIPDate.GMT;
	public static final String MON = SIPDate.MON;
	public static final String TUE = SIPDate.TUE;
	public static final String WED = SIPDate.WED;
	public static final String THU = SIPDate.THU;
	public static final String FRI = SIPDate.FRI;
	public static final String SAT = SIPDate.SAT;
	public static final String SUN = SIPDate.SUN;
	public static final String JAN = SIPDate.JAN;
	public static final String FEB = SIPDate.FEB;
	public static final String MAR = SIPDate.MAR;
	public static final String APR = SIPDate.APR;
	public static final String MAY = SIPDate.MAY;
	public static final String JUN = SIPDate.JUN;
	public static final String JUL = SIPDate.JUL;
	public static final String AUG = SIPDate.AUG;
	public static final String SEP = SIPDate.SEP;
	public static final String OCT = SIPDate.OCT;
	public static final String NOV = SIPDate.NOV;
	public static final String DEC = SIPDate.DEC;
	public static final String K = "K";
	public static final String C = "C";
	public static final String E = "E";
	public static final String F = "F";
	public static final String I = "I";
	public static final String M = "M";
	public static final String L = "L";
	public static final String S = "S";
	public static final String T = "T";
	public static final String V = "V";
	public static final String R = "R";
}
/*
 * $Log: TokenNames.java,v $
 * Revision 1.1  2004/11/15 14:24:53  afrei
 * initial checkin to osgirepo
 *
 * Revision 1.1  2004/11/14 14:30:08  andfrei
 * checkin of sip, btapi, axis
 *
 * Revision 1.4  2004/01/22 13:26:32  sverker
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
