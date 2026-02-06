package com.kejin.expression.operator.calculate;

import com.kejin.TestUtil;
import org.junit.Test;

public class MathOperatorTest {
    @Test
    public void 加法() {
        TestUtil.execute("1+1", 2);
    }

    @Test
    public void 加法编译失败() {
        TestUtil.compileFail("1+1+", "语法错误:[双目运算符缺少右边参数");
    }

    @Test
    public void 减法() {
        TestUtil.execute("1-3", -2);
    }

    @Test
    public void 乘法() {
        TestUtil.execute("2*3", 6);
    }

    @Test
    public void 除法() {
        TestUtil.execute("6/3", 2.0);
    }

    @Test
    public void 求余() {
        TestUtil.execute("11%2", 1);
    }

    @Test
    public void 幂() {
        TestUtil.execute("3^2", 1);
    }

    @Test
    public void 非() {
        TestUtil.execute("!false", true);
    }

    @Test
    public void 且() {
        TestUtil.execute("true&&false", false);
    }

    @Test
    public void 或() {
        TestUtil.execute("true||false", true);
    }
}