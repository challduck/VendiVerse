package shop.project.venver_user.user.controller.external.dto.request;

public record EmailVerifiedControllerRequest(String email, String verifiedCode) {
}
