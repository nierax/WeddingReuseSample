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
 * Value object representing a date.
 * 
 * @author nierax
 *
 */
@SuppressWarnings("serial")
public class Date extends ValueObject<LocalDate> {

	public final static Date NONE = Date.of(0, 1, 1);
	public final static Date TODAY = Date.of(LocalDate.now());

	private Date(LocalDate value) {
		super(value);
	}

	/**
	 * Returns a date with the given value.
	 * 
	 * @param value Value to be used.
	 * @return
	 */
	public final static Date of(LocalDate value) {
		return new Date(value);
	}

	/**
	 * Returns a date with the given value.
	 * 
	 * @param year
	 * @param month
	 * @param dayOfMonth
	 * @return
	 */
	public final static Date of(int year, int month, int dayOfMonth) {
		return of(LocalDate.of(year, month, dayOfMonth));
	}

	/**
	 * Returns a date object with the value today.
	 * 
	 * @return Date object with the value today.
	 */
	public final static Date of() {
		return Date.of(LocalDate.now());
	}

}
