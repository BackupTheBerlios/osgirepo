
/*******************************************************************************
* Product of NIST/ITL Advanced Networking Technologies Division (ANTD).        *
*******************************************************************************/
package gov.nist.javax.sip.header;

/**
 * WWWAuthenticate SIPHeader (of which there can be several?)
 *
 * @version JAIN-SIP-1.1 $Revision: 1.1 $ $Date: 2004/11/15 14:24:53 $
 *
 * @author M. Ranganathan <mranga@nist.gov>  <br/>
 *
 * <a href="{@docRoot}/uncopyright.html">This code is in the public domain.</a>
 *
 */
public class AuthorizationList extends SIPHeaderList {

	/**
	 * constructor.
	 */
	public AuthorizationList() {
		super(Authorization.class, Authorization.NAME);
	}
}
