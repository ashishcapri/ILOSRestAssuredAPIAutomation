package API;

import Utility.BaseFile;
import Utility.ExcelRead;
import Utility.PropertyFile;
import com.aventstack.extentreports.ExtentReports;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
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

public class CPU_Lead_Detail extends BaseFile {

    ExtentReports extent;
       String token=ILOS_Login.Token;

    PropertyFile propReader = new PropertyFile();
    int rownum;
    public static String portfolio_type=null;

    //Map<String, Object> cache = ReadMetaData.getMetdataCache();

    /**
     * This method is used to read from excel.
     */
    Map<String, Object>[][] empdata;


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

    @Test
    private void pinCodeAPI() {


        System.out.println("_________ print me ___________");



                System.out.println( "Token for second api :" +ILOS_Login.Token);

                Response responses = RestAssured.given()
                        .baseUri(propReader.getProp().get("CPU_leaddetail").toString().trim() +CPU_List.obj_ID)
                        .header("accept", "application/json, text/plain, */*")
                        .header("authorization",  ILOS_Login.Token)
                        .header("origin", "https://ilos-uat.capriglobal.in")
                        .header("referer", "https://ilos-uat.capriglobal.in/")
                        .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36")
                        .contentType(ContentType.JSON)
                        .when()
                        .get();

                // Store the response
                String response= responses.getBody().asString();
                System.out.println(response);

/////////////

                Object JSON = null;
                try {
                    JSON = new JSONParser().parse((String) response.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                JSONObject apiResponse;
                String Reason=null;

                apiResponse = (JSONObject) JSON;

                System.out.println("apiResponse 3 is : "+apiResponse);

                JSONObject newjson = (JSONObject) apiResponse.get("dt");

        System.out.println("portfolio_type is : "+newjson);

        JSONObject dt=(JSONObject) apiResponse.get("dt");
        JSONObject primary=(JSONObject) dt.get("primary");
        JSONObject inquiry_details=(JSONObject) primary.get("inquiry_details");
        portfolio_type=inquiry_details.get("portfolio_type").toString();




        System.out.println("portfolio_type is : "+portfolio_type);

///

    }}