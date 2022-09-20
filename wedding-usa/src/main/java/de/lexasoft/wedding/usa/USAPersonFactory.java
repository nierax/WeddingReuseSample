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

import de.lexasoft.wedding.AbstractPersonFactory;
import de.lexasoft.wedding.Birthday;
import de.lexasoft.wedding.Country;
import de.lexasoft.wedding.FamilyName;
import de.lexasoft.wedding.FirstName;
import de.lexasoft.wedding.Identity;
import de.lexasoft.wedding.Person;
import de.lexasoft.wedding.Sex;
import de.lexasoft.wedding.ValidateAgeForMarriage;

/**
 * @author nierax
 *
 */
public class USAPersonFactory extends AbstractPersonFactory {

	private final USAState state;

	/**
	 * Use the factory method instead of the constructor
	 */
	private USAPersonFactory(USAState state) {
		this.state = state;
	}

	/**
	 * Creates a factory for the given US State
	 * 
	 * @param state The state, this factory is used in
	 * @return A new factory instance
	 */
	public final static USAPersonFactory of(USAState state) {
		return new USAPersonFactory(state);
	}

	public Person createPerson(Identity id, FamilyName familyName, FirstName firstName, Sex sex, Birthday birthday) {
		Person person = createPerson(id, familyName, firstName, sex, birthday, Country.UNITED_STATES);
		person.addValidation(ValidateAgeForMarriage.of(state.minimumAge()));
		return person;
	}

	public Person createPerson(FamilyName familyName, FirstName firstName, Sex sex, Birthday birthday) {
		return createPerson(Identity.of(), familyName, firstName, sex, birthday);
	}

}
