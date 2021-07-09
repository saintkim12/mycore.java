package io.github.saintkim12.mycore.json.model;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.LinkedHashSet;
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

/**
 * ObjectInstance
 * <p>
 * 내부에서 공통적으로 사용할 인스턴스를 싱글톤 패턴으로 정의
 */
class ObjectInstance {
  private static ObjectInstance INSTANCE = null;

  private ObjectInstance() {
  }

  public static ObjectInstance getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ObjectInstance();
    }
    return INSTANCE;
  }

  /**
   * entry의 내부 객체를 확인하고 (변환)다시 감싸 entry로 리턴
   */
  @SuppressWarnings("unchecked")
  public Function<Entry<?, ?>, Entry<?, ?>> detectAndRewrapEntry = e -> {
    if (e.getValue() instanceof List) {
      Optional<List<Object>> optList = Optional.of((List<Object>) e.getValue());
      Integer listSize = Long.valueOf(optList.map(List::stream).orElseGet(Stream::empty).count()).intValue();
      Integer mappableCount = Long
          .valueOf(optList.map(List::stream).orElseGet(Stream::empty).filter(item -> item instanceof Map).count())
          .intValue();
      return listSize > 0 && listSize.equals(mappableCount)
          ? new SimpleEntry<Object, Object>(e.getKey(), JSONList.from(e.getValue()))
          : new SimpleEntry<Object, Object>(e.getKey(), optList.map(List::stream).orElseGet(Stream::empty)
              .map(item -> item instanceof Map ? JSONMap.from(item) : item).collect(Collectors.toList()));
    } else {
      return e;
    }
  };
  /**
   * object가 Map의 instance이면 변환하고 전달받은 함수(fn)을 수행
   * @see ObjectInstance#detectAndRewrapEntry
   */
  public BiConsumer<Object, Consumer<Entry<?, ?>>> collectFromObject = (object, fn) -> {
    Optional.ofNullable(object).filter(o -> o instanceof Map).map(o -> (Map<?, ?>) o).map(Map::entrySet)
        .map(Set::stream).orElseGet(Stream::empty).filter(e -> e.getKey() != null).map(detectAndRewrapEntry)
        .forEach(fn);
  };
  /**
   * collection 내의 entry의 instance를 변환하고 전달받은 함수(fn)을 수행
   * @see ObjectInstance#detectAndRewrapEntry
   */
  public BiConsumer<Collection<Entry<String, Object>>, Consumer<Entry<?, ?>>> collectFromEntryCollection = (collection,
      fn) -> {
    Optional.ofNullable(collection).map(Collection::stream).orElseGet(Stream::empty).filter(o -> o instanceof Entry)
        .map(o -> (Entry<?, ?>) o).filter(e -> e.getKey() != null).map(detectAndRewrapEntry).forEach(fn);
  };
  /**
   * object로부터 collect를 수행하여 생성된 entry를 stream 형태로 리턴
   * @see ObjectInstance#collectFromObject
   */
  public Function<Object, Stream<Entry<String, Object>>> streamEntryFromObject = (object) -> {
    LinkedHashSet<Entry<String, Object>> resultSet = new LinkedHashSet<>();
    collectFromObject.accept(object,
        e -> resultSet.add(new SimpleEntry<String, Object>(e.getKey().toString(), e.getValue())));
    return resultSet.stream();
  };
  /**
   * object로부터 collect를 수행하여 생성된 entry들을 JSONMap 형태로 모아 리턴
   * @see ObjectInstance#collectFromObject
   */
  public Function<Object, JSONMap> createJsonMapFromObject = (object) -> {
    JSONMap resultMap = new JSONMap();
    collectFromObject.accept(object, e -> resultMap.put(e.getKey().toString(), e.getValue()));
    return resultMap;
  };

  /**
   * collection으로부터 collect를 수행하여 생성된 entry들을 JSONMap 형태로 모아 리턴
   * @see ObjectInstance#collectFromEntryCollection
   */
  public Function<Collection<Entry<String, Object>>, JSONMap> createJsonMapFromCollection = (collection) -> {
    JSONMap resultMap = new JSONMap();
    collectFromEntryCollection.accept(collection, e -> resultMap.put(e.getKey().toString(), e.getValue()));
    return resultMap;
  };

  /**
   * object로부터 collect를 수행하여 생성된 JSONMap들을 stream 형태로 리턴
   */
  public Function<Object, Stream<JSONMap>> streamJsonListFromObject = (object) -> {
    return Optional.ofNullable(object).filter(o -> o instanceof List).map(l -> (List<?>) l).map(List::stream)
        .orElseGet(Stream::empty).filter(o -> o instanceof Map).map(JSONMap::from);
  };

  /**
   * object로부터 collect를 수행하여 생성된 JSONMap들을 JSONList 형태로 리턴
   * @see ObjectInstance#streamJsonListFromObject
   */
  public Function<Object, JSONList> createJsonListFromObject = (object) -> {
    return streamJsonListFromObject.apply(object).collect(Collectors.toCollection(JSONList::new));
  };
}
