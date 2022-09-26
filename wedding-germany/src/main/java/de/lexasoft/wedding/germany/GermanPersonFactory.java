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

import de.lexasoft.wedding.AbstractPersonFactory;
import de.lexasoft.wedding.Birthday;
import de.lexasoft.wedding.Country;
import de.lexasoft.wedding.FamilyName;
import de.lexasoft.wedding.FirstName;
import de.lexasoft.wedding.PersonId;
import de.lexasoft.wedding.Person;
import de.lexasoft.wedding.Sex;
import de.lexasoft.wedding.ValidateAgeForMarriage;

/**
 * This factory generates a person ready to use in Germany.
 * 
 * @author nierax
 *
 */
public class GermanPersonFactory extends AbstractPersonFactory {

	/**
	 * 
	 */
	private GermanPersonFactory() {
	}

	public static final GermanPersonFactory of() {
		return new GermanPersonFactory();
	}

	public Person createPerson(PersonId id, FamilyName familyName, FirstName firstName, Sex sex, Birthday birthday) {
		Person person = createPerson(id, familyName, firstName, sex, birthday, Country.GERMANY);
		person.addValidation(ValidateAgeForMarriage.of(18));
		return person;
	}

	public Person createPerson(FamilyName familyName, FirstName firstName, Sex sex, Birthday birthday) {
		return createPerson(PersonId.of(), familyName, firstName, sex, birthday);
	}

}
