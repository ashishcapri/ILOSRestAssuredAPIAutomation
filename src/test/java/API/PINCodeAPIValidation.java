package API;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class PINCodeAPIValidation extends BaseFile {

	private static final String TESTCASES_SHEET = "pinCodeFile";
	private static final String SHEET_NAME = "pinCodeSheet";
	ExtentReports extent;

	PropertyFile propReader = new PropertyFile();
	int rownum;
	Map<String, Object> cache = ReadMetaData.getMetdataCache();

	/**
	 * This method is used to read from excel.
	 */
	Map<String, Object>[][] empdata;

	@DataProvider(name = "pinCode")
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

	@Test(dataProvider = "pinCode")
	private void pinCodeAPI(Map<String, Object> testData) {

		String cacheData = propReader.getProp().get(SHEET_NAME).toString().trim();
		Map<String, Object> map = (Map<String, Object>) cache.get(cacheData);
		System.out.println(map);
		List<String> outputParameters = (List<String>) map.get("outputKey");

		if (testData.entrySet() != null) {
			for (Map.Entry<String, Object> entry : testData.entrySet()) {
				JSONObject apirequest = (JSONObject) entry.getValue();
				String cityRequestData = new String((String) apirequest.get("city"));
				String stateRequestData = new String((String) apirequest.get("state"));
				String httpStatusRequest = new String((String) apirequest.get("httpStatus"));
				int statusRequest = Integer.parseInt(httpStatusRequest);
				String testScenarioRequest = new String((String) apirequest.get("testScenarios"));

				Reporter.log(testScenarioRequest);

				
				System.out.println("++++++++++++++++++"+apirequest);
				for (String output : outputParameters)
					apirequest.remove(output);

				
				System.out.println("------------------"+apirequest);

				System.out.println(propReader.getProp().get("PINCodeBaseURL").toString().trim());
				System.out.println(apirequest);

				Response responses = given().header("Content-Type", "application/json").body((JSONObject) entry.getValue())
						.when().post(propReader.getProp().get("PINCodeBaseURL").toString().trim());

				String response = responses.getBody().asString();
				long ResponseTime = responses.getTime();
				if(ResponseTime>200){
					testScenarioRequest=testScenarioRequest + "<html><br><span style='color:red;'>Response Time: " + ResponseTime + "ms</span></html>" ;
				}
				else {
					testScenarioRequest = testScenarioRequest + "<html><br><span style='color:green;'>Response Time: " + ResponseTime + "ms</span></html>";
				}

				Object JSON = null;
				try {
					JSON = new JSONParser().parse((String) response.toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JSONObject apiResponse;

				apiResponse = (JSONObject) JSON;

				System.out.println(apiResponse);

				JSONObject apiResponsedata = null;
				if (apiResponse.get("data") != null) {
					apiResponsedata = (JSONObject) apiResponse.get("data");

					System.out.println(apiResponsedata);
					String apiResponseState = (String) apiResponsedata.get("state");
					String apiResponseCity = (String) apiResponsedata.get("city");

					if (apiResponseState.equals(stateRequestData) && (apiResponseCity.equals(cityRequestData))) {
						extent.createTest(testScenarioRequest).log(Status.PASS,
								"API Request:  " + apirequest + "     API Response:  " + apiResponse);
					}
				}
			}
		}
	}

}
