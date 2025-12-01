package br.csi.politecnico.financecontrol.repository;

import br.csi.politecnico.financecontrol.model.Bank;
import br.csi.politecnico.financecontrol.model.UserBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserBankRepository extends JpaRepository<UserBank, Long> {

    UserBank findUserBankByBank_IdAndUser_Id(Long bankId, Long userId);

    @Query(nativeQuery = true, value = """
        SELECT b.* FROM bank b 
        JOIN user_bank ub ON ub.bank_id = b.id
        JOIN users u ON u.id = ub.user_id
        WHERE u.uuid = :uuid
    """)
    List<Bank> findALlBanksByUserUuid(UUID uuid);
}
