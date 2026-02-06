package com.kejin.expression.express;

import com.kejin.TestUtil;
import com.kejin.expression.param.MapParam;
import com.kejin.expression.var.NumberConst;
import com.kejin.expression.var.Var;
import com.kejin.expression.value.Value;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BrokersExpressTest {

    @Test
    public void testBrokersExpressExecute(){
        TestUtil.execute("1+(1,2,3)",4);
    }
}