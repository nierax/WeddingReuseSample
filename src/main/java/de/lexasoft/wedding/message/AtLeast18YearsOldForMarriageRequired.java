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
package de.lexasoft.wedding.message;

import de.lexasoft.wedding.Person;

/**
 * @author nierax
 *
 */
public class AtLeast18YearsOldForMarriageRequired extends Message {

	/**
	 * 
	 */
	public AtLeast18YearsOldForMarriageRequired(Person person) {
		super(
		    MessageText
		        .of(String.format("Person %s must be at least 18 years old, but is %", person, person.ageInYearsToday())),
		    MessageSeverity.ERROR);
	}

}
