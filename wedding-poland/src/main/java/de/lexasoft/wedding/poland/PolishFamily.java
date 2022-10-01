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
package de.lexasoft.wedding.poland;

import java.util.ArrayList;
import java.util.List;

import de.lexasoft.wedding.PartnerShip;
import de.lexasoft.wedding.Person;
import de.lexasoft.wedding.ValidateAgeForMarriage;
import de.lexasoft.wedding.ValidateMarriage;
import de.lexasoft.wedding.ValidateMarriageRunner;
import de.lexasoft.wedding.ValidateNotMarriedBefore;
import de.lexasoft.wedding.message.Message;

/**
 * Represents the family in Poland.
 * 
 * @author nierax
 *
 */
public class PolishFamily {

	private final ValidateMarriage validations;
	private final Person partner1;
	private final Person partner2;
	private PartnerShip partnerShip;

	/**
	 * 
	 */
	private PolishFamily(Person partner1, Person partner2) {
		this.partner1 = partner1;
		this.partner2 = partner2;
		this.validations = configureValidations(new ValidateMarriageRunner());
		this.partnerShip = PartnerShip.NOT_MARRIED;
	}

	private ValidateMarriage configureValidations(ValidateMarriageRunner runner) {
		runner.addValidation(ValidateAgeForMarriage.of(18));
		runner.addValidation(ValidateNotMarriedBefore.of());
		runner.addValidation(ValidateHeteroSexMarriageOnly.of());
		return runner;
	}

	/**
	 * Creates the family object for the given persons as partners.
	 * 
	 * @param partner1
	 * @param partner2
	 * @return
	 */
	public final static PolishFamily of(Person partner1, Person partner2) {
		return new PolishFamily(partner1, partner2);
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

}
