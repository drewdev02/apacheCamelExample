package org.adrewdev.apachecamel.exercise3.dto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class InvoiceMapper {


    public InvoiceSummaryDTO toSummaryDTO(InvoiceDTO invoiceDTO) {
        var summaryDTO = new InvoiceSummaryDTO();

        // Mapeo de campos directos
        summaryDTO.setInvoiceId(invoiceDTO.getInvoiceNumber());
        summaryDTO.setIssueDate(invoiceDTO.getDate());
        summaryDTO.setTotalAmount(invoiceDTO.getSubtotal() + invoiceDTO.getTax());

        // Mapeo de CustomerDTO a CustomerSummaryDTO
        var customerSummary = new CustomerSummaryDTO();
        customerSummary.setFullName(invoiceDTO.getCustomer().getName());
        customerSummary.setEmailAddress(invoiceDTO.getCustomer().getEmail());
        customerSummary.setContactNumber(invoiceDTO.getCustomer().getPhone());
        summaryDTO.setCustomer(customerSummary);

        // Mapeo de ItemDTO a ItemSummaryDTO dentro de un Map
        var itemsMap = invoiceDTO.getItems().stream()
                .collect(Collectors.toMap(
                        ItemDTO::getDescription,
                        item -> new ItemSummaryDTO(item.getQuantity(), item.getUnitPrice())
                ));
        summaryDTO.setItems(itemsMap);

        // Mapeo de detalles de pago
        var paymentDetails = new PaymentDetailsDTO();
        paymentDetails.setMethod(invoiceDTO.getPaymentMethod());
        paymentDetails.setNotes(invoiceDTO.getNotes());
        summaryDTO.setPaymentDetails(paymentDetails);

        return summaryDTO;
    }
}
