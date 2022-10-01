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
package de.lexasoft.wedding;

/**
 * Provides support methods to test persons.
 * 
 * @author nierax
 *
 */
public class PersonTestSupport {

	/**
	 * 
	 */
	private PersonTestSupport() {
	}

	/**
	 * Creates a person with the given parameters, being married.
	 * 
	 * @param familyName
	 * @param firstname
	 * @param sex
	 * @param age
	 * @return Person which is married.
	 */
	public final static Person createMarriedPerson(FamilyName familyName, FirstName firstname, Sex sex, int age,
	    Country country) {
		Person person = Person.of(familyName, firstname, sex, BirthdayTestSupport.birthdayForAge(age), country);
		person.marries(FamilyId.of());
		return person;
	}

}
