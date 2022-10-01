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
import de.lexasoft.wedding.message.MustNotBeMarriedBeforeError;

/**
 * Validates, whether both partners are not already married.
 * 
 * @author nierax
 *
 */
public class ValidateNotMarriedBefore implements ValidateMarriage {

	/**
	 * 
	 */
	private ValidateNotMarriedBefore() {
	}

	@Override
	public List<Message> marriageAllowed(Person person1, Person person2) {
		List<Message> messages = new ArrayList<>();
		checkPersonNotMarried(person1, messages);
		checkPersonNotMarried(person2, messages);
		return messages;
	}

	/**
	 * @param person1
	 * @param messages
	 */
	private void checkPersonNotMarried(Person person1, List<Message> messages) {
		if (person1.partnerShip() == PartnerShip.MARRIED) {
			messages.add(new MustNotBeMarriedBeforeError(person1));
		}
	}

	public static final ValidateNotMarriedBefore of() {
		return new ValidateNotMarriedBefore();
	}

}
