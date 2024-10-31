package org.adrewdev.apachecamel.exercise3.dto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class InvoiceMapper {


    public DataInvoiceSummaryDTO toSummaryDTO(ResponseData data) {
        var dtos = data.getInvoices().stream().map(this::toSummaryDTO).toList();
        return new DataInvoiceSummaryDTO(dtos);
    }

    private InvoiceSummaryDTO toSummaryDTO(InvoiceDTO invoiceDTO) {
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
                        item -> new ItemSummaryDTO(item.getQuantity(), item.getUnitPrice()),
                        (item1, item2) -> new ItemSummaryDTO(
                                item1.getQuantity() + item2.getQuantity(),
                                item1.getPrice() + item2.getPrice()
                        )
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
