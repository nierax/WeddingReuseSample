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

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.wedding.message.Message;
import de.lexasoft.wedding.message.MessageSeverity;
import de.lexasoft.wedding.message.MessageText;

/**
 * @author nierax
 *
 */
class ValidateMarriageRunnerTest {

	private class TestMessage extends Message {

		protected TestMessage(MessageSeverity severity) {
			super(MessageText.of("Any message text"), severity);
		}

	}

	private ValidateMarriageRunner cut;
	private Person person1;
	private Person person2;
	private List<Message> messages;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new ValidateMarriageRunner();
		messages = new ArrayList<>();
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

	/**
	 * If the runner has one entry with an error message, this one should be in the
	 * list.
	 */
	@Test
	final void one_entry_with_error_must_be_in_list() {
		cut.addValidation((person1, person2) -> {
			messages.add(new TestMessage(MessageSeverity.ERROR));
			return messages;
		});

		List<Message> resultMessages = cut.marriageAllowed(person1, person2);
		assertEquals(1, resultMessages.size());
		assertEquals(MessageSeverity.ERROR, resultMessages.get(0).severity());
	}

	/**
	 * If the runner has one entry with no message, the returned list should be
	 * empty.
	 */
	@Test
	final void one_entry_with_no_message_list_must_be_empty() {
		cut.addValidation((person1, person2) -> {
			return messages;
		});

		List<Message> resultMessages = cut.marriageAllowed(person1, person2);
		assertEquals(0, resultMessages.size());
	}

	/**
	 * If the runner has two entries with one being an error and the other one no
	 * message, the returned list should contain the error message, only.
	 */
	@Test
	final void two_entries_with_one_error_and_no_message_list_must_contain_error() {
		cut.addValidation((person1, person2) -> {
			messages.add(new TestMessage(MessageSeverity.ERROR));
			return messages;
		});

		cut.addValidation((person1, person2) -> {
			return messages;
		});

		List<Message> resultMessages = cut.marriageAllowed(person1, person2);
		assertEquals(1, resultMessages.size());
		assertEquals(MessageSeverity.ERROR, resultMessages.get(0).severity());
	}

	/**
	 * If the runner has two entries with one being an error and the other one a
	 * warning, the returned list should contain both messages.
	 */
	@Test
	final void two_entries_with_one_error_and_a_warning_both_message_must_be_in_list() {
		cut.addValidation((person1, person2) -> {
			messages.add(new TestMessage(MessageSeverity.ERROR));
			return messages;
		});

		cut.addValidation((person1, person2) -> {
			messages.add(new TestMessage(MessageSeverity.WARNING));
			return messages;
		});

		List<Message> resultMessages = cut.marriageAllowed(person1, person2);
		assertEquals(1, resultMessages.size());
		assertEquals(MessageSeverity.ERROR, resultMessages.get(0).severity());
		assertEquals(MessageSeverity.WARNING, resultMessages.get(1).severity());
	}

}
