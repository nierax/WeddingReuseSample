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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.wedding.message.Message;
import de.lexasoft.wedding.message.MessageSeverity;

/**
 * @author nierax
 *
 */
class ValidateMarriageRunnerTest {

	private ValidateMarriageRunner cut;
	private Person person1;
	private Person person2;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new ValidateMarriageRunner();
		person1 = Person.of(//
		    FamilyName.of("FamilyName1"), //
		    FirstName.of("FirstName1"), //
		    Sex.of(SexEnum.MALE), //
		    Birthday.of(1990, 8, 11));
		person2 = Person.of(//
		    FamilyName.of("FamilyName2"), //
		    FirstName.of("FirstName2"), //
		    Sex.of(SexEnum.FEMALE), //
		    Birthday.of(1993, 9, 12));
	}

	/**
	 * If the runner is empty, it must not crash but deliver an empty list with a
	 * severity of {@link MessageSeverity#NONE}.
	 */
	@Test
	final void empty_List_Must_Not_Crash() {
		List<Message> messages = cut.marriageAllowed(person1, person2);
		assertEquals(0, messages.size());
	}

}
