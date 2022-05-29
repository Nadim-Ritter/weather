package ch.hslu.swde.wda.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.swde.g02wda.reader.client.ReaderClient;
import ch.hslu.swde.wda.domain.City;
import ch.hslu.swde.wda.domain.LastUpdate;
import ch.hslu.swde.wda.domain.Mitarbeiter;
import ch.hslu.swde.wda.domain.Wetterdaten;
import ch.hslu.swde.wda.persister.PersisterImpl;

public final class VerwaltungImpl implements Verwaltung {

	private static Logger LOGBusiness = LogManager.getLogger(VerwaltungImpl.class);

	private PersisterImpl persister = new PersisterImpl();
	private ReaderClient readerClient = new ReaderClient();

	/**
	 * Standard Konstruktor der Klasse VerwaltungImpl.
	 */
	public VerwaltungImpl() {

	}

	/**
	 * Parametrisierter Konstruktor der Klasse VerwaltungImpl.
	 */
	public VerwaltungImpl(PersisterImpl persister, ReaderClient readerClient) {
		this.persister = persister;
		this.readerClient = readerClient;
	}

	/**
	 * Die Methode ruft MitarbeiterHinzufuegen auf.
	 * 
	 * @param mitarbeiter
	 */
	@Override
	public void neuerMitarbeiterHinzufügen(Mitarbeiter mitarbeiter) throws Exception {
		LOGBusiness.info("VerwaltungImpl: Aufruf von Methode neuerMitarbeiterHinzufügen'");
		persister.mitarbeterSpeichern(mitarbeiter);

	}

	/**
	 * Die Methode ruft MitarbeiterAktualisieren' auf
	 * 
	 * @param mitarbeiter
	 */
	@Override
	public void mitarbeiterAktualisieren(Mitarbeiter mitarbeiter) throws Exception {
		LOGBusiness.info("VerwaltungImpl: Aufruf von Methode MitarbeiterAktualisieren");
		persister.mitarbeiterAktualisieren(mitarbeiter);
	}

	/**
	 * Die Methode ruft MitarbeiterLöschen' auf
	 * 
	 * @param mitarbeiter
	 */
	@Override
	public void mitarbeiterLöschen(Mitarbeiter mitarbeiter) throws Exception {
		LOGBusiness.info("VerwaltungImpl: Aufruf von Methode MitarbeiterLöschen");
		persister.mitarbeiterLöschen(mitarbeiter);

	}

	/**
	 * Die Methode ruft MaFindenMitEmailUndPasswort auf
	 * 
	 * @param email
	 * @param passwort
	 * @return mitarbeiter
	 */
	@Override
	public Mitarbeiter maFindenMitEmailUndPasswort(String email, String passwort) throws Exception {
		LOGBusiness.info("VerwaltungImpl: Aufruf von Methode MaFindenMitEmailUndPasswort ");
		Mitarbeiter mitarbeiterGefunden = persister.maFindenMitEmailUndPasswort(email, passwort); /* !!!! */
		return mitarbeiterGefunden;
	}

	/**
	 * Die Methode ruft MaFindenMitID auf
	 * 
	 * @param id
	 * @return mitarbeiter
	 */
	@Override
	public Mitarbeiter maFindenMitID(int id) throws Exception {
		LOGBusiness.info("VerwaltungImpl: Aufruf von Methode MaFindenMitID ");
		Mitarbeiter mitarbeiterGefundenNachId = persister.maFindenMitID(id);
		return mitarbeiterGefundenNachId;

	}

	/**
	 * Die Methode ruft AlleMitarbeiter()' auf
	 * 
	 * @return List<Mitarbeiter>
	 */
	@Override
	public List<Mitarbeiter> alleMitarbeiter() throws Exception {
		LOGBusiness.info("VerwaltungImpl: Aufruf von Methode AlleMitarbeiter()");
		List<Mitarbeiter> alleMitarbeiter = persister.alleMitarbeiter();
		return alleMitarbeiter;

	}

	/**
	 * Die Methode ruft AlleCitys() auf
	 * 
	 * @return List<City>
	 */
	@Override
	public List<City> alleCitysFinden() throws Exception {
		LOGBusiness.info("VerwaltungImpl: Aufruf von Methode AlleCitys()");
		List<City> cityListe = persister.alleCitys();

		return cityListe;
	}

