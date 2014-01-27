/*
 * Copyright 2013 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.common.test;

import static org.junit.Assert.assertTrue;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BaseStepUtils {
    public final static String BASE_URL = "http://localhost:8080/";
    public final static String THIRD_PARTY_CONTEXT = "ThirdParty";
    public final static String DATA_CUSTODIAN_CONTEXT = "DataCustodian";
    public final static String DATA_CUSTODIAN_BASE_URL = BASE_URL + DATA_CUSTODIAN_CONTEXT;
    public final static String THIRD_PARTY_BASE_URL = BASE_URL + THIRD_PARTY_CONTEXT;

    public static final String USERNAME = "alan";
    public static final String PASSWORD = "koala";

    public static final String CUSTODIAN_USERNAME = "grace";
    public static final String CUSTODIAN_PASSWORD = "koala";

    protected static WebDriver driver = WebDriverSingleton.getInstance();

    public static String newLastName() {
        return "Doe" + System.currentTimeMillis();
    }

    public static String newFirstName() {
        return "John" + System.currentTimeMillis();
    }

    public static String newUsername() {
        return "User" + System.currentTimeMillis();
    }

    protected void clickLink(String linkText) {
        WebElement link = driver.findElement(By.linkText(linkText));
        link.click();
    }

    public static void clickLinkByText(String linkText) {
        WebElement link = driver.findElement(By.linkText(linkText));
        link.click();
    }

    public static void clickLinkByPartialText(String linkText) {
        WebElement link = driver.findElement(By.partialLinkText(linkText));
        link.click();
    }

    public static void clickByName(String name) {
        WebElement submit = driver.findElement(By.name(name));
        submit.click();
    }

    public static void clickByClass(String className) {
        WebElement radio = driver.findElement(By.className(className));
        radio.click();
    }

    public static void selectRadioByLabel(String labelText) {
        driver.findElement(By.xpath("//label[contains(.,'" + labelText + "')]/input")).click();
    }

    public static void fillInByName(String name, String text) {
        WebElement usernameInput = driver.findElement(By.name(name));
        usernameInput.clear();
        usernameInput.sendKeys(text);
    }

    public static void assertContains(String text) {
        assertTrue("Page source did not contain '" + text + "'", driver.getPageSource().contains(text));
    }

    public static void login(String context, String username, String password) {
        navigateTo(context, "/");
        if (driver.findElements(By.id("logout")).size() > 0) {
            logout();
        }
        WebElement loginLink = driver.findElement(By.id("login"));
        loginLink.click();
        submitLoginForm(username, password);
    }

    public static void submitLoginForm(String username, String password) {
        WebElement usernameInput = driver.findElement(By.name("j_username"));
        usernameInput.clear();
        usernameInput.sendKeys(username);
        WebElement passwordInput = driver.findElement(By.name("j_password"));
        passwordInput.clear();
        passwordInput.sendKeys(password);
        WebElement login = driver.findElement(By.name("submit"));
        login.click();
    }

    public static void logout() {
        driver.findElement(By.id("logout")).click();
    }

    public static void navigateTo(String context, String path) {
        driver.get(BASE_URL + context + path);
    }

    public static void registerUser(String context, String username, String firstName, String lastName, String password) {
        login(context, CUSTODIAN_USERNAME, CUSTODIAN_PASSWORD);

        clickLinkByText("Customer List");
        clickLinkByPartialText("Add new customer");

        assertTrue(driver.getPageSource().contains("New Retail Customer"));

        WebElement form = driver.findElement(By.name("new_customer"));

        WebElement usernameField = form.findElement(By.name("username"));
        usernameField.sendKeys(username);

        WebElement firstNameField = form.findElement(By.name("firstName"));
        firstNameField.sendKeys(firstName);

        WebElement lastNameField = form.findElement(By.name("lastName"));
        lastNameField.sendKeys(lastName);

        WebElement passwordField = form.findElement(By.name("password"));
        passwordField.sendKeys(password);

        WebElement create = form.findElement(By.name("create"));
        create.click();

        assertTrue(driver.getPageSource().contains("Retail Customers"));
    }

    public static void registerDataCustodianUser(String username, String firstName, String lastName, String password) {
        registerUser(DATA_CUSTODIAN_CONTEXT, username, firstName, lastName, password);
    }

    public static void registerThirdPartyUser(String username, String firstName, String lastName, String password) {
        registerUser(THIRD_PARTY_CONTEXT, username, firstName, lastName, password);
    }

    public static void logoutDataCustodian() {
        driver.get(DATA_CUSTODIAN_BASE_URL + "/logout.do");
    }

    public static void logoutThirdParty() {
        driver.get(THIRD_PARTY_BASE_URL + "/j_spring_security_logout");
    }

    static void openPage() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI(driver.getCurrentUrl()));
    }

    static void saveAndOpenPage() throws IOException, URISyntaxException {
        File file = new File("/tmp/cucumber.html");

        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(driver.getPageSource());
        bw.close();

        Desktop.getDesktop().browse(new URI("file:///tmp/cucumber.html"));
    }
}
