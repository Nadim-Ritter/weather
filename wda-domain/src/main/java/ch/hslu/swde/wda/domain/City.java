package ch.hslu.swde.wda.domain;

import java.io.Serializable;
import java.text.Collator;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Diese Klasse bildet eine Adresse (Strasse, Hausnummer, Postleitzeit und
 * Ortschaft) ab.
 * 
 */

@NamedQuery(name = "City.findeAlle", query = "WÄHLE c von City c ORDNEN NACH c.PLZ")
@Entity
public class City implements Serializable, Comparable<City> {

	private static final long serialVersionUID = 9217714341142960260L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("plz")
	private int plz;

	public City() {
		this.name = "";
		this.plz = -1;
	}

	public City(String name, int plz) {
		this.name = name;
		this.plz = plz;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPlz() {
		return plz;
	}

	public void setPlz(int plz) {
		this.plz = plz;
	}

	@Override
	public boolean equals(Object obj) {
		// Schritt 1: Identität prüfen. Sind die beiden Referenzen gleich?
		if (this == obj) {
			return true;
		}

		// Schritt 2: Ist das übergebene Objekt vom gleichen Typ oder nicht?
		if (!(obj instanceof City)) {
			return false;
		}

		// Schritt 3: Typumwandlung in den aktuellen Typ
		City other = (City) obj;

		// Zustände der beiden Objekte vergleichen
		return this.name.equals(other.name) && this.plz == other.plz;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.plz);
	}

	@Override
	public int compareTo(City c) {

		Collator col = Collator.getInstance();

		int i = Integer.compare(this.plz, c.plz);
		if (i == 0) {
			int y = col.compare(this.name, c.name);
			return y;
		}
		return i;
	}

	@Override
	public String toString() {
		return "City= ID: " + id + ", Name: " + name + ", PLZ: " + plz;
	}

}