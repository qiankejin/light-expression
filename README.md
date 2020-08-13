# light-expression
## 工具介绍：
### 1、轻量级的表达式计算工具
### 2、支持自定义函数扩展
### 3、基于运算符优先分析


## 使用方法：

```
//表达式
        String ex = "TY00027T==\"单进单出\"&&notIn(XT00010T,\"无水阳台\",\"冷水阳台\",\"热水阳台\")";
        //编译
        Var compile  = ExpressCompiler.compile(ex);
        //变量入参
        Map<String, Value> varMap = new HashMap<>();
        varMap.put("TY00027T", Value.of("单进单出"));
        varMap.put("XT00010T", Value.of("衣帽间"));
        varMap.put("TY00020B", Value.of(false));
        varMap.put("XT00025N", Value.of(1));
        //计算表达式
        Value result = compile.fill(varMap);
        System.out.println(compile + "=" + result);
```
## 性能
TY00027T=="单进单出"&&notIn(XT00010T,"无水阳台","冷水阳台","热水阳台")=true
{XT00025N=1, TY00027T=单进单出, XT00010T=衣帽间, TY00020B=false}
计算十万次耗时:10ms
编译十万次耗时:3ms
词法分析十万次耗时:50ms
语法分析十万次耗时:7ms
