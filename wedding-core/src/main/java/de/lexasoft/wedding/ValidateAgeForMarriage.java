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

import java.util.ArrayList;
import java.util.List;

import de.lexasoft.wedding.message.AtLeast18YearsOldForMarriageRequired;
import de.lexasoft.wedding.message.Message;

/**
 * Ensures, that both persons are at least as old as a minimum age, given under
 * construction of the object.
 * 
 * @author nierax
 *
 */
public class ValidateAgeForMarriage implements ValidateMarriage {

	private final int minimumAge;

	/**
	 * 
	 */
	private ValidateAgeForMarriage(int minimumAge) {
		this.minimumAge = minimumAge;
	}

	/**
	 * Create a validation with the given minimum age.
	 * 
	 * @param minimumAge The minimum age, both persons should have.
	 * @return Object {@link ValidateAgeForMarriage}
	 */
	public final static ValidateAgeForMarriage of(int minimumAge) {
		return new ValidateAgeForMarriage(minimumAge);
	}

	private List<Message> validateAgeToday(Person person) {
		List<Message> messages = new ArrayList<>();
		if (person.ageInYearsToday() < minimumAge()) {
			messages.add(new AtLeast18YearsOldForMarriageRequired(person));
		}
		return messages;
	}

	/**
	 * Validates, whether both persons are at least as old as the minimum age is
	 * set.
	 * 
	 * @param person1
	 * @param person2
	 * @return A list of messages if a person is under the minimum age or an empty
	 *         list, if not.
	 */
	@Override
	public List<Message> marriageAllowed(Person person1, Person person2) {
		List<Message> messages = validateAgeToday(person1);
		messages.addAll(validateAgeToday(person2));
		return messages;
	}

	public int minimumAge() {
		return this.minimumAge;
	}

}
