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

import java.util.UUID;

/**
 * Representing an id of a family.
 * 
 * @author nierax
 *
 */
@SuppressWarnings("serial")
public class FamilyId extends ValueObject<UUID> {

	public static final FamilyId NONE = FamilyId.of("00000000-0000-0000-0000-000000000000");

	/**
	 * @param value
	 */
	private FamilyId(UUID value) {
		super(value);
	}

	/**
	 * Create an identity object with the given id.
	 * 
	 * @param value
	 * @return
	 */
	public final static FamilyId of(String value) {
		return new FamilyId(UUID.fromString(value));
	}

	/**
	 * Creates an identity object with a random id.
	 * 
	 * @return
	 */
	public final static FamilyId of() {
		return new FamilyId(UUID.randomUUID());
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FamilyId)) {
			return false;
		}
		FamilyId other = (FamilyId) obj;
		return this.value().equals(other.value());
	}

}
