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

import java.util.*;

public class technicalComp extends BaseFile {

    ExtentReports extent;
    public static String token=ILOS_Login.Token;


    PropertyFile propReader = new PropertyFile();


    @BeforeClass
    public void callExtentReportFile() {
        extent = extentBase;
    }



    @Test(priority = 0)
    public void tenicalAssignlead() {
        System.out.println("Response for token : " + ILOS_Login.Token);

    }

    }