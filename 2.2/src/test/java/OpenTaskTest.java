import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OpenTaskTest extends BaseTest {

    @Test
    void openTaskCard() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h4[text()='Список задач']")
        ));
        WebElement firstTaskCard = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[contains(.,'Реализация новой галереи изображений')][contains(@class,'MuiPaper-root')]")
        ));
        firstTaskCard.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(.,'Обновить') or contains(.,'ОБНОВИТЬ')]")
        ));
    }
}
