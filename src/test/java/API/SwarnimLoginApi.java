package API;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;

import Utility.BaseFile;
import Utility.PropertyFile;
import Utility.ExcelRead;
import Utility.ReadMetaData;
import io.restassured.response.Response;

public class SwarnimLoginApi extends BaseFile {
    public static String Token=new String();


    ExtentReports extent;

    PropertyFile propReader = new PropertyFile();
    int rownum;
    Map<String, Object> cache = ReadMetaData.getMetdataCache();

    // This method is used to read data from excel
    Map<String, Object>[][] empdata;



    @BeforeClass
    public void callExtentReportFile() {
        extent = extentBase;

    }

    @Test
    private void swarnimLogin(Map<String, Object> testData) {



                JSONObject apirequest = new JSONObject();
                apirequest.put("username", "Nikhil.jaiswal2@capriglobal.in");
        apirequest.put("app", "Gold Loans");
        apirequest.put("password", "CgC26062@3828$");



                    // Send POST request with login credentials
                    Response responses = given()
                            .header("accept", "application/json, text/plain, */*")
                            .header("accept-language", "en-GB,en-US;q=0.9,en;q=0.8")
                            .header("cache-control", "no-cache")
                            .header("content-type", "application/json")
                            .header("origin", "https://cggl-dev.capriglobal.in")
                            .header("pragma", "no-cache")
                            .header("priority", "u=1, i")
                            .header("referer", "https://cggl-dev.capriglobal.in/")
                            .header("sec-ch-ua", "\"Not)A;Brand\";v=\"99\", \"Google Chrome\";v=\"127\", \"Chromium\";v=\"127\"")
                            .header("sec-ch-ua-mobile", "?0")
                            .header("sec-ch-ua-platform", "\"Linux\"")
                            .header("sec-fetch-dest", "empty")
                            .header("sec-fetch-mode", "cors")
                            .header("sec-fetch-site", "same-site")
                            .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36")
                            .body((apirequest.toString()))
                            .when()
                            .post("https://middleware-dev.capriglobal.in/gl-services/user/users/user/v2/login");

                    String response = responses.getBody().asString();
                    long responseTime = responses.time();

                    Object JSON = null;
                    try {
                        JSON = new JSONParser().parse(response);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    JSONObject apiResponse = (JSONObject) JSON;

                    System.out.println(response);
                    //  JSONObject apiResponseData = null;

                    String apiResponseRole = null;
                    String apiResponseSucess = (String) apiResponse.get("success");
                    String data = null;


                    if (apiResponse.containsKey("role") && apiResponseSucess.equals("true")) {
                        apiResponseRole = (String) apiResponse.get("role");

                        // apiResponseSucess = (String) apiResponse.get("success");
                    }

                        Token = (String) apiResponse.get("accessToken");



                    // Validate the response reference against the request reference

    }}