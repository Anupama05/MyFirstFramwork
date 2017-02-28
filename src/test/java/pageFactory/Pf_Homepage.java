package pageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Pf_Homepage {
	
	@FindBy(linkText="Sign In")
	public WebElement lk_Signin;
	
	@FindBy(id="username")
	public WebElement lbl_username;
	
	public Pf_Homepage(WebDriver driver){
		
		PageFactory.initElements(driver, this);
		
	}
	
	public void click_signin(){
		lk_Signin.click();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
