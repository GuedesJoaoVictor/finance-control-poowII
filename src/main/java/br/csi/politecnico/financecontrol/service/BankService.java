package br.csi.politecnico.financecontrol.service;

import br.csi.politecnico.financecontrol.dto.BankDTO;
import br.csi.politecnico.financecontrol.exception.BadRequestException;
import br.csi.politecnico.financecontrol.model.Bank;
import br.csi.politecnico.financecontrol.repository.BankRepository;
import org.springframework.stereotype.Service;

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
}
