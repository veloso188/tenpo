package com.tenpo.calculator.repository;

import com.tenpo.calculator.entity.RequestResponseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RequestResponseLogRepository extends JpaRepository<RequestResponseLog, Long> {
}

