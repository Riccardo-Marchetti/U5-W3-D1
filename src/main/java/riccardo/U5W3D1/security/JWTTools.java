package riccardo.U5W3D1.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import riccardo.U5W3D1.entities.Employee;
import riccardo.U5W3D1.exceptions.UnauthorizedException;

import java.util.Date;

@Component
public class JWTTools {

    @Value("${JWT_SECRET}")
    private String secret;

    // METODO PER LA GENERAZIONE DEL TOKEN
    public String createToken (Employee employee){
        return Jwts.builder().issuedAt(new Date(System.currentTimeMillis())) // data di emissione del token in millisecondi
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // data di scadenza del token
                .subject(String.valueOf(employee.getId())) // indica a chi appartiene il token
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())) // firmo il token con l'algoritmo HMAC passandogli il segreto
                .compact(); // compatta tutto in una stringa
    }

    // VERIFICO IL TOKEN
    public void verifyToken(String token){
        try {
        Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())) // verifica il token con quel segreto
                .build() // mi costruisce l'oggetto che puo fare l'analisi
                .parse(token); // lancia delle eccezioni in caso di token scaduto o manipolato
        } catch (Exception ex) {
            throw new UnauthorizedException("Problemi con il token effettua di nuovo il login");
        }
    }
}
