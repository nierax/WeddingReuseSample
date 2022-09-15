package de.lexasoft.wedding;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.lexasoft.wedding.message.AtLeast18YearsOldForMarriageRequired;
import de.lexasoft.wedding.message.Message;
import de.lexasoft.wedding.message.MessageSeverity;
import de.lexasoft.wedding.message.MinimumAgeInUSAWarning;
import de.lexasoft.wedding.message.MustNotBeMarriedBeforeError;
import de.lexasoft.wedding.message.NotAllowedToMarryMyselfError;

class PersonTest {

	Person cut = Person.of(//
	    FamilyName.of("Smith"), //
	    FirstName.of("John"), //
	    Sex.of(SexEnum.MALE), //
	    Birthday.of(1990, 11, 27));

	@BeforeEach
	void setUp() throws Exception {

	}

	private static Birthday birthdayForAge(long age) {
		return Birthday.of(LocalDate.now().minusYears(age));
	}

	private final static Stream<Arguments> testAgeInYearsAtDate() {
		return Stream.of(//
		    Arguments.of(LocalDate.of(2008, 11, 27), 18), // 18th birthday
		    Arguments.of(LocalDate.of(2008, 11, 26), 17), // day before 18th birthday
		    Arguments.of(LocalDate.of(2008, 11, 28), 18), // day after 18th birthday
		    Arguments.of(LocalDate.of(2018, 11, 28), 28), // 28th birthday
		    Arguments.of(LocalDate.of(1990, 11, 28), 0), // day of birth
		    Arguments.of(LocalDate.of(1991, 11, 26), 0)); // day before first birthday
	}

	/**
	 * Check the age calculation
	 * 
	 * @param otherDate   Date to compare with.
	 * @param expectedAge Expected age
	 */
	@ParameterizedTest
	@MethodSource
	final void testAgeInYearsAtDate(LocalDate otherDate, long expectedAge) {
		assertEquals(expectedAge, cut.ageInYearsAtDate(otherDate));
	}

	@Test
	final void isMarried_intial_False() {
		assertFalse(cut.isMarried());
	}

	private static final Stream<Arguments> marries_otherPerson() {
		return Stream.of(//
		    // Today
		    Arguments.of(Date.of()),
		    // Day in future
		    Arguments.of(Date.of(LocalDate.of(2022, 11, 27))),
		    // Day in the past
		    Arguments.of(Date.of(LocalDate.of(2020, 11, 27))));
	}

	/**
	 * Marriage to another person.
	 */
	@ParameterizedTest
	@MethodSource
	final void marries_otherPerson(Date dateOfWedding) {
		Person partner = Person.of(//
		    FamilyName.of("Miller"), //
		    FirstName.of("Maria"), //
		    Sex.of(SexEnum.FEMALE), //
		    Birthday.of(1999, 12, 22));

		// Now they marry each other. Should return the partner.
		Result<Person> result = cut.marries(partner, dateOfWedding);

		// Are they married correctly?
		assertEquals(cut, result.value());
		assertTrue(cut.isMarried());
		assertEquals(partner.id(), cut.marriedWithID());
		assertEquals(dateOfWedding, cut.dateOfWedding());
		assertTrue(partner.isMarried());
		assertEquals(cut.id(), partner.marriedWithID());
		assertEquals(dateOfWedding, partner.dateOfWedding());
	}

	@Test
	final void not_allowed_to_marry_myself() {
		// Try to marry myself.
		Result<Person> result = cut.marries(cut);
		assertEquals(MessageSeverity.ERROR, result.resultSeverity());
		assertTrue(result.messages().get(0) instanceof NotAllowedToMarryMyselfError);
		assertEquals(cut, result.value());
		assertFalse(result.value().isMarried());
		assertNull(result.value().marriedWithID());
	}

