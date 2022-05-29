package ch.hslu.swde.wda.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class MitarbeiterTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test für getEmail Methode in der Klasse "Mitarbeiter"
	 */
	@Test
	void testGetEmail() {
		assertEquals("max@email.ch", new Mitarbeiter("max@email.ch", "12345678", "Mustermann", "Max").getEmail());
	}

	/**
	 * Test für getPasswort Methode in der Klasse "Mitarbeiter"
	 */
	@Test
	void testGetPasswort() {
		assertEquals("12345678", new Mitarbeiter("max@email.ch", "12345678", "Mustermann", "Max").getPasswort());
	}

	/**
	 * Test für getName Methode in der Klasse "Mitarbeiter"
	 */
	@Test
	void testGetName() {
		assertEquals("Mustermann", new Mitarbeiter("max@email.ch", "12345678", "Mustermann", "Max").getName());
	}

	/**
	 * Test für getVorname Methode in der Klasse "Mitarbeiter"
	 */
	@Test
	void testGetFirstName() {
		assertEquals("Max", new Mitarbeiter("max@email.ch", "12345678", "Mustermann", "Max").getVorname());
	}

	/**
	 * Test für toString Methode in der Klasse "Mitarbeiter"
	 */
	@Test
	void testToString() {
		assertThat(new Mitarbeiter("max@email.ch", "12345678", "Mustermann", "Max").toString())
				.containsSequence("Mitarbeiter= Email: ");
	}

	/**
	 * Test HashCode in der Klasse "Mitarbeiter"
	 */
	@Test
	void testEquals() {
		EqualsVerifier.forClass(Mitarbeiter.class).suppress(Warning.NULL_FIELDS).verify(); // https://jqno.nl/equalsverifier/manual/null/
	}

	/**
	 * Test zwei gleiche in der Klasse "Mitarbeiter"
	 */
	@Test
	void testEqualsSame() {
		final Mitarbeiter Mitarbeiter1 = new Mitarbeiter("max@email.ch", "12345678", "Mustermann", "Max");
		final Mitarbeiter Mitarbeiter2 = Mitarbeiter1;
		assertEquals(Mitarbeiter1, Mitarbeiter2);
	}

	/**
	 * Test zwei gleiche Mitarbeiter in der Klasse "Mitarbeiter"
	 */
	@Test
	void testEqualsEqual() {
		final Mitarbeiter Mitarbeiter1 = new Mitarbeiter("max@email.ch", "12345678", "Mustermann", "Max");
		final Mitarbeiter Mitarbeiter2 = new Mitarbeiter("max@email.ch", "12345678", "Mustermann", "Max");
		assertEquals(Mitarbeiter1, Mitarbeiter2);
	}

	/**
	 * Test zwei verschiedene Mitarbeiter in der Klasse "Mitarbeiter"
	 */
	@Test
	void testEqualsNotEqual() {
		final Mitarbeiter Mitarbeiter1 = new Mitarbeiter("max@email.ch", "12345678", "Mustermann", "Max");
		final Mitarbeiter Mitarbeiter2 = new Mitarbeiter("hans@email.ch", "abcdefgh", "Müller", "Hans");
		assertNotEquals(Mitarbeiter1, Mitarbeiter2);
	}

}
