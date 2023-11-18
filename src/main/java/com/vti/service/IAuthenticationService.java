package com.vti.service;

import com.vti.entity.User;

public interface IAuthenticationService {
    User signInAndReturnJWT(User signInRequest);
}
