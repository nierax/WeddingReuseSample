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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.wedding.BirthdayTestSupport;
import de.lexasoft.wedding.FamilyName;
import de.lexasoft.wedding.FirstName;
import de.lexasoft.wedding.Person;
import de.lexasoft.wedding.Result;
import de.lexasoft.wedding.Sex;
import de.lexasoft.wedding.SexEnum;
import de.lexasoft.wedding.message.MessageSeverity;

/**
 * @author nierax
 *
 */
class USAPersonFactoryMISSISSIPPITest {

	private USAPersonFactory cut;

	private Person male_30_years_old;
	private Person female_21_years_old;
	private Person male_20_years_old;
	private Person female_19_years_old;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = USAPersonFactory.of(USAState.MISSISSIPPI);
		male_30_years_old = cut.createPerson(//
		    FamilyName.of("Family1"), //
		    FirstName.of("First1"), //
		    Sex.of(SexEnum.MALE), //
		    BirthdayTestSupport.birthdayForAge(30));

		female_21_years_old = cut.createPerson(//
		    FamilyName.of("Family2"), //
		    FirstName.of("First2"), //
		    Sex.of(SexEnum.FEMALE), //
		    BirthdayTestSupport.birthdayForAge(21));

		male_20_years_old = cut.createPerson(//
		    FamilyName.of("Family1"), //
		    FirstName.of("First1"), //
		    Sex.of(SexEnum.MALE), //
		    BirthdayTestSupport.birthdayForAge(20));

		female_19_years_old = cut.createPerson(//
		    FamilyName.of("Family2"), //
		    FirstName.of("First2"), //
		    Sex.of(SexEnum.FEMALE), //
		    BirthdayTestSupport.birthdayForAge(19));
	}

	/**
	 * If both persons are at and over 21 years old, they may marry.
	 */
	@Test
	final void both_persons_over_21_years_may_marry() {
		Result<Person> result = male_30_years_old.marries(female_21_years_old);
		assertEquals(MessageSeverity.NONE, result.resultSeverity());
		assertEquals(male_30_years_old, result.value());
	}

	/**
	 * If one person is under 19 years old, they must not marry.
	 */
	@Test
	final void one_person_under_21_years_must_not_marry() {
		Result<Person> result = male_30_years_old.marries(female_19_years_old);
		assertEquals(MessageSeverity.ERROR, result.resultSeverity());
		assertEquals(male_30_years_old, result.value());
	}

	/**
	 * If both persons are under 21 years old, they must not marry.
	 */
	@Test
	final void both_persons_under_21_years_must_not_marry() {
		Result<Person> result = male_20_years_old.marries(female_19_years_old);
		assertEquals(MessageSeverity.ERROR, result.resultSeverity());
		assertEquals(male_20_years_old, result.value());
	}

}
