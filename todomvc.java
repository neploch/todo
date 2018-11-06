package todo;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.logging.Logger;


public class todomvc {
    WebDriver driver;
    WebElement element;
    By newTodo = By.className("new-todo");
    By todoCounter = By.xpath("(//span[@class='todo-count']//*)[1]");
    String HomePageURL = "http://todomvc.com/examples/react/#/";
    String todoBody = "//label[contains(text(),'%s')]";
    String toggleInput = "//label[contains(text(),'%s')]/../input";
    String removeTodo = "//label[contains(text(),'%s')]/../button";
    String markedAsCompleted = "//li[contains(@class,'completed')]//label[contains(text(),'%s')]";
    By presentActiveTodos = By.xpath("//a[contains(@href,'active')]");
    By activeTodosSelected = By.xpath("//a[contains(@href,'active') and contains(@class,'selected')]");
    By presentCompletedTodos = By.xpath("//a[contains(@href,'completed')]");
    By completedTodosSelected = By.xpath("//a[contains(@href,'completed') and contains(@class,'selected')]");
    By presentAllTodos = By.xpath("//a[@href='#/']");
    By allTodosSelected = By.xpath("//a[@href='#/' and contains(@class,'selected')]");
    By clearCompleted = By.className("clear-completed");
    By toggleAll = By.cssSelector("label[for='toggle-all']");
    String editTodo = ("//input[@class='edit' and @value='%s']");

    private static final Logger logger = Logger.getLogger("");


    public todomvc(WebDriver driver)
    {
        this.driver = driver;
    }
    public void createTodo(String todoString)
    {
        logger.info("Creating a new todo with " + todoString + " text");
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.elementToBeClickable(newTodo));
        driver.findElement(newTodo).sendKeys(todoString);
        driver.findElement(newTodo).sendKeys(Keys.ENTER);
    }
    public void editTodo(String originalText, String newText)
    {
        logger.info("Editing existing todo with " + originalText + " text to become " + newText);
        Actions action = new Actions(driver);
        String body = String.format(todoBody,originalText);
        String editField = String.format(editTodo,originalText);
        element = driver.findElement(By.xpath(body));
        action.moveToElement(element).doubleClick().build().perform();
        element = driver.findElement(By.xpath(editField));
        element.clear();
        element.sendKeys(newText);
        element.sendKeys(Keys.ENTER);
    }
    public void validateTodo(String expectedSting)
    {
        logger.info("Validating existence of todo with " + expectedSting + " text");
        WebDriverWait wait = new WebDriverWait(driver,10);
        String body = String.format(todoBody,expectedSting);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(body)));
    }
    public void validateNoTodo(String expectedSting)
    {
        logger.info("Validating no todo with " + expectedSting + " text presented");

        WebDriverWait wait = new WebDriverWait(driver,10);
        String body = String.format(todoBody,expectedSting);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(body)));
    }
    public void refreshTab()
    {
        logger.info("Refreshing the web page");

        driver.navigate().refresh();
    }
    public void validateCounter(String expectedCount)
    {
        logger.info("Validating counter presents " + expectedCount);
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(todoCounter,expectedCount));
    }
    public void markAsCompleted(String index)
    {
        logger.info("Mark todo with " + index + " text as completed");
        WebDriverWait wait = new WebDriverWait(driver,10);
        String inputToggle = String.format(toggleInput,index);
        String completedIndicator = String.format(markedAsCompleted,index);
        driver.findElement(By.xpath(inputToggle)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(completedIndicator)));
    }
    public void removeTodo(String index)
    {
        logger.info("Removing todo with " + index + " text");
        WebDriverWait wait = new WebDriverWait(driver,10);
        String body = String.format(todoBody,index);
        Actions actions = new Actions(driver);
        element = driver.findElement(By.xpath(body));
        actions.moveToElement(element).build().perform();
        String removeXpath = String.format(removeTodo,index);
        driver.findElement(By.xpath(removeXpath)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(body)));
    }
    public void presentActiveTodos()
    {
        logger.info("Filter todo list to present active todo only");
        WebDriverWait wait = new WebDriverWait(driver,10);
        driver.findElement(presentActiveTodos).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(activeTodosSelected));
    }
    public void presentCompletedTodos()
    {
        logger.info("Filter todo list to present completed todo only");
        WebDriverWait wait = new WebDriverWait(driver,10);
        driver.findElement(presentCompletedTodos).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(completedTodosSelected));
    }
    public void presentAllTodos()
    {
        logger.info("Filter todo list to present all the todo list");
        WebDriverWait wait = new WebDriverWait(driver,10);
        driver.findElement(presentAllTodos).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(allTodosSelected));
    }
    public void clearCompleted()
    {
        logger.info("Clear completed todo");
        WebDriverWait wait = new WebDriverWait(driver,10);
        driver.findElement(clearCompleted).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(clearCompleted));
    }
    public void toggleAll()
    {
        logger.info("Tggle all the todo");
        driver.findElement(toggleAll).click();
    }
    public void get_home(WebDriver driver)
    {
        logger.info("Filter todo list to present all the todo list");
        driver.get(HomePageURL);
    }
}
