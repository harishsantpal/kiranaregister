package com.kirana.service.impl;

import com.kirana.dto.CurrencyResponse;
import com.kirana.model.Transaction;
import com.kirana.repository.TransactionRepository;
import com.kirana.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        // Convert the amount to INR
        double convertedAmount = convertCurrency(transaction.getCurrency(), transaction.getAmount());
        transaction.setConvertedAmount(convertedAmount);
        return transactionRepository.save(transaction);
    }

    @Override
    public double convertCurrency(String fromCurrency, double amount) {
        if (fromCurrency.equals("INR")) {
            return amount; // No conversion needed
        }

        // Call the currency conversion API (can use caching to avoid excessive calls)
        String apiUrl = "https://api.exchangerate-api.com/v4/latest/" + fromCurrency;
        RestTemplate restTemplate = new RestTemplate();
        try {
            CurrencyResponse response = restTemplate.getForObject(apiUrl, CurrencyResponse.class);
            double exchangeRate = response.getRates().get("INR");
            return amount * exchangeRate;
        } catch (Exception e) {
            throw new RuntimeException("Currency conversion failed");
        }
    }
}
