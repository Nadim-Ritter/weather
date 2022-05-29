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
package ch.hslu.swde.wda.ui;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.swde.wda.business.VerwaltungImpl;
import ch.hslu.swde.wda.domain.City;
import ch.hslu.swde.wda.domain.Mitarbeiter;
import ch.hslu.swde.wda.domain.Wetterdaten;
import ch.hslu.swde.wda.rmi.api.WetterdatenRMI;

public final class UI {

	private static Logger loggerUI = LogManager.getLogger(UI.class);

	/* Menue-Konstanten */
	private static final String LOGINMENU_1_1_0 = "Login [1]\nBeenden [-1]";
	private static final String ABFRAGEMENU_1_2_1 = "Mitarbeiter Management [1]\nWetter-Daten abfragen [2]\nApplikation Beenden [-1]";
	private static final String MITARBEITERMENU_2_1_1 = "Mitarbeiter erstellen [1]\nMitarbeiter finden [2]\n"
			+ "Mitarbeiter ändern [3]\nMitarbeiter löschen [4]\nZurück [0]";
	private static final String WETTERDATENMENU_2_2_1 = "Alle Städte [1]\n"
			+ "Temperatur & Luftdruck & Feuchtigkeit für Ort & Zeitperiode [2]\n"
			+ "Durchschnitts Temperatur & Luftdruck & Feuchtigkeit für Ort & Zeitperiode [3]\n"
			+ "Maximal/Minimal Temperatur & Luftdruck & Feuchtigkeit für Ort & Zeitperiode [4]\n"
			+ "Maximal/Minimal Temperatur & Luftdruck & Feuchtigkeit für alle Orte & bestimmter Zeitpunkt [5]\n"
			+ "Alle Wetterdaten / Städte vom WebService importieren [6]\n" + "zurück [0]";
	private static final String DATENEXPORTMENU_2_2_2 = "Daten als CSV-Datei exportieren [1]\nZurück [0]";
	/**
	 * Anwendungslogik-Komponente
	 */
	private static String menu = LOGINMENU_1_1_0;
	private List<Wetterdaten> listeWetterdaten = new ArrayList<>();
	private List<City> listeCity = new ArrayList<>();
	private WetterdatenRMI verwaltung;

	/**
	 * Privater Konstruktor.
	 */

