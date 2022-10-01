package de.lexasoft.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PersonTest {

	Person cut = Person.of(//
	    FamilyName.of("Smith"), //
	    FirstName.of("John"), //
	    Sex.of(SexEnum.MALE), //
	    Birthday.of(1990, 11, 27));

	@BeforeEach
	void setUp() throws Exception {

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

	@Test
	final void partnership_initial_not_married() {
		assertEquals(PartnerShip.NOT_MARRIED, cut.partnerShip());
	}

	@Test
	final void family_initial_none() {
		assertEquals(FamilyId.NONE, cut.family());
	}

}
