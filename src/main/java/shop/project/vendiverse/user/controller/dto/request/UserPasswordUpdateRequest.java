package shop.project.vendiverse.user.controller.dto.request;

import lombok.Getter;

public record UserPasswordUpdateRequest(String oldPassword, String newPassword) {
}
