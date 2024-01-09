# Mobile Automation with Appium 
- Framework to hands-on or learn the new Appium's features updated
- Using "theApp" to hands-on with Android v1.12.0 / iOS (old version)  
- The demo application can be downloaded at https://github.com/appium-pro/TheApp/releases

## How to run 
 - Manually start the Appium 2.0 server & the emulators (Android or iOS) 
 - For Appium 2.0 showcases:
   - ```mvn clean test -Dsuite="testsuite.xml" -Dtest="appium2.AndroidDemo"```
   - ```mvn clean test -Dsuite="testsuite.xml" -Dtest="appium2.IOSDemo"```
 - For The App showcases:
   - ```appium --use-plugins=images```
   - ```mvn clean test -Dsuite="the_app_suite.xml" -Dplatform=ANDROID -Denv=DEMO```
   
## Key Takeaways
 - Cross-platform with Appium 2.0 - Page Object Factory
 - Locator Strategies in Appium such as iOSClassChain, iOS Predicate String, AndroidUIAutomator
 - ApacheLog4j v2
 - Extent Report 5.0.9
 - Custom Exception to handle the exception thrown 
 - Read the configurations, and test data of specific environments from Configuration.Properties, YAML, or JSON files
 - Navigation by the deeplink
 - Handle the QR Code Test
 - Visual Test with the image plugin. 
   - Require to start the appium with Image plugins attached: ```appium --use-plugins=images```
   - Find the image matching with the specific threshold.
   - Image Comparison