	public void execute() {
		Mitarbeiter admin = new Mitarbeiter("admin@admin.ch", "Admin123", "Admin", "Admin");

		try {
			if ((mitarbeiterFinden(admin)).equals(new Mitarbeiter())) {
				verwaltung.neuerMitarbeiterHinzufügen(admin);
			}
		} catch (Exception e) {
			loggerUI.error("UI: Fehler beim Erstellen des Admins!", e);
		}

		loggerUI.info("Start der 'execute()-Methode in Klasse UI");

		int wahl = -2;
		menu = LOGINMENU_1_1_0;

		do {

			if (menu.equals(LOGINMENU_1_1_0)) {

				showMenu();
				wahl = eingabeEinlesen();

				if (wahl == 1) {
					// LOGIN
					String feedback = "";
					feedback = login();
					if (feedback.equals("Login erfolgreich!")) {
						menu = ABFRAGEMENU_1_2_1;
					} else {
						menu = LOGINMENU_1_1_0;
					}
				} else if (wahl == -1) {
					System.out.println(
							"Programm wurde beendet!\nSollten Sie weiterarbeiten wollen, starten Sie die App erneut.");
				}
			}

			else if (menu.equals(ABFRAGEMENU_1_2_1)) {
				showMenu();
				wahl = eingabeEinlesen();
				// MITARBEITERMANAGEMENT
				if (wahl == 1) {
					menu = MITARBEITERMENU_2_1_1;
				} else if (wahl == 2) {
					menu = WETTERDATENMENU_2_2_1;
				} else if (wahl == -1) {
					System.out.println(
							"Programm wurde beendet!\nSollten Sie weiterarbeiten wollen, starten Sie die App erneut.");
				}
			}

			else if (menu.equals(MITARBEITERMENU_2_1_1)) {
				showMenu();
				wahl = eingabeEinlesen();
				if (wahl == 1) {
					// MITARBEITER HINZUFÜGEN
					neuerMitarbeiterHinzufügen();
				} else if (wahl == 2) {
					// MITARBEITER FINDEN
					alleMitarbeiterFinden();
				} else if (wahl == 3) {
					// MITARBEITER ÄNDERN
					mitarbeiterAktualisieren();
				} else if (wahl == 4) {
					// MITARBEITER LÖSCHEN
					mitarbeiterLöschen();
				} else if (wahl == 0) {
					menu = ABFRAGEMENU_1_2_1;
				}
			}

			else if (menu.equals(WETTERDATENMENU_2_2_1)) {
				showMenu();
				wahl = eingabeEinlesen();
				// Wetterdaten MANAGEMENT
				if (wahl == 1) {
					// ALLE STÄDTE
					listeCity = alleCitysFinden();
					printListeCity(listeCity);
					menu = DATENEXPORTMENU_2_2_2;
					showMenu();
					wahl = eingabeEinlesen();
					if (wahl == 1) {
						exportCitysCSV(listeCity);
						menu = WETTERDATENMENU_2_2_1;
					} else if (wahl == 0) {
						menu = WETTERDATENMENU_2_2_1;
					}
				} else if (wahl == 2) {
					// Temperaturbereich
					listeWetterdaten = TLFFinden();
					printListeWetterdaten(listeWetterdaten);
					menu = DATENEXPORTMENU_2_2_2;
					showMenu();
					wahl = eingabeEinlesen();
				}

				else if (wahl == 0) {
					menu = WETTERDATENMENU_2_2_1;
				}
			} else if (wahl == 3) {
				// Durchscnittsbereich
				listeWetterdaten = TLFDurchschnittFinden();
				menu = DATENEXPORTMENU_2_2_2;
				showMenu();
				wahl = eingabeEinlesen();
			} else if (wahl == 0) {
				menu = WETTERDATENMENU_2_2_1;
			}

			else if (wahl == 4) {
				// Min & Max Bereich
				listeWetterdaten = TLFMinMaxFinden();
				menu = DATENEXPORTMENU_2_2_2;
				showMenu();
				wahl = eingabeEinlesen();

			} else if (wahl == 0) {
				menu = WETTERDATENMENU_2_2_1;
			} else if (wahl == 5) {
				// Min & Max Punkt
				listeWetterdaten = TLFMinMaxAlleCitysFinden();
				menu = DATENEXPORTMENU_2_2_2;
				showMenu();
				wahl = eingabeEinlesen();
			}

			else if (wahl == 0) {
				menu = WETTERDATENMENU_2_2_1;
			} else if (wahl == 6) {
				// Min & Max Punkt
				wetterdatenVonWebserverAktualisieren();
				menu = DATENEXPORTMENU_2_2_2;
				showMenu();
				wahl = eingabeEinlesen();

			} else if (wahl == 0) {
				menu = WETTERDATENMENU_2_2_1;
			}

			else if (wahl == 0) {
				menu = ABFRAGEMENU_1_2_1;
			}

		} while (wahl != -1);
	}

	/**
	 * Login Methode
	 * 
	 * @return zeigt Lolgin Feedback
	 */
	private String login() {
		Scanner sc = new Scanner(System.in);
		boolean login = false;
		Mitarbeiter emCreated = leseMitarbeiterLogin();
		Mitarbeiter emFound = new Mitarbeiter();
		try {
			emFound = mitarbeiterFinden(emCreated);
			if (emFound == null) {
				login = false;
				loggerUI.info("UI: Mitarbeiter nicht in der Datenbank vorhanden!");
			} else if (emFound.getEmail().equals(emCreated.getEmail())
					&& emFound.getPasswort().equals(emCreated.getPasswort())) {
				login = true;
			} else {
				login = false;
			}
		} catch (Exception e) {
			loggerUI.error("UI: Fehler beim finden des Mitarbeiters!", e);
			System.out.println("\nFehler beim finden des Mitarbeiters!");
		}

		return login != true ? "Login fehlgeschlagen!" : "Login erfolgreich!";
	}

