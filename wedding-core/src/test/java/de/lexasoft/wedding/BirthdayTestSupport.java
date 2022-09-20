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

import java.time.LocalDate;

/**
 * Some functionality often used for testing.
 * 
 * @author nierax
 *
 */
public class BirthdayTestSupport {

	/**
	 * 
	 */
	private BirthdayTestSupport() {
	}

	/**
	 * Creates a birthday, that applies to the date today.
	 * 
	 * @param age The age wanted.
	 * @return A birthday this day, the given numbers of years ago.
	 */
	public static final Birthday birthdayForAge(long age) {
		return Birthday.of(LocalDate.now().minusYears(age));
	}

}
