package de.hsrm.mi.web.projekt.services.geo;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class GeoServiceImpl implements GeoService {

    @Override
    public List<GeoAdresse> findeAdressen(String ort) {

        if(ort==null){
            //fehler loggen
            return List.of();
        }

        WebClient webClient = WebClient.create("https://nominatim.openstreetmap.org");

        try{
            return webClient.get().uri(uriBuilder -> uriBuilder
                                    .path("/search")
                                    .queryParam("q", ort)
                                    .queryParam("format", "json")
                                    .queryParam("countrycodes", "de")
                                    .build()) // GET URI zusammenbauen (mit Platzhalter) 
                            .accept(MediaType.APPLICATION_JSON) // Rückgabeformat JSON wünschen 
                            .retrieve() .bodyToFlux(GeoAdresse.class) // Flux statt Mono, weil Folge von Antworten erwartet 
                            .filter(geoAdresse -> List.of("city", "town", "village").contains(geoAdresse.addresstype()))
                            .collectList() // aus Folge von Antworten Liste machen 
                            .block();
        }catch (WebClientResponseException e){
            return List.of();
        }

    }   
    
}
