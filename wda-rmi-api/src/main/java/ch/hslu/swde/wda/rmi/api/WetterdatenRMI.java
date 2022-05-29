package ch.hslu.swde.wda.rmi.api;

import java.rmi.Remote;
import java.sql.Timestamp;
import java.util.List;

import ch.hslu.swde.wda.domain.City;
import ch.hslu.swde.wda.domain.Mitarbeiter;
import ch.hslu.swde.wda.domain.Wetterdaten;

public interface WetterdatenRMI extends Remote {
	String RMI_WDA = "BUSINESS_RMI_WDA";

	/**
	 * Diese Methode fügt einen Mitarbeiter hinzu.
	 *
	 * @param person
	 * @return
	 * @throws Exception
	 */
	void neuerMitarbeiterHinzufügen(Mitarbeiter mitarbeiter) throws Exception;

	/**
	 * Dies Methode aktualisiert einen Mitarbeiter
	 *
	 * @param person
	 * @return
	 * @throws Exception
	 */
	void mitarbeiterAktualisieren(Mitarbeiter mitarbeiter) throws Exception;

	/**
	 * Diese Methode löscht einen MA aus der Datenbank
	 *
	 * @param person
	 * @return
	 * @throws Exception
	 */
	void mitarbeiterLöschen(Mitarbeiter mitarbeiter) throws Exception;

	/**
	 * Diese Methode zeigt die MA mit Email und Passwörter
	 *
	 *
	 * @param name
	 * @param vorname
	 * @return
	 * @throws Exception
	 */
	Mitarbeiter maFindenMitEmailUndPasswort(String email, String passwort) throws Exception;

	/**
	 * Diese Methode gibt die PersonenNummer an
	 *
	 * @param personNummer
	 * @return
	 * @throws Exception
	 */
	Mitarbeiter maFindenMitID(int id) throws Exception;

	/**
	 * Diese Methode zeigt alle MA an
	 *
	 *
	 * @param name
	 * @param vorname
	 * @return
	 * @throws Exception
	 */
	List<Mitarbeiter> alleMitarbeiter() throws Exception;

	/**
	 * Diese Methode zeigt alle Städte an.
	 *
	 *
	 * @throws Exception
	 */
	List<City> alleCitysFinden() throws Exception;

	/**
	 * Diese Methode findet den Durchscnitt TLF der Städte.
	 * 
	 * @throws Exception
	 */
	List<Wetterdaten> TLFFinden(String cityName, Timestamp start, Timestamp end) throws Exception;

	/**
	 * Diese Methode findet die TLF der Städte
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Wetterdaten> TLFDurchschnittFinden(String cityName, Timestamp start, Timestamp end) throws Exception;

	/**
	 * Diese Methode findet die TLF MIN MAX der Städte
	 * 
	 * @throws Exception
	 */
	List<Wetterdaten> TLFMinMaxFinden(String cityName, Timestamp start, Timestamp end) throws Exception;

	/**
	 * Diese Methode findet die TLF MIN MAX aller Städte
	 * 
	 * @throws Exception
	 */
	List<Wetterdaten> TLFMinMaxAlleCitysFinden(Timestamp time) throws Exception;

	/**
	 * Die Methode findet alle Städte im Web Server
	 * 
	 * @throws Exception
	 */
	List<City> alleCitysVonWebserverFinden() throws Exception;

	/**
	 * Die Methode findet alle Wetterdaten im Web Server
	 * 
	 * @throws Exception
	 */
	List<Wetterdaten> alleWetterdatenVonWebserver(String cityName) throws Exception;

	/**
	 * Diese Methode ladet alle Daten von Webserver
	 * 
	 * @throws Exception
	 */
	void WetterdatenAktualisierenVonWebserver() throws Exception;

}
