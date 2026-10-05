package de.hsrm.mi.web.projekt.services.tour;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.entities.benutzer.BenutzerRepository;
import de.hsrm.mi.web.projekt.entities.ort.Ort;
import de.hsrm.mi.web.projekt.entities.ort.OrtRepository;
import de.hsrm.mi.web.projekt.entities.tour.Tour;
import de.hsrm.mi.web.projekt.entities.tour.TourRepository;
import de.hsrm.mi.web.projekt.messaging.FrontendNachrichtEvent;
import de.hsrm.mi.web.projekt.messaging.FrontendNachrichtService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TourServiceImpl implements TourService {
    @Autowired
    private FrontendNachrichtService frontendNachrichtService;
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private OrtRepository ortRepository;
    @Autowired
    private BenutzerRepository benutzerRepository;

    @Override
    public List<Tour> holeAlleTouren() {
        // TODO Auto-generated method stub
        return tourRepository.findAll();
    }

    @Override
    public Optional<Tour> holeTourMitId(long id) {
        // TODO Auto-generated method stub
        return tourRepository.findById(id);
    }

        public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        return null;
    }

    @Override
    public void loescheTourMitId(long id) {

        Optional<Tour> optTour = tourRepository.findById(id);
        if (optTour.isPresent()) {
            Tour tour = optTour.get();
            String currentUserEmail = getCurrentUserEmail();
            if (tour.getAnbieter().getEmail().equals(currentUserEmail)) {

                frontendNachrichtService.sendEvent(new FrontendNachrichtEvent(
                    FrontendNachrichtEvent.EventType.TOUR,
                    id,
                    FrontendNachrichtEvent.Operation.DELETE
                ));
        
                tourRepository.deleteById(id);
            } else {
                throw new AccessDeniedException("access denied");
            }
        } else {
            throw new EntityNotFoundException("entity not found");
        }


    }

    @Override
    public Tour speichereTourangebot(long anbieterid, Tour tour, long startortid, long zielortid) {
        // TODO Auto-generated method stub
        Optional<Benutzer> anbieterOp = benutzerRepository.findById(anbieterid);
        Optional<Ort> startortOp = ortRepository.findById(startortid);
        Optional<Ort> zielortOp = ortRepository.findById(zielortid);

        if (!anbieterOp.isPresent() || !startortOp.isPresent() || !zielortOp.isPresent()) {
            throw new IllegalArgumentException("Anbieter oder Orte nicht gefunden");
        }
        Benutzer anbieter = anbieterOp.get();
        Ort startOrt = startortOp.get();
        Ort zielort = zielortOp.get();
        tour.setAnbieter(anbieter);
        tour.setStartort(startOrt);
        tour.setZielort(zielort);

        boolean istTourNeu = (tour.getId() == 0);
        Tour zuspeicherndesTour = tourRepository.save(tour);

        FrontendNachrichtEvent.Operation operation = istTourNeu ? FrontendNachrichtEvent.Operation.CREATE : FrontendNachrichtEvent.Operation.UPDATE;
        frontendNachrichtService.sendEvent(new FrontendNachrichtEvent(
            FrontendNachrichtEvent.EventType.TOUR,
            zuspeicherndesTour.getId(),
            operation
        ));

        return zuspeicherndesTour;
 
    }

}
