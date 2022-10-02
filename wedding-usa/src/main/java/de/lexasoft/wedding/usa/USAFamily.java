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
package de.lexasoft.wedding.usa;

import de.lexasoft.wedding.AbstractFamily;
import de.lexasoft.wedding.FamilyId;
import de.lexasoft.wedding.Person;

/**
 * Represents a family in USA.
 * 
 * @author nierax
 *
 */
public class USAFamily extends AbstractFamily {

	private final USAState federalState;

	/**
	 * 
	 */
	private USAFamily(FamilyId familyId, Person partner1, Person partner2, USAState state) {
		super(familyId, new USAMarriageValidations(state), partner1, partner2);
		this.federalState = state;
	}

	/**
	 * @return the federalState
	 */
	public USAState federalState() {
		return federalState;
	}

	public final static USAFamily of(FamilyId family, Person partner1, Person partner2, USAState state) {
		return new USAFamily(family, partner1, partner2, state);
	}

	public final static USAFamily of(Person partner1, Person partner2, USAState state) {
		return new USAFamily(FamilyId.of(), partner1, partner2, state);
	}

}
