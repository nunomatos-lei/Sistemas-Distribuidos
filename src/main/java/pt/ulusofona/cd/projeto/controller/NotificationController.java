package pt.ulusofona.cd.projeto.controller;


import io.swagger.v3.oas.annotations.Operation;
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

    // Post
    @Operation(
            summary = "Create notification",
            description = "Creates a new notification record manually. This is often used for testing or internal system alerts."
    )
    @PostMapping
    public ResponseEntity<NotificationResponse> create(
            @Valid @RequestBody NotificationRequest request
    ) {
        Notification created = service.createNotification(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(NotificationMapper.toResponse(created));
    }




    // Get
    @Operation(
            summary = "Get notification by ID",
            description = "Returns the details of a specific notification based on its UUID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getById(@PathVariable UUID id) {
        Notification notification = service.getNotificationById(id);
        return ResponseEntity.ok(NotificationMapper.toResponse(notification));
    }

    @Operation(
            summary = "Get all notifications",
            description = "Returns a complete history of all notifications sent or logged by the system."
    )
    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAll() {
        List<Notification> notifications = service.getAllNotifications();
        List<NotificationResponse> responseList = notifications.stream()
                .map(NotificationMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responseList);
    }
}

