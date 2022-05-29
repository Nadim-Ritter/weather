/*
 * Copyright 2022 Roland Gisler, HSLU Informatik, Switzerland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.hslu.swde.wda.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ch.hslu.swde.wda.domain.City;
import ch.hslu.swde.wda.domain.LastUpdate;
import ch.hslu.swde.wda.domain.Mitarbeiter;
import ch.hslu.swde.wda.domain.Wetterdaten;
import ch.hslu.swde.wda.persister.PersisterImpl;
import ch.hslu.swde.wda.util.DbHelper;
import ch.hslu.swde.wda.util.Util;

/**
 * Testcases fuer {@link ch.hslu.swde.wda.business.VerwaltungImpl}.
 */

final class VerwaltungImplTest {

	private static List<City> alleCitys;
	private static List<Mitarbeiter> alleMitarbeiter;
	private static List<LastUpdate> lastUpdate;
	private static List<Wetterdaten> wetterdatenListe;

	PersisterImpl persister = new PersisterImpl();

	VerwaltungImpl verwaltung = new VerwaltungImpl();

	@BeforeAll
	public static void setUpVorKlasse() throws Exception {

		alleCitys = Util.cityListeHinzufügen();
		alleMitarbeiter = Util.mitarbeiterListeHinzufügen();
		lastUpdate = Util.lastUptadeListeHinzufügen();
		wetterdatenListe = Util.wetterdatenListeHinzufügen();
	}

	@AfterEach
	public void tearDownNachKlasse() throws Exception {
		DbHelper.mitarbeiternLöschen();
		DbHelper.lastUpdatesLöschen();
		DbHelper.wetterdataLöschen();
		DbHelper.citysLöschen();
	}

	@Test
	public void testMitarbeiterHinzufuegen() throws Exception {
		VerwaltungImpl list = new VerwaltungImpl();
		Mitarbeiter m = new Mitarbeiter("mitarbeiter@test.ch", "1, 2, 3, 4", "Mann", "test");
		list.neuerMitarbeiterHinzufügen(m);

		assertEquals(m, new Mitarbeiter("mitarbeiter@test.ch", "1, 2, 3, 4", "Mann", "test"));

	}

	@Test
	public void testMitarbeiterAktualisieren() throws Exception {
		// Mitarbeiter m = new Mitarbeiter("mitarbeiter@test.ch", "1, 2, 3, 4", "Mann",
		// "test");

		List<Mitarbeiter> list = verwaltung.alleMitarbeiter();
		Mitarbeiter change = verwaltung.maFindenMitID(list.get(0).getId());

		change.setName("Peter");
		verwaltung.mitarbeiterAktualisieren(change);

		assertEquals(verwaltung.maFindenMitID(change.getId()).getName(), "Peter");
	}

	@Test
	public void testMaFindenMitEmailUndPasswort() throws Exception {
		VerwaltungImpl verwaltung = new VerwaltungImpl();
		Mitarbeiter m = new Mitarbeiter("mitarbeiter@test.ch", "1, 2, 3, 4", "Mann", "test");
		verwaltung.neuerMitarbeiterHinzufügen(m);
		List<Mitarbeiter> list = verwaltung.alleMitarbeiter();

		assertTrue(list
				.contains(verwaltung.maFindenMitEmailUndPasswort(list.get(0).getEmail(), list.get(0).getPasswort())));

	}

	@Test
	public void testMaFindenMitID() throws Exception {
		List<Mitarbeiter> list = verwaltung.alleMitarbeiter();

		assertTrue(list.contains(verwaltung.maFindenMitID(list.get(0).getId())));
	}

	@Test
	public void testAlleMitarbeiter() throws Exception {
		List<Mitarbeiter> list = verwaltung.alleMitarbeiter();

		assertTrue(list.containsAll(verwaltung.alleMitarbeiter()));

	}

	@Test
	public void testMitarbeiterLöschen() throws Exception {
		VerwaltungImpl list = new VerwaltungImpl();
		List<Mitarbeiter> mitarbeiterListe = new ArrayList<>();

		Mitarbeiter m1 = new Mitarbeiter("Irene@Soto.ch", "1, 2, 3, 4", "irene", "soto");
		Mitarbeiter m2 = new Mitarbeiter("christian@zbinden.ch", "1, 2, 3, 4", "zbinden", "christian");
		Mitarbeiter m3 = new Mitarbeiter("mitarbeiter@test.ch", "1, 2, 3, 4", "Mann", "test");

		list.neuerMitarbeiterHinzufügen(m1);
		list.neuerMitarbeiterHinzufügen(m2);
		list.neuerMitarbeiterHinzufügen(m3);

		list.mitarbeiterLöschen(m1);
		list.mitarbeiterLöschen(m2);
		list.mitarbeiterLöschen(m3);

		assertEquals(0, mitarbeiterListe.size());
	}

	@Test
	public void testAlleCitysFinden() throws Exception {

		List<City> list = verwaltung.alleCitysFinden();

		assertTrue(list.containsAll(verwaltung.alleCitysFinden()));
	}