	/**
	 * Methode zum einen neuen Mitarbeiter in die Datenbank zu hinzufügen
	 * 
	 * @return Nachricht Mitarbeiter wurde hinzugefügt.
	 */
	private String neuerMitarbeiterHinzufügen() {
		Scanner sc = new Scanner(System.in);
		Mitarbeiter emCreated = mitarbeiterDatenLesen();
		try {
			verwaltung.neuerMitarbeiterHinzufügen(emCreated);
			loggerUI.info("UI: Mitarbeiter wurde gespeichert!");
		} catch (Exception e) {
			loggerUI.error("UI: Fehler beim speichern des Mitarbeiters!", e);
		}

		return "Mitarbeiter wurde gespeichert:\n" + emCreated + "";
	}

	/**
	 *
	 * Helpermethode um alle Mitarbeiter von DB anzuzeigen.
	 *
	 */
	private void alleMitarbeiterFinden() {
		try {
			List<Mitarbeiter> alleMitarbeiter = verwaltung.alleMitarbeiter();
			System.out.println("Alle Mitarbeiter: ");
			for (Mitarbeiter n : alleMitarbeiter) {
				System.out.println(n);
			}
		} catch (Exception e) {
			loggerUI.info("UI: Keine Mitarbeiter gefunden!");
		}
	}

	/**
	 *
	 * @param ee
	 * @return Mitarbeiter welcher in der DB gefunden wurden angeben
	 */
	private Mitarbeiter mitarbeiterFinden(Mitarbeiter ee) {
		Mitarbeiter emFound = new Mitarbeiter();
		try {
			emFound = verwaltung.maFindenMitEmailUndPasswort(ee.getEmail(), ee.getPasswort());
			if (emFound != null) {
				loggerUI.info("UI: Mitarbeiter wurde gefunden!");
			}

		} catch (Exception e) {
			loggerUI.error("UI: Fehler beim Laden!", e);
			System.out.println("\nDaten konnten nicht geladen werden!");
		}

		return emFound;
	}

	/**
	 * Method um MA DB zu aktualisieren
	 */
	private void mitarbeiterAktualisieren() {
		try {

			List<String> emailUndPasswort = mitarbeiterPasswortUndEmailLesen();
			Mitarbeiter emFromDb = verwaltung.maFindenMitEmailUndPasswort(emailUndPasswort.get(0),
					emailUndPasswort.get(1));
			Mitarbeiter emWithUpdates = mitarbeiterDatenLesen();

			if (!emFromDb.equals(new Mitarbeiter())) {
				emWithUpdates.setId(emFromDb.getId());
				verwaltung.mitarbeiterAktualisieren(emWithUpdates);
				loggerUI.info("UI: Mitarbeiter wurde akutalisiert!");
			} else {
				verwaltung.neuerMitarbeiterHinzufügen(emWithUpdates);
				loggerUI.info("UI: Da der Mitarbeiter nicht existiert hat, wurde er neu erstellt!");
			}

		} catch (Exception e) {
			loggerUI.error("UI: Fehler beim Update des Mitarbeiters!", e);
			System.out.println("\nMitarbeiter konnte nicht upgedated werden!");
		}
	}

	/**
	 * Methode um MA Objekte zu löschen
	 */
	private void mitarbeiterLöschen() {
		try {
			List<String> emailUndPasswort = mitarbeiterPasswortUndEmailLesen();
			Mitarbeiter emFromDb = verwaltung.maFindenMitEmailUndPasswort(emailUndPasswort.get(0),
					emailUndPasswort.get(1));

			verwaltung.mitarbeiterLöschen(emFromDb);
			loggerUI.info("UI: Mitarbeiter wurde gelöscht!");
			System.out.println("Gelöschter Mitarbeiter: \n" + emFromDb);
		} catch (Exception e) {
			loggerUI.error("UI: Fehler beim Löschen des Mitarbeiters!", e);
			System.out.println("\nMitarbeiter konnte nicht gelöscht werden!");
		}
	}

	/**
	 * Methode um alle Städte in der DB zu finden
	 * 
	 * @return zeigt Liste der Städte an.
	 */
	private List<City> alleCitysFinden() {
		try {
			listeCity = verwaltung.alleCitysFinden();
			loggerUI.info("UI: Städte wurden gefunden!");
		} catch (Exception e) {
			loggerUI.error("UI: Fehler beim Finden der Städte!", e);
			System.out.println("\nStädte konnten nicht gefunden werden!");
		}
		return listeCity;
	}

