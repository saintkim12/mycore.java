/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package io.github.saintkim12.mycore;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.github.saintkim12.mycore.json.model.JSONMap;

public class LibraryTest {
  @Test
  public void testSomeLibraryMethod() {
    Library classUnderTest = new Library();
    assertTrue("someLibraryMethod should return 'true'", classUnderTest.someLibraryMethod());
  }
  @Test
  public void testApiCore() {
    new JSONMap();
  }
}
