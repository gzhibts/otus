package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final EntityClassMetaDataImpl<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaDataImpl<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {

        return "SELECT * FROM " + this.entityClassMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        return "SELECT * FROM "
                + this.entityClassMetaData.getName()
                + " WHERE " + this.entityClassMetaData.getIdField().getName()
                + " = ?";
    }

    @Override
    public String getInsertSql() {

        List<Field> fields = this.entityClassMetaData.getFieldsWithoutId();
        int paramCount = fields.size();

        // columns = (column1, column2, ... , columnN)
        StringBuilder columnsSB = new StringBuilder("(");
        columnsSB.append(fields.getFirst().getName());
        fields.removeFirst();

        for (var fieldName : fields) {
            columnsSB.append(", ").append(fieldName.getName());
        }

        columnsSB.append(")");
        String columns = columnsSB.toString();

        // parameters = (?, ?, ... , ?)
        StringBuilder parametersSB = new StringBuilder("(");
        parametersSB.append("?, ".repeat(Math.max(0, paramCount - 1)));
        parametersSB.append("?)");

        String parameters = parametersSB.toString();

        // final queryText
        return "INSERT INTO " + this.entityClassMetaData.getName() +
                " " +
                columns +
                " VALUES " +
                parameters;
    }

    @Override
    public String getUpdateSql() {

        // column1 = value1, column2 = value2, ... columnN = valueN
        List<Field> fieldsWithoutId = this.entityClassMetaData.getFieldsWithoutId();
        StringBuilder names = new StringBuilder(fieldsWithoutId.getFirst().getName());
        names.append("= ?");
        fieldsWithoutId.removeFirst();
        for (Field field : fieldsWithoutId) {
            names.append(",").append(field.getName()).append("= ?");
        }

        // final queryText
        return "UPDATE " + this.entityClassMetaData.getName()
                + " SET " + names +
                " WHERE " + this.entityClassMetaData.getIdField().getName() + " = ?";
    }
}
