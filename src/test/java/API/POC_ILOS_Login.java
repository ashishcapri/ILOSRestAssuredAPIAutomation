package API;

import Utility.BaseFile;
import Utility.ExcelRead;
import Utility.PropertyFile;
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

public class POC_ILOS_Login extends BaseFile {

    ExtentReports extent;
       public static String Token=null;
    PropertyFile propReader = new PropertyFile();
    int rownum;

    // ✅ DataProvider to supply multiple names, ages, and cities
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

    @Test(dataProvider = "loginDataProvider")
    public void testLogin(String user, String pswd, boolean isValidLogin) {
        // Request payload
        String payload = String.format("{ \"app\": \"XLX\", \"user\": \"%s\", \"pswd\": \"%s\", \"l_t\": \"capri_user\" }", user, pswd);

        // API request
        Response response = RestAssured.given()
                .baseUri("https://ilosapi-uat.capriglobal.in")
                .basePath("/ilosuser/v1/login")
                .header("accept", "application/json, text/plain, */*")
                .header("accept-language", "en-GB,en-US;q=0.9,en;q=0.8")
                .header("content-type", "application/json")
                .header("origin", "https://ilos-uat.capriglobal.in")
                .header("referer", "https://ilos-uat.capriglobal.in/")
                .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36")
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post();

        // Print Response
        System.out.println("Response for user [" + user + "]: " + response.getBody().asString());

        // Validate Response
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, isValidLogin ? 200 : 400, "Unexpected status code!");

        // Extract Token for valid login
        if (isValidLogin) {
            try {
                JSONObject jsonResponse = (JSONObject) new JSONParser().parse(response.getBody().asString());
                Token = jsonResponse.get("token").toString();
                System.out.println("Extracted Token: " + Token);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid login attempt, no token generated.");
        }
    }
}