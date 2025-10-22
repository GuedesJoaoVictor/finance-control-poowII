package br.csi.politecnico.financecontrol.model;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único da transação", example = "1")
    private Long id;
    @Schema(description = "Descrição da transação", example = "Pix para minha mãe")
    private String description;
    @Column(precision = 15, scale = 2)
    @Schema(description = "Valor da transação", example = "1000.00")
    private BigDecimal value;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @Schema(description = "Usuário que fez a transação", example = "Guedes")
    private User user;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    @Schema(description = "Categoria da transação", example = "Uber")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "bank_id", referencedColumnName = "id", nullable = false)
    @Schema(description = "Banco vinculado a transação", example = "Banco do Brasil")
    private Bank bank;
}
