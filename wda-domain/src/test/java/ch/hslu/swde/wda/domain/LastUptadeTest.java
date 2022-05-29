package ch.hslu.swde.wda.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class LastUptadeTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test für getZeitLastUptade Methode in der Klasse "LastUptade"
	 */
	@Test
	void testGetZeitLastUptade() {
		assertEquals(Timestamp.valueOf("2022-05-08 12:00:00"),
				new LastUpdate(Timestamp.valueOf("2022-05-08 12:00:00")).getZeitLastUptade());
	}

	/**
	 * Test für toString Methode in der Klasse "LastUptade"
	 */
	@Test
	void testToString() {
		assertThat(new LastUpdate(Timestamp.valueOf("2022-05-08 12:00:00")).toString())
				.containsSequence("LastUpdate= Zeit des letzten Uptades: ");
	}

	/**
	 * Test HashCode in der Klasse "LastUptade"
	 */
	@Test
	void testEquals() {
		EqualsVerifier.forClass(LastUpdate.class).suppress(Warning.NULL_FIELDS).verify(); // https://jqno.nl/equalsverifier/manual/null/
	}

	/**
	 * Test zwei gleiche in der Klasse "LastUptade"
	 */
	@Test
	void testEqualsSame() {
		final LastUpdate lastUpdate1 = new LastUpdate(Timestamp.valueOf("2022-05-08 12:00:00"));
		final LastUpdate lastUpdate2 = lastUpdate1;
		assertEquals(lastUpdate1, lastUpdate2);
	}

	/**
	 * Test zwei gleiche Zeiten in der Klasse "LastUptade"
	 */
	@Test
	void testEqualsEqual() {
		final LastUpdate lastUpdate1 = new LastUpdate(Timestamp.valueOf("2022-05-08 12:00:00"));
		final LastUpdate lastUpdate2 = new LastUpdate(Timestamp.valueOf("2022-05-08 12:00:00"));
		assertEquals(lastUpdate1, lastUpdate2);
	}

	/**
	 * Test zwei verschiedene Zeiten in der Klasse "LastUptade"
	 */
	@Test
	void testEqualsNotEqual() {
		final LastUpdate lastUpdate1 = new LastUpdate(Timestamp.valueOf("2022-05-08 12:00:00"));
		final LastUpdate lastUpdate2 = new LastUpdate(Timestamp.valueOf("2022-05-07 10:00:00"));
		assertNotEquals(lastUpdate1, lastUpdate2);
	}

}
