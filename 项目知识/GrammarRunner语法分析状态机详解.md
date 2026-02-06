---
title: GrammarRunner语法分析状态机详解
type: note
permalink: architecture/GrammarRunner语法分析状态机详解
tags:
- architecture
- parser
- state-machine
- grammar
---

# GrammarRunner语法分析状态机详解

## 概述

GrammarRunner是light-expression引擎中的核心组件之一，负责将词法分析后的节点列表转换为最终的表达式树。该组件使用了一个有限状态机来处理不同类型的语法节点，并根据运算符优先级构建表达式树。

## 语法分析状态

### GrammarState枚举

GrammarRunner使用以下状态来控制语法分析过程：

#### START
- **含义**：语法分析的初始状态
- **触发条件**：分析开始或完成一次规约后
- **处理逻辑**：准备接收下一个语法节点

#### BROKER_REDUCE
- **含义**：括号规约状态
- **触发条件**：遇到左括号[BLOCKERS_LEFT](file:///D:/code/light-expression/src/main/java/com/kejin/expression/enums/Lexical.java#L13-L13)时进入此状态
- **处理逻辑**：处理括号内的表达式直到遇到右括号[BLOCKERS_RIGHT](file:///D:/code/light-expression/src/main/java/com/kejin/expression/enums/Lexical.java#L17-L17)

#### OPERATOR_REDUCE
- **含义**：双目运算符规约状态
- **触发条件**：遇到[OPERATOR](file:///D:/code/light-expression/src/main/java/com/kejin/expression/enums/Lexical.java#L7-L7)类型的节点
- **处理逻辑**：根据运算符优先级处理双目运算符表达式

#### SINGLE_REDUCE
- **含义**：单目运算符规约状态
- **触发条件**：遇到[SIMPLE_OPERATOR](file:///D:/code/light-expression/src/main/java/com/kejin/expression/enums/Lexical.java#L21-L21)类型的节点
- **处理逻辑**：处理单目运算符表达式

#### METHOD_REDUCE
- **含义**：函数规约状态
- **触发条件**：遇到[METHOD](file:///D:/code/light-expression/src/main/java/com/kejin/expression/enums/Lexical.java#L29-L29)类型的节点
- **处理逻辑**：处理函数调用表达式

#### ARG_LIST_REDUCE
- **含义**：参数列表规约状态
- **触发条件**：遇到[ARG_LIST](file:///D:/code/light-expression/src/main/java/com/kejin/expression/enums/Lexical.java#L25-L25)类型的节点
- **处理逻辑**：处理参数列表表达式

## 核心算法流程

### 主循环逻辑

GrammarRunner的主循环通过遍历节点列表并根据当前状态和节点类型进行相应的处理：

```java
while (start != end) {
    Node ex = tree.get(endPoint);
    Lexical kind = ex.lexical();
    // 根据kind和grammarStat进行状态转换和处理
}
```

### 状态转换逻辑

根据不同类型的词法节点，GrammarRunner执行以下处理：

#### METHOD节点处理
- 在START、SINGLE_REDUCE、OPERATOR_REDUCE、ARG_LIST_REDUCE状态下，转换到METHOD_REDUCE状态
- 更新起点指针到当前节点位置

#### OPERATOR节点处理
- 在START、ARG_LIST_REDUCE状态下，设置优先级并转换到OPERATOR_REDUCE状态
- 在OPERATOR_REDUCE状态下，比较优先级决定是否继续规约或回退处理
- 在SINGLE_REDUCE状态下，先处理单目运算符再回到START状态
- 在METHOD_REDUCE状态下，处理函数表达式后回到START状态

#### BLOCKERS_LEFT (左括号) 节点处理
- 在多数状态下，转换到BROKER_REDUCE状态并增加括号深度
- 为后续括号内容的递归处理做准备

#### BLOCKERS_RIGHT (右括号) 节点处理
- 减少括号深度
- 当深度为0时，表示括号匹配完成，递归处理括号内的表达式

#### ARG_LIST (参数列表) 节点处理
- 根据当前状态执行不同的规约操作
- 可能涉及单目运算符处理、双目运算符处理或函数处理

#### SIMPLE_OPERATOR (简单操作符) 节点处理
- 处理单目操作符，如逻辑非(!)、按位取反(~)等
- 根据优先级决定是否立即处理或等待更高优先级操作

## 关键处理函数

### express函数
- **作用**：处理双目运算符表达式
- **逻辑**：从栈中取出左操作数、操作符和右操作数，构建BinaryExpress或CaseExpress
- **返回值**：从栈中移除的节点数量

### singleExpress函数
- **作用**：处理单目运算符表达式
- **逻辑**：从栈中取出操作符和操作数，构建SingleExpress

### methodExpress函数
- **作用**：处理函数调用表达式
- **逻辑**：从栈中取出函数操作符和参数列表，构建MethodExpress

### commaExpress函数
- **作用**：处理参数列表中的逗号分隔符
- **逻辑**：将逗号分隔的参数组织成CommaExpress

## 括号处理机制

GrammarRunner使用[brokerDeep](file:///D:/code/light-expression/src/main/java/com/kejin/expression/compile/GrammarRunner.java#L33-L33)变量追踪括号的嵌套深度：

1. 遇到左括号时，深度加1，进入BROKER_REDUCE状态
2. 遇到右括号时，深度减1
3. 当深度归零时，完成当前括号对的处理，递归处理括号内的表达式

## 优先级处理机制

GrammarRunner通过[priority](file:///D:/code/light-expression/src/main/java/com/kejin/expression/compile/GrammarRunner.java#L37-L37)变量跟踪当前处理的运算符优先级：

1. 优先级数值越小表示优先级越高
2. 在OPERATOR_REDUCE状态下，比较当前运算符与已有运算符的优先级
3. 如果当前运算符优先级更低，则先处理高优先级运算符

## 错误处理

GrammarRunner在多种情况下会抛出[ExpressionException](file:///D:/code/light-expression/src/main/java/com/kejin/expression/errors/ExpressionException.java#L11-L93)：

- 语法错误：如运算符缺少操作数、括号未闭合等
- 参数错误：如函数参数数量不匹配
- 类型错误：如操作符与操作数类型不匹配

## 性能优化

1. **状态机设计**：通过有限状态机避免复杂的递归或回溯
2. **优先级跟踪**：通过优先级变量避免重复的优先级比较
3. **就地修改**：直接修改节点列表而非创建新列表，节省内存

## 应用场景

GrammarRunner适用于：
- 表达式引擎的语法分析
- 数学公式的解析
- 配置脚本的解析
- 查询语言的解析

## 扩展性考虑

1. 新增运算符只需在相应位置添加处理逻辑
2. 新增函数只需在methodExpress中添加支持
3. 优先级系统允许轻松调整运算符优先级

## 总结

GrammarRunner的语法分析状态机是一个精心设计的系统，通过有限状态机和优先级机制，能够正确处理各种复杂的表达式结构，同时保持良好的性能和扩展性。