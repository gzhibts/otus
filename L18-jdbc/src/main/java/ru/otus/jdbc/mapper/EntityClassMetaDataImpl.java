package ru.otus.jdbc.mapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> entityClassType;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.entityClassType = clazz;
    }

    @Override
    public String getName() {
        return this.entityClassType.getSimpleName().toLowerCase();
    }

    @Override
    public Constructor<T> getConstructor() {

        Constructor<?>[] entityClassConstructors = this.entityClassType.getConstructors();
        Arrays.sort(entityClassConstructors, Comparator.comparingInt(Constructor::getParameterCount));
        Constructor<?> maxArgsConstructor = entityClassConstructors[entityClassConstructors.length - 1];

        if (maxArgsConstructor.getParameterCount() < getAllFields().size()) {
            throw new RuntimeException("No all-args constructor in entity class");
        }

        try {
            return this.entityClassType.getConstructor(maxArgsConstructor.getParameterTypes());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Field getIdField() {

        List<Field> idFields = new ArrayList<>();

        for (Field field : this.entityClassType.getDeclaredFields()) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof IdField) {
                    idFields.add(field);
                }
            }
        }

        if (idFields.isEmpty()) {
            throw new RuntimeException("No ID field in Entity class");
        }

        if (idFields.size() > 1) {
            throw new RuntimeException("More than one ID field in Entity class");
        }

        return idFields.getFirst();
    }

    @Override
    public List<Field> getAllFields() {

        ArrayList<Field> fields = new ArrayList<>();
        Collections.addAll(fields, this.entityClassType.getDeclaredFields());
        return fields;

    }

    @Override
    public List<Field> getFieldsWithoutId() {

        var fields = getAllFields();
        fields.remove(getIdField());

        return fields;

    }

}
