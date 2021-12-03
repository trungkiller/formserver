package com.example.FormServer.selenium;
//package com.selenium;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.FormServer.controller.GetDataFromGoogleController;

@RestController
public class GetDataFromGoogle {

     @Autowired
     private GetDataFromGoogleController getDataFromGoogleController;

 
   

     public String name;
     String url=null;
     private  static Boolean isFound = false;
     Pattern patternPhone = Pattern.compile("(\\+[0-9].*)");

     String currentDirectory = System.getProperty("user.dir");
     public void StartSpec(String name) throws InterruptedException {
         this.name = name;
         WebDriver driverGlobal = openwebGooglemap("https://www.google.com/maps/@21.0476192,105.7938183,15z");
         ExecuteFindString(name,driverGlobal);

     }

     public void ExecuteFindString(String name,WebDriver driver) throws InterruptedException {
         try {
             WebElement input = driver.findElement(By.xpath("//input[@class='tactile-searchbox-input']"));
             //name ="nail salon near London, UK"; // for test
             input.sendKeys(name);
             input.sendKeys(Keys.ENTER);
             while (!apply(driver)) {

             }
             Thread.sleep(7000);
         }catch(Exception ex)
         {

         }
         int i = 1;
         Boolean IsOne=false;
         Boolean Ismulti= false;
         int indexcheck =1;
         while(true) {
             try{
                 if(!IsOne) {
                     List<WebElement> ListResult = driver.findElements(By.xpath(".//a[@aria-label]"));

                     if (i == ListResult.stream().count()) {
                         driver.quit();
                         Thread.sleep(1000);
                         break;
                     }
                     for(;indexcheck<ListResult.stream().count();indexcheck++)
                     {   String jaction = ListResult.get(i).getAttribute("jsaction");
                         if (jaction.contains(";"))
                         {
                             if (ListResult.stream().count() != 0) {
                                 String hrefc = ListResult.get(i).getAttribute("href");
                                 if (hrefc.contains("https")) {
                                     Ismulti = true;
                                 }
                             }
                         }
                     }
                     Thread.sleep(3000);
                     String jaction = ListResult.get(i).getAttribute("jsaction");
                     if (jaction.contains(";")) {
                         if (ListResult.stream().count() != 0) {
                             String href = ListResult.get(i).getAttribute("href");
                             if (href.contains("https")) {
                                 ListResult.get(i).click();
                             }
                             while (!apply(driver)) {

                             }
                             Thread.sleep(10000);
                         }
                     }
                 }
             }catch (Exception ex)
             {

             }
             try {
                 while (!apply(driver)) {

                 }
                 List<WebElement> ListDetail = driver.findElements(By.xpath("//div[@class='AeaXub']//div[@class='QSFF4-text gm2-body-2']"));
                 Thread.sleep(10000);

                 JSONObject Jsonsss =  getdata(ListDetail,driver);

                 System.out.println(Jsonsss.toJSONString());
                 getDataFromGoogleController.addDataToDB(Jsonsss);
                 //getlog(Jsonsss.toJSONString());
                 //System.console().printf("data {0}",Jsonsss.toString());
                 if(!Ismulti)
                 {
                     driver.close();
                     driver.quit();
                     break;
                 }
             }catch(Exception ex)
             {

             }
             try {
                 WebElement Backbutton = driver.findElement(By.xpath("//img[@class='xoLGzf-icon']"));
                 Thread.sleep(2000);
                 String SrcImage = Backbutton.getAttribute("src");
                 if(SrcImage.equals("https://www.gstatic.com/images/icons/material/system_gm/1x/arrow_back_black_24dp.png"))
                 {
                     IsOne=false;
                     Backbutton.click();
                     Thread.sleep(1000);
                 }
             }catch(Exception ex)
             {

             }
             i++;

         }


     }
     
