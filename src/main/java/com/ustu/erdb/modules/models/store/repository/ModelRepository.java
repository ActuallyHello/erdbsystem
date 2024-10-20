package com.ustu.erdb.modules.models.store.repository;

import com.ustu.erdb.modules.models.store.models.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
}
