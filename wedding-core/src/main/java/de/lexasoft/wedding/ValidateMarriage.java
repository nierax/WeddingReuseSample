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

import java.util.List;

import de.lexasoft.wedding.message.Message;

/**
 * Interface defining method for checking preconditions for marriage.
 * 
 * @author nierax
 *
 */
public interface ValidateMarriage {

	/**
	 * Method used for validating, whether it is allowed for these two people to get
	 * married.
	 * 
	 * @param person1
	 * @param person2
	 * @return
	 */
	List<Message> marriageAllowed(Person person1, Person person2);

}
