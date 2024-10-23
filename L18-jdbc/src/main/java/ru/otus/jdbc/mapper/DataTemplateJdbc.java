package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Client;

/** Сохратяет объект в базу, читает объект из базы */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData entityClassMetaData;

    private static final Logger logger = LoggerFactory.getLogger(DataTemplateJdbc.class);

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, this.entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return createEntity(rs);
                }
                return null;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, this.entitySQLMetaData.getSelectAllSql(), List.of(), rs -> {
                List<T> result = new ArrayList<>();
                try {
                    while (rs.next()) {
                        result.add(createEntity(rs));
                    }
                    return result;
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new DataTemplateException(e);
                }
            })
            .orElseThrow(() -> new DataTemplateException(new RuntimeException("An error occurred while receiving data")));
    }

    @Override
    public long insert(Connection connection, T client) {

        List<Object> params = new ArrayList<>();
        List<Field> fields  = this.entityClassMetaData.getFieldsWithoutId();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                params.add(field.get(client));
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage(), e);
                throw new DataTemplateException(e);
            }
        }

        return dbExecutor.executeStatement(connection, this.entitySQLMetaData.getInsertSql(), params);

    }

    @Override
    public void update(Connection connection, T client) {

        List<Object> params = new ArrayList<>();
        List<Field> fields  = this.entityClassMetaData.getFieldsWithoutId();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                params.add(field.get(client));
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }

         dbExecutor.executeStatement(connection, this.entitySQLMetaData.getUpdateSql(), params);

    }

    private T createEntity(ResultSet rs) {

        Constructor<T> constructor = entityClassMetaData.getConstructor();
        var fields = entityClassMetaData.getAllFields();
        Object[] parameters = new Object[fields.size()];

        try {
            for (int i = 0; i < constructor.getParameterCount(); i++) {
                parameters[i] = rs.getObject(i + 1);
            }
            return constructor.newInstance(parameters);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }

    }

}
