package ch.hslu.swde.wda.persister;

import java.sql.Timestamp;
import java.util.List;

import ch.hslu.swde.wda.domain.City;
import ch.hslu.swde.wda.domain.LastUpdate;
import ch.hslu.swde.wda.domain.Mitarbeiter;
import ch.hslu.swde.wda.domain.Wetterdaten;

public interface PersisterInterface {

	void citySpeichern(List<City> cityListe) throws Exception;

	void mitarbeterSpeichern(Mitarbeiter mitarbeiter) throws Exception;

	void lastUptadeSpeichernOderAktualisieren(LastUpdate lastUpdate) throws Exception;

	void wetterdatenSpeichern(List<Wetterdaten> wetterdatenListe) throws Exception;

	void mitarbeiterAktualisieren(Mitarbeiter mitarbeiter) throws Exception;

	void mitarbeiterLöschen(Mitarbeiter mitarbeiter) throws Exception;

	void wetterdatenLöschen(Wetterdaten wetterdaten) throws Exception;

	List<City> alleCitys() throws Exception;

	List<Mitarbeiter> alleMitarbeiter() throws Exception;

	List<Wetterdaten> alleWetterdaten() throws Exception;

	Mitarbeiter maFindenMitID(int id) throws Exception;

	City cityFindenNachName(String name) throws Exception;

	Mitarbeiter maFindenMitEmailUndPasswort(String email, String passwort) throws Exception;

	LastUpdate lastUptadeFinden() throws Exception;

	// A02 Wie sehen die Temperatur, Luftdruck und Feuchtigkeit für eine angegebene
	// Ortschaft während einer angegebenen Zeitperiode aus?
	// A03 Wie gross waren die Durchschnittswerte für Temperatur, Luftdruck und
	// Feuchtigkeit für die angegebene Ortschaft in einer angegebenen Zeitperiode?
	// A04 Wie sahen die Maximal- und Minimalwerte für Temperatur, Luftdruck und
	// Feuchtigkeit für eine angegebene Ortschaft während einer angegebenen
	// Zeitperiode aus?
	List<Wetterdaten> TFLFinden(String cityName, Timestamp start, Timestamp end) throws Exception;

	// A05 Wie sahen die Maximal- und Minimalwerte für Temperatur, Luftdruck und
	// Feuchtigkeit über alle Ortschaften zu einem bestimmten Zeitpunkt aus?
	List<Wetterdaten> TLFMinMaxFinden(Timestamp time) throws Exception;
}
