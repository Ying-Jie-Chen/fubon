package com.fubon.ecplatformapi.service.impl;

//import com.fubon.ecplatformapi.repository.CompanyRepository;
//import com.fubon.ecplatformapi.repository.LicenseInfoRepository;
import com.fubon.ecplatformapi.model.dto.req.SsoReqDTO;
import com.fubon.ecplatformapi.service.SsoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class SsoServiceImpl implements SsoService {

//    @Autowired
//    LicenseInfoRepository licenseInfoRepository;
//    @Autowired
//    CompanyRepository companyRepository;

    public Object perfornSsoLogin(SsoReqDTO sspReq) {
        String webServiceAcc;
        String webServicePwd;

        try {

            log.info("判斷請求參數 domain決定環境 #Start");
            if(sspReq.getDomain().contains("/ttran.518fb.com/") || sspReq.getDomain().contains("/tb2b.518fb.com/")) {
                log.info(":測試環境");
                webServiceAcc = "zAq1xSw2";
                webServicePwd = "cDe3vFr4";
            } else {
                log.info(":正式環境");
                webServiceAcc = "zaq12wsxcde34rfV";
                webServicePwd = "vfr43edcxsw21qaZ";
            }

            log.info("組成請求字串 #Start");
            String soapResp = sendAndReceiveSoapRequest(webServiceAcc, webServicePwd, sspReq.getToken());
            String[] webserviceResp = soapResp.split("\\|");
            String userId = webserviceResp[1].split("-")[0].trim();
            String userName = webserviceResp[1].split("-")[1].trim();
            String unitCode = webserviceResp[1].split("-")[2].trim();
            log.info("soap response: " + soapResp);
            log.info("web service response: " + webserviceResp);

            log.info("檢核是否有考過保險證照 #Start");
            //List<LicenseInfo> licenseInfoList = licenseInfoRepository.findByLicenseIdAndCodeFlagNotIn(userId, Arrays.asList("C", "D", "S"));

//            if (licenseInfoList.isEmpty()) {
//                log.error("用户 " + userId + " 未考取富邦產險保險證照");
//                throw new RuntimeException("此ID[" + userId + "]尚未登錄富邦產險");
//            }

//            log.info("檢核產險公司別是否開跨售ID #Start");
//            if (!checkCrossSellingEligibility(unitCode)) {
//                log.error("產險EC公司別[" + unitCode + "]尚未開檔跨售ID[" + userId + "]，請洽產險窗口或電子商務部！");
//                throw new RuntimeException("產險EC公司別[" + unitCode + "]尚未開檔跨售ID[" + userId + "]，請洽產險窗口或電子商務部！");
//            }


//            UserInfo user = sessionService.getSession();
//            user.setAgent_id(userId);
//            user.setAgent_name(userName);
//            user.setUnionNum(unitCode);
//
//            return user;
            return null;

        } catch (IOException e) {
            log.error("SOAP 請求失敗: " + e.getMessage(), e);
            throw new RuntimeException("SOAP 請求失敗", e);
        }
    }

    private String sendAndReceiveSoapRequest(String webServiceAcc, String webServicePwd, String token) throws IOException {
        String soapEndpoint = ""; //  SOAP 服務地址
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(soapEndpoint);

        // 設置 SOAP 請求的内容
        String soapRequest = buildSoapRequest(webServiceAcc, webServicePwd, token);
        StringEntity entity = new StringEntity(soapRequest, "UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        // 執行 HTTP POST 請求
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new IOException("SOAP request failed with status code: " + response.getStatusLine().getStatusCode());
        }

        // 讀取回應
        String soapResponse = EntityUtils.toString(responseEntity);

        return soapResponse;
    }

    private String buildSoapRequest(String webServiceAcc, String webServicePwd, String token) {
        // 建構 SOAP 請求的 JSON 內容，包括 webServiceAcc、webServicePwd 和 token
//        String soapRequest = "<SOAPRequest>" +
//                "<WebServiceAcc>" + webServiceAcc + "</WebServiceAcc>" +
//                "<WebServicePwd>" + webServicePwd + "</WebServicePwd>" +
//                "<Token>" + token + "</Token>" + "</SOAPRequest>";
//        return soapRequest;

        String jsonRequest = "{" +
                "\"WebServiceAcc\":\"" + webServiceAcc + "\"," +
                "\"WebServicePwd\":\"" + webServicePwd + "\"," +
                "\"Token\":\"" + token + "\"" +
                "}";

        return jsonRequest;
    }

//    private boolean checkCrossSellingEligibility(String companyCode) {
//        CompanyInfo companyInfo = companyRepository.findById(companyCode).orElse(null);
//        return companyInfo != null && "B".equals(companyInfo.getCompanyType());
//    }
}