	/**
	 * Methode um Temperatur, Luftdruck und Feuchtigkeit einer Stadt und Zeitspanne
	 * aufzurufen.
	 * 
	 * @return zeigt Liste von Wetterdaten an.
	 */
	private List<Wetterdaten> TLFFinden() {
		List<Timestamp> listTimestamp = readTimestamp();

		String name = cityNameLesen();

		try {
			listeWetterdaten = verwaltung.TLFFinden(name, listTimestamp.get(0), listTimestamp.get(1));
			if (listeWetterdaten.size() == 0) {
				loggerUI.info("UI: DB enthält keine Werte!");
			} else {
				loggerUI.info("UI: Werte wurden gefunden!");
			}
		} catch (Exception e) {
			loggerUI.error("UI: Fehler beim Finden der Werte!", e);
			System.out.println("\nWerte konnten nicht gefunden werden!");
		}
		return listeWetterdaten;
	}

	/**
	 * Methode um TLF in den Citys und mit Zeit zu finden
	 *
	 */
	private List<Wetterdaten> TLFDurchschnittFinden() {
		List<Timestamp> listTimestamp = readTimestamp();

		String cityName = cityNameLesen();
		List<Wetterdaten> WetterdatenListe = new ArrayList<>();
		try {
			WetterdatenListe = verwaltung.TLFDurchschnittFinden(cityName, listTimestamp.get(0), listTimestamp.get(1));
			if (WetterdatenListe.size() > 0) {
				System.out.println("\nBerechnete Durchschnittstemperatur: "
						+ VerwaltungImpl.durchschnittsTemperaturVonListe(WetterdatenListe));
				System.out.println("\nBerechnete Durchschnittsfeuchtigkeit: "
						+ VerwaltungImpl.durchschnittsFeuchtigkeitswertVonListe(WetterdatenListe));
				System.out.println("\nBerechneter Durchschnittsluftdruck: "
						+ VerwaltungImpl.durchschnittsLuftdruckVonListe(WetterdatenListe));
			} else {
				System.out.println("\nKeine Werte gefunden.");
			}

		} catch (Exception e) {
			loggerUI.error("UI: Fehler beim Finden der Werte!", e);
			System.out.println("\nWerte konnten nicht gefunden werden!");
		}
		return WetterdatenListe;
	}

	/**
	 * Methode um min und max TLF einer Stadt und Zeitspanne zu finden
	 * 
	 * @return zeigt Liste von Wetterdaten an
	 */
	private List<Wetterdaten> TLFMinMaxFinden() {
		List<Timestamp> listTimestamp = readTimestamp();

		String cityName = cityNameLesen();
		List<Wetterdaten> WetterdatenListe = new ArrayList<>();
		try {
			WetterdatenListe = verwaltung.TLFMinMaxFinden(cityName, listTimestamp.get(0), listTimestamp.get(1));
			if (WetterdatenListe.size() > 0) {
				System.out.println("\nMaximale Temperatur: " + VerwaltungImpl.maxTemperaturvonListe(WetterdatenListe));
				System.out.println("\nMinimale Temperatur: " + VerwaltungImpl.minTemperaturVonListe(WetterdatenListe));
				System.out.println(
						"\nMaximale Feuchtigkeit: " + VerwaltungImpl.maxFeuchtigkeitswertVonListe(WetterdatenListe));
				System.out.println(
						"\nMinimale Feuchtigkeit: " + VerwaltungImpl.minFeuchtigkeitVonListe(WetterdatenListe));
				System.out.println("\nMaximaler Luftdruck: " + VerwaltungImpl.maxLuftdruckVonListe(WetterdatenListe));
				System.out.println("\nMinimaler Luftdruck: " + VerwaltungImpl.minLuftdruckVonListe(WetterdatenListe));
			} else {
				System.out.println("\nKeine Werte gefunden.");
			}

		} catch (Exception e) {
			loggerUI.error("UI: Fehler beim Finden der Werte!", e);
			System.out.println("\nWerte konnten nicht gefunden werden!");
		}

		return WetterdatenListe;
	}

