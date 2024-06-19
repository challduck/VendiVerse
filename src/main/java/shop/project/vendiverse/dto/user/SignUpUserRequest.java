package shop.project.vendiverse.dto.user;

import lombok.Getter;

@Getter
public class SignUpUserRequest {
    private String email;
    private String password;
    private String name;

    public SignUpUserRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
