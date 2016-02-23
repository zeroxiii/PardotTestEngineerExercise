/* Pardot Software Engineer in Test Exercise
 * Candidate: Rahmaan Lodhia
 * PardotTest Class
 * Description: Contains all test cases required in assignment
 */

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PardotTest 
{
	public static String originalListName, prospectEmail, pardotURL, userLogin, userPassword, campaignID, profileID;
	public static WebDriver driver;
	public static WebDriverWait block;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		// Initialize Global Variables
		
		// Initialize FireFox browser driver and DriverWait
		driver = new FirefoxDriver();
		block = new WebDriverWait(driver, 15);
		
		// Set driver to have an implicit wait to properly handle page loads
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		// Initialize string inputs for use in creating Lists and Prospects
		originalListName = "Lodhia_Test_" + (int) (Math.random()*100000);
		prospectEmail = originalListName + "@gmail.com";
		campaignID = "Adil Yellow Jackets";
		profileID = "Adil Yellow Jackets 1";
		
		// Initialize Login information
		pardotURL = "https://pi.pardot.com";
		userLogin = "pardot.applicant@pardot.com";
		userPassword = "Applicant2012";
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		// Close the browser after finished
		driver.quit();
	}

	@Test
	public void test01ValidLogin() 
	{
		// Attempt to login to Pardot and verify if successful
		System.out.println("Step 1: Login - Starting");
		
		driver.get(pardotURL);
		driver.findElement(By.id("email_address")).sendKeys(userLogin);
		driver.findElement(By.id("password")).sendKeys(userPassword);
		driver.findElement(By.name("commit")).submit();
		
		// Verify that login was successful
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		assertEquals("Dashboard - Pardot", driver.getTitle());
		
		System.out.println("Step 1: Login - Pass");
	}
	
	@Test
	public void test02CreateOriginalList()
	{
		// Navigate to Marketing > Segmentation > Lists
		driver.get(pardotURL + "/list");
		block.until(ExpectedConditions.titleIs("Lists - Pardot"));
		
		// Find and click Link Create button
		WebElement btnCreate = driver.findElement(By.id("listxistx_link_create"));
		btnCreate.click();
		
		System.out.println("Step 2: Creating a list with a random Name - Starting");
		
		// Attempt to create a list with a random generated name
		// Create a list with the generated name
		WebElement informationModal = block.until(ExpectedConditions.visibilityOfElementLocated(By.id("information_modal")));
		
		WebElement txtListName = informationModal.findElement(By.id("name"));
		txtListName.sendKeys(originalListName);
		
		WebElement btnSave = informationModal.findElement(By.id("save_information"));
		btnSave.click();
		
		// Verify successful creation by waiting for created List page to appear
		assertEquals(originalListName + " - Pardot", driver.getTitle());
		System.out.println("Step 2: Creating a list with a random Name - Pass");
	}
	
	@Test
	public void test03CreateDuplicateList()
	{
		// Navigate to Marketing > Segmentation > Lists
		driver.get(pardotURL + "/list");
		block.until(ExpectedConditions.titleIs("Lists - Pardot"));

		// Find and click Link Create button
		WebElement btnCreate = driver.findElement(By.id("listxistx_link_create"));
		btnCreate.click();

		System.out.println("Step 3: Creating a list with a used name - Starting");
		
		// Verify an error occurs if a list with the same name is created
		// Create a List with same name
		WebElement informationModal = block.until(ExpectedConditions.visibilityOfElementLocated(By.id("information_modal")));
		
		WebElement txtListName = informationModal.findElement(By.id("name"));
		txtListName.sendKeys(originalListName);
		
		WebElement btnSave = informationModal.findElement(By.id("save_information"));
		btnSave.click();

		// Wait to see if information Error Modal Appears
		WebElement informationErrorModal = block.until(ExpectedConditions.visibilityOfElementLocated(By.id("information_modal")));
		
		// Verify that error messages match what is expected
		if (informationErrorModal.findElement(By.className("alert-error")).isDisplayed())
			assertEquals("Please correct the errors below and re-submit", informationErrorModal.findElement(By.className("alert-error")).getText());
		if (informationErrorModal.findElement(By.id("error_for_name")).isDisplayed())
			assertEquals("Please input a unique value for this field", informationErrorModal.findElement(By.id("error_for_name")).getText());

		// Close out modal dialog and return to Lists Screen
		WebElement btnCancel = informationErrorModal.findElement(By.className("btn-default"));
		btnCancel.click();
		
		System.out.println("Step 3: Creating a list with a used name - Pass");
	}
	
	@Test
	public void test04RenameListAndRecreatOriginalList()
	{
		// Navigate to Marketing > Segmentation > Lists
		driver.get(pardotURL + "/list");
		block.until(ExpectedConditions.titleIs("Lists - Pardot"));
		
		System.out.println("Step 4: Rename original list - Starting");
		
		// Filter and search on the original list name
		WebElement txtListFilter = driver.findElement(By.id("listx_table_filter"));
		txtListFilter.sendKeys(originalListName);
		txtListFilter.sendKeys(Keys.RETURN);
		
		// Locate the original list in the filtered output if it exists and click it
		WebElement linkTargetList = block.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='"+ originalListName +"']")));
		linkTargetList.click();
		
		// Wait until list page loads and then click Edit
		WebElement btnEdit = block.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Edit']")));
		btnEdit.click();
		
		// Wait until list information modal appears
		WebElement informationEditModal = block.until(ExpectedConditions.visibilityOfElementLocated(By.id("information_modal")));
		
		// Locate text field for name, clear the original name and create a new name
		WebElement txtListName = informationEditModal.findElement(By.id("name"));
		txtListName.clear();
		txtListName.sendKeys(originalListName + "new");
		
		// Find update button and save changes
		WebElement btnUpdate = informationEditModal.findElement(By.className("btn-primary"));
		btnUpdate.submit();
		
		// Verify the list is now renamed
		assertEquals(originalListName + "new" + " - Pardot", driver.getTitle());
		
		System.out.println("Step 4: Rename original list - Pass");

		// Navigate to Marketing > Segmentation > Lists
		driver.get(pardotURL + "/list");
		block.until(ExpectedConditions.titleIs("Lists - Pardot"));
		
		// Find and click Link Create button
		WebElement btnCreate = driver.findElement(By.id("listxistx_link_create"));
		btnCreate.click();
		
		System.out.println("Step 5: Recreate a list with original name - Starting");
		
		// Attempt to create a list with a random generated name
		// Create a list with the generated name
		WebElement informationModal = block.until(ExpectedConditions.visibilityOfElementLocated(By.id("information_modal")));
		
		txtListName = informationModal.findElement(By.id("name"));
		txtListName.sendKeys(originalListName);
		
		WebElement btnSave = informationModal.findElement(By.id("save_information"));
		btnSave.click();
		
		// Verify successful creation by waiting for created List page to appear
		assertEquals(originalListName + " - Pardot", driver.getTitle());
		
		System.out.println("Step 5: Recreate a list with original name - Pass");
	}
	
	@Test
	public void test05CreateProspectAndAddToListAndVerify()
	{
		// Navigate to Prospect -> Prospect List
		driver.get(pardotURL + "/prospect");
		block.until(ExpectedConditions.titleIs("Prospects - Pardot"));
		
		// Find create button and click it
		WebElement btnCreate = driver.findElement(By.id("pr_link_create"));
		btnCreate.click();
		
		System.out.println("Step 6: Create a new prospect - Starting");
		
		// Input required fields into prospect creation form
		WebElement prospectForm = block.until(ExpectedConditions.visibilityOfElementLocated(By.id("pr_status")));
		
		// Type email
		WebElement txtEmail = prospectForm.findElement(By.id("email"));
		txtEmail.sendKeys(prospectEmail);
		
		// Select campaign
		WebElement campaignDropDownListBox = prospectForm.findElement(By.id("campaign_id"));
		Select campaign = new Select(campaignDropDownListBox);
		campaign.selectByVisibleText(campaignID);
		
		// Select Profile
		WebElement profileDropDownListBox = prospectForm.findElement(By.id("profile_id"));
		Select profile = new Select(profileDropDownListBox);
		profile.selectByVisibleText(profileID);
		
		// Type in score
		WebElement txtScore = prospectForm.findElement(By.id("score"));
		txtScore.clear();
		txtScore.sendKeys("100");
		
		// Submit creation form
		WebElement btnSubmit = prospectForm.findElement(By.className("btn-primary"));
		btnSubmit.submit();
		
		// Verify successful creation by waiting for created Prospect page to appear
		block.until(ExpectedConditions.titleContains(prospectEmail));
		assertEquals(prospectEmail + " - Pardot", driver.getTitle());
		
		System.out.println("Step 6: Create a new prospect - Pass");
		
		// Navigate to List Options
		WebElement linkToLists = driver.findElement(By.linkText("Lists"));
		linkToLists.click();
		
		System.out.println("Step 7: Add Prospect to list - Starting");
		
		// Attempt to add Prospect to List
		// Wait until list options load
		block.until(ExpectedConditions.titleIs("Lists - Pardot"));
		
		// Click the drop down list input
		WebElement dropDownListInput = driver.findElement(By.xpath("//span[text()='Select a list to add...']"));
		dropDownListInput.click();
		
		// Type in list name and add the list to the prospect
		WebElement listDropDownTXT = driver.switchTo().activeElement();
		listDropDownTXT.sendKeys(originalListName);
		listDropDownTXT.sendKeys(Keys.RETURN);
		
		// Submit changes
		driver.findElement(By.className("btn-primary")).submit();
		
		System.out.println("Step 7: Add Prospect to list - Pass");
		
		// Navigate to Marketing > Segmentation > Lists
		driver.get(pardotURL + "/list");
		block.until(ExpectedConditions.titleIs("Lists - Pardot"));

		System.out.println("Step 8: Verify prospect is added to list - Starting");
		
		// Filter and search on the original list name
		WebElement txtListFilter = driver.findElement(By.id("listx_table_filter"));
		txtListFilter.sendKeys(originalListName);
		txtListFilter.sendKeys(Keys.RETURN);
					
		// Locate the original list in the filtered output if it exists and click it
		WebElement linkTargetList = block.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='"+ originalListName +"']")));
		linkTargetList.click();
		
		// Verify that the prospect is linked to the original list
		WebElement targetProspect = block.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='"+ prospectEmail +"']")));
		assertEquals(prospectEmail, targetProspect.getText());
		
		System.out.println("Step 8: Verify prospect is added to list - Pass");
	}
	
	@Test
	public void test06CreateAndSendEmail()
	{
		System.out.println("Step 9: Create and send text only email to created list - Starting");
		
		// Attempt to create and send email to the created list
		// Navigate to Marketing -> Emails -> New Email and wait for new email dialog to appear
		driver.get(pardotURL + "/email/draft/edit");
		WebElement newEmailModal = block.until(ExpectedConditions.visibilityOfElementLocated(By.id("information_modal")));	
		
		// Type in email title
		WebElement txtEmailName = newEmailModal.findElement(By.id("name"));
		txtEmailName.sendKeys(originalListName + "_Email");
		
		// Select a campaign for email
		newEmailModal.findElement(By.xpath("//div[@data-placeholder-text='Choose a Campaign']")).click();
		WebElement campaignSelectionModal = block.until(ExpectedConditions.visibilityOfElementLocated(By.id("asset-chooser-app-modal")));		
		campaignSelectionModal.findElement(By.className("filter-by")).sendKeys(campaignID);
		WebElement campaignName = block.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[text() = ' " + campaignID + "']")));
		campaignName.click();
		campaignSelectionModal.findElement(By.id("select-asset")).click();
		
		// Select radio button to set email type to text only
		newEmailModal.findElement(By.id("email_type_text_only")).click();
		
		// Disable template option
		newEmailModal.findElement(By.id("from_template")).click();
		
		// Save the information
		newEmailModal.findElement(By.id("save_information")).click();

		// Wait until modal dialog disappears
		block.until(ExpectedConditions.invisibilityOfElementLocated(By.id("information_modal")));
		
		// Locate the sending option on the navigation bar
		driver.findElement(By.id("flow_sending")).click();;
		
		// Wait until sending options are loaded
		block.until(ExpectedConditions.titleIs("Sending: " + originalListName + "_Email - Pardot"));
		
		// Select the original list for the email
		driver.findElement(By.xpath("//span[text()='Select a list to add...']")).click();
		WebElement emailListDropDownTXT = driver.switchTo().activeElement();
		emailListDropDownTXT.sendKeys(originalListName);
		emailListDropDownTXT.sendKeys(Keys.RETURN);
		
		// Select a specific user to send the email
		WebElement selectSenderDropDownBox = driver.findElement(By.name("a_sender[]"));
		Select sender = new Select(selectSenderDropDownBox);
		sender.selectByVisibleText("Specific User");
		
		// Type in an email title
		driver.findElement(By.id("subject_a")).sendKeys("Pardot Exercise Email");
		
		// Save email draft
		driver.findElement(By.id("save_footer")).click();
		
		// Navigate to Marketing -> Emails -> Drafts
		driver.get(pardotURL + "/email/index/view/alldraft");		
		
		// Filter and search on the email name
		WebElement emailDraftFilter = driver.findElement(By.id("emailDraft_table_filter"));
		emailDraftFilter.sendKeys(originalListName + "_Email");
		emailDraftFilter.sendKeys(Keys.RETURN);
					
		// Locate the email draft in the filtered output if it exists and click it
		WebElement linkTargetEmailDraft = block.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='"+ originalListName + "_Email" +"']")));
		linkTargetEmailDraft.click();
		
		// Verify the draft is created with the proper name and exists
		assertEquals("Draft: " + originalListName + "_Email - Pardot", driver.getTitle());
		
		// Since email permissions are not in place, view drafts to ensure creation
		driver.get(pardotURL);
		
		System.out.println("Step 9: Create and send text only email to created list - Pass");

	}
	
	@Test
	public void test07ValidLogout()
	{
		// Attempt to logout of Pardot and verify if successful
		System.out.println("Step 10: Logout - Starting");
		
		driver.get(pardotURL + "/user/logout");
		assertEquals("Sign In - Pardot", driver.getTitle());
		//block.until(ExpectedConditions.titleIs("Sign In - Pardot"));
		
		System.out.println("Step 10: Logout - Pass");
	}

}