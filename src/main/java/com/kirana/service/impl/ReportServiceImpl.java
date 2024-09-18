package com.kirana.service.impl;

import com.kirana.model.Transaction;
import com.kirana.repository.TransactionRepository;
import com.kirana.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public double getWeeklyTotal(String userId) {
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);
        return getTransactionsTotal(userId, oneWeekAgo);
    }

    @Override
    public double getMonthlyTotal(String userId) {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        return getTransactionsTotal(userId, oneMonthAgo);
    }

    @Override
    public double getYearlyTotal(String userId) {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        return getTransactionsTotal(userId, oneYearAgo);
    }

    private double getTransactionsTotal(String userId, LocalDate fromDate) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        return transactions.stream()
                .filter(tx -> tx.getDate().toInstant().isAfter(fromDate.atStartOfDay().toInstant(null)))
                .mapToDouble(Transaction::getConvertedAmount)
                .sum();
    }
}
