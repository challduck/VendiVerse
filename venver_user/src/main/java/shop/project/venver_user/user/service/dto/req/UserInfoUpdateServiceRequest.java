package shop.project.venver_user.user.service.dto.req;

import lombok.Builder;
import shop.project.venver_user.user.controller.external.dto.request.UserInfoUpdateRequest;

@Builder
public record UserInfoUpdateServiceRequest(String address, String detailAddress, String phoneNumber) {
    public UserInfoUpdateServiceRequest(UserInfoUpdateRequest controllerRequest) {
        this(controllerRequest.address(),
                controllerRequest.phoneNumber(),
                controllerRequest.detailAddress());
    }
}
