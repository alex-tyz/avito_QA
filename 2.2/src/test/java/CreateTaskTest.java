import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateTaskTest extends BaseTest {

    @Test
    void createTask() {

        String taskTitle = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h4[text()='Список задач']")
        ));

        WebElement createButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//header//*[self::button or self::a]" +
                        "[contains(.,'Создать задачу') or contains(.,'СОЗДАТЬ ЗАДАЧУ')]")
        ));
        createButton.click();
        WebElement titleInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[contains(.,'Название')]/following-sibling::div//input")
        ));
        titleInput.click();
        titleInput.sendKeys(taskTitle);

        WebElement descriptionInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[contains(.,'Описание')]/following-sibling::div//textarea")
        ));
        descriptionInput.click();
        descriptionInput.sendKeys("Описание для " + taskTitle);
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.TAB)
                .sendKeys(Keys.ARROW_DOWN)
                .sendKeys(Keys.ENTER)
                .perform();


        actions.sendKeys(Keys.TAB)
                .sendKeys(Keys.ARROW_DOWN)
                .sendKeys(Keys.ENTER)
                .perform();
        actions.sendKeys(Keys.TAB)
                .sendKeys(Keys.ARROW_DOWN)
                .sendKeys(Keys.ENTER)
                .perform();
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(.,'Создать') or contains(.,'СОЗДАТЬ')]")
        ));
        submitButton.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[contains(@class,'MuiDialog-paper')]")
        ));
        WebElement allTasksTab = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[self::a or self::button][contains(.,'Все задачи')]")
        ));
        allTasksTab.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h4[text()='Список задач']")
        ));
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Поиск']")
        ));
        searchInput.clear();
        searchInput.sendKeys(taskTitle);
        WebElement newTaskElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'" + taskTitle + "')]")
        ));
        assertTrue(newTaskElement.isDisplayed(),
                "Новая задача с названием " + taskTitle + " не отобразилась в списке задач");
    }
}
