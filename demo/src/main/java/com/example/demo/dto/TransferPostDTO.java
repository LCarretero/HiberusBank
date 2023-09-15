package com.example.demo.dto;

import com.example.demo.entities.Transfer;
import lombok.Getter;
import lombok.Setter;

@Getter
public class TransferPostDTO {

    private final WorkerPostDTO source;
    private final WorkerPostDTO destiny;
    private final double amount;
    private final boolean valid;

    public TransferPostDTO(Transfer transfer) {
        this.source = new WorkerPostDTO(transfer.getSource());
        this.destiny = new WorkerPostDTO(transfer.getDestiny());
        this.amount = transfer.getAmount();
        this.valid = transfer.isValid();
    }
}
