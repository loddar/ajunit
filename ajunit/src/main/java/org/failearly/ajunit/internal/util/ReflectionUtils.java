/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2014 Marko Umek (ajunit.contact(at)fail-early.com)
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
package org.failearly.ajunit.internal.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * ReflectionUtils contains a set of reflection utility methods.
 */
public abstract class ReflectionUtils {
    private ReflectionUtils() {
    }

    /**
     * Visit all methods, fields, constructors and the class itself.
     * @param declaringClass the class to be inspect.
     * @param visitor the visitor.
     */
    public static void visit(final Class<?> declaringClass, final ClassVisitor visitor) {
        visitor.visit(declaringClass);
        visitMethods(declaringClass, visitor);
        visitFields(declaringClass, visitor);
        visitConstructors(declaringClass, visitor);
    }


    /**
     * Visit all methods of specified class.
     *
     * @param declaringClass the class to be inspect.
     * @param visitor the visitor.
     *
     * @see ClassVisitor#visit(java.lang.reflect.Method)
     */
    public static void visitMethods(final Class<?> declaringClass, final ClassVisitor visitor)  {
        for (Method method : declaringClass.getDeclaredMethods()) {
            visitor.visit(method);
        }
    }

    /**
     * Visit all fields of specified class.
     *
     * @param declaringClass the class to be inspect.
     * @param visitor the visitor.
     *
     * @see ClassVisitor#visit(java.lang.reflect.Field)
     */
    public static void visitFields(final Class<?> declaringClass, final ClassVisitor visitor)  {
        for (Field field : declaringClass.getDeclaredFields()) {
            visitor.visit(field);
        }
    }

    /**
     * Visit all constructors of specified class.
     *
     * @param declaringClass the class to be inspect.
     * @param visitor the visitor.
     *
     * @see ClassVisitor#visit(java.lang.reflect.Constructor)
     */
    public static void visitConstructors(final Class<?> declaringClass, final ClassVisitor visitor)  {
        for (Constructor<?> constructor : declaringClass.getDeclaredConstructors()) {
            visitor.visit(constructor);
        }
    }


}
