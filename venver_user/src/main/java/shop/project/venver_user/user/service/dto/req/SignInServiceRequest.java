package shop.project.venver_user.user.service.dto.req;

import shop.project.venver_user.user.controller.external.dto.request.SignInControllerRequest;

public record SignInServiceRequest (String email, String password) {
    public SignInServiceRequest(SignInControllerRequest request) {
        this(request.email(), request.password());
    }
}
