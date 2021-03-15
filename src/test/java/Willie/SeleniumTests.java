package Willie;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

// Demonstration of multi browser test cases
// when time permits the following work needs to be done:
// - get Google mail cases running again
// - add verification that email was sent successfully
// - extract element IDs to string constants
// - Safari not exiting gracefully

public class SeleniumTests {

  private String YahooAddress = "MySeleniumTest42@yahoo.com";
  private String GoogleAddress = "MySeleniumTest42@gmail.com";
  private int MaxEmailCount = 1;                  // terminate case after this many emails sent
  private int secondsToTimeout = 10;              // number of seconds to timeout
  private int numberRetriesFindElement = 10;       // number of times to look for an element before failing the case
  private boolean debug = true;                   // true will write each action to the console
  private enum Browser {CHROME, FIREFOX, IE, EDGE, SAFARI};
  private enum LocateType {ID, NAME, LINK_TEXT, CSS_SELECTOR, CLASS_NAME, XPATH};
  private WebDriver driver = null;

  @BeforeEach
  public void Setup() {
    driver = null;
  }

  @AfterEach
  public void Cleanup() {
    driver.quit();
  }

  //<editor-fold desc="Google SendEmail WithoutRetries">
  @Test
  @Disabled("Selenium is no longer able to sign in to gmail")
  public void Chrome_Google_SendEmail_WithoutRetries() {
    driver = BrowserFactory(Browser.CHROME);
    Google_Send_Email_WithoutRetries(driver);
  }

  @Test
  @Disabled("Selenium is no longer able to sign in to gmail")
  public void Firefox_Google_SendEmail_WithoutRetries() {
    driver = BrowserFactory(Browser.FIREFOX);
    Google_Send_Email_WithoutRetries(driver);
  }

  @Test
  @Disabled("Selenium is no longer able to sign in to gmail")
  public void Safari_Google_SendEmail_WithoutRetries() {
    driver = BrowserFactory(Browser.SAFARI);
    Google_Send_Email_WithoutRetries(driver);
  }
  //</editor-fold>

  //<editor-fold desc="Google SendEmail">
  @Test
  @Disabled("Selenium is no longer able to sign in to gmail")
  public void Chrome_Google_Send_Email() {
    driver = BrowserFactory(Browser.CHROME);
    Google_Send_Email(driver);
  }

  @Test
  @Disabled("Selenium is no longer able to sign in to gmail")
  public void Firefox_Google_Send_Email() {
    driver = BrowserFactory(Browser.FIREFOX);
    Google_Send_Email(driver);
  }

  @Test
  @Disabled("Selenium is no longer able to sign in to gmail")
  public void Safari_Google_Send_Email() {
    driver = BrowserFactory(Browser.SAFARI);
    Google_Send_Email(driver);
  }
  //</editor-fold>

  //<editor-fold desc="Google Search">
  @Test
  public void Chrome_Google_Search() {
    driver = BrowserFactory(Browser.CHROME);
    Google_Search(driver);
  }

  @Test
  public void Firefox_Google_Search() {
    driver = BrowserFactory(Browser.FIREFOX);
    Google_Search(driver);
  }

  @Test
  public void Safari_Google_Search() {
    driver = BrowserFactory(Browser.SAFARI);
    Google_Search(driver);
  }
  //</editor-fold>

  //<editor-fold desc="Yahoo Send Email">
  @Test
  public void Chrome_Yahoo_Send_Email() {
    driver = BrowserFactory(Browser.CHROME);
    Yahoo_Send_Email(driver);
  }

  @Test
  public void Firefox_Yahoo_Send_Email() {
    driver = BrowserFactory(Browser.FIREFOX);
    Yahoo_Send_Email(driver);
  }

  @Test
  public void Safari_Yahoo_Send_Email() {
    driver = BrowserFactory(Browser.SAFARI);
    Yahoo_Send_Email(driver);
  }
  //</editor-fold>

  //<editor-fold desc="Yahoo Search">
  @Test
  public void Chrome_Yahoo_Search() {
    driver = BrowserFactory(Browser.CHROME);
    Yahoo_Search(driver);
  }

  @Test
  public void Firefox_Yahoo_Search() {
    driver = BrowserFactory(Browser.FIREFOX);
    Yahoo_Search(driver);
  }

