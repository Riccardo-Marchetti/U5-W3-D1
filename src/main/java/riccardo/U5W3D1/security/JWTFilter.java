package riccardo.U5W3D1.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import riccardo.U5W3D1.exceptions.UnauthorizedException;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Controlliamo se nella richiesta corrente ci sia un Authorization Header, se non c'è --> 401
          String authHeader = request.getHeader("Authorization");

        // 2. Se c'è estraiamo il token dall'header
            if (authHeader == null || !authHeader.startsWith("Bearer ")) throw new UnauthorizedException("Inserisci il token nell'Authorization Header");

            String accessToken = authHeader.substring(7);

        // 3. Verifichiamo se il token è stato manipolato (verifica della signature) e se non è scaduto (verifica Expiration Date)
            jwtTools.verifyToken(accessToken);

        // 4. Se tutto è OK andiamo al prossimo elemento della Filter Chain, per prima o poi arrivare all'endpoint
            filterChain.doFilter(request, response); // vado al prossimo elemento della catena
        // 5. Se il token non fosse OK --> 401

    }

    // disabilito il filtro per determinare richieste di tipo login o register
    @Override
    protected boolean shouldNotFilter (HttpServletRequest request){
        // metodo per specificare quando non deve FILTRARE
        // se l'URL della richiesta corrente corrisponde a auth/login non filtrare
        return new AntPathMatcher().match("/auth/**", request.getServletPath());

    }
}
