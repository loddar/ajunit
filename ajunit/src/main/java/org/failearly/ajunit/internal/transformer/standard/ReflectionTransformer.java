/*
 * ajUnit - Unit Testing AspectJ.
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
package org.failearly.ajunit.internal.transformer.standard;

import org.failearly.ajunit.internal.transformer.TransformerBase;
import org.failearly.ajunit.internal.util.ClassUtils;
import org.failearly.ajunit.internal.util.DefaultClassCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * ReflectionTransformer is responsible for ...
 */
final class ReflectionTransformer extends TransformerBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionTransformer.class);
    private Method method=null;
    ReflectionTransformer(Class<?> clazz, String methodName) {
        this.method = getDeclaredMethod(clazz, methodName);
        if( this.method==null ) {
            this.method = lookupMethod(clazz, methodName);
        }
        if( this.method==null ) {
            LOGGER.warn("Method '{}()' does not exists on class '{}'", methodName, clazz.getName());
        }
    }

    private static Method lookupMethod(Class<?> clazz, String methodName) {
        final DefaultClassCollector classCollector = new DefaultClassCollector();
        ClassUtils.collectClassesAndInterfaces(clazz, classCollector);
        for (Class<?> currentClazz : classCollector) {
            final Method method=getDeclaredMethod(currentClazz, methodName);
            if( method!=null ) {
                return method;
            }
        }
        return null;
    }

    private static Method getDeclaredMethod(Class<?> clazz, String methodName) {
        try {
            return clazz.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException ex) {
            LOGGER.debug("Method '{}()' does not exists on class '{}'", methodName, clazz.getName());
        }

        return null;
    }

    @Override
    protected Object doTransform(Object input) {
        if( method==null ) {
            return null;
        }
        try {
            method.setAccessible(true);
            return method.invoke(input);
        } catch (Exception ex) {
            LOGGER.error("Exception caught", ex);
        }

        return null;
    }
}
