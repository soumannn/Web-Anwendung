package de.hsrm.mi.web.projekt.services.geo;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface GeoService {
    List<GeoAdresse> findeAdressen(String ort);
}
