package my.exam.catalog.client.catalog_client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import my.exam.catalog.client.catalog_client.dto.UserDto;
import my.exam.catalog.client.catalog_client.sevice.AuthService;
import my.exam.catalog.client.catalog_client.sevice.CatalogService;
import my.exam.catalog.client.catalog_client.sevice.HeaderValues;
import my.exam.catalog.client.catalog_client.sevice.RequestService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final RequestService requestService;
    private final CatalogService catalogService;
    private final AuthService authService;

    @GetMapping
    public ModelAndView auth() {
        return new ModelAndView("login.jsp");
    }

    @PostMapping
    public ModelAndView authorize(@RequestParam("login") String login,
            @RequestParam("password") String password,
            HttpServletResponse response) throws IOException {
        UserDto user = new UserDto(login, password);
        ResponseEntity<UserDto> authResponse = requestService.authorize(user);
        if (authResponse.getStatusCode() == HttpStatus.OK) {
            HttpHeaders headers = authResponse.getHeaders();
            catalogService.setCurrentUser(user, headers.getFirst(HeaderValues.AUTHORIZATION));
            response.sendRedirect("/catalog");
            return null;
        } else {
            return new ModelAndView("login.jsp").addObject("message", "Access Denied");
        }
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletResponse response) throws IOException {
        authService.logout();
        response.sendRedirect("/catalog");
        return null;
    }

    @GetMapping("/user")
    public ModelAndView createUser(){
        return new ModelAndView("create_user.jsp");
    }
}