     public String getClipBoard(){
    	    try {
    	        return (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
    	    } catch (HeadlessException e) {
    	        // TODO Auto-generated catch block
    	        return e.toString();          
    	    } catch (UnsupportedFlavorException e) {
    	        // TODO Auto-generated catch block
    	        e.printStackTrace();            
    	    } catch (IOException e) {
    	        // TODO Auto-generated catch block
    	        e.printStackTrace();
    	    }
    	    return "";
    	}

     @SuppressWarnings("unchecked")
     public JSONObject getdata(List<WebElement> ListDetail, WebDriver driver)
     {
         try {
             int index = 0;
             int counterLink=1;
             while (!apply(driver)) {

             }
             Thread.sleep(1000);
             WebElement NameStore = driver.findElement(By.xpath("//h1[@class='x3AX1-LfntMc-header-title-title gm2-headline-5']"));
             WebElement Copybutton = driver.findElement(By.xpath("//img[@alt='Copy website']"));
             Thread.sleep(1000);
             Thread.sleep(1000);
             Copybutton.click();
             Thread.sleep(1000);
             
             String linkinstagram = getClipBoard();
            /* ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
             driver.switchTo().window(tabs2.get(1));
             Thread.sleep(1000);
             String linkinstagram = driver.getCurrentUrl();
             Thread.sleep(1000);
             driver.close();
             Thread.sleep(1000);
             driver.switchTo().window(tabs2.get(0));
             */
             Thread.sleep(1000);
             JSONObject jsonobject = new JSONObject();
             String UrlStoreLinkonMap = driver.getCurrentUrl();
             jsonobject.put("NameStore",NameStore.getText());
             jsonobject.put("StoreLinkonMap",UrlStoreLinkonMap);
             boolean checkcovid=false;
             //instagram or link website of store
             jsonobject.put("linkCopy",linkinstagram);
             while (index != ListDetail.stream().count()) {
                 Matcher matcher = patternPhone.matcher(ListDetail.get(index).getText().toString());
                 if(matcher.matches()) {
                     jsonobject.put("phone", ListDetail.get(index).getText());
                 }
                 else {
                     if(index == 0 ) {
                         checkcovid = ListDetail.get(index).getText().contains("COVID-19");
                     }
                     if(index == 0)
                         if(!checkcovid) {
                             jsonobject.put("Address", ListDetail.get(index).getText());
                         }else
                         {
                             jsonobject.put("Address", ListDetail.get(1).getText());
                         }
                     else {
                         if(ListDetail.get(index).getText().contains(".")) {
                             jsonobject.put("Link" +String.valueOf(counterLink), ListDetail.get(index).getText());
                             counterLink++;
                         }
                     }

                 }
                 index++;
             }
             return jsonobject;
         }catch(Exception ex)
         {
             return null;
         }

     }
     void getlog(String jsons) throws IOException {

         System.out.println("Current absolute path is: " + currentDirectory);
         File file = new File(currentDirectory+"\\myfile.txt");

         /* This logic will make sure that the file
          * gets created if it is not present at the
          * specified location*/
         if (!file.exists()) {
             file.createNewFile();
         }

         FileWriter fw = new FileWriter(file,true);
         BufferedWriter bw = new BufferedWriter(fw);
         bw.write(jsons);
         bw.newLine();
         bw.close();

     }
     public WebDriver openwebGooglemap(String urls)
     {
         try {

             ChromeOptions chromeOptions = new ChromeOptions();
             System.setProperty("webdriver.chrome.driver", "F:\\Download\\chromedriver_win32\\chromedriver.exe");

//             chromeOptions.addArguments("--no-sandbox");
//               chromeOptions.addArguments("--headless");

             WebDriver webDriver = new ChromeDriver(chromeOptions);
             webDriver.manage().window().maximize();
             webDriver.get(urls);
             url=urls;

             while(!apply(webDriver))
             {

             }
             return webDriver;
         }catch(Exception ex){
             System.console().printf("init driver error: {0}",ex.toString());
             return null;
         }

     }


     public boolean apply(WebDriver driver) {
         try {
             if(((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"))
             {
                 return true;
             }
             else
             {
                 return false;
             }
         }catch(Exception ex)
         {
             return false;
         }
     }
}
