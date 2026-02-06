package com.kejin.expression.operator.method;

import com.kejin.TestUtil;
import org.junit.Test;

import java.util.Date;

public class MethodOperatorTest {
    @Test
    public void 正弦(){
        TestUtil.execute("ROUND_UP(SIN(1),4)",0.0175);
    }

    @Test
    public void 当前时间(){
        TestUtil.execute("TO_DATE()", new Date());
    }

    @Test
    public void 字符串拼接() {
        TestUtil.execute("CONCAT(false,\"true\")", "falsetrue");
    }

    @Test
    public void 阶梯取整() {
        TestUtil.execute("ROUND_STEP(5.2,0.3)", 5.4);
    }

    @Test
    public void Switch() {
        TestUtil.execute("SWITCH(I04N,1:\"a\",2:\"b\",3:\"c\",4:\"d\",5:\"e\")", "d");
    }
}