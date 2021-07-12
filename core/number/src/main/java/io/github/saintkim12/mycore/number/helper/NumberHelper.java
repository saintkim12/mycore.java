package io.github.saintkim12.mycore.number.helper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Optional;

public class NumberHelper {
  private static NumberHelper INSTANCE = null;

  private NumberHelper() {
  }

  /**
   * 싱글톤 클래스의 인스턴스를 얻는다.
   * @return  NumberHelper instance(INSTANCE)
   */
  public static NumberHelper getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new NumberHelper();
    }
    return INSTANCE;
  }
  /**
   * 객체를 Integer로 변환(파싱)한다.
   * <p>
   * 변환 실패 시 또는 null값을 변환 시도할 경우 null을 리턴한다.
   * @see #_tryParseTo(Object, Number, Class)
   * @see #parse
   * @param object
   * @return Integer로 변환된 값 또는 null
   */
  public Integer tryParseInteger(Object object) {
    return this._tryParseTo(object, Integer.class);
  }
  /**
   * 객체를 Integer로 변환(파싱)한다.
   * <p>
   * 변환 실패 시 또는 null값을 변환 시도할 경우 지정한 defaultValue을 리턴한다.
   * @see #_tryParseTo(Object, Number, Class)
   * @see #parse
   * @param object
   * @param defaultValue 변환 실패시 리턴받을 기본값
   * @return Integer로 변환된 값 또는 defaultValue
   */
  public Integer tryParseInteger(Object object, Integer defaultValue) {
    return this._tryParseTo(object, defaultValue, Integer.class);
  }
  /**
   * 객체를 Long으로 변환(파싱)한다.
   * <p>
   * 변환 실패 시 또는 null값을 변환 시도할 경우 null을 리턴한다.
   * @see #_tryParseTo(Object, Number, Class)
   * @see #parse
   * @param object
   * @return Long으로 변환된 값 또는 null
   */
  public Long tryParseLong(Object object) {
    return this._tryParseTo(object, Long.class);
  }
  /**
   * 객체를 Long으로 변환(파싱)한다.
   * <p>
   * 변환 실패 시 또는 null값을 변환 시도할 경우 지정한 defaultValue을 리턴한다.
   * @see #_tryParseTo(Object, Number, Class)
   * @see #parse
   * @param object
   * @param defaultValue 변환 실패시 리턴받을 기본값 
   * @return Long으로 변환된 값 또는 defaultValue
   */
  public Long tryParseLong(Object object, Long defaultValue) {
    return this._tryParseTo(object, defaultValue, Long.class);
  }
  /**
   * 객체를 Double로 변환(파싱)한다.
   * <p>
   * 변환 실패 시 또는 null값을 변환 시도할 경우 null을 리턴한다.
   * @see #_tryParseTo(Object, Number, Class)
   * @see #parse
   * @param object
   * @return Double로 변환된 값 또는 null
   */
  public Double tryParseDouble(Object object) {
    return this._tryParseTo(object, Double.class);
  }
  /**
   * 객체를 Double로 변환(파싱)한다.
   * <p>
   * 변환 실패 시 또는 null값을 변환 시도할 경우 지정한 defaultValue을 리턴한다.
   * @see #_tryParseTo(Object, Number, Class)
   * @see #parse
   * @param object
   * @param defaultValue 변환 실패시 리턴받을 기본값 
   * @return Double로 변환된 값 또는 defaultValue
   */
  public Double tryParseDouble(Object object, Double defaultValue) {
    return this._tryParseTo(object, defaultValue, Double.class);
  }
  /**
   * 객체를 Float으로 변환(파싱)한다.
   * <p>
   * 변환 실패 시 또는 null값을 변환 시도할 경우 null을 리턴한다.
   * @see #_tryParseTo(Object, Number, Class)
   * @see #parse
   * @param object
   * @return Float으로 변환된 값 또는 null
   */
  public Float tryParseFloat(Object object) {
    return this._tryParseTo(object, Float.class);
  }
  /**
   * 객체를 Float으로 변환(파싱)한다.
   * <p>
   * 변환 실패 시 또는 null값을 변환 시도할 경우 지정한 defaultValue을 리턴한다.
   * @see #_tryParseTo(Object, Number, Class)
   * @see #parse
   * @param object
   * @param defaultValue 변환 실패시 리턴받을 기본값 
   * @return Float으로 변환된 값 또는 defaultValue
   */
  public Float tryParseFloat(Object object, Float defaultValue) {
    return this._tryParseTo(object, defaultValue, Float.class);
  }
  /**
   * 객체를 Byte로 변환(파싱)한다.
   * <p>
   * 변환 실패 시 또는 null값을 변환 시도할 경우 null을 리턴한다.
   * @see #_tryParseTo(Object, Number, Class)
   * @see #parse
   * @param object
   * @return Byte로 변환된 값 또는 null
   */
  public Byte tryParseByte(Object object) {
    return this._tryParseTo(object, Byte.class);
  }
  /**
   * 객체를 Byte로 변환(파싱)한다.
   * <p>
   * 변환 실패 시 또는 null값을 변환 시도할 경우 지정한 defaultValue을 리턴한다.
   * @see #_tryParseTo(Object, Number, Class)
   * @see #parse
   * @param object
   * @param defaultValue 변환 실패시 리턴받을 기본값 
   * @return Byte로 변환된 값 또는 defaultValue
   */
  public Byte tryParseByte(Object object, Byte defaultValue) {
    return this._tryParseTo(object, defaultValue, Byte.class);
  }
  /**
   * 객체를 Short으로 변환(파싱)한다.
   * <p>
   * 변환 실패 시 또는 null값을 변환 시도할 경우 null을 리턴한다.
   * @see #_tryParseTo(Object, Number, Class)
   * @see #parse
   * @param object
   * @return Short으로 변환된 값 또는 null
   */
  public Short tryParseShort(Object object) {
    return this._tryParseTo(object, Short.class);
  }
  /**
   * 객체를 Short으로 변환(파싱)한다.
   * <p>
   * 변환 실패 시 또는 null값을 변환 시도할 경우 지정한 defaultValue을 리턴한다.
   * @see #_tryParseTo(Object, Number, Class)
   * @see #parse
   * @param object
   * @param defaultValue 변환 실패시 리턴받을 기본값 
   * @return Short으로 변환된 값 또는 defaultValue
   */
  public Short tryParseShort(Object object, Short defaultValue) {
    return this._tryParseTo(object, defaultValue, Short.class);
  }
  /**
   * 객체를 Number로 변환(파싱)한다.
   * <p>
   * 변환 실패 시 또는 null값을 변환 시도할 경우 null을 리턴한다.
   * @see #_tryParseTo(Object, Number, Class)
   * @see #parse
   * @param object
   * @return Number로 변환된 값 또는 null
   */
  public Number tryParseNumber(Object object) {
    return this._tryParseTo(object, Number.class);
  }
  /**
   * 객체를 Number로 변환(파싱)한다.
   * <p>
   * 변환 실패 시 또는 null값을 변환 시도할 경우 지정한 defaultValue을 리턴한다.
   * @see #_tryParseTo(Object, Number, Class)
   * @see #parse
   * @param object
   * @return Number로 변환된 값 또는 defaultValue
   */
  public Number tryParseNumber(Object object, Number defaultValue) {
    return this._tryParseTo(object, defaultValue, Number.class);
  }
  /**
   * 객체를 BigDecimal로 변환(파싱)한다.
   * <p>
   * 변환 실패 시 또는 null값을 변환 시도할 경우 지정한 defaultValue을 리턴한다.
   * @see #_tryParseTo(Object, Number, Class)
   * @see #parse
   * @param object
   * @return BigDecimal로 변환된 값 또는 null
   */
  public BigDecimal tryParseBigDecimal(Object object) {
    return this._tryParseTo(object, BigDecimal.class);
  }
  /**
   * 객체를 BigDecimal로 변환(파싱)한다.
   * <p>
   * 변환 실패 시 또는 null값을 변환 시도할 경우 지정한 defaultValue을 리턴한다.
   * @see #_tryParseTo(Object, Number, Class)
   * @see #parse
   * @param object
   * @param defaultValue 변환 실패시 리턴받을 기본값 
   * @return BigDecimal로 변환된 값 또는 defaultValue
   */
  public BigDecimal tryParseBigDecimal(Object object, BigDecimal defaultValue) {
    return this._tryParseTo(object, defaultValue, BigDecimal.class);
  }
  /**
   * 객체를 BigInteger로 변환(파싱)한다.
   * <p>
   * 변환 실패 시 또는 null값을 변환 시도할 경우 null을 리턴한다.
   * @see #_tryParseTo(Object, Number, Class)
   * @see #parse
   * @param object
   * @return BigInteger로 변환된 값 또는 null
   */
  public BigInteger tryParseBigInteger(Object object) {
    return this._tryParseTo(object, BigInteger.class);
  }
  /**
   * 객체를 BigInteger로 변환(파싱)한다.
   * <p>
   * 변환 실패 시 또는 null값을 변환 시도할 경우 지정한 defaultValue을 리턴한다.
   * @see #_tryParseTo(Object, Number, Class)
   * @see #parse
   * @param object
   * @param defaultValue 변환 실패시 리턴받을 기본값 
   * @return BigInteger로 변환된 값 또는 defaultValue
   */
  public BigInteger tryParseBigInteger(Object object, BigInteger defaultValue) {
    return this._tryParseTo(object, defaultValue, BigInteger.class);
  }

  /**
   * 객체를 지정한 클래스로 변환(파싱)한다.
   * <p>
   * 변환 실패 시 또는 null값을 변환 시도할 경우 null을 리턴한다.
   * @see #parse
   * @param <N> (java.lang.Number를 상속받는)변환할 클래스
   * @param object
   * @param _class 변환할 클래스(N값과 동일하나, 클래스 인스턴스를 사용해야하므로 명시적으로 넘겨야 함)
   * @return 변환된 값 또는 defaultValue
   */
  private <N extends Number> N _tryParseTo(Object object, Class<N> _class) {
    return this._tryParseTo(object, null, _class);
  }

  /**
   * 객체를 지정한 클래스로 변환(파싱)한다.
   * <p>
   * 변환 실패 시 또는 null값을 변환 시도할 경우 지정한 defaultValue을 리턴한다.
   * @see #parse
   * @param <N> (java.lang.Number를 상속받는)변환할 클래스
   * @param object
   * @param defaultValue 변환 실패시 리턴받을 기본값 
   * @param _class 변환할 클래스(N값과 동일하나, 클래스 인스턴스를 사용해야하므로 명시적으로 넘겨야 함)
   * @return 변환된 값 또는 defaultValue
   */
  private <N extends Number> N _tryParseTo(Object object, N defaultValue, Class<N> _class) {
    try {
      return this.parse(object, defaultValue, _class);
    } catch (NumberFormatException | ClassCastException e) {
      return defaultValue;
    }
  }

  /**
   * 객체를 지정한 클래스로 변환(파싱)한다.
   * <p>
   * null값을 변환 시도할 경우 지정한 defaultValue을 리턴한다.
   * <p>
   * 변환 실패 또는 타입 캐스팅 실패 시 에러 발생 
   * @see #parse
   * @param <N> (java.lang.Number를 상속받는)변환할 클래스
   * @param object
   * @param defaultValue 변환 실패시 리턴받을 기본값 
   * @param _class 변환할 클래스(N값과 동일하나, 클래스 인스턴스를 사용해야하므로 명시적으로 넘겨야 함)
   * @return 변환된 값 또는 defaultValue
   * @throws NumberFormatException
   * @throws ClassCastException
   */
  public <N extends Number> N parse(Object object, N defaultValue, Class<N> _class) {
    // 정확한 값을 위해 BigDecimal 이용
    BigDecimal _number = Optional.ofNullable(object).map(Object::toString).map(BigDecimal::new).orElse(null);
    if (_number == null) {
      return defaultValue;
    } else if ("java.lang.Integer".equals(_class.getCanonicalName())) {
      return _class.cast(Integer.valueOf(_number.intValue()));
    } else if ("java.lang.Long".equals(_class.getCanonicalName())) {
      return _class.cast(Long.valueOf(_number.longValue()));
    } else if ("java.lang.Double".equals(_class.getCanonicalName())) {
      return _class.cast(Double.valueOf(_number.doubleValue()));
    } else if ("java.lang.Float".equals(_class.getCanonicalName())) {
      return _class.cast(Float.valueOf(_number.floatValue()));
    } else if ("java.lang.Byte".equals(_class.getCanonicalName())) {
      return _class.cast(Byte.valueOf(_number.byteValue()));
    } else if ("java.lang.Short".equals(_class.getCanonicalName())) {
      return _class.cast(Short.valueOf(_number.shortValue()));
    } else if ("java.lang.Number".equals(_class.getCanonicalName())) {
      return _class.cast(Double.valueOf(_number.doubleValue()));
    } else if ("java.math.BigDecimal".equals(_class.getCanonicalName())) {
      return _class.cast(BigDecimal.valueOf(_number.doubleValue()));
    } else if ("java.math.BigInteger".equals(_class.getCanonicalName())) {
      return _class.cast(BigInteger.valueOf(_number.longValue()));
    } else {
      // It will be error(ClassCastException)!
      return _class.cast(_number);
    }
  }

  /**
   * Number 기반 객체의 소수점 첫번째 자리에서 반올림한다.(Math.round)
   * @param <T> (java.lang.Number를 상속받는)변환할 클래스
   * @param number
   * @return 소수점 첫번째 자리에서 반올림된 값
   */
  public <T extends Number> Number round(T number) {
    return this.round(number, 0);
  }

  /**
   * Number 기반 객체의 소수점 p+1번째 자리에서 반올림한다.(Math.round)
   * @param <T> (java.lang.Number를 상속받는)변환할 클래스
   * @param number
   * @param p 반올림할 소수점 자리수
   * @return 소수점 p+1번째 자리에서 반올림된 값
   */
  public <T extends Number> Number round(T number, Integer p) {
    return Double.valueOf(BigDecimal.valueOf(number.doubleValue()).setScale(p, RoundingMode.HALF_UP).doubleValue());
  }

}
