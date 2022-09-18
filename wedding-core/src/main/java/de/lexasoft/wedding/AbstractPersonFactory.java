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
 * Base for all person factories. Only way to instantiate a person object.
 * 
 * @author nierax
 *
 */
public abstract class AbstractPersonFactory {

	/**
	 * 
	 */
	protected AbstractPersonFactory() {
	}

	/**
	 * Creates a new person object with the given id.
	 * 
	 * @param id
	 * @param familyName
	 * @param firstName
	 * @param sex
	 * @param birthday
	 * @param country
	 * @return
	 */
	protected Person createPerson(Identity id, FamilyName familyName, FirstName firstName, Sex sex, Birthday birthday,
	    Country country) {
		return new Person(id, familyName, firstName, sex, birthday, country);
	}

}
