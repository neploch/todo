package todo;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import java.text.SimpleDateFormat;
import java.util.Date;
import static junit.framework.TestCase.fail;

public class todo_test{

    public todomvc todo;
    WebDriver driver;
    public static ExtentReports extent;
    public static ExtentTest test;

    @Before
    public void setup()
    {
        //  Create WebDriver instance
        //  Create todomvc class instance
        //  Create report file with timestamp in the name
        //  Open the application web page
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Eliyahu\\Downloads\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
        extent = new ExtentReports("C:\\Users\\Eliyahu\\Downloads\\FB\\todoExam\\target\\reports\\ExecutionReport_"+timestamp+".html",false);
        todo = new todomvc(driver);
        todo.get_home(driver);

    }

    @Test
    public void test_counter()
    {
        try {
            test = extent.startTest("test_counter", "Validates todo counter");
            // Validate regular functionality of counter by adding/removing to-do
            // Validate counter is decreased by marking active to-do as completed and by removing active to-do
            // Validate counter is not affected by clearing or removing completed to-do
            // Validate filtering displayed to-do by completed only / active only / all do not affect the counter
            for (int i = 0; i < 10; i++) {
                todo.createTodo(Integer.toString(i));
                todo.validateCounter(Integer.toString(i + 1));
            }
            test.log(LogStatus.PASS, "10 todo created");
            todo.markAsCompleted("3");
            test.log(LogStatus.PASS, "mark as complete decreasing counter");
            todo.presentActiveTodos();
            todo.validateCounter("9");
            todo.presentCompletedTodos();
            todo.validateCounter("9");
            todo.presentAllTodos();
            todo.validateCounter("9");
            todo.clearCompleted();
            todo.validateCounter("9");
            todo.toggleAll();
            test.log(LogStatus.PASS, "counter presents the same data for different display filtration");
            todo.validateCounter("0");
            todo.toggleAll();
            todo.validateCounter("9");
            test.log(LogStatus.PASS, "counter works correct in case of toggling the todo");
            todo.removeTodo("5");
            todo.validateCounter("8");
            test.log(LogStatus.PASS, "counter works correct in case todo deleted");
            todo.markAsCompleted("6");
            todo.validateCounter("7");
            todo.removeTodo("6");
            todo.validateCounter("7");
            test.log(LogStatus.PASS, "removing completed todo have not affect on counter");
        }
        catch (Exception exp)
        {
            fail("Test Failed, See Error in Report");
        }
        finally
        {
            extent.endTest(test);
        }
    }

    @Test
    public void test_create_todo()
    {
        try {
            test = extent.startTest("test_create_todo", "Validates todo creation");
            // Validate creating to-do containing English and 0B80-0BFF ASCII(Hebrew and Tamil) letters, digits and special signs
            // validate inserting an empty (space, tab etc) to-do is ignored
            todo.createTodo("aZ8%");
            todo.validateCounter("1");
            todo.validateTodo("aZ8%");
            test.log(LogStatus.PASS, "creating English/ASCII text todo");
            todo.createTodo("שלום חבר");
            todo.validateCounter("2");
            todo.validateTodo("שלום חבר");
            test.log(LogStatus.PASS, "creating Hebrew text todo");
            todo.createTodo("שלום חבר");
            todo.validateCounter("3");
            test.log(LogStatus.PASS, "creating todo with already existing text");
            todo.validateTodo("שלום חבר");
            todo.createTodo("வணக்கம்");
            todo.validateCounter("4");
            todo.validateTodo("வணக்கம்");
            test.log(LogStatus.PASS, "creating Tamil text todo");
            todo.createTodo("ÇÅ▒ΩδΘ");
            todo.validateCounter("5");
            todo.validateTodo("ÇÅ▒ΩδΘ");
            test.log(LogStatus.PASS, "creating special signs text todo");
            todo.createTodo("   ");
            todo.refreshTab();
            todo.validateCounter("5");
            todo.validateNoTodo("   ");
            test.log(LogStatus.PASS, "creating empty text todo ignored");
        }
        catch (Exception exp)
        {
            fail("Test Failed, See Error in Report");
        }
        finally
        {
            extent.endTest(test);
        }
    }
    @Test
    public void test_edit_todo()
    {
        try {
            test = extent.startTest("test_edit_todo", "Validates todo editing");
        // Validate editing a to-do
        // Validate changing to-do text to empty (spaces, tab etc) actually deletes the to-do
        todo.createTodo("_u+");
        todo.validateCounter("1");
        todo.validateTodo("_u+");
        todo.createTodo("aP7");
        todo.validateCounter("2");
        todo.validateTodo("aP7");
        todo.editTodo("_u+","Hello!");
        todo.validateCounter("2");
        todo.validateTodo("Hello!");
        test.log(LogStatus.PASS, "editing todo");
        todo.editTodo("aP7","  ");
        todo.validateCounter("1");
        todo.validateNoTodo("aP7");
        test.log(LogStatus.PASS, "editing todo replacing text by empty text deletes todo");
        }
        catch (Exception exp)
        {
            fail("Test Failed, See Error in Report");
        }
        finally
        {
            extent.endTest(test);
        }
    }
    @Test
    public void test_filters()
    {
        try {
            test = extent.startTest("test_filters", "Validates todo display filtering");
        // Validate displayed to-do filtering: All/Active/Completed
        todo.createTodo("▼◙");
        todo.validateTodo("▼◙");
        todo.createTodo("↔←");
        todo.validateTodo("↔←");
        todo.markAsCompleted("↔←");
        todo.presentAllTodos();
        todo.validateTodo("▼◙");
        todo.validateTodo("↔←");
        test.log(LogStatus.PASS, "All todo filtering");
        todo.presentActiveTodos();
        todo.validateTodo("▼◙");
        todo.validateNoTodo("↔←");
        test.log(LogStatus.PASS, "Active todo filtering");
        todo.presentCompletedTodos();
        todo.validateNoTodo("▼◙");
        todo.validateTodo("↔←");
        test.log(LogStatus.PASS, "Completed todo filtering");
        }
        catch (Exception exp)
        {
            fail("Test Failed, See Error in Report");
        }
        finally
        {
            extent.endTest(test);
        }
    }

    @After
    public void quit()
    {
        extent.flush();
        extent.close();
        driver.quit();
    }
}