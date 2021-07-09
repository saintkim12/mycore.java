package io.github.saintkim12.mycore.json.model;

import com.fasterxml.jackson.databind.ObjectMapper;

final class ObjectMapperInstance {
  // private ObjectMapper INSTANCE;

  private ObjectMapperInstance() {
  }

  public static ObjectMapper getInstance() {
    ObjectMapper INSTANCE = new ObjectMapper();
    // TODO: 필요한 디폴트 세팅이 있으면 생성
    return INSTANCE;
  }
}
