/*******************************************************************************
* Product of NIST/ITL Advanced Networking Technologies Division (ANTD)         *
*******************************************************************************/
package gov.nist.javax.sip.parser;
import gov.nist.javax.sip.message.*;

/**
 * Interface  that provides methods for processing good 
 * and bad messages for the PipelinedMessageParser.
 * 
 * @version JAIN-SIP-1.1 $Revision: 1.1 $ $Date: 2004/11/15 14:24:54 $
 * @see PipelinedMsgParser
 */
public interface SIPMessageListener extends ParseExceptionListener {
	/**
	 * This is called from the parser on successful message processing.
	 * @see ParseExceptionListener for the method that gets called 
	 *	on parse exception.
	 * @param msg  SIP Message structure that is generated by the parser.
	 */
	public void processMessage(SIPMessage msg) throws Exception;
}
/*
 * $Log: SIPMessageListener.java,v $
 * Revision 1.1  2004/11/15 14:24:54  afrei
 * initial checkin to osgirepo
 *
 * Revision 1.1  2004/11/14 14:30:10  andfrei
 * checkin of sip, btapi, axis
 *
 * Revision 1.5  2004/02/29 00:46:34  mranga
 * Reviewed by:   mranga
 * Added new configuration property to limit max message size for TCP transport.
 * The property is gov.nist.javax.sip.MAX_MESSAGE_SIZE
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
