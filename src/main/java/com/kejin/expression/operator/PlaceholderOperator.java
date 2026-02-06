package com.kejin.expression.operator;


import static com.kejin.expression.enums.OperatorsPriority.LEVEL_0;

/**
 * 占位计算符
 */
public abstract class PlaceholderOperator extends Operators {
    @Override
    public int priority() {
        return LEVEL_0;
    }

}
