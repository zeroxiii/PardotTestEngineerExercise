package PardotTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class PardotTestRunner 
{
   public static void main(String[] args) 
   {
	  // Run all the test cases in PardotTest
      Result result = JUnitCore.runClasses(PardotTest.class);
      
      // Check to see if any failures occurred
      for (Failure failure : result.getFailures()) 
      {
         System.out.println(failure.toString());
      }
      
      // If no failures present, test was successful
      if (result.wasSuccessful())
    	  System.out.println("All test steps completed successfully!");
   }
} 
