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

import de.lexasoft.wedding.Person;
import de.lexasoft.wedding.ValidateMarriage;
import de.lexasoft.wedding.message.Message;

/**
 * Make sure, that only persons with different sex may marry.
 * 
 * @author nierax
 *
 */
public class ValidateHeteroSexMarriageOnly implements ValidateMarriage {

	/**
	 * 
	 */
	private ValidateHeteroSexMarriageOnly() {
	}

	/**
	 * 
	 * @return Validation {@link ValidateHeteroSexMarriageOnly}
	 */
	public static final ValidateHeteroSexMarriageOnly of() {
		return new ValidateHeteroSexMarriageOnly();
	}

	@Override
	public List<Message> marriageAllowed(Person person1, Person person2) {
		List<Message> messages = new ArrayList<>();
		if (person1.sex().equals(person2.sex())) {
			messages.add(new NoWeddingWithSameSexAllowed());
		}
		return messages;
	}

}
