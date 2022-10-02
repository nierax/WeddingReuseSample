package de.lexasoft.wedding;

import java.util.ArrayList;
import java.util.List;

import de.lexasoft.wedding.message.Message;

/**
 * The base class for the family.
 * <p>
 * Provides common functionality for marriage.
 * 
 * @author nierax
 *
 */
public abstract class AbstractFamily {

	protected final FamilyId familyId;

	protected final ValidateMarriage validations;
	protected final Person partner1;
	protected final Person partner2;
	protected PartnerShip partnerShip;
	protected Date dateOfWedding;

	protected AbstractFamily(FamilyId familyId, ValidateMarriage validations, Person partner1, Person partner2) {
		super();
		this.familyId = familyId;
		this.validations = validations;
		this.partner1 = partner1;
		this.partner2 = partner2;
		this.partnerShip = PartnerShip.NOT_MARRIED;
		this.dateOfWedding = Date.NONE;
	}

	/**
	 * Checks, whether both partner are allowed to marry.
	 * 
	 * @return
	 */
	public List<Message> allowedToMarry() {
		List<Message> messages = new ArrayList<>();
		messages.addAll(validations.marriageAllowed(partner2, partner1));
		return messages;
	}

	public Result<AbstractFamily> marry() {
		Result<AbstractFamily> result = Result.of(this, allowedToMarry());
		if (!result.isErroneous()) {
			partnerShip(PartnerShip.MARRIED);
			partner1().marries(familyId());
			partner2().marries(familyId());
			this.dateOfWedding = Date.TODAY;
		}
		return result;
	}

	/**
	 * @return the partnerShip
	 */
	public PartnerShip partnerShip() {
		return partnerShip;
	}

	/**
	 * @param partnerShip the partnerShip to set
	 */
	PartnerShip partnerShip(PartnerShip partnerShip) {
		return (this.partnerShip = partnerShip);
	}

	/**
	 * @return the partner1
	 */
	public Person partner1() {
		return partner1;
	}

	/**
	 * @return the partner2
	 */
	public Person partner2() {
		return partner2;
	}

	/**
	 * @return the dateOfWedding
	 */
	public Date dateOfWedding() {
		return dateOfWedding;
	}

	/**
	 * @return the familyId
	 */
	public FamilyId familyId() {
		return familyId;
	}

}