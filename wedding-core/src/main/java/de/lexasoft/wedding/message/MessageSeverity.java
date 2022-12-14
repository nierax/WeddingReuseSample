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
 * Severity of a message.
 * 
 * @author nierax
 *
 */
public enum MessageSeverity {

	NONE(0), HINT(10), WARNING(50), ERROR(100);

	private final int level;

	private MessageSeverity(int level) {
		this.level = level;
	}

	public int level() {
		return level;
	}

	public boolean isHigher(MessageSeverity other) {
		return this.level > other.level;
	}
}
