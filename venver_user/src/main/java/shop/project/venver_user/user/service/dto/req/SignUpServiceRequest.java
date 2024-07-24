package shop.project.venver_user.user.service.dto.req;

import lombok.Builder;
import shop.project.venver_user.user.controller.external.dto.request.SignUpControllerRequest;

@Builder
public record SignUpServiceRequest(String email, String password, String name, String address, String phoneNumber, String detailAddress) {
    public SignUpServiceRequest(SignUpControllerRequest controllerRequest) {
        this(controllerRequest.email(),
                controllerRequest.password(),
                controllerRequest.name(),
                controllerRequest.address(),
                controllerRequest.phoneNumber(),
                controllerRequest.detailAddress());
    }
}
