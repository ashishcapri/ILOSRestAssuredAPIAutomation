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
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ILOS_Login extends BaseFile {

    private static final String TESTCASES_SHEET = "ILOS_login";
    private static final String SHEET_NAME = "ILOSSheet1";
    ExtentReports extent;
       public static String Token=null;
    PropertyFile propReader = new PropertyFile();
    int rownum;
    //Map<String, Object> cache = ReadMetaData.getMetdataCache();

    /**
     * This method is used to read from excel.
     */
    Map<String, Object>[][] empdata;

    /*
     * DataProvider for multiple login scenarios
     */
    @DataProvider(name = "loginDataProvider")
    public Object[][] provideLoginData() {
        return new Object[][]{
                {"EMPAAtC@capriglobal.in", "vIker0b0#3v5", true},  // ✅ Valid Login
                {"invaliduser@capriglobal.in", "wrongpassword", false},  // ❌ Invalid Login
        };
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

    /*
     * Test Login API with different credentials
     */
    @Test(dataProvider = "loginDataProvider")
    public void testLogin(String user, String pswd, boolean isValidLogin) {
        // Request payload
        String payload = String.format("{ \"app\": \"XLX\", \"user\": \"%s\", \"pswd\": \"%s\", \"l_t\": \"capri_user\" }", user, pswd);

        // API request
        Response response = RestAssured.given()
                .baseUri("https://ilosapi-uat.capriglobal.in")
                .basePath("/ilosuser/v1/login")
                .header("accept", "application/json, text/plain, */*")
                .header("content-type", "application/json")
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post();

        // Print Response
        String responseBody = response.getBody().asString();
        System.out.println("Response for user [" + user + "]: " + responseBody);

        // Validate Response
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, isValidLogin ? 200 : 400, "Unexpected status code!");

        // Extract Token from JSON path "dt.token"
        if (isValidLogin) {
            try {
                JSONObject jsonResponse = (JSONObject) new JSONParser().parse(responseBody);
                JSONObject dtObject = (JSONObject) jsonResponse.get("dt");  // Extract "dt" object
                if (dtObject != null && dtObject.get("token") != null) {
                    Token = dtObject.get("token").toString();
                    System.out.println("Extracted Token: " + Token);
                } else {
                    System.out.println("Token not found in response!");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid login attempt, no token generated.");
        }
    }
}