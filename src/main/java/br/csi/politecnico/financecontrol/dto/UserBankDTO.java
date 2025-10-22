package br.csi.politecnico.financecontrol.dto;

import br.csi.politecnico.financecontrol.model.UserBank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBankDTO {
    private Long id;
    private String name;
    private BigDecimal totalAmount;
    private UserDTO user;
    private BankDTO bank;

    public UserBankDTO(UserBank userBank) {
        this.id = userBank.getId();
        this.name = userBank.getName();
        this.totalAmount = userBank.getTotalAmount();
        this.user = new UserDTO(userBank.getUser());
        this.bank = new BankDTO(userBank.getBank());
    }
}
