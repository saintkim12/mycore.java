package io.github.saintkim12.mycore.number;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.github.saintkim12.mycore.number.helper.NumberHelper;

public class NumberHelperTest {
  
  @Test
  public void testNumberHelperUtil() {
    assertEquals(Double.valueOf(3.14d), NumberHelper.getInstance().round(Math.PI, 2));
    assertEquals(Double.valueOf(3200d), NumberHelper.getInstance().round(3210, -2));
    assertEquals(Double.valueOf(4000d), NumberHelper.getInstance().round(3600, -3));
  }
  @Test
  public void testNumberHelperParse() {
    assertEquals(Integer.valueOf(3), NumberHelper.getInstance().tryParseInteger(Math.PI));
  }
}
