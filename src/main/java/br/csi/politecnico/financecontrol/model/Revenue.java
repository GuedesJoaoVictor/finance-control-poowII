package br.csi.politecnico.financecontrol.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "revenues")
public class Revenue extends Transaction {
    @Column(name = "receipt_date", nullable = false)
    private LocalDate receiptDate = LocalDate.now();
}
