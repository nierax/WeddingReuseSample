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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.lexasoft.wedding.message.AtLeast18YearsOldForMarriageRequired;
import de.lexasoft.wedding.message.Message;
import de.lexasoft.wedding.message.MessageSeverity;

/**
 * @author nierax
 *
 */
class ValidateAgeForMarriageTest {

	private ValidateAgeForMarriage cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = ValidateAgeForMarriage.of(18);
	}

	private static Birthday birthdayForAge(long age) {
		return Birthday.of(LocalDate.now().minusYears(age));
	}

	private static final Stream<Arguments> marriage_allwed_from_18_years() {
		return Stream.of(//
		    // both > 18
		    Arguments.of(//
		        Person.of(FamilyName.of("fam1"), FirstName.of("first1"), Sex.of(SexEnum.MALE), birthdayForAge(20)),
		        Person.of(FamilyName.of("fam2"), FirstName.of("first2"), Sex.of(SexEnum.FEMALE), birthdayForAge(18)), //
		        0), //
		    // first person < 18
		    Arguments.of(//
		        Person.of(FamilyName.of("fam1"), FirstName.of("first1"), Sex.of(SexEnum.MALE), birthdayForAge(8)),
		        Person.of(FamilyName.of("fam2"), FirstName.of("first2"), Sex.of(SexEnum.FEMALE), birthdayForAge(18)), //
		        1),
		    // both persons < 18
		    Arguments.of(//
		        Person.of(FamilyName.of("fam1"), FirstName.of("first1"), Sex.of(SexEnum.MALE), birthdayForAge(8)),
		        Person.of(FamilyName.of("fam2"), FirstName.of("first2"), Sex.of(SexEnum.FEMALE), birthdayForAge(17)), //
		        2),
		    // second person > 18
		    Arguments.of(//
		        Person.of(FamilyName.of("fam1"), FirstName.of("first1"), Sex.of(SexEnum.MALE), birthdayForAge(18)),
		        Person.of(FamilyName.of("fam2"), FirstName.of("first2"), Sex.of(SexEnum.FEMALE), birthdayForAge(17)), //
		        1));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.wedding.ValidateAgeForMarriage#marriageAllowed(de.lexasoft.wedding.Person, de.lexasoft.wedding.Person)}.
	 */
	@ParameterizedTest
	@MethodSource
	final void marriage_allwed_from_18_years(Person person1, Person person2, int expectedNrOfEntriesInList) {
		List<Message> messages = cut.marriageAllowed(person1, person2);
		assertEquals(expectedNrOfEntriesInList, messages.size());
		if (expectedNrOfEntriesInList > 0) {
			assertEquals(MessageSeverity.ERROR, messages.get(0).severity());
			assertTrue(messages.get(0) instanceof AtLeast18YearsOldForMarriageRequired);
		}
	}

}
