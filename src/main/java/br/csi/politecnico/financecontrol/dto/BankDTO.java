package br.csi.politecnico.financecontrol.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankDTO {
    private Long id;
    private String name;
    private String type;
}
