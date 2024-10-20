package com.ustu.erdb.modules.models.store.repository;

import com.ustu.erdb.modules.models.store.models.Model;
import com.ustu.erdb.modules.models.store.models.ModelInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModelInfoRepository extends JpaRepository<ModelInfo, Long> {
    @Query("""
            select modelInfo from ModelInfo modelInfo where modelInfo.model = :model
            """)
    Optional<ModelInfo> findByModel(Model model);
}