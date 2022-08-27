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
 * Value object of a family name.
 * 
 * @author nierax
 *
 */
public class FamilyName extends ValueObject<String> {

	/**
	 * @param value
	 */
	private FamilyName(String value) {
		super(value);
	}

	public final static FamilyName of(String value) {
		return new FamilyName(value);
	}
}
