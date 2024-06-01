package Model;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class Courier {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;
    private String login;
    private String password;
    private String firstName;
}
