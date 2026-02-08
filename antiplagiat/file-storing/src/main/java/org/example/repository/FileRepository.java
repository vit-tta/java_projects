package org.example.repository;

import org.example.entity.StoredFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с файлами
 */
@Repository
public interface FileRepository extends JpaRepository<StoredFile, Long> {
    List<StoredFile> findByTaskId(Integer taskId);
}