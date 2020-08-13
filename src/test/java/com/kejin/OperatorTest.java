package com.kejin;

import org.junit.Test;

import static com.kejin.TestUtil.*;

public class OperatorTest {
    @Test
    public void 加法() {
        execute("1+1", 2);
    }
    @Test
    public void 加法编译失败(){
        compileFail("1+1+","双目运算符缺少右边参数");
    }
    @Test
    public void 减法() {
        execute("1-3", -2);
    }
    @Test
    public void 乘法() {
        execute("2*3", 6);
    }
    @Test
    public void 除法() {
        execute("6/3", 2.0);
    }
    @Test
    public void 字符串拼接() {
        execute("concat(false,\"true\")", "falsetrue");
    }
    @Test
    public void 非() {
        execute("!false", true);
    }
    @Test
    public void 且() {
        execute("true&&false", false);
    }
    @Test
    public void 或() {
        execute("true||false", true);
    }
    @Test
    public void 求余() {
        execute("11%2", 1);
    }
    @Test
    public void 幂() {
        execute("3^2", 1);
    }
    @Test
    public void 大于() {
        execute("1>2", false);
    }
    @Test
    public void 大于等于() {
        execute("2>=2", true);
    }
    @Test
    public void 小于() {
        execute("2<2", false);
    }
    @Test
    public void 小于等于() {
        execute("2<=2", true);
    }
    @Test
    public void 等于() {
        execute("3==2", false);
    }
}
