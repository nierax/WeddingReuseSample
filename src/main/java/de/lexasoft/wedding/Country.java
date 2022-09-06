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

import java.util.Locale;

/**
 * Defines countries in which the application is running.
 * 
 * @see https://kodejava.org/how-do-i-get-a-list-of-country-names/
 * 
 * @author nierax
 *
 */
public enum Country {

	UNITED_STATES("USA"), GERMANY("DEU"), POLAND("POL");

	private final Locale country;

	private Country(String isoCode) {
		country = new Locale("", isoCode);
	}

	public String iso3() {
		return country.getISO3Country();
	}

	public String countryCode() {
		return country.getCountry();
	}

	public String countryName() {
		return country.getDisplayCountry();
	}

	@Override
	public String toString() {
		return String.format("%s|%s|%s", iso3(), countryCode(), countryName());
	}

}
