package br.csi.politecnico.financecontrol.repository;

import br.csi.politecnico.financecontrol.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}
