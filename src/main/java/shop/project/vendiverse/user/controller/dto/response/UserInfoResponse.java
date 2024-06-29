package shop.project.vendiverse.user.controller.dto.response;

import lombok.Getter;

@Getter
public class UserInfoResponse {

    private final String name;
    private final String address;
    private final String detailAddress;
    private final String phoneNumber;

    public UserInfoResponse(String name, String address, String phoneNumber, String detailAddress) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.detailAddress = detailAddress;
    }
}
