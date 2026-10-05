package de.hsrm.mi.web.projekt.ui.benutzer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.validators.GutesPasswort;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class BenutzerFormular {

    @Size(min=3, max=80, message = "{name_laenge.fehler}")
    @NotBlank
    private String name;

    @Email
    @NotNull
    @NotBlank
    private String email;

    @GutesPasswort
    private String passwort;

    @Past
    @NotNull
    private LocalDate geburtsdatum;

    private String neueVorliebe;
    private String neueAntipathien;
    private Set<String> vorlieben = new HashSet<>();
    private Set<String> antipathien = new HashSet<>();

    // Getter und Setter für name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter und Setter für email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter und Setter für password
    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    // Getter und Setter für geburtsdatum
    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public void setVorlieben(Set<String> vorlieben) {
        this.vorlieben = vorlieben;
    }

    public Set<String> getVorlieben() {
        return vorlieben;
    }

    public Set<String> getAntipathien() {
        return antipathien;
    }

    public void setAntipathien(Set<String> antipathien) {
        this.antipathien = antipathien;
    }

    public void setNeueVorliebe(String neueVorliebe) {
        if(neueVorliebe!=null && neueVorliebe!="") {
            this.neueVorliebe = neueVorliebe;
            this.vorlieben.add(neueVorliebe);
        }
        
    }

    public String getNeueVorliebe() {
        return neueVorliebe;
    }

    public void setNeueAntipathien(String neueAntipathien) {
        if(neueAntipathien!=null && neueAntipathien!=""){
            this.neueAntipathien = neueAntipathien;
            this.antipathien.add(neueAntipathien);
        }

    }

    public String getNeueAntipathien() {
        return neueAntipathien;
    }

    // befuellt b mit Formularinhalt
    public void toBenutzer(Benutzer b){
        b.setName(this.name);
        b.setEmail(this.email);
        b.setPasswort(this.passwort);
        b.setGeburtsdatum(this.geburtsdatum);
        b.setVorlieben(this.vorlieben);
        b.setAntipathien(this.antipathien);
    } 
    // fuellt Formularinhalt aus b
    public void fromBenutzer(Benutzer b){
        this.name = b.getName();
        this.email = b.getEmail();
        this.geburtsdatum = b.getGeburtsdatum();
        this.vorlieben = b.getVorlieben();
        this.antipathien = b.getAntipathien();

    }


}
