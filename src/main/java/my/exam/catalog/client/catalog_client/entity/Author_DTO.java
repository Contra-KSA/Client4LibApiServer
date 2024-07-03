package my.exam.catalog.client.catalog_client.entity;

import java.util.Objects;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Author_DTO {

    private Long id;

    private String firstName;
    private String middleName;
    private String lastName;



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Author_DTO author = (Author_DTO) o;
        return Objects.equals(firstName.toUpperCase(), author.firstName.toUpperCase())
                && Objects.equals(middleName.toUpperCase(), author.middleName.toUpperCase())
                && Objects.equals(lastName.toUpperCase(), author.lastName.toUpperCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName.toUpperCase(), middleName.toUpperCase(), lastName.toUpperCase());
    }

    public String getFullNameForWeb() {
        Function<String, String> out = (str) -> (Objects.isNull(str) ? "" : str + " ");
        return out.apply(firstName) + out.apply(middleName) + out.apply(lastName);
    }
}