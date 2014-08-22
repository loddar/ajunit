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
package org.failearly.ajunit.internal.util;

import org.failearly.ajunit.internal.runner.AjUnitSetupError;
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
            throwIllegalArgumentException(MessageUtils.message("Parameter").arg(parameterName).part("is null"));
        }
    }

    public static void attributeIsNull(final Object object, final String attributeName) {
        if(object!=null) {
            throwIllegalArgumentException(MessageUtils.message("Attribute").arg(attributeName).part("is not null"));
        }
    }

    public static void attributeIsNotNull(final Object object, final String attributeName) {
        if(object==null) {
            throwIllegalArgumentException(MessageUtils.message("Attribute").arg(attributeName).part("is null"));
        }
    }

    public static void state(boolean state, String msg) {
        if( ! state ) {
            throwIllegalStateException(MessageUtils.message("Illegal state:").part(msg));
        }
    }

    public static void parameterNotEmpty(Collection<?> collection, String parameterName) {
        parameterNotNull(collection, parameterName);
        if(collection.isEmpty()) {
            throwIllegalArgumentException(MessageUtils.message("Parameter").arg(parameterName).part("is empty"));
        }
    }

    public static void parameter(boolean condition, String msg) {
        if( ! condition ) {
            throwIllegalArgumentException(MessageUtils.message("Illegal argument condition:").part(msg));
        }
    }

    public static void assertCondition(boolean condition, MessageBuilder messageBuilder) {
        if( ! condition ) {
            throwIllegalStateException(messageBuilder);
        }
    }

    private static void throwIllegalArgumentException(MessageBuilder messageBuilder) throws IllegalArgumentException {
        final String message=messageBuilder.build();
        LOGGER.error(message);
        throw new IllegalArgumentException(message);
    }

    private static void throwIllegalStateException(MessageBuilder messageBuilder) throws IllegalStateException {
        final String message=messageBuilder.build();
        LOGGER.error(message);
        throw new IllegalStateException(message);
    }

    public static void throwSetupError(MessageBuilder messageBuilder) {
        final String message=messageBuilder.build();
        LOGGER.error(message);
        throw new AjUnitSetupError(message);
    }
}
