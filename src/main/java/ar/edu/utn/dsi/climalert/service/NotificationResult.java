package ar.edu.utn.dsi.climalert.service;

import ar.edu.utn.dsi.climalert.domain.NotificationStatus;

public record NotificationResult(NotificationStatus status, String detail) {

    public static NotificationResult sent(String detail) {
        return new NotificationResult(NotificationStatus.SENT, detail);
    }

    public static NotificationResult skipped(String detail) {
        return new NotificationResult(NotificationStatus.SKIPPED, detail);
    }

    public static NotificationResult failed(String detail) {
        return new NotificationResult(NotificationStatus.FAILED, detail);
    }
}
