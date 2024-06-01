package Model;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class Order {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private Integer rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

}
