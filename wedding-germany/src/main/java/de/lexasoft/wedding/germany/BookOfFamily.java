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
package de.lexasoft.wedding.germany;

import java.util.ArrayList;
import java.util.List;

import de.lexasoft.wedding.Date;
import de.lexasoft.wedding.FamilyId;
import de.lexasoft.wedding.PartnerShip;
import de.lexasoft.wedding.Person;
import de.lexasoft.wedding.Result;
import de.lexasoft.wedding.message.Message;
import de.lexasoft.wedding.message.MustNotBeMarriedBeforeError;

/**
 * Represents the book of the family in Germany.
 * 
 * @author nierax
 *
 */
public class BookOfFamily {

	private final FamilyId id;
	private final List<Person> partner = new ArrayList<>();
	private PartnerShip kindOfPartnership;
	private final Date dateOfCreation;
	private Date dateOfWedding;

	/**
	 * 
	 */
	private BookOfFamily(FamilyId id, Person partner1, Person partner2) {
		this.partner.add(partner1);
		this.partner.add(partner2);
		this.kindOfPartnership = PartnerShip.NOT_MARRIED;
		this.id = id;
		this.dateOfCreation = Date.of();
		this.dateOfWedding = Date.NONE;
	}

	/**
	 * As a family must consist of two partners, the book of family needs to have
	 * two persons to work.
	 * 
	 * @param id
	 * @param partner1
	 * @param partner2
	 * @return
	 */
	public static final BookOfFamily of(FamilyId id, Person partner1, Person partner2) {
		return new BookOfFamily(id, partner1, partner2);
	}

	/**
	 * As a family must consist of two partners, the book of family needs to have
	 * two persons to work.
	 * 
	 * @param partner1
	 * @param partner2
	 * @return
	 */
	public static final BookOfFamily of(Person partner1, Person partner2) {
		return of(FamilyId.of(), partner1, partner2);
	}

	private List<Message> checkNotMarriedBefore(Person person) {
		List<Message> messages = new ArrayList<>();
		if (person.partnerShip() == PartnerShip.MARRIED) {
			messages.add(new MustNotBeMarriedBeforeError(person));
		}
		return messages;
	}

	/**
	 * Checks, whether both partners are allowed to marry according to laws in
	 * Germany.
	 * 
	 * @return
	 */
	public Result<Boolean> allowedToMarry() {
		List<Message> messages = new ArrayList<>();
		for (Person person : partner) {
			messages.addAll(checkNotMarriedBefore(person));
		}
		return Result.of(Boolean.valueOf(messages.size() == 0), messages);
	}

	/**
	 * The kind of partnership initially is set to NOT_MARRIED. The state can be
	 * changed by several use cases.
	 * 
	 * @return The kindOfPartnership
	 */
	public PartnerShip kindOfPartnership() {
		return kindOfPartnership;
	}

	/**
	 * @return the id
	 */
	public FamilyId id() {
		return id;
	}

	/**
	 * @return the dateOfWedding
	 */
	public Date dateOfWedding() {
		return dateOfWedding;
	}

	/**
	 * @param dateOfWedding the dateOfWedding to set
	 */
	private Date dateOfWedding(Date dateOfWedding) {
		return (this.dateOfWedding = dateOfWedding);
	}

	/**
	 * @return the dateOfCreation
	 */
	public Date dateOfCreation() {
		return dateOfCreation;
	}

}
