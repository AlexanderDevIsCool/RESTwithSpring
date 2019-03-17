package ua.l5.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.l5.service.forms.UserForm;
import ua.l5.service.models.Role;
import ua.l5.service.models.State;
import ua.l5.service.models.User;
import ua.l5.service.repositories.UsersRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UsersRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUp(UserForm userForm) {
        String hashPassword = passwordEncoder.encode(userForm.getPassword());
        User user = User
                .builder()
                .firstName(userForm.getFirstName())
                .lastName(userForm.getLastName())
                .hashPassword(hashPassword)
                .login(userForm.getLogin())
                .role(Role.USER)
                .state(State.ACTIVE).build();
        repository.save(user);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findOne(Long userId) {
        return repository.findOne(userId);
    }
}
