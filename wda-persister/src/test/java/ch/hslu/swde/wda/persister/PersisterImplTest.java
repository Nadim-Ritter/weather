package ch.hslu.swde.wda.persister;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import ch.hslu.swde.wda.domain.City;
import ch.hslu.swde.wda.domain.LastUpdate;
import ch.hslu.swde.wda.domain.Mitarbeiter;
import ch.hslu.swde.wda.domain.Wetterdaten;
import ch.hslu.swde.wda.util.DbHelper;
import ch.hslu.swde.wda.util.UtilTest;

public class PersisterImplTest {

	private static List<City> listeCity;
	private static List<Mitarbeiter> mitarbeiterListe;
	private static List<LastUpdate> lastUptadeListe;
	private static List<Wetterdaten> wetterdatenListe;

	static PersisterImpl objectPersister = new PersisterImpl();

	@BeforeAll
	public static void setUpVorKlasse() throws Exception {

		listeCity = UtilTest.cityListeHinzufügen();

		mitarbeiterListe = UtilTest.mitarbeiterListeHinzufügen();

		lastUptadeListe = UtilTest.lastUptadeListeHinzufügen();

		wetterdatenListe = UtilTest.wetterdatenListeHinzufügen();

	}

	@AfterAll
	public static void tearDownNachKlass() throws Exception {

		DbHelper.mitarbeiternLöschen();

		DbHelper.lastUpdatesLöschen();

		DbHelper.wetterdataLöschen();

		DbHelper.citysLöschen();
	}

	/**
	 * Test: City Speichern
	 * 
	 * @throws Exception
	 */
	@Test
	void testCitySpeichern() throws Exception {
		objectPersister.citySpeichern(listeCity);
		List<City> AlleCity = objectPersister.alleCitys();
		assertTrue(AlleCity.containsAll(listeCity));
	}

	/**
	 * Test: MA speichern
	 * 
	 * @throws Exception
	 */
	@Test
	void testMitarbeterSpeicherne() throws Exception {
		objectPersister.mitarbeterSpeichern(mitarbeiterListe.get(0));
		List<Mitarbeiter> AlleMitarbeiter = objectPersister.alleMitarbeiter();
		assertTrue(AlleMitarbeiter.contains(mitarbeiterListe.get(0)));
	}

	/**
	 * Test: LastUptade speichern
	 * 
	 * @throws Exception
	 */
	@Test
	void testLastUptadeSpeichern() throws Exception {
		objectPersister.lastUptadeSpeichernOderAktualisieren(lastUptadeListe.get(0));
		List<LastUpdate> alleLastUptades = objectPersister.alleLastUptades();
		assertTrue(alleLastUptades.contains(lastUptadeListe.get(0)));
	}

	/**
	 * Test: LastUptade Aktualisieren
	 * 
	 * @throws Exception
	 */
	@Disabled
	void testLastUpdateAktualisieren() throws Exception {
		objectPersister.lastUptadeSpeichernOderAktualisieren(lastUptadeListe.get(0));
		List<LastUpdate> alleLastUptades = objectPersister.alleLastUptades();
		LastUpdate last = lastUptadeListe.get(1);
		objectPersister.lastUptadeSpeichernOderAktualisieren(last);
		alleLastUptades = new ArrayList<>();
		System.out.println("Hallo");
		System.out.println(alleLastUptades.get(0).getZeitLastUptade());
		assertTrue(objectPersister.alleLastUptades().get(1).getZeitLastUptade()
				.equals(Timestamp.valueOf("1900-05-05 23:47:13")));
	}

	/**
	 * Test: Wetterdaten speichern
	 * 
	 * @throws Exception
	 */
	@Test
	void testWetterdatenSpeichern() throws Exception {
		objectPersister.wetterdatenSpeichern(wetterdatenListe);
		List<Wetterdaten> alleWetterdaten = objectPersister.alleWetterdaten();
		assertTrue(alleWetterdaten.containsAll(wetterdatenListe));
	}

	/**
	 * Test: MA aktualisieren
	 * 
	 * @throws Exception
	 */
	@Test
	void testMitarbeiterAktualisierene() throws Exception {
		objectPersister.mitarbeiterAktualisieren(mitarbeiterListe.get(0));
		Mitarbeiter emp = new Mitarbeiter("Test", "Test", "Test", "Test");
		objectPersister.mitarbeiterAktualisieren(emp);
		List<Mitarbeiter> alleMitarbeiter = objectPersister.alleMitarbeiter();
		assertTrue(alleMitarbeiter.contains(emp));
	}

	/**
	 * Test: MA löschen
	 * 
	 * @throws Exception
	 */
	@Test
	void testMitarbeiterLöschen() throws Exception {
		objectPersister.mitarbeiterLöschen(mitarbeiterListe.get(0));
		objectPersister.mitarbeiterLöschen(mitarbeiterListe.get(0));
		List<Mitarbeiter> alleMitarbeiter = objectPersister.alleMitarbeiter();
		assertFalse(alleMitarbeiter.contains(mitarbeiterListe.get(0)));
	}

