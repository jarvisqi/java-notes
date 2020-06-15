package com.softmax.oauth2.security.interceptors;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Properties;

/**
 * Mybatis SQL 拦截器
 * Delete 语句 没有Where 条件拦截
 *
 * @author Jarvis
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class CheckSQLInterceptor implements Interceptor {

    private final Logger logger = LoggerFactory.getLogger(CheckSQLInterceptor.class);

    private static String SQL_DELETE_WHERE = "where";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        //获取方法的第0个参数，也就是MappedStatement。@Signature注解中的args中的顺序
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        //获取sql命令操作类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        final Object[] queryArgs = invocation.getArgs();
        final Object parameter = queryArgs[1];

        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        String sql = boundSql.getSql();

        if (SqlCommandType.DELETE.equals(sqlCommandType)) {
            //格式化sql
            sql = sql.replace("\n", "");
            if (!sql.toLowerCase().contains(SQL_DELETE_WHERE)) {
                sql = sql.replace(" ", "");
                logger.warn("删除语句中没有where条件,sql为:{}", sql);
                throw new SQLException("删除语句中没有where条件");
            }
            return invocation.proceed();
        }

        return null;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
