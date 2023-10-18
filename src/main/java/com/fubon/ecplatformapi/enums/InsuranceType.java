package com.fubon.ecplatformapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InsuranceType {
    MOT("MOT"), CQCCX("CQCCX"), CHCRX("CHCRX"), CTX("CTX"),
    CGX("CGX"), FIR("FIR"), ENG("ENG"), MGO("MGO"), CAS("CAS");

    private final String name;

}
