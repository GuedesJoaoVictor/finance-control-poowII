package br.csi.politecnico.financecontrol.service;

import br.csi.politecnico.financecontrol.dto.*;
import br.csi.politecnico.financecontrol.exception.BadRequestException;
import br.csi.politecnico.financecontrol.model.*;
import br.csi.politecnico.financecontrol.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    private final RevenuesRepository revenuesRepository;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final CategoryRepository categoryRepository;

    public TransactionService(RevenuesRepository revenuesRepository, UserRepository userRepository, BankRepository bankRepository, CategoryRepository categoryRepository) {
        this.revenuesRepository = revenuesRepository;
        this.userRepository = userRepository;
        this.bankRepository = bankRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<RevenueDTO> findAll() {
        List<Revenue> revenues = revenuesRepository.findAll();
        List<RevenueDTO> dtos = new ArrayList<>();
        if (revenues.isEmpty()) {
            throw new BadRequestException("Nenhuma transação encontrada.");
        }
        for (Revenue revenue : revenues) {
            RevenueDTO dto = RevenueDTO.builder()
                    .id(revenue.getId())
                    .user(new UserDTO(revenue.getUser()))
                    .bank(new BankDTO(revenue.getBank()))
                    .category(new CategoryDTO(revenue.getCategory()))
                    .value(revenue.getValue())
                    .description(revenue.getDescription())
                    .receiptDate(revenue.getReceiptDate())
                    .build();
            dtos.add(dto);
        }
        return dtos;
    }

    public RevenueDTO createRevenue(RevenueDTO revenue) {
        User user = userRepository.findById(revenue.getUser().getId()).orElse(null);
        if (user == null) {
            throw new BadRequestException("Usuário não encontrado.");
        }
        Bank bank = bankRepository.findById(revenue.getBank().getId()).orElse(null);
        if (bank == null) {
            throw new BadRequestException("Banco não encontrado.");
        }
        Category category = categoryRepository.findById(revenue.getCategory().getId()).orElse(null);
        if (category == null) {
            throw new BadRequestException("Categoria não encontrada.");
        }
        if (revenue.getValue().intValue() < 0) {
            throw new BadRequestException("Valor da transação não pode ser negativo.");
        }

        Revenue revenueEntity = Revenue.builder()
                .user(user)
                .bank(bank)
                .category(category)
                .value(revenue.getValue())
                .description(revenue.getDescription())
                .receiptDate(revenue.getReceiptDate())
                .build();
        return new RevenueDTO(revenuesRepository.saveAndFlush(revenueEntity));
    }

}
