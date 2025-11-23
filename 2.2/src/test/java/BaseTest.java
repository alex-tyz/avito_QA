import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        File profile = new File("C:/temp/chrome-profile-test");
        profile.mkdirs();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-first-run");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--disable-features=ChromeCleanup");
        options.addArguments("--user-data-dir=C:/temp/chrome-profile-test");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://avito-tech-internship-psi.vercel.app/");
    }
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
