package br.csi.politecnico.financecontrol.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "revenues")
public class Revenue extends Transaction {
    @Column(name = "receipt_date", nullable = false)
    @Schema(description = "Momento em que foi feita a renda", example = "07/04/2004")
    private LocalDate receiptDate = LocalDate.now();
}
