package com.invoiceservice.Controller;

import com.invoiceservice.Entity.Invoice;
import com.invoiceservice.Service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService service;

    @PostMapping("/generate")
    public Invoice generate(
            @RequestParam Long orderId,
            @RequestParam Double amount,
            @RequestHeader("X-User-Email") String email) {
        return service.generateInvoice(orderId, amount, email);
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> pdf(
            @RequestParam Long orderId,
            @RequestParam Double amount,
            @RequestHeader("X-User-Email") String email) {

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=invoice.pdf")
                .header("Content-Type", "application/pdf")
                .body(service.generatePdf(orderId, amount, email));
    }
}
