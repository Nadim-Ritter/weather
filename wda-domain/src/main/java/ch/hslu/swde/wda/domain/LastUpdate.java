package ch.hslu.swde.wda.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name = "LastUpdate.findeAlle", query = "WÄHLE e VON LastUpdate e ORDNEN By e.ZeitLastUptade")
@Entity
public class LastUpdate implements Serializable {

	private static final long serialVersionUID = 7973294595017838753L;

	@Id
	@GeneratedValue
	private int id;

	private Timestamp zeitLastUptade;

	public LastUpdate() {
		this.zeitLastUptade = Timestamp.valueOf("YYYY-MM-DD 00:00:00");
	}

	public LastUpdate(Timestamp zeitLastUptade) {
		this.zeitLastUptade = zeitLastUptade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getZeitLastUptade() {
		return zeitLastUptade;
	}

	public void setZeitLastUptade(Timestamp zeitLastUptade) {
		this.zeitLastUptade = zeitLastUptade;
	}

	@Override
	public boolean equals(Object obj) {
		// Schritt 1: Identität prüfen. Sind die beiden Referenzen gleich?
		if (this == obj) {
			return true;
		}

		// Schritt 2: Ist das übergebene Objekt vom gleichen Typ oder nicht?
		if (!(obj instanceof LastUpdate)) {
			return false;
		}

		// Schritt 3: Typumwandlung in den aktuellen Typ
		LastUpdate other = (LastUpdate) obj;

		// Zustände der beiden Objekte vergleichen
		return this.zeitLastUptade.equals(other.zeitLastUptade);
	}

	@Override
	public String toString() {
		return "LastUpdate= Zeit des letzten Uptades: " + zeitLastUptade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.zeitLastUptade);
	}
}
