package org.example.event;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class PaymentResultEvent {
    private UUID orderId;
    private boolean successful;
}
