package com.skillswap.server.controller;

import com.skillswap.server.dto.request.CreateUserRequest;
import com.skillswap.server.dto.response.ProfileImageDTO;
import com.skillswap.server.dto.response.UserDTO;
import com.skillswap.server.enums.Role;
import com.skillswap.server.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Đăng kí người dùng", description = "Chỉ dành cho người dùng chưa đăng nhập")
    @PostMapping
    public ResponseEntity<UserDTO> register(@Valid @RequestBody CreateUserRequest request){
        UserDTO userDTO = userService.registerUser(request);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Lấy thông tin người dùng đang đăng nhập", description = "Chỉ dành cho người dùng đã đăng nhập")
    @GetMapping("/p")
    public ResponseEntity<?> authenticatedUser(){
        return ResponseEntity.ok(userService.getAuthenticatedUser());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Update user skill tags")
    @PostMapping("/skill-tags")
    public ResponseEntity<?> updateUserSkill(@RequestParam String tags) {
        userService.updateUserSkillTags(tags);
        return ResponseEntity.ok("Cập nhật kỹ năng thành công");
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Cập nhật thông tin người dùng", description = "Chỉ dành cho người dùng đã đăng nhập")
    @PutMapping("/update-profile")
    public ResponseEntity<UserDTO> updateProfile(@RequestBody Map<String, Object> updateRequest) {
        UserDTO updatedUser = userService.updateProfile(updateRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Lấy danh sách người dùng", description = "Chỉ dành cho người dùng đã đăng nhập")
    @GetMapping("/find-friends")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> getAllUsers(@RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(userService.getAllUsers(page, size));
    }

    @Operation(summary = "Lấy danh sách người dùng theo vai trò", description = "Chỉ dành cho người dùng có vai trò ADMIN")
    @GetMapping("/all")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsersForAdmin(@RequestParam(name = "page", defaultValue = "0") int page,
                                                 @RequestParam(name = "size", defaultValue = "10") int size,
                                                 @RequestParam(name = "searchString", required = false) String searchString,
                                                 @RequestParam(name = "sortBy", defaultValue = "ASC") Sort.Direction sortBy,
                                                 @RequestParam(name = "role", defaultValue = "USER") Role role) {
        return ResponseEntity.ok(userService.getAllUsersForAdmin(page, size, searchString, sortBy, role));
    }

    @PostMapping("/upload-profile-images")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Tải lên ảnh đại diện của người dùng")
    public ResponseEntity<?> uploadProfileImages(@RequestBody List<ProfileImageDTO> dtos) throws IOException {
        return ResponseEntity.ok(userService.uploadProfileImages(dtos));
    }

    @GetMapping("/profile-images/{userId}")
    @Operation(summary = "Lấy danh sách ảnh đại diện của người dùng")
    public ResponseEntity<?> getProfileImagesByUserId(@PathVariable int userId) {
        return ResponseEntity.ok(userService.getProfileImagesByUserId(userId));
    }

}
