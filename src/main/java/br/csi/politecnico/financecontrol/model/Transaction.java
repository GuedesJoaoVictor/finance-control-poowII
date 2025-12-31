package br.csi.politecnico.financecontrol.model;

import java.math.BigDecimal;

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
    private Long id;
    private String description;
    @Column(precision = 15, scale = 2)
    private BigDecimal value;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "bank_id", referencedColumnName = "id", nullable = false)
    private Bank bank;
}
