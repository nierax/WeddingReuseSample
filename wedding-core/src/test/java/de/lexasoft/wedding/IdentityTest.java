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
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.wedding.Identity;

/**
 * @author nierax
 *
 */
class IdentityTest {

	private static final String VALID_UUID = "067e6162-3b6f-4ae2-a171-2470b63dff00";

	private Identity cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		this.cut = Identity.of(VALID_UUID);
	}

	/**
	 * Checks how a Identity is created.
	 */
	@Test
	final void testGenerateId() {
		assertEquals(VALID_UUID, cut.value().toString());
		cut = Identity.of();
		System.out.println("id1: " + cut.value());
		Identity cut2 = Identity.of();
		System.out.println("id2: " + cut2.value());
		assertNotEquals(cut2.value(), cut.value());
	}

	@Test
	final void testEquals() {
		assertTrue(cut.equals(cut));
		// Other object but same id should also be equal.
		assertTrue(cut.equals(Identity.of(VALID_UUID)));
	}

}
