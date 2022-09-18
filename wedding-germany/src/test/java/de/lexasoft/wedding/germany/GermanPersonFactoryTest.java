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

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.wedding.Birthday;
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
class GermanPersonFactoryTest {

	private GermanPersonFactory cut;

	private Person male_30_years_old;
	private Person female_28_years_old;
	private Person male_17_years_old;
	private Person female_16_years_old;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = GermanPersonFactory.of();
		male_30_years_old = cut.createPerson(//
		    FamilyName.of("Family1"), //
		    FirstName.of("First1"), //
		    Sex.of(SexEnum.MALE), //
		    birthdayForAge(30));

		female_28_years_old = cut.createPerson(//
		    FamilyName.of("Family2"), //
		    FirstName.of("First2"), //
		    Sex.of(SexEnum.FEMALE), //
		    birthdayForAge(28));

		male_17_years_old = cut.createPerson(//
		    FamilyName.of("Family1"), //
		    FirstName.of("First1"), //
		    Sex.of(SexEnum.MALE), //
		    birthdayForAge(17));

		female_16_years_old = cut.createPerson(//
		    FamilyName.of("Family2"), //
		    FirstName.of("First2"), //
		    Sex.of(SexEnum.FEMALE), //
		    birthdayForAge(16));
	}

	private Birthday birthdayForAge(long age) {
		return Birthday.of(LocalDate.now().minusYears(age));
	}

	/**
	 * If both persons are over 18 years old, they may marry.
	 */
	@Test
	final void both_persons_over_18_years_may_marry() {
		Result<Person> result = male_30_years_old.marries(female_28_years_old);
		assertEquals(MessageSeverity.NONE, result.resultSeverity());
		assertEquals(male_30_years_old, result.value());
	}

	/**
	 * If one person is under 18 years old, they must not marry.
	 */
	@Test
	final void one_person_under_18_years_must_not_marry() {
		Result<Person> result = male_30_years_old.marries(female_16_years_old);
		assertEquals(MessageSeverity.ERROR, result.resultSeverity());
		assertEquals(male_30_years_old, result.value());
	}

	/**
	 * If both persons are under 18 years old, they must not marry.
	 */
	@Test
	final void both_persons_under_18_years_must_not_marry() {
		Result<Person> result = male_17_years_old.marries(female_16_years_old);
		assertEquals(MessageSeverity.ERROR, result.resultSeverity());
		assertEquals(male_17_years_old, result.value());
	}

}
