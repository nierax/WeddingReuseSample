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

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author nierax
 *
 */
class SexTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	private static Stream<Arguments> testEquals() {
		return Stream.of(//
		    Arguments.of(Sex.of(SexEnum.MALE), Sex.of(SexEnum.MALE), true),
		    Arguments.of(Sex.of(SexEnum.FEMALE), Sex.of(SexEnum.FEMALE), true),
		    Arguments.of(Sex.of(SexEnum.DIVERS), Sex.of(SexEnum.DIVERS), true),
		    Arguments.of(Sex.of(SexEnum.MALE), Sex.of(SexEnum.FEMALE), false),
		    Arguments.of(Sex.of(SexEnum.FEMALE), Sex.of(SexEnum.MALE), false),
		    Arguments.of(Sex.of(SexEnum.DIVERS), Sex.of(SexEnum.MALE), false),
		    Arguments.of(Sex.of(SexEnum.MALE), Sex.of(SexEnum.DIVERS), false),
		    Arguments.of(Sex.of(SexEnum.DIVERS), Sex.of(SexEnum.FEMALE), false),
		    Arguments.of(Sex.of(SexEnum.FEMALE), Sex.of(SexEnum.DIVERS), false));
	}

	@ParameterizedTest
	@MethodSource
	final void testEquals(Sex sex1, Sex sex2, boolean expected) {
		assertEquals(expected, sex1.equals(sex2));
	}

}
