package com.gagarin.bankAPI.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class StatsRepository {
    private final JdbcTemplate jdbcTemplate;

    public StatsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getOperationList(Long userId, LocalDate fromDate, LocalDate beforeDate) {

        if (fromDate != null && beforeDate != null) {
            return jdbcTemplate.queryForList("SELECT * FROM operation_list WHERE user_id= ? AND date_operation BETWEEN ? AND ?", userId, fromDate, beforeDate);

        } else if (beforeDate == null && fromDate != null) {
            return jdbcTemplate.queryForList("SELECT * FROM operation_list WHERE user_id= ? AND date_operation >= ?", userId, fromDate);

        } else if (fromDate == null && beforeDate != null) {
            return jdbcTemplate.queryForList("SELECT * FROM operation_list WHERE user_id= ? AND date_operation <= ?", userId, beforeDate);

        } else {
            return jdbcTemplate.queryForList("SELECT * FROM operation_list WHERE user_id= ?", userId);
        }
    }

}
