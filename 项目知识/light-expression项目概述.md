---
title: light-expression项目概述
type: note
permalink: overview/light-expression-项目概述
---

# light-expression 项目概述

这是一个轻量级表达式引擎，用于解析和计算表达式。

## 项目特性
- 支持多种运算符（算术、比较、逻辑运算符）
- 支持函数调用
- 支持变量引用
- 支持复杂表达式解析

## 主要包结构
- `com.kejin.expression.compile`: 编译相关，包括词法分析和语法分析
- `com.kejin.expression.enums`: 枚举类，包括词法类型、语法状态等
- `com.kejin.expression.errors`: 异常处理
- `com.kejin.expression.express`: 表达式相关
- `com.kejin.expression.operator`: 操作符实现
- `com.kejin.expression.param`: 参数处理
- `com.kejin.expression.util`: 工具类
- `com.kejin.expression.value`: 值类型
- `com.kejin.expression.var`: 变量相关

## 技术栈
- Java 8+
- Maven
- JUnit 4 (测试)
- Lombok

## 项目依赖
- JUnit 4.13 (测试)
- Lombok 1.18.24