package my.exam.catalog.client.catalog_client.dto;

import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.exam.catalog.client.catalog_client.entity.Author_DTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class BookDTO {
    private Long id;
    private String title;
    private int year;
    private String isbn;
    private List<Author_DTO> authors;

    public List<Author_DTO> addAuthor(Author_DTO author){
        authors.add(author);
        return authors;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year ;
    }

    public String getIsbn() {
        return Objects.isNull(isbn) ? "" : isbn;
    }

    public List<Author_DTO> getAuthors() {
        return authors;
    }

    public String getAuthorsForWeb(){
        String authorsWeb = "";
        for (Author_DTO author : authors ){
            String tmp = author.getFullNameForWeb();
            if (tmp.isEmpty()){
                continue;
            }
            if (authorsWeb.isEmpty()){
                authorsWeb = tmp;
            }else{
                authorsWeb = authorsWeb + ", " + tmp;
            }
        }
        return authorsWeb;
    }
}
