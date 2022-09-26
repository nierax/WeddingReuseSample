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
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * 
 * @author nierax
 *
 */
class DateTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	private static final Stream<Arguments> testEquals() {
		return Stream.of(//
		    Arguments.of(Date.of(1996, 11, 27), Date.of(1996, 11, 27), true),
		    Arguments.of(Date.of(1996, 11, 27), Date.of(1996, 11, 28), false),
		    Arguments.of(Date.of(1996, 11, 27), Date.of(1996, 11, 26), false));
	}

	/**
	 * Make sure the equals() method also works for Date objects.
	 * 
	 * @param date1
	 * @param date2
	 * @param expected
	 */
	@ParameterizedTest
	@MethodSource
	final void testEquals(Date date1, Date date2, boolean expected) {
		if (expected) {
			assertEquals(date1, date2);
		} else {
			assertNotEquals(date1, date2);
		}
	}

}
