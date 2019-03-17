package ua.l5.service.services;


import ua.l5.service.forms.UserForm;
import ua.l5.service.models.User;

import java.util.List;

public interface UserService {
    void signUp(UserForm userForm);

    List<User> findAll();

    User findOne(Long userId);
}
