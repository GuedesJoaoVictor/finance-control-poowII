package br.csi.politecnico.financecontrol.repository;

import br.csi.politecnico.financecontrol.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
