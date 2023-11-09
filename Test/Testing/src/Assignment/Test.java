package Assignment;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;


public class Test {

	public static WebDriver driver;
	static String TableData;

	public static void PageLogin() throws InterruptedException {
//		Setup the Browser
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
//		Maximize the Window
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//		Navigate to the given URL
		driver.navigate().to("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");

//		Search the Table Data Button And Click on it
		WebElement Table_Data = driver.findElement(By.xpath("//*[text()='Table Data']"));
		Table_Data.click();

		String InputData = "[{\"name\" : \"Bob\", \"age\" : 20, \"gender\": \"male\"},"
				+ " {\"name\": \"George\", \"age\" : 42, \"gender\": \"male\"},"
				+ " {\"name\": \"Sara\", \"age\" : 42, \"gender\": \"female\"},"
				+ " {\"name\": \"Conor\", \"age\" : 40, \"gender\": \"male\"},"
				+ "{\"name\": \"Jennifer\", \"age\" : 42, \"gender\": \"female\"}]";

//		Search the input Box
		WebElement Input_Box = driver.findElement(By.xpath("//textarea[@id='jsondata']"));
//		click on input Box
		Input_Box.click();
//		Clear The Input Box 
		Input_Box.clear();
//		insert Data in input box
		Input_Box.sendKeys(InputData);

//		Delete First and Last from the data
		InputData = InputData.substring(1, InputData.length() - 1);
		ArrayList<String> InputArray = new ArrayList<String>();
//		Add InputData into Input Array
		InputArray.add(InputData);
		for (int i = 0; i < InputArray.size(); i++) {
			String originalString = InputArray.get(i);
			String stringWithoutQuotes = originalString.replaceAll("\"", "");
			String d = stringWithoutQuotes.replaceAll("name", "");
			String age = d.replaceAll("age", "");
			String gender = age.replaceAll("gender", "");
			String g = gender.replaceAll(":", "");
			g = g.replaceAll(",", "");
			// g = g.replaceAll(" ", "");
//			String b = g.replaceAll("{", "");
//			String m = b.replaceAll("}", "");

			InputArray.set(i, g);
		}

		System.out.println("Data1 :- " + InputArray);

		Thread.sleep(2000);
//		locate and Click Refresh Button
		WebElement Refresh_Button = driver.findElement(By.xpath("//button[@id='refreshtable']"));
		Refresh_Button.click();

		ArrayList<String> Data = new ArrayList<String>();

		int Table_size = driver.findElements(By.tagName("tr")).size();

//		Read Data from Table
		for (int i = 2; i <= Table_size; i++) {
			TableData = driver.findElement(By.xpath("(//tr)[" + i + "]")).getText();

			Data.add("{ " + TableData + " }");
		}
		System.out.println("Data 2 :- " + Data);

		boolean matched = Data.equals(InputArray);

	}

	public static void main(String[] args) throws InterruptedException {
		PageLogin();
		driver.quit();
	}

}