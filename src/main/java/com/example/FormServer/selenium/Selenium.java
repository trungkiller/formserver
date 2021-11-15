package com.example.FormServer.selenium;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.FormServer.controller.FormController;

@RestController
public class Selenium {
	@Autowired
	private FormController formController;
	
	int idForm;
	
//	public Selenium(int idForm) {
//		this.idForm = idForm;
//	}
	
	public Selenium() {
		
	}
	
	public  static ChromeOptions options;
    public  static ChromeDriver driver = null;
    private  String NameStore=null;
    private  String Adress=null;
    private  String LinkUrlStore=null;
    String url=null;
    private Boolean isDone=true;
    private  Boolean isStop=false;

    public void StartLoop(String urlStart, int idForm)  {
    	this.idForm = idForm;
        Selenium webtarget= new Selenium();
        url=urlStart;
        webtarget.openweb(url);
        int isOK = 0;
        isStop = false;
        do {
            LoadPagenum(url);
            isOK = NextPage();
        }while(isOK==1&&isOK!=9999);
    }

    public void StartLoopUntil(String urlStart,String urlEnd, int idForm)  {
    	this.idForm = idForm;
        Selenium webtarget= new Selenium();
        url=urlStart;
        webtarget.openweb(url);
        int isOK = 0;
        isStop=false;

        do {

            LoadPagenum(url);
            if(url.equals(urlEnd))
            {
                isStop=true;
            }
        }while((isOK=NextPage())==1);
    }
    public void StartSpec(String UrlPage, int idForm) {
    	this.idForm = idForm;
        isStop = false;
        Selenium webtarget= new Selenium();
        webtarget.openweb(UrlPage);
        LoadPagenum(UrlPage);

    }
    public void disablePopup()
    {
        try {
            options = new ChromeOptions();
            options.addArguments("--disable-notifications");
        }catch(Exception ex) {

        }

    }
    public ChromeDriver openweb(String urls)
    {
        try {
            disablePopup();
            System.setProperty("webdriver.chrome.driver","F:\\Download\\chromedriver_win32\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            driver = new ChromeDriver(options);
            driver.get(urls);
            while(!apply())
            {

            }
            WebElement buttonAccept = driver.findElement(By.xpath("//button[@id='onetrust-accept-btn-handler']"));
            buttonAccept.click();
            while(!apply())
            {

            }
            return driver;
        }catch(Exception ex){
            return null;
        }
    }
    public boolean apply() {
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
    public int NextPage()
    {
        try {
             List<WebElement> e = loadElements(".//*[@class='Pagination-module--item--b6f01b Pagination-module--next--740edf']");
             if (e.stream().count() == 1) {
                 if (isDone) {
                     if(!isStop) {
                         e.get(0).click();
                         while (!apply()) {
                         }
                         Thread.sleep(5000);
                         url = driver.getCurrentUrl();
                     }
                     else
                     {
                         Thread.sleep(5000);
                         while (!apply()) {
                         }
                         url = driver.getCurrentUrl();
                         return 9999;
                     }
                 }
             }else {
              return 9999;
             }
         return (int) e.stream().count();
         }catch(Exception ex){
            isDone=false;
            return 9999;
         }
    }
    public void LoadPagenum(String urlPage) {
        try {
            driver.get(urlPage);
            while (!apply()) {

            }
            ExcuteGetData(urlPage, ".//*[@class='Results-module--results--e3b89d']//h2[@class='Text-module_mdHeader__2D1lu']");
        }catch(Exception ex)
        {
            //todo something
        }
    }
    public List<WebElement> loadElements(String strclass)
    {
        try {
            List<WebElement> e = driver.findElements(By.xpath(strclass));
            return e;
        }catch(Exception ex)
        {
            return null;
        }
    }
    
    public void StopSelenium() {
    	driver.quit();
    }

    @SuppressWarnings("unchecked")
	public void ExcuteGetData(String Page,String strclass) {
       try {
           int i = 0;
           isDone = false;
           while (true) {
               List<WebElement> e = loadElements(strclass);
               NameStore = e.get(i).getText();
               e.get(i).click();
               while (!apply()) {

               }
               Thread.sleep(2000);
               LinkUrlStore = driver.getCurrentUrl();
               WebElement adress = driver.findElement(By.xpath("//span[@class='Text-module_smHeader__3mR_U']"));
               Adress = adress.getText();
               JSONObject jsonobject = new JSONObject();
               jsonobject.put("Business Name", NameStore);
               jsonobject.put("Full Address", Adress);
               jsonobject.put("Website/URL", LinkUrlStore);
//               FormController formController = new FormController();
               formController.writeDataToDB(jsonobject, idForm);
               driver.get(Page);
               Thread.sleep(2000);
               while (!apply()) {
               }
               i++;
               if (i == e.stream().count()) {
                   isDone = true;
                   break;
               }
           }
       }catch(Exception ex)
       {
           //Todo Somthing
       }
    }

    public void savefileJson(String name,String adress, String urlStore)
    {
        try(FileWriter fw = new FileWriter("myfile.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            adress =   adress.replace("\'","|");
            name = name.replace("\'","|");
            urlStore = urlStore.replace("\'","|");
            String jsonString = "{'name':'"+name+ "','adress':'"+adress+"','urlStore':'"+urlStore+"'"+"}";
            JSONParser parser = new JSONParser();
    		JSONObject json = (JSONObject) parser.parse(jsonString);
            out.println(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

