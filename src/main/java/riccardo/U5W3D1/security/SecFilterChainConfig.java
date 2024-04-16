package riccardo.U5W3D1.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecFilterChainConfig {

    // CONFIGURO LA SECURITY FILTER CHAIN
    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        // DISABILITO COMPORTAMENTI DI DEFAULT

        // DISABILITO IL FORM DI LOGIN
        httpSecurity.formLogin(http -> http.disable());
        // DISABILITO LA PROTEZIONE DA CSRF
        httpSecurity.csrf(http -> http.disable());
        // TOLGO LE SESSIONI
        httpSecurity.sessionManagement(http -> http.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // CONSENTO A TUTTE LE RICHIESTE DI PASSARE ATTRAVERSO LA FILTER CHAIN, TUTTE LE RISORSE SONO ACCESSIBILI A TUTTI GLI UTENTI CON AUTENTICAZIONE O MENO
        httpSecurity.authorizeHttpRequests(http -> http.requestMatchers("/**").permitAll());

        return httpSecurity.build();
    }


    @Bean
    PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder(11);
    }
}
