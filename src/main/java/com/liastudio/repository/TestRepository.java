package com.liastudio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.liastudio.model.TestEntity;

public interface TestRepository extends JpaRepository<TestEntity, Long> {
}
