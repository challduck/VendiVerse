package shop.project.venver_user.user.controller.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.project.venver_user.user.service.UserService;


@RestController
@RequestMapping("/api/internal/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserInternalController {

    private final UserService userService;

}
