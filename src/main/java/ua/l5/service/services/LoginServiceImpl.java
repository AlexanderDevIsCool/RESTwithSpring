package ua.l5.service.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sun.security.util.Password;
import ua.l5.service.forms.LoginForm;
import ua.l5.service.models.Token;
import ua.l5.service.models.User;
import ua.l5.service.repositories.TokensRepository;
import ua.l5.service.repositories.UsersRepository;
import ua.l5.service.transfer.TokenDto;

import java.util.Optional;

import static ua.l5.service.transfer.TokenDto.from;

@Component
public class LoginServiceImpl implements LoginService {

    private final TokensRepository tokensRepository;

    private final PasswordEncoder passwordEncoder;

    private final UsersRepository usersRepository;

    @Autowired
    public LoginServiceImpl(TokensRepository tokensRepository, PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
        this.tokensRepository = tokensRepository;
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }

    @Override
    public TokenDto login(LoginForm loginForm) {
        Optional<User> userCandidate = usersRepository.findOneByLogin(loginForm.getLogin());
        if(userCandidate.isPresent()){
            User user = userCandidate.get();

            if(passwordEncoder.matches(loginForm.getPassword(), user.getHashPassword())) {
                Token token = Token.builder()
                        .user(user)
                        .value(RandomStringUtils.random(10, true, true))
                        .build();
                tokensRepository.save(token);
                return from(token);
            }
        } throw new IllegalArgumentException("User not found");
    }
}
