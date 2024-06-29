package shop.project.vendiverse.user.controller.dto.request;

public record EmailVerifiedRequest (String email, String verifiedCode) {
}
