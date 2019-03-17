package ua.l5.service.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.l5.service.repositories.UsersRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final
    UsersRepository repository;

    @Autowired
    public UserDetailsServiceImpl(UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return new UserDetailsImpl(repository.findOneByLogin(login)
                .orElseThrow(IllegalArgumentException::new));
    }
}
