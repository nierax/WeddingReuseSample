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
 * Value object representing the birthday of a person.
 * 
 * @author nierax
 *
 */
@SuppressWarnings("serial")
public class Birthday extends ValueObject<LocalDate> {

	private Birthday(LocalDate value) {
		super(value);
	}

	public final static Birthday of(LocalDate value) {
		return new Birthday(value);
	}

	public final static Birthday of(int year, int month, int dayOfMonth) {
		return of(LocalDate.of(year, month, dayOfMonth));
	}

}
