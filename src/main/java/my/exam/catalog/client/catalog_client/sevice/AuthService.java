package my.exam.catalog.client.catalog_client.sevice;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import my.exam.catalog.client.catalog_client.dto.UserDto;
import my.exam.catalog.client.catalog_client.entity.User;
import my.exam.catalog.client.catalog_client.repository.SessionDataRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthService {

    private final SessionDataRepository repo;
    private final RequestService requestService;

    public void logout(){
        repo.setCurrentUser(new User());
    }

}
