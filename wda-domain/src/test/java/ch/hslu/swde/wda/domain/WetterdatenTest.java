package ch.hslu.swde.wda.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class WetterdatenTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test für getCity Methode in der Klasse "Wetterdaten"
	 */
	@Test
	void testGetCity() {
		assertEquals(new City("Zürich", 8057),
				new Wetterdaten(1020, 80, 15, new City("Zürich", 8057), Timestamp.valueOf("2022-05-08 12:00:00"))
						.getCity());
	}

	/**
	 * Test für getLuftdruck Methode in der Klasse "Wetterdaten"
	 */
	@Test
	void testGetLuftdruck() {
		assertEquals(1020,
				new Wetterdaten(1020, 80, 15, new City("Zürich", 8057), Timestamp.valueOf("2022-05-08 12:00:00"))
						.getLuftdruck());
	}

	/**
	 * Test für getFeuchtigkeit Methode in der Klasse "Wetterdaten"
	 */
	@Test
	void testGetHumidity() {
		assertEquals(80,
				new Wetterdaten(1020, 80, 15, new City("Zürich", 8057), Timestamp.valueOf("2022-05-08 12:00:00"))
						.getFeuchtigkeit());
	}

	/**
	 * Test für getTemperatur Methode in der Klasse "Wetterdaten"
	 */
	@Test
	void testGetTemperatur() {
		assertEquals(15,
				new Wetterdaten(1020, 80, 15, new City("Zürich", 8057), Timestamp.valueOf("2022-05-08 12:00:00"))
						.getTemperatur());
	}

	/**
	 * Test für getTime Methode in der Klasse "Wetterdaten"
	 */
	@Test
	void testGetTime() {
		assertEquals(Timestamp.valueOf("2022-05-08 12:00:00"),
				new Wetterdaten(1020, 80, 15, new City("Zürich", 8057), Timestamp.valueOf("2022-05-08 12:00:00"))
						.getTime());
	}

	/**
	 * Test HashCode in der Klasse "Wetterdaten"
	 */
	@Test
	void testEquals() {
		EqualsVerifier.forClass(Wetterdaten.class).suppress(Warning.NULL_FIELDS).verify(); // https://jqno.nl/equalsverifier/manual/null/
	}

	/**
	 * Test zwei gleiche in der Klasse "Wetterdaten"
	 */
	@Test
	void testEqualsSame() {
		final Wetterdaten Wetterdaten1 = new Wetterdaten(1020, 80, 15, new City("Zürich", 8057),
				Timestamp.valueOf("2022-05-08 12:00:00"));
		final Wetterdaten Wetterdaten2 = Wetterdaten1;
		assertEquals(Wetterdaten1, Wetterdaten2);
	}

	/**
	 * Test zwei gleiche Wetterdaten in der Klasse "Wetterdaten"
	 */
	@Test
	void testEqualsEqual() {
		final Wetterdaten Wetterdaten1 = new Wetterdaten(1020, 80, 15, new City("Zürich", 8057),
				Timestamp.valueOf("2022-05-08 12:00:00"));
		final Wetterdaten Wetterdaten2 = new Wetterdaten(1020, 80, 15, new City("Zürich", 8057),
				Timestamp.valueOf("2022-05-08 12:00:00"));
		assertEquals(Wetterdaten1, Wetterdaten2);
	}

	/**
	 * Test zwei verschiedene Wetterdaten in der Klasse "Wetterdaten"
	 */
	@Test
	void testEqualsNotEqual() {
		final Wetterdaten Wetterdaten1 = new Wetterdaten(1020, 80, 15, new City("Zürich", 8057),
				Timestamp.valueOf("2022-05-08 12:00:00"));
		final Wetterdaten Wetterdaten2 = new Wetterdaten(1030, 70, 10, new City("Bern", 3000),
				Timestamp.valueOf("2022-05-07 10:00:00"));
		assertNotEquals(Wetterdaten1, Wetterdaten2);
	}

}
