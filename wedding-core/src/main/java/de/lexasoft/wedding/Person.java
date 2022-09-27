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

import de.lexasoft.wedding.message.Message;
import de.lexasoft.wedding.message.MessageSeverity;
import de.lexasoft.wedding.message.NotAllowedToMarryMyselfError;

/**
 * Represents a natural person in our system.
 * 
 * @author nierax
 *
 */
public class Person {

	private final PersonId id;
	private FamilyName familyName;
	private FirstName firstName;
	private Sex sex;
	private Birthday birthday;
	@Deprecated
	private PersonId marriedWithID;
	private FamilyId family;
	private PartnerShip partnerShip;
	private Country country;
	private final ValidateMarriageRunner validations;

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
	protected Person(PersonId id, FamilyName familyName, FirstName firstName, Sex sex, Birthday birthday,
	    Country country) {
		this.id = id;
		this.familyName = familyName;
		this.firstName = firstName;
		this.sex = sex;
		this.birthday = birthday;
		this.country = country;
		this.validations = createValidations();
		this.partnerShip = PartnerShip.NOT_MARRIED;
		this.family = FamilyId.NONE;
	}

	private ValidateMarriageRunner createValidations() {
		ValidateMarriageRunner runner = new ValidateMarriageRunner();
		runner.addValidation((person1, person2) -> {
			List<Message> messages = new ArrayList<>();
			if (isSamePerson(person2)) {
				messages.add(new NotAllowedToMarryMyselfError());
			}
			return messages;
		});

		return runner;
	}

	public PersonId id() {
		return id;
	}

	/**
	 * Adds a validation to teh list of validations. To be used in factory only.
	 * 
	 * @param validation
	 * @return
	 */
	public Person addValidation(ValidateMarriage validation) {
		this.validations.addValidation(validation);
		return this;
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

	public Country country() {
		return this.country;
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
	public PersonId marriedWithID() {
		return marriedWithID;
	}

	private PersonId marriedWithID(PersonId marriedWithID) {
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
	 * <p>
	 * In case marriage is not possible, the marriage will not be done and the
	 * reasons will be delivered inside the message list. In this case the result
	 * severity is {@link MessageSeverity#ERROR}.
	 * 
	 * If everything was ok, the {@link MessageSeverity#NONE} is used.
	 * 
	 * @param partner
	 * @return Result with this person.
	 */
	@Deprecated
	public Result<Person> marries(Person partner) {
		Result<Person> result = Result.of(this, validations().marriageAllowed(this, partner));
		if (!result.isErroneous()) {
			this.marriedWithID(partner.id());
			partner.marriedWithID(this.id());
		}
		return result;
	}

	public Person marries(FamilyId family) {
		this.family = family;
		this.partnerShip = PartnerShip.MARRIED;
		return this;
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
	public static Person of(FamilyName familyName, FirstName firstName, Sex sex, Birthday birthday) {
		return Person.of(PersonId.of(), familyName, firstName, sex, birthday, Country.GERMANY);
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
	public final static Person of(FamilyName familyName, FirstName firstName, Sex sex, Birthday birthday,
	    Country country) {
		return Person.of(PersonId.of(), familyName, firstName, sex, birthday, country);
	}

	/**
	 * Creates a new person object with the given id.
	 * 
	 * @param id
	 * @param familyName
	 * @param firstName
	 * @param sex
	 * @param birthday
	 * @param country
	 * @return
	 */
	public final static Person of(PersonId id, FamilyName familyName, FirstName firstName, Sex sex, Birthday birthday,
	    Country country) {
		return new Person(id, familyName, firstName, sex, birthday, country);
	}

	/**
	 * 
	 * @return The list of marriage validations connected to this person
	 */
	private ValidateMarriage validations() {
		return validations;
	}

	/**
	 * @return the partnerShip
	 */
	public PartnerShip partnerShip() {
		return partnerShip;
	}

	/**
	 * @return the family
	 */
	public FamilyId family() {
		return family;
	}

}
