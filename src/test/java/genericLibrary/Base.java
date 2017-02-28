package genericLibrary;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Base {
	
	public WebDriver driver;
	public static ExtentReports eReports;
	public ExtentTest startTest;
	
	
	@BeforeSuite
	public void initReports(){
		eReports = new ExtentReports("C:\\FebReports\\proj_" + getDatetimestamp() + ".html",false);
		
	}
	
	@BeforeMethod
	public void launchApp() throws Exception{
		driver = new FirefoxDriver();
		driver.get(getPropertyVal(getPropertyVal("env")));
		driver.manage().window().maximize();	
	}
	
	@AfterMethod
	public void tearDown(){		
		eReports.endTest(startTest);
		eReports.flush();
		driver.quit();
	}
	
	
//	read property file
	public String getPropertyVal(String key) throws Exception{
		
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\GlobalSettings.properties");	
		Properties prop = new Properties();
		prop.load(fis);
		return prop.getProperty(key);
		
	}
	
//	get the screen shot
	public String getScreen() throws IOException{
		
		TakesScreenshot screen=(TakesScreenshot)driver;
		File screenshotAs = screen.getScreenshotAs(OutputType.FILE);
		String path="C:\\FebReports\\snapshot_" + getDatetimestamp() + ".png";
		FileUtils.copyFile(screenshotAs, new File(path));
		return path;
	}
	
//	get Element Screenshot
	public String getElementScreen(WebElement ele) throws Exception{

		// Get entire page screenshot
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		BufferedImage  fullImg = ImageIO.read(screenshot);

		// Get the location of element on the page
		Point point = ele.getLocation();

		// Get width and height of the element
		int eleWidth = ele.getSize().getWidth();
		int eleHeight = ele.getSize().getHeight();

		// Crop the entire page screenshot to get only element screenshot
		BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(),
		    eleWidth, eleHeight);
		ImageIO.write(eleScreenshot, "png", screenshot);
		String path="C:\\FebReports\\snapshot_" + getDatetimestamp() + ".png";
		// Copy the element screenshot to disk
		FileUtils.copyFile(screenshot, new File(path));
		
		return path;
		
		
		
	}
//	get date time stamp 
	public   String getDatetimestamp(){
		Date date = new Date();
		SimpleDateFormat specialformat = new SimpleDateFormat("dd-MMM-yyyy hh-mm-ss");
		String customdate = specialformat.format(date);
		return customdate;		
		
	}
	
	
//	------Common Validation----------------
	
	public void cv_equals(String act,String exp,String Stepname,WebElement ele) throws Exception{
		
		if(act.equals(exp)){			

			startTest.log(LogStatus.PASS, Stepname,"Passed the step: " + Stepname  + startTest.addScreenCapture(getElementScreen(ele)));
		}else{
//			
			startTest.log(LogStatus.FAIL, Stepname,"Failed the step: " + Stepname + " as the Actual was " + act + " and the expected was" + exp + startTest.addScreenCapture(getElementScreen(ele)));
//			
		}
		
	}
	
	
public void cv_equals(String act,String exp,String Stepname) throws Exception{
		
		if(act.equals(exp)){			

			startTest.log(LogStatus.PASS, Stepname,"Passed the step: " + Stepname  + startTest.addScreenCapture(getScreen()));
		}else{
//			
			startTest.log(LogStatus.FAIL, Stepname,"Failed the step: " + Stepname + " as the Actual was " + act + " and the expected was" + exp + startTest.addScreenCapture(getScreen()));
//			
		}
		
	}
	
	
	
	
	

}
