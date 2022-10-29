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
package de.lexasoft.wedding.germany;

import de.lexasoft.wedding.ValidateAgeForMarriage;
import de.lexasoft.wedding.ValidateMarriageRunner;
import de.lexasoft.wedding.ValidateNotMarriedBefore;

/**
 * @author nierax
 *
 */
public class GermanMarriageValidations extends ValidateMarriageRunner {

	public final static int MINIMUM_AGE = 19;

	/**
	 * 
	 */
	GermanMarriageValidations() {
		addValidation(ValidateNotMarriedBefore.of());
		addValidation(ValidateAgeForMarriage.of(MINIMUM_AGE));
	}

}