	/**
	 * Die Methode ruft TFLFinden auf
	 * 
	 * @param cityName
	 * @param start
	 * @param end
	 * @return List<Wetterdaten>
	 */
	@Override
	public List<Wetterdaten> TLFFinden(String cityName, Timestamp start, Timestamp end) throws Exception {
		LOGBusiness.info("VerwaltungImpl: Aufruf von Methode TFLFinden");
		List<Wetterdaten> TFLFinden = persister.TFLFinden(cityName, start, end);

		return TFLFinden;
	}

	/**
	 * Die Methode ruft TLFFindenDurchschnitt auf und errechnet die
	 * Durchschnittswerte
	 * 
	 * @param cityName
	 * @param start
	 * @param end
	 */
	@Override
	public List<Wetterdaten> TLFDurchschnittFinden(String cityName, Timestamp start, Timestamp end) throws Exception {
		LOGBusiness.info("VerwaltungImpl: Aufruf von Methode TLFDurchschnittFinden");

		List<Wetterdaten> wetterdatenListe = new ArrayList<>();
		try {
			wetterdatenListe = persister.TFLFinden(cityName, start, end);
			if (wetterdatenListe.size() > 0) {
				LOGBusiness.info("VerwaltungImpl: Werte wurden gefunden!");
			} else {
				LOGBusiness.info("VerwaltungImpl: Keine Werte gefunden!");
			}

		} catch (Exception e) {
			LOGBusiness.error("VerwaltungImpl: Fehler beim Finden der Werte!", e);
			System.out.println("\nWerte konnten nicht gefunden werden!");
		}

		return wetterdatenListe;
	}

	/**
	 * Die Methode ruft TLFFindenMinMax auf und errechnet die Minimal und
	 * Maximalwerte
	 * 
	 * @param cityName
	 * @param start
	 * @param end
	 */
	@Override
	public List<Wetterdaten> TLFMinMaxFinden(String cityName, Timestamp start, Timestamp end) throws Exception {
		LOGBusiness.info("VerwaltungImpl: Aufruf von Methode TLFFindenMinMax");

		List<Wetterdaten> wetterDatenListe = new ArrayList<>();
		try {
			wetterDatenListe = persister.TFLFinden(cityName, start, end);
			if (wetterDatenListe.size() > 0) {
				LOGBusiness.info("VerwaltungImpl: Werte wurden gefunden!");
			} else {
				LOGBusiness.info("VerwaltungImpl: Keine Werte gefunden!");
			}

		} catch (Exception e) {
			LOGBusiness.error("VerwaltungImpl: Fehler beim Finden der Werte!", e);
			System.out.println("\nWerte konnten nicht gefunden werden!");
		}

		return wetterDatenListe;
	}

	/**
	 * Die Methode ruft TLFFindenMinMaxAlleCity auf und errechnet die Minimal und
	 * Maximalwerte
	 * 
	 * @param time
	 */
	@Override
	public List<Wetterdaten> TLFMinMaxAlleCitysFinden(Timestamp time) throws Exception {
		LOGBusiness.info("BusinessImpl: Aufruf von Methode TLFFindenMinMaxAlleCity");
		List<Wetterdaten> wetterdatenListe = new ArrayList<>();
		try {
			wetterdatenListe = persister.TLFMinMaxFinden(time);
			if (wetterdatenListe.size() > 0) {
				LOGBusiness.info("VerwaltungImpl: Werte wurden gefunden!");
			} else {
				LOGBusiness.info("VerwaltungImpl: Keine Werte gefunden!");
			}

		} catch (Exception e) {
			LOGBusiness.error("VerwaltungImpl: Fehler beim Finden der Werte!", e);
			System.out.println("\nWerte konnten nicht gefunden werden!");
		}

		return wetterdatenListe;
	}

