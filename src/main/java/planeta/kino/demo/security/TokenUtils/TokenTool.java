package planeta.kino.demo.security.TokenUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import planeta.kino.demo.security.dto.MyUserDet;

import java.util.*;

@Component
public class TokenTool {

    public String createToken(String email, String role){
        String jwt;

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR,3);
        Date validTo = cal.getTime();

        jwt = Jwts.builder().setIssuer("OurProject")
                .claim("email",email)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(validTo)
                .signWith(SignatureAlgorithm.HS256,"ourKey")
                .compact();
        return jwt;
    }

    public boolean isTokenValid(String token){
        boolean valid  = false;
        try{
            Jwts.parser().setSigningKey("ourKey").parseClaimsJws(token);
            valid = true;
        }catch (Exception e){
        }
        return valid;
    }

    public UserDetails getUserByToken(String token){
        Claims claims = Jwts.parser().setSigningKey("ourKey").parseClaimsJws(token).getBody();
        String role = (String) claims.get("role");
        GrantedAuthority role1 = new SimpleGrantedAuthority(role);
        String email = (String) claims.get("email");
        return new MyUserDet(email, Collections.singletonList(role1));
    }
}
