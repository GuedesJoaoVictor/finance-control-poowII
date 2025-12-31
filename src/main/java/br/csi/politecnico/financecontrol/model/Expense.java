package br.csi.politecnico.financecontrol.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "expenses")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Expense extends Transaction {
    @Builder.Default
    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate = LocalDate.now();
}
