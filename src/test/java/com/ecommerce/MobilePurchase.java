package com.ecommerce;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.beust.jcommander.Parameter;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.WebDriverManagerException;

public class MobilePurchase {
	
	@DataProvider(name="mobile")
	 public Object [][] MobileName() {
		Object [][] o=new Object[][] {{"realme"}};
       return o;
	}
	static  WebDriver driver;
	  @Parameters("URL")
	   @BeforeClass
		public static void OpenBrowser(String url) {
			WebDriverManager.chromedriver().setup();
	        driver= new ChromeDriver();
	        driver.get(url);
	        driver.manage().window().maximize();
	        		}
	   @AfterClass
		public static void CloseBrowser() {
		   driver.quit();
		 
		}
	   static  long starttime;
	   @BeforeMethod
	    public  void BeforeTest() {
		long starttime =System.currentTimeMillis();
	    }
	   @AfterMethod
	    public  void AfterTest() {
	       long endstime= System.currentTimeMillis();
	       System.out.println(endstime-starttime);
	   }
	   @Parameters({"UserName","Password"})
	   @Test(priority = 1)
	   public  void login(String user,String pass) {
		   System.out.println(user);
		   System.out.println(pass);
		   driver.findElement(By.xpath("//button[text()='✕']")).click();
	}
	   @Test(priority = 2,dataProvider ="mobile")
	   public  void search(String name) {
		   driver.findElement(By.name("q")).sendKeys(name);
		      driver.findElement(By.xpath("//button[@class='L0Z3Pu']")).click();

	} 
	  static XSSFSheet sheet;
	   @Test(priority = 3)
	   public  void choose () throws Exception{
		   driver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);
	   WebElement name=driver.findElement(By.xpath("(//div[@class='_4rR01T'])[1]"));
	   String Mname=name.getText();
	   name.click();
	   File f= new File("C:\\Users\\jeyapandi\\eclipse-workspace\\Flipkart-TestNG\\src\\test\\resources\\Files\\Book1.xlsx");
	   FileInputStream f1= new FileInputStream(f);
	   XSSFWorkbook book= new XSSFWorkbook(f1);
	   XSSFSheet sheet=book.getSheet("Mobile");
	   sheet.getRow(0).createCell(1).setCellValue(Mname);
		FileOutputStream w= new FileOutputStream(f);
		book.write(w);
	    w.close();
	   book.close();
	}
	   @Test(priority = 4)
	   public void window() throws Exception {
		Set<String> Allwin=  driver.getWindowHandles();
		List<String> win= new ArrayList<String>();
		win.addAll(Allwin);
		String Win =win.get(1);
		driver.switchTo().window(Win);
	    WebElement FinalMname=	driver.findElement(By.xpath("//span[@class='B_NuCI']"));
	   String mobileName= FinalMname.getText();
	   File f= new File("C:\\Users\\jeyapandi\\eclipse-workspace\\Flipkart-TestNG\\src\\test\\resources\\Files\\Book1.xlsx");
	   FileInputStream f1= new FileInputStream(f);
	   XSSFWorkbook book= new XSSFWorkbook(f1);
	   XSSFSheet sheet=book.getSheet("Mobile");
	  String data=sheet.getRow(0).getCell(1).getStringCellValue();
	   book.close();
	   SoftAssert s= new SoftAssert();
	    s.assertEquals(data,mobileName);
	}
	   @Test(priority = 5)
	   public  void screenshot() throws IOException {
		   TakesScreenshot ts=(TakesScreenshot)driver;
	   File source=   ts.getScreenshotAs(OutputType.FILE);
	   File target= new File("C:\\Users\\jeyapandi\\eclipse-workspace\\Flipkart-TestNG\\Screenshots\\realme.png");
	   FileUtils.copyFile(source,target);
	  WebElement down= driver.findElement(By.xpath("//div[@class='_3a9CI2']"));
	   JavascriptExecutor js=(JavascriptExecutor)driver;
	    js.executeScript("arguments[0].scrollIntoView(true)",down );
	    File source1=   ts.getScreenshotAs(OutputType.FILE);
	    File target1 = new File("C:\\Users\\jeyapandi\\eclipse-workspace\\Flipkart-TestNG\\Screenshots\\highlights.png");
	   FileUtils.copyFile(source1, target1);
	}}