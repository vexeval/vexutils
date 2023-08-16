package io.github.vexeval.vexutils;

import java.util.Objects;
import java.util.UUID;

public class TeleportRequest {
    private UUID requester;
    private UUID target;
    private long expirationTime;

    public TeleportRequest(UUID requester, UUID target) {
        this.requester = requester;
        this.target = target;
        this.expirationTime = System.currentTimeMillis() + 60000; // Set expiration time to 1 minute
    }

    public UUID getRequester() {
        return requester;
    }

    public UUID getTarget() {
        return target;
    }
    
    public long getExpirationTime() {
    	return expirationTime;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expirationTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(requester, target);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TeleportRequest other = (TeleportRequest) obj;
        return Objects.equals(requester, other.requester) && Objects.equals(target, other.target);
    }
}
