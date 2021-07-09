package io.github.saintkim12.mycore.json.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * JSONList
 * <p>
 * JSONMap으로 이루어진 List(LinkedList)
 * 
 * @author saintkim12
 * @version 0.1.0
 * @since 0.1.0
 */
@Builder(toBuilder = false)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JSONList extends LinkedList<JSONMap> {
  /* member */

  /* constructor */
  public <T extends Map<String, Object>> JSONList(List<T> l) {
    l.stream().map(JSONMap::from).forEach(this::add);
  }

  public JSONList(Object object) {
    Optional.ofNullable(object).filter(o -> o instanceof List).map(l -> (List<?>) l).map(List::stream)
        .orElseGet(Stream::empty).map(JSONMap::from).forEach(this::add);
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
   * Java List(LinkedList)으로 변환하여 리턴
   * <p>
   * 중첩된 JSONMap, JSONList도 변환한다. (JSONMap -> LinkedHashMap, JSONList -> LinkedList)
   * @see JSONMap#castToJavaMap
   * @return (Linked)List object
   */
  public List<Map<String, Object>> castToJavaList() {
    List<Map<String, Object>> list = new LinkedList<>();
    this.stream().forEach(e -> {
      Map<String, Object> value = e.castToJavaMap();
      list.add(value);
    });
    return list;
  }

  /* static method */
  /**
   * List(or not) 객체를 JSONList로 변환
   * @param object list(or not) object
   * @return JSONList object
   */
  public static JSONList from(Object object) {
    return ObjectInstance.getInstance().createJsonListFromObject.apply(object);
  }

  /**
   * JSONList 객체를 기반으로 JSONList 생성
   * @param list JSONList object
   * @return JSONList object
   */
  public static JSONList from(JSONList list) {
    return new JSONList(list);
  }

  /**
   * json String을 JSONList로 변환
   * <p>
   * 기본 제공하는 jackson json ObjectMapper을 사용함
   * <p>
   * 파싱 실패 시 에러 발생
   * @param jsonString json string
   * @return JSONList object
   * @throws JsonProcessingException
   */
  public static JSONList fromJsonString(String jsonString) throws JsonProcessingException {
    return fromJsonString(jsonString, null);
  }

  /**
   * json String을 JSONList로 변환
   * <p>
   * 임의의 jackson json ObjectMapper를 쓸 경우 사용
   * <p>
   * 미제공시(null), 기본 제공하는 jackson json ObjectMapper을 사용함
   * <p>
   * 파싱 실패 시 에러 발생
   * @param jsonString json string
   * @param mapper 임의의 jackson json ObjectMapper
   * @return JSONList object
   * @throws JsonProcessingException
   */
  public static JSONList fromJsonString(String jsonString, ObjectMapper mapper) throws JsonProcessingException {
    ObjectMapper mpr = Optional.ofNullable(mapper).orElseGet(ObjectMapperInstance::getInstance);
    return JSONList.from(mpr.readValue(jsonString, LinkedList.class));
  }

  /**
   * json String을 JSONList로 변환
   * <p>
   * 기본 제공하는 jackson json ObjectMapper을 사용함
   * <p>
   * 파싱 실패 시 null 리턴
   * @param jsonString json string
   * @return JSONList object
   */
  public static JSONList tryParseJsonString(String jsonString) throws JsonProcessingException {
    try {
      return fromJsonString(jsonString);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  /**
   * json String을 JSONList로 변환
   * <p>
   * 임의의 jackson json ObjectMapper를 쓸 경우 사용
   * <p>
   * 미제공시(null), 기본 제공하는 jackson json ObjectMapper을 사용함
   * <p>
   * 파싱 실패 시 null 리턴
   * @param jsonString json string
   * @param mapper 임의의 jackson json ObjectMapper
   * @return JSONList object
   */
  public static JSONList tryParseJsonString(String jsonString, ObjectMapper mapper) throws JsonProcessingException {
    try {
      return fromJsonString(jsonString, mapper);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  /**
   * JSONMap 1개로 생성된 객체 생성
   * @param k1
   * @param v1
   * @return JSONList object
   */
  public static JSONList of(JSONMap e1) {
    return JSONList.builder().add(e1).build();
  }

  /**
   * JSONMap 2개로 생성된 객체 생성
   * @param e1
   * @param e2
   * @return JSONList object
   */
  public static JSONList of(JSONMap e1, JSONMap e2) {
    return JSONList.builder().add(e1).add(e2).build();
  }

  /**
   * JSONMap 3개로 생성된 객체 생성
   * @param e1
   * @param e2
   * @param e3
   * @return JSONList object
   */
  public static JSONList of(JSONMap e1, JSONMap e2, JSONMap e3) {
    return JSONList.builder().add(e1).add(e2).add(e3).build();
  }

  /**
   * JSONMap 4개로 생성된 객체 생성
   * @param e1
   * @param e2
   * @param e3
   * @param e4
   * @return JSONList object
   */
  public static JSONList of(JSONMap e1, JSONMap e2, JSONMap e3, JSONMap e4) {
    return JSONList.builder().add(e1).add(e2).add(e3).add(e4).build();
  }

  /**
   * JSONMap 5개로 생성된 객체 생성
   * @param e1
   * @param e2
   * @param e3
   * @param e4
   * @param e5
   * @return JSONList object
   */
  public static JSONList of(JSONMap e1, JSONMap e2, JSONMap e3, JSONMap e4, JSONMap e5) {
    return JSONList.builder().add(e1).add(e2).add(e3).add(e4).add(e5).build();
  }

  /**
   * JSONMap 6개로 생성된 객체 생성
   * @param e1
   * @param e2
   * @param e3
   * @param e4
   * @param e5
   * @param e6
   * @return JSONList object
   */
  public static JSONList of(JSONMap e1, JSONMap e2, JSONMap e3, JSONMap e4, JSONMap e5, JSONMap e6) {
    return JSONList.builder().add(e1).add(e2).add(e3).add(e4).add(e5).add(e6).build();
  }

  /**
   * JSONMap 7개로 생성된 객체 생성
   * @param e1
   * @param e2
   * @param e3
   * @param e4
   * @param e5
   * @param e6
   * @param e7
   * @return JSONList object
   */
  public static JSONList of(JSONMap e1, JSONMap e2, JSONMap e3, JSONMap e4, JSONMap e5, JSONMap e6, JSONMap e7) {
    return JSONList.builder().add(e1).add(e2).add(e3).add(e4).add(e5).add(e6).add(e7).build();
  }

  /**
   * JSONMap 8개로 생성된 객체 생성
   * @param e1
   * @param e2
   * @param e3
   * @param e4
   * @param e5
   * @param e6
   * @param e7
   * @param e8
   * @return JSONList object
   */
  public static JSONList of(JSONMap e1, JSONMap e2, JSONMap e3, JSONMap e4, JSONMap e5, JSONMap e6, JSONMap e7,
      JSONMap e8) {
    return JSONList.builder().add(e1).add(e2).add(e3).add(e4).add(e5).add(e6).add(e7).add(e8).build();
  }

  /**
   * JSONMap 9개로 생성된 객체 생성
   * @param e1
   * @param e2
   * @param e3
   * @param e4
   * @param e5
   * @param e6
   * @param e7
   * @param e8
   * @param e9
   * @return JSONList object
   */
  public static JSONList of(JSONMap e1, JSONMap e2, JSONMap e3, JSONMap e4, JSONMap e5, JSONMap e6, JSONMap e7,
      JSONMap e8, JSONMap e9) {
    return JSONList.builder().add(e1).add(e2).add(e3).add(e4).add(e5).add(e6).add(e7).add(e8).add(e9).build();
  }

  /* builder */
  /**
   * 현재 JSONList object를 기반으로 builder 객체 생성(builder 객체로 변환)
   * @return JSONListBuilder
   */
  public JSONListBuilder toBuilder() {
    return JSONList.builder().from(this);
  }

  /**
   * Builder class
   */
  public static class JSONListBuilder {
    // JSONMap들을 순서대로 담기 위해 List 사용
    private LinkedList<JSONMap> _stack = new LinkedList<>();

    /* aliases */
    /**
     * @see JSONListBuilder#addAll
     * @param o
     * @return JSONListBuilder
     */
    public <T extends Map<String, Object>> JSONListBuilder from(List<T> o) {
      return this.addAll(o);
    }

    /**
     * @see JSONListBuilder#addAll
     * @param o
     * @return JSONListBuilder
     */
    public JSONListBuilder from(Object o) {
      return this.addAll(o);
    }

    /* build-ing methods */
    /**
     * List(or not) 객체에 속한 Map을 모두 담기 위해 사용
     * @param object List(or not) object
     * @return JSONListBuilder
     */
    public JSONListBuilder addAll(Object object) {
      ObjectInstance.getInstance().streamJsonListFromObject.apply(object).forEach(_stack::add);
      return this;
    }

    /**
     * List를 구성하는 Map 하나를 담기 위해 사용
     * @param key
     * @param value
     * @return JSONListBuilder
     */
    public JSONListBuilder add(Map<String, Object> map) {
      _stack.add(JSONMap.from(map));
      return this;
    }

    /**
     * list에서 첫번째 값 삭제
     * @see LinkedList#removeFirst
     * @param key
     * @return JSONListBuilder
     */
    public JSONListBuilder removeFirst() {
      if (_stack.size() > 0) {
        _stack.removeFirst();
      }
      return this;
    }

    /**
     * list에서 마지막째 값 삭제
     * @see LinkedList#removeLast
     * @param key
     * @return JSONListBuilder
     */
    public JSONListBuilder removeLast() {
      if (_stack.size() > 0) {
        _stack.removeLast();
      }
      return this;
    }

    /**
     * list에서 조건에 맞는 값 삭제
     * <p>
     * ex> builder.removeIf(m -> "SOME_ID".equals(m.<String>castGet("id")));
     * @see Collection#removeIf
     * @param key
     * @return JSONListBuilder
     */
    public JSONListBuilder removeIf(Predicate<? super JSONMap> pr) {
      if (_stack.size() > 0) {
        _stack.removeIf(pr);
      }
      return this;
    }

    /* build method */
    /**
     * list를 기반으로 JSONList 생성
     * @return JSONList
     */
    public JSONList build() {
      return _stack.stream().collect(Collectors.toCollection(JSONList::new));
    }
  }
}
