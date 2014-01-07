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
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package org.failearly.ajunit.internal.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * AbstractClassVisitor is a abstract base implementation for ClassVisitor. Only override those visit methods you are
 * interested in.
 */
public abstract class AbstractClassVisitor implements ClassVisitor {
    protected AbstractClassVisitor() {
    }

    @Override
    public void visit(Class<?> declaringClass) {
        // not interested in declaring class
    }

    @Override
    public void visit(final Method method) {
        // not interested in methods.
    }

    @Override
    public void visit(final Constructor<?> constructor) {
        // not interested in constructors.
    }

    @Override
    public void visit(final Field field) {
        // not interested in fields.
    }
}
