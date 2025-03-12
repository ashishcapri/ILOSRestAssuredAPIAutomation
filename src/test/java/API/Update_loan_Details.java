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
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

                Comman.generateRandom16DigitNumber();

        System.out.println("_________ print me _______ 4____");



        if (CPU_Lead_Detail.coApplicants != null)
        {
            for (int i = 0; i < CPU_Lead_Detail.coApplicantsSize; i++) {
                JSONObject coApplicant = (JSONObject) CPU_Lead_Detail.coApplicants.get(i);
                String entityType = (String) coApplicant.get("entity_type");
                String borrowertype = (String) coApplicant.get("borrower_type");
                JSONArray    bankAccDetails = (JSONArray) coApplicant.get("bank_acc_details");
                int  bankAccDetailsSize = bankAccDetails.size();

                System.out.println("size of bank account details" + bankAccDetailsSize);


                System.out.println("Co-applicant " + (i + 1) + " Entity Type new: " + entityType);
                System.out.println("Co-applicant " + (i + 1) + " Borrower Type new: " + borrowertype);
                for (int j = 0; j < bankAccDetailsSize; j++) {

                if (entityType.equals("individual") && borrowertype.equals("sep")){

                    System.out.println("Co-applicant " + (i) + " bank acc details: " +(j) + Comman.random16DigitNumber);



                    String patchPayloadCoApp = "{\n" +
                            "    \"applicant\": {\n" +
                            "        \"co_applicant_index\": {\n" +
                            "            \""+i+"\": {\n" +
                            "                \"bank_acc_details_index\": {\n" +
                            "                    \""+j+"\": {\n" +
                            "                        \"bank_name\": \"PUNJAB NATIONAL BANK two\",\n" +
                            "                        \"account_number\": \"" + (1000000000000000L + new Random().nextLong(9000000000000000L))+"\",\n" +

                            "           \"name_of_account_holder\": \"test\",\n" +
                            "                        \"branch_name\": \"PUNE BHOSARI IND. AREA, DISTT.\",\n" +
                            "                        \"account_since\": \"More than 10 years\",\n" +
                            "                        \"account_type\": \"Savings\",\n" +
                            "                        \"ifsc_code\": \"PUNB0291900\"\n" +
                            "                    }\n" +
                            "                },\n" +
                            "                \"organization_info\": {\n" +
                            "                    \"organization_name\": \"TEST\",\n" +
                            "                    \"constitution\": \"individual\",\n" +
                            "                    \"nature_of_business\": \"service\",\n" +
                            "                    \"current_business_experience\": \"5\",\n" +
                            "                    \"overall_business_experience\": \"12\"\n" +
                            "                },\n" +
                            "                \"designation\": \"dovtor\",\n" +
                            "                \"authorised_signatory\": \"Yes\",\n" +
                            "                \"co_applicant_category\": \"doctor\",\n" +
                            "                \"mobile_number\": \"9787875787\",\n" +
                            "                \"entity_type\": \"individual\",\n" +
                            "                \"borrower_type\": \"sep\",\n" +
                            "                \"email\": \"\",\n" +
                            "                \"address_details\": {\n" +
                            "                    \"current_address_same_as\": \"Aadhar\",\n" +
                            "                    \"current_address_vintage_in_months\": 120,\n" +
                            "                    \"permanent_address_same_as\": \"Aadhar\",\n" +
                            "                    \"permanent_address\": {\n" +
                            "                        \"address_line_1\": \"RAM  288 KH WARD NO SO CHAND\",\n" +
                            "                        \"address_line_2\": \"\",\n" +
                            "                        \"landmark\": \"\",\n" +
                            "                        \"taluka\": \"DUNGARGARH\",\n" +
                            "                        \"area\": \"DUNGARGARH\",\n" +
                            "                        \"city\": \"JANJGIR CHAMPA\",\n" +
                            "                        \"district\": \"champa\",\n" +
                            "                        \"state\": \"Chattisgarh\",\n" +
                            "                        \"pincode\": \"495668\"\n" +
                            "                    },\n" +
                            "                    \"current_address\": {\n" +
                            "                        \"address_line_1\": \"RAM  288 KH WARD NO SO CHAND\",\n" +
                            "                        \"address_line_2\": \"\",\n" +
                            "                        \"landmark\": \"\",\n" +
                            "                        \"taluka\": \"talik\",\n" +
                            "                        \"area\": \"Grurgram\",\n" +
                            "                        \"city\": \"JANJGIR CHAMPA\",\n" +
                            "                        \"district\": \"champa\",\n" +
                            "                        \"state\": \"Chattisgarh\",\n" +
                            "                        \"pincode\": \"495668\"\n" +
                            "                    },\n" +
                            "                    \"business_address\": {\n" +
                            "                        \"address_line_1\": \"RAM  288 KH WARD NO SO CHAND\",\n" +
                            "                        \"address_line_2\": \"\",\n" +
                            "                        \"taluka\": \"talik\",\n" +
                            "                        \"area\": \"Grurgram\",\n" +
                            "                        \"city\": \"JANJGIR CHAMPA\",\n" +
                            "                        \"district\": \"champa\",\n" +
                            "                        \"state\": \"Chattisgarh\",\n" +
                            "                        \"pincode\": \"495668\"\n" +
                            "                    }\n" +
                            "                },\n" +
                            "                \"gross_income_per_month\": \"150000\",\n" +
                            "                \"gross_income_yearly\": 1800000,\n" +
                            "                \"additional_info\": {\n" +
                            "                    \"marital_status\": \"single\",\n" +
                            "                    \"mother_name\": \"RANI\",\n" +
                            "                    \"educational_qualification\": \"post graduate or master degree\",\n" +
                            "                    \"spouse_name\": \"\"\n" +
                            "                },\n" +
                            "                \"relationship\": \"daughter\",\n" +
                            "                \"is_financial_co_applicant\": \"Yes\",\n" +
                            "                \"number_of_dependents\": \"2\",\n" +
                            "                \"kyc_ckyc\": {\n" +
                            "                    \"ckyc_num\": null\n" +
                            "                },\n" +
                            "                \"kyc_pan\": {\n" +
                            "                    \"pan_number\": \"BDVPR3698Q\",\n" +
                            "                    \"father_name\": \"LAKSHMINARYANAPPA\"\n" +
                            "                },\n" +
                            "                \"kyc_aadhar\": {\n" +
                            "                    \"gender\": \"male\",\n" +
                            "                    \"aadhaar_number\": \"xxxxxxxx5867\",\n" +
                            "                    \"name\": \"RAJA\",\n" +
                            "                    \"address_line_1\": \"RAM  288 KH WARD NO SO CHAND\",\n" +
                            "                    \"address_line_2\": \"\",\n" +
                            "                    \"landmark\": \"Bank of baroda\",\n" +
                            "                    \"taluka\": \"talik\",\n" +
                            "                    \"area\": \"Grurgram\",\n" +
                            "                    \"city\": \"JANJGIR CHAMPA\",\n" +
                            "                    \"district\": \"champa\",\n" +
                            "                    \"state\": \"Chattisgarh\",\n" +
                            "                    \"pincode\": \"495668\",\n" +
                            "                    \"dob\": \"1992-09-07\"\n" +
                            "                },\n" +
                            "                \"kyc_additional_doc\": [\n" +
                            "                    {\n" +
                            "                        \"url\": \"https://pragati-uat-bucket.s3.ap-south-1.amazonaws.com/uploads/586908d4-9b1e-47f4-9ba5-7600ab430628_rn_image_picker_lib_temp_69fdcda5-a96e-4462-a2c8-86e01b3d6860jpg.jpg\",\n" +
                            "                        \"id_number\": \"NRD3354297\",\n" +
                            "                        \"document_type\": \"Voter ID\",\n" +
                            "                        \"valid_upto\": null\n" +
                            "                    }\n" +
                            "                ]\n" +
                            "            }\n" +
                            "        }\n" +
                            "    }\n" +
                            "}";
                    System.out.println("Start one i " +i+" coapplicant" );
                    Response responses = RestAssured.given()
                            .baseUri(propReader.getProp().get("loandetailupdateURL").toString().trim()+CPU_List.obj_ID).body(patchPayloadCoApp)
                            .header("accept", "application/json, text/plain, */*")
                            .header("authorization",  ILOS_Login.Token)
                            .header("origin", "https://ilos-uat.capriglobal.in")
                            .header("referer", "https://ilos-uat.capriglobal.in/")
                            .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36")
                            .contentType(ContentType.JSON)
                            .when().log().all().patch();



                    System.out.println("PATCH Response: " + responses.getBody().asString());
                    Assert.assertEquals(responses.getStatusCode(), 200, "PATCH request failed!");

                    System.out.println("END one i" +i+" bankAccDetails" );

                }

            }}
        }

        if (CPU_Lead_Detail.guarantors != null) {
            System.out.println("Total guarantors: " + CPU_Lead_Detail.guarantorSize);
            for (int i = 0; i < CPU_Lead_Detail.guarantorSize; i++) {
                JSONObject guarantor = (JSONObject) CPU_Lead_Detail.guarantors.get(i);
                String entityType = (String) guarantor.get("entity_type");

                System.out.println("Guarantor " + (i + 1) + " Entity Type new: " + entityType);
                if (entityType.equals("Organization") || entityType.equals("organization")){

                    String patchPayload = String.format( "{\"applicant\":{\"guarantors_index\":{\"%d\":{\"mobile_number\":\"9599362508\",\"entity_type\":\"organization\",\"kyc_aadhar\":{\"name\":\"test\"},\"address_details\":{\"current_address\":{\"address_line_1\":\"cO Sushil Gyanchand Gadi Plot No\"}},\"relationship\":\"partnership\",\"is_financial_co_applicant\":\"No\",\"kyc_pan\":{\"pan_number\":\"AZQPV9557D\",\"name\":\"test\",\"date_of_incorporation\":\"1998-01-08\"},\"gst_number\":\"\",\"kyc_additional_doc\":[{\"url\":\"https://pragati-uat-bucket.s3.ap-south-1.amazonaws.com/uploads/9bd68e91-7621-41e2-a676-6823072c27ce_1000254107jpg.jpg\",\"id_number\":\"12457\",\"document_type\":\"GST returns\",\"valid_upto\":null}]}}}}",i);


                    System.out.println("Start " +i+" guarantors" );
                    Response responses = RestAssured.given()
                            .baseUri(propReader.getProp().get("loandetailupdateURL").toString().trim()+CPU_List.obj_ID).body(patchPayload)
                            .header("accept", "application/json, text/plain, */*")
                            .header("authorization",  ILOS_Login.Token)
                            .header("origin", "https://ilos-uat.capriglobal.in")
                            .header("referer", "https://ilos-uat.capriglobal.in/")
                            .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36")
                            .contentType(ContentType.JSON)
                            .when().log().all().patch();



                System.out.println("PATCH Response: " + responses.getBody().asString());
                Assert.assertEquals(responses.getStatusCode(), 200, "PATCH request failed!");

                    System.out.println("END " +i+" guarantors" );


                }

                else {

                    System.out.println("Guarantor is invalid" );
                }
            }

        }

        if (CPU_Lead_Detail.portfolio_type.equals("home loan") && CPU_Lead_Detail.borrower_type.equals("sep"))
        {


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

    }

        if (CPU_Lead_Detail.portfolio_type.equals("home loan") && CPU_Lead_Detail.borrower_type.equals("sep"))
        {
            {
                System.out.println("Total bankAccDetails: " + CPU_Lead_Detail.bankAccDetailsSize);
                for (int i = 0; i < CPU_Lead_Detail.bankAccDetailsSize; i++) {


              //      String patchPayload = String.format("{\"activeFormIndex\":0,\"applicant\":{\"primary\":{\"bank_acc_details_index\":{\"%d\":{\"bank_name\":\"STATE BANK OF INDIA\",\"account_number\":\"564534221122\",\"name_of_account_holder\":\"sam\",\"branch_name\":\"PORBANDAR\",\"account_since\":\"Less than 1 year\",\"account_type\":\"Current\",\"ifsc_code\":\"SBIN0000456\"}}},\"organization_info\":{\"organization_name\":\"TRIMURTI two\",\"constitution\":\"individual\",\"nature_of_business\":\"manufacturing\",\"investment_in_business\":\"0\",\"current_business_experience\":\"25\",\"overall_business_experience\":\"25\"},\"address_details\":{\"permanent_address_same_as\":\"Current\",\"permanent_address\":{\"address_line_1\":\"CO SUSHIL GYANCHAND GADI PLOT NO\",\"address_line_2\":\"127 POCKED D SECTOR 4 BAWANA BAWANA\",\"landmark\":\"\",\"taluka\":\"talik\",\"area\":\"North\",\"city\":\"NORTH DELHI\",\"district\":\"Delhi\",\"state\":\"DELHI\",\"pincode\":\"110039\"},\"current_address\":{\"address_line_2\":\"127 POCKED D SECTOR 4 BAWANA BAWANA\",\"landmark\":\"\",\"taluka\":\"talik\",\"area\":\"North\",\"city\":\"NORTH DELHI\",\"district\":\"Delhi\",\"state\":\"DELHI\",\"address_line_1\":\"CO SUSHIL GYANCHAND GADI PLOT NO\",\"pincode\":\"110039\"},\"business_address\":{\"address_line_1\":\"CO SUSHIL GYANCHAND GADI PLOT NO\",\"address_line_2\":\"127 POCKED D SECTOR 4 BAWANA BAWANA\",\"taluka\":\"Talik\",\"area\":\"North\",\"city\":\"NORTH DELHI\",\"district\":\"Delhi\",\"state\":\"DELHI\",\"pincode\":\"110039\"},\"current_address_same_as\":\"Aadhar\",\"current_address_vintage_in_months\":120},\"kyc_ckyc\":{\"ckyc_num\":null},\"kyc_aadhar\":{\"gender\":\"male\",\"dob\":\"1953-05-02\",\"aadhaar_number\":\"xxxxxxxx2110\",\"name\":\"HL PRIME\",\"address_line_1\":\"CO SUSHIL GYANCHAND GADI PLOT NO\",\"address_line_2\":\"127 POCKED D SECTOR 4 BAWANA BAWANA\",\"landmark\":\"\",\"taluka\":\"Talik\",\"area\":\"North\",\"city\":\"NORTH DELHI\",\"district\":\"Delhi\",\"state\":\"DELHI\",\"pincode\":\"110039\"},\"kyc_additional_doc\":[],\"mobile_number\":\"7847585658\",\"borrower_type\":\"sep\",\"email\":\"Sam@gmail.com\",\"kyc_pan\":{\"pan_number\":\"CYMPB5839A\",\"father_name\":\"BORUGULA MUNASWAMY\"},\"additional_info\":{\"mother_name\":\"SUMAN\",\"educational_qualification\":\"post graduate or master degree\",\"spouse_name\":\"\"},\"number_of_dependents\":\"2\"},\"primary\":{\"inquiry_details\":{\"entity_type\":\"individual\",\"profession_type\":\"chartered accountants\",\"income_program\":\"nip\",\"gross_income_per_month\":\"25000\",\"gross_income_yearly\":300000,\"marital_status\":\"married\",\"alternate_phone_number\":\"\",\"bt_loan_monthly_emi\":\"0\",\"applicant_obligation\":\"1200\",\"type_of_financial_institution\":\"\",\"name_of_financial_institution\":\"\"}}}", i);


                      String patchPayload = "{\n" +
                              "    \"activeFormIndex\": 0,\n" +
                              "    \"applicant\": {\n" +
                              "        \"primary\": {\n" +
                              "            \"bank_acc_details_index\": {\n" +
                              "                \"" + i + "\": {\n" +
                              "                    \"bank_name\": \"STATE BANK OF INDIA\",\n" +
                              "                    \"account_number\": \"564534221122\",\n" +
                              "                    \"name_of_account_holder\": \"sam\",\n" +
                              "                    \"branch_name\": \"PORBANDAR\",\n" +
                              "                    \"account_since\": \"Less than 1 year\",\n" +
                              "                    \"account_type\": \"Current\",\n" +
                              "                    \"ifsc_code\": \"SBIN0000456\"\n" +
                              "                }\n" +
                              "            },\n" +
                              "            \"organization_info\": {\n" +
                              "                \"organization_name\": \"TRIMURTI four\",\n" +
                              "                \"constitution\": \"individual\",\n" +
                              "                \"nature_of_business\": \"manufacturing\",\n" +
                              "                \"investment_in_business\": \"0\",\n" +
                              "                \"current_business_experience\": \"25\",\n" +
                              "                \"overall_business_experience\": \"25\"\n" +
                              "            },\n" +
                              "            \"address_details\": {\n" +
                              "                \"permanent_address_same_as\": \"Current\",\n" +
                              "                \"permanent_address\": {\n" +
                              "                    \"address_line_1\": \"CO SUSHIL GYANCHAND GADI PLOT NO\",\n" +
                              "                    \"address_line_2\": \"127 POCKED D SECTOR 4 BAWANA BAWANA\",\n" +
                              "                    \"landmark\": \"\",\n" +
                              "                    \"taluka\": \"talik\",\n" +
                              "                    \"area\": \"North\",\n" +
                              "                    \"city\": \"NORTH DELHI\",\n" +
                              "                    \"district\": \"Delhi\",\n" +
                              "                    \"state\": \"DELHI\",\n" +
                              "                    \"pincode\": \"110039\"\n" +
                              "                },\n" +
                              "                \"current_address\": {\n" +
                              "                    \"address_line_2\": \"127 POCKED D SECTOR 4 BAWANA BAWANA\",\n" +
                              "                    \"landmark\": \"\",\n" +
                              "                    \"taluka\": \"talik\",\n" +
                              "                    \"area\": \"North\",\n" +
                              "                    \"city\": \"NORTH DELHI\",\n" +
                              "                    \"district\": \"Delhi\",\n" +
                              "                    \"state\": \"DELHI\",\n" +
                              "                    \"address_line_1\": \"CO SUSHIL GYANCHAND GADI PLOT NO\",\n" +
                              "                    \"pincode\": \"110039\"\n" +
                              "                },\n" +
                              "                \"business_address\": {\n" +
                              "                    \"address_line_1\": \"CO SUSHIL GYANCHAND GADI PLOT NO\",\n" +
                              "                    \"address_line_2\": \"127 POCKED D SECTOR 4 BAWANA BAWANA\",\n" +
                              "                    \"taluka\": \"Talik\",\n" +
                              "                    \"area\": \"North\",\n" +
                              "                    \"city\": \"NORTH DELHI\",\n" +
                              "                    \"district\": \"Delhi\",\n" +
                              "                    \"state\": \"DELHI\",\n" +
                              "                    \"pincode\": \"110039\"\n" +
                              "                },\n" +
                              "                \"current_address_same_as\": \"Aadhar\",\n" +
                              "                \"current_address_vintage_in_months\": 120\n" +
                              "            },\n" +
                              "            \"kyc_ckyc\": {\n" +
                              "                \"ckyc_num\": null\n" +
                              "            },\n" +
                              "            \"kyc_aadhar\": {\n" +
                              "                \"gender\": \"male\",\n" +
                              "                \"dob\": \"1953-05-02\",\n" +
                              "                \"aadhaar_number\": \"xxxxxxxx2110\",\n" +
                              "                \"name\": \"HL PRIME\",\n" +
                              "                \"address_line_1\": \"CO SUSHIL GYANCHAND GADI PLOT NO\",\n" +
                              "                \"address_line_2\": \"127 POCKED D SECTOR 4 BAWANA BAWANA\",\n" +
                              "                \"landmark\": \"\",\n" +
                              "                \"taluka\": \"Talik\",\n" +
                              "                \"area\": \"North\",\n" +
                              "                \"city\": \"NORTH DELHI\",\n" +
                              "                \"district\": \"Delhi\",\n" +
                              "                \"state\": \"DELHI\",\n" +
                              "                \"pincode\": \"110039\"\n" +
                              "            },\n" +
                              "            \"kyc_additional_doc\": [],\n" +
                              "            \"mobile_number\": \"7847585658\",\n" +
                              "            \"borrower_type\": \"sep\",\n" +
                              "            \"email\": \"Sam@gmail.com\",\n" +
                              "            \"kyc_pan\": {\n" +
                              "                \"pan_number\": \"CYMPB5839A\",\n" +
                              "                \"father_name\": \"BORUGULA MUNASWAMY\"\n" +
                              "            },\n" +
                              "            \"additional_info\": {\n" +
                              "                \"mother_name\": \"SUMAN\",\n" +
                              "                \"educational_qualification\": \"post graduate or master degree\",\n" +
                              "                \"spouse_name\": \"\"\n" +
                              "            },\n" +
                              "            \"number_of_dependents\": \"2\"\n" +
                              "        }\n" +
                              "    },\n" +
                              "    \"primary\": {\n" +
                              "        \"inquiry_details\": {\n" +
                              "            \"entity_type\": \"individual\",\n" +
                              "            \"profession_type\": \"chartered accountants\",\n" +
                              "            \"income_program\": \"nip\",\n" +
                              "            \"gross_income_per_month\": \"25000\",\n" +
                              "            \"gross_income_yearly\": 300000,\n" +
                              "            \"marital_status\": \"married\",\n" +
                              "            \"alternate_phone_number\": \"\",\n" +
                              "            \"bt_loan_monthly_emi\": \"0\",\n" +
                              "            \"applicant_obligation\": \"1200\",\n" +
                              "            \"type_of_financial_institution\": \"\",\n" +
                              "            \"name_of_financial_institution\": \"\"\n" +
                              "        }\n" +
                              "    }\n" +
                              "}";
                    System.out.println("Start i" +i+" bankAccDetails" );
                    Response responses = RestAssured.given()
                            .baseUri(propReader.getProp().get("loandetailupdateURL").toString().trim()+CPU_List.obj_ID).body(patchPayload)
                            .header("accept", "application/json, text/plain, */*")
                            .header("authorization",  ILOS_Login.Token)
                            .header("origin", "https://ilos-uat.capriglobal.in")
                            .header("referer", "https://ilos-uat.capriglobal.in/")
                            .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36")
                            .contentType(ContentType.JSON)
                            .when().log().all().patch();



                    System.out.println("PATCH Response: " + responses.getBody().asString());
                    Assert.assertEquals(responses.getStatusCode(), 200, "PATCH request failed!");

                    System.out.println("END i" +i+" bankAccDetails" );


                }

            }

        }


        if (CPU_Lead_Detail.propertyDetails != null && CPU_Lead_Detail.portfolio_type.equals("home loan") && CPU_Lead_Detail.borrower_type.equals("sep")){

            for (int i = 0; i < CPU_Lead_Detail.propertyDetailsSize; i++) {

                String patchPayloadProperty = "{\n" +
                        "    \"primary\": {\n" +
                        "        \"inquiry_details\": {\n" +
                        "            \"transaction_type\": \"new purchase\"\n" +
                        "        }\n" +
                        "    },\n" +
                        "    \"applicant\": {\n" +
                        "        \"primary\": {\n" +
                        "            \"property_details_index\": {\n" +
                        "                \"" + i + "\": {\n" +
                        "                    \"property_type\": \"non diverted\",\n" +
                        "                    \"property_ownership\": {\n" +
                        "                        \"property_owners\": [\n" +
                        "                            {\n" +
                        "                                \"owner_id\": 925657,\n" +
                        "                                \"owner_name\": \"HL PRIME\",\n" +
                        "                                \"owner_type\": \"Primary Applicant\"\n" +
                        "                            }\n" +
                        "                        ],\n" +
                        "                        \"type\": \"NO\"\n" +
                        "                    },\n" +
                        "                    \"tentative_property_value\": \"400002\",\n" +
                        "                    \"refinance_transaction\": \"NO\",\n" +
                        "                    \"is_it_seller_bt\": \"NO\",\n" +
                        "                    \"property_address\": {\n" +
                        "                        \"address_line_1\": \" new CO SUSHIL GYANCHAND GADI PLOT NO\",\n" +
                        "                        \"address_line_2\": \"127 POCKED D SECTOR 4 BAWANA BAWANA\",\n" +
                        "                        \"taluka\": \"Tahsil\",\n" +
                        "                        \"area\": \"North\",\n" +
                        "                        \"city\": \"NORTH DELHI\",\n" +
                        "                        \"district\": \"Delhi\",\n" +
                        "                        \"state\": \"DELHI\",\n" +
                        "                        \"pincode\": \"110039\"\n" +
                        "                    },\n" +
                        "                    \"occupancy\": \"vacant\",\n" +
                        "                    \"area_in_sqft\": \"230\"\n" +
                        "                }\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}";

                System.out.println("Start i" +i+" bankAccDetails" );
                Response responses = RestAssured.given()
                        .baseUri(propReader.getProp().get("loandetailupdateURL").toString().trim()+CPU_List.obj_ID).body(patchPayloadProperty)
                        .header("accept", "application/json, text/plain, */*")
                        .header("authorization",  ILOS_Login.Token)
                        .header("origin", "https://ilos-uat.capriglobal.in")
                        .header("referer", "https://ilos-uat.capriglobal.in/")
                        .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36")
                        .contentType(ContentType.JSON)
                        .when().log().all().patch();



                System.out.println("PATCH Response: " + responses.getBody().asString());
                Assert.assertEquals(responses.getStatusCode(), 200, "PATCH request failed!");

                System.out.println("END i" +i+" bankAccDetails" );


            }

            for (int k = 0; k < CPU_Lead_Detail.referenceDetailsSize; k++) {

                String patchPayloadReferences = "{\n" +
                        "    \"applicant\": {\n" +
                        "        \"primary\": {\n" +
                        "            \"reference_index\": {\n" +
                        "                \""+k+"\": {\n" +
                        "                    \"full_name\": \"sam\",\n" +
                        "                    \"mobile\": \"7867890090\",\n" +
                        "                    \"relationship_with_the_applicant\": \"son\",\n" +
                        "                    \"knows_since\": \"2 Years 2 Months\",\n" +
                        "                    \"occupation\": \"qa one\"\n" +
                        "                },\n" +
                        "                \""+k+"\": {\n" +
                        "                    \"full_name\": \"sam\",\n" +
                        "                    \"mobile\": \"7867890090\",\n" +
                        "                    \"relationship_with_the_applicant\": \"son\",\n" +
                        "                    \"knows_since\": \"3 Years 2 Months\",\n" +
                        "                    \"occupation\": \"qa one\"\n" +
                        "                }\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}";

                System.out.println("Start k " +k+" referencesDetails" );
                Response responses = RestAssured.given()
                        .baseUri(propReader.getProp().get("loandetailupdateURL").toString().trim()+CPU_List.obj_ID).body(patchPayloadReferences)
                        .header("accept", "application/json, text/plain, */*")
                        .header("authorization",  ILOS_Login.Token)
                        .header("origin", "https://ilos-uat.capriglobal.in")
                        .header("referer", "https://ilos-uat.capriglobal.in/")
                        .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36")
                        .contentType(ContentType.JSON)
                        .when().log().all().patch();



                System.out.println("PATCH Response: " + responses.getBody().asString());
                Assert.assertEquals(responses.getStatusCode(), 200, "PATCH request failed!");

                System.out.println("END k " +k+" references" );


            }

        }

        String DmsUatPayload = "{\n" +
                "    \"application_id\": " +
                "\"" + CPU_List.app_ID + "\"" +
                ",\n" +
                "    \"is_new_application\": true\n" +
                "}";





                Response responses = RestAssured.given()
                .baseUri(propReader.getProp().get("DmsUat").toString().trim()).body(DmsUatPayload)
                .header("accept", "application/json, text/plain, */*")
                .header("authorization",  ILOS_Login.Token)
                .header("origin", "https://ilos-uat.capriglobal.in")
                .header("referer", "https://ilos-uat.capriglobal.in/")
                .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36")
                .contentType(ContentType.JSON)
                .when().log().all().post();

        System.out.println("Application ID: " + CPU_List.app_ID);

    }

    }