  @Test
  public void Safari_Yahoo_Search() {
    driver = BrowserFactory(Browser.SAFARI);
    Yahoo_Search(driver);
  }
  //</editor-fold>

  //<editor-fold desc="Case Methods">
  private void Google_Send_Email_WithoutRetries(WebDriver driver) {
    // this method does not retry the finding of elements
    // mainly to test Selenium accuracy of locating elements

    // OPEN BROWSER
    driver.navigate().to("http://google.com/");
    try {Thread.sleep(2000);} catch (InterruptedException e) {}
    Assertions.assertTrue(driver.getTitle().startsWith("Google"));

    // SIGN IN
    WebElement signin = driver.findElement(By.linkText("Sign in"));
    signin.click();

    // USER NAME
    WebElement name = driver.findElement(By.name("identifier"));
    name.sendKeys(GoogleAddress);

    // NEXT
    WebElement next = driver.findElement(By.id("identifierNext"));
    next.click();

    // PASSWORD
    WebElement password = driver.findElement(By.name("Passwd"));
    password.sendKeys("!@34ASdf");
    password.sendKeys(Keys.RETURN);

    // GO TO EMAIL
    WebElement email = driver.findElement(By.id("Gmail"));
    email.click();

    DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, new Locale("en", "EN"));
    String formattedDate = df.format(new Date());

    // COMPOSE NEW EMAIL
    WebElement compose = driver.findElement(By.cssSelector("*[class^='T-I J-J5-Ji T-I-KE L3']"));    // 6 css selector
    compose.click();

    // ENTER RECIPIENT
    WebElement toField = driver.findElement(By.name("to"));
    MySendkeys(toField, YahooAddress);

    // ENTER SUBJECT
    WebElement subject = driver.findElement(By.name("subjectbox"));
    subject.sendKeys("Google Send Email WithoutRetries - " + formattedDate);

    // SEND EMAIL
    WebElement send = driver.findElement(By.cssSelector("*[class^='T-I J-J5-Ji aoO']"));
    send.click();

