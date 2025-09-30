package br.csi.politecnico.financecontrol.service;

import br.csi.politecnico.financecontrol.dto.BankDTO;
import br.csi.politecnico.financecontrol.exception.BadRequestException;
import br.csi.politecnico.financecontrol.exception.NotFoundException;
import br.csi.politecnico.financecontrol.model.Bank;
import br.csi.politecnico.financecontrol.repository.BankRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BankService {

    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public String create(BankDTO dto) {
        Bank bank = bankRepository.findBankByName(dto.getName()).orElse(null);
        if (bank != null) {
            throw new BadRequestException("O banco já existe");
        }
        if (validateInputsBank(dto)) {
            throw new BadRequestException("Os campos devem ser preenchidos");
        }

        bank = Bank.builder()
                .name(dto.getName())
                .type(dto.getType())
                .build();
        bankRepository.saveAndFlush(bank);

        return "Banco criado com sucesso";
    }

    private boolean validateInputsBank(BankDTO dto) {
        if (dto.getName() == null || dto.getType() == null) {
            return true;
        }
        if (dto.getName().isEmpty() || dto.getType().isEmpty()) {
            return true;
        }
        return false;
    }

    public List<BankDTO> findAll() {
        List<Bank> banks = bankRepository.findAll();
        List<BankDTO> dtos = new ArrayList<>();
        if (banks.isEmpty()) {
            throw new NotFoundException("Nenhum banco encontrado.");
        }

        for (Bank bank : banks) {
            BankDTO dto = BankDTO.builder()
                    .id(bank.getId())
                    .name(bank.getName())
                    .type(bank.getType())
                    .build();
            dtos.add(dto);
        }

        return dtos;
    }
}
