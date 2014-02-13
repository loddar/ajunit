/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2014 Marko Umek (http://fail-early.com/contact)
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
package org.failearly.ajunit.internal.universe.impl;

import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointType;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * AjJoinPointFactory is responsible for ...
 */
final class AjJoinPointFactory {
    private AjJoinPointFactory() {
    }

    static AjJoinPoint createAjJoinPoint(AjJoinPointType type, Method method) {
        return new MethodJoinPoint(type, method);
    }

    static AjJoinPoint createAjJoinPoint(AjJoinPointType type, Field field) {
        return new FieldJoinPoint(type, field);
    }

    static AjJoinPoint createAjJoinPoint(AjJoinPointType type, Constructor<?> constructor) {
        return new ConstructorJoinPoint(type, constructor);
    }
}
