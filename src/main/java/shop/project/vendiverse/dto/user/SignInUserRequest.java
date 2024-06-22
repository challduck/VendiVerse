package shop.project.vendiverse.dto.user;

import lombok.Getter;

@Getter
public class SignInUserRequest {

    private String email;
    private String password;

    public SignInUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
