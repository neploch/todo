# General
This short Java Selenium WebDriver based project implements several simple UI tests for http://todomvc.com/examples/react/#/ todo managing application.
Tests run by jUnit, reports created by ExtentTest, console logs implemented by jUnit.Logger, the project managed with Maven.

# Instructions
This project in build and run on Windows based machine.
1) To run this project you first need to download this project from GitHub https://github.com/neploch/todo repository.
2) In case you do not have chromedriver.exe file on your computer you can download it from http://chromedriver.chromium.org/downloads
Choose the latest version folder, select and download chromedriver_win32.zip for Windows OS based machine
Unzip it.
3) Unzip the downloaded project files and open them on your computer
4) Change 2 paths in todo_test.java file:
line 27:
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Eliyahu\\Downloads\\chromedriver_win32\\chromedriver.exe");
update to the folder actually containing chromedriver.exe on your machine

line30:
        extent = new ExtentReports("C:\\Users\\Eliyahu\\Downloads\\FB\\todoExam\\target\\reports\\ExecutionReport_"+timestamp+".html",false);
update to the actual location of reports folder inside the project on your machine
5) Run the project with jUnit 
6) Find the run reports in reports location you defined in step 4
