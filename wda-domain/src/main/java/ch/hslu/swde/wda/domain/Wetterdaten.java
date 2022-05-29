package ch.hslu.swde.wda.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

/**
 * Diese Klasse bildet die Wetterdaten (Temperatur, Luftdruck & Feuchtigkeit )
 * ab.
 * 
 */

@NamedQuery(name = "Wetterdaten.findeAlle", query = "WÄHLE e VON Wetterdaten e ORDNEN NACH e.zeit")
//@NamedQuery(name="Wetterdaten.findResult", query="WÄHLE w VON Wetterdaten w WO (w.city_id=:city_id) UND (w.zeit ZWISCHEN :start UND :end)")
@Entity
public class Wetterdaten implements Serializable {

	private static final long serialVersionUID = 5235961136479713965L;

	@GeneratedValue
	@Column(name = "id")
	@Id
	private int id;
	private double luftdruck;
	private double feuchtigkeit;
	private double temperatur;

	@ManyToOne
	// @JoinColumn(name = "id", referencedColumnName = "id")
	private City city;
	private Timestamp time;

	// Standard constructor
	public Wetterdaten() {
		this.luftdruck = -1;
		this.feuchtigkeit = -1;
		this.temperatur = -274;
		this.city = new City();
		this.time = Timestamp.valueOf("1900-01-01 12:00:00");

	}

	public Wetterdaten(double luftdruck, double feuchtigkeit, double temperatur, City city, Timestamp time) {
		this.luftdruck = luftdruck;
		this.feuchtigkeit = feuchtigkeit;
		this.temperatur = temperatur;
		this.city = city;
		this.time = time;
	}

	// Getter and Setter
	public int getId() {
		return id;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLuftdruck() {
		return luftdruck;
	}

	public void setLuftdruck(double luftdruck) {
		this.luftdruck = luftdruck;
	}

	public double getFeuchtigkeit() {
		return feuchtigkeit;
	}

	public void setFeuchtigkeit(double feuchtigkeit) {
		this.feuchtigkeit = feuchtigkeit;
	}

	public double getTemperatur() {
		return temperatur;
	}

	public void setTemperatur(double temperatur) {
		this.temperatur = temperatur;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	@Override
	public int hashCode() {

		return Objects.hash(this.luftdruck, this.feuchtigkeit, this.temperatur, this.city, this.time);
	}

	@Override
	public boolean equals(Object obj) {
		// Schritt 1: Identität prüfen. Sind die beiden Referenzen gleich?
		if (this == obj) {
			return true;
		}

		// Schritt 2: Ist das übergebene Objekt vom gleichen Typ oder nicht?
		if (!(obj instanceof Wetterdaten)) {
			return false;
		}

		// Schritt 3: Typumwandlung in den aktuellen Typ
		Wetterdaten other = (Wetterdaten) obj;

		// Zustände der beiden Objekte vergleichen
		return (Double.compare(this.luftdruck, other.luftdruck) == 0)
				&& (Double.compare(this.feuchtigkeit, other.feuchtigkeit) == 0)
				&& (Double.compare(this.temperatur, other.temperatur) == 0) && this.city.equals(other.city)
				&& this.time.equals(other.time);
	}

	@Override
	public String toString() {
		return "Wetterdaten= ID: " + id + ", Luftdruck:" + luftdruck + ", Luftfeuchtigkeit:" + feuchtigkeit
				+ ", Temperatur:" + temperatur + ", Stadt: " + city + ", Zeit: " + time + "]";
	}

}