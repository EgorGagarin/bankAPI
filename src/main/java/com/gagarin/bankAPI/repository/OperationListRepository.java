package com.gagarin.bankAPI.repository;

import com.gagarin.bankAPI.entity.OperationList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationListRepository extends JpaRepository<OperationList, Long> {
}
