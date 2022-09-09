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

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
class PolishPersonTest {

	private static PolishPerson male01;
	private static PolishPerson male02;
	private static PolishPerson female01;
	private static PolishPerson female02;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUp() throws Exception {
		male01 = PolishPerson.of(//
		    FamilyName.of("Nowak"), //
		    FirstName.of("Jakub"), //
		    Sex.of(SexEnum.MALE), //
		    Birthday.of(1990, 9, 11));
		male02 = PolishPerson.of(//
		    FamilyName.of("Kowalski"), //
		    FirstName.of("Antoni"), //
		    Sex.of(SexEnum.MALE), //
		    Birthday.of(1993, 8, 11));
		female01 = PolishPerson.of(//
		    FamilyName.of("Lewandowska"), //
		    FirstName.of("Zuzanna"), //
		    Sex.of(SexEnum.FEMALE), //
		    Birthday.of(1990, 9, 11));
		female02 = PolishPerson.of(//
		    FamilyName.of("Krawczyk"), //
		    FirstName.of("Lena"), //
		    Sex.of(SexEnum.FEMALE), //
		    Birthday.of(1993, 8, 11));
	}

	private static Stream<Arguments> no_marriage_between_persons_of_same_sex() {
		return Stream.of(//
		    Arguments.of(male01, male02), //
		    Arguments.of(female01, female02));
	}

	/**
	 * Make sure, people of same sex are not allowed to marry.
	 * 
	 * @param person1
	 * @param person2
	 */
	@ParameterizedTest
	@MethodSource
	final void no_marriage_between_persons_of_same_sex(Person person1, Person person2) {
		Result<Person> result = person1.marries(person2);
		assertThat(result.messages(), hasItem(new NoWeddingWithSameSexAllwoed()));
		assertEquals(MessageSeverity.ERROR, result.resultSeverity());
	}

	private static Stream<Arguments> marriage_between_persons_of_different_sex() {
		return Stream.of(//
		    Arguments.of(female01, male02), //
		    Arguments.of(female02, male01));
	}

	/**
	 * Make sure, people of same sex are not allowed to marry.
	 * 
	 * @param person1
	 * @param person2
	 */
	@ParameterizedTest
	@MethodSource
	final void marriage_between_persons_of_different_sex(Person person1, Person person2) {
		Result<Person> result = person1.marries(person2);
		assertEquals(MessageSeverity.NONE, result.resultSeverity());
	}

}
