package com.fubon.ecplatformapi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fubon.ecplatformapi.model.dto.resp.FbQueryResp;
import com.fubon.ecplatformapi.model.dto.resp.FbQueryResp.PolicyResult;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FbQueryRespDeserializer extends JsonDeserializer<FbQueryResp> {

    @Override
    public FbQueryResp deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        FbQueryResp fbQueryResp = new FbQueryResp();

        // 解析 policyResults
        JsonNode policyResultsNode = node.get("policyResults");
        if (policyResultsNode != null && policyResultsNode.isArray()) {
            for (JsonNode policyResultNode : policyResultsNode) {
                PolicyResult policyResult = new PolicyResult();

                policyResult.setClsGrp(policyResultNode.get("clsGrp").asText());
                policyResult.setModule(policyResultNode.get("module").asText());
                policyResult.setPolFormatid(policyResultNode.get("polFormatid").asText());
                policyResult.setRmaClinameI(policyResultNode.get("rmaClinameI").asText());
                policyResult.setRmaUidI(policyResultNode.get("rmaUidI").asText());
                policyResult.setMohPlatno(policyResultNode.get("mohPlatno").asText());
                policyResult.setAscIscXref(policyResultNode.get("ascIscXref").asText());
                policyResult.setUnPaidPrm(policyResultNode.get("unPaidPrm").asInt());

                // 解析日期
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                try {
                    Calendar secEffdate = Calendar.getInstance();
                    secEffdate.setTime(dateFormat.parse(policyResultNode.get("secEffdate").asText()));
                    policyResult.setSecEffdate(secEffdate);

                    Calendar secExpdate = Calendar.getInstance();
                    secExpdate.setTime(dateFormat.parse(policyResultNode.get("secExpdate").asText()));
                    policyResult.setSecExpdate(secExpdate);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                fbQueryResp.getPolicyResults().add(policyResult);
            }
        }

        return fbQueryResp;
    }
}
