package de.hsrm.mi.web.projekt.entities.benutzer;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.hsrm.mi.web.projekt.entities.tour.Tour;
import de.hsrm.mi.web.projekt.validators.GutesPasswort;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
public class Benutzer {
    
    @Id
    @GeneratedValue
    private long id;

    @Version
    private long version;

    @Size(min=3, max=80, message = "{name_laenge.fehler}")
    private String name;

    @Email
    @Column(unique=true)
    private String email;

    private String passwort;

    @OneToMany(mappedBy="anbieter", cascade = CascadeType.ALL) 
    private Collection<Tour> touren = new HashSet<Tour>();


    @Past
    private LocalDate geburtsdatum;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> vorlieben;

    @ElementCollection
    private Set<String> antipathien;

    @ManyToMany
    private Collection<Tour> gebuchteTouren = new HashSet<>();

    public long getId(){
        return this.id;
    }

    public long getVersion(){
        return this.version;
    }
   
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name=name;
    }

    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email=email;
    }

    public String getPasswort(){
        return this.passwort;
    }
    public void setPasswort(String passwort){
        if( (this.passwort==null  || this.passwort.trim().isEmpty()) || (!passwort.trim().isEmpty())){
            this.passwort=passwort;
        }

    }

    public LocalDate getGeburtsdatum(){
        return this.geburtsdatum;
    }
    public void setGeburtsdatum(LocalDate geburtsdatum){
        this.geburtsdatum=geburtsdatum;
    }

    public Set<String>  getVorlieben(){
        return this.vorlieben;
    }
    public void setVorlieben( Set<String>  vorlieben){
        this.vorlieben=vorlieben;
    }

    public Set<String>  getAntipathien(){
        return this.antipathien;
    }
    public void setAntipathien( Set<String>  antipathien){
        this.antipathien=antipathien;
    }

    public boolean hatMachtVorliebe() {
        return vorlieben != null && vorlieben.contains("MACHT");
    }
    
}

