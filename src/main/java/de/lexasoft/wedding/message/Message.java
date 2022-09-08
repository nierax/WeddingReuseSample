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

/**
 * Represents a message from a method.
 * <p>
 * Is abstract because there should be defined a specific message for every
 * situation.
 * 
 * @author nierax
 *
 */
public abstract class Message {

	private final MessageText text;
	private final MessageSeverity severity;

	/**
	 * Must not be instantiated from outside the extension hierarchy.
	 */
	protected Message(MessageText text, MessageSeverity severity) {
		this.text = text;
		this.severity = severity;
	}

	/**
	 * @return the text
	 */
	public MessageText text() {
		return text;
	}

	/**
	 * @return the severity
	 */
	public MessageSeverity severity() {
		return severity;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Message)) {
			return false;
		}
		return ((Message) obj).getClass().equals(this.getClass());
	}

}
