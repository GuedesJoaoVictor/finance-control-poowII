package br.csi.politecnico.financecontrol.dto;

import br.csi.politecnico.financecontrol.model.Revenue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RevenueDTO {
    private Long id;
    private String description;
    private BigDecimal value;
    private UserDTO user;
    private CategoryDTO category;
    private BankDTO bank;
    private LocalDate receiptDate;

    public RevenueDTO(Revenue revenue) {
        this.id = revenue.getId();
        this.description = revenue.getDescription();
        this.value = revenue.getValue();
        this.user = new UserDTO(revenue.getUser());
        this.category = new CategoryDTO(revenue.getCategory());
        this.bank = new BankDTO(revenue.getBank());
        this.receiptDate = revenue.getReceiptDate();
    }
}
