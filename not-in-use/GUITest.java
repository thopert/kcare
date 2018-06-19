package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.Main;
import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.repositories.*;
import at.qe.sepm.skeleton.services.*;
import at.qe.sepm.skeleton.ui.beans.*;

import org.junit.Assume;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.rules.ExpectedException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.context.embedded.LocalServerPort;

import java.nio.file.Paths;

// http://memorynotfound.com/selenium-chrome-test-cases-junit-java

/**
 * GUI-Tests.
 * @author
 */
// create a spring-application-instance on a random port
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class GUITest {

    @LocalServerPort
    private int port;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Autowired
    SupervisorRepository supervisorRepository;

    @Autowired
    SupervisorService supervisorService;

    // driver to run selenium tests
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        
        String browser = System.getProperty("browser");
        if (browser != null)
            browser = browser.toLowerCase();
        String osextension = "-linux";
        String ossep = "/";
        String osname = System.getProperty("os.name");
        if (osname.contains("Mac")) {
            osextension = "-mac";
        } else if (osname.contains("Windows")) {
            osextension = ".exe";
            ossep = "\\";
        }
        System.setProperty("webdriver.chrome.driver", Paths.get("chromedriver" + ossep + "chromedriver" + osextension).toAbsolutePath().toString());
        System.setProperty("webdriver.gecko.driver", Paths.get("geckodriver" + ossep + "geckodriver" + osextension).toAbsolutePath().toString());
//System.setProperty("webdriver.opera.driver", Paths.get("chromedriver/chromedriver-mac").toAbsolutePath().toString());
        System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
        System.setProperty("phantomjs.binary.path", Paths.get("phantomjsdriver" + ossep + "phantomjs" + osextension).toAbsolutePath().toString());
        DesiredCapabilities dcap = new DesiredCapabilities();
        String[] phantomArgs = new  String[] {"--webdriver-loglevel=NONE"};
        dcap.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomArgs);
        if (browser == null || browser.compareTo("phantomjs") == 0)
            driver = new PhantomJSDriver(dcap);
        else if (browser.compareTo("firefox") == 0)
            driver = new FirefoxDriver();
        else if (browser.compareTo("chrome") == 0)
            driver = new ChromeDriver();
        else if (browser.compareTo("safari") == 0)
            driver = new SafariDriver();
        else
            Assert.fail("Error: Invalid browser given: '" + browser + "'!");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//        driver.get("http://www.google.at/");
    }

    @AfterClass
    public static void cleanUp() {
        driver.close();
        // some drivers don't use it
        try {
            driver.quit();
        } catch (Exception e) {}
    }

    // returns a web-element by the given css-selector
    public By select(String selector) {
        return By.cssSelector(selector);
    }

    // returns a web-element by the given tag, attribute with given attribute-value
    public By select(String tag, String attr, String attrval) {
        return By.cssSelector(tag + "[" + attr + "='" + attrval + "']");
    }

    // wait for url to be given url at most timeout seconds
    public void waitURL(String url, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.urlToBe("http://localhost:" + port + "/" + url));
    }

    // wait for url to be given url
    public void waitURL(String url) {
        waitURL(url, 10);
    }

    // wait for given element
    public void wait(By ele, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ele));
    }

    // wait for given element
    public void wait(By ele) {
        wait(ele, 10);
    }

    // wait for given css-selector
    public void wait(String sel) {
        wait(select(sel));
    }

    // wait for element of given tag-name, given attribute with given attrval at most timeout seconds
    public void wait(String tag, String attr, String attrval, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//" + tag + "[@" + attr + "='" + attrval + "']")));
    }

    // wait for element of given tag-name, given attribute with given attrval
    public void wait(String tag, String attr, String attrval) {
        wait(tag, attr, attrval, 10);
    }

    // wait for element of given tag-name, containing text at most timeout seconds
    public void wait(String tag, String text, int timeout) {
        WebDriverWait w = new WebDriverWait(driver, 10);
        w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//" + tag + "[.='" + text + "']")));
    }

    // wait for element of given tag-name, containing text
    public void wait(String tag, String text) {
        wait(tag, text, 10);
    }

    // wait for given seconds
    // (though it isn't good practice, it works!)
    public void wait(int secs) {
        try {
            Thread.sleep(secs * 1000);
        } catch (InterruptedException e) {}
    }

    // wait for clickability for element with given tag-name, attribute with attribute-value at most timeout seconds
    public void waitClick(String tag, String attr, String attrval, int timeout) {
        WebDriverWait w = new WebDriverWait(driver, timeout);
        w.until(ExpectedConditions.elementToBeClickable(By.xpath("//" + tag + "[@" + attr + "='" + attrval + "']")));
    }

    // wait for clickability for element with given tag-name, attribute with attribute-value
    public void waitClick(String tag, String attr, String attrval) {
        waitClick(tag, attr, attrval, 10);
    }

    // navigate to given url
    public void nav(String url) {
        driver.get("http://localhost:" + port + "/" + url);
    }

    // find an element with given tag-name, given attribute with given attribute-value, containing given text
    public void find(String tag, String attr, String attrval) {
        wait(tag, attr, attrval);
        WebElement ele = driver.findElement(By.xpath("//" + tag + "[@" + attr + "='" + attrval + "']"));
        Assert.assertNotNull("Error: Unable to find tag " + tag + " with attribute: '" + attr + "' with value '" + attrval + "'!", ele);
    }

    // find an element with given tag-name, given attribute with given attribute-value
    public void find(String tag, String text) {
        wait(tag, text);
        WebElement ele = driver.findElement(By.xpath("//" + tag + "[.='" + text + "']"));
        Assert.assertNotNull("Error: Unable to find tag " + tag + " with text: '" + text + "'!", ele);
    }

    // don't find element with given tag-name, containing text
