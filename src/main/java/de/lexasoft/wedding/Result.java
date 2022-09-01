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
import de.lexasoft.wedding.message.MessageSeverity;

/**
 * @author nierax
 *
 */
@SuppressWarnings("serial")
public class Result<T> extends ValueObject<T> {

	/**
	 * Immutable list of messages
	 */
	private final List<Message> messages;

	private Result(T value) {
		super(value);
		messages = new ArrayList<>();
	}

	/**
	 * @return The messages in the result.
	 */
	public List<Message> messages() {
		return new ArrayList<Message>(messages);
	}

	/**
	 * Add this message to then result.
	 * 
	 * @param message
	 */
	public Result<T> addMessage(Message message) {
		messages.add(message);
		return this;
	}

	/**
	 * Add messages to this result from a list.
	 * 
	 * @param messages
	 */
	public Result<T> addMessages(List<Message> messages) {
		for (Message message : messages) {
			addMessage(message);
		}
		return this;
	}

	/**
	 * Returns the strongest severity in the list or NONE, if there is not message.
	 * 
	 * @return
	 */
	public MessageSeverity resultSeverity() {
		MessageSeverity severity = MessageSeverity.NONE;
		for (Message message : messages) {
			if (message.severity().isHigher(severity)) {
				severity = message.severity();
			}
		}
		return severity;
	}

	public boolean isErroneous() {
		return resultSeverity() == MessageSeverity.ERROR;
	}

	/**
	 * Factory method with a value only.
	 * 
	 * @param <T>
	 * @param value
	 * @return
	 */
	public final static <T> Result<T> of(T value) {
		return new Result<T>(value);
	}

	/**
	 * Factory method with a value and a single message.
	 * 
	 * @param <T>
	 * @param message
	 * @param value
	 * @return
	 */
	public final static <T> Result<T> of(T value, Message message) {
		return new Result<T>(value).addMessage(message);
	}

	/**
	 * Factory method with a value and a list of messages.
	 * 
	 * @param <T>
	 * @param messages
	 * @param value
	 * @return
	 */
	public final static <T> Result<T> of(T value, List<Message> messages) {
		return new Result<T>(value).addMessages(messages);
	}

}
