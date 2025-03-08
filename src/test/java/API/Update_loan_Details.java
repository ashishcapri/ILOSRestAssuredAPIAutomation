package API;

import Utility.BaseFile;
import Utility.ExcelRead;
import Utility.PropertyFile;
import com.aventstack.extentreports.ExtentReports;
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

public class Update_loan_Details extends BaseFile {

    private static final String TESTCASES_SHEET = "loandetailupdate";
    private static final String SHEET_NAME = "loandetailupdateSheet";
    ExtentReports extent;
       String token=ILOS_Login.Token;


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

        System.out.println("_________ print me _______ 4____");

        if (CPU_Lead_Detail.portfolio_type.equals("home loan") && CPU_Lead_Detail.borrower_type.equals("sep")) {


        if (testData.entrySet() != null) {
            for (Map.Entry<String, Object> entry : testData.entrySet()) {
                JSONObject apirequest = (JSONObject) entry.getValue();

                String httpStatusRequest = new String((String) apirequest.get("httpRequest"));
                int statusRequest = Integer.parseInt(httpStatusRequest);
                String testScenarioRequest = new String((String) apirequest.get("TestSenario"));
                String TestID = new String((String) apirequest.get("Test_ID"));

                String PortfolioType = new String((String) apirequest.get("portfoliotype"));
                String EntityType = new String((String) apirequest.get("entitytype"));
                String Source = new String((String) apirequest.get("source"));
                String NameOfRM = new String((String) apirequest.get("name_of_rm"));
                String LoanBranch = new String((String) apirequest.get("loan_branch"));
                String LoanPurpose = new String((String) apirequest.get("loan_purpose"));
                String TransactionType = new String((String) apirequest.get("transaction_type"));
                String ProductType = new String((String) apirequest.get("product_type"));
                String LoanRequirement = new String((String) apirequest.get("loan_requirement"));
                String ExpectedROI = new String((String) apirequest.get("expected_roi"));
                String SpecialProgramCode = new String((String) apirequest.get("special_program_code"));
                String LoanTenureInMonths = new String((String) apirequest.get("loan_tenor_in_months"));


                Reporter.log(testScenarioRequest);
                System.out.println( "Token for second api :" +ILOS_Login.Token);

                JSONObject subSource = new JSONObject();
                subSource.put("name_of_rm", NameOfRM);

                JSONObject inquiryDetails = new JSONObject();
                inquiryDetails.put("portfolio_type", PortfolioType);
                inquiryDetails.put("entity_type", EntityType);
                inquiryDetails.put("source", Source);
                inquiryDetails.put("sub_source", subSource);
                inquiryDetails.put("loan_branch", LoanBranch);
                inquiryDetails.put("loan_purpose", LoanPurpose);
                inquiryDetails.put("transaction_type", TransactionType);
                inquiryDetails.put("product_type", ProductType);
                inquiryDetails.put("loan_requirement", LoanRequirement);
                inquiryDetails.put("loan_tenor_in_months", LoanTenureInMonths);
                inquiryDetails.put("expected_roi", ExpectedROI);
                inquiryDetails.put("special_program_code", SpecialProgramCode);

                JSONObject primary = new JSONObject();
                primary.put("inquiry_details", inquiryDetails);

                JSONObject requestBody = new JSONObject();
                requestBody.put("primary", primary);


                System.out.println("_________ print me _______ 5____");


                Response responses = RestAssured.given()
                        .baseUri(propReader.getProp().get("loandetailupdateURL").toString().trim()+CPU_List.obj_ID).body(requestBody)
                        .header("accept", "application/json, text/plain, */*")
                        .header("authorization",  ILOS_Login.Token)
                        .header("origin", "https://ilos-uat.capriglobal.in")
                        .header("referer", "https://ilos-uat.capriglobal.in/")
                        .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36")
                        .contentType(ContentType.JSON)
                        .when().log().all().patch();

                System.out.println("_________ print me _______ 6____");

                //   System.out.println(propReader.getProp().get("CPU_listURL").toString().trim() + "?status="+Status + "&start_date="+Comman.twomonthDate+"&end_date=" + Comman.CurrentDate);


                // Store the response
                String response= responses.getBody().asString();
                System.out.println("get response : " +response);

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
           System.out.println("Size of guarantors is : "+CPU_Lead_Detail.guarantorSize);

    }}

    }