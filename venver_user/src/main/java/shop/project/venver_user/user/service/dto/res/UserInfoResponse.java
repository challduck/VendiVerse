package shop.project.venver_user.user.service.dto.res;

import lombok.Builder;

public record UserInfoResponse(String name, String address, String phoneNumber, String detailAddress, boolean isEmailVerified) {
    @Builder
    public UserInfoResponse{

    }
}
