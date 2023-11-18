package com.vti.entity;



import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter(autoApply = true)
public class CategoryConvert implements AttributeConverter<Category.Type, String> {
    @Override
    public String convertToDatabaseColumn(Category.Type type) {
        if (type == null) {
            return null;
        }

        return type.getValue();
    }

    @Override
    public Category.Type convertToEntityAttribute(String sqlValue) {
        if (sqlValue == null) {
            return null;
        }

        return Category.Type.toEnum(sqlValue);
    }

}
