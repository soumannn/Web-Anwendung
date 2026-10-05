package de.hsrm.mi.web.projekt.ui.benutzer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.services.benutzer.BenutzerService;
import de.hsrm.mi.web.projekt.services.benutzer.BenutzerUserDetailsService;
import jakarta.validation.Valid;

//@RestController
@RequestMapping("/admin/benutzer")
@Controller
@SessionAttributes("benutzerForm")
public class BenutzerController {

    @Autowired
    private BenutzerService benutzerService;

    @Autowired
    private BenutzerUserDetailsService benutzerUserDetailsService;

    @Autowired
    private PasswordEncoder pwEncoder;

    public static final int MAXWUNSCH = 5;
    Benutzer zwischenBenutzer;

    // @ModelAttribute("benutzerForm")
    // public void initListe(Model m) {

    // }
    @ModelAttribute("benutzerForm")
    public BenutzerFormular initBenutzerFormular() {
        return new BenutzerFormular();
    }

    @GetMapping()
    public String get_BenutzerListe(Model model) {
        model.addAttribute("alleBenutzer", benutzerService.holeAlleBenutzer());
        return "benutzerliste";
    }

    @GetMapping("/{benutzerNr}/del")
    public String del_Benutzer(@PathVariable("benutzerNr") int benutzerNr) {
        benutzerService.loescheBenutzerMitId(benutzerNr);
        return "redirect:/admin/benutzer";
    }

    @GetMapping("/{benutzerid}/hx/feld/{feldname}")
    public String get_Benutzerfeld(@PathVariable("benutzerid") Long benutzerid,
            @PathVariable("feldname") String feldname,
            Model m) {

        Optional<Benutzer> optionalBenutzer = benutzerService.holeBenutzerMitId(benutzerid);
        if (optionalBenutzer.isPresent()) {
            zwischenBenutzer = optionalBenutzer.get();
        } else {
            //  wenn benutzer nich gefunden
            return "redirect:/admin/benutzer"; // Redirect zu error seite, nachher ist benutzer liste site
        }
        String wert = "";
        switch (feldname) {
            case "name":
                wert = zwischenBenutzer.getName();
                break;
            case "email":
                wert = zwischenBenutzer.getEmail();
                break;
        }
        m.addAttribute("benutzerid", benutzerid);
        m.addAttribute("feldname", feldname);
        m.addAttribute("wert", wert);
        return "fragments/benutzerliste-zeile :: feldbearbeiten";
    }

    @PostMapping("/{benutzerid}/hx/feld/{feldname}")
    public String post_Benutzerfeld(@PathVariable("benutzerid") Long benutzerid,
            @PathVariable("feldname") String feldname,
            @RequestParam("wert") String wert,
            Model m) {

        Optional<Benutzer> optionalBenutzer = benutzerService.holeBenutzerMitId(benutzerid);
        if (optionalBenutzer.isPresent()) {
            zwischenBenutzer = optionalBenutzer.get();
        } else {
            //  wenn benutzer nich gefunden
            return "redirect:/admin/benutzer"; // Redirect zu error seite, nachher ist benutzer liste site
        }

        try {
            benutzerService.aktualisiereBenutzerAttribut(benutzerid, feldname, wert);
            m.addAttribute("wert", wert);
        } catch (Exception e) {
            // On exception, get the current value from the database
            m.addAttribute("wert", wert);
            return "fragments/benutzerliste-zeile :: feldbearbeiten";
        }

        m.addAttribute("benutzerid", benutzerid);
        m.addAttribute("feldname", feldname);
        m.addAttribute("wert", wert);
        return "fragments/benutzerliste-zeile :: feldausgeben";
    }

    @GetMapping("/{benutzerNr}")
    public String get_Benutzer(@PathVariable("benutzerNr") int benutzerNr,
            @ModelAttribute("benutzerForm") BenutzerFormular form,
            Model m) {

        m.addAttribute("benutzerNr", benutzerNr);
        m.addAttribute("MAXWUNSCH", MAXWUNSCH);

        if (benutzerNr == 0) {
            m.addAttribute("benutzerForm", new BenutzerFormular());
            zwischenBenutzer = new Benutzer();

        } else {
            Optional<Benutzer> optionalBenutzer = benutzerService.holeBenutzerMitId(benutzerNr);
            if (optionalBenutzer.isPresent()) {
                zwischenBenutzer = optionalBenutzer.get();
                form.fromBenutzer(zwischenBenutzer);
                m.addAttribute("benutzerForm", form);
            } else {
                // wenn benutzer nich gefunden
                return "redirect:/admin/benutzer"; // Redirect zu error seite, nachher ist benutzer liste site
            }
        }

        // ansonst bei jede Anfrage wird eine neue Formular erstellt
        // diese Attribute bleibt in Session Scope

        return "benutzerbearbeiten";
    }

    @Autowired
    private MessageSource messageSource;

    @PostMapping("/{benutzerNr}")
    public String post_benutzerForm(@Valid @ModelAttribute("benutzerForm") BenutzerFormular form,
            BindingResult result,
            @PathVariable("benutzerNr") int benutzerNr,
            Model m) {

        form.toBenutzer(zwischenBenutzer);
        zwischenBenutzer.setPasswort(pwEncoder.encode(zwischenBenutzer.getPasswort()));

        if (benutzerNr == 0 && (form.getPasswort() == null || form.getPasswort().trim().isEmpty())) {

            String errorMessage = messageSource.getMessage("passwort.fehler", null, LocaleContextHolder.getLocale());
            result.rejectValue("passwort", "benutzer.passwort.ungesetzt", errorMessage);

        }

        // Fehler zu fangen
        if (result.hasErrors()) {

            return "benutzerbearbeiten"; // mit # am Ende des Pfads
        }

        try {
            benutzerService.speichereBenutzer(zwischenBenutzer);
            return "redirect:/admin/benutzer/" + zwischenBenutzer.getId(); // nur bei erfolgreich

        } catch (Exception e) {
            m.addAttribute("info", "Fehler beim Speichern des Benutzers: " + e.getMessage());
            return "benutzerbearbeiten";
        }

    }

}
