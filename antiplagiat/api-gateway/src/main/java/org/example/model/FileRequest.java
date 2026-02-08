package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileRequest {
    private String firstName;
    private String lastName;
    private Integer taskId;
    private String fileName;
    private String fileContent;

    @Override
    public String toString() {
        return "FileRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", taskId=" + taskId +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}