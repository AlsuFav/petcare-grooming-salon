package ru.fav.petcare.grooming.salon.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.fav.petcare.grooming.salon.exception.UnauthorizedAccessException;

@Component
@RequiredArgsConstructor
public class AuthUtils {

    private final JwtTokenUtils jwtTokenUtils;

    public Long getCurrentClientId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedAccessException("Пользователь не аутентифицирован");
        }

        String clientId = authentication.getName();
        return Long.valueOf(clientId);
    }
}
