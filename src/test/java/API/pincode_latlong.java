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

public class pincode_latlong extends BaseFile {

    private static final String TESTCASES_SHEET = "PINCODE_SET";
    private static final String SHEET_NAME = "PINCODE_SETSheet";
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

    /*
     * This method is used to test the API.
     */

    @Test(dataProvider = "SAAUTH")
    private void pinCodeAPI(Map<String, Object> testData) {
        if (testData.entrySet() != null) {
            for (Map.Entry<String, Object> entry : testData.entrySet()) {
                JSONObject apirequest = (JSONObject) entry.getValue();
                String pincode = new String((String) apirequest.get("Pincode"));


                // Logging the test scenario name
                // Reporter.log(testScenarioRequest);

                // Creating the request body for pincode API
                JSONObject requestBody = new JSONObject();
                requestBody.put("pincode", pincode);

                // Performing the API call
                Response responses = RestAssured.given()
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .post("https://middleware-dev.capriglobal.in/dms/pincode/v2/get");

                // Extracting response details
                String response = responses.getBody().asString();
                int StatusCode = responses.getStatusCode();
                long ResponseTime = responses.getTime();

                // Log response time for reporting


                // Parsing the response JSON
                Object JSON = null;
                try {
                    JSON = new JSONParser().parse(response);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                JSONObject apiResponse = (JSONObject) JSON;
                JSONObject data = (JSONObject) apiResponse.get("data");
                Object latitude = data.get("latitude");
                Object longitude = data.get("longitude");

                // Check if latitude or longitude is null
                if (latitude == null || longitude == null) {
                    System.out.println(pincode);
                }

                // Validate if message in response matches expected message

            }
        }
    }
}
