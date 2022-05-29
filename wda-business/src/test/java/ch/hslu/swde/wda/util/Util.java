package ch.hslu.swde.wda.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ch.hslu.swde.wda.domain.City;
import ch.hslu.swde.wda.domain.LastUpdate;
import ch.hslu.swde.wda.domain.Mitarbeiter;
import ch.hslu.swde.wda.domain.Wetterdaten;

public class Util {

	private Util() {

	}

	public static List<City> cityListeHinzufügen() {

		List<City> list = new ArrayList<>();

		list.add(new City("Luzern", 60000));
		list.add(new City("Zürich", 80000));
		list.add(new City("Basel", 70000));
		list.add(new City("Bern", 30000));

		return list;
	}

	public static List<Mitarbeiter> mitarbeiterListeHinzufügen() {
		List<Mitarbeiter> list = new ArrayList<>();

		list.add(new Mitarbeiter("test", "test", "Test", "Test"));

		return list;
	}

	public static List<LastUpdate> lastUptadeListeHinzufügen() {
		List<LastUpdate> list = new ArrayList<>();

		list.add(new LastUpdate(Timestamp.valueOf("2022-05-05 23:00:00")));
		list.add(new LastUpdate(Timestamp.valueOf("2022-05-05 23:00:00")));
		list.add(new LastUpdate(Timestamp.valueOf("2022-05-05 23:00:00")));
		list.add(new LastUpdate(Timestamp.valueOf("2022-05-05 23:00:00")));

		return list;
	}

	public static List<Wetterdaten> wetterdatenListeHinzufügen() {
		List<Wetterdaten> list = new ArrayList<>();

		list.add(new Wetterdaten(20000, 30000, 40000, new City("Luzern", 60000),
				Timestamp.valueOf("1900-05-05 23:46:12")));
		list.add(new Wetterdaten(20000, 30000, 40001, new City("Zürich", 80000),
				Timestamp.valueOf("1900-05-06 23:46:12")));
		list.add(new Wetterdaten(20001, 30001, 40001, new City("Basel", 70000),
				Timestamp.valueOf("1900-05-05 23:46:13")));
		list.add(new Wetterdaten(20002, 30002, 40002, new City("Bern", 30000),
				Timestamp.valueOf("1900-05-05 23:46:14")));
		list.add(new Wetterdaten(20003, 30003, 40003, new City("Genf", 10000),
				Timestamp.valueOf("1900-05-05 23:46:15")));

		return list;
	}
}
