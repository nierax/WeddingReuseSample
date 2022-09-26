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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.wedding.Birthday;
import de.lexasoft.wedding.BirthdayTestSupport;
import de.lexasoft.wedding.Country;
import de.lexasoft.wedding.Date;
import de.lexasoft.wedding.FamilyName;
import de.lexasoft.wedding.FirstName;
import de.lexasoft.wedding.PartnerShip;
import de.lexasoft.wedding.Person;
import de.lexasoft.wedding.PersonId;
import de.lexasoft.wedding.Result;
import de.lexasoft.wedding.Sex;
import de.lexasoft.wedding.SexEnum;
import de.lexasoft.wedding.message.MessageSeverity;
import de.lexasoft.wedding.message.MustNotBeMarriedBeforeError;

/**
 * @author nierax
 *
 */
class BookOfFamilyTest {

	private BookOfFamily cut;
	private Person male_28;
	private Person female_26;
	private Person old_partner_female_23;
	private MarriedPerson married_person;

	private class MarriedPerson extends Person {

		protected MarriedPerson(PersonId id, FamilyName familyName, FirstName firstName, Sex sex, Birthday birthday,
		    Country country) {
			super(id, familyName, firstName, sex, birthday, country);
		}

		@Override
		public PartnerShip partnerShip() {
			return PartnerShip.MARRIED;
		}

	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		male_28 = Person.of(//
		    FamilyName.of("FamilyName01"), //
		    FirstName.of("FirstName01"), //
		    Sex.of(SexEnum.MALE), //
		    BirthdayTestSupport.birthdayForAge(28));

		female_26 = Person.of(//
		    FamilyName.of("FamilyName02"), //
		    FirstName.of("FirstName02"), //
		    Sex.of(SexEnum.FEMALE), //
		    BirthdayTestSupport.birthdayForAge(26));

		old_partner_female_23 = Person.of(//
		    FamilyName.of("FamilyName02"), //
		    FirstName.of("FirstName02"), //
		    Sex.of(SexEnum.FEMALE), //
		    BirthdayTestSupport.birthdayForAge(23));

		married_person = new MarriedPerson(//
		    PersonId.of(), //
		    FamilyName.of("Married"), //
		    FirstName.of("Person"), //
		    Sex.of(SexEnum.FEMALE), BirthdayTestSupport.birthdayForAge(23), //
		    Country.GERMANY);

		cut = BookOfFamily.of(male_28, female_26);

	}

	/**
	 * KindOfParttnerShip must be initially set to NOT_MARRIED.
	 */
	@Test
	final void kindOfPartnership_initial_NOT_MARRIED() {
		assertEquals(PartnerShip.NOT_MARRIED, cut.kindOfPartnership());
	}

	@Test
	final void dateOfCreation_is_today_after_creation() {
		assertEquals(Date.of(), cut.dateOfCreation());
	}

	@Test
	final void dateOfWedding_is_NONE_after_creation() {
		assertEquals(Date.NONE.value(), cut.dateOfWedding().value());
	}

	@Test
	final void people_must_not_marry_when_one_of_them_is_already_married() {
		BookOfFamily myCut = BookOfFamily.of(male_28, married_person);
		Result<Boolean> result = myCut.allowedToMarry();
		assertFalse(result.value());
		assertEquals(MessageSeverity.ERROR, result.resultSeverity());
		assertThat(result.messages(), hasItem(new MustNotBeMarriedBeforeError(married_person)));
	}

	@Test
	final void people_must_be_allowed_to_marry_when_everything_is_right() {
		Result<Boolean> result = cut.allowedToMarry();
		assertTrue(result.value());
		assertEquals(MessageSeverity.NONE, result.resultSeverity());
		assertEquals(0, result.messages().size());
	}

}
