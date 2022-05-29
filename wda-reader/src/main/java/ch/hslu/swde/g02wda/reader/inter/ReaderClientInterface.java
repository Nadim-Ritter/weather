package ch.hslu.swde.g02wda.reader.inter;

import java.util.List;

import ch.hslu.swde.wda.domain.City;
import ch.hslu.swde.wda.domain.Wetterdaten;

public interface ReaderClientInterface {

	public List<City> findeCities() throws Exception;
	//public WeatherData findWeatherData(String cityName) throws Exception;
	public List<Wetterdaten> findeWetterdaten(String cityName) throws Exception;
}
		