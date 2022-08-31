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

/**
 * Represents a message from a method.
 * 
 * @author nierax
 *
 */
public abstract class Message {

	private final MessageText text;
	private final MessageSeverity severity;

	/**
	 * Must not be instantiated from outside the class.
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

}
