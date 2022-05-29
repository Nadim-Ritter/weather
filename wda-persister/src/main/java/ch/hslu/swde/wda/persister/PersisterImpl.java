package ch.hslu.swde.wda.persister;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.swde.wda.domain.City;
import ch.hslu.swde.wda.domain.LastUpdate;
import ch.hslu.swde.wda.domain.Mitarbeiter;
import ch.hslu.swde.wda.domain.Wetterdaten;
import ch.hslu.swde.wda.util.ObjectJpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class PersisterImpl implements PersisterInterface {

	private static Logger loggerPersister = LogManager.getLogger(PersisterImpl.class);

	public PersisterImpl() {

	}

	// Speichern
	/**
	 * Die Methode speichert Städte.
	 * 
	 * @param city - Liste von City
	 */
	@Override
	public void citySpeichern(List<City> cityListe) throws Exception {

		loggerPersister.info("Start der CitysSpeicher Methode");

		EntityManager em = ObjectJpaUtil.createEntityManager();

		try {
			for (City c : cityListe) {

				City cityVonDB = cityFindenNachName(c.getName());

				// Prüfen ob neues City-Object.== schauen ob schon in Datenbank ist
				if (cityVonDB == null) {
					em.getTransaction().begin();
					em.persist(c);
					em.getTransaction().commit();
				}

			}

		} catch (Exception e) {

			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}

			loggerPersister.error("Fehler beim Aufruf der citysSpeicher Methode", e);
		} finally {

			if (em.isOpen()) {
				em.close();
			}
		}
	}

	/**
	 *
	 * Die Methode speichert Mitarbeiter.
	 * 
	 * @param mitarbeiter – Mitarbeiter
	 *
	 */
	@Override
	public void mitarbeterSpeichern(Mitarbeiter mitarbeiter) throws Exception {

		loggerPersister.info("Start der mitarbeiterSpeichern Methode");

		EntityManager em = ObjectJpaUtil.createEntityManager();

		try {
			Mitarbeiter maVonDB = maFindenMitID(mitarbeiter.getId());

			if (maVonDB == null || maVonDB.getEmail() != mitarbeiter.getEmail()) {
				em.getTransaction().begin();
				em.persist(mitarbeiter);
				em.getTransaction().commit();
			}

		} catch (Exception e) {

			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}

			loggerPersister.error("Fehler beim Aufruf der mitarbeterSpeichern Methode", e);

		} finally {

			if (em.isOpen()) {
				em.close();
			}
		}
	}

	/**
	 *
	 * Die Methode speichert 'LastUpdates'.
	 * 
	 * @param lastUptadeListe – Liste von LastUpdates
	 *
	 */
	@Override
	public void lastUptadeSpeichernOderAktualisieren(LastUpdate lastUpdate) throws Exception {

		loggerPersister.info("Start der lastUptadeSpeichernOderAktualisieren Methode");

		EntityManager em = ObjectJpaUtil.createEntityManager();

		try {

			LastUpdate luVonDB = lastUptadeFinden();

			if (luVonDB == null) {
				em.getTransaction().begin();
				em.persist(lastUpdate);
				em.getTransaction().commit();
			} else if (luVonDB.getZeitLastUptade() != lastUpdate.getZeitLastUptade()) {
				em.getTransaction().begin();
				em.merge(lastUpdate);
				em.getTransaction().commit();
			}
		} catch (Exception e) {

			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}

			loggerPersister.error("Fehler beim Aufruf der lastUptadeSpeichernOderAktualisieren Methode'", e);

		} finally {

			if (em.isOpen()) {
				em.close();
			}
		}
	}

	/**
	 *
	 * Die Methode speichert Wetterdaten.
	 * 
	 * @param alleWetterdaten – Liste von Wetterdaten
	 *
	 */

	@Override
	public void wetterdatenSpeichern(List<Wetterdaten> wetterdatenListe) throws Exception {

		loggerPersister.info("Start der WetterdatenSpeichern Methode");

		List<City> cityListe = alleCitys();

		int testcount = 0;
		for (Wetterdaten w : wetterdatenListe) {

			if (w.getCity().getName().equals("Biel/Bienne") || w.getCity().getName().equals("Fiesch")) {
				w.getCity().setName("Biel");
			}

			if (w.getCity().getName().equals("Canton of Bern")) {
				w.getCity().setName("Bern");
			}

			for (int x = 0; x < cityListe.size(); x++) {
				if (w.getCity().getName().equals(cityListe.get(x).getName())) {

					w.setCity(cityListe.get(x));
					x = cityListe.size();
				}
			}

			++testcount;
		}

		System.out.println("So viele Datensaetze hat es: " + testcount);
		System.out.println("Wetterdaten Liste: " + wetterdatenListe.size());

		EntityManager em = ObjectJpaUtil.createEntityManager();

		try {
			int temp = 0;
			int counter = 0;

			em.getTransaction().begin();

			for (Wetterdaten w : wetterdatenListe) {

				em.persist(w);

				temp++;
				counter++;

				if (temp >= 1000 || wetterdatenListe.size() == counter) {
					em.getTransaction().commit();
					temp = 0;

					System.out.println("So viele Datensaetze wurden comitted: " + counter);

					if (wetterdatenListe.size() >= counter) {
						em.getTransaction().begin();
					}
				}
			}

		} catch (Exception e) {

			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}

			loggerPersister.error("Fehler beim Aufruf der WetterdatenSpeichern Methode'", e);

		} finally {

			if (em.isOpen()) {
				em.close();
			}
		}

	}

	// UPDATE

	/**
	 *
	 * Die Methode aktualisiert Mitarbeiter.
	 * 
	 * @param mitarbeiter – Mitarbeiter
	 *
	 */
	@Override
	public void mitarbeiterAktualisieren(Mitarbeiter mitarbeiter) throws Exception {

		loggerPersister.info("Start der MitarbeiterAktualisieren Methode");

		if (mitarbeiter == null) {
			throw new IllegalArgumentException("Employee can't be null!");
		}

		EntityManager em = ObjectJpaUtil.createEntityManager();

		try {
			em.getTransaction().begin();
			em.merge(mitarbeiter);
			em.getTransaction().commit();

		} catch (Exception e) {

			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}

			loggerPersister.error("Fehler beim Aufruf der MitarbeiterAktualisieren Methode'", e);

		} finally {

			if (em.isOpen()) {
				em.close();
			}
		}
	}

	// Löschen

	/**
	 *
	 * Die Methode löscht Mitarbeiter.
	 * 
	 * @param mitarbeiter – Mitarbeiter
	 *
	 */
	@Override
	public void mitarbeiterLöschen(Mitarbeiter mitarbeiter) throws Exception {

		loggerPersister.info("Start der MitarbeiterLöschen Methode");

		if (mitarbeiter == null) {
			throw new IllegalArgumentException("Mitarbeiter kann nicht null sein!");
		}

		EntityManager em = ObjectJpaUtil.createEntityManager();

		try {
			Mitarbeiter maVonDB = maFindenMitEmailUndPasswort(mitarbeiter.getEmail(), mitarbeiter.getPasswort());

			if (maVonDB != null && maVonDB.getEmail().equals(maVonDB.getEmail())) {

				em.getTransaction().begin();
				mitarbeiter = em.merge(maVonDB);
				em.remove(mitarbeiter);
				em.getTransaction().commit();

			} else {
				throw new NoResultException("Mitarbeiter existiert nicht in der Datenbank.");
			}

		} catch (Exception e) {

			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}

			loggerPersister.error("Fehler beim Aufruf der MitarbeiterLöschen Methode'", e);

		} finally {

			if (em.isOpen()) {
				em.close();
			}
		}
	}

	/**
	 *
	 * Die Methode löscht Wetterdaten.
	 * 
	 * @param wetterdaten – Wetterdaten
	 *
	 */
	@Override
	public void wetterdatenLöschen(Wetterdaten wetterdaten) throws Exception {

		loggerPersister.info("Start der WetterdatenLöschen Methode");

		if (wetterdaten == null) {
			throw new IllegalArgumentException("Wetterdaten können nicht null sein!");
		}

		EntityManager em = ObjectJpaUtil.createEntityManager();

		try {

			Wetterdaten wdVonDB = wetterdatenFindenMitID(wetterdaten.getId());

			if (wdVonDB != null && wdVonDB.getId() == wetterdaten.getId()) {

				em.getTransaction().begin();
				wetterdaten = em.merge(wdVonDB);
				em.remove(wetterdaten);
				em.getTransaction().commit();

			} else {
				throw new NoResultException("Wetterdaten existieren nicht in de Datenbank.");
			}

		} catch (Exception e) {

			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}

			loggerPersister.error("Fehler beim Aufruf der WetterdatenLöschen Methode", e);

		} finally {

			if (em.isOpen()) {
				em.close();
			}
		}
	}

	// Alles
	/**
	 *
	 * Die Methode ruft alle Städte ab.
	 *
	 */
	@Override
	public List<City> alleCitys() throws Exception {

		loggerPersister.info("Start der alleCitys() Methode");

		EntityManager em = ObjectJpaUtil.createEntityManager();

		TypedQuery<City> tQryCity = em.createNamedQuery("City.findAll", City.class);
		List<City> citys = tQryCity.getResultList();

		return !citys.isEmpty() ? citys : new ArrayList<City>();

	}

	/**
	 *
	 * Die Methode ruft alle Mitarbeiter ab.
	 *
	 */
	@Override
	public List<Mitarbeiter> alleMitarbeiter() throws Exception {

		loggerPersister.info("Start der alleMitarbeiter() Methode");

		EntityManager em = ObjectJpaUtil.createEntityManager();

		TypedQuery<Mitarbeiter> tQryEmployee = em.createNamedQuery("Mitarbeiter.findeAlle", Mitarbeiter.class);
		List<Mitarbeiter> mitarbeiter = tQryEmployee.getResultList();

		return !mitarbeiter.isEmpty() ? mitarbeiter : new ArrayList<Mitarbeiter>();

	}

	/**
	 *
	 * Die Methode ruft alle 'LastUpdates' ab.
	 *
	 */
	public List<LastUpdate> alleLastUptades() throws Exception {

		loggerPersister.info("Start der alleLastUptades() Methode");

		EntityManager em = ObjectJpaUtil.createEntityManager();

		TypedQuery<LastUpdate> tQryLastUpdate = em.createNamedQuery("LastUpdate.findAll", LastUpdate.class);
		List<LastUpdate> lastUpdates = tQryLastUpdate.getResultList();

		return !lastUpdates.isEmpty() ? lastUpdates : new ArrayList<LastUpdate>();

	}

	/**
	 *
	 * Die Methode ruft alle Wetterdaten ab.
	 *
	 */
	@Override
	public List<Wetterdaten> alleWetterdaten() throws Exception {

		loggerPersister.info("Start der alleWetterdaten() Methode");

		EntityManager em = ObjectJpaUtil.createEntityManager();

		TypedQuery<Wetterdaten> tQryWeatherData = em.createNamedQuery("Wetterdaten.FindeAlle", Wetterdaten.class);
		List<Wetterdaten> wetterdaten = tQryWeatherData.getResultList();

		return !wetterdaten.isEmpty() ? wetterdaten : new ArrayList<Wetterdaten>();

	}

	// FIND
	/**
	 *
	 * Die Methode findet Mitarbeiter nach ID.
	 * 
	 * @param id
	 * @return mitarbeiter – Mitarbeiter
	 *
	 */
	@Override
	public Mitarbeiter maFindenMitID(int id) throws Exception {

		EntityManager em = ObjectJpaUtil.createEntityManager();
		Mitarbeiter mitarbeiterVonDB = null;

		try {
			mitarbeiterVonDB = em.find(Mitarbeiter.class, id);
			return mitarbeiterVonDB;

		} catch (NoResultException nre) {
			loggerPersister.error("Fehler beim Aufruf der MaFindenMitID Methode'", nre);
			return new Mitarbeiter();

		} catch (Exception e) {
			loggerPersister.error("Fehler beim Aufruf der MaFindenMitID Methode'", e);
			throw new Exception("Unbehandlte Exception wenn Mitarbeiter geucht wird");

		} finally {
			em.close();
		}
	}

	/**
	 *
	 * Die Methode findet Mitarbeiter nach E-Mail und Passwort.
	 * 
	 * @param email
	 * @param passwort
	 * @return mitarbeiter – Mitarbeiter
	 *
	 */
	@Override
	public Mitarbeiter maFindenMitEmailUndPasswort(String email, String passwort) throws Exception {
		EntityManager em = ObjectJpaUtil.createEntityManager();

		TypedQuery<Mitarbeiter> tQryEmpl = em.createQuery(
				"WÄHLE c von Mitarbeiter c WO c.email = :email UND c.passwort = :passwort", Mitarbeiter.class);
		tQryEmpl.setParameter("email", email);
		tQryEmpl.setParameter("passwort", passwort);

		Mitarbeiter maVonDB = null;
		try {
			maVonDB = tQryEmpl.getSingleResult();
			return maVonDB;

		} catch (NoResultException nre) {
			loggerPersister.error("Fehler beim Aufruf der MaFindenMitEmailUndPasswort Methode'", nre);
			return new Mitarbeiter();

		} catch (IllegalStateException ise) {
			loggerPersister.error("Fehler beim Aufruf der MaFindenMitEmailUndPasswort Methode'", ise);
			throw new IllegalStateException(
					"Mehr als ein Mitarbeiter gefunden. Bitte überprüfen auf Dubletten (Email & passwort)");

		} catch (Exception e) {
			loggerPersister.error("Fehler beim Aufruf der MaFindenMitEmailUndPasswort Methode'", e);
			throw new Exception("Unhandled exception während der Suche nach einem MA mit Email und Passwort");

		} finally {
			em.close();
		}
	}

	/**
	 *
	 * Die Methode findet 'LastUpdate' nach ID.
	 * 
	 * @param id
	 * @return LastUpdate
	 *
	 */
	@Override
	public LastUpdate lastUptadeFinden() throws Exception {

		EntityManager em = ObjectJpaUtil.createEntityManager();
		LastUpdate lastuptadeVonDB = null;

		TypedQuery<LastUpdate> tQryCity = em.createQuery("WÄHLE c VON LastUpdate c", LastUpdate.class);
		try {
			lastuptadeVonDB = tQryCity.getSingleResult();
			return lastuptadeVonDB;

		} catch (NoResultException nre) {
			loggerPersister.error("Fehler beim Aufruf der LastUptadeFinden() Methode'", nre);
			return null;

		} catch (Exception e) {
			loggerPersister.error("Fehler beim Aufruf der LastUptadeFinden Methode'", e);
			throw new Exception("Unhandled exception während der Suche nach der ID des LastUptades");

		} finally {
			em.close();
		}
	}

	/**
	 *
	 * Die Methode findet Städte nach Namen.
	 * 
	 * @param name – Name der Stadt
	 * @return City – Stadt
	 *
	 */
	public City cityFindenNachName(String name) throws Exception {
		EntityManager em = ObjectJpaUtil.createEntityManager();

		TypedQuery<City> tQryCity = em.createQuery("WÄHLE c VON City c WO c.name = :name", City.class);
		tQryCity.setParameter("name", name);

		City cityVonDB = null;
		try {
			cityVonDB = tQryCity.getSingleResult();
			return cityVonDB;

		} catch (NoResultException nre) {
			// loggerPersister.info("Fehler beim Aufruf der 'findCityByName(String name)'
			// Methode'", nre);
			return null;

		} catch (IllegalStateException ise) {
			loggerPersister.error("Fehler beim Aufruf der CityFindenNachName Methode'", ise);
			throw new IllegalStateException("Mehr als ein Resultat. Bitte Dubletten überprüfen. Cityname)");

		} catch (Exception e) {
			loggerPersister.error("Fehler beim Aufruf der CityFindenNachName Methode", e);
			throw new Exception("Unhandled exception während der Suche des Namen der Stadt");

		} finally {
			em.close();
		}
	}

	/**
	 *
	 * Die Methode liefert die Temperatur, Luftdruck und Feuchtigkeit einer Stadt.
	 * 
	 * @param cityName – Name der Stadt
	 * @param start    – Startzeitpunkt
	 * @param end      – Endzeitpunkt
	 * @return List<Wetterdaten> – Liste von Wetterdaten
	 *
	 */
	@Override
	public List<Wetterdaten> TFLFinden(String cityName, Timestamp start, Timestamp end) throws Exception {
		EntityManager em = ObjectJpaUtil.createEntityManager();

		TypedQuery<City> tQryCity = em.createQuery("WÄHLE c VON City c WO c.name = :cityName", City.class);
		tQryCity.setParameter("cityName", cityName);

		City c = null;
		try {
			c = tQryCity.getSingleResult();

		} catch (IllegalStateException ise) {
			loggerPersister.error("Fehler beim Aufruf der TFLFinden Methode'", ise);
			throw new IllegalStateException("Mehr als ein Resultat. Bitte auf Dubletten überprüfen. (cityname)");

		} catch (Exception e) {
			loggerPersister.error("Fehler beim Aufruf TFLFinden Methode'", e);
			throw new Exception("Unhandled exception während der Siche nach dem Cityname");

		}

		List<Wetterdaten> list = new ArrayList<>();

		try {
			TypedQuery<Wetterdaten> tQryWD = em.createQuery(
					"WÄHLE w VON Wetterdaten w WO (w.city.id =:cityID) UND (w.zeit ZWISCHEN :start UND :end)",
					Wetterdaten.class);
			tQryWD.setParameter("cityID", c.getId());
			tQryWD.setParameter("start", start);
			tQryWD.setParameter("end", end);

			list = tQryWD.getResultList();

		} catch (NoResultException nre) {
			loggerPersister.error("Fehler beim Aufruf der TFLFinden Methode'", nre);
			return new ArrayList<Wetterdaten>();

		} catch (Exception e) {
			loggerPersister.error("Fehler beim Aufruf der TFLFinden Methode'", e);
			throw new Exception("Unhandled exception während der Suche nach Wetterdaten mit CityID und oder Zeit");

		} finally {
			em.close();
		}

		return !list.isEmpty() ? list : new ArrayList<Wetterdaten>();
	}

	/**
	 *
	 * Die Methode liefert die minimale und maximale Temperatur, Luftdruck und
	 * Feuchtigkeit aller Städte zu einem Zeitpunkt.
	 * 
	 * @param time – Zeitpunkt
	 * @return List<Wetterdaten> – Liste von Wetterdaten
	 *
	 */
	@Override
	public List<Wetterdaten> TLFMinMaxFinden(Timestamp time) throws Exception {

		Timestamp end = Timestamp.valueOf("YYYY-MM-DD 00:00:00.00");
		end.setTime(time.getTime());
		end.setMinutes(end.getMinutes() + 30);

		EntityManager em = ObjectJpaUtil.createEntityManager();

		TypedQuery<Wetterdaten> tQry = em.createQuery("WÄHLE w VON Wetterdaten w WO w.time ZWISCHEN :time UND :end",
				Wetterdaten.class);
		tQry.setParameter("time", time);
		tQry.setParameter("end", end);

		List<Wetterdaten> list = new ArrayList<>();
		try {
			list = tQry.getResultList();

		} catch (NoResultException nre) {
			loggerPersister.error("Fehler beim Aufruf der TLFMinMaxFinden Methode'", nre);
			return new ArrayList<Wetterdaten>();

		} catch (Exception e) {
			loggerPersister.error("Fehler beim Aufruf der TLFMinMaxFinden Methode'", e);
			throw new Exception("Unhandled exception währen der Suche nach den Wetterdaten mit Zeit");

		} finally {
			em.close();
		}

		return !list.isEmpty() ? list : new ArrayList<Wetterdaten>();
	}

	/**
	 *
	 * Die Methode findet Wetterdaten nach id.
	 * 
	 * @param id
	 * @return Wetterdaten – Wetterdaten
	 *
	 */
	private Wetterdaten wetterdatenFindenMitID(int id) throws Exception {

		EntityManager em = ObjectJpaUtil.createEntityManager();
		Wetterdaten wetterdatenVonDB = null;

		try {
			wetterdatenVonDB = em.find(Wetterdaten.class, id);
			return wetterdatenVonDB;

		} catch (NoResultException nre) {
			loggerPersister.error("Fehler beim Aufruf der wetterdatenFindenMitID Methode'", nre);
			return new Wetterdaten();

		} catch (Exception e) {
			loggerPersister.error("Fehler beim Aufruf der wetterdatenFindenMitID Methode'", e);
			throw new Exception("Unhandled exception während der Suche nach der ID Wetterdaten");

		} finally {
			em.close();
		}
	}
}
