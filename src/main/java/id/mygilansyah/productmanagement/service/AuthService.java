package id.mygilansyah.productmanagement.service;

import id.mygilansyah.productmanagement.dto.AuthDTO;
import id.mygilansyah.productmanagement.model.User;
import id.mygilansyah.productmanagement.repository.RolesRepository;
import id.mygilansyah.productmanagement.repository.UserRepository;
import id.mygilansyah.productmanagement.util.exception.CustomException;
import id.mygilansyah.productmanagement.util.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthService {

    private final UserService userService;
    private final RolesServices rolesServices;
    private final UserRepository userRepository;
    private final Argon2PasswordEncoder passwordEncoder;

    public AuthService(UserService userService, RolesServices rolesServices, UserRepository userRepository, Argon2PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.rolesServices = rolesServices;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${jwt.secret}")
    private String SECRET;
    @Value("${user.token.expiredTime}")
    private Long EXPIRED_TIME;
    private static final String PREFIX = "Bearer ";


    @Transactional
    public AuthDTO.LoginResponse login(AuthDTO.LoginRequest loginRequest) throws CustomException {
        User user = userRepository.findTopByUsernameOrEmailAndDeleted(loginRequest.getUsername(), loginRequest.getUsername(), false)
                .orElseThrow(() -> new CustomException("Username / email isn't available", ErrorCode.GENERIC_FAILURE));
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            isLogin(user);
            user.setLoginStatus(true);
            user.setTokenExpiryDate(LocalDateTime.now().plusMinutes(EXPIRED_TIME));
            user.setWrongPasswordCount(0);
            userRepository.save(user);
            return toDTO(user);
        } else {
            wrongPassword(user);
            log.info("Wrong password");
            throw new CustomException("Wrong password", ErrorCode.GENERIC_FAILURE);
        }
    }

    @Transactional
    public String logout (String authHeader) throws CustomException {
        String status = "";
        String token = authHeader.replace(PREFIX, "").trim();
        String username = getClaims(token).getSubject();
        User user = userRepository.findByUsernameAndDeleted(username, false)
                .orElseThrow(() -> new CustomException("User tidak terdaftar", ErrorCode.GENERIC_FAILURE));
        user.setLoginStatus(false);
        userRepository.save(user);
        status = "Success Logout";
        return status;
    }

    private Claims getClaims(String token) throws CustomException {
        try {
            return Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (SignatureException e){
            throw new CustomException("Signature exception", ErrorCode.GENERIC_FAILURE);
        }
    }

    public String getJWTToken(AuthDTO.LoginResponse response){
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(checkAuthorityString(response));
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

        String token = Jwts
                .builder()
                .id(UUID.randomUUID().toString())
                .subject(response.getUser().getUsername())
                .claim("authorities", grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (EXPIRED_TIME*60*1000)))
                .signWith(key)
                .compact();
        return PREFIX + token;
    }

    private String checkAuthorityString(AuthDTO.LoginResponse loginResponse) {
        String authorities = "";
        if (loginResponse.getRoles().getRolename().equals("ADMIN")) {
            authorities = authorities + "ROLE_ADMIN";
        } else {
            authorities = authorities + "ROLE_CUSTOMER";
        }

        return authorities;
    }

    private void isLogin(User user) throws CustomException {
        if (user.getLoginStatus()) {
            throw new CustomException("User already logged in", ErrorCode.GENERIC_FAILURE);
        }
    }

    private void wrongPassword(User user) throws CustomException {
        if (user.getWrongPasswordCount() != null) {
            if (user.getWrongPasswordCount() >+ 3) {
                user.setActive(false);
                user.setPasswordExpired(true);
                userRepository.save(user);
                throw new CustomException("Your account is blocked, please contact admin", ErrorCode.GENERIC_FAILURE);
            } else {
                user.setWrongPasswordCount(user.getWrongPasswordCount() + 1);
                userRepository.save(user);
            }
        }
    }

    private AuthDTO.LoginResponse toDTO(User user) {
        AuthDTO.LoginResponse loginResponse = new AuthDTO.LoginResponse();
        loginResponse.setUser(userService.toDTO(user));
        loginResponse.setRoles(rolesServices.toDTO(user.getRole()));
        loginResponse.setExpiredLogin(user.getTokenExpiryDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return loginResponse;
    }
}
