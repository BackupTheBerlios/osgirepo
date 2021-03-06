package gov.nist.javax.sip.parser;

import java.text.ParseException;
import gov.nist.javax.sip.header.*;

/**
 * To Header parser.
 *
 * @version JAIN-SIP-1.1 $Revision: 1.1 $ $Date: 2004/11/15 14:24:53 $
 *
 * @author Olivier Deruelle <deruelle@nist.gov>  <br/>
 *
 * <a href="{@docRoot}/uncopyright.html">This code is in the public domain.</a>
 *
 */
public class ReferToParser extends AddressParametersParser {

	/**
	 * Creates new ToParser
	 * @param referTo String to set
	 */
	public ReferToParser(String referTo) {
		super(referTo);
	}

	protected ReferToParser(Lexer lexer) {
		super(lexer);
	}
	public SIPHeader parse() throws ParseException {

		headerName(TokenTypes.REFER_TO);
		ReferTo referTo = new ReferTo();
		super.parse(referTo);
		this.lexer.match('\n');
		return referTo;
	}

	public static void main(String args[]) throws ParseException {
		String to[] =
			{
				"Refer-To: <sip:+1-650-555-2222@ss1.wcom.com;user=phone>;tag=5617\n",
				"Refer-To: T. A. Watson <sip:watson@bell-telephone.com>\n",
				"Refer-To: LittleGuy <sip:UserB@there.com>\n",
				"Refer-To: sip:mranga@120.6.55.9\n",
				"Refer-To: sip:mranga@129.6.55.9 ; tag=696928473514.129.6.55.9\n" };

		for (int i = 0; i < to.length; i++) {
			ReferToParser tp = new ReferToParser(to[i]);
			ReferTo t = (ReferTo) tp.parse();
			System.out.println("encoded = " + t.encode());
		}
	}
}
/*
 * $Log: ReferToParser.java,v $
 * Revision 1.1  2004/11/15 14:24:53  afrei
 * initial checkin to osgirepo
 *
 * Revision 1.1  2004/11/14 14:30:08  andfrei
 * checkin of sip, btapi, axis
 *
 * Revision 1.3  2004/01/22 13:26:31  sverker
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