	/**
	 * Test: Wetterdaten löschen
	 * 
	 * @throws Exception
	 */
	@Test
	void testWetterdatenLöschen() throws Exception {
		List<Wetterdaten> liste = UtilTest.zweiteWetterdatenListeHinzufügen();
		List<Wetterdaten> listePrüfen = new ArrayList<>();
		int count = 0;
		for (Wetterdaten w : liste) {
			w.setId(50000 + count);
			count++;
		}

		objectPersister.wetterdatenSpeichern(listePrüfen);
		Wetterdaten temp = liste.get(0);
		objectPersister.wetterdatenLöschen(temp);
		List<Wetterdaten> alleWetterdaten = objectPersister.alleWetterdaten();
		assertFalse(alleWetterdaten.contains(temp));
	}

	/**
	 * Test: Alle Citys
	 * 
	 * @throws Exception
	 */
	@Test
	void testAlleCitys() throws Exception {
		objectPersister.citySpeichern(listeCity);
		List<City> cityVonDb = objectPersister.alleCitys();
		assertTrue(cityVonDb.containsAll(listeCity));

	}

	/**
	 * Test: Alle MA
	 * 
	 * @throws Exception
	 */
	@Test
	void testAlleMitarbeiter() throws Exception {
		objectPersister.mitarbeterSpeichern(mitarbeiterListe.get(0));
		List<Mitarbeiter> maVonDb = objectPersister.alleMitarbeiter();
		assertTrue(maVonDb.contains(mitarbeiterListe.get(0)));
	}

	/**
	 * Test: Alle Wetterdaten
	 * 
	 * @throws Exception
	 */
	@Test
	void testAllWeatherData() throws Exception {
		objectPersister.citySpeichern(listeCity);
		objectPersister.wetterdatenSpeichern(wetterdatenListe);
		List<Wetterdaten> wetterdatenVonDb = objectPersister.alleWetterdaten();
		assertTrue(wetterdatenVonDb.containsAll(wetterdatenListe));
	}

	/**
	 * Test: MaFindenMitID
	 * 
	 * @throws Exception
	 */
	@Test
	void testMaFindenMitID() throws Exception {
		objectPersister.mitarbeterSpeichern(mitarbeiterListe.get(0));

		Mitarbeiter maVonDb = objectPersister.maFindenMitID(mitarbeiterListe.get(0).getId());
		assertTrue(maVonDb.equals(mitarbeiterListe.get(0)));
	}

	/**
	 * Test: MaFindenMitEmailUndPasswort
	 * 
	 * @throws Exception
	 */
	@Test
	void testMaFindenMitEmailUndPasswort() throws Exception {
		objectPersister.mitarbeterSpeichern(mitarbeiterListe.get(0));

		Mitarbeiter maVonDb = objectPersister.maFindenMitID(mitarbeiterListe.get(0).getId());
		assertTrue(maVonDb.equals(mitarbeiterListe.get(0)));
	}

	/**
	 * Test: LastUptadeFinden
	 * 
	 * @throws Exception
	 */
	@Test
	void testLastUptadeFinden() throws Exception {
		List<LastUpdate> list = objectPersister.alleLastUptades();
		LastUpdate luFromDb = list.get(0);
		if (luFromDb == null) {
			objectPersister.lastUptadeSpeichernOderAktualisieren(lastUptadeListe.get(0));
		}

		assertTrue(luFromDb.getZeitLastUptade() != Timestamp.valueOf("2022-05-05 12:00:00")
				&& luFromDb.getZeitLastUptade() != null);

	}

	/**
	 * Test: CityFindenNachName
	 * 
	 * @throws Exception
	 */
	@Test
	void testCityFindenNachName() throws Exception {
		objectPersister.citySpeichern(listeCity);
		City cityVonDb = objectPersister.cityFindenNachName("Luzern");

		assertTrue(listeCity.get(0).equals(cityVonDb));

	}

	/**
	 * Test: TFLFinden
	 * 
	 * @throws Exception
	 */
	@Test
	void testTFLFinden() throws Exception {
		objectPersister.wetterdatenSpeichern(wetterdatenListe);
		List<Wetterdaten> wetterdatenVonDb = objectPersister.TFLFinden("Luzern",
				Timestamp.valueOf("2022-05-05 23:00:00"), Timestamp.valueOf("2022-05-05 23:46:00"));

		assertTrue(wetterdatenVonDb.contains(wetterdatenListe.get(0)));

	}

	/**
	 * Test: TLFFindenMinMax
	 * 
	 * @throws Exception
	 */
	@Test
	void testTLFFindenMinMax() throws Exception {
		objectPersister.wetterdatenSpeichern(wetterdatenListe);
		List<Wetterdaten> wetterdatenVonDb = objectPersister.TLFMinMaxFinden(Timestamp.valueOf("2022-05-05 23:00:00"));

		assertTrue(wetterdatenVonDb.containsAll(wetterdatenListe));

	}

}
