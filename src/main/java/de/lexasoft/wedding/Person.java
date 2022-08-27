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

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Represents a natural person in our system.
 * 
 * @author nierax
 *
 */
public class Person {

	private FamilyName familyName;
	private FirstName firstName;
	private Sex sex;
	private Birthday birthday;

	/**
	 * Must not be instantiated from outside the class.
	 */
	private Person(FamilyName familyName, FirstName firstName, Sex sex, Birthday birthday) {
		this.familyName = familyName;
		this.firstName = firstName;
		this.sex = sex;
		this.birthday = birthday;
	}

	/**
	 * @return the familyName
	 */
	public FamilyName familyName() {
		return familyName;
	}

	/**
	 * @return the firstName
	 */
	public FirstName firstName() {
		return firstName;
	}

	/**
	 * @return the sex
	 */
	public Sex sex() {
		return sex;
	}

	/**
	 * @return the birthday
	 */
	public Birthday birthday() {
		return birthday;
	}

	public long ageInYearsToday() {
		return ageInYearsAtDate(LocalDate.now());
	}

	public long ageInYearsAtDate(LocalDate date) {
		return ChronoUnit.YEARS.between(birthday().value(), date);
	}

	public final static Person of(FamilyName familyName, FirstName firstName, Sex sex, Birthday birthday) {
		return new Person(familyName, firstName, sex, birthday);
	}

}