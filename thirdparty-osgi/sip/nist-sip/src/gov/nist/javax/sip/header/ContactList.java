/*******************************************************************************
* Product of NIST/ITL Advanced Networking Technologies Division (ANTD).        *
*******************************************************************************/
package gov.nist.javax.sip.header;

import javax.sip.header.*;
import java.util.ListIterator;

/**
 * List of contact headers.ContactLists are also maintained in a hashtable
 * for quick lookup.
 * @author M. Ranganathan <mranga@nist.gov>  <br/>
 * @version JAIN-SIP-1.1 $Revision: 1.1 $ $Date: 2004/11/15 14:24:53 $
 *
 *<a href="${docRoot}/uncopyright.html">This code is in the public domain.</a>
 */
public class ContactList extends SIPHeaderList {

	/**
	 * Constructor
	 * @param hl SIPObjectList
	 */
	public ContactList(SIPObjectList hl) {
		super(hl, CONTACT);
	}

	/**
	 * Constructor. 
	 */
	public ContactList() {
		super(Contact.class, ContactHeader.NAME);
		// Set the headerlist field in our superclass.
	}

	/**
	 * add a new contact header. Store it in the hashtable also
	 * @param contact -- contact to add to this list.
	 * @throws IllegalArgumentException if Duplicate Contact for same addr
	 */
	protected void add(Contact contact) throws IllegalArgumentException {
		// Concatenate my lists.
		super.add(contact);
	}

	/**
	 * make a clone of this contact list.
	 * @return Object cloned list.
	 */
	public Object clone() {
		ContactList retval = new ContactList();
		for (Contact c = (Contact) this.first();
			c != null;
			c = (Contact) this.next()) {
			Contact newc = (Contact) c.clone();
			retval.add(newc);
		}
		return retval;
	}

	/**
	 * Get an array of contact addresses.
	 *
	 * @return  array of contacts.
	 */
	public Contact[] getContacts() {
		Contact[] retval = new Contact[this.size()];

		ListIterator li = this.listIterator();
		int i = 0;
		while (li.hasNext()) {
			Contact nextContact = (Contact) li.next();
			retval[i] = nextContact;
			i++;
		}
		return retval;
	}
}
/*
 * $Log: ContactList.java,v $
 * Revision 1.1  2004/11/15 14:24:53  afrei
 * initial checkin to osgirepo
 *
 * Revision 1.1  2004/11/14 14:30:03  andfrei
 * checkin of sip, btapi, axis
 *
 * Revision 1.2  2004/01/22 13:26:29  sverker
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
