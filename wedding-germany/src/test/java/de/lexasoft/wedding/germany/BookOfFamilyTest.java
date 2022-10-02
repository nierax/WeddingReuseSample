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

import java.util.List;

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
import de.lexasoft.wedding.Sex;
import de.lexasoft.wedding.SexEnum;
import de.lexasoft.wedding.message.AtLeast18YearsOldForMarriageRequired;
import de.lexasoft.wedding.message.Message;
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
	private Person male_18;
	private Person female_17;
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

		male_18 = Person.of(//
		    FamilyName.of("FamilyName03"), //
		    FirstName.of("FirstName03"), //
		    Sex.of(SexEnum.MALE), //
		    BirthdayTestSupport.birthdayForAge(18));

		female_17 = Person.of(//
		    FamilyName.of("FamilyName04"), //
		    FirstName.of("FirstName04"), //
		    Sex.of(SexEnum.FEMALE), //
		    BirthdayTestSupport.birthdayForAge(17));

		married_person = new MarriedPerson(//
		    PersonId.of(), //
		    FamilyName.of("Married"), //
		    FirstName.of("Person"), //
		    Sex.of(SexEnum.FEMALE), BirthdayTestSupport.birthdayForAge(23), //
		    Country.GERMANY);

		cut = BookOfFamily.of(male_28, female_26);

	}

	@Test
	final void dateOfCreation_is_today_after_creation() {
		assertEquals(Date.of(), cut.dateOfCreation());
	}

	@Test
	final void people_must_not_marry_when_one_of_them_is_already_married() {
		BookOfFamily myCut = BookOfFamily.of(male_28, married_person);
		List<Message> messages = myCut.allowedToMarry();
		assertEquals(1, messages.size());
		assertEquals(MessageSeverity.ERROR, messages.get(0).severity());
		assertThat(messages, hasItem(new MustNotBeMarriedBeforeError(married_person)));
	}

	@Test
	final void people_must_be_allowed_to_marry_when_everything_is_right() {
		List<Message> messages = cut.allowedToMarry();
		assertEquals(0, messages.size());
	}

	@Test
	final void must_be_allowed_to_marry_at_18_years_old() {
		BookOfFamily myCut = BookOfFamily.of(male_18, female_26);
		List<Message> messages = myCut.allowedToMarry();
		assertEquals(0, messages.size());
	}

	@Test
	final void marriage_not_permitted_when_under_18_years_old() {
		BookOfFamily myCut = BookOfFamily.of(male_18, female_17);
		List<Message> messages = myCut.allowedToMarry();
		assertEquals(1, messages.size());
		assertEquals(MessageSeverity.ERROR, messages.get(0).severity());
		assertThat(messages, hasItem(new AtLeast18YearsOldForMarriageRequired(female_17)));
	}

	@Test
	final void married_and_too_young_all_messages_required() {
		BookOfFamily myCut = BookOfFamily.of(married_person, female_17);
		List<Message> messages = myCut.allowedToMarry();
		assertEquals(2, messages.size());
		assertEquals(MessageSeverity.ERROR, messages.get(0).severity());
		assertEquals(MessageSeverity.ERROR, messages.get(1).severity());
		assertThat(messages, hasItem(new AtLeast18YearsOldForMarriageRequired(female_17)));
		assertThat(messages, hasItem(new MustNotBeMarriedBeforeError(married_person)));
	}

}
