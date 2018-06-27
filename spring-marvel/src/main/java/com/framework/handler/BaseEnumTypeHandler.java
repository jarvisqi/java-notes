package com.framework.handler;

import com.framework.common.BaseEnum;
import com.framework.utils.EnumUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 默认枚举转换类
 *
 * @author : Jarvis
 * @date : 2018/5/29
 */
public class BaseEnumTypeHandler<T extends Enum<?> & BaseEnum> extends BaseTypeHandler<BaseEnum> {

    private Class<T> tClass;

    public BaseEnumTypeHandler(Class<T> enumType) {
        if (enumType == null) {
            throw new IllegalArgumentException("参数类型不能为空");
        }
        this.tClass = enumType;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseEnum parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public BaseEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return EnumUtil.valueOf(tClass, rs.getInt(columnName));
    }

    @Override
    public BaseEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return EnumUtil.valueOf(tClass, rs.getInt(columnIndex));
    }

    @Override
    public BaseEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return EnumUtil.valueOf(tClass, cs.getInt(columnIndex));
    }
}
