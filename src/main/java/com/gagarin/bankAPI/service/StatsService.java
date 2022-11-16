package com.gagarin.bankAPI.service;

import com.gagarin.bankAPI.repository.StatsRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class StatsService {
    private final StatsRepository statsRepository;

    public StatsService(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    public List<Map<String, Object>> getOperationList(Long userId, LocalDate fromDate, LocalDate beforeDate) {

        return statsRepository.getOperationList(userId, fromDate, beforeDate);
    }
}
