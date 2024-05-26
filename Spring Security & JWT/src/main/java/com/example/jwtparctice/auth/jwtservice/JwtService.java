package com.example.jwtparctice.auth.jwtservice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

//    Token Validity time
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

//    secrete key
    private String signinKey = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String,Object> claims,String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(getSigningKey() ,SignatureAlgorithm.HS256).compact();
    }

//    Generate a token
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

//    valid token or not
    public Boolean validateToken(String token,UserDetails userDetails){
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(signinKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }



//    for retrieveing any infromation from token we will need the secret key
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(signinKey).parseClaimsJws(token).getBody();
    }

    public <T> T getClaimsFromToken(String token, Function<Claims,T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //    getUser name from token
    public String getUsernameFromToken(String token){
        return getClaimsFromToken(token,Claims::getSubject);
    }

//    Get Expiration date from jwt token
    public Date getExpirationDateFromToken(String token){
        return getClaimsFromToken(token,Claims::getExpiration);
    }


//    is token expired or not
    public Boolean isTokenExpired(String token){
        final Date expirationToken = getExpirationDateFromToken(token);
        return expirationToken.before(new Date());
    }

}
