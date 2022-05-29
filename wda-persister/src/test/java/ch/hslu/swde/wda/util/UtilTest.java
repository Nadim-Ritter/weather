package ch.hslu.swde.wda.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ch.hslu.swde.wda.domain.City;
import ch.hslu.swde.wda.domain.LastUpdate;
import ch.hslu.swde.wda.domain.Mitarbeiter;
import ch.hslu.swde.wda.domain.Wetterdaten;

public class UtilTest {

	private UtilTest() {

	}

	public static List<City> cityListeHinzufügen() {

		List<City> liste = new ArrayList<>();

		liste.add(new City("Luzern", 60000));
		liste.add(new City("Zürich", 80000));
		liste.add(new City("Basel", 70000));
		liste.add(new City("Bern", 30000));

		return liste;
	}

	public static List<Mitarbeiter> mitarbeiterListeHinzufügen() {
		List<Mitarbeiter> liste = new ArrayList<>();

		liste.add(new Mitarbeiter("Test", "Test", "Test", "Test"));

		return liste;
	}

	public static List<LastUpdate> lastUptadeListeHinzufügen() {
		List<LastUpdate> liste = new ArrayList<>();

		liste.add(new LastUpdate(Timestamp.valueOf("2022-05-05 23:00:00")));
		liste.add(new LastUpdate(Timestamp.valueOf("2022-05-05 23:00:00")));
		liste.add(new LastUpdate(Timestamp.valueOf("2022-05-05 23:00:00")));
		liste.add(new LastUpdate(Timestamp.valueOf("2022-05-05 23:00:00")));

		return liste;
	}

	public static List<Wetterdaten> wetterdatenListeHinzufügen() {
		List<Wetterdaten> liste = new ArrayList<>();

		liste.add(new Wetterdaten(20000, 30000, 40000, new City("Luzern", 60000),
				Timestamp.valueOf("1900-05-05 23:46:12")));
		liste.add(new Wetterdaten(20001, 30001, 40001, new City("Zürich", 80000),
				Timestamp.valueOf("1900-05-05 23:46:13")));
		liste.add(new Wetterdaten(20002, 30002, 40002, new City("Basel", 70000),
				Timestamp.valueOf("1900-05-05 23:46:14")));
		liste.add(new Wetterdaten(20003, 30003, 40003, new City("Bern", 30000),
				Timestamp.valueOf("1900-05-05 23:46:15")));

		return liste;
	}

	public static List<Wetterdaten> zweiteWetterdatenListeHinzufügen() {
		List<Wetterdaten> liste = new ArrayList<>();

		liste.add(new Wetterdaten(20000, 30000, 40000, new City("Luzern", 60000),
				Timestamp.valueOf("1901-05-05 23:46:12")));
		liste.add(new Wetterdaten(20001, 30001, 40001, new City("Zürich", 80000),
				Timestamp.valueOf("1901-05-05 23:46:13")));
		liste.add(new Wetterdaten(20002, 30002, 40002, new City("Basel", 70000),
				Timestamp.valueOf("1901-05-05 23:46:14")));
		liste.add(new Wetterdaten(20003, 30003, 40003, new City("Bern", 30000),
				Timestamp.valueOf("1901-05-05 23:46:15")));

		return liste;
	}
}
