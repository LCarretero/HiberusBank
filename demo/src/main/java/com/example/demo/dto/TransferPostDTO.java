package com.example.demo.dto;

import com.example.demo.entities.Transfer;
import lombok.Getter;
import lombok.Setter;

@Getter
public class TransferPostDTO {

    private WorkerPostDTO source;
    private WorkerPostDTO destiny;
    private double amount;
    private boolean valid;

    public TransferPostDTO(Transfer transfer) {
        this.source = new WorkerPostDTO(transfer.getSource());
        this.destiny = new WorkerPostDTO(transfer.getDestiny());
        this.amount = transfer.getAmount();
        this.valid = transfer.isValid();
    }
}
