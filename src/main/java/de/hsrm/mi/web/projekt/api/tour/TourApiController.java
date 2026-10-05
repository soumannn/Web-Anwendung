package de.hsrm.mi.web.projekt.api.tour;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.web.projekt.entities.tour.Tour;
import de.hsrm.mi.web.projekt.services.tour.TourDTD;
import de.hsrm.mi.web.projekt.services.tour.TourService;

@RestController
@RequestMapping("/api/tour")
public class TourApiController {
    @Autowired
    TourService tourService;

    @GetMapping()
    public ResponseEntity<List<TourDTD>> getAllOrte() {
        List<Tour> alleToure = tourService.holeAlleTouren();
        List<TourDTD> tourDTDs = alleToure.stream()
                                        .map(tour -> TourDTD.fromTour(tour))
                                        .collect(Collectors.toList());
        return new ResponseEntity<>(tourDTDs, HttpStatus.OK);
    }
}
