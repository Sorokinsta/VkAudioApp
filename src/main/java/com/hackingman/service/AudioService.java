package com.hackingman.service;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AudioService {

    private String vkLogin = "89118465234";
    private String vkPassword = "Test1234567";
    private ChromeDriver musicDriver;

    public String[] getAudioByVkId(String vkId) throws InterruptedException {
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
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#profile_audios > a.module_header")));

        headlessDriver.findElement(By.cssSelector("#profile_audios > a.module_header")).click();



        JavascriptExecutor jse = ((JavascriptExecutor) headlessDriver);

        for (int i = 0; i < 3; i++) {
            Thread.sleep(1000);
            jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        }
        Thread.sleep(1000);

        List<WebElement> audioWrappers = headlessDriver.findElements(By.cssSelector(".audio_row_with_cover"));

        String[] audios = new String[audioWrappers.size()];

        for (int i = 0; i < audios.length; i++) {
            String wrapperClass = audioWrappers.get(i).getAttribute("class");
            audios[i] = headlessDriver.findElement(By.cssSelector("."+wrapperClass.replaceAll(" ",".") + " " + ".audio_row__title_inner._audio_row__title_inner")).getText() + " - " + headlessDriver.findElement(By.cssSelector("."+wrapperClass.replaceAll(" ",".") + " " + "a[onmouseover=\"setTitle(this)\"]")).getText();
        }

        headlessDriver.close();
        headlessDriver.quit();
        return  audios;
    }

    public void startAudio(String vkId, int number) throws InterruptedException {

        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--headless");
        musicDriver = new ChromeDriver(chromeOptions);

        musicDriver.get("https://vk.com/");

        (new WebDriverWait(musicDriver, 100))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("index_email")));

        musicDriver.findElement(By.id("index_email")).sendKeys(vkLogin);
        musicDriver.findElement(By.id("index_pass")).sendKeys(vkPassword);
        musicDriver.findElement(By.id("index_login_button")).click();

        Thread.sleep(3000);

        musicDriver.get("https://vk.com/" + vkId);

        (new WebDriverWait(musicDriver, 100))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#profile_audios > a.module_header")));

        musicDriver.findElement(By.cssSelector("#profile_audios > a.module_header")).click();

        (new WebDriverWait(musicDriver, 100))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".audio_row_with_cover")));

        Thread.sleep(3000);

        musicDriver.findElements(By.cssSelector(".audio_row_with_cover")).get(number).click();

    }

    public void stopMusic(){
        musicDriver.close();
        musicDriver.quit();
    }
}