	@Test
	public void testTLFFinden() throws Exception {
		persister.wetterdatenSpeichern(wetterdatenListe);
		List<Wetterdaten> TLFFinden = verwaltung.TLFFinden("Bern", Timestamp.valueOf("2000-09-22s 10:11:12"),
				Timestamp.valueOf("2000-09-22 18:36:15"));

		assertTrue(TLFFinden.size() == 5);
	}

	@Test
	public void testTemperaturDurchschnittFinden() throws Exception {
		List<Wetterdaten> TLF = verwaltung.TLFDurchschnittFinden("Bern", Timestamp.valueOf("2000-09-05 23:46:10"),
				Timestamp.valueOf("2000-09-22 18:34:15"));
		double temperatur = VerwaltungImpl
				.durchschnittsTemperaturVonListe(TLF); /* Neu gemacht, ablgleich mit Gruppe nötig */

		assertTrue(temperatur == 30001.5);

	}

	@Test
	public void testLuftdruckDurchschnittFinden() throws Exception {
		List<Wetterdaten> TLF = verwaltung.TLFDurchschnittFinden("Zürich", Timestamp.valueOf("2000-10-10 10:18:23"),
				Timestamp.valueOf("2000-09-22 18:34:15"));
		double luftdruck = VerwaltungImpl.durchschnittsLuftdruckVonListe(TLF);

		assertTrue(luftdruck == 20001.5);

	}

	@Test
	public void testFeuchtigkeitDurchschnittFinden() throws Exception {
		List<Wetterdaten> TLF = verwaltung.TLFDurchschnittFinden("Luzern", Timestamp.valueOf("2000-07-08 15:46:10"),
				Timestamp.valueOf("2000-09-22 18:34:15"));
		double feuchtigkeit = VerwaltungImpl.durchschnittsFeuchtigkeitswertVonListe(TLF);

		assertTrue(feuchtigkeit == 40001.5);

	}

	@Test
	public void testTLFMinMaxTemperaturFinden() throws Exception {
		persister.citySpeichern(alleCitys);
		persister.wetterdatenSpeichern(wetterdatenListe);
		List<Wetterdaten> TemperaturFindenMinMax = verwaltung.TLFMinMaxFinden("Zürich",
				Timestamp.valueOf("1900-05-05 23:46:09"), Timestamp.valueOf("1900-05-06 23:46:16"));
		double maxTemparatur = VerwaltungImpl.maxTemperaturvonListe(TemperaturFindenMinMax);
		double minTemperatur = VerwaltungImpl.minTemperaturVonListe(TemperaturFindenMinMax);

		assertTrue(maxTemparatur == 40001 && minTemperatur == 40000);

	}

	@Test
	public void testMinMaxLuftdruckFinden() throws Exception {
		persister.citySpeichern(alleCitys);
		persister.wetterdatenSpeichern(wetterdatenListe);
		List<Wetterdaten> LuftdruckFindenMinMax = verwaltung.TLFMinMaxFinden("Bern",
				Timestamp.valueOf("1900-05-05 23:46:12"), Timestamp.valueOf("2000-09-22 18:34:15"));

		double maxLuftdruck = VerwaltungImpl.maxLuftdruckVonListe(LuftdruckFindenMinMax);
		double minLuftdruck = VerwaltungImpl.minLuftdruckVonListe(LuftdruckFindenMinMax);

		assertTrue(maxLuftdruck == wetterdatenListe.get(0).getLuftdruck()
				&& minLuftdruck == wetterdatenListe.get(0).getLuftdruck());

	}

	@Test
	public void testMinMaxFeuchtigkeitFinden() throws Exception {
		List<Wetterdaten> FeuchtigkeitFindenMinMax = verwaltung.TLFMinMaxFinden("Luzern",
				Timestamp.valueOf("1900-05-05 23:46:12"), Timestamp.valueOf("2000-09-22 18:34:15"));
		double maxFeuchtigkeit = VerwaltungImpl.maxFeuchtigkeitswertVonListe(FeuchtigkeitFindenMinMax);
		double minFeuchtigkeit = VerwaltungImpl.minFeuchtigkeitVonListe(FeuchtigkeitFindenMinMax);

		assertTrue(maxFeuchtigkeit == wetterdatenListe.get(0).getFeuchtigkeit()
				&& minFeuchtigkeit == wetterdatenListe.get(0).getFeuchtigkeit());

	}

	@Test
	public void testTLFFindenMinMaxAlleCity() throws Exception {
		List<Wetterdaten> wetterdatenListe = verwaltung
				.TLFMinMaxAlleCitysFinden(Timestamp.valueOf("1900-05-05 23:46:12"));

		assertTrue(wetterdatenListe
				.containsAll(verwaltung.TLFMinMaxAlleCitysFinden(Timestamp.valueOf("1900-05-05 23:46:12"))));
	}
}

/**
 * Quellen:
 * https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html
 */