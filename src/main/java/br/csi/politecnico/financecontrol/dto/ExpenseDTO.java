package br.csi.politecnico.financecontrol.dto;

import br.csi.politecnico.financecontrol.model.Expense;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO {
    private Long id;
    private String description;
    private BigDecimal value;
    private UserDTO user;
    private CategoryDTO category;
    private BankDTO bank;
    private LocalDate expenseDate;

    public ExpenseDTO(Expense expense) {
        this.id = expense.getId();
        this.description = expense.getDescription();
        this.value = expense.getValue();
        this.user = new UserDTO(expense.getUser());
        this.category = new CategoryDTO(expense.getCategory());
        this.bank = new BankDTO(expense.getBank());
        this.expenseDate = expense.getExpenseDate();
    }
}
