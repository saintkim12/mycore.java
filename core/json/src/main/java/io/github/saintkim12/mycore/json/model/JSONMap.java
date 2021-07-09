package io.github.saintkim12.mycore.json.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder(toBuilder = false)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JSONMap extends LinkedHashMap<String, Object> {
  /* member, inner methods */
  @SuppressWarnings("unchecked")
  private final static Function<Entry<?, ?>, Entry<?, ?>> DETECT_AND_REWRAP_ENTRY = e -> {
    if (e.getValue() instanceof List) {
      Optional<List<Object>> optList = Optional.of((List<Object>) e.getValue());
      Integer listSize = Long.valueOf(optList.map(List::stream).orElseGet(Stream::empty).count()).intValue();
      Integer mappableCount = Long
          .valueOf(optList.map(List::stream).orElseGet(Stream::empty).filter(item -> item instanceof Map).count())
          .intValue();
      return listSize > 0 && listSize.equals(mappableCount)
          ? new SimpleEntry<Object, Object>(e.getKey(), JSONList.from(e.getValue()))
          : new SimpleEntry<Object, Object>(e.getKey(), optList.map(List::stream).orElseGet(Stream::empty).map(item -> item instanceof Map ? JSONMap.from(item) : item).collect(Collectors.toList()));
    } else {
      return e;
    }
  };
  private final static BiConsumer<Object, Consumer<Entry<?, ?>>> COLLECT_FROM_OBJECT = (object, fn) -> {
    Optional.ofNullable(object).filter(o -> o instanceof Map).map(o -> (Map<?, ?>) o).map(Map::entrySet)
        .map(Set::stream).orElseGet(Stream::empty).filter(e -> e.getKey() != null).map(DETECT_AND_REWRAP_ENTRY)
        .forEach(fn);
  };
  private final static BiConsumer<Collection<Entry<String, Object>>, Consumer<Entry<?, ?>>> COLLECT_FROM_ENTRY_COLLECTION = (
      collection, fn) -> {
    Optional.ofNullable(collection).map(Collection::stream).orElseGet(Stream::empty).filter(o -> o instanceof Entry)
        .map(o -> (Entry<?, ?>) o).filter(e -> e.getKey() != null).map(DETECT_AND_REWRAP_ENTRY).forEach(fn);
  };
  private final static Function<Object, Stream<Entry<String, Object>>> STREAM_FROM_OBJECT = (object) -> {
    LinkedHashSet<Entry<String, Object>> resultSet = new LinkedHashSet<>();
    COLLECT_FROM_OBJECT.accept(object,
        e -> resultSet.add(new SimpleEntry<String, Object>(e.getKey().toString(), e.getValue())));
    return resultSet.stream();
  };

  private final static Function<Object, JSONMap> CREATE_FROM_OBJECT = (object) -> {
    JSONMap resultMap = new JSONMap();
    COLLECT_FROM_OBJECT.accept(object, e -> resultMap.put(e.getKey().toString(), e.getValue()));
    return resultMap;
  };

  private final static Function<Collection<Entry<String, Object>>, JSONMap> CREATE_FROM_COLLECTION = (collection) -> {
    JSONMap resultMap = new JSONMap();
    COLLECT_FROM_ENTRY_COLLECTION.accept(collection, e -> resultMap.put(e.getKey().toString(), e.getValue()));
    return resultMap;
  };

  /* constructor */
  public <T extends Map<String, Object>> JSONMap(T o) {
    this.putAll(o);
  }

  /* default method */
  public String toString() {
    try {
      return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    } catch (Exception e) {
      return super.toString();
    }
  }

  /* original method */
  /**
   * castGet()
   * 설명   : (map).get 사용 시 type cast 과정 생략을 위한 메소드
   * @param key
   * @return casted value
   **/
  @SuppressWarnings("unchecked")
  public <T> T castGet(Object key) {
    Object value = this.get(key);
    return (T) value;
  }

  /**
   * castGet()
   * 설명   : (map).get 사용 시 type cast 과정 생략을 위한 메소드
   *        해당 key의 값이 없는 경우(getOrDefault) defaultValue 리턴
   * @param key
   * @return casted value or default value
   **/
  @SuppressWarnings("unchecked")
  public <T> T castGet(Object key, T defaultValue) {
    Object value = this.getOrDefault(key, defaultValue);
    return (T) value;
  }

  /**
   * castGetExceptsNull()
   * 설명   : (map).get 사용 시 type cast 과정 생략을 위한 메소드
   *        해당 key의 값이 null이거나 값이 없는 경우(getOrDefault) defaultValue 리턴
   * @param key
   * @return casted value or default value
   **/
  public <T> T castGetExceptsNull(Object key, T defaultValue) {
    Optional<T> optValue = Optional.ofNullable(this.castGet(key));
    return optValue.orElse(defaultValue);
  }

  /* static method */
  public static JSONMap from(Object object) {
    JSONMap resultMap = CREATE_FROM_OBJECT.apply(object);
    return resultMap;
  }

  public static JSONMap fromJsonString(String jsonString) {
    try {
      // return new ObjectMapper().readValue(jsonString, JSONMap.class);
      return JSONMap.from(new ObjectMapper().readValue(jsonString, LinkedHashMap.class));
      // return JSONMap.from(new JSONParser().parse(jsonString));
    } catch (JsonProcessingException e) {
      log.error("Parsing error with {}", jsonString, e);
      return new JSONMap();
    }
  }

  public static JSONMap from(JSONMap object) {
    return new JSONMap(object);
  }

  /**
   * of()
   * 설명   : k, v 1쌍으로 생성된 객체 생성
   * @param k1
   * @param v1
   * @return
   */
  public static JSONMap of(String k1, Object v1) {
    return JSONMap.builder().put(k1, v1).build();
  }

  /**
   * of()
   * 설명   : k, v 2쌍으로 생성된 객체 생성
   * @param k1
   * @param v1
   * @param k2
   * @param v2
   * @return
   */
  public static JSONMap of(String k1, Object v1, String k2, Object v2) {
    return JSONMap.builder().put(k1, v1).put(k2, v2).build();
  }

  /**
   * of()
   * 설명   : k, v 3쌍으로 생성된 객체 생성
   * @param k1
   * @param v1
   * @param k2
   * @param v2
   * @param k3
   * @param v3
   * @return
   */
  public static JSONMap of(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
    return JSONMap.builder().put(k1, v1).put(k2, v2).put(k3, v3).build();
  }

  /**
   * of()
   * 설명   : k, v 4쌍으로 생성된 객체 생성
   * @param k1
   * @param v1
   * @param k2
   * @param v2
   * @param k3
   * @param v3
   * @param k4
   * @param v4
   * @return
   */
  public static JSONMap of(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
    return JSONMap.builder().put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4).build();
  }

  /**
   * of()
   * 설명   : k, v 5쌍으로 생성된 객체 생성
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
   * @return
   */
  public static JSONMap of(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4,
      String k5, Object v5) {
    return JSONMap.builder().put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5).build();
  }

  /* builder */
  public JSONMapBuilder toBuilder() {
    return JSONMap.builder().from(this);
  }

  public static class JSONMapBuilder {
    private LinkedList<Entry<String, Object>> _stack = new LinkedList<>();

    /* aliases */
    public <T extends Map<String, Object>> JSONMapBuilder from(T o) {
      return this.putAll(o);
    }

    public JSONMapBuilder from(Object o) {
      return this.putAll(o);
    }

    /* build-ing methods */
    public JSONMapBuilder putAll(Object object) {
      STREAM_FROM_OBJECT.apply(object).forEach(_stack::add);
      return this;
    }

    public JSONMapBuilder put(String key, Object value) {
      _stack.add(new SimpleEntry<>(key, value));
      return this;
    }

    public JSONMapBuilder remove(String key) {
      _stack.removeIf(e -> e.getKey().equals(key));
      return this;
    }

    /* build method */
    public JSONMap build() {
      return CREATE_FROM_COLLECTION.apply(_stack);
    }
  }
}
