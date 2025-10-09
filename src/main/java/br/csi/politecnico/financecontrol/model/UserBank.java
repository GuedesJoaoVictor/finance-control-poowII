package br.csi.politecnico.financecontrol.model;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_bank")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class UserBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do vinculo do usuáro com o banco", example = "1")
    private Long id;
    @Schema(description = "Nome do vínculo", example = "Conta corrente banco do brasil")
    private String name;
    @Column(name = "total_amount", precision = 15, scale = 2)
    @Schema(description = "Valor inicial do vinculo", example = "1000.00")
    private BigDecimal totalAmount;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @Schema(description = "Usuário do vínculo", example = "Guedes")
    private User user;
    @ManyToOne
    @JoinColumn(name = "bank_id", referencedColumnName = "id", nullable = false)
    @Schema(description = "Banco do vínculo", example = "Banco do Brasil")
    private Bank bank;
}
