package ch.hslu.swde.wda.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * Testfälle für City.
 */
class CityTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test für getName Methode in der Klasse "City"
	 */
	@Test
	void testGetName() {
		assertEquals("Zürich", new City("Zürich", 8057).getName());
	}

	/**
	 * Test für getPLZ Methode in der Klasse "City"
	 */
	@Test
	void testGetPLZ() {
		assertEquals(8057, new City("Zürich", 8057).getPlz());
	}

	/**
	 * Test für toString Methode in der Klasse "City"
	 */
	@Test
	void testToString() {
		assertThat(new City("Zürich", 8057).toString()).containsSequence("City= ID: ");
	}

	/**
	 * Test HashCode in der Klasse "City"
	 */
	@Test
	void testEquals() {
		EqualsVerifier.forClass(City.class).suppress(Warning.NULL_FIELDS).verify(); // https://jqno.nl/equalsverifier/manual/null/
	}

	/**
	 * Test zwei gleiche in der Klasse "City"
	 */
	@Test
	void EqualsSame() {
		final City city1 = new City("Zürich", 8057);
		final City city2 = city1;
		assertEquals(city1, city2);
	}

	/**
	 * Test zwei gleiche Städte in der Klasse "City"
	 */
	@Test
	void testEqualsEquals() {
		final City city1 = new City("Zürich", 8057);
		final City city2 = new City("Zürich", 8057);
		assertEquals(city1, city2);
	}

	/**
	 * Test zwei verschiedene Städte in der Klasse "City"
	 */
	@Test
	void testEqualsNotEqual() {
		final City city1 = new City("Zürich", 8057);
		final City city2 = new City("Bern", 3000);
		assertNotEquals(city1, city2);
	}

}
