# Appium Mobile Automation Framework

A cross-platform mobile automation testing framework built with **Appium 2.0** and **Java**, designed for hands-on learning and real-world mobile QA scenarios. It targets "TheApp" demo application and demonstrates advanced capabilities including visual testing, deep link navigation, QR code handling, and multi-environment configuration.

---

## Table of Contents

- [Framework Overview](#framework-overview)
- [Key Features](#key-features)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Test Suites](#test-suites)
- [Page Object Model](#page-object-model)
- [Utilities & Helpers](#utilities--helpers)
- [Visual Testing](#visual-testing)
- [Deep Link & QR Code Testing](#deep-link--qr-code-testing)
- [Dependencies](#dependencies)

---

## Framework Overview

| Item | Detail |
|---|---|
| Language | Java 11 |
| Build Tool | Maven |
| Test Runner | TestNG 7.9.0 |
| Mobile Driver | Appium 2.0 (java-client 9.0.0) |
| Platforms | Android & iOS |
| Test App | [TheApp](https://github.com/appium-pro/TheApp/releases) (Android v1.12.0 / iOS) |
| Reporting | ExtentReports 5.0.9 |
| Logging | Log4j 2.22.1 |

---

## Key Features

- **Cross-platform** — single codebase runs on both Android and iOS using platform-specific locator annotations
- **Page Object Model (POM)** — clean separation of test logic and UI interactions using `PageFactory` + `AppiumFieldDecorator`
- **Multi-environment support** — STAGING / DEMO / PRODUCTION environments configurable via system properties
- **Visual testing** — pixel-level image comparison and image occurrence detection via Appium image plugin + OpenCV
- **Deep link navigation** — bypass UI flows using custom URI schemes for rapid test setup
- **QR code handling** — decode QR code images using ZXing (Google barcode library)
- **Web view testing** — hybrid app testing with context switching between native and web views
- **Fluent wait helpers** — robust element synchronisation with stale element recovery
- **JSON/YAML test data** — per-environment test data files with Jackson-based deserialization
- **Extensible utilities** — image cropping, screen resolution helpers, OCR (Tess4J), and more

---

## Architecture

```
┌─────────────────────────────────────────────────────────┐
│                      Test Layer                         │
│   TC001_LoginScreen  TC002_WebViewTest  TC003/TC004...  │
└────────────────────────┬────────────────────────────────┘
                         │ extends
┌────────────────────────▼────────────────────────────────┐
│                    BaseSetup                            │
│  @BeforeSuite: report init, test data load              │
│  @BeforeMethod: driver init (Android/iOS)               │
│  @AfterMethod: quit driver                              │
└────────────────────────┬────────────────────────────────┘
                         │ uses
         ┌───────────────┼───────────────┐
         ▼               ▼               ▼
  ┌─────────────┐ ┌────────────┐ ┌──────────────────┐
  │ Page Objects│ │  Helpers   │ │   Utilities       │
  │ LoginScreen │ │ElementHelper│ │ ImageComparison  │
  │ DashBoard   │ │WaitHelper  │ │ ImageUtil         │
  │ WebviewDemo │ └────────────┘ │ ConvertUtil       │
  │ PhotoDemo   │                │ LogicalScreen...  │
  │ DeepLink... │                └──────────────────┘
  └─────────────┘
         │
┌────────▼────────────────────────────────────────────────┐
│               Configuration Layer                       │
│  ConfigurationPropertiesReader (Configuration.properties)│
│  EnvironmentYAMLReader (demo/staging/production.yml)    │
└─────────────────────────────────────────────────────────┘
```

### Design Patterns

| Pattern | Usage |
|---|---|
| Page Object Model | Each screen is a dedicated class with `@AndroidFindBy` / `@iOSXCUITFindBy` locators |
| Singleton | `ConfigurationPropertiesReader`, `EnvironmentYAMLReader`, `LogicalScreenResolution` |
| Factory | `PageFactory.initElements()` with `AppiumFieldDecorator` for cross-platform element init |
| Fluent Wait | `WaitHelper` wraps Selenium `FluentWait` with `StaleElementReferenceException` handling |

---

## Project Structure

```
AppiumFramework/
├── apps/
│   └── TheApp.app/                  # iOS application bundle
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── constants/
│   │   │   │   ├── LogConstants.java          # Log messages & file path constants
│   │   │   │   └── TheAppMessages.java        # App-specific message constants
│   │   │   ├── dataHandler/
│   │   │   │   ├── ConfigurationPropertiesReader.java  # Reads Configuration.properties (Singleton)
│   │   │   │   └── EnvironmentYAMLReader.java          # Reads environment YAML files (Singleton)
│   │   │   ├── enumerations/
│   │   │   │   └── EnvironmentType.java       # STAGING, DEMO, PRODUCTION enum with file paths
│   │   │   ├── exceptions/
│   │   │   │   ├── AutomationTestRunException.java     # Runtime test execution errors
│   │   │   │   ├── AutomationUtilException.java        # Runtime utility/conversion errors
│   │   │   │   └── ImageNotFoundException.java         # Thrown when image matching fails
│   │   │   ├── helper/
│   │   │   │   ├── ElementHelper.java         # Click, input text, get text/attributes
│   │   │   │   └── WaitHelper.java            # Fluent waits with stale element recovery
│   │   │   ├── model/
│   │   │   │   ├── GenericModel.java          # Flexible HashMap-based model
│   │   │   │   └── Student.java              # Student data model (Lombok)
│   │   │   └── utilities/
│   │   │       ├── ConvertUtil.java           # JSON/YAML deserialization helpers
│   │   │       ├── ImageComparison.java       # Image similarity & occurrence matching
│   │   │       ├── ImageUtil.java             # Load, crop, scale, tap-on-image
│   │   │       └── LogicalScreenResolution.java  # Screen center/up/down coordinate helpers
│   │   └── resources/
│   │       ├── Configuration.properties       # App paths, device settings, Appium config
│   │       ├── log4j2.xml                     # Log4j 2 configuration
│   │       └── environments/
│   │           ├── demo.yml                   # DEMO environment variables
│   │           ├── staging.yml                # STAGING environment variables (default)
│   │           └── production.yml             # PRODUCTION environment variables
│   └── test/
│       ├── java/
│       │   ├── appium2/
│       │   │   ├── AndroidDemo.java           # Standalone Appium 2.0 Android demo
│       │   │   └── IOSDemo.java               # Standalone Appium 2.0 iOS demo
│       │   └── theApp/
│       │       ├── BaseSetup.java             # TestNG lifecycle + driver factory
│       │       ├── TC001_LoginScreen.java     # Login screen tests
│       │       ├── TC002_WebViewTest.java     # Web view / hybrid app tests
│       │       ├── TC003_HandDeepLinkAndQRCode.java   # Deep link + QR code tests
│       │       ├── TC004_VisualTestWithImagePlugin.java # Visual regression tests
│       │       ├── listener/                  # TestNG listeners (reporting hooks)
│       │       └── pages/
│       │           ├── LoginScreen.java       # Login page object
│       │           ├── DashBoard.java         # Dashboard / navigation page object
│       │           ├── WebviewDemo.java       # Web view page object
│       │           ├── PhotoDemo.java         # Photo library page object
│       │           └── DeepLinkSecretArea.java # Deep link secret area page object
│       └── resources/
│           ├── image_comparision/             # Reference images for visual tests
│           └── testData/
│               ├── demo.json                  # Test data for DEMO environment
│               ├── stage.json                 # Test data for STAGING environment
│               └── prod.json                  # Test data for PRODUCTION environment
├── testsuites/
│   ├── testsuite.xml                          # Appium 2.0 demo tests
│   └── the_app_suite.xml                      # TheApp full test suite
└── pom.xml
```

---

## Prerequisites

| Requirement | Version |
|---|---|
| Java JDK | 11+ |
| Maven | 3.6+ |
| Node.js | 16+ (for Appium) |
| Appium | 2.x (`npm install -g appium`) |
| Appium UiAutomator2 Driver | `appium driver install uiautomator2` |
| Appium XCUITest Driver | `appium driver install xcuitest` |
| Appium Images Plugin | `appium plugin install images` |
| Android SDK | API level matching target device |
| Xcode | Latest stable (macOS only, for iOS) |

**Devices / Emulators:**

| Platform | Default Device |
|---|---|
| Android | `emulator-5554` |
| iOS | `iPhone 14` |

---

## Installation & Setup

### 1. Clone the repository

```bash
git clone <repository-url>
cd AppiumFramework
```

### 2. Install Appium and drivers

```bash
npm install -g appium
appium driver install uiautomator2
appium driver install xcuitest
appium plugin install images
```

### 3. Install Maven dependencies

```bash
mvn clean install -DskipTests
```

### 4. Configure the app under test

Edit `src/main/resources/Configuration.properties`:

```properties
# Android
apkPathInWindows=C:/path/to/TheApp-v1.12.0-release.apk
apkPathInMacOS=/Users/you/path/to/TheApp-v1.12.0-release.apk
appPackage=io.cloudgrey.the_app
appActivity=io.cloudgrey.the_app.MainActivity

# iOS
ipaPathInWindows=C:/path/to/apps/TheApp.app
ipaPathInMacOS=/path/to/apps/TheApp.app

# Device
device=emulator-5554
platform=ANDROID
deviceVersion=13.0
appiumTimeout=120
```

Download TheApp: [https://github.com/appium-pro/TheApp/releases](https://github.com/appium-pro/TheApp/releases)

### 5. Start Appium server

```bash
# Standard server
appium

# With image plugin (required for TC004 visual tests)
appium --use-plugins=images
```

---

## Configuration

### Environment Selection

Pass `-Denv` and `-Dplatform` as Maven system properties:

| Property | Values | Default |
|---|---|---|
| `-Denv` | `DEMO`, `PROD`, `STAGE` | `STAGE` |
| `-Dplatform` | `ANDROID`, `IOS` | from `Configuration.properties` |

### Environment YAML files

Located in `src/main/resources/environments/`. Each file contains environment-specific values:

```yaml
gateway: gatewayStage
apiKey: apiKey_stage
clientSecret: clientSecret_stage
dbUrl: dbUrl_stage
dbUserName: dbUserName_stage
dbPassword: dbPassword_stage
broker: 10.40.26.222:9092
```

### Test Data

JSON files per environment in `src/test/resources/testData/`:

```json
{
  "studentManagement": {
    "studentId": "DEMO-uuid",
    "firstName": "auto",
    "lastName": "tester"
  }
}
```

---

## Running Tests

### Appium 2.0 Standalone Demos

```bash
# Android Demo
mvn clean test -Dsurefire.suiteXmlFiles=testsuites/testsuite.xml -Dtest=appium2.AndroidDemo

# iOS Demo
mvn clean test -Dsurefire.suiteXmlFiles=testsuites/testsuite.xml -Dtest=appium2.IOSDemo
```

### TheApp Full Test Suite

```bash
# Android - STAGING (default)
mvn clean test -Dsurefire.suiteXmlFiles=testsuites/the_app_suite.xml -Dplatform=ANDROID

# Android - DEMO environment
mvn clean test -Dsurefire.suiteXmlFiles=testsuites/the_app_suite.xml -Dplatform=ANDROID -Denv=DEMO

# iOS - PRODUCTION environment
mvn clean test -Dsurefire.suiteXmlFiles=testsuites/the_app_suite.xml -Dplatform=IOS -Denv=PROD
```

### Run a Single Test Class

```bash
mvn clean test -Dtest=TC001_LoginScreen -Dplatform=ANDROID
```

### Run with Visual Testing (image plugin required)

```bash
appium --use-plugins=images &
mvn clean test -Dsurefire.suiteXmlFiles=testsuites/the_app_suite.xml -Dplatform=ANDROID -Denv=DEMO
```

---

## Test Suites

### `testsuite.xml` — Appium 2.0 Demos

| Test Class | Description |
|---|---|
| `appium2.AndroidDemo` | Basic Appium 2.0 Android driver setup demo |
| `appium2.IOSDemo` | Basic Appium 2.0 iOS driver setup demo |

### `the_app_suite.xml` — TheApp Tests

| Group | Test Class | Description |
|---|---|---|
| Smoke | `TC001_LoginScreen` | Valid/invalid login, error message validation |
| Smoke | `TC002_WebViewTest` | Hybrid app web view URL navigation |
| Normal | `TC003_HandDeepLinkAndQRCode` | Deep link navigation + QR code decoding |
| Normal | `TC004_VisualTestWithImagePlugin` | Visual regression using image similarity |

---

## Page Object Model

Each screen extends a base page object that initialises elements using Appium's `PageFactory`:

```java
public class LoginScreen extends TheAppPageObject {
    @AndroidFindBy(accessibility = "username")
    @iOSXCUITFindBy(accessibility = "username")
    private MobileElement usernameField;

    @AndroidFindBy(accessibility = "password")
    @iOSXCUITFindBy(accessibility = "password")
    private MobileElement passwordField;

    public void login(String username, String password) {
        elementHelper.inputText(usernameField, username);
        elementHelper.inputText(passwordField, password);
        elementHelper.click(loginButton);
    }
}
```

Cross-platform locator support:

| Annotation | Strategy |
|---|---|
| `@AndroidFindBy(accessibility = "...")` | Android accessibility ID |
| `@iOSXCUITFindBy(accessibility = "...")` | iOS accessibility ID |
| `@iOSXCUITFindBy(iOSClassChain = "...")` | iOS class chain |
| `@iOSXCUITFindBy(iOSNsPredicate = "...")` | iOS NS predicate |
| `@AndroidFindBy(xpath = "...")` | XPath (both platforms) |

---

## Utilities & Helpers

### ElementHelper

Wraps common element interactions:

```java
elementHelper.click(element);
elementHelper.inputText(element, "text");
String text = elementHelper.getText(element);
boolean displayed = elementHelper.isDisplayed(element);
```

### WaitHelper

Fluent waits with automatic `StaleElementReferenceException` recovery:

```java
waitHelper.waitUntilElementDisplayed(element);          // 60s timeout, 200ms poll
waitHelper.waitUntilElementCanBeClicked(element);
waitHelper.waitUntilElementVisibleByLocator(locator);
waitHelper.waitUntilElementDisappear(element);
```

### ConvertUtil

Deserialize JSON/YAML test data:

```java
Student student = ConvertUtil.convertJsonToObject(jsonString, Student.class);
Map<String, Object> map = ConvertUtil.convertYamlToMap(yamlString);
```

---

## Visual Testing

Requires Appium server started with `--use-plugins=images`.

### Image Similarity (full-screen comparison)

```java
// Compare current screenshot against reference image
ImageComparison.similar(driver, actualImageFile, expectedImageFile, 0.85);
```

### Image Occurrence (find partial image in screenshot)

```java
// Find and tap on an image within the current screen
ImageUtil.tapOnImage(driver, referenceImageFile, 0.8);

// Find image within a threshold range (0.1 to 1.0)
ImageUtil.findMatchedImageByThresholdRange(driver, referenceImageFile);

// Find image at exact threshold
ImageUtil.findMatchedImageBySpecificThreshold(driver, referenceImageFile, 0.8);
```

### Image Cropping

```java
// Crop screenshot to a specific element's bounds
BufferedImage cropped = ImageUtil.cropByTarget(driver, screenshotFile, webElement);
```

**Reference images** are stored in `src/test/resources/image_comparision/`.  
Use platform-specific naming: `imageName_android.png` / `imageName_ios.png`.

---

## Deep Link & QR Code Testing

### Deep Link Navigation

Bypass UI flows by navigating directly to an app state:

```java
// Navigate directly to the login screen with credentials pre-filled
driver.get("theapp://login/alice/mypassword");
```

### QR Code Decoding

Decode QR code content from an image file using ZXing:

```java
// Reads and decodes a QR code image
String decodedContent = QRCodeUtil.decode("src/test/resources/Kitty_QRCode.png");
```

---

## Dependencies

| Category | Library | Version |
|---|---|---|
| Mobile Automation | io.appium:java-client | 9.0.0 |
| Browser/Remote | org.seleniumhq.selenium:selenium-remote-driver | 4.13.0 |
| Test Runner | org.testng:testng | 7.9.0 |
| Reporting | com.aventstack:extentreports | 5.0.9 |
| Logging | org.apache.logging.log4j:log4j-core | 2.22.1 |
| Image Processing | org.openpnp:opencv | 4.5.1-2 |
| OCR | net.sourceforge.tess4j:tess4j | 4.5.4 |
| QR Code | com.google.zxing:core | 3.3.0 |
| YAML Parsing | com.fasterxml.jackson.dataformat:jackson-dataformat-yaml | 2.8.11 |
| JSON Parsing | com.fasterxml.jackson.core:jackson-databind | 2.9.6 |
| Code Generation | org.projectlombok:lombok | 1.18.18 |
| API Testing | io.rest-assured:rest-assured | 3.3.0 |
| Database | org.postgresql:postgresql | 42.0.0 |
| Cache | redis.clients:jedis | 3.3.0 |
| Messaging | org.apache.kafka:kafka-clients | 3.2.1 |
| Auth | org.keycloak:keycloak-admin-client | 9.0.2 |
| JWT | io.jsonwebtoken:jjwt | 0.7.0 |

---

## License

This project is intended for educational and demonstration purposes.
