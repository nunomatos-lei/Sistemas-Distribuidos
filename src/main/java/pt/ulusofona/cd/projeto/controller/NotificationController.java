package pt.ulusofona.cd.projeto.controller;


import lombok.RequiredArgsConstructor;
import pt.ulusofona.cd.projeto.dto.NotificationRequest;
import pt.ulusofona.cd.projeto.dto.NotificationResponse;
import pt.ulusofona.cd.projeto.model.Notification;
import pt.ulusofona.cd.projeto.mapper.NotificationMapper;
import pt.ulusofona.cd.projeto.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping
    public ResponseEntity<NotificationResponse> create(
            @Valid @RequestBody NotificationRequest request
    ) {
        Notification created = service.createNotification(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(NotificationMapper.toResponse(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getById(@PathVariable UUID id) {
        Notification notification = service.getNotificationById(id);
        return ResponseEntity.ok(NotificationMapper.toResponse(notification));
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAll() {
        List<Notification> notifications = service.getAllNotifications();
        List<NotificationResponse> responseList = notifications.stream()
                .map(NotificationMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody NotificationRequest request
    ) {
        Notification updated = service.updateNotification(id, request);
        return ResponseEntity.ok(NotificationMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}

