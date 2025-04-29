package com.example.Asum_BE.common.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;
import java.util.Properties;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})
})
public class SlowQueryInterceptor implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(SlowQueryInterceptor.class);
    private long thresholdMillis = 1000; // 1초 이상이면 slow query로 간주

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = invocation.proceed();

        long end = System.currentTimeMillis();
        long time = end - start;

        StatementHandler handler = (StatementHandler) getRealTarget(invocation.getTarget());
        BoundSql boundSql = handler.getBoundSql();
        String sql = boundSql.getSql().replaceAll("\\s+", " ");

        if (time >= thresholdMillis) {
            log.warn("[SLOW QUERY] {}ms : {}", time, sql);
        } else {
            log.debug("[QUERY] {}ms : {}", time, sql);
        }

        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String threshold = properties.getProperty("thresholdMillis");
        if (threshold != null) {
            this.thresholdMillis = Long.parseLong(threshold);
        }
    }

    private Object getRealTarget(Object target) throws Exception {
        if (Proxy.isProxyClass(target.getClass())) {
            Field h = target.getClass().getSuperclass().getDeclaredField("h");
            h.setAccessible(true);
            Plugin plugin = (Plugin) h.get(target);
            Field targetField = plugin.getClass().getDeclaredField("target");
            targetField.setAccessible(true);
            return getRealTarget(targetField.get(plugin));
        }
        return target;
    }
}