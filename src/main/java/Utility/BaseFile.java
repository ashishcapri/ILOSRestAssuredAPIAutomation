package Utility;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;

public class BaseFile {
	ExtentReport reportObj = new ExtentReport();
	public static ExtentReports extentBase;
	
	@BeforeSuite
	public ExtentReports callBaseReport() {
		extentBase = ExtentReport.report();
		return extentBase;
	}

	@AfterSuite
	public void flushBaseReport() {
		reportObj.flushReport();;

	}

}
