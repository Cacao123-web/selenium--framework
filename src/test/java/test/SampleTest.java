package test;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SampleTest extends BaseTest {

    @Test
    public void testGoogle1() {
        getDriver().get("https://www.google.com");
        Assert.assertTrue(getDriver().getTitle().toLowerCase().contains("google"));
    }

    @Test
    public void testGoogle2() {
        getDriver().get("https://www.google.com");
        Assert.assertTrue(getDriver().getCurrentUrl().contains("google"));
    }
}