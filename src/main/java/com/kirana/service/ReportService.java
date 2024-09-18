package com.kirana.service;

import com.kirana.model.Transaction;
import java.util.List;

public interface ReportService {
    double getWeeklyTotal(String userId);
    double getMonthlyTotal(String userId);
    double getYearlyTotal(String userId);
}
