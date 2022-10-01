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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.wedding.BirthdayTestSupport;
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
class PolishFamilyTest {

	private Person male_28_not_married;
	private Person female_26_not_married;
	private Person female_24_not_married;
	private Person male_31_married;
	private Person female_16_not_married;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		male_28_not_married = Person.of(//
		    FamilyName.of("Nowak"), //
		    FirstName.of("Jakub"), //
		    Sex.of(SexEnum.MALE), //
		    BirthdayTestSupport.birthdayForAge(28));
		male_31_married = PersonTestSupport.createMarriedPerson(//
		    FamilyName.of("Kowalski"), //
		    FirstName.of("Antoni"), //
		    Sex.of(SexEnum.MALE), //
		    31);
		female_26_not_married = Person.of(//
		    FamilyName.of("Lewandowska"), //
		    FirstName.of("Zuzanna"), //
		    Sex.of(SexEnum.FEMALE), //
		    BirthdayTestSupport.birthdayForAge(26));
		female_24_not_married = Person.of(//
		    FamilyName.of("Michalska"), //
		    FirstName.of("Irena"), //
		    Sex.of(SexEnum.FEMALE), //
		    BirthdayTestSupport.birthdayForAge(24));
		female_16_not_married = Person.of(//
		    FamilyName.of("Krawczyk"), //
		    FirstName.of("Lena"), //
		    Sex.of(SexEnum.FEMALE), //
		    BirthdayTestSupport.birthdayForAge(16));
	}

	@Test
	final void partnership_not_married_after_creation() {
		PolishFamily cut = PolishFamily.of(male_28_not_married, female_26_not_married);
		assertEquals(PartnerShip.NOT_MARRIED, cut.partnerShip());
	}

	@Test
	final void wedding_allowed_for_male_28_and_female_26() {
		PolishFamily cut = PolishFamily.of(male_28_not_married, female_26_not_married);
		List<Message> messages = cut.allowedToMarry();
		assertEquals(0, messages.size());
	}

	@Test
	final void wedding_not_allowed_for_male_28_and_female_16() {
		PolishFamily cut = PolishFamily.of(male_28_not_married, female_16_not_married);
		List<Message> messages = cut.allowedToMarry();
		assertEquals(1, messages.size());
		assertThat(messages, hasItem(new AtLeast18YearsOldForMarriageRequired(female_16_not_married)));
	}

	@Test
	final void wedding_not_allowed_for_male_31_married_and_female_26() {
		PolishFamily cut = PolishFamily.of(male_31_married, female_26_not_married);
		List<Message> messages = cut.allowedToMarry();
		assertEquals(1, messages.size());
		assertThat(messages, hasItem(new MustNotBeMarriedBeforeError(male_31_married)));
	}

	@Test
	final void wedding_not_allowed_for_female_28_not_married_and_female_24_not_married() {
		PolishFamily cut = PolishFamily.of(female_26_not_married, female_24_not_married);
		List<Message> messages = cut.allowedToMarry();
		assertEquals(1, messages.size());
		assertThat(messages, hasItem(new NoWeddingWithSameSexAllowed()));
	}

	@Test
	final void wedding_ok() {
		PolishFamily cut = PolishFamily.of(male_28_not_married, female_26_not_married);
		Result<PolishFamily> result = cut.marry();
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
		PolishFamily cut = PolishFamily.of(male_28_not_married, female_16_not_married);
		Result<PolishFamily> result = cut.marry();
		assertEquals(MessageSeverity.ERROR, result.resultSeverity());
		assertEquals(PartnerShip.NOT_MARRIED, result.value().partnerShip());
		assertEquals(PartnerShip.NOT_MARRIED, result.value().partner1().partnerShip());
		assertEquals(PartnerShip.NOT_MARRIED, result.value().partner2().partnerShip());
		assertEquals(Date.NONE, result.value().dateOfWedding());
		assertThat(result.messages(), hasItem(new AtLeast18YearsOldForMarriageRequired(female_16_not_married)));
	}

}