	/**
	 * Methode um alle min max TLF von Städten zu finden
	 * 
	 * @return zeigt Liste von Wetterdaten an.
	 * @throws Exception
	 */
	private List<Wetterdaten> TLFMinMaxAlleCitysFinden() {
		Timestamp timestamp = readOneTimeStamp();
		List<Wetterdaten> WetterdatenListe = new ArrayList<>();
		try {
			WetterdatenListe = verwaltung.TLFMinMaxAlleCitysFinden(timestamp);
			if (WetterdatenListe.size() > 0) {
				System.out.println("\nMaximale Temperatur: " + VerwaltungImpl.maxTemperaturvonListe(WetterdatenListe));
				System.out.println("\nMinimale Temperatur: " + VerwaltungImpl.minTemperaturVonListe(WetterdatenListe));
				System.out.println(
						"\nMaximale Feuchtigkeit: " + VerwaltungImpl.maxFeuchtigkeitswertVonListe(WetterdatenListe));
				System.out.println(
						"\nMinimale Feuchtigkeit: " + VerwaltungImpl.minFeuchtigkeitVonListe(WetterdatenListe));
				System.out.println("\nMaximaler Luftdruck: " + VerwaltungImpl.maxLuftdruckVonListe(WetterdatenListe));
				System.out.println("\nMinimaler Luftdruck: " + VerwaltungImpl.minLuftdruckVonListe(WetterdatenListe));
			} else {
				System.out.println("\nKeine Werte gefunden.");
			}

		} catch (Exception e) {
			loggerUI.error("UI: Fehler beim Finden der Werte!", e);
			System.out.println("\nWerte konnten nicht gefunden werden!");
		}

		return WetterdatenListe;
	}

	private void wetterdatenVonWebserverAktualisieren() {
		try {
			verwaltung.WetterdatenAktualisierenVonWebserver();
		} catch (Exception e) {
			loggerUI.error("UI: Fehler beim Finden der Werte!", e);
			System.out.println("\nWerte konnten nicht gefunden werden!");
		}
	}

