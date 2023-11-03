package com.fubon.ecplatformapi.model.dto.resp.fubon;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class FubonPrnDetailResp {
    private List<PrnResult> prmList;

    @Data
    @Builder
    public static class PrnResult {
        @Size(max = 30)
        private String prnDoc;

        @Size(max = 100)
        private String prnDocName;

        @Size(max = 1)
        private String prnFormat;

        @Size(max = 1)
        private String prnType;

        @Size(max = 1)
        private String prnSendType;

        @Size(max = 1)
        private String prnPrintStatus;

        private Date prnPrintdate;
    }
}
