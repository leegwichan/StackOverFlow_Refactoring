package com.team17.preProject.security.auth;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String errorMessage;
        if (exception instanceof BadCredentialsException) {
            errorMessage = "Email or Password id Not Matched";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "Account is not Exist";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "Account is not Exist";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            errorMessage = "Authentication is Denied";
        } else {
            errorMessage = "Sorry, Developer's mistake. Just send Email To Developer";
        }

        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
        setDefaultFailureUrl("/auth/fail?message="+errorMessage);
        super.onAuthenticationFailure(request, response, exception);
    }
}
