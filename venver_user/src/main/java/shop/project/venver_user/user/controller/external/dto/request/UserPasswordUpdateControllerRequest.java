package shop.project.venver_user.user.controller.external.dto.request;

public record UserPasswordUpdateControllerRequest(String oldPassword, String newPassword) {
}
