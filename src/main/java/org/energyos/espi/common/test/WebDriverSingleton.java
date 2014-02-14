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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class WebDriverSingleton {
    private WebDriverSingleton() { }

    private static class SingletonHolder {

        public static HtmlUnitDriver HTML_UNIT;
        public static ChromeDriver CHROME;

        @SuppressWarnings("unused")
        // TODO: clean the code of needing this as external web testing strategy is in place
		public static ChromeDriver chrome() {
            if (CHROME == null) {
                CHROME = new ChromeDriver();
                System.out.println("********************************************************");
                System.out.println("/!\\    Hey! You're using Chrome from the Googles!    /!\\");
                System.out.println("********************************************************");
            }

            return CHROME;
        }

        public static HtmlUnitDriver htmlUnit() {
            if (HTML_UNIT == null) {
                HTML_UNIT = new HtmlUnitDriver(BrowserVersion.FIREFOX_17);
                HTML_UNIT.setJavascriptEnabled(true);
            }
            return HTML_UNIT;
        }
    }

    public static WebDriver getInstance() {
        return SingletonHolder.htmlUnit();
    }
}