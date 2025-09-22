package br.csi.politecnico.financecontrol.repository;

import br.csi.politecnico.financecontrol.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Long> {
    Optional<Bank> findBankByName(String name);
}
