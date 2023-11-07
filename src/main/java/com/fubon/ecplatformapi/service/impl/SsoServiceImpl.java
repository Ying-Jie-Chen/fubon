package com.fubon.ecplatformapi.service.impl;


import com.fubon.ecplatformapi.config.EcwsConfig;
import com.fubon.ecplatformapi.enums.SSOLoginEnum;
import com.fubon.ecplatformapi.helper.JsonHelper;
import com.fubon.ecplatformapi.model.dto.resp.SSOTokenRespDTO;
import com.fubon.ecplatformapi.model.dto.req.SsoReqDTO;
import com.fubon.ecplatformapi.service.SsoService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class SsoServiceImpl implements SsoService {
    @Autowired
    JsonHelper jsonHelper;
    @Autowired
    EcwsConfig ecwsConfig;

    private final WebClient webClient;

    @Autowired
    public SsoServiceImpl(WebClient.Builder webClientBuilder, EcwsConfig ecwsConfig) {
        this.ecwsConfig = ecwsConfig;
        this.webClient = webClientBuilder.baseUrl(ecwsConfig.getDomain()).build();
    }

    @Override
    public String getSSOToken(String sessionId){
            String jsonRequest = jsonHelper.convertSsoTokenToJson(ecwsConfig.fubonSsoTokenDTO(), sessionId);
            SSOTokenRespDTO respDTO = callFubonService(jsonRequest, SSOTokenRespDTO.class);
            return respDTO.getAny().getSid();
    }

    private <T> T callFubonService(String jsonRequest, Class<T> responseType) {
        return webClient
                .post()
                .uri("")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonRequest)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }


    @Override
    public void verifySSOLogin(SsoReqDTO ssoReq) {
        String domain = ecwsConfig.getDomain();
        String tokenKey = ssoReq.getToken();
        try {

            String msg = createSoapRequest(domain, tokenKey);
            sendHttpPostRequest(ecwsConfig.getDomain(), msg);

        } catch (Exception e) {
            log.error("SOAP 請求失敗");
            e.printStackTrace();
        }
    }

    private String createSoapRequest(String domain, String tokenKey) {
        String webServiceAcc;
        String webServicePwd;
        String ipAddress = null;
        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        String srcSystem = "FBLIFESSO";

        if (domain.contains("/ttran.518fb.com/") || domain.contains("/tb2b.518fb.com/")) {
            log.info(":測試環境");
            webServiceAcc = SSOLoginEnum.TEST.getWebServiceAcc();
            webServicePwd = SSOLoginEnum.TEST.getWebServicePwd();
        } else {
            log.info(":正式環境");
            webServiceAcc = SSOLoginEnum.PRODUCTION.getWebServiceAcc();
            webServicePwd = SSOLoginEnum.PRODUCTION.getWebServicePwd();
        }

        log.info("組成請求字串 #Start");
        String reqInputParam = webServiceAcc +"|"+ webServicePwd +"|"+ srcSystem + "|" + ipAddress + "||" + tokenKey;
        return genSoapRequest(reqInputParam).replace("#requestParam", reqInputParam);
    }

    private String genSoapRequest(String reqInputParam) {
        String soapRequestTemplate = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">" +
                "<soapenv:Header>" + "<soapenv:Body>" + "<tem:GetData>" +
                "<tem:InputMessage>#requestParam</tem:InputMessage>" +
                "</tem:GetData>" + "</soapenv:Body>" + "</soapenv:Envelope>";
        return soapRequestTemplate.replace("#requestParam", reqInputParam);
    }

    public static void sendHttpPostRequest(String url, String xmlData) {
        log.info("建立對外 API Proxy 連線 #Start");
        HttpURLConnection conn = null;
        try {
            conn = openHttpConnection(url);
            writeXmlDataToConnection(conn, xmlData);

            handleResponse(conn);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private static HttpURLConnection openHttpConnection(String url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.connect();
            return conn;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    private static void writeXmlDataToConnection(HttpURLConnection conn, String xmlData) {
        try (OutputStreamWriter reqOutput = new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8)) {
            reqOutput.write(xmlData);
            reqOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void handleResponse(HttpURLConnection conn) {
        int responseCode = 0;
        try {
            responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try {
                    handleSuccessfulResponse(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            } else {
                throw new Exception("HTTP request failed with response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void handleSuccessfulResponse(HttpURLConnection conn){
        InputStream respInput = null;
        try {
            respInput = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(respInput, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder outputString = new StringBuilder();
            String respString;
            while ((respString = bufferedReader.readLine()) != null) {
                outputString.append(respString);
            }
            String soapResponse = outputString.toString();
            if (StringUtils.isNotBlank(soapResponse) && !soapResponse.startsWith("<?xml")) {
                log.info("***人壽SSO 回應 ERR:" + soapResponse);
            } else {
                handleSoapResponse(soapResponse);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleSoapResponse(String soapResponse) {
        log.info("發送 SOAP 請求，取得回覆資料 #Start");
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(soapResponse));
            Document doc = docBuilder.parse(is);
            NodeList returnNodes = doc.getElementsByTagName("GetDataResponse");
            if (returnNodes.getLength() > 0) {
                Node returnNode = returnNodes.item(0);
                Element returnNodeElement = (Element) returnNode;
                NodeList getDataResultNodes = returnNodeElement.getElementsByTagName("GetDataResult");

                if (getDataResultNodes.getLength() > 0) {
                    String getDataResult = getDataResultNodes.item(0).getTextContent();

                    if (StringUtils.isNotEmpty(getDataResult) && getDataResult.startsWith("00|")) {
                        handleSuccessfulDataResult(getDataResult, getDataResult, soapResponse);
                    } else {
                        throw new Exception("人壽SSO WebService 返回錯誤: " + getDataResult);
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void handleSuccessfulDataResult(String getDataResult, String OriGetDataResult, String soapResponse) throws Exception {
        String[] getDataResultArry = getDataResult.split("\\|");
        String[] getDataResultArry2 = getDataResultArry[1].split("-");

        if (getDataResultArry2.length >= 2) {
            String responseId = maskIdAndCompanyNo(getDataResultArry2[0]);
            String responseName = maskName(getDataResultArry2[1]);
            getDataResult = getDataResult.replace(getDataResultArry2[0], responseId);
            getDataResult = getDataResult.replace(getDataResultArry2[1], responseName);

            System.out.println("***人壽SSO WebService 回應SOAP XML:" + soapResponse.replace(OriGetDataResult, getDataResult));
            System.out.println("人壽SSO WebService 回應解析結果:" + getDataResult);

            String wsUnitCode = getDataResultArry2[2].trim().substring(0, 5);
        } else {
            throw new Exception("SOAP Response 格式不正確");
        }
    }

    public static String maskIdAndCompanyNo(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        int numStars = Math.min(5, input.length());
        String maskedPart = "*".repeat(numStars);
        String remainingPart = input.substring(numStars);
        return maskedPart + remainingPart;
    }

    public static String maskName(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        char firstChar = input.charAt(0);
        String maskedPart = "*".repeat(input.length() - 1);
        return firstChar + maskedPart;
    }

}

