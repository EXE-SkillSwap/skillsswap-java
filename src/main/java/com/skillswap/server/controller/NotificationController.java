package com.skillswap.server.controller;

import com.skillswap.server.dto.request.NotificationRequest;
import com.skillswap.server.services.NotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> getNotifications() {
        return ResponseEntity.ok(notificationService.getNotificationsByUser());
    }

    @PostMapping
    public ResponseEntity<?> createNotification(@RequestBody NotificationRequest request) {
        notificationService.sendNotification(request);
        return ResponseEntity.ok("Thông báo đã được gửi thành công");
    }

    @PutMapping("/make-read/{notificationId}")
    public ResponseEntity<?> makeNotificationRead(@PathVariable int notificationId) {
        notificationService.makeNotificationRead(notificationId);
        return ResponseEntity.ok("Thông báo đã được đánh dấu là đã đọc");
    }

    @PutMapping("/make-all-read")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> makeAllNotificationsRead() {
        notificationService.makeAllNotificationsRead();
        return ResponseEntity.ok("Tất cả thông báo đã được đánh dấu là đã đọc");
    }

}
