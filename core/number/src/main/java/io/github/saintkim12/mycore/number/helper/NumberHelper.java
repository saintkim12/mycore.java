package io.github.saintkim12.mycore.number.helper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Optional;

public class NumberHelper {
  private static NumberHelper INSTANCE = null;

  private NumberHelper() {
  }

  public static NumberHelper getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new NumberHelper();
    }
    return INSTANCE;
  }

  public Integer tryParseInteger(Object object) {
    return this._tryParseTo(object, Integer.class);
  }

  public Integer tryParseInteger(Object object, Integer defaultValue) {
    return this._tryParseTo(object, defaultValue, Integer.class);
  }

  public Long tryParseLong(Object object) {
    return this._tryParseTo(object, Long.class);
  }

  public Long tryParseLong(Object object, Long defaultValue) {
    return this._tryParseTo(object, defaultValue, Long.class);
  }

  public Double tryParseDouble(Object object) {
    return this._tryParseTo(object, Double.class);
  }

  public Double tryParseDouble(Object object, Double defaultValue) {
    return this._tryParseTo(object, defaultValue, Double.class);
  }

  public Float tryParseFloat(Object object) {
    return this._tryParseTo(object, Float.class);
  }

  public Float tryParseFloat(Object object, Float defaultValue) {
    return this._tryParseTo(object, defaultValue, Float.class);
  }

  public Byte tryParseByte(Object object) {
    return this._tryParseTo(object, Byte.class);
  }

  public Byte tryParseByte(Object object, Byte defaultValue) {
    return this._tryParseTo(object, defaultValue, Byte.class);
  }

  public Short tryParseShort(Object object) {
    return this._tryParseTo(object, Short.class);
  }

  public Short tryParseShort(Object object, Short defaultValue) {
    return this._tryParseTo(object, defaultValue, Short.class);
  }

  public Number tryParseNumber(Object object) {
    return this._tryParseTo(object, Number.class);
  }

  public Number tryParseNumber(Object object, Number defaultValue) {
    return this._tryParseTo(object, defaultValue, Number.class);
  }

  public BigDecimal tryParseBigDecimal(Object object) {
    return this._tryParseTo(object, BigDecimal.class);
  }

  public BigDecimal tryParseBigDecimal(Object object, BigDecimal defaultValue) {
    return this._tryParseTo(object, defaultValue, BigDecimal.class);
  }

  public BigInteger tryParseBigInteger(Object object) {
    return this._tryParseTo(object, BigInteger.class);
  }

  public BigInteger tryParseBigInteger(Object object, BigInteger defaultValue) {
    return this._tryParseTo(object, defaultValue, BigInteger.class);
  }

  private <N extends Number> N _tryParseTo(Object object, Class<N> _class) {
    return this._tryParseTo(object, null, _class);
  }

  private <N extends Number> N _tryParseTo(Object object, N defaultValue, Class<N> _class) {
    try {
      return this.parse(object, defaultValue, _class);
    } catch (NumberFormatException | ClassCastException e) {
      return defaultValue;
    }
  }

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

  public <T extends Number> Number round(T number) {
    return this.round(number, 0);
  }

  public <T extends Number> Number round(T number, Integer p) {
    return BigDecimal.valueOf(number.doubleValue()).setScale(p, RoundingMode.HALF_UP).doubleValue();
  }

}
