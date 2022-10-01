/**
 * Copyright (C) 2022 admin
 * This program is free software; you can redistribute it and/or modify it under the terms of 
 * the GNU General Public License as published by the Free Software Foundation; either version 3 
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * 
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; 
 * if not, see <http://www.gnu.org/licenses/>. 
 */
package de.lexasoft.wedding.usa;

import java.util.ArrayList;
import java.util.List;

import de.lexasoft.wedding.Date;
import de.lexasoft.wedding.FamilyId;
import de.lexasoft.wedding.PartnerShip;
import de.lexasoft.wedding.Person;
import de.lexasoft.wedding.Result;
import de.lexasoft.wedding.ValidateAgeForMarriage;
import de.lexasoft.wedding.ValidateMarriage;
import de.lexasoft.wedding.ValidateMarriageRunner;
import de.lexasoft.wedding.ValidateNotMarriedBefore;
import de.lexasoft.wedding.message.Message;

/**
 * Represents a family in USA.
 * 
 * @author nierax
 *
 */
public class USAFamily {

	private final FamilyId familyId;
	private final Person partner1;
	private final Person partner2;
	private final USAState federalState;
	private PartnerShip partnerShip;
	private Date dateOfWedding;
	private final ValidateMarriage validations;

	/**
	 * 
	 */
	private USAFamily(FamilyId familyId, Person partner1, Person partner2, USAState state) {
		this.familyId = familyId;
		this.partner1 = partner1;
		this.partner2 = partner2;
		this.federalState = state;
		this.validations = createValidations(state);
		this.dateOfWedding = Date.NONE;
		this.partnerShip = PartnerShip.NOT_MARRIED;
	}

	private ValidateMarriage createValidations(USAState state) {
		ValidateMarriageRunner runner = new ValidateMarriageRunner();
		runner.addValidation(ValidateNotMarriedBefore.of());
		runner.addValidation(ValidateAgeForMarriage.of(state.minimumAge()));
		return runner;
	}

	/**
	 * Check, whether the partner are allowed to marry.
	 * 
	 * @return A list of messages in case of any violation.
	 */
	public List<Message> allowedToMarry() {
		List<Message> messages = new ArrayList<>();
		messages.addAll(validations.marriageAllowed(partner1, partner2));
		return messages;
	}

	/**
	 * Marry the two partner in this family.
	 * 
	 * @return
	 */
	public Result<USAFamily> marry() {
		Result<USAFamily> result = Result.of(this, allowedToMarry());
		if (!result.isErroneous()) {
			this.partnerShip = PartnerShip.MARRIED;
			partner1.marries(familyId());
			partner2.marries(familyId());
			this.dateOfWedding = Date.TODAY;
		}
		return result;
	}

	/**
	 * @return the familyId
	 */
	public FamilyId familyId() {
		return familyId;
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
	 * @return the federalState
	 */
	public USAState federalState() {
		return federalState;
	}

	/**
	 * @return the partnerShip
	 */
	public PartnerShip partnerShip() {
		return partnerShip;
	}

	/**
	 * @return the dateOfWedding
	 */
	public Date dateOfWedding() {
		return dateOfWedding;
	}

	public final static USAFamily of(FamilyId family, Person partner1, Person partner2, USAState state) {
		return new USAFamily(family, partner1, partner2, state);
	}

	public final static USAFamily of(Person partner1, Person partner2, USAState state) {
		return new USAFamily(FamilyId.of(), partner1, partner2, state);
	}

}
