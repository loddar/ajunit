/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2013  Marko Umek (ajunit.contact(at)gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package org.failearly.ajunit.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;

/**
 * Utility class for class related operations.
 */
public final class ClassUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtils.class);


    private ClassUtils() {
    }

    /**
     * Collect all classes and interfaces for given classes.
     */
    public static Collection<Class<?>> getClassesAndInterfaces(Class<?>... classes) {
        return getClassesAndInterfaces(Arrays.asList(classes));
    }

    /**
     * Collect all classes and interfaces for given classes.
     */
    public static Collection<Class<?>> getClassesAndInterfaces(Collection<Class<?>> classes) {
        final DefaultClassCollector classCollector = new DefaultClassCollector();
        for (Class<?> aClass : classes) {
            collectClassesAndInterfaces(aClass, classCollector);
        }
        return classCollector.getClassCollection();
    }

    /**
     * Convenient class collector method which collects all classes and interfaces for given class. <code>
     * collectClasses(MyClass.class, myCollector); collectInterfaces(MyClass.class, myCollector); </code>
     *
     * @param aClass         the class to collect all interfaces and superclasses.
     * @param classCollector the class collector
     */
    public static void collectClassesAndInterfaces(Class<?> aClass, ClassCollector classCollector) {
        collectClasses(aClass, classCollector);
        collectInterfaces(aClass, classCollector);
    }

    /**
     * Collect all classes of {@code aClass} (including itself).
     *
     * @param aClass         the class to collect all super classes (not null)
     * @param classCollector the class collector instance  (not null)
     */
    public static void collectClasses(Class<?> aClass, ClassCollector classCollector) {
        classCollector.addClass(aClass);
        collectSuperclasses(aClass, classCollector);
    }

    /**
     * Collect all super classes of {@code aClass}.
     *
     * @param aClass              the class to collect all super classes (not null)
     * @param superclassCollector the super class collector instance  (not null)
     */
    public static void collectSuperclasses(Class<?> aClass, ClassCollector superclassCollector) {
        Class<?> nextClass=aClass;
        while (true) {
            nextClass = nextClass.getSuperclass();
            if (nextClass == null)
                break;

            superclassCollector.addClass(nextClass);
        }
    }

    /**
     * Collect all (direct or indirect) interfaces of {@code aClass}.
     *
     * @param aClass             the class to collect all super classes (not null)
     * @param interfaceCollector the interface collector
     */
    public static void collectInterfaces(Class<?> aClass, ClassCollector interfaceCollector) {
        collectInterfacesOf(aClass, interfaceCollector);
        collectInterfacesOfSuperclasses(aClass, interfaceCollector);
    }

    private static void collectInterfacesOfSuperclasses(Class<?> aClass, ClassCollector interfaceCollector) {
        final DefaultClassCollector superClasses = new DefaultClassCollector();
        collectSuperclasses(aClass, superClasses);
        for (Class<?> superClass : superClasses) {
            collectInterfacesOf(superClass, interfaceCollector);
        }
    }

    private static void collectInterfacesOf(Class<?> aClass, ClassCollector interfaceCollector) {
        for (Class<?> currentInterface : aClass.getInterfaces()) {
            interfaceCollector.addClass(currentInterface);

            // In case of extending an interface itself, it's necessary to collect
            // these interfaces, too, by climbing up the implementation tree of the interfaces.
            collectInterfacesOf(currentInterface, interfaceCollector);
        }
    }

    /**
     * Load a class by full qualified class name.
     * @param fqcn  full qualified class name.
     * @param initialize {@code true} if the class should be initialized, otherwise {@code false}.
     * @return the class object.
     * @throws ClassNotFoundException
     */
    public static Class<?> loadClass(String fqcn, boolean initialize) throws ClassNotFoundException {
        try {
            return Class.forName(fqcn, initialize, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("Class '{}' could not be found", fqcn);
            throw e;
        }
    }
}
