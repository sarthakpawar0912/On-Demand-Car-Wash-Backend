package com.invoiceservice.Service;

import com.invoiceservice.DTO.User;
import com.invoiceservice.DTO.UserClient;
import com.invoiceservice.Entity.Invoice;
import com.invoiceservice.Repository.InvoiceRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository repo;
    private final UserClient userClient;

    @Retry(name = "userService")
    @CircuitBreaker(name = "userService", fallbackMethod = "getUserFallback")
    public User getUser(String email) {
        return userClient.getUserByEmail(email);
    }

    public User getUserFallback(String email, Exception e) {
        System.out.println("USER SERVICE DOWN (Invoice): " + e.getMessage());
        User u = new User();
        u.setId(0L);
        u.setEmail(email);
        return u;
    }

    public Invoice generateInvoice(Long orderId, Double amount, String email) {

        User user = getUser(email);

        double cgst = amount * 0.09;
        double sgst = amount * 0.09;
        double total = amount + cgst + sgst;

        Invoice invoice = new Invoice();
        invoice.setUserId(user.getId());
        invoice.setOrderId(orderId);
        invoice.setAmount(amount);
        invoice.setCgst(cgst);
        invoice.setSgst(sgst);
        invoice.setTotalAmount(total);
        invoice.setInvoiceNumber("INV-" + System.currentTimeMillis());
        invoice.setCreatedAt(LocalDateTime.now());

        return repo.save(invoice);
    }

    public byte[] generatePdf(Long orderId, Double amount, String email) {

        User user = getUser(email);

        double platformFee = amount * 0.15;
        double taxable = amount + platformFee;
        double cgst = taxable * 0.09;
        double sgst = taxable * 0.09;
        double total = taxable + cgst + sgst;

        try {
            Document doc = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(doc, out);

            doc.open();

            // TITLE
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("TAX INVOICE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);

            doc.add(new Paragraph(" "));

            // INVOICE META
            doc.add(new Paragraph("Invoice No: INV-" + System.currentTimeMillis()));
            doc.add(new Paragraph("Date: " + LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd MMM yyyy"))));

            doc.add(new Paragraph(" "));

            // SELLER DETAILS
            doc.add(new Paragraph("SELLER DETAILS", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            doc.add(new Paragraph("Car Wash Pvt Ltd"));
            doc.add(new Paragraph("GSTIN: 27ABCDE1234F1Z5"));
            doc.add(new Paragraph("Pune, Maharashtra (Code: 27)"));

            doc.add(new Paragraph(" "));

            // BUYER DETAILS
            doc.add(new Paragraph("BUYER DETAILS", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            doc.add(new Paragraph("Email: " + user.getEmail()));
            doc.add(new Paragraph("Phone: " + user.getPhone()));

            doc.add(new Paragraph(" "));

            // TABLE
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            table.addCell("Description");
            table.addCell("Days");
            table.addCell("Rate");
            table.addCell("Amount");

            table.addCell("Car Wash Service");
            table.addCell("1");
            table.addCell("Rs. " + amount);
            table.addCell("Rs. " + amount);

            doc.add(table);

            doc.add(new Paragraph(" "));

            // CALCULATION
            doc.add(new Paragraph("Bill Breakdown", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));

            doc.add(new Paragraph("Service Amount: Rs. " + amount));
            doc.add(new Paragraph("Platform Fee (15%): Rs. " + platformFee));
            doc.add(new Paragraph("Taxable Value: Rs. " + taxable));
            doc.add(new Paragraph("CGST (9%): Rs. " + cgst));
            doc.add(new Paragraph("SGST (9%): Rs. " + sgst));

            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("TOTAL: Rs. " + total,
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));

            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("This is a computer-generated invoice."));

            doc.close();

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
