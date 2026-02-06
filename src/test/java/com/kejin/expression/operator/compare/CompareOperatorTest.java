package com.kejin.expression.operator.compare;

import com.kejin.TestUtil;
import org.junit.Test;

public class CompareOperatorTest {
    @Test
    public void 大于() {
        TestUtil.execute("1>2", false);
    }

    @Test
    public void 大于等于() {
        TestUtil.execute("2>=2", true);
    }

    @Test
    public void 小于() {
        TestUtil.execute("2<2", false);
    }

    @Test
    public void 小于等于() {
        TestUtil.execute("2<=3", true);
    }

    @Test
    public void 等于() {
        TestUtil.execute("3==2", false);
    }
}