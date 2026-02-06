package com.kejin.expression.express;

import com.kejin.expression.enums.ValueType;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.TextConst;
import com.kejin.expression.var.Var;
import lombok.Getter;

import java.util.Set;
@Getter
/**
 * Case表达式类，用于实现switch-case语句中的case分支逻辑
 * 包含when条件表达式和then结果表达式，支持默认case处理
 */
public class CaseExpress implements Express {
    private final Var whenVar;
    private final Var thenVar;
    private final boolean defaultCase;
    public static final String DEFAULT = "default";

    /**
     * 构造函数，创建Case表达式实例
     * @param whenVar when条件表达式变量
     * @param thenVar then结果表达式变量
     */
    public CaseExpress(Var whenVar, Var thenVar) {
        this.whenVar = whenVar;
        this.thenVar = thenVar;
        this.defaultCase = (whenVar instanceof TextConst) && ((TextConst) whenVar).getValue().toString().equals(DEFAULT);
    }

    /**
     * 判断当前case是否为默认case
     * @return true表示是默认case，false表示不是默认case
     */
    public boolean isDefault() {
        return defaultCase;
    }


    /**
     * 获取表达式的值类型
     * @return then表达式的值类型
     */
    @Override
    public ValueType valueType() {
        return thenVar.valueType();
    }


    /**
     * 执行case匹配逻辑，根据switch值和when条件判断是否匹配
     * @param switchValue switch语句的比较值
     * @param param 参数集合
     * @return 匹配成功时返回then表达式的结果值，否则返回null
     */
    public Value<?> caseFill(Value<?> switchValue, ParamCollection param) {
        if (defaultCase) {
            return thenVar.execute(param);
        }
        Value<?> whenValue = whenVar.execute(param);
        if (switchValue == null) {
            if (whenValue.toBoolean()) {
                return thenVar.execute(param);
            }
        } else {
            if (switchValue.eq(whenValue)) {
                return thenVar.execute(param);
            }
        }
        return null;

    }

    /**
     * 执行表达式（未实现）
     * @param param 参数集合
     * @return null（该方法在CaseExpress中未实现）
     */
    @Override
    public Value<?> execute(ParamCollection param) {
        return null;
    }

    /**
     * 返回表达式的字符串表示
     * @return 格式为"whenVar:thenVar"的字符串
     */
    @Override
    public String toString() {
        return whenVar + ":" + thenVar;
    }

    /**
     * 收集表达式使用的所有参数名称
     * @param args 用于收集参数名称的集合
     */
    @Override
    public void usedArg(Set<String> args) {
        whenVar.usedArg(args);
        thenVar.usedArg(args);
    }

}
