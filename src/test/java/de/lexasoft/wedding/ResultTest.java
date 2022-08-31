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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author nierax
 *
 */
class ResultTest {

	private Result<String> cut;
	private Message anotherHint = new Message(MessageText.of("My message text - hint"), MessageSeverity.HINT) {
	};

	private Message anotherWarning = new Message(MessageText.of("My message text - warning"), MessageSeverity.WARNING) {
	};

	private Message anotherError = new Message(MessageText.of("My message text - error"), MessageSeverity.ERROR) {
	};

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = Result.of("my value");
	}

	/**
	 * The messages must be immutable. Adding a message to the returned list must
	 * not affect the list inside the result object.
	 */
	@Test
	final void returned_messages_must_not_affect_result_list() {
		List<Message> messages = cut.messages();
		// Add another message
		messages.add(anotherHint);
		// Must not be in the original result.
		assertFalse(cut.messages().contains(anotherHint));
	}

	/**
	 * A message added via the add method must be in the messages list.
	 */
	@Test
	final void an_added_message_mus_be_inside_the_list() {
		cut.addMessage(anotherHint);
		assertTrue(cut.messages().contains(anotherHint));
	}

	/**
	 * Must find the highest severity in the list
	 */
	@Test
	final void resultSeverity_must_deliver_the_highest_severity_or_none() {
		// No message in the list - NONE expected
		assertEquals(MessageSeverity.NONE, cut.resultSeverity());
		// Add a Message with severity HINT
		cut.addMessage(anotherHint);
		assertEquals(MessageSeverity.HINT, cut.resultSeverity());
		// Add a Message with severity WARNING
		cut.addMessage(anotherWarning);
		assertEquals(MessageSeverity.WARNING, cut.resultSeverity());
		// Add a Message with severity ERROR
		cut.addMessage(anotherError);
		assertEquals(MessageSeverity.ERROR, cut.resultSeverity());
	}

}
