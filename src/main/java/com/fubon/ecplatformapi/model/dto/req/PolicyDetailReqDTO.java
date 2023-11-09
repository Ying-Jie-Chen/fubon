package com.fubon.ecplatformapi.model.dto.req;

import com.fubon.ecplatformapi.validation.InsTypeValidation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PolicyDetailReqDTO {

    @NotNull(message = "insType 不可為空值")
    @Size(max = 5, message = "insType 最大長度為 5")
    @InsTypeValidation
    private String insType;

    @NotNull(message = "policyNum 不可為空值")
    @Size(max = 14, message = "policyNum 最大長度為 14")
    private String policyNum;
}
