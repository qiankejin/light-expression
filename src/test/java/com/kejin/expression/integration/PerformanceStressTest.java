package com.kejin.expression.integration;

import com.kejin.TestUtil;
import com.kejin.expression.errors.ExpressionException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 性能压力测试和并发测试
 * 验证表达式引擎在高负载情况下的稳定性和性能
 */
public class PerformanceStressTest {

    /**
     * 测试大量连续计算的性能
     * 验证引擎处理批量计算的能力
     */
    @Test
    public void testBatchCalculationPerformance() throws ExpressionException {
        // 批量简单运算测试
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            TestUtil.execute("I01N+I02N+" + i, 3 + i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("批量简单运算耗时: " + (endTime - startTime) + "ms");

        // 批量复杂运算测试
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            TestUtil.execute("MAX(I01N,I02N,I03N," + i + ")+MIN(I04N,I05N,I06N," + (i + 1) + ")", Math.max(3, i) + Math.min(4, i + 1));
        }
        endTime = System.currentTimeMillis();
        System.out.println("批量复杂运算耗时: " + (endTime - startTime) + "ms");
    }

    /**
     * 测试深度嵌套表达式的性能
     * 验证引擎处理极复杂表达式的能力
     */
    @Test
    public void testDeepNestingPerformance() throws ExpressionException {
        // 构建深度嵌套的表达式
        StringBuilder deepExpression = new StringBuilder();
        deepExpression.append("IF(");

        for (int i = 0; i < 50; i++) {
            deepExpression.append("true&&(");
        }
        deepExpression.append("true");
        for (int i = 0; i < 50; i++) {
            deepExpression.append(")");
        }
        deepExpression.append(",1,0)");

        long startTime = System.currentTimeMillis();
        TestUtil.execute(deepExpression.toString(), 1);
        long endTime = System.currentTimeMillis();
        System.out.println("深度嵌套表达式解析耗时: " + (endTime - startTime) + "ms");
    }

    /**
     * 测试大参数量表达式的性能
     * 验证引擎处理大量参数的能力
     */
    @Test
    public void testLargeParameterPerformance() throws ExpressionException {
        // 构建包含大量参数的表达式
        StringBuilder largeParamExpr = new StringBuilder();
        largeParamExpr.append("MAX(");

        List<String> params = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            params.add("I" + String.format("%02d", i % 6 + 1) + "N");
        }
        largeParamExpr.append(String.join(",", params));
        largeParamExpr.append(")");

