package com.kejin.expression.value;

import com.kejin.expression.enums.ValueType;
import com.kejin.expression.util.DateUtil;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class DateValue extends Value<Date> {

    public DateValue(Date date) {
        super(date);
    }

    public DateValue() {
        super(new Date());
    }

    @Override
    public ValueType type() {
        return ValueType.DATE;
    }

    @Override
    protected String show() {
        return DateUtil.format(this.value);
    }

    @Override
    public boolean eq(Value<?> value) {
        if (value instanceof DateValue) {
            return value.toValue().equals(this.value);
        }
        return false;
    }

    @Override
    public int compareTo(Value o) {
        if (o.type() == ValueType.DATE) {
            return this.value.compareTo((Date) o.value);
        }
        return this.show().compareTo(o.show());
    }

    @Override
    public Date toDate() {
        return this.value;
    }
}
