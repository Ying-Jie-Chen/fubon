package com.fubon.ecplatformapi.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "innerBuilder")
public class ApiRespDTO<T> {

        @Builder.Default
        private String StatusCode = "0000";

        @Builder.Default
        private String StatusDesc = "Success!!";

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private transient T data;

        public static <T> ApiRespDTOBuilder<T> builder () {
            return (ApiRespDTOBuilder<T>) innerBuilder();
        }

        public static <T> ApiRespDTOBuilder<T> builder(StatusCodeEnum statusCode, String message) {
            return (ApiRespDTOBuilder<T>) innerBuilder().StatusCode(statusCode.name()).StatusDesc(message);
        }

}
