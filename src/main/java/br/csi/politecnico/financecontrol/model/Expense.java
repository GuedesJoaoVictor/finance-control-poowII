package br.csi.politecnico.financecontrol.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "expenses")
public class Expense extends Transaction {
    @Column(name = "expense_date", nullable = false)
    @Schema(description = "Momento em que foi feito o gasto", example = "07/04/2004")
    private LocalDate expenseDate = LocalDate.now();
}
