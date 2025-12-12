package org.nextme.account_server.account.domain.entity.BankItem;

public enum JoinEligibility {
    UNLIMITED, // 제한없음
    SEOMIN, // 서민전용
    PARTINAL; // 일부제한

    public static JoinEligibility fromString(String value) {
        switch (value) {
            case "1": return UNLIMITED;
            case "2": return SEOMIN;
            case "3": return PARTINAL;
            default: throw new IllegalArgumentException("Unknown join_deny value: " + value);
        }
    }
}
