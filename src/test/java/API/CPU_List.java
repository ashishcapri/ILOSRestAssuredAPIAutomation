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
import org.json.JSONArray;
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

public class CPU_List extends BaseFile {

    private static final String TESTCASES_SHEET = "Cpulisting";
    private static final String SHEET_NAME = "CPUlistingSheet";
    ExtentReports extent;
       String token=ILOS_Login.Token;
       public static String app_ID=null;
    public static String obj_ID=null;

    PropertyFile propReader = new PropertyFile();
    int rownum;
    //Map<String, Object> cache = ReadMetaData.getMetdataCache();

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
            return empdata;
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
        Comman.Setdate();

        System.out.println("_________ print me ___________");
        if (testData.entrySet() != null) {
            for (Map.Entry<String, Object> entry : testData.entrySet()) {
                JSONObject apirequest = (JSONObject) entry.getValue();

                String httpStatusRequest = new String((String) apirequest.get("httpRequest"));
                int statusRequest = Integer.parseInt(httpStatusRequest);
                String testScenarioRequest = new String((String) apirequest.get("TestSenario"));
                String Status = new String((String) apirequest.get("Status"));
                String TestID = new String((String) apirequest.get("Test_ID"));


                Reporter.log(testScenarioRequest);

                System.out.println( "Token for second api :" +ILOS_Login.Token);

                Response responses = RestAssured.given()
                        .baseUri(propReader.getProp().get("CPU_listURL").toString().trim())
                        .queryParam("status", Status)
                        .queryParam("start_date", Comman.twomonthDate)
                        .queryParam("end_date", Comman.CurrentDate)
                        .header("accept", "application/json, text/plain, */*")
                        .header("authorization",  ILOS_Login.Token)
                        .header("origin", "https://ilos-uat.capriglobal.in")
                        .header("referer", "https://ilos-uat.capriglobal.in/")
                        .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36")
                        .contentType(ContentType.JSON)
                        .when()
                        .get();

                  System.out.println(propReader.getProp().get("CPU_listURL").toString().trim() + "?status="+Status + "&start_date="+Comman.twomonthDate+"&end_date=" + Comman.CurrentDate);


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

                System.out.println("apiResponse is : "+apiResponse);
///
                org.json.simple.JSONArray arr = (org.json.simple.JSONArray) apiResponse.get("dt");

                JSONObject firstele = (JSONObject) arr.get(0);

                app_ID= firstele.get("ap_no").toString();
                obj_ID= firstele.get("_id").toString();

                System.out.println("applicaiton no is : "+app_ID +" object id is : "+obj_ID);


                int StatusCode = responses.getStatusCode();
                long ResponseTime = responses.getTime();
                if(ResponseTime>200){
                    testScenarioRequest=testScenarioRequest + "<html><br><span style='color:red;'>Response Time: " + ResponseTime + "ms</span></html>" ;
                }
                else {
                    testScenarioRequest = testScenarioRequest + "<html><br><span style='color:green;'>Response Time: " + ResponseTime + "ms</span></html>";
                }

            }

        }
    }}