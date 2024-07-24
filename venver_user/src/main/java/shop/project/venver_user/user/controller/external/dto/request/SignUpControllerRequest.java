package shop.project.venver_user.user.controller.external.dto.request;


public record SignUpControllerRequest(String email, String password, String name, String address, String phoneNumber, String detailAddress) {

}
