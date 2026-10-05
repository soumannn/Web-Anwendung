package de.hsrm.mi.web.projekt.ui.tour;


import java.util.Optional;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.hsrm.mi.web.projekt.entities.tour.Tour;
import de.hsrm.mi.web.projekt.services.benutzer.BenutzerService;
import de.hsrm.mi.web.projekt.services.ort.OrtService;
import de.hsrm.mi.web.projekt.services.tour.TourService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RequestMapping("/admin")
@Controller
@SessionAttributes("tourForm")

public class TourController {
    @Autowired
    private TourService tourService;
    @Autowired
    private OrtService ortService;
    @Autowired
    private BenutzerService benutzerService;

    Tour zwischenTour;

    @ModelAttribute("tourForm")
    public TourFormular initTourFormular() {
        return new TourFormular();
    }

    @GetMapping
    public String redirectToTourList() {
        return "redirect:/admin/tour";
    }

    // @GetMapping()
    // public String get_TourListe(Model model) {
    // model.addAttribute("alleTouren", tourService.holeAlleTouren());
    // return "tour/tourliste";
    // }

    @GetMapping("/tour")
    public String get_TourListe(Model model) {
        model.addAttribute("alleTouren", tourService.holeAlleTouren());
        return "tour/tourliste";
    }

    @GetMapping("/tour/{id}/del")
    public String del_Tour(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {

        try {
            tourService.loescheTourMitId(id);
            return "redirect:/admin/tour";
        } catch (AccessDeniedException e) {
            redirectAttributes.addFlashAttribute("info", "Du darfst nicht eine Tour von einem anderen löschen");
            return "redirect:/admin/tour";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("info", "Kein Tour gefunden");
            return "redirect:/admin/tour";
        }
    }

    @GetMapping("/tour/{id}")
    public String get_Tour(@PathVariable("id") int id,
            @ModelAttribute("tourForm") TourFormular form,
            Model m) {

        m.addAttribute("id", id);
        m.addAttribute("ortListe", ortService.holeAlleOrte());
        m.addAttribute("benutzerListe", benutzerService.holeAlleBenutzer());

        if (id == 0) {
            m.addAttribute("tourForm", new TourFormular());
            zwischenTour = new Tour();

        } else {
            Optional<Tour> optionalTour = tourService.holeTourMitId(id);
            if (optionalTour.isPresent()) {
                zwischenTour = optionalTour.get();
                form.fromTour(zwischenTour);
                m.addAttribute("tourForm", form);
            } else {
                // wenn benutzer nich gefunden
                return "redirect:/admin/tour"; // Redirect zu error seite, nachher ist benutzer liste site
            }
        }

        // ansonst bei jede Anfrage wird eine neue Formular erstellt
        // diese Attribute bleibt in Session Scope

        return "tour/tourbearbeiten";
    }

    @PostMapping("/tour/{id}")
    public String post_TourForm(@Valid @ModelAttribute("tourForm") TourFormular form,
            BindingResult result,
            @PathVariable("id") int id,
            Model m) {

        form.toTour(zwischenTour);

        // Fehler zu fangen
        if (result.hasErrors()) {
            m.addAttribute("id", id);
            m.addAttribute("ortListe", ortService.holeAlleOrte());
            m.addAttribute("benutzerListe", benutzerService.holeAlleBenutzer());
            return "tour/tourbearbeiten"; // mit # am Ende des Pfads
        }

        try {
            tourService.speichereTourangebot(form.getAnbieterId(), zwischenTour, form.getStartortId(),
                    form.getZielortId());
            return "redirect:/admin/tour/" + zwischenTour.getId(); // nur bei erfolgreich

        } catch (Exception e) {
            m.addAttribute("info", "Fehler beim Speichern des Ortes: " + e.getMessage());
            return "tour/tourbearbeiten";
        }

    }

}
