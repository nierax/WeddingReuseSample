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

import de.lexasoft.wedding.AbstractFamily;
import de.lexasoft.wedding.FamilyId;
import de.lexasoft.wedding.Person;

/**
 * Represents the family in Poland.
 * 
 * @author nierax
 *
 */
public class PolishFamily extends AbstractFamily {

	/**
	 * 
	 */
	private PolishFamily(FamilyId familyId, Person partner1, Person partner2) {
		super(familyId, new PolishMarriageValidations(), partner1, partner2);
	}

	/**
	 * Creates the family object for the given persons as partners.
	 * 
	 * @param familyId
	 * @param partner1
	 * @param partner2
	 * @return
	 */
	public final static PolishFamily of(FamilyId familyId, Person partner1, Person partner2) {
		return new PolishFamily(familyId, partner1, partner2);
	}

	/**
	 * Creates the family object for the given persons as partners.
	 * 
	 * @param partner1
	 * @param partner2
	 * @return
	 */
	public final static PolishFamily of(Person partner1, Person partner2) {
		return PolishFamily.of(FamilyId.of(), partner1, partner2);
	}

}
