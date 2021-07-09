package io.github.saintkim12.mycore.json;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;

import io.github.saintkim12.mycore.json.model.JSONList;
import io.github.saintkim12.mycore.json.model.JSONMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JSONMapTest {

  @Test
  public void testJSONMapFrom() {
    // JSONMap m1 = new JSONMap();
    // m1.put("message", "hello");
    // assertTrue(String.format("m1: %s, m2: %s", m1, m2), true);
    JSONMap m2 = JSONMap.builder().put("message", "hello2").build();
    JSONMap m3 = JSONMap.from(m2);
    assertEquals(String.format("m2: %s, m3: %s", m2, m3), m2.get("message"), m3.get("message"));
    JSONMap m4 = JSONMap.fromJsonString("{\"message\":\"hello3\",\"result\":true}");
    assertEquals(m4.get("message"), "hello3");
  }

  @Test
  public void testJSONMapCastGet() {
    JSONMap m1 = JSONMap.builder().put("message", "hello3").put("KEY_FOR_NULL", null).build();
    // castGet 테스트
    assertEquals(m1.<String>castGet("message"), "hello3");
    // castGet의 클래스 확인
    assertEquals(
        String.format("%s, m1.class: %s", m1,
            Optional.ofNullable(m1.<String>castGet("message")).map(Object::getClass).orElse(null)),
        Optional.ofNullable(m1.<String>castGet("message")).map(Object::getClass).orElse(null), "hello3".getClass());
    // 존재하지 않는 키의 castGet 결과 확인
    assertEquals(m1.<String>castGet("NOT_EXISTS_KEY", "defaultValue"), "defaultValue");
    // 값이 null일 때 castGet 결과 확인
    assertEquals(m1.<String>castGet("KEY_FOR_NULL", "defaultValue"), null);
    // 값이 null일 때 castGetExceptsNull 결과 확인
    assertEquals(m1.<Integer>castGetExceptsNull("KEY_FOR_NULL", 1), Integer.valueOf(1));

  }

  @Test
  public void testJSONMapCastGetList() {
    JSONMap m2 = JSONMap.fromJsonString(
        "{\"message\":\"hello3\",\"result\":true,\"primitiveList\":[1,2,5],\"list\":[{\"value\":\"a\"},{\"value\":\"b\"},{\"value\":\"C\"}]}");
    log.debug("m2: {}", m2.get("list"));
    assertEquals(m2.<List<Object>>castGet("primitiveList"), Arrays.asList(1, 2, 5));
    assertArrayEquals(m2.<JSONList>castGet("list").toArray(),
        Arrays.asList(JSONMap.of("value", "a"), JSONMap.of("value", "b"), JSONMap.of("value", "C")).toArray());
    assertEquals(m2.<JSONList>castGet("list").toString(), JSONList
        .from(Arrays.asList(JSONMap.of("value", "a"), JSONMap.of("value", "b"), JSONMap.of("value", "C"))).toString());
  }

  @Test
  public void testJSONMapCastJavaCollection() {
    Map<String, Object> javaMap = new HashMap<>();
    // javaMap.put("message")
  }

}