/*    public void notfind(String tag, String text) {
        try {
            driver.findElement(By.xpath("//" + tag + "[.='" + text + "']"));
            Assert.assertFalse("Found <" + tag + ">" + text + "</" + tag + ">, which shouldn't exist!", true);
        } catch (NoSuchElementException e) {}
    }*/

    // clear element with given tag-name, given attribute with given attribute-value
    public void clear(String tag, String attr, String attrval) {
        WebElement ele = driver.findElement(By.xpath("//" + tag + "[@" + attr + "='" + attrval + "']"));
        ele.clear();
    }

    // enter text at element with given tag-name, given attribute with given attribute-value
    public void enter(String tag, String attr, String attrval, String text) {
        wait(tag, attr, attrval);
        clear(tag, attr, attrval);
        WebElement ele = driver.findElement(By.xpath("//" + tag + "[@" + attr + "='" + attrval + "']"));
        ele.sendKeys(text);
    }

    // click on given element
    public void click(By ele) {
        wait(ele);
        driver.findElement(ele).click();
    }

    // click on given css-selected element
    public void click(String sel) {
        click(select(sel));
    }

    // click on element with given tag-name, given attribute with given attribute-value
    public void click(String tag, String attr, String attrval) {
        waitClick(tag, attr, attrval);
        WebElement btn = driver.findElement(By.xpath("//" + tag + "[@" + attr + "='" + attrval + "']"));
        btn.click();
        // wait for expected url when clicking on a link
        if (tag.compareTo("a") == 0 && attr.compareTo("href") == 0)
            waitURL(attrval.replaceAll("^/", ""));
    }

    // click on element with given tagname and containing text
    public void click(String tag, String text) {
        WebElement btn = driver.findElement(By.xpath("//" + tag + "[.='" + text + "']"));
        btn.click();
    }

    private Select _dropdown(String tag, String attr, String attrval) {
        wait(tag, attr, attrval);
        return new Select(driver.findElement(By.xpath("//" + tag + "[@" + attr + "='" + attrval + "']")));
    }

    // select item with index i of a dropdown with given tag, attribute and attribute-value
    public void dropdown(String tag, String attr, String attrval, int i) {
        _dropdown(tag, attr, attrval).selectByIndex(i);
    }

    // select item with value val of a dropdown with given tag, attribute and attribute-value
    public void dropdown(String tag, String attr, String attrval, String val) {
        _dropdown(tag, attr, attrval).selectByValue(val);
    }

    // presses the button of type in the first row, where a table-data equals to given value
    public void clickDataTableButton(String value, String type) {
        By by = By.xpath("//tr/td[.='" + value + "']/../td[last()]/button[@title='" + type + "']");
        wait(by);
        driver.findElement(by).click();
    }

    // press the dele-button from a data-table-entry and confirm the deletion
    public void deleteDataTableEntry(String value) {
        clickDataTableButton(value, "Delete");
        click(By.xpath("//div/div[1]/span[.='Löschen']/../../div[3]/button[1]"));
        wait(1);    // give spring some time :P
    }

    // another dirty hack to select dates
    // select a date for given id and given day
    public void date(String id, String day) {
        click("span[id='" + id + "'] button[aria-label='Show Calendar']");
        click("td", day);
    }

    // another dirty hack to select form a multi-selectable
    // select the first element of given multi-select
    public void multisel(String id) {
        click("div[id='" + id + "'] li");
    }

    // pick val and put it to right-picklist with given id
    public void picksel(String id, String val) {
        click("div[id='" + id + "'] li[data-item-label='" + val + "']");
        click("div[id='" + id + "'] .ui-picklist-button-add");
//        wait("#" + id + " .ui-picklist-buttons ~ .ui-picklist-list-wrapper li[data-item-label='" + val + "']");
    }

    public void login(String username, String password) {
        nav("login.xhtml");
        enter("input", "id", "username", username);
        enter("input", "id", "password", password);

        click("input", "type", "submit");

        waitURL("secured/welcome.xhtml");
    }

    public void welcome() {
        nav("secured/welcome.xhtml");
    }

    public void logout() {
        nav("logout");
        nav("login.xhtml");
    }

    // some spring golfing :P
    public void p() {
        wait(1);
    }

    @Test
    public void testLogins() {
        login("admin", "passwd");
        logout();
        login("user1", "passwd");
        logout();
        login("user2", "passwd");
        logout();
    }

    @Test
    public void testEditUser() {
        login("admin", "passwd");

        welcome();
        click("a", "href", "/admin/users.xhtml");
        clickDataTableButton("admin", "Edit");
        wait("label", "Bearbeitung: User[ admin ]");
        dropdown("select", "id", "editUserForm:roles", "SUPERVISOR");
        click("button", "id", "editUserForm:submit");
        wait("p", "User[ admin ] erfolgreich modifiziert!");
        p();

        welcome();
        click("a", "href", "/admin/users.xhtml");
        clickDataTableButton("admin", "Edit");
        wait("label", "Bearbeitung: User[ admin ]");
        dropdown("select", "id", "editUserForm:roles", "ADMIN");
        click("button", "id", "editUserForm:submit");
        wait("p", "User[ admin ] erfolgreich modifiziert!");
        p();

        logout();
    }

    @Test
    public void testChangeAdminsPassword() {
        login("admin", "passwd");

        welcome();
        click("a", "href", "/secured/common/editPassword.xhtml");
        enter("input", "id", "editPasswordForm:pwd1", "abc");
        enter("input", "id", "editPasswordForm:pwd2", "abc");
        click("button", "id", "editPasswordForm:submit");
        p();

        logout();

        login("admin", "abc");

        welcome();
        click("a", "href", "/secured/common/editPassword.xhtml");
        enter("input", "id", "editPasswordForm:pwd1", "passwd");
        enter("input", "id", "editPasswordForm:pwd2", "passwd");
        click("button", "id", "editPasswordForm:submit");
        p();

        logout();
        login("admin", "passwd");
        p();
        logout();
    }

    @Test
    public void testAddRemoveSupervisorAdmin() {
        login("admin", "passwd");

        // add supervisor
        welcome();
        click("a", "href", "/admin/createSupervisor.xhtml");
        enter("input", "id", "createSupervisorForm:userNameInput", "supvis");
        enter("input", "id", "createSupervisorForm:firstName", "Super");
        enter("input", "id", "createSupervisorForm:lastName", "Visor");
        click("label", "for", "createSupervisorForm:sex:0");
        enter("input", "id", "createSupervisorForm:mail", "test.mail@mail.com");
        enter("input", "id", "createSupervisorForm:phone", "11111");
        enter("input", "id", "createSupervisorForm:businessPhone", "22222");
        date("createSupervisorForm:birthDate", "20");
        click("button", "id", "createSupervisorForm:submit");
        wait("p", "Betreuer/in erfolgreich erstellt!");
        p();

        // check if supervisor was actually created
        welcome();
        click("a", "href", "/admin/listSupervisors.xhtml");
        find("td", "supvis");

        // delete supervisor
        deleteDataTableEntry("Visor");

        // checks if supervisor was removed
        nav("admin/listSupervisors.xhtml");
        wait("h2", "Betreuer:");

        logout();
    }

    // as a supervisor:
    // view child-list, view details of first child, edits first child, checks for edited data, removes a child, checks if child was removed
    @Test
    public void testSupervisorsChildOperations() {
        login("user1", "passwd");

        // add child
        welcome();
        click("a", "href", "/secured/supervisor/dataManagement/createChild.xhtml");
        wait("td", "Erstellung: Kind");
        enter("input", "id", "createChildForm:firstName", "Braves");
        enter("input", "id", "createChildForm:lastName", "Mädchen");
        click("label", "for", "createChildForm:sex:0");
        date("createChildForm:birthDate", "20");
        date("createChildForm:cancellationDate", "21");
        click("button", "id", "createChildForm:submit");
        wait("p", "Kind erfolgreich erstellt!");
        p();

        // view child-list
        welcome();
        click("a", "href", "/secured/supervisor/dataManagement/listChildren.xhtml");
        wait("h2", "Kinder:");
        find("td", "Braves");
        find("td", "Mädchen");
        clickDataTableButton("Braves", "View");

        // check if data is correct
        wait("td", "Personaldaten: Braves Mädchen");
        find("label", "Braves");
        find("label", "Mädchen");
        find("label", "weiblich");
        click("button", "id", "detailsChildForm:cancel");

        // edits new child
        wait("h2", "Kinder:");
        clickDataTableButton("Braves", "Edit");
        wait("td", "Bearbeitung: Braves Mädchen");
        enter("input", "id", "editChildForm:firstName", "Böses");
        enter("textarea", "id", "editChildForm:notes", "{{anmerkung}}");
        click("button", "id", "editChildForm:submit");
        wait("p", "Böses Mädchen erfolgreich modifiziert!");
        p();

        // checks for edited data
        welcome();
        click("a", "href", "/secured/supervisor/dataManagement/listChildren.xhtml");
        wait("h2", "Kinder:");
        find("td", "Böses");
        clickDataTableButton("Böses", "View");
        wait("td", "Personaldaten: Böses Mädchen");
        find("label", "Böses");
        find("label", "{{anmerkung}}");
        click("button", "id", "detailsChildForm:cancel");

        // remove new child
        wait("h2", "Kinder:");
        deleteDataTableEntry("Böses");

        // checks if child was removed
        welcome();
        click("a", "href", "/secured/supervisor/dataManagement/listChildren.xhtml");
        wait("h2", "Kinder:");
        //notfind("td", "Böses");
        //notfind("td", "Mädchen");

        logout();
    }

    @Test
    public void testSupervisorsParentOperations() {
        login("user1", "passwd");

        // add child
        welcome();
        click("a", "Kind erstellen");
        wait("td", "Erstellung: Kind");
        enter("input", "id", "createChildForm:firstName", "Braves");
        enter("input", "id", "createChildForm:lastName", "Mädchen");
        click("label", "for", "createChildForm:sex:0");
        date("createChildForm:birthDate", "20");
        date("createChildForm:cancellationDate", "21");
        click("button", "id", "createChildForm:submit");
        wait("p", "Kind erfolgreich erstellt!");
        p();

        // add new parent-account
        welcome();
        click("a", "href", "/secured/supervisor/dataManagement/createParent.xhtml");
        enter("input", "id", "createParentForm:userNameInput", "parenti");

        enter("input", "id", "createParentForm:firstNameMother", "Tolle");
        enter("input", "id", "createParentForm:lastNameMother", "Mutter");
        enter("input", "id", "createParentForm:mailMother", "mother123@mail.com");
        enter("input", "id", "createParentForm:phoneMother", "123456543");
        enter("input", "id", "createParentForm:businessPhoneMother", "62382384234");
        date("createParentForm:birthDateMother", "20");

        enter("input", "id", "createParentForm:firstNameFather", "Bester");
        enter("input", "id", "createParentForm:lastNameFather", "Vater");
        enter("input", "id", "createParentForm:mailFather", "vater123@mail.com");
        enter("input", "id", "createParentForm:phoneFather", "123232356543");
        enter("input", "id", "createParentForm:businessPhoneFather", "621232344234");
        date("createParentForm:birthDateFather", "21");
        multisel("createParentForm:children");
        click("button", "id", "createParentForm:submit");
        wait("p", "Eltern erfolgreich erstellt!");
        p();

        // view parent-list
        welcome();
        click("a", "Eltern verwalten");
        wait("h2", "Eltern:");
        find("td", "Tolle Mutter");
        find("td", "Bester Vater");

        // edit parents
        clickDataTableButton("Tolle Mutter", "Edit");
        wait("label", "Bearbeitung: Tolle Mutter/Bester Vater");
        enter("input", "id", "editParentForm:mailMother", "mother@mom.com");
        enter("input", "id", "editParentForm:businessPhoneFather", "888777666");
        click("button", "id", "editParentForm:submit");
        wait("p", "Personaldaten erfolgreich modifiziert!");
        p();

        // remove new child
        welcome();
        click("a", "Kinder verwalten");
        wait("h2", "Kinder:");
        deleteDataTableEntry("Braves");

        // remove parents
        welcome();
        wait("h1", "Betreuer - Hauptmenü");
        click("a", "Eltern verwalten");
        wait("h2", "Eltern:");
        deleteDataTableEntry("Tolle Mutter");

        // checks if child was removed
        welcome();
        click("a", "Eltern verwalten");
        wait("h2", "Eltern:");

        logout();
    }

    @Test
    public void testSupervisorsContactOperations() {
        login("user1", "passwd");

        // edit contact
        welcome();
        click("a", "href", "/secured/supervisor/dataManagement/listContacts.xhtml");
        wait("h2", "Bezugspersonen:");
        clickDataTableButton("Derlohn", "Edit");
        wait("td", "Bearbeitung: Finn Derlohn");
        enter("input", "id", "editContactForm:firstName", "Paul");
        enter("input", "id", "editContactForm:lastName", "Panzer");
        date("editContactForm:birthDate", "20");

        click("label", "for", "editContactForm:verification:1");
        click("button", "id", "editContactForm:submit");
        wait("p", "Paul Panzer erfolgreich modifiziert!");
        p();

        // check edited data
        welcome();
        click("a", "href", "/secured/supervisor/dataManagement/listContacts.xhtml");
        wait("h2", "Bezugspersonen:");
        find("td", "Paul");
        find("td", "Panzer");

        // reset data
        clickDataTableButton("Panzer", "Edit");
        wait("td", "Bearbeitung: Paul Panzer");
        enter("input", "id", "editContactForm:firstName", "Finn");
        enter("input", "id", "editContactForm:lastName", "Derlohn");
        click("label", "for", "editContactForm:verification:0");
        click("button", "id", "editContactForm:submit");
        wait("p", "Finn Derlohn erfolgreich modifiziert!");
        p();

        // check restted data
        welcome();
        click("a", "href", "/secured/supervisor/dataManagement/listContacts.xhtml");
        wait("h2", "Bezugspersonen:");
        find("td", "Finn");
        find("td", "Derlohn");
        //notfind("td", "Paul");

        logout();
    }

    @Test
    public void testSupervisorsScheduleOperations() {
        login("user1", "passwd");

        // add schedule
        welcome();
        wait("h1", "Betreuer - Hauptmenü");
        click("a", "href", "/secured/supervisor/schedule/setupSchedule.xhtml");
        enter("input", "id", "setupScheduleForm:maxChildren_input", "15");
        click("a", "1");
        p();
        picksel("setupScheduleForm:plChild", "Anna Log");
        picksel("setupScheduleForm:plSupervisor", "Frank Reich");
        p();
        click("button", "id", "setupScheduleForm:submit");
        wait("p", "Schedule erfolgreich modifiziert!");
        p();

        // check for new schedule
        welcome();
        wait("h1", "Betreuer - Hauptmenü");
        click("a", "href", "/secured/supervisor/schedule/schedule.xhtml");
        wait("h2", "Tagesplan:");
        date("scheduleForm:calendar", "1");
        p();
        find("td", "Anna Log");

        logout();
    }

    @Test
    public void testSupervisorsJobOperations() {
        login("user1", "passwd");

        // add job
        welcome();
        wait("h1", "Betreuer - Hauptmenü");
        click("a", "href", "/secured/supervisor/dataManagement/createJob.xhtml");
        wait("td", "Erstellung: Job");
        enter("input", "id", "createJobForm:title", "Atmen");
        enter("textarea", "id", "createJobForm:description", "5x Einatmen, 6x Ausatmen!");
        multisel("createJobForm:parent");
        date("createJobForm:startDate", "20");
        date("createJobForm:endDate", "21");
        enter("input", "id", "createJobForm:daysBefore_input", "7");
        click("button", "id", "createJobForm:submit");
        wait("p", "Job erfolgreich erstellt!");
        p();

        // check new job
        welcome();
        wait("h1", "Betreuer - Hauptmenü");
        click("a", "href", "/secured/supervisor/dataManagement/listJobs.xhtml");
        wait("h2", "Jobs:");
        find("td", "Atmen");

        logout();
    }

