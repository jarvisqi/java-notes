package com.framework.handler;

import com.framework.common.BaseEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 枚举类型转换器，在SqlSessionFactory 中注册
 *
 * @author : Jarvis
 * @date : 2018/5/30
 */
public class EnumValueTypeHandler<T extends Enum<T>> extends BaseTypeHandler<T> {

    private final BaseTypeHandler typeHandler;

    public EnumValueTypeHandler(Class<T> tClass) {
        if (tClass == null) {
            throw new IllegalArgumentException("参数类型不能为空");
        }
        if (BaseEnum.class.isAssignableFrom(tClass)) {
            // 如果实现了 BaseCodeEnum 则使用我们自定义的转换器
            typeHandler = new BaseEnumTypeHandler(tClass);
        } else {
            // 默认转换器 也可换成 EnumOrdinalTypeHandler
            typeHandler = new EnumTypeHandler(tClass);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        typeHandler.setNonNullParameter(ps, i, parameter, jdbcType);
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return (T) typeHandler.getNullableResult(rs, columnName);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return (T) typeHandler.getNullableResult(rs, columnIndex);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return (T) typeHandler.getNullableResult(cs, columnIndex);
    }
}