	/**
	 * Methode um Stadt als CSV File zu exportieren
	 * 
	 * @param listeWetterdaten;
	 */
	private void exportWetterdatenCSV(List<Wetterdaten> listeWetterdaten) {

		final char TRENNZEICHEN = ';';
		System.out.println("Benennen Sie die Datei:\n");
		Scanner sc = new Scanner(System.in);
		String fileName = sc.nextLine();

		File file = new File(System.getProperty("user.home") + File.separator + fileName + ".csv");

		try (PrintWriter printer = new PrintWriter(file)) {

			StringBuilder sBuilder = new StringBuilder();

			sBuilder.append("Wetterdaten-ID").append(TRENNZEICHEN).append("Luftdruck").append(TRENNZEICHEN)
					.append("Feuchtigkeit").append(TRENNZEICHEN).append("Temperatur").append(TRENNZEICHEN)
					.append("Zeitpunkt").append(TRENNZEICHEN).append("Stadt-ID").append(TRENNZEICHEN).append("Name")
					.append(TRENNZEICHEN).append("PLZ");

			printer.println(sBuilder.toString());
			sBuilder.setLength(0);
			for (Wetterdaten wd : listeWetterdaten) {
				sBuilder.append(wd.getId()).append(TRENNZEICHEN).append(wd.getLuftdruck()).append(TRENNZEICHEN)
						.append(wd.getFeuchtigkeit()).append(TRENNZEICHEN).append(wd.getTemperatur())
						.append(TRENNZEICHEN).append(wd.getTime()).append(TRENNZEICHEN).append(wd.getCity().getId())
						.append(TRENNZEICHEN).append(wd.getCity().getName()).append(TRENNZEICHEN)
						.append(wd.getCity().getPlz());

				printer.println(sBuilder.toString());
				sBuilder.setLength(0);
			}

			System.out.println("\nDaten exportiert: " + file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			loggerUI.error("UI: Fehler beim Export von WetterDaten zu CSV!", e);
			System.out.println("\nFehler beim Export von WetterDaten zu CSV!");
		}
	}

	/**
	 * Methode um Durchschnitt als CSV File zu exportieren
	 * 
	 * @param listeWetterdaten
	 */
	private void exportDurchschnittsWertCSV(List<Wetterdaten> listeWetterdaten) {
		double durchschnittsTemperatur = VerwaltungImpl.durchschnittsTemperaturVonListe(listeWetterdaten);
		double durchschnittsFeuchtigkeit = VerwaltungImpl.durchschnittsFeuchtigkeitswertVonListe(listeWetterdaten);
		double durchschnittsLuftdruck = VerwaltungImpl.durchschnittsLuftdruckVonListe(listeWetterdaten);
		final char TRENNZEICHEN = ';';
		System.out.println("Benennen Sie die Datei:\n");
		Scanner sc = new Scanner(System.in);
		String fileName = sc.nextLine();

		File file = new File(System.getProperty("user.home") + File.separator + fileName + ".csv");

		try (PrintWriter printer = new PrintWriter(file)) {

			StringBuilder sBuilder = new StringBuilder();

			sBuilder.append("Wetterdaten-ID").append(TRENNZEICHEN).append("Luftdruck").append(TRENNZEICHEN)
					.append("Feuchtigkeit").append(TRENNZEICHEN).append("Temperatur");

			printer.println(sBuilder.toString());
			sBuilder.setLength(0);
			for (Wetterdaten wd : listeWetterdaten) {
				sBuilder.append(wd.getId()).append(TRENNZEICHEN).append(durchschnittsLuftdruck).append(TRENNZEICHEN)
						.append(durchschnittsFeuchtigkeit).append(TRENNZEICHEN).append(durchschnittsTemperatur);

				printer.println(sBuilder.toString());
				sBuilder.setLength(0);
			}

			System.out.println("\nDaten exportiert: " + file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			loggerUI.error("UI: Fehler beim Export von WetterDaten zu CSV!", e);
			System.out.println("\nFehler beim Export von WetterDaten zu CSV!");
		}
	}

	/**
	 * Methode um min max range als ein CSV zu exportieren
	 * 
	 * @param listeWetterdaten
	 */
	private void exportMinMaxCSV(List<Wetterdaten> listeWetterdaten) {
		double maxTemperatur = VerwaltungImpl.maxTemperaturvonListe(listeWetterdaten);
		double minTemperatur = VerwaltungImpl.minTemperaturVonListe(listeWetterdaten);
		double maxFeuchtigkeit = VerwaltungImpl.maxFeuchtigkeitswertVonListe(listeWetterdaten);
		double minFeuchtigkeit = VerwaltungImpl.minFeuchtigkeitVonListe(listeWetterdaten);
		double maxLuftdruck = VerwaltungImpl.maxLuftdruckVonListe(listeWetterdaten);
		double minLuftdruck = VerwaltungImpl.minLuftdruckVonListe(listeWetterdaten);

		final char TRENNZEICHEN = ';';
		System.out.println("Benennen Sie die Datei:\n");
		Scanner sc = new Scanner(System.in);
		String fileName = sc.nextLine();

		File file = new File(System.getProperty("user.home") + File.separator + fileName + ".csv");

		try (PrintWriter printer = new PrintWriter(file)) {

			StringBuilder sBuilder = new StringBuilder();

			sBuilder.append("Wetterdaten-ID").append(TRENNZEICHEN).append("Min-Luftdruck").append(TRENNZEICHEN)
					.append("Max-Luftdruck").append(TRENNZEICHEN).append("Max-Feuchtigkeit").append(TRENNZEICHEN)
					.append("Min-Feuchtigkeit").append(TRENNZEICHEN).append("Min-Temperatur").append(TRENNZEICHEN)
					.append("Max-Temperatur");

			printer.println(sBuilder.toString());
			sBuilder.setLength(0);
			for (Wetterdaten wd : listeWetterdaten) {
				sBuilder.append(wd.getId()).append(TRENNZEICHEN).append(minLuftdruck).append(TRENNZEICHEN)
						.append(maxLuftdruck).append(TRENNZEICHEN).append(maxFeuchtigkeit).append(TRENNZEICHEN)
						.append(minFeuchtigkeit).append(TRENNZEICHEN).append(minTemperatur).append(TRENNZEICHEN)
						.append(maxTemperatur);

				printer.println(sBuilder.toString());
				sBuilder.setLength(0);
			}

			System.out.println("\nDaten exportiert: " + file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			loggerUI.error("UI: Fehler beim Export von WetterDaten zu CSV!", e);
			System.out.println("\nFehler beim Export von WetterDaten zu CSV!");
		}
	}

	/**
	 * Methode um Städte-Objekte als ein CSV zu exportieren
	 * 
	 * @param cityList
	 */
	private void exportCitysCSV(List<City> cityList) {
		final char TRENNZEICHEN = ';';
		System.out.println("Benennen Sie die Datei:\n");
		Scanner sc = new Scanner(System.in);
		String fileName = sc.nextLine();

		File file = new File(System.getProperty("user.home") + File.separator + fileName + ".csv");

		try (PrintWriter printer = new PrintWriter(file)) {

			StringBuilder sBuilder = new StringBuilder();

			sBuilder.append("City-ID").append(TRENNZEICHEN).append("Name").append(TRENNZEICHEN).append("PLZ");

			printer.println(sBuilder.toString());
			sBuilder.setLength(0);
			for (City ct : cityList) {
				sBuilder.append(ct.getId()).append(TRENNZEICHEN).append(ct.getName()).append(TRENNZEICHEN)
						.append(ct.getPlz());

				printer.println(sBuilder.toString());
				sBuilder.setLength(0);
			}
			System.out.println("\nDaten exportiert: " + file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			loggerUI.error("UI: Fehler beim Export von WetterDaten zu CSV!", e);
			System.out.println("\nFehler beim Export von WetterDaten zu CSV!");
		}
	}

	/**
	 * Helpermethode um Menus anzuzeigen
	 */
	private void showMenu() {
		System.out.println();
		System.out.println(menu);
		System.out.print("Ihre Wahl: ");
	}

	/**
	 * Helpermethode für Eingabe
	 * 
	 * @return Eingabe
	 */
	private int eingabeEinlesen() {

		Scanner sc = new Scanner(System.in);

		int eingabe = -2;

		List<Integer> werte = new ArrayList<>();

		switch (menu) {
		case LOGINMENU_1_1_0:
			werte = Arrays.asList(1, -1);
			break;

		case ABFRAGEMENU_1_2_1:
			werte = Arrays.asList(1, 2, -1);
			break;

		case MITARBEITERMENU_2_1_1:
			werte = Arrays.asList(1, 2, 3, 4, 0);
			break;

		case WETTERDATENMENU_2_2_1:
			werte = Arrays.asList(1, 2, 3, 4, 5, 6, 0);
			break;

		case DATENEXPORTMENU_2_2_2:
			werte = Arrays.asList(1, 0);
			break;
		default:

		}

		do {
			try {
				eingabe = sc.nextInt();

				if (!werte.contains(eingabe)) {
					Toolkit.getDefaultToolkit().beep();
					showMenu();
				}
			} catch (Exception e) {

				sc.nextLine();

				Toolkit.getDefaultToolkit().beep();
				showMenu();
			}

		} while (!werte.contains(eingabe));

		return eingabe;
	}

	/**
	 * Helpermethode um Mitarbeiter zu lesen
	 * 
	 * @return gibt gelesene MA Daten zurpück.
	 */
	private Mitarbeiter mitarbeiterDatenLesen() {
		Scanner sc = new Scanner(System.in);
		System.out.println("\nMitarbeiterangaben eingeben: \n");

		System.out.print(" Name:\t\t\t");
		String name = sc.nextLine();

		System.out.print(" Vorname:\t\t");
		String vorname = sc.nextLine();

		System.out.print(" Email:\t\t\t");
		String email = sc.nextLine();

		System.out.print(" Passwort:\t\t");
		String passwort = sc.nextLine();

		return new Mitarbeiter(email, passwort, name, vorname);
	}

	/**
	 * Helpermethode um email und passwort von MA zu erhalten
	 * 
	 * @return gelesene MA Daten
	 */
	private List<String> mitarbeiterPasswortUndEmailLesen() {
		Scanner sc = new Scanner(System.in);
		System.out.println("\nMitarbeiterangaben eingeben: \n");

		System.out.print(" Email:\t\t\t");
		String email = sc.nextLine();

		System.out.print(" Passwort:\t\t");
		String passwort = sc.nextLine();

		List<String> mailUndPasswort = new ArrayList<>();
		mailUndPasswort.add(email);
		mailUndPasswort.add(passwort);

		return mailUndPasswort;
	}

	/**
	 * Helpermethode um MA login zu lesen
	 * 
	 * @return gibt gelesene MA Daten zurück
	 */
	private Mitarbeiter leseMitarbeiterLogin() {
		Scanner sc = new Scanner(System.in);
		System.out.print(" Email:\t\t\t");
		String email = sc.nextLine();

		System.out.print(" Passwort:\t\t");
		String passwort = sc.nextLine();

		Mitarbeiter mitarbeiterLogin = new Mitarbeiter();
		mitarbeiterLogin.setEmail(email);
		mitarbeiterLogin.setPasswort(passwort);

		return mitarbeiterLogin;
	}

	/**
	 * Helpermethode um MA login zu lesen.
	 * 
	 * @return gibt gelesene MA Daten zurück
	 */
	private Mitarbeiter leseMitarbeiterNameUndEmail() {
		Scanner sc = new Scanner(System.in);
		System.out.println("\nMitarbeiterangaben eingeben: \n");

		System.out.print(" Name:\t\t\t");
		String name = sc.nextLine();

		System.out.print(" Vorname:\t\t");
		String vorname = sc.nextLine();

		System.out.print(" Email:\t\t\t");
		String email = sc.nextLine();

		Mitarbeiter mitarbeiterEmail = new Mitarbeiter();
		mitarbeiterEmail.setEmail(email);

		return mitarbeiterEmail;
	}

	/**
	 * Helpermethode Zeit einlesen
	 */
	private List<Timestamp> readTimestamp() {
		List<Timestamp> listeTimestamp = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		System.out.println("\nStarttag der Periode eingeben [dd]: \n");
		String dateStart = sc.nextLine();
		System.out.println("\nMonat eingeben [mm]: \n");
		String monthStart = sc.nextLine();
		System.out.println("\nJahr eingeben [yyyy]: \n");
		String yearStart = sc.nextLine();
		System.out.println("\nStunde der Tageszeit eingeben [hh]: \n");
		String hourStart = sc.nextLine();
		System.out.println("\nEndtag der Periode eingeben [dd]: \n");
		String dateEnd = sc.nextLine();
		System.out.println("\nMonat eingeben [mm]: \n");
		String monthEnd = sc.nextLine();
		System.out.println("\nJahr eingeben [yyyy]: \n");
		String yearEnd = sc.nextLine();
		System.out.println("\nStunde der Tageszeit eingeben [hh]: \n");
		String hourEnd = sc.nextLine();
		listeTimestamp
				.add(Timestamp.valueOf(yearStart + "-" + monthStart + "-" + dateStart + " " + hourStart + ":00:00"));
		listeTimestamp.add(Timestamp.valueOf(yearEnd + "-" + monthEnd + "-" + dateEnd + " " + hourEnd + ":00:00"));

		return listeTimestamp;
	}

	private Timestamp readOneTimeStamp() {

		Scanner sc = new Scanner(System.in);
		System.out.println("\nStarttag eingeben [dd]: \n");
		String dateStart = sc.nextLine();
		System.out.println("\nMonat eingeben [mm]: \n");
		String monthStart = sc.nextLine();
		System.out.println("\nJahr eingeben [yyyy]: \n");
		String yearStart = sc.nextLine();
		System.out.println("\nStunde der Tageszeit eingeben [hh]: \n");
		String hourStart = sc.nextLine();
		System.out.println("\nMinute der Tageszeit eingeben [hh]: \n");
		String minStart = sc.nextLine();

		return Timestamp
				.valueOf(yearStart + "-" + monthStart + "-" + dateStart + " " + hourStart + ":" + minStart + ":00");
	}

	/**
	 * Helpermethod um City Name zu lesen
	 */

	private String cityNameLesen() {
		Scanner sc = new Scanner(System.in);
		System.out.println("\nNamen der Stadt eingeben:\t");
		String name = sc.nextLine();

		return name;
	}

	/**
	 * Helpermethode um ELemente der CityListe anzuzeigen.
	 * 
	 * @param list
	 */
	private void printListeCity(List<City> list) {
		for (City c : list) {
			System.out.println(c.toString());
		}
	}

	/**
	 * Helpermethode um Elemente der WetterdatenListe anzuzeigen.
	 * 
	 * @param list
	 */
	private void printListeWetterdaten(List<Wetterdaten> list) {
		for (Wetterdaten c : list) {
			System.out.println(c.toString());
		}
	}
}
