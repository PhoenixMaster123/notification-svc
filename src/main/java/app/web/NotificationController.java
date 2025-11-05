package app.web;

import app.model.Notification;
import app.service.NotificationService;
import app.web.dto.NotificationRequest;
import app.web.dto.NotificationResponse;
import app.web.mapper.DtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<?> sendNotification(@RequestBody NotificationRequest request) {
        Notification notification = notificationService.send(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(DtoMapper.from(notification));
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getHistory(@RequestParam("userId") UUID userId) {
        List<Notification> notifications = notificationService.getHistory(userId);

        return ResponseEntity.ok(notifications.stream().map(DtoMapper::from).toList());
    }
}
