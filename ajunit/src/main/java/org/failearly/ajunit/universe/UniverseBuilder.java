/*
 * AJUnit - Unit Testing AspectJ pointcuts definitions.
 *
 * Copyright (C) 2013  Marko Umek (ajunit.contact(at)gmail.com)
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
package org.failearly.ajunit.universe;



import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * UniverseBuilder is responsible for ...
 */
public interface UniverseBuilder {

    /**
     * Builds a universe.
     * @return
     */
    Universe build();

    /**
     * Adds classes to the universe. Only leaf classes are necessary. The super classes will be added
     * automatically.
     *
     * @param classes
     */
    // void addClasses(Class<?>... classes);

    /**
     * Adds a MethodJoinPoint of {@code method} and {@code joinPointType} to the current universe.
     */
    void addMethodJoinpoint(Method method, AjJoinPointType joinPointType);

    /**
     * Adds a FieldJoinPoint of {@code field} and {@code joinPointType} to the current universe.
     */
    void addFieldJoinpoint(Field field, AjJoinPointType joinPointType);

    /**
     * Adds a ConstructorJoinPoint of  {@code constructor}  and {@code joinPointType} to the current universe.
     */
    void addConstructorJoinpoint(Constructor constructor, AjJoinPointType joinPointType);

}
