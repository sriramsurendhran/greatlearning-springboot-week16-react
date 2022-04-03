package com.greatlearning.surabi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greatlearning.surabi.entity.AuditLog;


/**
 * @author sriram
 *
 */
@Repository
public interface AuditRepository extends JpaRepository<AuditLog, Integer> {

}
