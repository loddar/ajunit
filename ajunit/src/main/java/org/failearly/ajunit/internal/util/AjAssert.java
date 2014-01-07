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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Some assert methods.
 */
public abstract class AjAssert {
    private static final Logger LOGGER = LoggerFactory.getLogger(AjAssert.class);

    private AjAssert() {
    }

    public static void parameterNotNull(final Object parameter, final String parameterName) {
        if(parameter==null) {
            LOGGER.error("ajUnit - Parameter {} is null.", parameterName);
            throw new IllegalArgumentException("ajUnit - Parameter '" + parameterName + "' is null");
        }
    }

    public static void attributeIsNull(final Object object, final String attributeName) {
        if(object!=null) {
            LOGGER.error("ajUnit - Attribute {} is not null.", attributeName);
            throw new IllegalArgumentException("ajUnit - Attribute '" + attributeName + "' is not null");
        }
    }

    public static void state(boolean state, String msg) {
        if( ! state ) {
            LOGGER.error("ajUnit - Illegal state: {}", msg);
            throw new IllegalStateException("ajUnit - Illegal state: " + msg);
        }
    }

    public static void parameterNotEmpty(Collection<?> collection, String parameterName) {
        parameterNotNull(collection, parameterName);
        if(collection.isEmpty()) {
            LOGGER.error("ajUnit - Parameter {} is empty.", parameterName);
            throw new IllegalArgumentException("ajUnit - Parameter '" + parameterName + "' is empty.");
        }
    }

    public static void parameter(boolean condition, String msg) {
        if( ! condition ) {
            LOGGER.error("ajUnit - Illegal argument condition: {}", msg);
            throw new IllegalArgumentException("ajUnit - Illegal argument condition: " + msg);
        }

    }
}