	static Stream<Arguments> both_persons_must_be_at_least_18_years_old() {
		return Stream.of(//
		    // both > 18
		    Arguments.of(//
		        Person.of(FamilyName.of("fam1"), FirstName.of("first1"), Sex.of(SexEnum.MALE), birthdayForAge(20)),
		        Person.of(FamilyName.of("fam2"), FirstName.of("first2"), Sex.of(SexEnum.FEMALE), birthdayForAge(18)), //
		        0, //
		        MessageSeverity.NONE), //
		    // first person < 18
		    Arguments.of(//
		        Person.of(FamilyName.of("fam1"), FirstName.of("first1"), Sex.of(SexEnum.MALE), birthdayForAge(8)),
		        Person.of(FamilyName.of("fam2"), FirstName.of("first2"), Sex.of(SexEnum.FEMALE), birthdayForAge(18)), //
		        1, //
		        MessageSeverity.ERROR),
		    // both persons < 18
		    Arguments.of(//
		        Person.of(FamilyName.of("fam1"), FirstName.of("first1"), Sex.of(SexEnum.MALE), birthdayForAge(8)),
		        Person.of(FamilyName.of("fam2"), FirstName.of("first2"), Sex.of(SexEnum.FEMALE), birthdayForAge(17)), //
		        2, //
		        MessageSeverity.ERROR),
		    // second person > 18
		    Arguments.of(//
		        Person.of(FamilyName.of("fam1"), FirstName.of("first1"), Sex.of(SexEnum.MALE), birthdayForAge(18)),
		        Person.of(FamilyName.of("fam2"), FirstName.of("first2"), Sex.of(SexEnum.FEMALE), birthdayForAge(17)), //
		        1, //
		        MessageSeverity.ERROR));
	}

	@ParameterizedTest
	@MethodSource
	final void both_persons_must_be_at_least_18_years_old(Person person1, Person person2, int nrOfMessages,
	    MessageSeverity severity) {
		Result<Person> result = person1.marries(person2);
		assertEquals(nrOfMessages, result.nrOfMessages());
		if (nrOfMessages > 0) {
			for (Message message : result.messages()) {
				assertTrue(message instanceof AtLeast18YearsOldForMarriageRequired);
			}
		}
		assertSame(severity, result.resultSeverity());
	}

	private final static Person partnerUsedToBeFemale() {
		return Person.of(//
		    FamilyName.of("PartnerUsedToBeFemale"), //
		    FirstName.of("other"), //
		    Sex.of(SexEnum.FEMALE), //
		    birthdayForAge(20));
	}

	private final static Person partnerUsedToBeMale() {
		return Person.of(//
		    FamilyName.of("PartnerUsedToBeMale"), //
		    FirstName.of("other"), //
		    Sex.of(SexEnum.MALE), //
		    birthdayForAge(30));
	}

	private final static Stream<Arguments> both_persons_must_not_be_married_before() {
		return Stream.of(//
		    Arguments.of(//
		        // Valid, not married
		        Person.of(FamilyName.of("fam1"), FirstName.of("first1"), Sex.of(SexEnum.MALE), birthdayForAge(20)),
		        Person.of(FamilyName.of("fam2"), FirstName.of("first2"), Sex.of(SexEnum.FEMALE), birthdayForAge(18)), //
		        0, //
		        MessageSeverity.NONE), //
		    // Not valid, first partner married
		    Arguments.of(//
		        Person.of(FamilyName.of("fam1"), FirstName.of("first1"), Sex.of(SexEnum.MALE), birthdayForAge(20))
		            .marries(partnerUsedToBeFemale()).value(),
		        Person.of(FamilyName.of("fam2"), FirstName.of("first2"), Sex.of(SexEnum.FEMALE), birthdayForAge(18)), //
		        1, //
		        MessageSeverity.ERROR), //
		    // Not valid, second partner married
		    Arguments.of(//
		        Person.of(FamilyName.of("fam1"), FirstName.of("first1"), Sex.of(SexEnum.MALE), birthdayForAge(20)),
		        Person.of(FamilyName.of("fam2"), FirstName.of("first2"), Sex.of(SexEnum.FEMALE), birthdayForAge(18))
		            .marries(partnerUsedToBeMale()).value(), //
		        1, //
		        MessageSeverity.ERROR), //
		    // Not valid, second partner married
		    Arguments.of(//
		        Person.of(FamilyName.of("fam1"), FirstName.of("first1"), Sex.of(SexEnum.MALE), birthdayForAge(20))
		            .marries(partnerUsedToBeFemale()).value(),
		        Person.of(FamilyName.of("fam2"), FirstName.of("first2"), Sex.of(SexEnum.FEMALE), birthdayForAge(18))
		            .marries(partnerUsedToBeMale()).value(), //
		        2, //
		        MessageSeverity.ERROR) //
		);
	}

