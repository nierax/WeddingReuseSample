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

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.wedding.message.Message;
import de.lexasoft.wedding.message.MessageSeverity;
import de.lexasoft.wedding.message.MessageText;

/**
 * @author nierax
 *
 */
class AbstractFamilyTest {

	/**
	 * Class t be used in this test.
	 */
	class CUTFamily extends AbstractFamily {

		protected CUTFamily(ValidateMarriage validations, Person partner1, Person partner2) {
			super(FamilyId.of(), validations, partner1, partner2);
		}

	}

	class ErroneousMessage extends Message {

		ErroneousMessage() {
			super(MessageText.of("AnyError"), MessageSeverity.ERROR);
		}

	}

	private Person male_28_not_married;
	private Person female_26_not_married;
	private List<Message> empty_messages;
	private List<Message> erroneous_messages;

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
		female_26_not_married = Person.of(//
		    FamilyName.of("Lewandowska"), //
		    FirstName.of("Zuzanna"), //
		    Sex.of(SexEnum.FEMALE), //
		    BirthdayTestSupport.birthdayForAge(26));

		empty_messages = new ArrayList<>();
		erroneous_messages = new ArrayList<>();
		erroneous_messages.add(new ErroneousMessage());
	}

	@Test
	final void wedding_ok() {
		AbstractFamily cut = new CUTFamily(//
		    (p1, p2) -> empty_messages, // empty list, no validations
		    male_28_not_married, //
		    female_26_not_married);
		Result<AbstractFamily> result = cut.marry();
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
		AbstractFamily cut = new CUTFamily(//
		    (p1, p2) -> erroneous_messages, // list with errors
		    male_28_not_married, //
		    female_26_not_married);
		Result<AbstractFamily> result = cut.marry();
		assertEquals(MessageSeverity.ERROR, result.resultSeverity());
		assertEquals(PartnerShip.NOT_MARRIED, result.value().partnerShip());
		assertEquals(PartnerShip.NOT_MARRIED, result.value().partner1().partnerShip());
		assertEquals(PartnerShip.NOT_MARRIED, result.value().partner2().partnerShip());
		assertEquals(Date.NONE, result.value().dateOfWedding());
		assertThat(result.messages(), hasItem(new ErroneousMessage()));
	}

}