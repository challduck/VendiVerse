package shop.project.vendiverse.dto.user;

import lombok.Getter;

@Getter
public class SignUpUserRequest {
    private String email;
    private String password;
    private String name;
    private String address;
    private String phoneNumber;

    public SignUpUserRequest(String email, String password, String name, String address, String phone_number) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phoneNumber = phone_number;
    }
}
