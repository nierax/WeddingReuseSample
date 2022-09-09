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

import de.lexasoft.wedding.message.Message;
import de.lexasoft.wedding.message.MessageSeverity;
import de.lexasoft.wedding.message.MessageText;

/**
 * Message for restricted marriage of same sex persons in Poland.
 * 
 * @author nierax
 *
 */
public class NoWeddingWithSameSexAllwoed extends Message {

	/**
	 * @param text
	 * @param severity
	 */
	public NoWeddingWithSameSexAllwoed() {
		super(MessageText.of("Not allowed to marry a person with same sex in Poland"), MessageSeverity.ERROR);
	}

}
