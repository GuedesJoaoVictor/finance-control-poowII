package br.csi.politecnico.financecontrol.repository;

import br.csi.politecnico.financecontrol.model.UserBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBankRepository extends JpaRepository<UserBank, Long> {

    UserBank findUserBankByBank_IdAndUser_Id(Long bankId, Long userId);
}
