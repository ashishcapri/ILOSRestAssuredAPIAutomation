package API;

import Utility.BaseFile;
import Utility.ExcelRead;
import Utility.PropertyFile;
import Utility.ReadMetaData;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ILOS_Login extends BaseFile {

    private static final String TESTCASES_SHEET = "Sbu_login";
    private static final String SHEET_NAME = "Sbu_loginSheet";
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
                String app = new String((String) apirequest.get("app"));

                Reporter.log(testScenarioRequest);

                JSONObject requestBody = new JSONObject();
                requestBody.put("username", UserID);
                requestBody.put("password", Passward);
                requestBody.put("app", app);
                if(TestID.equals("TC_04"))
                    requestBody.remove("username");
                if(TestID.equals("TC_05"))
                    requestBody.remove("password");
                if(TestID.equals("TC_06"))
                    requestBody.remove("app");

                // Perform the API call
                Response responses = RestAssured.given()
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .post(propReader.getProp().get("Sbu_loginBaseURL").toString().trim());

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
                    e.printStackTrace();
                }
                JSONObject apiResponse;

                apiResponse = (JSONObject) JSON;

                if(apiResponse.containsKey("msg")){
                    String message = (String) apiResponse.get("msg");
                    if(!message.equals(Message))
                        flag = false;
                }
            if(TestID.equals("TC_04") || TestID.equals("TC_09") ){
                if(!apiResponse.containsKey("username")){

                        flag = false;
                }

            }
                if(TestID.equals("TC_05") || TestID.equals("TC_08") ){
                    if(!apiResponse.containsKey("password")){

                        flag = false;
                    }

                }
                if(TestID.equals("TC_06") || TestID.equals("TC_07") ){
                    if(!apiResponse.containsKey("app")){

                        flag = false;
                    }

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