package ch.hslu.swde.g02wda.reader.client;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import ch.hslu.swde.g02wda.reader.inter.ReaderClientInterface;
import ch.hslu.swde.wda.domain.City;
import ch.hslu.swde.wda.domain.Wetterdaten;

public class ReaderClient implements ReaderClientInterface {
    private static final Logger LOGREADER = LogManager.getLogger(ReaderClient.class);
	private static final String CONTENT_TYPE = "application/json";
	private HttpClient client = HttpClient.newBuilder().build();
	private ObjectMapper mapper = new ObjectMapper();
	private Wetterdaten wd = new Wetterdaten();
	private City city = new City();

	public ReaderClient() {

	}

	@Override
	public List<City> findeCities() throws Exception {
		List<City> cityList = new ArrayList<>();
		URI uri = new URI("http://swde.el.eee.intern:8080/weatherdata-provider/rest/weatherdata/cities" + "cities");
		HttpRequest req = HttpRequest.newBuilder(uri).header("Accept", CONTENT_TYPE).GET().build();
		HttpResponse<String> res = client.send(req, BodyHandlers.ofString());
		if (res.statusCode() == 200) {
			String contentAsJson = res.body();
			cityList = mapper.readValue(contentAsJson, new TypeReference<List<City>>() {
			});
		} else {
			throw new Exception("Uebertragung fehlgeschlagen [Status Code =" + res.statusCode() + "]");
		}
		return cityList != null ? cityList : new ArrayList<>();
	}

	@Override
	public List<Wetterdaten> findeWetterdaten(String cityName) throws Exception {
		List<Wetterdaten> wdList = new ArrayList<Wetterdaten>();
		List<City> cityListe = findeCities();

		URI uri = new URI("http://swde.el.eee.intern:8080/weatherdata-provider/rest/weatherdata/cityandyear?city={cityname}&year={value}"
						+ cityName + "/all");
		String cityNameAngepasst = cityName.replace("%20", " ");
		HttpRequest req = HttpRequest.newBuilder(uri).header("Accept", CONTENT_TYPE).GET().build();
		HttpResponse<String> res = client.send(req, BodyHandlers.ofString());

		if (res.statusCode() == 200) {
			double temperatur;
			double luftdruck;
			double feuchtigkeit;
			Timestamp time;
			ArrayNode node = (ArrayNode) mapper.readTree(res.body());

			try {
				for (int i = 0; i < node.size(); i++) {
					String data = null;
					wd = new Wetterdaten();
					data = node.get(i).findValue("data").asText();
					String[] parts = data.split("#");
					temperatur = Double.parseDouble(parts[9].substring(28));
					luftdruck = Double.parseDouble(parts[10].substring(9));
					feuchtigkeit = Double.parseDouble(parts[11].substring(9));
					time = Timestamp.valueOf(parts[0].substring(17));
					city.setName((parts[3].substring(10)));
					city.setPlz(Integer.parseInt(parts[2].substring(9)));

					for (int g = 0; g < cityListe.size(); g++) {
						if (city.getPlz() == 0) {
							city.setName(cityNameAngepasst);
							city.setPlz(cityListe.get(g).getPlz());
							g = cityListe.size();
						}
					}
					wd.setTemperatur(temperatur);
					wd.setLuftdruck(luftdruck);
					wd.setFeuchtigkeit(feuchtigkeit);
					wd.setTime(time);
					wd.setCity(city);
					wdList.add(wd);
					temperatur = -1;
					luftdruck = -1;
					feuchtigkeit = -1;
					time = Timestamp.valueOf("2022-05-08 12:00:00");
					city = new City();
				}
			} catch (Exception e) {
				 LOGREADER.error("ERROR: ", e);
				e.printStackTrace();
			}

			System.out.println("Groesse der WDD Liste " + cityName + ": " + wdList.size());

			return wdList;
		} else {
			throw new Exception("Uebertragung fehlgeschlagen [Status Code =" + res.statusCode() + "]");
		}
	}

}