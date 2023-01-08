package ru.rinat.restservice.dto;

import java.util.UUID;

public class SubscribDto {
    private UUID ownerUserId;
    private UUID subscribeUserId;

    public UUID getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(UUID ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public UUID getSubscribeUserId() {
        return subscribeUserId;
    }

    public void setSubscribeUserId(UUID subscribeUserId) {
        this.subscribeUserId = subscribeUserId;
    }

    @Override
    public String toString() {
        return "SubscribDto{" +
                "ownerUserId=" + ownerUserId +
                ", subscribeUserId=" + subscribeUserId +
                '}';
    }
}
