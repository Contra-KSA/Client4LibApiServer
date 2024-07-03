package my.exam.catalog.client.catalog_client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
public class UserDto {
    private String username;
    private String password;
}
