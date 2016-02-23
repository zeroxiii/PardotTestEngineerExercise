# PardotTestEngineerExcercise
## Candidate: Rahmaan Lodhia

Problem Statement: 

Thank you for taking the time out of your day to speak with me about the Software Engineer in Test role.  I’d like to move you on to the next step in the process where we have you complete a technical assessment by building a Selenium test suite in Java using Selenium WebDriver (please do not export from the IDE, this should be coded with Web Driver).  Please include all files necessary to run the project on github with a readme.md.  It shouldn't take too long to complete, and I've pasted the instructions below:

1. Log in to Pardot (https://pi.pardot.com, Username: pardot.applicant@pardot.com, Password: Applicant2012)
2. Create a list with a random name (Marketing > Segmentation > Lists)
3. Attempt to create another list with that same name and ensure the system correctly gives a validation failure
4. Rename the original list
5. Ensure the system allows the creation of another list with the original name now that the original list is renamed
6. Create a new prospect (Prospect > Prospect List)
7. Add your new prospect to the newly created list
8. Ensure the new prospect is successfully added to the list upon save
9. Send a text only email to the list (Marketing > Emails)  *Please note, email is disabled in this account so you will not actually be able to send the email.  This is okay.
10. Log out


## Instructions

Run via Eclipse IDE:

1. Download the entire repo to your computer and note its location.

2. On a computer, download the latest version of Java (Note: this program was compiled using Java Version 1.8.0_71), Mozilla FireFox, and Eclipse for Java (For the creation of these files, the Eclipse IDE for Java was used; however, any IDE should work the same way with similar steps).

3. Launch Eclipse (or any IDE) and select any workspace to work in.

4. Create a new project via File -> New -> JavaProject and name it "Lodhia_Pardot_Test" (or anything else).  Click "Finish". (NOTE: If you already have Selenium jars configured in the build path, you skip to step 7)

5. Right click the created project and click "Properties". Click on "Java Build Path" and in the new window, click on "Libraries".

6. In the "Libraries" menu, click "Add External JARS" and navigate to the "selenium-2.52.0" folder in the downloaded repo folder structure.  Select from this folder all *.jar files in the directory and /lib folder levels and click open in the window.  All the selected jars should appear in the "Libraries" window.  Click "OK" to close the window.

7. Right-click the src folder in the project view and click "Import".  Select "General"->"File System".  In the "From Directory" box, browse to the "src" folder in the downloaded repo and select ok.  Verify that the IDE recognizes the two java files "PardotTest.java" and "PardotTestRunner.java" to be added.  Select them and click "Finish".

8. Run the program by selecting PardotTestRunner and clicking the run command.  The program will run all the test functions listed in PardotTest.java in order via Mozilla FireFox.

9. The program can also be run via PardotTest.java.  Running this in the IDE will run the JUnit test cases and show the output of the JUnit tests as well.

## Outputs

Console Output:
Step 1: Login - Starting  
Step 1: Login - Pass  
Step 2: Creating a list with a random Name - Starting  
Step 2: Creating a list with a random Name - Pass  
Step 3: Creating a list with a used name - Starting  
Step 3: Creating a list with a used name - Pass  
Step 4: Rename original list - Starting  
Step 4: Rename original list - Pass  
Step 5: Recreate a list with original name - Starting  
Step 5: Recreate a list with original name - Pass  
Step 6: Create a new prospect - Starting  
Step 6: Create a new prospect - Pass  
Step 7: Add Prospect to list - Starting  
Step 7: Add Prospect to list - Pass 
Step 8: Verify prospect is added to list - Starting  
Step 8: Verify prospect is added to list - Pass  
Step 9: Create and send text only email to created list - Starting  
Step 9: Create and send text only email to created list - Pass  
Step 10: Logout - Starting  
Step 10: Logout - Pass  
All test steps completed successfully!  

## Notes

The WebDriver used in this implementation was with Mozilla FireFox as it does not require any additional setup on the users end.  This code will only work on a computer with FireFox installed.  

A timeout was implemented in this code to for about 10-15 seconds.  This was to accomodate slower internet connections, but it is hard to predict what the best timeout to use is, so a standard amount was chosen.

The code itself will verify using "assert" statements after each major step.  Most of these assertions verified that a proper page was displayed when a certain action was committed.  In addition, this test suite runs through all the test cases in numerical order, as every test cases is loosely related to the previous one and all require the user to be logged into the system.