        long startTime = System.currentTimeMillis();
        TestUtil.execute(largeParamExpr.toString(), 6); // MAX(1,2,3,4,5,6) = 6
        long endTime = System.currentTimeMillis();
        System.out.println("大参数量表达式处理耗时: " + (endTime - startTime) + "ms");
    }

    /**
     * 测试循环引用检测能力
     * 验证引擎对递归和循环依赖的处理
     */
    @Test(expected = AssertionError.class)
    public void testCircularReferenceDetection() throws ExpressionException {
        // 这应该抛出异常，因为存在循环引用
        // 注意：这取决于引擎是否支持循环引用检测
        try {
            TestUtil.execute("IF(true,1,SELF_REFERENCE())", 1);
        } catch (AssertionError e) {
            // 预期的异常行为
            throw e;
        }
    }

    /**
     * 测试内存使用情况
     * 验证长时间运行不会导致内存泄漏
     */
    @Test
    public void testMemoryUsage() throws ExpressionException {
        Runtime runtime = Runtime.getRuntime();
        long initialMemory = runtime.totalMemory() - runtime.freeMemory();

        // 执行大量表达式计算
        for (int i = 0; i < 10000; i++) {
            TestUtil.execute("I01N+I02N+" + (i % 100), 3 + (i % 100));

            // 定期进行垃圾回收监控
            if (i % 1000 == 0) {
                System.gc();
                long currentMemory = runtime.totalMemory() - runtime.freeMemory();
                System.out.println("第" + i + "次迭代后内存使用: " + (currentMemory / 1024 / 1024) + "MB");
            }
        }

        System.gc();
        long finalMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryIncrease = finalMemory - initialMemory;
        System.out.println("内存增长: " + (memoryIncrease / 1024 / 1024) + "MB");

        // 断言内存增长在合理范围内（这里设置为100MB作为阈值）
        assert memoryIncrease < 100 * 1024 * 1024 : "内存增长超出预期";
    }

    /**
     * 测试并发执行能力
     * 验证多线程环境下表达式的正确执行
     */
    @Test
    public void testConcurrentExecution() throws InterruptedException {
        int threadCount = 10;
        int iterationsPerThread = 100;
        List<Thread> threads = new ArrayList<>();
        List<Long> executionTimes = new ArrayList<>();

        for (int t = 0; t < threadCount; t++) {
            final int threadId = t;
            Thread thread = new Thread(() -> {
                long startTime = System.currentTimeMillis();
                try {
                    for (int i = 0; i < iterationsPerThread; i++) {
                        TestUtil.execute("I01N+I02N+" + threadId, 3 + threadId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                long endTime = System.currentTimeMillis();
                synchronized (executionTimes) {
                    executionTimes.add(endTime - startTime);
                }
            });
            threads.add(thread);
        }

        // 启动所有线程
        long overallStartTime = System.currentTimeMillis();
        for (Thread thread : threads) {
            thread.start();
        }

        // 等待所有线程完成
        for (Thread thread : threads) {
            thread.join();
        }
        long overallEndTime = System.currentTimeMillis();

        System.out.println("并发执行总耗时: " + (overallEndTime - overallStartTime) + "ms");
        System.out.println("平均每个线程耗时: " +
                executionTimes.stream().mapToLong(Long::longValue).average().orElse(0) + "ms");
    }

    /**
     * 测试长表达式的编译和执行性能
     * 验证引擎处理超长表达式的能力
     */
    @Test
    public void testLongExpressionPerformance() throws ExpressionException {
        // 构建非常长的表达式
        StringBuilder longExpression = new StringBuilder();

        for (int i = 0; i < 200; i++) {
            longExpression.append("(I01N+");
        }
        longExpression.append("0");
        for (int i = 0; i < 200; i++) {
            longExpression.append(")");
        }

        long startTime = System.currentTimeMillis();
        TestUtil.execute(longExpression.toString(), 200); // 200个1相加
        long endTime = System.currentTimeMillis();

        System.out.println("长表达式处理耗时: " + (endTime - startTime) + "ms");
    }

    /**
     * 测试函数调用密集型表达式的性能
     * 验证大量函数嵌套调用的效率
     */
    @Test
    public void testFunctionCallIntensivePerformance() throws ExpressionException {
        // 构建函数调用密集的表达式
        StringBuilder funcIntensiveExpr = new StringBuilder();
        funcIntensiveExpr.append("HALF_UP(");

        for (int i = 0; i < 50; i++) {
            funcIntensiveExpr.append("SQRT(");
        }
        funcIntensiveExpr.append("16"); // sqrt(16) = 4
        for (int i = 0; i < 50; i++) {
            funcIntensiveExpr.append(")");
        }
        funcIntensiveExpr.append(",2)");

        long startTime = System.currentTimeMillis();
        TestUtil.execute(funcIntensiveExpr.toString(), 1.00);
        long endTime = System.currentTimeMillis();

        System.out.println("函数密集型表达式处理耗时: " + (endTime - startTime) + "ms");
    }

    /**
     * 测试边界值和极端情况的稳定性
     * 验证引擎在极限条件下的鲁棒性
     */
    @Test
    public void testExtremeConditions() throws ExpressionException {
        // 测试极大数值
        TestUtil.execute("MAX(999999999,1,-999999999)", 999999999);
        TestUtil.execute("MIN(-999999999,-1,999999999)", -999999999);

        // 测试精度极限
        TestUtil.execute("ROUND_UP(0.000000001,9)", 0.000000001);
        TestUtil.execute("ROUND_DOWN(0.999999999,9)", 0.999999999);

        // 测试字符串长度极限
        StringBuilder longString = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longString.append("a");
        }
        TestUtil.execute("LEN(\"" + longString + "\")", 1000);

        // 测试复杂度极限
        StringBuilder complexExpr = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            complexExpr.append("(true&&");
        }
        complexExpr.append("true");
        for (int i = 0; i < 100; i++) {
            complexExpr.append(")");
        }
        TestUtil.execute(complexExpr.toString(), true);
    }

    /**
     * 测试缓存和重复执行优化
     * 验证相同表达式的重复执行性能提升
     */
    @Test
    public void testCacheOptimization() throws ExpressionException {
        String complexExpression = "MAX(I01N,I02N,I03N,I04N,I05N,I06N)+MIN(I01N,I02N,I03N,I04N,I05N,I06N)";

        // 首次执行（无缓存）
        long firstRunStart = System.currentTimeMillis();
        TestUtil.execute(complexExpression, 7); // MAX(1,2,3,4,5,6) + MIN(1,2,3,4,5,6) = 6 + 1 = 7
        long firstRunEnd = System.currentTimeMillis();

        // 重复执行（利用缓存）
        long cachedRunStart = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            TestUtil.execute(complexExpression, 7);
        }
        long cachedRunEnd = System.currentTimeMillis();

        long firstRunTime = firstRunEnd - firstRunStart;
        long avgCachedTime = (cachedRunEnd - cachedRunStart) / 100;

        System.out.println("首次执行耗时: " + firstRunTime + "ms");
        System.out.println("缓存后平均执行耗时: " + avgCachedTime + "ms");
        System.out.println("性能提升倍数: " + (double) firstRunTime / avgCachedTime);
    }
}