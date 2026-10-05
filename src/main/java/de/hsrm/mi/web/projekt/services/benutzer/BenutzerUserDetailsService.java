package de.hsrm.mi.web.projekt.services.benutzer;
import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.entities.benutzer.BenutzerRepository;
import java.util.Optional;

@Service
public class BenutzerUserDetailsService implements UserDetailsService {

    @Autowired
    private BenutzerRepository benutzerRepository;;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Benutzer> benutzerOp = benutzerRepository.findByEmail(username);
        if (!benutzerOp.isPresent()) {
            throw new UsernameNotFoundException("Benutzer nicht gefunden: " + username);
        }

        Benutzer benutzer = benutzerOp.get();

        String role = benutzer.hatMachtVorliebe() ? "CHEF" : "USER";
        return org.springframework.security.core.userdetails.User.withUsername(benutzer.getEmail())
                .password(benutzer.getPasswort())
                .roles(role)
                .build();
    }
    
}
