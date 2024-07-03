package my.exam.catalog.client.catalog_client.dto;

import java.util.Objects;
import java.util.function.Function;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthorDTO {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;

    public String getFullNameForWeb(){
        Function<String, String> out = (str) -> (Objects.isNull(str) ? "" : str + " ");
        return out.apply(firstName) + out.apply(middleName) + out.apply(lastName);
    }
}
