package com.yupaits.batch.mapper;

import com.google.common.collect.Lists;
import com.yupaits.commons.consts.enums.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author yupaits
 * @date 2018/12/11
 */
public class HistoryDataBeanPropertyRowMapper<T> extends BeanPropertyRowMapper<T> {

    public HistoryDataBeanPropertyRowMapper(Class<T> mappedClass) {
        super(mappedClass);
    }

    private static final List<Class<?>> ENUM_LIST = Lists.newArrayList(Gender.class, MatchRule.class, MsgLevel.class,
            MsgType.class, NotifyAction.class, NotifyTargetType.class, PrivilegeType.class);

    @Override
    protected Object getColumnValue(ResultSet rs, int index, PropertyDescriptor pd) throws SQLException {
        Class<?> propertyType = pd.getPropertyType();
        try {
            for (Class<?> enumClass : ENUM_LIST) {
                if (enumClass.equals(propertyType)) {
                    Method fromCodeMethod = propertyType.getDeclaredMethod("fromCode", int.class);
                    return fromCodeMethod.invoke(null, rs.getInt(index));
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {

        }
        return super.getColumnValue(rs, index, pd);
    }
}
