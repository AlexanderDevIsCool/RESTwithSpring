package ua.l5.service.security.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ua.l5.service.models.Token;
import ua.l5.service.models.User;
import ua.l5.service.repositories.TokensRepository;
import ua.l5.service.security.token.TokenAuthentication;

import java.util.Optional;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final TokensRepository tokensRepository;

    private final UserDetailsService userDetailsService;

    @Autowired
    public TokenAuthenticationProvider(TokensRepository tokensRepository, UserDetailsService userDetailsService) {
        this.tokensRepository = tokensRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;
        Optional<Token> tokenCandidate = tokensRepository.findOneByValue(tokenAuthentication.getName());
        if(tokenCandidate.isPresent()) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(tokenCandidate.get().getUser().getLogin());
            tokenAuthentication.setUserDetails(userDetails);
            tokenAuthentication.setAuthenticated(true);
            return tokenAuthentication;
        } throw new IllegalArgumentException("Bad token");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return TokenAuthentication.class.equals(aClass);
    }
}
