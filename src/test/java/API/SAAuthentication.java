package API;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
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

public class SAAuthentication extends BaseFile {

    private static final String TESTCASES_SHEET = "SAAuthFie";
    private static final String SHEET_NAME = "SAAuthSheet";
    ExtentReports extent;

    PropertyFile propReader = new PropertyFile();
    int rownum;
    Map<String, Object> cache = ReadMetaData.getMetdataCache();

    /**
     * This method is used to read from excel.
     */
    Map<String, Object>[][] empdata;

    @DataProvider(name = "SAAUTH")
    private Map<String, Object>[][] callTestData() throws Exception {
        String testDataSheet = propReader.getProp().get(SHEET_NAME).toString().trim();
        String filePath = System.getProperty("user.dir") + propReader.getProp().get(TESTCASES_SHEET).toString().trim();

        {
            List<Map<String, Object>> dataList = ExcelRead.getExcelData(filePath, testDataSheet);
            rownum = dataList.size();
            empdata = (Map<String, Object>[][]) new HashMap[rownum][1];
            for (int i = 0; i < rownum; i++) {
                empdata[i][0] = dataList.get(i);
            }
            return (empdata);
        }
    }

    /*
     * This method is calling extent report.
     */

    @BeforeClass
    public void callExtentReportFile() {
        extent = extentBase;
    }

    /*
     * This method is used to test the API.
     */

    @Test(dataProvider = "SAAUTH")
    private void pinCodeAPI(Map<String, Object> testData) {


        if (testData.entrySet() != null) {
            for (Map.Entry<String, Object> entry : testData.entrySet()) {
                JSONObject apirequest = (JSONObject) entry.getValue();
                String UserID = new String((String) apirequest.get("User"));
                String Passward = new String((String) apirequest.get("Passward"));
                String httpStatusRequest = new String((String) apirequest.get("httpStatus"));
                int statusRequest = Integer.parseInt(httpStatusRequest);
                String testScenarioRequest = new String((String) apirequest.get("testScenarios"));
                String Message = new String((String) apirequest.get("message"));
                String TestID = new String((String) apirequest.get("testID"));

                Reporter.log(testScenarioRequest);

                JSONObject requestBody = new JSONObject();
                requestBody.put("username", UserID);
                requestBody.put("password", Passward);
                if(TestID.equals("TC_04"))
                    requestBody.remove("username");
                if(TestID.equals("TC_05"))
                    requestBody.remove("password");

                // Convert the map to a JSON string


                // Perform the API call
                Response responses = RestAssured.given()
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .post("https://middleware-dev.capriglobal.in/sa/authentication");

                // Store the response
                String response= responses.getBody().asString();
                System.out.println(response);
                int StatusCode = responses.getStatusCode();
                long ResponseTime = responses.getTime();
                if(ResponseTime>200){
                    testScenarioRequest=testScenarioRequest + "<html><br><span style='color:red;'>Response Time: " + ResponseTime + "ms</span></html>" ;
                }
                else {
                    testScenarioRequest = testScenarioRequest + "<html><br><span style='color:green;'>Response Time: " + ResponseTime + "ms</span></html>";
                }
                boolean flag = true;
                if(statusRequest!=StatusCode)
                    flag = false;
                Object JSON = null;
                try {
                    JSON = new JSONParser().parse((String) response.toString());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                JSONObject apiResponse;

                apiResponse = (JSONObject) JSON;

                if(apiResponse.containsKey("message")){
                    String message = (String) apiResponse.get("message");
                    if(!message.equals(Message))
                        flag = false;
                }
                if(flag){
                    extent.createTest(testScenarioRequest).log(Status.PASS,
                            "API Request:  " + requestBody + "     API Response:  " + response);
                } else {
                    extent.createTest(testScenarioRequest).log(Status.FAIL,
                            "API Request:  " + requestBody + "     API Response:  " + response);
                }




            }
        }

    }
}
