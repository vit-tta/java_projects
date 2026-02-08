package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * JPA сущность, которая представляет собой отчет об анализе. Сохраняется в БД H2
 */
@Entity
@Table(name = "analysis_reports")
@Getter
@Setter
public class AnalysisReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @Column(name = "task_id", nullable = false)
    private Integer taskId;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "is_plagiarism", nullable = false)
    private Boolean isPlagiarism = false;

    @Column(name = "plagiarism_source")
    private String plagiarismSource;

    @Column(name = "analysis_date", nullable = false)
    private LocalDateTime analysisDate;

    @PrePersist
    protected void onCreate() {
        if (analysisDate == null) {
            analysisDate = LocalDateTime.now();
        }
    }
}