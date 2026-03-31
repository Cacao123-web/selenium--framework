package test;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SampleTest extends BaseTest {

    @Test
    public void testGoogle1(){

        getDriver().get("https://www.google.com");

        Assert.assertEquals("A","B"); // cố tình fail để chụp screenshot
    }

    @Test
    public void testGoogle2(){

        getDriver().get("https://www.google.com");
    }

    @Test
    public void testGoogle3(){

        getDriver().get("https://www.google.com");
    }
}