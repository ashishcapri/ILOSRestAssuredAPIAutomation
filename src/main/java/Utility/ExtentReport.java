package Utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReport {

	public static ExtentReports extent;
	public static ExtentSparkReporter spark;

	public static ExtentReports report() {
		extent = new ExtentReports();
		spark = new ExtentSparkReporter("extent-report.html");
		extent.attachReporter(spark);
		return extent;
	}

	public void flushReport() {
		extent.flush();
	}
}
