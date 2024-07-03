package my.exam.catalog.client.catalog_client.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import my.exam.catalog.client.catalog_client.dto.BookDTO;
import my.exam.catalog.client.catalog_client.dto.UserDto;
import my.exam.catalog.client.catalog_client.sevice.AuthService;
import my.exam.catalog.client.catalog_client.sevice.CatalogService;
import my.exam.catalog.client.catalog_client.sevice.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/catalog")
@AllArgsConstructor
public class CatalogController {

    private CatalogService catalogService;
    private AuthService authService;
    private RequestService requestService;

    @GetMapping
    public ModelAndView welcome(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!catalogService.isCurrentUserAuthorized()) {
            response.sendRedirect("/auth");
            return null;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/index.jsp");
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "year", required = false) Integer year,
            HttpServletResponse response) throws IOException {
        if (!catalogService.isCurrentUserAuthorized()) {
            response.sendRedirect("/auth");
            return null;
        }
        List<BookDTO> books = null;
        try {
            books = catalogService.searchByTitleAnfYear(title, year);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ModelAndView modelAndView = new ModelAndView("/cataloglist.jsp");
        catalogService.setSearchResult(books);
        response.sendRedirect("/catalog/list");
        return null;
    }

    @PostMapping
    public ModelAndView find(@RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "year", required = false) Integer year,
            HttpServletResponse response) throws IOException {
        return new ModelAndView("/index.jsp");
    }

    @GetMapping("/list")
    public ModelAndView listBooks(HttpServletResponse response) throws IOException {
        if (!catalogService.isCurrentUserAuthorized()) {
            response.sendRedirect("/auth");
            return null;
        }
        ModelAndView modelAndView = new ModelAndView("/cataloglist.jsp");
        List<BookDTO> books = catalogService.getSearchResult();

        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @PostMapping("/list")
    public ModelAndView postListBooks(HttpServletResponse response) throws IOException{
        return listBooks(response);
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletResponse response) throws IOException {
        authService.logout();
        response.sendRedirect("/catalog");
        return null;    }

    @GetMapping("/users")
    public ModelAndView createUser(HttpServletResponse response) throws IOException {
        if (!catalogService.isCurrentUserAuthorized()) {
            response.sendRedirect("/catalog");
            return null;
        }
        return new ModelAndView("/createuser.jsp");
    }

    @PostMapping("/users")
    public ModelAndView createUser(@RequestParam("login") String login,
            @RequestParam("password") String password,
            HttpServletResponse response) throws IOException {
        if (!catalogService.isCurrentUserAuthorized()) {
            response.sendRedirect("/catalog");
            return null;
        }
        UserDto user = new UserDto(login, password);
        ResponseEntity<UserDto> createResponse = requestService.createUser(user);
        ModelAndView modelAndView = new ModelAndView("/createuser.jsp");
        if (createResponse.getStatusCode().value() == 200) {
            modelAndView.addObject("message", "OK");
        } else {
            modelAndView.addObject("message", "Creation forbidden");
        }
        return modelAndView;
    }
}
