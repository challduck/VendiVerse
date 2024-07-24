package shop.project.venver_user.user.service.dto.req;

import shop.project.venver_user.user.controller.external.dto.request.EmailVerifiedControllerRequest;

public record EmailVerifiedServiceRequest(String email, String verifiedCode) {
    public EmailVerifiedServiceRequest (EmailVerifiedControllerRequest request){
        this(request.email(), request.verifiedCode());
    }
}
