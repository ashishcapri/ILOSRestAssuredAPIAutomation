package API;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;

import Utility.BaseFile;
import Utility.ExcelRead;
import Utility.PropertyFile;
import Utility.ReadMetaData;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.ValidatableResponseOptions;

public class Bank_Account_Verification extends BaseFile {

    private static final String TESTCASES_SHEET = "bankAccount";
    private static final String SHEET_NAME = "bankAccountSheet";
    ExtentReports extent;

    PropertyFile propReader = new PropertyFile();
    int rownum;
    Map<String, Object> cache = ReadMetaData.getMetdataCache();

    // Read from Excel
    Map<String, Object>[][] empdata;

    @DataProvider(name = "bankdata")
    private Map<String, Object>[][] callTestData() throws Exception {
        String testDataSheet = propReader.getProp().get(SHEET_NAME).toString().trim();
        String filePath = System.getProperty("user.dir") + propReader.getProp().get(TESTCASES_SHEET).toString().trim();

        List<Map<String, Object>> dataList = ExcelRead.getExcelData(filePath, testDataSheet);
        rownum = dataList.size();
        empdata = new HashMap[rownum][1];
        for (int i = 0; i < rownum; i++) {
            empdata[i][0] = dataList.get(i);
        }
        return empdata;
    }

    // Set up Extent report
    @BeforeClass
    public void callExtentReportFile() {
        extent = extentBase;
    }

