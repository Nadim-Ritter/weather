package ch.hslu.swde.g02wda.reader.client.UT;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ch.hslu.swde.wda.domain.City;

public class ReaderTest {

	private ReaderTest() {

	}

	@Test
	public static List<City> erstelleCityListe() {
		List<City> list = new ArrayList<>();

		list.add(new City("Bern", 3001));
		list.add(new City("St. Gallen", 9000));
		list.add(new City("Genf", 1201));
		return list;
	}

}
