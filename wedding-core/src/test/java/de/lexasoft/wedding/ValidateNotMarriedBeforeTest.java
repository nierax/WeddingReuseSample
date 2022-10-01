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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.wedding.message.Message;
import de.lexasoft.wedding.message.MustNotBeMarriedBeforeError;

/**
 * @author nierax
 *
 */
class ValidateNotMarriedBeforeTest {

	private Person male_28;

	private Person female_19;

	private Person married_male_28;

	private Person married_female_31;

	private ValidateNotMarriedBefore cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		male_28 = Person.of(//
		    FamilyName.of("Miller"), //
		    FirstName.of("Jack"), //
		    Sex.of(SexEnum.MALE), //
		    BirthdayTestSupport.birthdayForAge(28));

		female_19 = Person.of(//
		    FamilyName.of("Smith"), //
		    FirstName.of("Cathlyn"), //
		    Sex.of(SexEnum.FEMALE), //
		    BirthdayTestSupport.birthdayForAge(19));

		married_male_28 = PersonTestSupport.createMarriedPerson(//
		    FamilyName.of("Miller"), //
		    FirstName.of("John"), //
		    Sex.of(SexEnum.MALE), //
		    28);

		married_female_31 = PersonTestSupport.createMarriedPerson(//
		    FamilyName.of("Smith"), //
		    FirstName.of("Lea"), //
		    Sex.of(SexEnum.FEMALE), //
		    31);

		cut = ValidateNotMarriedBefore.of();
	}

	/**
	 * Marriage should be allowed, when both partners are not married.
	 */
	@Test
	final void marriage_allowed_with_both_partners_not_married() {
		List<Message> messages = cut.marriageAllowed(male_28, female_19);
		assertEquals(0, messages.size());
	}

	@Test
	final void marriage_not_allowed_with_one_partner_being_married() {
		List<Message> messages = cut.marriageAllowed(married_male_28, female_19);
		assertEquals(1, messages.size());
		assertThat(messages, hasItem(new MustNotBeMarriedBeforeError(married_male_28)));
	}

	@Test
	final void marriage_not_allowed_with_both_partner_being_married() {
		List<Message> messages = cut.marriageAllowed(married_male_28, married_female_31);
		assertEquals(2, messages.size());
		assertThat(messages, hasItem(new MustNotBeMarriedBeforeError(married_male_28)));
		assertThat(messages, hasItem(new MustNotBeMarriedBeforeError(married_female_31)));
	}

}
