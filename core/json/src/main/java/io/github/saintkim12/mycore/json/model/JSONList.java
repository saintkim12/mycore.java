package io.github.saintkim12.mycore.json.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder(toBuilder = false)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JSONList extends LinkedList<JSONMap> {

  /* member, inner methods */
  private final static Function<Object, Stream<JSONMap>> STREAM_FROM_OBJECT = (object) -> {
    return Optional.ofNullable(object).filter(o -> o instanceof List).map(l -> (List<?>) l).map(List::stream)
        .orElseGet(Stream::empty).filter(o -> o instanceof Map).map(JSONMap::from);
  };

  private final static Function<Object, JSONList> CREATE_FROM_OBJECT = (object) -> {
    return STREAM_FROM_OBJECT.apply(object).collect(Collectors.toCollection(JSONList::new));
  };

  /* constructor */
  public <T extends Map<String, Object>> JSONList(List<T> l) {
    l.stream().map(JSONMap::from).forEach(this::add);
  }

  public JSONList(Object object) {
    Optional.ofNullable(object).filter(o -> o instanceof List).map(l -> (List<?>) l).map(List::stream)
        .orElseGet(Stream::empty).map(JSONMap::from).forEach(this::add);
  }

  /* default method */
  public String toString() {
    try {
      return ObjectMapperInstance.getInstance().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    } catch (Exception e) {
      return super.toString();
    }
  }

  /* original method */
  public List<Map<String, Object>> castToJavaCollection() {
    List<Map<String, Object>> list = new LinkedList<>();
    this.stream().forEach(e -> {
      Map<String, Object> value = e.castToJavaCollection();
      list.add(value);
    });
    return list;
  }

  /* static method */
  public static JSONList from(Object object) {
    return CREATE_FROM_OBJECT.apply(object);
  }

  public static JSONList fromJsonString(String jsonString) throws JsonProcessingException {
    return JSONList.from(ObjectMapperInstance.getInstance().readValue(jsonString, LinkedList.class));
  }

  public static JSONList tryParseJsonString(String jsonString) {
    try {
      return JSONList.from(ObjectMapperInstance.getInstance().readValue(jsonString, LinkedList.class));
    } catch (JsonProcessingException e) {
      // log.error("Parsing error with {}", jsonString, e);
      return null;
    }
  }

  /* builder */
  public JSONListBuilder toBuilder() {
    return JSONList.builder().from(this);
  }

  public static class JSONListBuilder {
    private LinkedList<JSONMap> _stack = new LinkedList<>();

    /* aliases */
    public <T extends Map<String, Object>> JSONListBuilder from(List<T> o) {
      return this.addAll(o);
    }

    public JSONListBuilder from(Object o) {
      return this.addAll(o);
    }

    /* build-ing methods */
    public JSONListBuilder addAll(Object object) {
      STREAM_FROM_OBJECT.apply(object).forEach(_stack::add);
      return this;
    }

    public JSONListBuilder add(Map<String, Object> map) {
      _stack.add(JSONMap.from(map));
      return this;
    }

    /* build method */
    public JSONList build() {
      return _stack.stream().collect(Collectors.toCollection(JSONList::new));
    }
  }
}
