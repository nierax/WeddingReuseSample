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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.wedding.BirthdayTestSupport;
import de.lexasoft.wedding.Country;
import de.lexasoft.wedding.Date;
import de.lexasoft.wedding.FamilyName;
import de.lexasoft.wedding.FirstName;
import de.lexasoft.wedding.PartnerShip;
import de.lexasoft.wedding.Person;
import de.lexasoft.wedding.PersonTestSupport;
import de.lexasoft.wedding.Result;
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
class USAFamilyTest {

	private USAFamily cut_other;
	private Person male_28_not_married;
	private Person male_31_married;
	private Person female_26_not_married;
	private Person female_20_not_married;
	private Person female_18_not_married;
	private Person female_16_not_married;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		male_28_not_married = Person.of(//
		    FamilyName.of("William"), //
		    FirstName.of("John"), //
		    Sex.of(SexEnum.MALE), //
		    BirthdayTestSupport.birthdayForAge(28), //
		    Country.UNITED_STATES);
		male_31_married = PersonTestSupport.createMarriedPerson(//
		    FamilyName.of("Smith"), //
		    FirstName.of("Jake"), //
		    Sex.of(SexEnum.MALE), //
		    31, //
		    Country.UNITED_STATES);
		female_26_not_married = Person.of(//
		    FamilyName.of("Miller"), //
		    FirstName.of("Elizabeth"), //
		    Sex.of(SexEnum.FEMALE), //
		    BirthdayTestSupport.birthdayForAge(26), //
		    Country.UNITED_STATES);
		female_20_not_married = Person.of(//
		    FamilyName.of("Snyder"), //
		    FirstName.of("Malena"), //
		    Sex.of(SexEnum.FEMALE), //
		    BirthdayTestSupport.birthdayForAge(20), //
		    Country.UNITED_STATES);
		female_18_not_married = Person.of(//
		    FamilyName.of("Snyder"), //
		    FirstName.of("Malena"), //
		    Sex.of(SexEnum.FEMALE), //
		    BirthdayTestSupport.birthdayForAge(18), //
		    Country.UNITED_STATES);
		female_16_not_married = Person.of(//
		    FamilyName.of("Truman"), //
		    FirstName.of("Helena"), //
		    Sex.of(SexEnum.FEMALE), //
		    BirthdayTestSupport.birthdayForAge(16), //
		    Country.UNITED_STATES);
		cut_other = USAFamily.of(male_28_not_married, female_26_not_married, USAState.OTHER);
	}

	/**
	 * Check, whether all attributes are correctly initialized, where necessary to
	 * check.
	 */
	@Test
	final void check_after_creation() {
		assertEquals(PartnerShip.NOT_MARRIED, cut_other.partnerShip());
		assertEquals(Date.NONE, cut_other.dateOfWedding());
	}

	@Test
	final void check_marriage_allowed_male_28_and_female_26() {
		List<Message> messages = cut_other.allowedToMarry();
		assertEquals(0, messages.size());
	}

	@Test
	final void may_not_marry_with_one_partner_under_18() {
		USAFamily cut = USAFamily.of(male_28_not_married, female_16_not_married, USAState.OTHER);
		List<Message> messages = cut.allowedToMarry();
		assertEquals(1, messages.size());
		assertThat(messages, hasItem(new AtLeast18YearsOldForMarriageRequired(female_16_not_married)));
	}

	@Test
	final void may_not_marry_with_one_partner_under_19_in_nebraska() {
		USAFamily cut = USAFamily.of(male_28_not_married, female_18_not_married, USAState.NEBRASKA);
		List<Message> messages = cut.allowedToMarry();
		assertEquals(1, messages.size());
		assertThat(messages, hasItem(new AtLeast18YearsOldForMarriageRequired(female_18_not_married)));
	}

	@Test
	final void may_not_marry_with_one_partner_under_21_in_mississippi() {
		USAFamily cut = USAFamily.of(male_28_not_married, female_20_not_married, USAState.MISSISSIPPI);
		List<Message> messages = cut.allowedToMarry();
		assertEquals(1, messages.size());
		assertThat(messages, hasItem(new AtLeast18YearsOldForMarriageRequired(female_20_not_married)));
	}

	@Test
	final void may_not_marry_with_one_partner_already_married() {
		USAFamily cut = USAFamily.of(male_31_married, female_26_not_married, USAState.OTHER);
		List<Message> messages = cut.allowedToMarry();
		assertEquals(1, messages.size());
		assertThat(messages, hasItem(new MustNotBeMarriedBeforeError(male_31_married)));
	}

	@Test
	final void wedding_ok() {
		USAFamily cut = USAFamily.of(male_28_not_married, female_26_not_married, USAState.OTHER);
		Result<USAFamily> result = cut.marry();
		assertEquals(MessageSeverity.NONE, result.resultSeverity());
		assertEquals(PartnerShip.MARRIED, result.value().partnerShip());
		assertEquals(PartnerShip.MARRIED, result.value().partner1().partnerShip());
		assertEquals(PartnerShip.MARRIED, result.value().partner2().partnerShip());
		assertEquals(result.value().familyId(), result.value().partner1().family());
		assertEquals(result.value().familyId(), result.value().partner2().family());
		assertEquals(Date.TODAY, result.value().dateOfWedding());
	}

	@Test
	final void wedding_not_ok() {
		USAFamily cut = USAFamily.of(male_28_not_married, female_16_not_married, USAState.OTHER);
		Result<USAFamily> result = cut.marry();
		assertEquals(MessageSeverity.ERROR, result.resultSeverity());
		assertEquals(PartnerShip.NOT_MARRIED, result.value().partnerShip());
		assertEquals(PartnerShip.NOT_MARRIED, result.value().partner1().partnerShip());
		assertEquals(PartnerShip.NOT_MARRIED, result.value().partner2().partnerShip());
		assertEquals(Date.NONE, result.value().dateOfWedding());
		assertThat(result.messages(), hasItem(new AtLeast18YearsOldForMarriageRequired(female_16_not_married)));
	}

}
