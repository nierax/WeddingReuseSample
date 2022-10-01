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

import de.lexasoft.wedding.message.Message;

/**
 * This implementation runs a number of validations and delivers their results
 * in a list of messages.
 * 
 * @author nierax
 *
 */
public class ValidateMarriageRunner implements ValidateMarriage {

	private final List<ValidateMarriage> validations = new ArrayList<>();

	@Override
	public List<Message> marriageAllowed(Person person1, Person person2) {
		List<Message> messages = new ArrayList<>();
		validations.forEach(v -> messages.addAll(v.marriageAllowed(person1, person2)));
		return messages;
	}

	/**
	 * Adds a validation to the list, which should be checked.
	 * 
	 * @param validation The validation to be checked.
	 * @return The runner itself for fluent API.
	 */
	public ValidateMarriageRunner addValidation(ValidateMarriage validation) {
		this.validations.add(validation);
		return this;
	}

}
