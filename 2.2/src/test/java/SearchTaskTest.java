import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchTaskTest extends BaseTest {

    @Test
    void searchTask() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h4[text()='Список задач']")
        ));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Поиск']")
        )).sendKeys("Оптимизация");
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(.,'Оптимизация')]")
        ));
    }
}
