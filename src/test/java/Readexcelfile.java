import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Readexcelfile {
    public static void main(String[] args) throws IOException {
        FileInputStream fi=new FileInputStream(System.getProperty("user.dir") + "\\Testcase_login.xlsx");
        XSSFWorkbook wb=new XSSFWorkbook(fi);

        XSSFSheet sh=wb.getSheetAt(0);
        int rw=sh.getLastRowNum();
        //System.out.println("the total row is : " +rw);
        String Testcase = sh.getRow(1).getCell(0).getStringCellValue();
        System.out.println(Testcase);
        if (Testcase.equals("tc_001")) {
            String username = sh.getRow(1).getCell(2).getStringCellValue();
            String password = sh.getRow(1).getCell(3).toString();

            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver();

            //launching the url
            driver.get("https://demo.opencart.com/index.php?route=account/login");

            //entering the values in the email and password
            driver.findElement(By.xpath("//input[@name='email']")).sendKeys(username);
            driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);

            //click on the login button
            driver.findElement(By.xpath("//input[@type='submit']")).submit();

            boolean status = driver.findElement(By.xpath("//div[text()=' Warning: No match for E-Mail Address and/or Password.']")).isDisplayed();

            FileOutputStream fw = new FileOutputStream(System.getProperty("user.dir") + "\\Testcase_login.xlsx");
           if (status) {
                sh.getRow(1).getCell(4).setCellValue("log in is failed due to invalid credentials");
                wb.write(fw);
                wb.close();
            } else {
                sh.getRow(1).getCell(4).setCellValue("failed");
                wb.write(fw);
                wb.close();
            }
        }
        else {
           System.out.println("test case is not passed");

       }


    }
}
