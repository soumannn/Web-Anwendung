package de.hsrm.mi.web.projekt.ui.ort;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

import de.hsrm.mi.web.projekt.entities.ort.Ort;
import de.hsrm.mi.web.projekt.services.ort.OrtService;
import jakarta.validation.Valid;

@RequestMapping("/admin/ort")
@Controller
@SessionAttributes("ortForm")
public class OrtController {

    @Autowired
    private OrtService ortService;

    Ort zwischenOrt;

    @ModelAttribute("ortForm")
    public OrtFormular initOrtFormular() {
        return new OrtFormular();
    }

    @GetMapping()
    public String get_OrtListe(Model model) {
        model.addAttribute("alleOrte", ortService.holeAlleOrte());
        return "ort/ortliste";
    }

    @GetMapping("/{id}/del")
    public String del_Ort(@PathVariable("id") int id) {
        ortService.loescheOrtMitId(id);
        return "redirect:/admin/ort";
    }

    @GetMapping("/{id}")
    public String get_Ort(@PathVariable("id") int id,
            @ModelAttribute("ortForm") OrtFormular form,
            Model m) {

        m.addAttribute("id", id);

        if (id == 0) {
            m.addAttribute("ortForm", new OrtFormular());
            zwischenOrt = new Ort();

        } else {
            Optional<Ort> optionalOrt = ortService.holeOrtMitId(id);
            if (optionalOrt.isPresent()) {
                zwischenOrt = optionalOrt.get();
                form.fromOrt(zwischenOrt);
                m.addAttribute("ortForm", form);
            } else {
                // wenn benutzer nich gefunden
                return "redirect:/admin/ort"; // Redirect zu error seite, nachher ist benutzer liste site
            }
        }

        // ansonst bei jede Anfrage wird eine neue Formular erstellt
        // diese Attribute bleibt in Session Scope

        return "ort/ortbearbeiten";
    }

    @Autowired
    private MessageSource messageSource;

    @PostMapping("/{id}")
    public String post_ortForm(@Valid @ModelAttribute("ortForm") OrtFormular form,
            BindingResult result,
            @PathVariable("id") int id,
            Model m) {

        form.toOrt(zwischenOrt);

        // Fehler zu fangen
        if (result.hasErrors()) {

            return "ort/ortbearbeiten"; // mit # am Ende des Pfads
        }
        if (zwischenOrt.getGeobreite() == 0.0 && zwischenOrt.getGeolaenge() == 0) {
            List<Ort> ortsVorschlaege = ortService.findeOrtsvorschlaegeFuerAdresse(zwischenOrt.getName());
            if (!ortsVorschlaege.isEmpty()) {
                Ort ersterVorschlag = ortsVorschlaege.get(0);
                zwischenOrt.setGeobreite(ersterVorschlag.getGeobreite());
                zwischenOrt.setGeolaenge(ersterVorschlag.getGeolaenge());

                form.fromOrt(zwischenOrt);
                String bestaetigungMessage = messageSource.getMessage("bestaetigen.validator", null,
                        LocaleContextHolder.getLocale());
                m.addAttribute("info", bestaetigungMessage);
            } else {
                String bestaetigungMessage = messageSource.getMessage("bestaetigen.fehler", null,
                        LocaleContextHolder.getLocale());
                m.addAttribute("info", bestaetigungMessage);
            }
            m.addAttribute("ortForm", form);
            return "ort/ortbearbeiten";
        }

        try {
            ortService.speichereOrt(zwischenOrt);
            return "redirect:/admin/ort/" + zwischenOrt.getId(); // nur bei erfolgreich

        } catch (Exception e) {
            m.addAttribute("info", "Fehler beim Speichern des Ortes: " + e.getMessage());
            return "ort/ortbearbeiten";
        }

    }

}
