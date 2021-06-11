package uz.pdp.appnewssite.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotNull(message = "fullName bo'sh bo'lmasin")
    private String fullName;

    @NotNull(message = "username bo'sh bo'masin")
    private String username;

    @NotNull(message = "parol bo'sh bo'masin")
    private String password;

    @NotNull(message = "role id bo'sh bo'lmasin")
    private Long roleId;
}
