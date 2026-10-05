package de.hsrm.mi.web.projekt.ui.tour;

import java.time.LocalDate;
import java.time.LocalDateTime;

import de.hsrm.mi.web.projekt.entities.tour.Tour;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class TourFormular {

    @FutureOrPresent(message = "{zeit.fehler}")
    private LocalDateTime abfahrDateTime;

    @Min(value=0, message="{preis.fehler}")
    private int preis;

    @Min(value=1,message = "{plaetze.fehler}")
    private int plaetze;

    @Size(max=100, message="{info.fehler}")
    private String info;

    @Positive(message = "{ort.fehler}")
    private long startortId;

    @Positive(message = "{ort.fehler}")
    private long zielortId;

    @Positive(message = "{anbieter.fehler}")
    private long anbieterId;

    // Getter and Setter for abfahrDateTime
    public LocalDateTime getAbfahrDateTime() {
        return abfahrDateTime;
    }

    public void setAbfahrDateTime(LocalDateTime abfahrDateTime) {
        this.abfahrDateTime = abfahrDateTime;
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

    // Getter und Setter für startortId
    public long getStartortId() {
        return startortId;
    }

    public void setStartortId(long startortId) {
        this.startortId = startortId;
    }

    // Getter und Setter für zielortId
    public long getZielortId() {
        return zielortId;
    }

    public void setZielortId(long zielortId) {
        this.zielortId = zielortId;
    }

    // Getter und Setter für anbieterId
    public long getAnbieterId() {
        return anbieterId;
    }

    public void setAnbieterId(long anbieterId) {
        this.anbieterId = anbieterId;
    }

    // befuellt b mit Formularinhalt
    public void toTour(Tour t) {
        t.setAbfahrDateTime(this.abfahrDateTime);
        t.setPreis(this.preis);
        t.setPlaetze(this.plaetze);
        t.setInfo(this.info);
    }

    // fuellt Formularinhalt aus b
    public void fromTour(Tour t) {
        this.abfahrDateTime = t.getAbfahrDateTime();
        this.preis = t.getPreis();
        this.plaetze = t.getPlaetze();
        this.info = t.getInfo();
        this.anbieterId = t.getAnbieter().getId();
        this.startortId = t.getStartort().getId();
        this.zielortId = t.getZielort().getId();
    }
}
