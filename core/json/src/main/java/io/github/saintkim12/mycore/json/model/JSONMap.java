package io.github.saintkim12.mycore.json.model;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.saintkim12.mycore.number.helper.NumberHelper;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * JSONMap
 * 
 * @author saintkim12
 * @version 0.1.0
 * @since 0.1.0
 */
@Builder(toBuilder = false)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JSONMap extends LinkedHashMap<String, Object> {
  /* member */

  /* constructor */
  public <T extends Map<String, Object>> JSONMap(T o) {
    this.putAll(o);
  }

  /* default method */
  @Override
  public String toString() {
    try {
      return ObjectMapperInstance.getInstance().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    } catch (Exception e) {
      return super.toString();
    }
  }

  /* original method */
  /**
   * (map).get 사용 시 type cast 과정 생략을 위한 메소드
   * <p>
   * 해당 key의 값이 없는 경우 null 리턴
   * <p>
   * 타입 캐스팅 실패 시 에러 발생
   * @param key Original key of Map
   * @return casted value
   * @throws ClassCastException
   **/
  @SuppressWarnings("unchecked")
  public <T> T castGet(Object key) {
    Object value = this.get(key);
    return (T) value;
  }

  /**
   * (map).get 사용 시 type cast 과정 생략을 위한 메소드
   * <p>
   * 해당 key의 값이 없는 경우(getOrDefault) defaultValue 리턴
   * <p>
   * 타입 캐스팅 실패 시 에러 발생
   * @param key
   * @param defaultValue 해당 key의 값이 없는 경우 리턴할 기본값
   * @return casted value or default value
   * @throws ClassCastException
   **/
  @SuppressWarnings("unchecked")
  public <T> T castGet(Object key, T defaultValue) {
    Object value = this.getOrDefault(key, defaultValue);
    return (T) value;
  }

  /**
   * (map).get 사용 시 type cast 과정 생략을 위한 메소드
   * <p>
   * 타입 캐스팅 실패 시 null 리턴
   * @param key
   * @return casted value or null
   */
  public <T> T tryCastGet(Object key) {
    return this.tryCastGet(key, null);
  }

  /**
   * (map).get 사용 시 type cast 과정 생략을 위한 메소드
   * <p>
   * 타입 캐스팅 실패 시 defaultValue 리턴
   * @param key
   * @param defaultValue 해당 key의 값이 없는 경우 리턴할 기본값
   * @return casted value or default value
   */
  @SuppressWarnings("unchecked")
  public <T> T tryCastGet(Object key, T defaultValue) {
    Object value = this.get(key);
    try {
      return (T) value;
    } catch (ClassCastException e) {
      return defaultValue;
    }
  }

  /**
   * (map).get 사용 시 type cast 과정 생략을 위한 메소드
   * <p>
   * 해당 key의 값이 null이거나 값이 없는 경우 defaultValue 리턴
   * <p>
   * 타입 캐스팅 실패 시 에러 발생
   * @param key
   * @return casted value or default value
   * @throws ClassCastException
   **/
  public <T> T castGetExceptsNull(Object key, T defaultValue) {
    Optional<T> optValue = Optional.ofNullable(this.castGet(key));
    return optValue.orElse(defaultValue);
  }

  /**
   * (map).get 사용 시 type cast 과정 생략을 위한 메소드
   * <p>
   * 타입 캐스팅 실패 시, 해당 key의 값이 null이거나 값이 없는 경우 defaultValue 리턴
   * @param key
   * @return casted value or default value
   * @throws ClassCastException
   **/
  public <T> T tryCastGetExceptsNull(Object key, T defaultValue) {
    Optional<T> optValue = Optional.ofNullable(this.tryCastGet(key));
    return optValue.orElse(defaultValue);
  }

  /**
   * map의 값을 string으로 변환하여 가져오는 메소드
   * <p>
   * 타입 캐스팅 실패 시 에러 발생
   * @param key
   * @return casted value
   * @throws ClassCastException
   */
  public String castGetAsString(Object key) {
    return castGetAsString(key, null);
  }

  /**
   * map의 값을 string으로 변환하여 가져오는 메소드
   * <p>
   * 타입 캐스팅 실패 시 에러 발생
   * @param key
   * @param defaultValue 해당 key의 값이 없는 경우 리턴할 기본값
   * @return casted value or default value
   * @throws ClassCastException
   */
  public String castGetAsString(Object key, String defaultValue) {
    return Optional.ofNullable(this.<Object>castGet(key)).map(Object::toString).orElse(defaultValue);
  }

  /**
   * map의 값을 string으로 변환하여 가져오는 메소드
   * <p>
   * 타입 캐스팅 실패 시 null 리턴
   * @param key
   * @return casted value
   * @throws ClassCastException
   */
  public String tryCastGetAsString(Object key) {
    return tryCastGetAsString(key, null);
  }

  /**
   * map의 값을 string으로 변환하여 가져오는 메소드
   * <p>
   * 타입 캐스팅 실패 시, 해당 key의 값이 null이거나 값이 없는 경우 defaultValue 리턴
   * @param key
   * @param defaultValue 해당 key의 값이 없는 경우 리턴할 기본값
   * @return casted value or default value
   * @throws ClassCastException
   */
  public String tryCastGetAsString(Object key, String defaultValue) {
    try {
      String value = castGetAsString(key);
      return Optional.ofNullable(value).orElse(defaultValue);
    } catch (ClassCastException e) {
      return defaultValue;
    }
  }

  /**
   * map의 값을 Number 기반 클래스로 변환하여 가져오는 메소드
   * <p>
   * 클래스 처리를 위해 Number로부터 파생된 클래스를 넘겨주어야 함
   * <p>
   * 변환 실패 또는 타입 캐스팅 실패 시 에러 발생
   * @param key
   * @param _class 전달받을 클래스(ex> Double.class)
   * @return casted value
   * @throws NumberFormatException
   * @throws ClassCastException
   */
  public <N extends Number> N castGetAsNumber(Object key, Class<N> _class) {
    return castGetAsNumber(key, null, _class);
  }

  /**
   * map의 값을 Number 기반 클래스로 변환하여 가져오는 메소드
   * <p>
   * 클래스 처리를 위해 Number로부터 파생된 클래스를 넘겨주어야 함
   * <p>
   * 변환 실패 또는 타입 캐스팅 실패 시 에러 발생
   * @see NumberHelper#parse
   * @param key
   * @param defaultValue 해당 key의 값이 없는 경우 리턴할 기본값
   * @param _class 전달받을 클래스(ex> Double.class)
   * @return casted value
   * @throws NumberFormatException
   * @throws ClassCastException
   */
  public <N extends Number> N castGetAsNumber(Object key, N defaultValue, Class<N> _class) {
    return Optional.ofNullable(this.castGetAsString(key))
      .map(object -> NumberHelper.getInstance().parse(object, defaultValue, _class))
      .orElse(defaultValue);
  }

  /**
   * map의 값을 Number 기반 클래스로 변환하여 가져오는 메소드
   * <p>
   * 클래스 처리를 위해 Number로부터 파생된 클래스를 넘겨주어야 함
   * <p>
   * 타입 캐스팅 실패 시 null 리턴
   * @param key
   * @param _class 전달받을 클래스(ex> Double.class)
   * @return casted value
   */
  public <N extends Number> N tryCastGetAsNumber(Object key, Class<N> _class) {
    return tryCastGetAsNumber(key, null, _class);
  }

  /**
   * map의 값을 Number 기반 클래스로 변환하여 가져오는 메소드
   * <p>
   * 클래스 처리를 위해 Number로부터 파생된 클래스를 넘겨주어야 함
   * <p>
   * 변환 실패 또는 타입 캐스팅 실패 시, 해당 key의 값이 null이거나 값이 없는 경우 defaultValue 리턴
   * @param key
   * @param defaultValue 해당 key의 값이 없는 경우 리턴할 기본값
   * @param _class 전달받을 클래스(ex> Double.class)
   * @return casted value
   */
  public <N extends Number> N tryCastGetAsNumber(Object key, N defaultValue, Class<N> _class) {
    try {
      N value = castGetAsNumber(key, _class);
      return Optional.ofNullable(value).orElse(defaultValue);
    } catch (NumberFormatException | ClassCastException e) {
      return defaultValue;
    }
  }

  /**
   * Java Map(LinkedHashMap)으로 변환하여 리턴
   * <p>
   * 중첩된 JSONMap, JSONList도 변환한다. (JSONMap -> LinkedHashMap, JSONList -> LinkedList)
   * @see JSONList#castToJavaList
   * @return (LinkedHash)Map object
   */
  public Map<String, Object> castToJavaMap() {
    Map<String, Object> map = new LinkedHashMap<>();
    this.entrySet().stream().forEach(e -> {
      Object value = Optional.ofNullable(e.getValue()).map(o -> {
        if (o instanceof JSONMap)
          return ((JSONMap) o).castToJavaMap();
        else if (o instanceof JSONList)
          return ((JSONList) o).castToJavaList();
        else
          return o;
      }).orElse(e.getValue());
      map.put(e.getKey(), value);
    });
    return map;
  }

  /* static method */
  /**
   * Map(or not) 객체를 JSONMap으로 변환
   * @param object map(or not) object
   * @return JSONMap object
   */
  public static JSONMap from(Object object) {
    JSONMap resultMap = ObjectInstance.getInstance().createJsonMapFromObject.apply(object);
    return resultMap;
  }

  /**
   * Map 객체를 JSONMap으로 변환
   * <p>
   * 현재는 from(Object)의 alias이지만, 추후 성능 이슈 관련해서 구현해야 할 수 있음(필터링 과정 생략)
   * @param object map object
   * @return JSONMap object
   */
  public static <T extends Map<?, ?>> JSONMap from(T map) {
    return from((Object) map);
  }

  /**
   * JSONMap 객체를 기반으로 JSONMap 생성
   * @param map JSONMap object
   * @return JSONMap object
   */
  public static JSONMap from(JSONMap map) {
    return new JSONMap(map);
  }

  /**
   * json String을 JSONMap으로 변환
   * <p>
   * 기본 제공하는 jackson json ObjectMapper을 사용함
   * <p>
   * 파싱 실패 시 에러 발생
   * @param jsonString json string
   * @return JSONMap object
   * @throws JsonProcessingException
   */
  public static JSONMap fromJsonString(String jsonString) throws JsonProcessingException {
    return fromJsonString(jsonString, null);
  }

  /**
   * json String을 JSONMap으로 변환
   * <p>
   * 임의의 jackson json ObjectMapper를 쓸 경우 사용
   * <p>
   * 미제공시(null), 기본 제공하는 jackson json ObjectMapper을 사용함
   * <p>
   * 파싱 실패 시 에러 발생
   * @param jsonString json string
   * @param mapper 임의의 jackson json ObjectMapper
   * @return JSONMap object
   * @throws JsonProcessingException
   */
  public static JSONMap fromJsonString(String jsonString, ObjectMapper mapper) throws JsonProcessingException {
    ObjectMapper mpr = Optional.ofNullable(mapper).orElseGet(ObjectMapperInstance::getInstance);
    return JSONMap.from(mpr.readValue(jsonString, LinkedHashMap.class));
  }

  /**
   * json String을 JSONMap으로 변환
   * <p>
   * 기본 제공하는 jackson json ObjectMapper을 사용함
   * <p>
   * 파싱 실패 시 null 리턴
   * @param jsonString json string
   * @return JSONMap object
   */
  public static JSONMap tryParseJsonString(String jsonString) {
    try {
      return fromJsonString(jsonString);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  /**
   * json String을 JSONMap으로 변환
   * <p>
   * 임의의 jackson json ObjectMapper를 쓸 경우 사용
   * <p>
   * 미제공시(null), 기본 제공하는 jackson json ObjectMapper을 사용함
   * <p>
   * 파싱 실패 시 null 리턴
   * @param jsonString json string
   * @param mapper 임의의 jackson json ObjectMapper
   * @return JSONMap object
   */
  public static JSONMap tryParseJsonString(String jsonString, ObjectMapper mapper) {
    try {
      return fromJsonString(jsonString, mapper);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  /**
   * k, v 1쌍으로 생성된 객체 생성
   * @param k1
   * @param v1
   * @return JSONMap object
   */
  public static JSONMap of(String k1, Object v1) {
    return JSONMap.builder().put(k1, v1).build();
  }

  /**
   * k, v 2쌍으로 생성된 객체 생성
   * @param k1
   * @param v1
   * @param k2
   * @param v2
   * @return JSONMap object
   */
  public static JSONMap of(String k1, Object v1, String k2, Object v2) {
    return JSONMap.builder().put(k1, v1).put(k2, v2).build();
  }

  /**
   * k, v 3쌍으로 생성된 객체 생성
   * @param k1
   * @param v1
   * @param k2
   * @param v2
   * @param k3
   * @param v3
   * @return JSONMap object
   */
  public static JSONMap of(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
    return JSONMap.builder().put(k1, v1).put(k2, v2).put(k3, v3).build();
  }

  /**
   * k, v 4쌍으로 생성된 객체 생성
   * @param k1
   * @param v1
   * @param k2
   * @param v2
   * @param k3
   * @param v3
   * @param k4
   * @param v4
   * @return JSONMap object
   */
  public static JSONMap of(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
    return JSONMap.builder().put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4).build();
  }

  /**
   * k, v 5쌍으로 생성된 객체 생성
   * <p>
   * @param k1
   * @param v1
   * @param k2
   * @param v2
   * @param k3
   * @param v3
   * @param k4
   * @param v4
   * @param k5
   * @param v5
   * @return JSONMap object
   */
  public static JSONMap of(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4,
      String k5, Object v5) {
    return JSONMap.builder().put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5).build();
  }

  /* builder */
  /**
   * 현재 JSONMap object를 기반으로 builder 객체 생성(builder 객체로 변환)
   * @return JSONMapBuilder
   */
  public JSONMapBuilder toBuilder() {
    return JSONMap.builder().from(this);
  }

  /**
   * Builder class
   */
  public static class JSONMapBuilder {
    // Entry(key, value 1쌍)들을 순서대로 담기 위해 List 사용
    private LinkedList<Entry<String, Object>> _stack = new LinkedList<>();

    /* aliases */
    /**
     * @see JSONMapBuilder#putAll
     * @param o
     * @return JSONMapBuilder
     */
    public <T extends Map<String, Object>> JSONMapBuilder from(T o) {
      return this.putAll(o);
    }

    /**
     * @see JSONMapBuilder#putAll
     * @param o
     * @return JSONMapBuilder
     */
    public JSONMapBuilder from(Object o) {
      return this.putAll(o);
    }

    /* build-ing methods */
    /**
     * Map(or not) 객체에 속한 Entry를 모두 담기 위해 사용
     * @param object Map(or not) object
     * @return JSONMapBuilder
     */
    public JSONMapBuilder putAll(Object object) {
      ObjectInstance.getInstance().streamEntryFromObject.apply(object).forEach(_stack::add);
      return this;
    }

    /**
     * Entry를 구성하는 key, value 한 쌍을 담기 위해 사용
     * @param key
     * @param value
     * @return JSONMapBuilder
     */
    public JSONMapBuilder put(String key, Object value) {
      _stack.add(new SimpleEntry<>(key, value));
      return this;
    }

    /**
     * Entry list에서 key에 해당하는 모든 Entry 삭제
     * @param key
     * @return JSONMapBuilder
     */
    public JSONMapBuilder remove(String key) {
      _stack.removeIf(e -> e.getKey().equals(key));
      return this;
    }

    /* build method */
    /**
     * Entry list를 기반으로 JSONMap 생성
     * @return JSONMap
     */
    public JSONMap build() {
      return ObjectInstance.getInstance().createJsonMapFromCollection.apply(_stack);
    }
  }
}
