package ru.hse.bank.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Класс для операций
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Operation {
    private Integer id;
    private OperationType type;
    private Integer bankAccountId;
    private Integer amount;
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date = LocalDate.now();
    private String description;
    private Integer categoryId;

    @Override
    public String toString() {
        return "Operation{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", bankAccountId='" + bankAccountId + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }
}
