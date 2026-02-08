package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 *  Сущность файла в БД
 */
@Entity
@Table(name = "stored_files")
@Getter
@Setter
public class StoredFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(name = "task_id", nullable = false)
    private Integer taskId;

    @Lob
    @Column(name = "file_content", columnDefinition = "CLOB")
    private String fileContent;

    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;

    @PrePersist
    protected void onCreate() {
        if (uploadDate == null) {
            uploadDate = LocalDateTime.now();
        }
    }
}