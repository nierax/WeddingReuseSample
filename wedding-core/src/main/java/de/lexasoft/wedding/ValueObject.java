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

import java.io.Serializable;

/**
 * Base class for any value object.
 * <p>
 * Value objects are immutable by design.
 * 
 * @author nierax
 *
 */
@SuppressWarnings("serial")
public abstract class ValueObject<T> implements Serializable {

	private final T value;

	/**
	 * Get the value.
	 * 
	 * @return The value of the object.
	 */
	public T value() {
		return this.value;
	}

	private T assureValueNotNull(T value) {
		if (value == null) {
			throw new ValueMustNotBeNullExcpetion("The value must not be null.");
		}
		return value;
	}

	/**
	 * Must not be instantiated, if not extended.
	 */
	protected ValueObject(T value) {
		this.value = assureValueNotNull(value);
	}

	@Override
	public String toString() {
		return value.toString();
	}

}

@SuppressWarnings("serial")
class ValueMustNotBeNullExcpetion extends IllegalArgumentException {

	/**
	 * @param message
	 * @param cause
	 */
	public ValueMustNotBeNullExcpetion(String message) {
		super(message);
	}

}
