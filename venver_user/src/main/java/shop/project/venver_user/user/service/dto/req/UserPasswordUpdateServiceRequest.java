package shop.project.venver_user.user.service.dto.req;

import shop.project.venver_user.user.controller.external.dto.request.UserPasswordUpdateControllerRequest;

public record UserPasswordUpdateServiceRequest(String oldPassword, String newPassword) {
    public UserPasswordUpdateServiceRequest (UserPasswordUpdateControllerRequest request){
        this(request.oldPassword(),request.newPassword());
    }
}
