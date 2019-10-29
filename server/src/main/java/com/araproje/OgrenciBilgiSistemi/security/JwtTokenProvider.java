package com.araproje.OgrenciBilgiSistemi.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Component;

import com.araproje.OgrenciBilgiSistemi.model.User;
import com.araproje.OgrenciBilgiSistemi.util.MessageConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
		
	@Value("${app.jwtSecret}")
    private String jwtSecret;
	
	@Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;
	
	public Claims generateToken(Authentication authentication) {

    	LdapUserDetailsImpl userPrincipal = (LdapUserDetailsImpl) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        
        Claims claims = Jwts.claims()
				.setExpiration(expiryDate)
				.setSubject(userPrincipal.getUsername());
        //claims.put("roles", userPrincipal.getAuthorities());
        System.out.println(userPrincipal.getAuthorities().getClass());
		// USERID EKLE
        
        return claims;
    }
	
	//@SuppressWarnings("unchecked")
	public User getUserFromJWT(String token) {
		
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        
        User user = new User();
        user.setUserName(claims.getSubject());
        //user.setRoles((ArrayList<String>) claims.get("roles"));
        //System.out.println("DENEME= "+user.getRoles());
        //USERID EKLE KOY
        return user;
    }
	
	public boolean validateToken(String authToken) throws Exception {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            throw new Exception(MessageConstants.INVALID_JWT_SIGNATURE);
        } catch (MalformedJwtException ex) {
        	throw new Exception(MessageConstants.INVALID_TOKEN);
        } catch (ExpiredJwtException ex) {
        	throw new Exception(MessageConstants.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException ex) {
        	throw new Exception(MessageConstants.UNSUPPORTED_TOKEN);
        }
    }
}
