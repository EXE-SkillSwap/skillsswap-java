package com.skillswap.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
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
    @NotBlank(message = "Tuổi là bắt buộc")
    String birthDay;
    @NotBlank(message = "Giới tính là bắt buộc")
    String gender;

}
