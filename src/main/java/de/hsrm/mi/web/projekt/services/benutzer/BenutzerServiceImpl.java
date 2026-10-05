package de.hsrm.mi.web.projekt.services.benutzer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.entities.benutzer.BenutzerRepository;

@Service
public class BenutzerServiceImpl implements BenutzerService{

    @Autowired
    private BenutzerRepository benutzerRepository;


    @Override
    public List<Benutzer> holeAlleBenutzer() {
        // TODO Auto-generated method stub
        return benutzerRepository.findAll();
    }

    @Override
    public Optional<Benutzer> holeBenutzerMitId(long id) {
        // TODO Auto-generated method stub
        return benutzerRepository.findById(id);
    }

    @Override
    public Benutzer speichereBenutzer(Benutzer b) {
        // TODO Auto-generated method stub
        return benutzerRepository.save(b);
    }

    @Override
    public void loescheBenutzerMitId(long id) {
        // TODO Auto-generated method stub
        benutzerRepository.deleteById(id);
    }

    @Override
    public Benutzer aktualisiereBenutzerAttribut(long id, String feldname, String wert) {
        Benutzer benutzer = benutzerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Benutzer nicht gefunden"));
        
        switch (feldname) {
            case "name":
                benutzer.setName(wert);
                break;
            case "email":
                benutzer.setEmail(wert);
                break;
            default:
                throw new IllegalArgumentException("Ungültiges Feldname: " + feldname);
        }

        return benutzerRepository.save(benutzer);
    
    }
    
}
