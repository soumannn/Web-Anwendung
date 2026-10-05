package de.hsrm.mi.web.projekt.entities.tour;

import java.time.LocalDate;
import java.time.LocalDateTime;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.entities.ort.Ort;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;

@Entity
public class Tour {

    @Id
    @GeneratedValue
    private long id;

    @Version
    private long version;

    @ManyToOne
    private Benutzer anbieter;

    private LocalDateTime abfahrDateTime;
    @Min(0)
    private int preis;
    @Min(1)
    private int plaetze;

    private String info;

    @ManyToOne
    private Ort startort;

    @ManyToOne
    private Ort zielort;

    @ManyToMany(mappedBy = "gebuchteTouren")
    private Collection<Benutzer> mitfahrgaeste = new HashSet<>();


    public Collection<Benutzer> getMitfahrgaeste() {
        return mitfahrgaeste;
    }

    public void setMitfahrgaeste(Collection<Benutzer> mitfahrgaeste) {
        this.mitfahrgaeste = mitfahrgaeste;
    }

    // Getter and Setter for id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // Getter and Setter for version
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    // Getter and Setter for abfahrDateTime
    public LocalDateTime getAbfahrDateTime() {
        return abfahrDateTime;
    }

    public void setAbfahrDateTime(@FutureOrPresent(message = "{zeit.fehler}") LocalDateTime abfahrDateTime2) {
        this.abfahrDateTime = abfahrDateTime2;
    }

    // Getter and Setter for preis
    public int getPreis() {
        return preis;
    }

    public void setPreis(int preis) {
        this.preis = preis;
    }

    // Getter and Setter for plaetze
    public int getPlaetze() {
        return plaetze;
    }

    public void setPlaetze(int plaetze) {
        this.plaetze = plaetze;
    }

    // Getter and Setter for info
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Ort getStartort() {
        return startort;
    }

    public void setStartort(Ort startort) {
        this.startort = startort;
    }

    public Ort getZielort() {
        return zielort;
    }

    public void setZielort(Ort zielort) {
        this.zielort = zielort;
    }

    public Benutzer getAnbieter() {
        return anbieter;
    }

    public void setAnbieter(Benutzer anbieter) {
        this.anbieter = anbieter;
    }

}
