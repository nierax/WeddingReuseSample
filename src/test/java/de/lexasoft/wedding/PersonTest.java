package de.lexasoft.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
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
		    Arguments.of(LocalDate.of(2008, 11, 27), 18), //
		    Arguments.of(LocalDate.of(2008, 11, 26), 17), //
		    Arguments.of(LocalDate.of(2008, 11, 28), 18), //
		    Arguments.of(LocalDate.of(2018, 11, 28), 28), //
		    Arguments.of(LocalDate.of(1990, 11, 28), 0), //
		    Arguments.of(LocalDate.of(1991, 11, 26), 0));
	}

	@ParameterizedTest
	@MethodSource
	final void testAgeInYearsAtDate(LocalDate otherDate, long expectedAge) {
		assertEquals(expectedAge, cut.ageInYearsAtDate(otherDate));
	}

}
