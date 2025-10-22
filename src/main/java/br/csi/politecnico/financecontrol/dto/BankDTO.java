package br.csi.politecnico.financecontrol.dto;

import br.csi.politecnico.financecontrol.model.Bank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankDTO {
    private Long id;
    private String name;
    private String type;

    public BankDTO(Bank bank) {
        this.id = bank.getId();
        this.name = bank.getName();
        this.type = bank.getType();
    }
}
