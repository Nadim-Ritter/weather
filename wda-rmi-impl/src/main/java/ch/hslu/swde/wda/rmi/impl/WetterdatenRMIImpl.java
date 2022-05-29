package ch.hslu.swde.wda.rmi.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.List;

import ch.hslu.swde.wda.business.Verwaltung;
import ch.hslu.swde.wda.business.VerwaltungImpl;
import ch.hslu.swde.wda.domain.City;
import ch.hslu.swde.wda.domain.Mitarbeiter;
import ch.hslu.swde.wda.domain.Wetterdaten;
import ch.hslu.swde.wda.rmi.api.WetterdatenRMI;

public class WetterdatenRMIImpl extends UnicastRemoteObject implements WetterdatenRMI {

	private Verwaltung verwaltung;

	public WetterdatenRMIImpl() throws RemoteException {
		verwaltung = new VerwaltungImpl(new ch.hslu.swde.wda.persister.PersisterImpl(),
				new ch.hslu.swde.g02wda.reader.client.ReaderClient());
	}

	@Override
	public void neuerMitarbeiterHinzufügen(Mitarbeiter mitarbeiter) throws Exception {
		verwaltung.neuerMitarbeiterHinzufügen(mitarbeiter);

	}

	@Override
	public void mitarbeiterAktualisieren(Mitarbeiter mitarbeiter) throws Exception {
		verwaltung.mitarbeiterAktualisieren(mitarbeiter);
	}

	@Override
	public void mitarbeiterLöschen(Mitarbeiter mitarbeiter) throws Exception {
		verwaltung.mitarbeiterLöschen(mitarbeiter);
	}

	@Override
	public Mitarbeiter maFindenMitEmailUndPasswort(String email, String password) throws Exception {
		return verwaltung.maFindenMitEmailUndPasswort(email, password);
	}

	@Override
	public Mitarbeiter maFindenMitID(int id) throws Exception {
		return verwaltung.maFindenMitID(id);
	}

	@Override
	public List<Mitarbeiter> alleMitarbeiter() throws Exception {
		return verwaltung.alleMitarbeiter();
	}

	@Override
	public List<City> alleCitysFinden() throws Exception {
		return verwaltung.alleCitysFinden();
	}

	@Override
	public List<Wetterdaten> TLFFinden(String cityName, Timestamp start, Timestamp end) throws Exception {
		return verwaltung.TLFFinden(cityName, start, end);
	}

	@Override
	public List<Wetterdaten> TLFDurchschnittFinden(String cityName, Timestamp start, Timestamp end) throws Exception {
		return verwaltung.TLFDurchschnittFinden(cityName, start, end);
	}

	@Override
	public List<Wetterdaten> TLFMinMaxFinden(String cityName, Timestamp start, Timestamp end) throws Exception {
		return verwaltung.TLFMinMaxFinden(cityName, start, end);
	}

	@Override
	public List<Wetterdaten> TLFMinMaxAlleCitysFinden(Timestamp time) throws Exception {
		return verwaltung.TLFMinMaxAlleCitysFinden(time);
	}

	@Override
	public List<City> alleCitysVonWebserverFinden() throws Exception {
		return verwaltung.alleCitysVonWebserverFinden();
	}

	@Override
	public List<Wetterdaten> alleWetterdatenVonWebserver(String cityName) throws Exception {
		return verwaltung.alleWetterdatenVonWebserverFinden(cityName);
	}

	@Override
	public void WetterdatenAktualisierenVonWebserver() throws Exception {
		verwaltung.wetterdatenVonWebserverAktualisieren();
	}

}
