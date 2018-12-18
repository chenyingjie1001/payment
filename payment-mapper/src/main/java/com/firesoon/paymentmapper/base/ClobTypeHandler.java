package com.firesoon.paymentmapper.base;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 得到的数据是List<Map>类型数据，此时，如果有返回的有clob字段时，数据是这样的oracle.sql.CLOB@63636de0 ，显然，这不是我想要的，我需要的是字符串数据
 * 那么怎么来处理clob字段呢，很简单
 * 就是定义类型处理器，来专门处理Clob字段，将Clob数据转换为字符串数据返回
 * @author create by yingjie.chen on 2018/11/29.
 * @version 2018/11/29 17:49
 */
@MappedJdbcTypes(JdbcType.CLOB)
public class ClobTypeHandler extends BaseTypeHandler<Object> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setObject(i, parameter);
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getString(columnIndex);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getString(columnIndex);
    }
}
