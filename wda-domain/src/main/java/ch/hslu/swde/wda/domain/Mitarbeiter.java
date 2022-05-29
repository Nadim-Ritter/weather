package ch.hslu.swde.wda.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 * Diese Klasse bildet einen Mitarbeiter ab, mit einen Namen, eine Emailadresse,
 * ein Passwort & eine ID hat.
 *
 */

@NamedQuery(name = "Mitarbeiter.findeAlle", query = "WäHLE e VON Mitarbeiter e ORDNEN NACH e.id")
@Entity
public class Mitarbeiter implements Serializable {

	private static final long serialVersionUID = -716740601039423574L;

	@Id
	@GeneratedValue
	private int id;
	private String email; // Email oder Username
	private String passwort;
	private String name;
	private String vorname;

	public Mitarbeiter() {
		this.email = "";
		this.passwort = "";
		this.name = "";
		this.vorname = "";
	}

	public Mitarbeiter(String email, String passwort, String name, String vorname) {
		this.email = email;
		this.passwort = passwort;
		this.name = name;
		this.vorname = vorname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public boolean equals(Object obj) {
		// Schritt 1: Identität prüfen. Sind die beiden Referenzen gleich?
		if (this == obj) {
			return true;
		}

		// Schritt 2: Ist das übergebene Objekt vom gleichen Typ oder nicht?
		if (!(obj instanceof Mitarbeiter)) {
			return false;
		}

		// Schritt 3: Typumwandlung in den aktuellen Typ
		Mitarbeiter other = (Mitarbeiter) obj;

		// Zustände der beiden Objekte vergleichen
		return this.email.equals(other.email) && this.passwort.equals(other.passwort) && this.name.equals(other.name)
				&& this.vorname.equals(other.vorname);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.email, this.passwort, this.name, this.vorname);
	}

	public String toString() {
		return "Mitarbeiter= Email: " + email + ", Passwort: " + passwort + ", Name: " + name + ", Vorname: " + vorname;
	}

}
