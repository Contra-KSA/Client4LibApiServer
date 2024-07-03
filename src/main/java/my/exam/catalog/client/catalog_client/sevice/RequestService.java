package my.exam.catalog.client.catalog_client.sevice;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import my.exam.catalog.client.catalog_client.dto.BookDTO;
import my.exam.catalog.client.catalog_client.dto.UserDto;
import my.exam.catalog.client.catalog_client.repository.SessionDataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Component
@RequiredArgsConstructor
public class RequestService {

    private final SessionDataRepository repo;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${catalog.basic.url}")
    private String basicCatalogUrl;
    @Value("${catalog.basic.context}")
    private String basicCatalogContextUrl;
    @Value("${catalog.login.context}")
    private String basicCatalogLoginUrl;
    @Value("${catalog.request.search}")
    private String searchRequest;

    @PostConstruct
    private void configureRestTemplate() {
        restTemplate.setErrorHandler((new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

            }
        }));
    }


    public ResponseEntity<UserDto> authorize(UserDto user){
        try{
            String loginUrl = basicCatalogUrl + basicCatalogLoginUrl;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<UserDto> requestEntity = new HttpEntity<>(user, headers);
            ResponseEntity<UserDto> response = restTemplate.postForEntity(loginUrl, requestEntity, UserDto.class);
            log.info("Response: " + response);
            return response;
        }catch (Exception e){
            log.error("Something wrong: " + e );
            return null;
        }
    }


    public List<BookDTO> findAll() throws Exception{
        try {
            String searchUrl = basicCatalogUrl + basicCatalogContextUrl;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(HeaderValues.AUTHORIZATION, repo.getCurrentUserJwt());

            List<BookDTO> books = new ArrayList<>();
            HttpEntity<List<BookDTO>> requestEntry = new HttpEntity<>(books, headers);
            ResponseEntity<List<BookDTO>> response = restTemplate.exchange( searchUrl, HttpMethod.GET, requestEntry,
                    new ParameterizedTypeReference<List<BookDTO>>(){} );
            HttpStatusCode status = response.getStatusCode();
            return response.getBody();
        }catch (Exception e){
            log.error("Something wrong: " + e );
            return null;
        }
    }

    public List<BookDTO> searchByTitleAndYear(String title, Integer year) {
        try {
            Function<Object, String> objectToString = (str) -> (Objects.isNull(str)) ? "" : str.toString();
             String searchUrl = basicCatalogUrl + basicCatalogContextUrl + searchRequest
                    + "title=" + objectToString.apply(title) + "&year=" + objectToString.apply(year);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(HeaderValues.AUTHORIZATION, repo.getCurrentUserJwt());

            List<BookDTO> books = new ArrayList<>();
            HttpEntity<List<BookDTO>> requestEntry = new HttpEntity<>(books, headers);
            ResponseEntity<List<BookDTO>> response = restTemplate.exchange( searchUrl, HttpMethod.GET, requestEntry,
                    new ParameterizedTypeReference<List<BookDTO>>(){} );
            HttpStatusCode status = response.getStatusCode();
            return response.getBody();
        }catch (Exception e){
            log.error("Something wrong: " + e );
            return null;
        }
    }
}
