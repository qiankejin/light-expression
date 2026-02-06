---
title: JaCoCo测试覆盖率提升实践
type: note
permalink: testing/JaCoCo测试覆盖率提升实践
tags:
- testing
- coverage
- jacoco
- best-practices
---

# JaCoCo测试覆盖率提升实践

## 概述

本文档记录了在light-expression项目中引入JaCoCo测试覆盖度统计插件并将单元测试的覆盖度从55%提升到80%的实践经验。

## JaCoCo插件配置

在项目中添加JaCoCo插件以统计测试覆盖率：

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.7</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## 覆盖率提升策略

### 1. 识别覆盖率低的模块

通过JaCoCo报告识别覆盖率较低的包和类：
- `com.kejin.expression.util` - 工具类
- `com.kejin.expression.operator.window` - 窗口函数
- `com.kejin.expression.enums` - 枚举类
- `com.kejin.expression.errors` - 错误处理类

### 2. 有针对性地编写测试

为每个低覆盖率模块编写专门的测试用例：
- 覆盖所有公共方法
- 测试边界条件
- 验证异常处理路径
- 测试枚举类的各种情况

### 3. 重构测试用例结构

将原来集中在`UnitCoverageTest`和`OperatorTest`中的测试用例按功能模块分布：
- 创建专门的运算符测试类
- 按包结构创建对应的测试类
- 确保每个功能模块都有独立的测试类

## 覆盖率提升过程

### 初始状态
- 总体覆盖率：约55%

### 中间状态
- 经过初步测试用例重构后：64.44%
- 经过窗口函数和工具类测试补充后：66.33%

### 目标达成
- 最终覆盖率：达到80%以上

## 有效测试用例编写

### 避免无效测试

不再编写仅做getter/setter验证的无效测试用例，而是专注于：

1. **核心功能测试**：针对表达式引擎的主要功能编写测试
2. **边界条件测试**：测试极限值和特殊输入
3. **错误路径测试**：验证异常情况的处理
4. **集成测试**：测试多个组件协同工作的场景

### 测试用例命名规范

采用中文描述性的测试方法名，如：
- `加法()`
- `大于()`
- `当前时间()`
- `Switch()`

## 测试工具类优化

优化`TestUtil`类以更好地支持测试用例编写：
- 提供多种类型的`execute`方法
- 包含`compileFail`方法测试预期失败情况
- 预定义测试参数映射

## 最佳实践总结

1. **持续监控**：定期运行JaCoCo插件检查覆盖率
2. **目标导向**：设定明确的覆盖率目标并努力达成
3. **质量优先**：重视测试用例的有效性而非数量
4. **结构合理**：按源代码结构组织测试用例
5. **全面覆盖**：不仅关注代码行覆盖率，还要关注分支覆盖率