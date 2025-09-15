package br.csi.politecnico.financecontrol.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "expenses")
public class Expense extends Transaction {
    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate = LocalDate.now();
}
