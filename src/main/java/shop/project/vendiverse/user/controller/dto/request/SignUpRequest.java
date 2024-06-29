package shop.project.vendiverse.user.controller.dto.request;


public record SignUpRequest(String email, String password, String name, String address, String phoneNumber, String detailAddress) {

}
