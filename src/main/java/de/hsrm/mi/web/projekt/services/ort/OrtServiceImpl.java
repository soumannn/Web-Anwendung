package de.hsrm.mi.web.projekt.services.ort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.entities.ort.Ort;
import de.hsrm.mi.web.projekt.entities.ort.OrtRepository;
import de.hsrm.mi.web.projekt.services.geo.GeoAdresse;
import de.hsrm.mi.web.projekt.services.geo.GeoService;

@Service
public class OrtServiceImpl implements OrtService {

    @Autowired
    private OrtRepository ortRepository;

    @Autowired
    private GeoService geoService;

    @Override
    public List<Ort> holeAlleOrte() {
        // TODO Auto-generated method stub
        return ortRepository.findAll();
    }

    @Override
    public Optional<Ort> holeOrtMitId(long id) {
        // TODO Auto-generated method stub
        return ortRepository.findById(id);
    }

    @Override
    public Ort speichereOrt(Ort o) {
        // TODO Auto-generated method stub
        return ortRepository.save(o);
    }

    @Override
    public void loescheOrtMitId(long id) {
        // TODO Auto-generated method stub
        ortRepository.deleteById(id);
    }

    
    @Override
    public List<Ort> findeOrtsvorschlaegeFuerAdresse(String ort) {
        List<GeoAdresse> geoAdressen = geoService.findeAdressen(ort);
        List<Ort> ortsVorschlaege = new ArrayList<>();

        for (GeoAdresse geoAdresse : geoAdressen) {
            Ort neuerOrt = new Ort();
            neuerOrt.setName(geoAdresse.name());
            neuerOrt.setGeobreite(geoAdresse.lat());
            neuerOrt.setGeolaenge(geoAdresse.lon());
            ortsVorschlaege.add(neuerOrt);
            
        }

        return ortsVorschlaege;

    }

}
