package io.github.saintkim12.mycore.json;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
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
    JSONMap m4 = JSONMap.tryParseJsonString("{\"message\":\"hello3\",\"result\":true}");
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
    JSONMap m2 = JSONMap.tryParseJsonString(
        "{\"message\":\"hello3\",\"result\":true,\"primitiveList\":[1,2,5],\"list\":[{\"value\":\"a\"},{\"value\":\"b\"},{\"value\":\"C\"}]}");
    // log.debug("m2: {}", m2.get("list"));
    assertEquals(m2.<List<Object>>castGet("primitiveList"), Arrays.asList(1, 2, 5));
    assertArrayEquals(m2.<JSONList>castGet("list").toArray(),
        Arrays.asList(JSONMap.of("value", "a"), JSONMap.of("value", "b"), JSONMap.of("value", "C")).toArray());
    assertEquals(m2.<JSONList>castGet("list").toString(), JSONList
        .from(Arrays.asList(JSONMap.of("value", "a"), JSONMap.of("value", "b"), JSONMap.of("value", "C"))).toString());
  }

  @Test
  public void testJSONMapCastJavaCollection() {
    JSONMap m2 = JSONMap.tryParseJsonString(
        "{\"message\":\"hello3\",\"result\":true,\"primitiveList\":[1,2,5],\"list\":[{\"value\":\"a\"},{\"value\":\"b\"},{\"value\":\"C\"}]}");
    // log.debug("m2: {}", m2.get("list"));
    Map<String, Object> javaMap = m2.castToJavaMap();
    // cast check for primitive type(boolean)
    assertEquals(javaMap.get("result"), true);
    // cast check for string
    assertEquals(javaMap.get("message"), "hello3");
    // cast check for primitive list
    assertArrayEquals(((List<Object>) javaMap.get("primitiveList")).toArray(), Arrays.asList(1, 2, 5).toArray());
    // cast check for JSONMap list
    assertArrayEquals(((List<Map<String, Object>>) javaMap.get("list")).toArray(),
        Arrays.asList(JSONMap.of("value", "a"), JSONMap.of("value", "b"), JSONMap.of("value", "C")).toArray());
  }

  @Test
  public void testJSONMapCastApiJson() throws IOException {
    // https://jsonplaceholder.typicode.com/users
    String strData = String.join("\n", new String[]{
      "[",
      "  {",
      "    \"id\": 1,",
      "    \"name\": \"Leanne Graham\",",
      "    \"username\": \"Bret\",",
      "    \"email\": \"Sincere@april.biz\",",
      "    \"address\": {",
      "      \"street\": \"Kulas Light\",",
      "      \"suite\": \"Apt. 556\",",
      "      \"city\": \"Gwenborough\",",
      "      \"zipcode\": \"92998-3874\",",
      "      \"geo\": {",
      "        \"lat\": \"-37.3159\",",
      "        \"lng\": \"81.1496\"",
      "      }",
      "    },",
      "    \"phone\": \"1-770-736-8031 x56442\",",
      "    \"website\": \"hildegard.org\",",
      "    \"company\": {",
      "      \"name\": \"Romaguera-Crona\",",
      "      \"catchPhrase\": \"Multi-layered client-server neural-net\",",
      "      \"bs\": \"harness real-time e-markets\"",
      "    }",
      "  },",
      "  {",
      "    \"id\": 2,",
      "    \"name\": \"Ervin Howell\",",
      "    \"username\": \"Antonette\",",
      "    \"email\": \"Shanna@melissa.tv\",",
      "    \"address\": {",
      "      \"street\": \"Victor Plains\",",
      "      \"suite\": \"Suite 879\",",
      "      \"city\": \"Wisokyburgh\",",
      "      \"zipcode\": \"90566-7771\",",
      "      \"geo\": {",
      "        \"lat\": \"-43.9509\",",
      "        \"lng\": \"-34.4618\"",
      "      }",
      "    },",
      "    \"phone\": \"010-692-6593 x09125\",",
      "    \"website\": \"anastasia.net\",",
      "    \"company\": {",
      "      \"name\": \"Deckow-Crist\",",
      "      \"catchPhrase\": \"Proactive didactic contingency\",",
      "      \"bs\": \"synergize scalable supply-chains\"",
      "    }",
      "  },",
      "  {",
      "    \"id\": 3,",
      "    \"name\": \"Clementine Bauch\",",
      "    \"username\": \"Samantha\",",
      "    \"email\": \"Nathan@yesenia.net\",",
      "    \"address\": {",
      "      \"street\": \"Douglas Extension\",",
      "      \"suite\": \"Suite 847\",",
      "      \"city\": \"McKenziehaven\",",
      "      \"zipcode\": \"59590-4157\",",
      "      \"geo\": {",
      "        \"lat\": \"-68.6102\",",
      "        \"lng\": \"-47.0653\"",
      "      }",
      "    },",
      "    \"phone\": \"1-463-123-4447\",",
      "    \"website\": \"ramiro.info\",",
      "    \"company\": {",
      "      \"name\": \"Romaguera-Jacobson\",",
      "      \"catchPhrase\": \"Face to face bifurcated interface\",",
      "      \"bs\": \"e-enable strategic applications\"",
      "    }",
      "  },",
      "  {",
      "    \"id\": 4,",
      "    \"name\": \"Patricia Lebsack\",",
      "    \"username\": \"Karianne\",",
      "    \"email\": \"Julianne.OConner@kory.org\",",
      "    \"address\": {",
      "      \"street\": \"Hoeger Mall\",",
      "      \"suite\": \"Apt. 692\",",
      "      \"city\": \"South Elvis\",",
      "      \"zipcode\": \"53919-4257\",",
      "      \"geo\": {",
      "        \"lat\": \"29.4572\",",
      "        \"lng\": \"-164.2990\"",
      "      }",
      "    },",
      "    \"phone\": \"493-170-9623 x156\",",
      "    \"website\": \"kale.biz\",",
      "    \"company\": {",
      "      \"name\": \"Robel-Corkery\",",
      "      \"catchPhrase\": \"Multi-tiered zero tolerance productivity\",",
      "      \"bs\": \"transition cutting-edge web services\"",
      "    }",
      "  },",
      "  {",
      "    \"id\": 5,",
      "    \"name\": \"Chelsey Dietrich\",",
      "    \"username\": \"Kamren\",",
      "    \"email\": \"Lucio_Hettinger@annie.ca\",",
      "    \"address\": {",
      "      \"street\": \"Skiles Walks\",",
      "      \"suite\": \"Suite 351\",",
      "      \"city\": \"Roscoeview\",",
      "      \"zipcode\": \"33263\",",
      "      \"geo\": {",
      "        \"lat\": \"-31.8129\",",
      "        \"lng\": \"62.5342\"",
      "      }",
      "    },",
      "    \"phone\": \"(254)954-1289\",",
      "    \"website\": \"demarco.info\",",
      "    \"company\": {",
      "      \"name\": \"Keebler LLC\",",
      "      \"catchPhrase\": \"User-centric fault-tolerant solution\",",
      "      \"bs\": \"revolutionize end-to-end systems\"",
      "    }",
      "  },",
      "  {",
      "    \"id\": 6,",
      "    \"name\": \"Mrs. Dennis Schulist\",",
      "    \"username\": \"Leopoldo_Corkery\",",
      "    \"email\": \"Karley_Dach@jasper.info\",",
      "    \"address\": {",
      "      \"street\": \"Norberto Crossing\",",
      "      \"suite\": \"Apt. 950\",",
      "      \"city\": \"South Christy\",",
      "      \"zipcode\": \"23505-1337\",",
      "      \"geo\": {",
      "        \"lat\": \"-71.4197\",",
      "        \"lng\": \"71.7478\"",
      "      }",
      "    },",
      "    \"phone\": \"1-477-935-8478 x6430\",",
      "    \"website\": \"ola.org\",",
      "    \"company\": {",
      "      \"name\": \"Considine-Lockman\",",
      "      \"catchPhrase\": \"Synchronised bottom-line interface\",",
      "      \"bs\": \"e-enable innovative applications\"",
      "    }",
      "  },",
      "  {",
      "    \"id\": 7,",
      "    \"name\": \"Kurtis Weissnat\",",
      "    \"username\": \"Elwyn.Skiles\",",
      "    \"email\": \"Telly.Hoeger@billy.biz\",",
      "    \"address\": {",
      "      \"street\": \"Rex Trail\",",
      "      \"suite\": \"Suite 280\",",
      "      \"city\": \"Howemouth\",",
      "      \"zipcode\": \"58804-1099\",",
      "      \"geo\": {",
      "        \"lat\": \"24.8918\",",
      "        \"lng\": \"21.8984\"",
      "      }",
      "    },",
      "    \"phone\": \"210.067.6132\",",
      "    \"website\": \"elvis.io\",",
      "    \"company\": {",
      "      \"name\": \"Johns Group\",",
      "      \"catchPhrase\": \"Configurable multimedia task-force\",",
      "      \"bs\": \"generate enterprise e-tailers\"",
      "    }",
      "  },",
      "  {",
      "    \"id\": 8,",
      "    \"name\": \"Nicholas Runolfsdottir V\",",
      "    \"username\": \"Maxime_Nienow\",",
      "    \"email\": \"Sherwood@rosamond.me\",",
      "    \"address\": {",
      "      \"street\": \"Ellsworth Summit\",",
      "      \"suite\": \"Suite 729\",",
      "      \"city\": \"Aliyaview\",",
      "      \"zipcode\": \"45169\",",
      "      \"geo\": {",
      "        \"lat\": \"-14.3990\",",
      "        \"lng\": \"-120.7677\"",
      "      }",
      "    },",
      "    \"phone\": \"586.493.6943 x140\",",
      "    \"website\": \"jacynthe.com\",",
      "    \"company\": {",
      "      \"name\": \"Abernathy Group\",",
      "      \"catchPhrase\": \"Implemented secondary concept\",",
      "      \"bs\": \"e-enable extensible e-tailers\"",
      "    }",
      "  },",
      "  {",
      "    \"id\": 9,",
      "    \"name\": \"Glenna Reichert\",",
      "    \"username\": \"Delphine\",",
      "    \"email\": \"Chaim_McDermott@dana.io\",",
      "    \"address\": {",
      "      \"street\": \"Dayna Park\",",
      "      \"suite\": \"Suite 449\",",
      "      \"city\": \"Bartholomebury\",",
      "      \"zipcode\": \"76495-3109\",",
      "      \"geo\": {",
      "        \"lat\": \"24.6463\",",
      "        \"lng\": \"-168.8889\"",
      "      }",
      "    },",
      "    \"phone\": \"(775)976-6794 x41206\",",
      "    \"website\": \"conrad.com\",",
      "    \"company\": {",
      "      \"name\": \"Yost and Sons\",",
      "      \"catchPhrase\": \"Switchable contextually-based project\",",
      "      \"bs\": \"aggregate real-time technologies\"",
      "    }",
      "  },",
      "  {",
      "    \"id\": 10,",
      "    \"name\": \"Clementina DuBuque\",",
      "    \"username\": \"Moriah.Stanton\",",
      "    \"email\": \"Rey.Padberg@karina.biz\",",
      "    \"address\": {",
      "      \"street\": \"Kattie Turnpike\",",
      "      \"suite\": \"Suite 198\",",
      "      \"city\": \"Lebsackbury\",",
      "      \"zipcode\": \"31428-2261\",",
      "      \"geo\": {",
      "        \"lat\": \"-38.2386\",",
      "        \"lng\": \"57.2232\"",
      "      }",
      "    },",
      "    \"phone\": \"024-648-3804\",",
      "    \"website\": \"ambrose.net\",",
      "    \"company\": {",
      "      \"name\": \"Hoeger LLC\",",
      "      \"catchPhrase\": \"Centralized empowering task-force\",",
      "      \"bs\": \"target end-to-end models\"",
      "    }",
      "  }",
      "]"
    });
    log.debug("strData: {}", strData);
    JSONList userList = JSONList.fromJsonString(strData);
    assertEquals(10, userList.size());
    assertEquals(userList.get(9).<Integer>castGet("id"), Integer.valueOf(10));
      // "    \"id\": 6,",
      // "    \"address\": {",
      // "      \"geo\": {",
      // "        \"lat\": \"-71.4197\",",
    assertEquals(
      Double.valueOf(-71.4197d),
      userList
        .stream()
        .filter(m -> m.castGet("id") == Integer.valueOf(6))
        .findFirst()
        .map(m -> m.<JSONMap>castGet("address"))
        .map(m -> m.<JSONMap>castGet("geo"))
        .map(m -> m.<String>castGet("lat"))
        .map(Double::valueOf)
        .orElse(0d)
      );
  }
  @Test
  public void testJSONMapCastGetAsNumber() throws IOException {
    JSONMap m1 = JSONMap.of("value", 15.1, "nonValue", "I'm not a number");
    // assertEquals(m1.castGetAsString("value"), "15.1");
    // default type is Number
    assertEquals(m1.castGetAsNumber("value", Number.class), Double.valueOf(15.1));
    // Double to Integer
    assertEquals(m1.castGetAsNumber("value", Integer.class), Integer.valueOf(15));
    assertEquals(m1.castGetAsNumber("value", Double.class), Double.valueOf(15.1));
    assertEquals(m1.castGetAsNumber("value", Long.class), Long.valueOf(15L));
    assertEquals(m1.castGetAsNumber("value", Short.class), Short.valueOf("15"));
    assertEquals(m1.castGetAsNumber("value", Byte.class), Byte.valueOf(Integer.valueOf(15).byteValue()));
    assertEquals(m1.castGetAsNumber("value", BigDecimal.class), BigDecimal.valueOf(15.1));
    assertEquals(m1.tryCastGetAsNumber("value", BigInteger.class), BigInteger.valueOf(15));
    assertEquals(m1.tryCastGetAsNumber("nonValue", 0d, Double.class), Double.valueOf(0d));
  }
}
