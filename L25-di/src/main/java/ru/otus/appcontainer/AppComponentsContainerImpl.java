package ru.otus.appcontainer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

@SuppressWarnings("squid:S1068")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        Object o;
        try {
            o = configClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException("Cannot instantiate application config" + configClass.getName(), e);
        }
        List<Method> componentCreationMethods = List.of(configClass.getMethods());

        List<Method> componentCreationMethodsSorted = componentCreationMethods.stream()
                .filter(m -> m.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(m-> m.getAnnotation(AppComponent.class).order())).toList();

        for (var method : componentCreationMethodsSorted) {

            String componentName = method.getAnnotation(AppComponent.class).name();
            var component = createAppComponent(method, o, componentName);
            appComponents.add(component);

            if (appComponentsByName.get(componentName) != null) {
                throw new RuntimeException("Duplicate component name: " + componentName);
            } else {
                appComponentsByName.put(componentName, component);
            }

        }
    }


    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private Object createAppComponent(Method method, Object o, String componentName) {

        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            for (Object component : appComponents) {
                if (parameterTypes[i].isInstance(component)) {
                    args[i] = component;
                }
            }
        }
        try {
            return method.invoke(o, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Cannot create app component " + componentName, e);
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<Object> components =
                appComponents.stream().filter(componentClass::isInstance).toList();
        if (components.size() > 1) {
            throw new RuntimeException(
                    String.format("Given component class %s is already registered", componentClass.getName()));
        }
        Object component = components.getFirst();
        if (component == null) {
            throw new RuntimeException(
                    String.format("Given component class %s is not found", componentClass.getName()));
        } else {
            return componentClass.cast(component);
        }
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        Object component = appComponentsByName.get(componentName);
        if (component == null) {
            throw new IllegalArgumentException(String.format("Given component name %s is not found", componentName));
        }
        return (C) component;
    }
}
