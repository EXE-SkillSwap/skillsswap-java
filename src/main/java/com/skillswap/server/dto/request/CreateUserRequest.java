package com.skillswap.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserRequest {

    @NotBlank(message = "Tên là bắt buộc")
    String firstName;
    @NotBlank(message = "Họ là bắt buộc")
    String lastName;
    @NotBlank(message = "Email là bắt buộc")
    String email;
    @NotBlank(message = "Mật khẩu là bắt buộc")
    String password;
    @NotBlank(message = "Xác nhận mật khẩu là bắt buộc")
    String confirmPassword;

}
