package com.kejin.operator;


import static com.kejin.enums.OperatorsPriority.LEVEL_0;

/**
 * 占位计算符
 */
public abstract class PlaceholderOperator extends Operators {
    @Override
    public int priority() {
        return LEVEL_0;
    }

}