    // SIGN OUT
    WebElement user = (new WebDriverWait(driver, secondsToTimeout)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("*[class^='gb_2a']")));
    user.click();

    WebElement signOut = (new WebDriverWait(driver, secondsToTimeout)).until(ExpectedConditions.presenceOfElementLocated(By.id("gb_71")));
    signOut.click();
  }

  private void Google_Send_Email(WebDriver driver) {
    // this method loops in a try statement while finding elements

    // OPEN BROWSER
    driver.navigate().to("http://google.com/");
    try {Thread.sleep(2000);} catch (InterruptedException e) {}
    Assertions.assertTrue(driver.getTitle().startsWith("Google"));

    // SIGN IN
    WebElement signin = Locate(driver, LocateType.ID, "gb_70", "SignIn button");        // 1
    signin.click();

    // USER NAME
    // using try because Google caches username, so we need to work with and without username
    WebElement name = Locate(driver, LocateType.ID, "Email", "UserName");               // 2
    MySendkeys(name, GoogleAddress);
    // get next button
    WebElement next = Locate(driver, LocateType.ID, "next", "Next button");             // 3
    next.click();

    // PASSWORD
    // we might have a cached connection
    WebElement password = Locate(driver, LocateType.NAME, "Passwd", "Password");        // 4
    MySendkeys(password, "!@34ASdf");
    MySendkeys(password, "Keys.RETURN");

    // GO TO EMAIL
    WebElement email = Locate(driver, LocateType.LINK_TEXT, "Gmail", "Gmail link");     // 5
    email.click();

    // at this point we handle the case of one email send, or loop indefinitely sending email
    boolean loop = true;
    int loopCount = 0;
    WebElement compose = null;
    WebElement toField = null;
    WebElement subject = null;
    DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, new Locale("en", "EN"));
    String formattedDate = df.format(new Date());
    WebElement send = null;

    if (debug) {
      System.out.println("BEGIN LOOPING...");
    }
    while (loop) {
      loopCount++;
      if (debug) {
        System.out.println(" ");
        System.out.println("LOOP " + loopCount);
      }

      // COMPOSE NEW EMAIL
      compose = Locate(driver, LocateType.CSS_SELECTOR, "*[class^='T-I J-J5-Ji T-I-KE L3']", "Compose button");
      compose.click();

      // ENTER RECIPIENT
      toField = Locate(driver, LocateType.NAME, "to", "To");
      MySendkeys(toField, YahooAddress);

      // ENTER SUBJECT
      subject = Locate(driver, LocateType.NAME, "subjectbox", "Subject");
      MySendkeys(subject, "Automation - " + formattedDate);

      // SEND EMAIL
      send = Locate(driver, LocateType.CSS_SELECTOR, "*[class^='T-I J-J5-Ji aoO']", "Send button");
      send.click();

      if (loopCount >= MaxEmailCount)
        loop = false;
    }

    // SIGN OUT
    Google_Signout(driver);
  }

  private void Google_Search(WebDriver driver) {

    // OPEN BROWSER
    driver.navigate().to("http://google.com/");
    Assertions.assertTrue(driver.getTitle().startsWith("Google"));

    // get Search box
    WebElement search = Locate(driver, LocateType.NAME, "q", "Search field");
    search.sendKeys("10 Google bombs that will live in infamy");
    search.sendKeys(Keys.RETURN);
    try {Thread.sleep(3000);} catch (InterruptedException e) {}

    if (!verifyTextExistsOnPage(driver, "mashable.com")) {
      driver.close();
      Assertions.fail();
    }

  }

  private void Yahoo_Send_Email(WebDriver driver) {

    // OPEN BROWSER
    driver.navigate().to("http://yahoo.com/");
    Assertions.assertTrue(driver.getTitle().startsWith("Yahoo"));

    // SIGN IN
    WebElement signIn;
    signIn = Locate(driver, LocateType.LINK_TEXT, "Sign in", "Sign In link");
    signIn.click();

    // USER NAME
    WebElement userName = Locate(driver, LocateType.NAME, "username", "UserName");
    userName.sendKeys("MySeleniumTest42");

    // NEXT
    WebElement next = Locate(driver, LocateType.ID, "login-signin", "Next button");
    next.click();
    try {Thread.sleep(1000);} catch (InterruptedException e) {}

    // PASSWORD
    WebElement password = Locate(driver, LocateType.NAME, "password", "Password");
    password.sendKeys("!@34ASdf");
    password.sendKeys(Keys.RETURN);

    // EMAIL LINK
    WebElement mail = Locate(driver, LocateType.LINK_TEXT, "Mail", "Mail link");
    mail.click();

    // at this point we handle the case of one email send, or loop indefinitely sending email
    boolean loop = true;
    int loopCount = 0;
    WebElement compose = null;
    WebElement to = null;
    WebElement subject = null;
    DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, new Locale("en", "EN"));
    String formattedDate = df.format(new Date());
    WebElement send = null;

    if (debug) {
      System.out.println("BEGIN LOOPING...");
    }
    while (loop) {
      loopCount++;
      if (debug) {
        System.out.println(" ");
        System.out.println("LOOP " + loopCount);
      }

      // COMPOSE
      compose = Locate(driver, LocateType.LINK_TEXT, "Compose", "Compose button");
      compose.click();

      // TO FIELD
      to = Locate(driver, LocateType.ID, "message-to-field", "To field");
      to.sendKeys(GoogleAddress);

      // SUBJECT FIELD
      subject = Locate(driver, LocateType.XPATH, "//*[@data-test-id='compose-subject']", "Subject field");
      subject.sendKeys("Automation - " + formattedDate);

      // SEND
      send = Locate(driver, LocateType.XPATH, "//*[@data-test-id='compose-send-button']", "Send");
      send.click();

      if (loopCount >= MaxEmailCount)
        loop = false;

      // SIGN OUT
      Yahoo_Signout(driver);
    }
  }

  private void Yahoo_Search(WebDriver driver) {

    // OPEN BROWSER
    driver.navigate().to("http://yahoo.com/");
    Assertions.assertTrue(driver.getTitle().startsWith("Yahoo"));

    // SEARCH BOX
    WebElement searchbox = Locate(driver, LocateType.NAME, "p", "Search box");
    searchbox.sendKeys("10 Google bombs that will live in infamy - Mashable");
    searchbox.sendKeys(Keys.RETURN);
    try {Thread.sleep(3000);} catch (InterruptedException e) {}

    if (!verifyTextExistsOnPage(driver, "mashable.com")) {
      Assertions.fail();
    }
  }
  //</editor-fold>
  
  //<editor-fold desc="Helper methods">
  private boolean verifyTextExistsOnPage(WebDriver driver, String text) {

    String textToSearch = "";
    String textToVerify = text;
    List<WebElement> allSearchResults = driver.findElements(By.cssSelector("*"));
    boolean textFound = false;
    for (WebElement eachResult : allSearchResults) {
      textToSearch = eachResult.getText();
      if (textToSearch.contains(textToVerify)) {
        textFound = true;
        break;
      }
    }
    if (textFound) {
      return true;
    }

    return false;
  }

  private void MySendkeys(WebElement el, String send) {
    if (send == "Keys.RETURN")
      el.sendKeys(Keys.RETURN);
    else
      el.sendKeys(send);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private WebDriver BrowserFactory(Browser browser) {

    WebDriver driver = null;

    switch (browser) {
      case CHROME:
        ChromeOptions options = new ChromeOptions();
        options.addArguments("incognito");
        driver = new ChromeDriver(options);
        break;

      case FIREFOX:
        FirefoxOptions opts = new FirefoxOptions();
        opts.addArguments("-private");
        driver = new FirefoxDriver(opts);
        break;

      case SAFARI:
        driver = new SafariDriver();
        break;
    }

    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(secondsToTimeout, TimeUnit.SECONDS);
    driver.manage().timeouts().pageLoadTimeout(secondsToTimeout, TimeUnit.SECONDS);
    driver.manage().timeouts().setScriptTimeout(secondsToTimeout, TimeUnit.SECONDS);

    return driver;
  }

  private void Google_Signout(WebDriver driver) {
    try {
      WebElement user = (new WebDriverWait(driver, secondsToTimeout)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("*[class^='gb_2a']")));
      user.click();

      if (debug)
        System.out.println("get sign out button");

      WebElement signOut = (new WebDriverWait(driver, secondsToTimeout)).until(ExpectedConditions.presenceOfElementLocated(By.id("gb_71")));
      signOut.click();
    }
    catch (Exception ex) {
      if (debug)
        System.out.println("exception thrown signing out");
    }
  }

  private WebElement Locate(WebDriver driver, LocateType lt, String search, String message) {
    WebElement found = null;
    if (debug)
      System.out.println("find " + message + " using " + lt + " " + search);
    switch (lt) {
      case XPATH:
        for (int i = 0; i < numberRetriesFindElement; i++) {
          try {
            Thread.sleep(500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          try {
            found = driver.findElement(By.xpath(search));
            break;
          } catch (Exception ex) {
            System.out.println(ex.getMessage());
          }
        }
        break;
      case ID:
        for (int i = 0; i < numberRetriesFindElement; i++) {
          try {
            Thread.sleep(500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          try {
            found = driver.findElement(By.id(search));
            break;
          } catch (Exception ex) {
            System.out.println(ex.getMessage());
          }
        }
        break;
      case NAME:
        for (int i = 0; i < numberRetriesFindElement; i++) {
          try {
            Thread.sleep(500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          try {
            found = driver.findElement(By.name(search));
            break;
          } catch (Exception ex) {
            System.out.println(ex.getMessage());
          }
        }
        break;
      case LINK_TEXT:
        for (int i = 0; i < numberRetriesFindElement; i++) {
          try {
            Thread.sleep(500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          try {
            found = driver.findElement(By.linkText(search));
            break;
          } catch (Exception ex) {
            System.out.println(ex.getMessage());
          }
        }
        break;
      case CSS_SELECTOR:
        for (int i = 0; i < numberRetriesFindElement; i++) {
          try {
            Thread.sleep(500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          try {
            found = driver.findElement(By.cssSelector(search));
            break;
          } catch (Exception ex) {
            System.out.println(ex.getMessage());
          }
        }
        break;
      case CLASS_NAME:
        for (int i = 0; i < numberRetriesFindElement; i++) {
          try {
            Thread.sleep(500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          try {
            found = driver.findElement(By.className(search));
            break;
          } catch (Exception ex) {
            System.out.println(ex.getMessage());
          }
        }
        break;
      default:
        found = null;
    }
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return found;
  }

  private void Yahoo_Signout(WebDriver driver) {
    try {
      WebElement user = driver.findElement(By.cssSelector("*[class^='uh-menu-btn']"));
      user.click();

      WebElement signOut = driver.findElement(By.id("uh-signout"));
      signOut.click();
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    catch (Exception ex) {

    }
  }
  //</editor-fold>
}
