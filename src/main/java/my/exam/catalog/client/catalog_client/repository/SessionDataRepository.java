package my.exam.catalog.client.catalog_client.repository;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import my.exam.catalog.client.catalog_client.dto.BookDTO;
import my.exam.catalog.client.catalog_client.entity.User;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
@Getter
@Setter
public class SessionDataRepository {

    private User currentUser;
    private List<BookDTO> result = new ArrayList<>();

    public String getCurrentUserJwt() {
        return currentUser.getJwtToken();
    }

    public void setSearchResult(List<BookDTO> books) {
        result = books;
    }

    public List<BookDTO> getSearchResult(){
        return result;
    }

    public void clearSearchResult(){
        result.clear();
    }
}
