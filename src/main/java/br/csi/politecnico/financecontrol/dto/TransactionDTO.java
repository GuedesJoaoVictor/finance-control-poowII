package br.csi.politecnico.financecontrol.dto;

import br.csi.politecnico.financecontrol.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long id;
    private String description;
    private Double value;
    private UserDTO user;
    private CategoryDTO category;
    private BankDTO bank;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.description = transaction.getDescription();
        this.value = transaction.getValue().doubleValue();
        this.user = new UserDTO(transaction.getUser());
        this.category = new CategoryDTO(transaction.getCategory());
        this.bank = new BankDTO(transaction.getBank());
    }
}
