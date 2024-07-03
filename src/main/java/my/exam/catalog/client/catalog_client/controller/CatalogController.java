package my.exam.catalog.client.catalog_client.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import my.exam.catalog.client.catalog_client.dto.BookDTO;
import my.exam.catalog.client.catalog_client.sevice.AuthService;
import my.exam.catalog.client.catalog_client.sevice.CatalogService;
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
}