	/**
	 * 
	 * @param person1
	 * @param person2
	 * @param nrOfMessages
	 * @param severity
	 */
	@ParameterizedTest
	@MethodSource
	final void both_persons_must_not_be_married_before(Person person1, Person person2, int nrOfMessages,
	    MessageSeverity severity) {
		// Let's marry ;-)
		Result<Person> result = person1.marries(person2);
		// Check the result
		assertEquals(nrOfMessages, result.nrOfMessages());
		assertEquals(severity, result.resultSeverity());
		if (nrOfMessages > 0) {
			assertThat(result.messages(), hasItem(new MustNotBeMarriedBeforeError(person1)));
		}
	}

	static Stream<Arguments> minimal_age_in_nebraska_19_and_21_in_mississippi() {
		return Stream.of(//
		    // first ok, second 19
		    Arguments.of(//
		        Person.of(FamilyName.of("fam1"), FirstName.of("first1"), Sex.of(SexEnum.MALE), birthdayForAge(21),
		            Country.UNITED_STATES),
		        Person.of(FamilyName.of("fam2"), FirstName.of("first2"), Sex.of(SexEnum.FEMALE), birthdayForAge(19),
		            Country.UNITED_STATES), //
		        1, //
		        MessageSeverity.WARNING, //
		        MinimumAgeInUSAWarning.class), //
		    // both persons > 21
		    Arguments.of(//
		        Person.of(FamilyName.of("fam1"), FirstName.of("first1"), Sex.of(SexEnum.MALE), birthdayForAge(22),
		            Country.UNITED_STATES),
		        Person.of(FamilyName.of("fam2"), FirstName.of("first2"), Sex.of(SexEnum.FEMALE), birthdayForAge(21),
		            Country.UNITED_STATES), //
		        0, //
		        MessageSeverity.NONE, //
		        null), //
		    // both persons < 18 => Dominates, the USA validation should not be executed.
		    Arguments.of(//
		        Person.of(FamilyName.of("fam1"), FirstName.of("first1"), Sex.of(SexEnum.MALE), birthdayForAge(8),
		            Country.UNITED_STATES),
		        Person.of(FamilyName.of("fam2"), FirstName.of("first2"), Sex.of(SexEnum.FEMALE), birthdayForAge(17),
		            Country.UNITED_STATES), //
		        2, //
		        MessageSeverity.ERROR, //
		        AtLeast18YearsOldForMarriageRequired.class),
		    // both persons < 21
		    Arguments.of(//
		        Person.of(FamilyName.of("fam1"), FirstName.of("first1"), Sex.of(SexEnum.MALE), birthdayForAge(18),
		            Country.UNITED_STATES),
		        Person.of(FamilyName.of("fam2"), FirstName.of("first2"), Sex.of(SexEnum.FEMALE), birthdayForAge(20),
		            Country.UNITED_STATES), //
		        2, //
		        MessageSeverity.WARNING, //
		        MinimumAgeInUSAWarning.class));
	}

	@ParameterizedTest
	@MethodSource
	final void minimal_age_in_nebraska_19_and_21_in_mississippi(Person person1, Person person2, int nrOfMessages,
	    MessageSeverity severity, Class<?> msgClass) {
		Result<Person> result = person1.marries(person2);
		assertEquals(nrOfMessages, result.nrOfMessages());
		if (nrOfMessages > 0) {
			for (Message message : result.messages()) {
				assertEquals(msgClass, message.getClass());
			}
		}
		assertSame(severity, result.resultSeverity());
	}

}
