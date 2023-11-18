package com.vti.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SizeConvert implements AttributeConverter<Size.Type, String> {
    @Override
    public String convertToDatabaseColumn(Size.Type type) {
        if (type == null) {
            return null;
        }
        return type.getValue();
    }

    @Override
    public Size.Type convertToEntityAttribute(String sqlValue) {
        if (sqlValue == null) {
            return null;
        }
        return Size.Type.toEnum(sqlValue);
    }
}