	/**
	 * Helpermethode zum finden von max temperatur
	 */
	public static double maxTemperaturvonListe(List<Wetterdaten> list) {
		double max = -200;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getTemperatur() > max) {
				max = list.get(i).getTemperatur();
			}
		}
		return max;
	}

	/**
	 * Helpermethode zum finden von & max feuchtigkeit
	 */
	public static double maxFeuchtigkeitswertVonListe(List<Wetterdaten> list) {
		double max = -200;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getFeuchtigkeit() > max) {
				max = list.get(i).getFeuchtigkeit();
			}
		}
		return max;
	}

	/**
	 * Helpermethode zum finden von max Luftdruck
	 */
	public static double maxLuftdruckVonListe(List<Wetterdaten> list) {
		double max = -200;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getLuftdruck() > max) {
				max = list.get(i).getLuftdruck();
			}
		}
		return max;
	}

	/**
	 * Helpermethode zum finden von min Temperatur
	 */
	public static double minTemperaturVonListe(List<Wetterdaten> list) {
		double min = 1000;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getTemperatur() < min) {
				min = list.get(i).getTemperatur();
			}
		}
		return min;
	}

	/**
	 * Helpermethode zum finden von min Feuchtigkeit
	 */
	public static double minFeuchtigkeitVonListe(List<Wetterdaten> list) {
		double min = 1000;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getFeuchtigkeit() < min) {
				min = list.get(i).getFeuchtigkeit();
			}
		}
		return min;
	}

	/**
	 * Helpermethode zum Finden von min Luftdruck
	 */
	public static double minLuftdruckVonListe(List<Wetterdaten> list) {
		double min = 100000;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getLuftdruck() < min) {
				min = list.get(i).getLuftdruck();
			}
		}
		return min;
	}

	/**
	 * Helpermethode zum Finden von DurchschnittsTemperatur
	 */
	public static double durchschnittsTemperaturVonListe(List<Wetterdaten> list) {
		Double durchschnitt = 0.0;
		for (int i = 0; i < list.size(); i++) {
			durchschnitt = durchschnitt + list.get(i).getTemperatur();
		}
		durchschnitt = durchschnitt / list.size();
		return durchschnitt;
	}

	/**
	 * Helpermethode zum Finden von durchschnittlicher Feuchtigkeit
	 */
	public static double durchschnittsFeuchtigkeitswertVonListe(List<Wetterdaten> list) {
		Double durchschnitt = 0.0;
		for (int i = 0; i < list.size(); i++) {
			durchschnitt = durchschnitt + list.get(i).getFeuchtigkeit();
		}
		durchschnitt = durchschnitt / list.size();
		return durchschnitt;
	}

	/**
	 * Helpermethode zum finden von durchschnittlichem Luftdruck
	 */
	public static double durchschnittsLuftdruckVonListe(List<Wetterdaten> list) {
		Double durchschnitt = 0.0;
		for (int i = 0; i < list.size(); i++) {
			durchschnitt = durchschnitt + list.get(i).getLuftdruck();
		}
		durchschnitt = durchschnitt / list.size();
		return durchschnitt;
	}

	/**
	 * Die Methode ruft die Methode AlleCitysVonWebserver auf
	 * 
	 * @return List<City>
	 */
	@Override
	public List<City> alleCitysVonWebserverFinden() throws Exception {
		List<City> citysVonWebserver = readerClient.findeCities();
		return citysVonWebserver;
	}

	/**
	 * Die Methode ruf die Methode AlleWetterdatenVonWebserver auf.
	 * 
	 * @return List<Wetterdaten>
	 */
	@Override
	public List<Wetterdaten> alleWetterdatenVonWebserverFinden(String cityName) throws Exception {
		List<Wetterdaten> wetterdatenVonWebservice = readerClient.findeWetterdaten(cityName);
		return wetterdatenVonWebservice;
	}

	/**
	 * Die Methode holt die aktuellen Wetterdaten vom WebService und speichert sie
	 * in die Datenbank. Zur Performance-Optimierung wird nur das Delta vom
	 * LastUpdate bis heute geladen.
	 */
	@Override
	public void wetterdatenVonWebserverAktualisieren() throws Exception {
		List<Wetterdaten> wetterdatenVonWebserver = new ArrayList<>();
		List<Wetterdaten> wetterdatenSpeichern = new ArrayList<>();
		List<City> citysVonWebserver = new ArrayList<>();

		LastUpdate lastUpdate = new LastUpdate();
		if (persister.lastUptadeFinden() != null) {
			lastUpdate = persister.lastUptadeFinden();
		} else {
			lastUpdate = new LastUpdate();
		}
		citysVonWebserver = readerClient.findeCities();
		persister.citySpeichern(citysVonWebserver);

		for (City c : citysVonWebserver) {
			c.setName(c.getName().replace(" ", "%20"));
			wetterdatenVonWebserver.addAll(readerClient.findeWetterdaten(c.getName()));
		}

		for (Wetterdaten f : wetterdatenVonWebserver) {
			if (lastUpdate.getZeitLastUptade().before(f.getTime())) {
				wetterdatenSpeichern.add(f);
			}
		}

		lastUpdate.setZeitLastUptade(wetterdatenSpeichern.get(wetterdatenSpeichern.size() - 1).getTime());
		persister.wetterdatenSpeichern(wetterdatenSpeichern);
		persister.lastUptadeSpeichernOderAktualisieren(lastUpdate);
	}
}