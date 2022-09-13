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
 * Represents any date in the model.
 * 
 * @author nierax
 *
 */
@SuppressWarnings("serial")
public class Date extends ValueObject<LocalDate> {

	protected Date(LocalDate value) {
		super(value);
	}

	/**
	 * Returns a Date object with the given value.
	 * 
	 * @param value The date to be represented.
	 * @return Date object with the given value
	 */
	public static Date of(LocalDate value) {
		return new Date(value);
	}

	/**
	 * Returns a Date object with the given value.
	 * 
	 * @param year       The year of the date
	 * @param month      The month of the date
	 * @param dayOfMonth The day in month of the date
	 * @return Date object with the given value
	 */
	public static Date of(int year, int month, int dayOfMonth) {
		return Date.of(LocalDate.of(year, month, dayOfMonth));
	}

}
