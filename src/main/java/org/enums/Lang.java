package org.enums;

import lombok.Getter;

@Getter
public enum Lang {
    SPANISH("es"),
    UKRAINIAN("ua"),
    JAPANESE("ja"),
    CHINESE_SIMPLIFIED("zh_cn"),
    VIETNAMESE("vi");

    private String langCode;

    Lang(String langCode) {
        this.langCode = langCode;
    }
}
