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
    public static String borrower_type=null;
    JSONObject apiResponse=null;;
    JSONObject dt= null;
    public static int guarantorSize=0;
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

                String Reason=null;

                apiResponse = (JSONObject) JSON;

                System.out.println("apiResponse 3 is : "+apiResponse);

                JSONObject newjson = (JSONObject) apiResponse.get("dt");

        System.out.println("portfolio_type is : "+newjson);

         dt=(JSONObject) apiResponse.get("dt");
        JSONObject primary=(JSONObject) dt.get("primary");
        JSONObject inquiry_details=(JSONObject) primary.get("inquiry_details");
        portfolio_type=inquiry_details.get("portfolio_type").toString();

        borrower_type=inquiry_details.get("borrower_type").toString();



        System.out.println("portfolio_type is : "+portfolio_type +"borrower_type is : "+borrower_type);



        try
        {
            JSONObject apiResponse = (JSONObject) new JSONParser().parse(response);
            JSONObject dt = (JSONObject) apiResponse.get("dt");

            if (dt != null) {
                JSONObject applicant = (JSONObject) dt.get("applicant");

                if (applicant != null) {
                    JSONArray guarantors = (JSONArray) applicant.get("guarantors");

                    if (guarantors != null) {
                         guarantorSize = guarantors.size();
                        System.out.println("Total guarantors: " + guarantorSize);
                    } else {
                        System.out.println("No guarantors found.");
                    }
                } else {
                    System.out.println("applicant is missing or null.");
                }
            } else {
                System.out.println("dt is missing or null.");
            }
        } catch (ParseException e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
        }

        /// ////////
        try
        {
            JSONObject apiResponse = (JSONObject) new JSONParser().parse(response);
            JSONObject dt = (JSONObject) apiResponse.get("dt");

            if (dt != null) {
                JSONObject applicant = (JSONObject) dt.get("applicant");

                if (applicant != null) {
                    JSONArray coApplicants = (JSONArray) applicant.get("co_applicant");

                    if (coApplicants != null) {
                        int coApplicantsSize = coApplicants.size();
                        System.out.println("Total coApplicants: " + coApplicantsSize);
                    } else {
                        System.out.println("No coapplicant found.");
                    }
                } else {
                    System.out.println("applicant is missing or null.");
                }
            } else {
                System.out.println("dt is missing or null.");
            }
        } catch (ParseException e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
        }



///

    }}