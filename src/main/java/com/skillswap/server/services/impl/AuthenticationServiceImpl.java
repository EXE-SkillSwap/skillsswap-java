package com.skillswap.server.services.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.skillswap.server.dto.request.AuthenticationRequest;
import com.skillswap.server.dto.request.ExchangeTokenRequest;
import com.skillswap.server.dto.response.AuthenticationResponse;
import com.skillswap.server.entities.User;
import com.skillswap.server.enums.Role;
import com.skillswap.server.repositories.UserRepository;
import com.skillswap.server.services.AuthenticationService;
import com.skillswap.server.services.GoogleIdentityService;
import com.skillswap.server.services.GoogleUserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String secretKey;
    @NonFinal
    @Value("${jwt.validDuration}")
    protected Long duration;
    @NonFinal
    @Value("${google.identity.clientId}")
    protected String CLIENT_ID;
    @NonFinal
    @Value("${google.identity.clientSecret}")
    protected String CLIENT_SECRET;
    @NonFinal
    @Value("${client.url}")
    protected String REDIRECT_URI;
    @NonFinal
    protected final String GRANT_TYPE = "authorization_code";
    private final GoogleIdentityService googleIdentityService;
    private final GoogleUserService googleUserService;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User authUser = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Tên đăng nhập không đúng"));
        if (!passwordEncoder.matches(request.getPassword(), authUser.getPassword())) {
            throw new RuntimeException("Mật khẩu không đúng");
        }
        String authToken = generateAuthToken(authUser);

        return AuthenticationResponse.builder()
                .token(authToken)
                .isFirstLogin(authUser.isFirstLogin())
                .build();
    }

    private String generateAuthToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        User thisUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(String.valueOf(thisUser.getId()))
                .issuer("SkillSwap")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(duration, ChronoUnit.DAYS).toEpochMilli()))
                .claim("email", thisUser.getEmail())
                .claim("scope", thisUser.getRole())
                .jwtID(String.valueOf(UUID.randomUUID()))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes(StandardCharsets.UTF_8)));
            return jwsObject.serialize();
        }catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private SignedJWT verifyAuthToken(String token) throws JOSEException, ParseException {
        JWSVerifier jwsVerifier = new MACVerifier(secretKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(jwsVerifier);
        if(!(verified && expTime.after(new Date()))){
            throw new JOSEException("Invalid token");
        }
        return signedJWT;
    }

    @Override
    public AuthenticationResponse googleAuthentication(String code) {
        var response = googleIdentityService.exchangeToken(ExchangeTokenRequest.builder()
                .code(code)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .redirectUri(REDIRECT_URI+"/auth")
                .grantType(GRANT_TYPE)
                .build());

        var userInfo = googleUserService.getUserInfo("json", response.getAccessToken());

        var user = userRepository.findByEmail(userInfo.getEmail()).orElseGet(
                () -> {
                    User googleUser = new User();
                    googleUser.setEmail(userInfo.getEmail());
                    googleUser.setUsername(userInfo.getEmail());
                    googleUser.setFirstLogin(true);
                    googleUser.setGoogleAuth(true);
                    googleUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                    googleUser.setRole(Role.USER);
                    googleUser.setFirstName(userInfo.getGivenName());
                    googleUser.setLastName(userInfo.getFamilyName());
                    googleUser.setAvatarUrl(userInfo.getPicture());
                    googleUser.setUsername(userInfo.getEmail().substring(0,userInfo.getEmail().lastIndexOf("@")));

                    userRepository.save(googleUser);
                    return googleUser;
                }
        );

        if(!user.isActive()){
            throw new RuntimeException("Tài khoản của bạn đã bị khóa");
        }else if(!user.isGoogleAuth()){
            throw new RuntimeException("Ban đã đăng nhập bằng tài khoản thường, vui lòng sử dụng tính năng đăng nhập bằng tài khoản thường");
        }

        String authToken = generateAuthToken(user);

        return AuthenticationResponse.builder()
                .token(authToken)
                .isFirstLogin(user.isFirstLogin())
                .build();
    }
}
