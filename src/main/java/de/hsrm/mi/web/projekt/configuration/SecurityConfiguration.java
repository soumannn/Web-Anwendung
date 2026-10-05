package de.hsrm.mi.web.projekt.configuration;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.security.config.Customizer.withDefaults;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // @Bean -> Encoder woanders per @Autowired abrufbar
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // @Bean
    // public UserDetailsService userDetailsService() {
    //     // Klartext-Passwort codiert speichern
    //     UserBuilder userbuilder = User.withDefaultPasswordEncoder();
        
    //     UserDetails user1 = userbuilder.username("joendhard@diebiffels.de")
    //                                    .password("1234")
    //                                    .roles("USER")
    //                                    .build();
        
    //     UserDetails user2 = userbuilder.username("joghurta@diebiffels.de")
    //                                    .password("5678")
    //                                    .roles("CHEF")
    //                                    .build();
        
    //     return new InMemoryUserDetailsManager(user1, user2);
    // }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(toH2Console()).permitAll()
                .requestMatchers("/api/**", "/h2-console/**").permitAll()
                .requestMatchers("/admin/ort/**").hasRole("CHEF")
                .requestMatchers("/admin/**").authenticated()
                // Alle anderen Requests müssen authentifiziert sein
                .anyRequest().permitAll())
                //.anyRequest().authenticated())
            .formLogin(withDefaults())
            .logout(out -> out
            .logoutUrl("/logout"))               
            .csrf(csrf-> csrf.ignoringRequestMatchers(toH2Console()))
            .headers(hdrs-> hdrs.frameOptions(fo-> fo.sameOrigin()));
            

        

        return http.build();
    }

    
    
}
