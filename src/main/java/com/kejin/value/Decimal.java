package com.kejin.value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Decimal {
    private BigDecimal data;

    private Decimal(int data) {
        this.data = BigDecimal.valueOf(data);
    }

    private Decimal(double data) {
        this.data = BigDecimal.valueOf(data);
    }

    private Decimal(BigDecimal data) {
        this.data = data;
    }

    public Decimal plus(int num) {
        this.data = this.data.add(BigDecimal.valueOf(num));
        return this;
    }

    public Decimal plus(Decimal num) {
        this.data = this.data.add(num.data);
        return this;
    }

    public Decimal subtract(int num) {
        this.data = this.data.subtract(BigDecimal.valueOf(num));
        return this;
    }

    public Decimal subtract(Decimal num) {
        this.data = this.data.subtract(num.data);
        return this;
    }

    public Decimal subtract(double num) {
        this.data = this.data.subtract(BigDecimal.valueOf(num));
        return this;
    }

    public Decimal plus(double num) {
        this.data = this.data.add(BigDecimal.valueOf(num));
        return this;
    }

    public Decimal copy() {
        return new Decimal(this.data);
    }

    public Decimal multiply(int num) {
        this.data = this.data.multiply(BigDecimal.valueOf(num));
        return this;
    }

    public Decimal multiply(double num) {
        this.data = this.data.multiply(BigDecimal.valueOf(num));
        return this;
    }

    public Decimal multiply(Decimal num) {
        this.data = this.data.multiply(num.data);
        return this;
    }

    public Decimal divide(int num) {
        if (num == 0) {
            this.data = BigDecimal.ZERO;
        } else {
            this.data = this.data.divide(BigDecimal.valueOf(num), 10, RoundingMode.HALF_UP);
        }
        return this;
    }

    public Decimal divide(Decimal num) {
        if(num==null||num.data.equals(BigDecimal.ZERO)){
            this.data = BigDecimal.ZERO;
        }else {
            this.data = this.data.divide(num.data, 10, RoundingMode.HALF_UP);
        }
        return this;
    }

    public Decimal divide(BigDecimal num) {
        if(num==null||num.equals(BigDecimal.ZERO)){
            this.data = BigDecimal.ZERO;
        }else {
            this.data = this.data.divide(num, 10, RoundingMode.HALF_UP);
        }
        return this;
    }

    public Decimal divide(Double num) {
        if (num == null || num == 0) {
            this.data = BigDecimal.ZERO;
        } else {
            this.data = this.data.divide(BigDecimal.valueOf(num), 10, RoundingMode.HALF_UP);
        }
        return this;
    }

    public int round() {
        return this.data.setScale(0, RoundingMode.HALF_UP).intValue();
    }

    public Decimal round(int scale) {
        this.data = this.data.setScale(scale, RoundingMode.HALF_UP);
        return this;
    }
    public BigDecimal toDecimal(){
        return this.data;
    }

    public double toDouble() {
        return this.data.doubleValue();
    }

    public int upScale() {
        return this.data.setScale(0, RoundingMode.UP).intValue();
    }

    public Decimal up() {
        this.data = this.data.setScale(0, RoundingMode.UP);
        return this;
    }

    public Decimal up(int scale) {
        this.data = this.data.setScale(scale, RoundingMode.UP);
        return this;
    }

    public Decimal upScale(int scale) {
        this.data = this.data.setScale(scale, RoundingMode.UP);
        return this;
    }

    public int downScale() {
        return this.data.setScale(0, RoundingMode.DOWN).intValue();
    }

    public Decimal downScale(int scale) {
        this.data = this.data.setScale(scale, RoundingMode.DOWN);
        return this;
    }

    public int toInt(int roundType) {
        switch (roundType) {
            case 1:
                return upScale();
            case 2:
                return downScale();
            default:
                return round();
        }
    }

    public String format(DecimalFormat format) {
        return format.format(this.data);
    }

    @Override
    public String toString() {
        return data.toString();
    }

    public static Decimal of(int data) {
        return new Decimal(data);
    }

    public static Decimal of(double data) {
        return new Decimal(data);
    }

    public static Decimal of(BigDecimal data) {
        return new Decimal(data);
    }

}


