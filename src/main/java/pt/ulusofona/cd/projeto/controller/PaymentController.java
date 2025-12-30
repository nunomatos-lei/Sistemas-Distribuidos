package pt.ulusofona.cd.projeto.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ulusofona.cd.projeto.dto.PaymentRequest;
import pt.ulusofona.cd.projeto.dto.PaymentResponse;
import pt.ulusofona.cd.projeto.mapper.PaymentMapper;
import pt.ulusofona.cd.projeto.model.Payment;
import pt.ulusofona.cd.projeto.service.PaymentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    //Const
    private final PaymentService service;


    //***************  Post  ***************//
    @PostMapping
    public ResponseEntity<PaymentResponse> create(@Valid @RequestBody PaymentRequest request) {
        Payment created = service.createPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(PaymentMapper.toResponse(created));
    }




    //***************  Get  ***************//
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getById(@PathVariable UUID id) {
        Payment payment = service.getPaymentById(id);
        return ResponseEntity.ok(PaymentMapper.toResponse(payment));
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAll() {
        List<Payment> payments = service.getAllPayments();
        List<PaymentResponse> responseList = payments.stream().map(PaymentMapper::toResponse).toList();

        return ResponseEntity.ok(responseList);
    }




    //***************  Put  ***************//
    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponse> update(@PathVariable UUID id, @Valid @RequestBody PaymentRequest request) {
        Payment updated = service.updatePayment(id, request);
        return ResponseEntity.ok(PaymentMapper.toResponse(updated));
    }




    //***************  Delete  ***************//
    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentResponse> delete(@PathVariable UUID id) {
        Payment payment = service.deletePayment(id);
        PaymentResponse response = PaymentMapper.toResponse(payment);
        return ResponseEntity.ok(response);
    }
}
