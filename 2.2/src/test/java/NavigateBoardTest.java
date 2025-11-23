import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NavigateBoardTest extends BaseTest {

    @Test
    void openBoardFromTaskCard() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h4[text()='Список задач']")
        ));
        WebElement firstTaskCard = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[contains(.,'Реализация новой галереи изображений')][contains(@class,'MuiPaper-root')]")
        ));
        firstTaskCard.click();
        WebElement toBoardLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[self::a or self::button][contains(.,'Перейти на доску') or contains(.,'ПЕРЕЙТИ НА ДОСКУ')]")
        ));
        toBoardLink.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[text()='To Do']")
        ));
    }
}
