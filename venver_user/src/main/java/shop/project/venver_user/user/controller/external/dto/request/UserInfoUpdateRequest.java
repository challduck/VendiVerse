package shop.project.venver_user.user.controller.external.dto.request;

import lombok.Builder;

public record UserInfoUpdateRequest (String address, String detailAddress, String phoneNumber){
    @Builder
    public UserInfoUpdateRequest{

    }
}
