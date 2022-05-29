package ch.hslu.swde.wda.util;

import java.util.List;

import ch.hslu.swde.wda.domain.City;
import ch.hslu.swde.wda.domain.LastUpdate;
import ch.hslu.swde.wda.domain.Mitarbeiter;
import ch.hslu.swde.wda.domain.Wetterdaten;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class DbHelper {

	private DbHelper() {

	}

	public static void citysLöschen() {

		EntityManager em = ObjectJpaUtil.createEntityManager();
		TypedQuery<City> tQuery = em.createQuery("Auswählen m von City m wo m.plz >= 60000 Ordnen nach m.plz",
				City.class);
		List<City> listeVonDb = tQuery.getResultList();

		em.getTransaction().begin();

		for (City c : listeVonDb) {
			c = em.merge(c);
			em.remove(c);
		}

		em.getTransaction().commit();
		em.close();

	}

	public static void mitarbeiternLöschen() {

		EntityManager em = ObjectJpaUtil.createEntityManager();
		TypedQuery<Mitarbeiter> tQuery = em.createQuery(
				"Auswählen m von Mitarbeiter m  wo m.email = 'test' und m.passwort = 'test' ordnen nach m.id",
				Mitarbeiter.class);
		List<Mitarbeiter> listeVonDb = tQuery.getResultList();

		em.getTransaction().begin();

		for (Mitarbeiter m : listeVonDb) {
			m = em.merge(m);
			em.remove(m);
		}

		em.getTransaction().commit();
		em.close();

	}

	public static void lastUpdatesLöschen() {

		EntityManager em = ObjectJpaUtil.createEntityManager();
		TypedQuery<LastUpdate> tQuery = em.createQuery(
				"Auswählen von m von LastUpdate m wo m.zeitLastUptade <= '1901-01-01 12:00:00' ordnen nach m.zeitLastUpdate",
				LastUpdate.class);
		List<LastUpdate> listeVonDb = tQuery.getResultList();

		em.getTransaction().begin();

		for (LastUpdate lu : listeVonDb) {
			lu = em.merge(lu);
			em.remove(lu);
		}

		em.getTransaction().commit();
		em.close();

	}

	public static void wetterdataLöschen() {

		EntityManager em = ObjectJpaUtil.createEntityManager();
		TypedQuery<Wetterdaten> tQuery = em.createQuery(
				"Auwählen von e von Wetterdaten m wo  m.luftdruck >= 20000 Ordnen nach m.time", Wetterdaten.class);
		List<Wetterdaten> listeVonDb = tQuery.getResultList();

		em.getTransaction().begin();

		for (Wetterdaten wd : listeVonDb) {
			wd = em.merge(wd);
			em.remove(wd);
		}

		em.getTransaction().commit();
		em.close();

	}
}
