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
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.lexasoft.wedding.ValueObject;

/**
 * @author nierax
 *
 */
class ValueObjectTest {

	@SuppressWarnings("serial")
	class CUT extends ValueObject<String> {

		protected CUT(String value) {
			super(value);
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	final void nullNotAcceptedAsValue() {
		assertThrows(IllegalArgumentException.class, //
		    () -> new CUT(null));
	}

	@ParameterizedTest
	@ValueSource(strings = { "Me", "too", "yes", "no", "i don't know why, but it's ok.", "" })
	final void acceptStringsAsValues(String value) {
		CUT cut = new CUT(value);
		assertEquals(value, cut.value());
	}

}
