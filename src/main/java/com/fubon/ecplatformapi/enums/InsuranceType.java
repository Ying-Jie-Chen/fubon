package com.fubon.ecplatformapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Set;

@Getter
@AllArgsConstructor
public enum InsuranceType {
    MOT("MOT"), CQCCX("CQCCX"), CHCRX("CHCRX"), CTX("CTX"),
    CGX("CGX"), FIR("FIR"), ENG("ENG"), MGO("MGO"), CAS("CAS");

    public static final InsuranceType Car_Insurance = MOT;
    public static final Set<InsuranceType> Business_Insurance = EnumSet.of(FIR, MGO, CAS, ENG);
    public static final Set<InsuranceType> Personal_Insurance = EnumSet.of(CQCCX, CHCRX, CTX, CGX);

    private final String name;

}
