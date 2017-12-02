package com.hackingman.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private String vkLogin = "89118465234";
    private String vkPassword = "Test1234567";
    private String message = "Test";

    public void sendMessage(String vkId) throws InterruptedException {

        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--headless");

        WebDriver headlessDriver = new ChromeDriver(chromeOptions);

        headlessDriver.get("https://vk.com/");

        (new WebDriverWait(headlessDriver, 100))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("index_email")));

        headlessDriver.findElement(By.id("index_email")).sendKeys(vkLogin);
        headlessDriver.findElement(By.id("index_pass")).sendKeys(vkPassword);
        headlessDriver.findElement(By.id("index_login_button")).click();

        Thread.sleep(3000);

        headlessDriver.get("https://vk.com/" + vkId);

        (new WebDriverWait(headlessDriver, 100))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("profile_message_send")));
        headlessDriver.findElement(By.id("profile_message_send")).click();
        (new WebDriverWait(headlessDriver, 100))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("mail_box_editable")));
        headlessDriver.findElement(By.id("mail_box_editable")).sendKeys(message);
        headlessDriver.findElement(By.id("mail_box_send")).click();
        Thread.sleep(2000);
        headlessDriver.close();
        headlessDriver.quit();
    }
}