    // Test method to automate API
    @Test(dataProvider = "bankdata")
    private void bankAccount(Map<String, Object> testData) throws InterruptedException {

//        String cacheData = "bankData";
//        Map<String, Object> map = (Map<String, Object>) cache.get(cacheData);
//        List<String> outputParameters = (List<String>) map.get("outputKey");

        if (testData.entrySet() != null) {
            for (Map.Entry<String, Object> entry : testData.entrySet()) {
                JSONObject apirequest = (JSONObject) entry.getValue();
                String ifsc = (String) apirequest.get("ifsc");
                String accountNumber = (String) apirequest.get("account_number");
                Boolean str= new Boolean((boolean) apirequest.get("success"));
                String Success = str.toString();
                String httpStatusRequest = new String((String) apirequest.get("status"));
                int statusRequest = Integer.parseInt(httpStatusRequest);
                String TestID=(String) apirequest.get("testId");
                String testScenarioRequest = new String((String) apirequest.get("testScenarios"));
                String stringforreporting = TestID +"_"+ testScenarioRequest;
                // System.out.println(stringforreporting);
                testScenarioRequest =  "BankAccountVerfication_" +TestID+"_"+ testScenarioRequest;
                Reporter.log(testScenarioRequest);





                String auth = SwarnimLoginApi.Token;
                // Request Headers
                Map<String, String> headers = new HashMap<>();
                headers.put("accept", "application/json, text/plain, */*");
                headers.put("accept-language", "en-GB,en-US;q=0.9,en;q=0.8");
                headers.put("authorization", auth);
                headers.put("branch", "GLB0003");
                headers.put("cache-control", "no-cache");
                headers.put("content-type", "application/json");
                headers.put("origin", "https://cggl-dev.capriglobal.in");
                headers.put("pragma", "no-cache");
                headers.put("referer", "https://cggl-dev.capriglobal.in/");
                headers.put("sec-ch-ua", "\"Not)A;Brand\";v=\"99\", \"Google Chrome\";v=\"127\", \"Chromium\";v=\"127\"");
                headers.put("sec-ch-ua-mobile", "?0");
                headers.put("sec-ch-ua-platform", "\"Linux\"");
                headers.put("sec-fetch-dest", "empty");
                headers.put("sec-fetch-mode", "cors");
                headers.put("sec-fetch-site", "same-site");
                headers.put("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36");

                // Request Body
                JSONObject requestBody = new JSONObject();
                if(!TestID.equals("TC_16")) {
                    requestBody.put("ifsc", ifsc);
                    requestBody.put("account_number", accountNumber);}
                if(TestID.equals("TC_14"))
                    requestBody.remove("ifsc");
                if(TestID.equals("TC_15"))
                    requestBody.remove("account_number");
                //requestBody.put("customer_id", customerId);

                // Send API request using RestAssured
                Response initateresponses = given().headers(headers).body(requestBody.toJSONString()) // Setting headers and body
                        .when().post(propReader.getProp().get("initateBankURL").toString().trim()) // Make the POST request
                        ; // Expecting status code 200
                String initateresponse=initateresponses.getBody().asString();


                // Parse JSON response
                JSONObject apiResponse = null;
                try {
                    apiResponse = (JSONObject) new JSONParser().parse(initateresponse);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if(apiResponse.containsKey("data")) {
                    String ReqID=(String) ((JSONObject) apiResponse.get("data")).get("request_id");
                    String statusofAcoount=(String) apirequest.get("status_account");
                    String Bank_Name=(String) apirequest.get("Bank_Name");
                    String Branch=(String) apirequest.get("Branch_Name");
                    String Name=(String) apirequest.get("Benificary_Name");

                    String StausStatusRequest = new String((String) apirequest.get("StatusCode"));
                    int statusRequest2 = Integer.parseInt(StausStatusRequest);

                    if(TestID.equals("TC_10"))
                        ReqID=ReqID.substring(0,ReqID.length()-1);

                    String url=propReader.getProp().get("StatusBankURL").toString().trim() +ReqID;
                    Thread.sleep(2000);

                    Response response = given()
                            .header("accept", "application/json, text/plain, */*")
                            .header("accept-language", "en-GB,en-US;q=0.9,en;q=0.8")
                            .header("authorization", auth)
                            .header("cache-control", "no-cache")
                            .header("origin", "https://cggl-dev.capriglobal.in")
                            .header("pragma", "no-cache")
                            .header("priority", "u=1, i")
                            .header("branch", propReader.getProp().get("branch").toString().trim())
                            .header("referer", "https://cggl-dev.capriglobal.in/")
                            .header("sec-ch-ua", "\"Not)A;Brand\";v=\"99\", \"Google Chrome\";v=\"127\", \"Chromium\";v=\"127\"")
                            .header("sec-ch-ua-mobile", "?0")
                            .header("sec-ch-ua-platform", "\"Linux\"")
                            .header("sec-fetch-dest", "empty")
                            .header("sec-fetch-mode", "cors")
                            .header("sec-fetch-site", "same-site")
                            .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36")
                            .queryParam("pennyless_status_id", "8dcfbc04-8fca-4398-8ee3-1446776f92fd")
                            .when()
                            .get(url);

                    String responseBody = response.getBody().asString();
                    long responseTime = response.time();
                    if(responseTime>200){
                        testScenarioRequest=testScenarioRequest + "<html><br><span style='color:red;'>Response Time: " + responseTime + "ms</span></html>" ;
                    }
                    else {
                        testScenarioRequest = testScenarioRequest + "<html><br><span style='color:green;'>Response Time: " + responseTime + "ms</span></html>";
                    }
                    // Extract status code from the response
                    int statusCode = response.getStatusCode();
                    if(TestID.equals("TC_10")) {
                        if (statusCode== statusRequest2) {
                            extent.createTest(testScenarioRequest).log(Status.PASS,
                                    "API Request:  " + apirequest + "     API Response:  " + responseBody);
                        } else {
                            extent.createTest(testScenarioRequest).log(Status.PASS,
                                    "API Request:  " + apirequest + "     API Response:  " + responseBody);
                        }
                        break;
                    }


                    // String responseBody = response.getBody().asString();
                    JSONObject apiResponsestatus = null;
                    try {
                        apiResponsestatus = (JSONObject) new JSONParser().parse(responseBody);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    JSONObject apiResponsedata = null;
                    if (apiResponsestatus.get("data") != null) {
                        apiResponsedata = (JSONObject) apiResponsestatus.get("data");
                        //System.out.println("_________________________"+responseBody+auth);

                        String Statusof=(String)apiResponsedata.get("status");
                        boolean result= false;
                        if(Statusof.equals(statusofAcoount))
                            result=true;
                        else
                            result=false;
                        if(apiResponsedata.get("status").equals("SUCCESS")) {
                            JSONObject bankdeatils=(JSONObject) apiResponsedata.get("bank_details");

                            if(bankdeatils.get("bank_name").toString().equals(Bank_Name))
                                result=true;
                            else
                                result=false;
                            if(bankdeatils.get("branch_name").toString().equals(Branch))
                                result=true;
                            else
                                result=false;
                            if(bankdeatils.get("beneficiary_name").toString().equals(Name))
                                result=true;
                            else
                                result=false;


                        }
                        if (result ) {
                            extent.createTest(testScenarioRequest).log(Status.PASS,
                                    "API Request:  " + apirequest + "     API Response:  " + responseBody);
                        } else {
                            extent.createTest(testScenarioRequest).log(Status.FAIL,
                                    "API Request:  " + apirequest + "     API Response:  " + responseBody);
                        }




                    }
                }



                else {

                    Boolean stra= new Boolean((boolean) apiResponse.get("success"));
                    String ResponseSuccess = stra.toString();
                    long responseTime = initateresponses.time();
                    if(responseTime>200){
                        testScenarioRequest=testScenarioRequest + "<html><br><span style='color:red;'>Response Time: " + responseTime + "ms</span></html>" ;
                    }
                    else {
                        testScenarioRequest = testScenarioRequest + "<html><br><span style='color:green;'>Response Time: " + responseTime + "ms</span></html>";
                    }
                    // If API response data matches the request data, pass the test
                    if (ResponseSuccess.equals(Success) ) {
                        extent.createTest(testScenarioRequest).log(Status.PASS,
                                "API Request:  " + apirequest + "     API Response:  " + apiResponse);
                    } else {
                        extent.createTest(testScenarioRequest).log(Status.PASS,
                                "API Request:  " + apirequest + "     API Response:  " + apiResponse);
                    }
                }
            }
        }
    }


}
