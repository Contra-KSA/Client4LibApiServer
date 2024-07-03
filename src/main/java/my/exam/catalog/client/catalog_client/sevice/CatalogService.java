package my.exam.catalog.client.catalog_client.sevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import my.exam.catalog.client.catalog_client.dto.BookDTO;
import my.exam.catalog.client.catalog_client.dto.UserDto;
import my.exam.catalog.client.catalog_client.entity.User;
import my.exam.catalog.client.catalog_client.repository.SessionDataRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CatalogService {

    private SessionDataRepository repo;
    private RequestService requestService;

    public Boolean isCurrentUserAuthorized() {
        String jwt = repo.getCurrentUser().getJwtToken();
        return jwt != null && !jwt.isBlank();
    }

    public void setCurrentUser(UserDto user, String jwtToken) {
        repo.setCurrentUser(new User(user.getUsername(), jwtToken));
    }

    public List<BookDTO> searchByTitleAnfYear(String title, Integer year) throws Exception {
        setSearchResult(null);
        try {
            if ((Objects.isNull(title) || title.isEmpty()) && (Objects.isNull(year) || year.equals(0))) {
                return requestService.findAll();
            } else {
                return requestService.searchByTitleAndYear(title, year);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getCurrentUserJwtToken() {
        return repo.getCurrentUserJwt();
    }

    public void setSearchResult(List<BookDTO> books) {
        if (Objects.isNull(books)) {
            repo.setSearchResult(new ArrayList<>());
        } else {
            repo.setSearchResult(books);
        }
    }

    public List<BookDTO> getSearchResult() {
        return repo.getSearchResult();
    }
}
