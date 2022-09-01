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
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a natural person in our system.
 * 
 * @author nierax
 *
 */
public class Person {

	private final Identity id;
	private FamilyName familyName;
	private FirstName firstName;
	private Sex sex;
	private Birthday birthday;
	private Identity marriedWithID;

	@SuppressWarnings("serial")
	class IdMustNotBeNullException extends RuntimeException {

		/**
		 * 
		 */
		private IdMustNotBeNullException() {
			super("The Id must not be null.");
		}
	}

	/**
	 * Must not be instantiated from outside the class.
	 */
	private Person(Identity id, FamilyName familyName, FirstName firstName, Sex sex, Birthday birthday) {
		this.id = id;
		this.familyName = familyName;
		this.firstName = firstName;
		this.sex = sex;
		this.birthday = birthday;
	}

	public Identity id() {
		return id;
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

	/**
	 * Returns the age of the person in years today with respect to the birthday.
	 * 
	 * @return The age in years.
	 */
	public long ageInYearsToday() {
		return ageInYearsAtDate(LocalDate.now());
	}

	/**
	 * 
	 * @return The person, this person is married with, null otherwise.
	 */
	public Identity marriedWithID() {
		return marriedWithID;
	}

	private Identity marriedWithID(Identity marriedWithID) {
		if (marriedWithID == null) {
			throw new IdMustNotBeNullException();
		}
		return this.marriedWithID = marriedWithID;
	}

	/**
	 * 
	 * @return True, if married. False otherwise.
	 */
	public boolean isMarried() {
		return this.marriedWithID != null;
	}

	/**
	 * Marriage between two person.
	 * 
	 * @param partner
	 * @return Result with this person.
	 */
	public Result<Person> marries(Person partner) {
		List<Message> messages = new ArrayList<>();
		if (isSamePerson(partner)) {
			messages.add(new NotAllowedToMarryMyselfError());
		} else {
			this.marriedWithID(partner.id());
			partner.marriedWithID(this.id());
		}
		return Result.of(this, messages);
	}

	/**
	 * Returns the age of the person in years to a given date with respect to the
	 * birthday.
	 * 
	 * @param date The date to compare with
	 * @return The age in years
	 */
	public long ageInYearsAtDate(LocalDate date) {
		return ChronoUnit.YEARS.between(birthday().value(), date);
	}

	public boolean isSamePerson(Person other) {
		return (this.equals(other) || this.id.equals(other.id));
	}

	/**
	 * Creates a new person object and generates an id for it.
	 * 
	 * @param familyName
	 * @param firstName
	 * @param sex
	 * @param birthday
	 * @return
	 */
	public final static Person of(FamilyName familyName, FirstName firstName, Sex sex, Birthday birthday) {
		return new Person(Identity.of(), familyName, firstName, sex, birthday);
	}

	/**
	 * Creates a new person object with the given id.
	 * 
	 * @param id
	 * @param familyName
	 * @param firstName
	 * @param sex
	 * @param birthday
	 * @return
	 */
	public final static Person of(Identity id, FamilyName familyName, FirstName firstName, Sex sex, Birthday birthday) {
		return new Person(id, familyName, firstName, sex, birthday);
	}

}
