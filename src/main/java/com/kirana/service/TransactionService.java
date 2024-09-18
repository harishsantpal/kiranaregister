package com.kirana.service;

import com.kirana.model.Transaction;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);
    double convertCurrency(String fromCurrency, double amount);
}
