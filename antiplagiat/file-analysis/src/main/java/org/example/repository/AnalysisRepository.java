package org.example.repository;

import org.example.entity.AnalysisReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA репозиторий для операций с таблицей analysis_reports.
 * Автоматически генерирует SQL запросы
 */
@Repository
public interface AnalysisRepository extends JpaRepository<AnalysisReport, Long> {
    List<AnalysisReport> findByTaskId(Integer taskId);
    List<AnalysisReport> findByFileId(Long fileId);
}
