package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportOfTasks {
    private Integer taskId;
    private List<FileReport> fileReports;
    private Boolean hasPlagiarism;
    private Integer totalFiles;

    // Этот метод будет вызываться при установке fileReports
    public void setFileReports(List<FileReport> fileReports) {
        this.fileReports = fileReports;
        this.totalFiles = fileReports != null ? fileReports.size() : 0;
        calculatePlagiarism();
    }

    private void calculatePlagiarism() {
        if (fileReports == null || fileReports.isEmpty()) {
            this.hasPlagiarism = false;
            return;
        }

        // Проверяем наличие плагиата
        this.hasPlagiarism = fileReports.stream()
                .anyMatch(FileReport::getIsPlagiarism);
    }
}
