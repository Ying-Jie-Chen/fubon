package com.fubon.ecplatformapi.FubonApi;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FubonApiResponse {

    private String code;
    private String message;
    private Object data;
    private String token;

}
