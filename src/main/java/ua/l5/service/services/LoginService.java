package ua.l5.service.services;

import ua.l5.service.forms.LoginForm;
import ua.l5.service.transfer.TokenDto;

public interface LoginService {
    TokenDto login(LoginForm loginForm);
}