/*    @Test
    public void testSupervisorsLunchReport() {
        login("user1", "passwd");

        welcome();
        wait("h1", "Betreuer - Hauptmenü");
        click("a", "href", "/secured/supervisor/services/lunchReport.xhtml");
        find("td", "Ismir");
        find("td", "Schnuppe");

        logout();
    }*/

    @Test
    public void testSupervisorsEditPassword() {
        login("user1", "passwd");
        click("a", "href", "/secured/common/editPassword.xhtml");
        wait("input", "id", "editPasswordForm:pwd1");
        enter("input", "id", "editPasswordForm:pwd1", "abc");
        enter("input", "id", "editPasswordForm:pwd2", "abc");
        click("button", "id", "editPasswordForm:submit");
        p();

        welcome();
        logout();

        login("user1", "abc");
        click("a", "href", "/secured/common/editPassword.xhtml");
        wait("input", "id", "editPasswordForm:pwd1");
        enter("input", "id", "editPasswordForm:pwd1", "passwd");
        enter("input", "id", "editPasswordForm:pwd2", "passwd");
        click("button", "id", "editPasswordForm:submit");
        p();

        welcome();
        logout();
    }

    @Test
    public void testParentsContacts() {
        login("user2", "passwd");

        // add contact
        welcome();
        click("a", "href", "/secured/parent/createContact.xhtml");
        enter("input", "id", "createContactForm:firstName", "ZBezugs");
        enter("input", "id", "createContactForm:lastName", "Person");
        click("label", "for", "createContactForm:sex:0");
        enter("input", "id", "createContactForm:mail", "xD@lol.com");
        enter("input", "id", "createContactForm:phone", "65377000000");
        enter("input", "id", "createContactForm:businessPhone", "65377000001");
        date("createContactForm:birthDate", "20");
        click("button", "id", "createContactForm:submit");
        wait("p", "Bezugsperson erfolgreich erstellt!");
        p();

        welcome();

        // check new contact
        click("a", "href", "/secured/supervisor/dataManagement/listContacts.xhtml");
        find("td", "ZBezugs");
        find("td", "Person");

        // remove new contact
        deleteDataTableEntry("ZBezugs");
        wait("p", "ZBezugs Person wurde erfolgreich gelöscht!");
        p();

        // check if contact removed
        welcome();
        click("a", "href", "/secured/supervisor/dataManagement/listContacts.xhtml");
        //notfind("td", "ZBezugs");
        //notfind("td", "Person");

        logout();
    }

    @Test
    public void testParentsAbsentChildActions() {
        login("user2", "passwd");

        // set first child as absent for today and undo it
        welcome();
        click("a", "href", "/secured/parent/setAbsence.xhtml");
        click("button", "id", "setAbsenceForm:submit");

        welcome();
        click("a", "href", "/secured/parent/setAbsence.xhtml");
        find("label", "JA");

        click("button", "id", "setAbsenceForm:delete");

        welcome();
        click("a", "href", "/secured/parent/setAbsence.xhtml");
        find("label", "NEIN");

        logout();
    }

    @Test
    public void testParentsContactPickupActions() {
        login("user2", "passwd");

        // set first child as absent for today and undo it
        welcome();
        click("a", "href", "/secured/parent/setPickup.xhtml");
        click("button", "id", "setPickupForm:submit");

        welcome();
        click("a", "href", "/secured/parent/setPickup.xhtml");
        wait("td", "Meldung: Abholperson");
        find("label", "Ali Gator");

        click("button", "id", "setPickupForm:delete");

        welcome();
        click("a", "href", "/secured/parent/setPickup.xhtml");
        wait("td", "Meldung: Abholperson");
        find("label", "NEIN");

        logout();
    }

    @Test
    public void testParentsLunchActions() {
        login("user2", "passwd");

        // set first child as absent for today and undo it
        welcome();
        click("a", "href", "/secured/parent/setLunch.xhtml");
        click("button", "id", "setLunchForm:submit");

        welcome();
        click("a", "href", "/secured/parent/setLunch.xhtml");
        wait("td", "Meldung: Mittagessen");
        find("label", "JA");

        click("button", "id", "setLunchForm:remove");
        welcome();
        click("a", "href", "/secured/parent/setLunch.xhtml");
        wait("td", "Meldung: Mittagessen");
        find("label", "NEIN");

        logout();
    }

    @Test
    public void testParentsChildActions() {
        login("user2", "passwd");

        // edit first child of person
        welcome();
        click("a", "href", "/secured/supervisor/dataManagement/listChildren.xhtml");
        clickDataTableButton("Saurier", "Edit");
        waitURL("secured/supervisor/dataManagement/listChildren.xhtml");
        wait("td", "Bearbeitung: Tino Saurier");

        enter("input", "id", "editChildForm:firstName", "Raine");
        enter("input", "id", "editChildForm:lastName", "Absicht");
        click("label", "for", "editChildForm:sex:0");
        enter("textarea", "id", "editChildForm:notes", "Wie style ist das denn?");
        click("button", "id", "editChildForm:submit");
        wait("p", "Raine Absicht erfolgreich modifiziert!");
        p();

        // check edited data
        welcome();
        click("a", "href", "/secured/supervisor/dataManagement/listChildren.xhtml");
        wait("h2", "Kinder:");
        find("td", "Raine");
        find("td", "Absicht");
        //notfind("td", "Zufall");

        // view child data
        clickDataTableButton("Absicht", "View");
        wait("td", "Personaldaten: Raine Absicht");
        find("label", "Raine");
        find("label", "Absicht");
        find("label", "Wie style ist das denn?");

        // reset child data
        welcome();
        click("a", "href", "/secured/supervisor/dataManagement/listChildren.xhtml");
        clickDataTableButton("Absicht", "Edit");
        waitURL("secured/supervisor/dataManagement/listChildren.xhtml");
        wait("td", "Bearbeitung: Raine Absicht");
        enter("input", "id", "editChildForm:firstName", "Rainer");
        enter("input", "id", "editChildForm:lastName", "Zufall");
        click("label", "for", "editChildForm:sex:1");
        click("button", "id", "editChildForm:submit");
        wait("p", "Rainer Zufall erfolgreich modifiziert!");
        p();

        logout();
    }

    @Test
    public void testParentsSettings() {
        login("user2", "passwd");

        // navigate to settings
        welcome();
        click("a", "Einstellungen");
        p();

        // change some data
        enter("input", "id", "editParentForm:firstNameMother", "Baseball");
        enter("input", "id", "editParentForm:mailFather", "k-r222@mail.com");

        // save changed data
        click("button", "id", "editParentForm:submit");
        wait("p", "Personaldaten erfolgreich modifiziert!");
        p();

        // navigate to the menu and back to the settings
        welcome();
        click("a", "Einstellungen");
        p();

        // check if all data were modified
        find("input", "value", "Baseball");
        find("input", "value", "k-r222@mail.com");

        // reset data
        enter("input", "id", "editParentForm:firstNameMother", "Dennis");
        enter("input", "id", "editParentForm:mailFather", "kkulator@mail.com");

        // save changed data
        click("button", "id", "editParentForm:submit");
        wait("p", "Personaldaten erfolgreich modifiziert!");
        p();

        logout();
    }

}