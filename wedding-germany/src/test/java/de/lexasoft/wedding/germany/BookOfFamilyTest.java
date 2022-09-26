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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.wedding.BirthdayTestSupport;
import de.lexasoft.wedding.FamilyName;
import de.lexasoft.wedding.FirstName;
import de.lexasoft.wedding.PartnerShip;
import de.lexasoft.wedding.Person;
import de.lexasoft.wedding.Sex;
import de.lexasoft.wedding.SexEnum;

/**
 * @author nierax
 *
 */
class BookOfFamilyTest {

	private BookOfFamily cut;
	private Person male_28 = Person.of(//
	    FamilyName.of("FamilyName01"), //
	    FirstName.of("FirstName01"), //
	    Sex.of(SexEnum.MALE), //
	    BirthdayTestSupport.birthdayForAge(28));

	private Person female_26 = Person.of(//
	    FamilyName.of("FamilyName02"), //
	    FirstName.of("FirstName02"), //
	    Sex.of(SexEnum.FEMALE), //
	    BirthdayTestSupport.birthdayForAge(26));

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = BookOfFamily.of(male_28, female_26);
	}

	/**
	 * KindOfParttnerShip must be initially set to NOT_MARRIED.
	 */
	@Test
	final void kindOfPartnership_initial_NOT_MARRIED() {
		assertEquals(PartnerShip.NOT_MARRIED, cut.kindOfPartnership());
	}

}
