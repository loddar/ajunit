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
package org.failearly.ajunit.universe;

import org.aspectj.lang.JoinPoint;
import org.failearly.ajunit.util.EnumUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * JoinPointType defines the supported join points of AJUnit and bind them to the JoinPoints of AspectJ.
 *
 * @see org.aspectj.lang.JoinPoint#getKind()
 */
public enum JoinPointType {
    /**
     * @see org.aspectj.lang.JoinPoint#METHOD_EXECUTION
     */
    METHOD_EXECUTION(JoinPoint.METHOD_EXECUTION) {
        @Override
        protected void accept(final Method method, final UniverseBuilder universeBuilder) {
            addMethodJoinpoint(method, universeBuilder);
        }
    },
    /**
     * @see JoinPoint#METHOD_CALL
     */
    METHOD_CALL(JoinPoint.METHOD_CALL) {
        @Override
        protected void accept(final Method method, final UniverseBuilder universeBuilder) {
            addMethodJoinpoint(method, universeBuilder);
        }
    },
    /**
     * @see JoinPoint#CONSTRUCTOR_EXECUTION
     */
    CONSTRUCTOR_EXECUTION(JoinPoint.CONSTRUCTOR_EXECUTION) {
        @Override
        protected void accept(final Constructor constructor, final UniverseBuilder universeBuilder) {
            addConstructorJoinPoint(constructor, universeBuilder);
        }
    },
    /**
     * @see JoinPoint#CONSTRUCTOR_CALL
     */
    CONSTRUCTOR_CALL(JoinPoint.CONSTRUCTOR_CALL) {
        @Override
        protected void accept(final Constructor constructor, final UniverseBuilder universeBuilder) {
            addConstructorJoinPoint(constructor, universeBuilder);
        }
    },
    /**
     * @see JoinPoint#FIELD_GET
     */
    FIELD_GET(JoinPoint.FIELD_GET) {
        @Override
        protected void accept(final Field field, final UniverseBuilder universeBuilder) {
            addFieldJoinPoint(field, universeBuilder);
        }
    },
    /**
     * @see JoinPoint#FIELD_SET
     */
    FIELD_SET(JoinPoint.FIELD_SET) {
        @Override
        protected void accept(final Field field, final UniverseBuilder universeBuilder) {
            addFieldJoinPoint(field, universeBuilder);
        }
    },
    /**
     * @see JoinPoint#STATICINITIALIZATION
     */
    _STATICINITIALIZATION(JoinPoint.STATICINITIALIZATION),
    /**
     * @see JoinPoint#PREINITIALIZATION
     */
    _PREINITIALIZATION(JoinPoint.PREINITIALIZATION),
    /**
     * @see JoinPoint#INITIALIZATION
     */
    _INITIALIZATION(JoinPoint.INITIALIZATION),
    /**
     * @see JoinPoint#EXCEPTION_HANDLER
     */
    _EXCEPTION_HANDLER(JoinPoint.EXCEPTION_HANDLER),
    /**
     * @see JoinPoint#SYNCHRONIZATION_LOCK
     */
    _SYNCHRONIZATION_LOCK(JoinPoint.SYNCHRONIZATION_LOCK),
    /**
     * @see JoinPoint#SYNCHRONIZATION_UNLOCK
     */
    _SYNCHRONIZATION_UNLOCK(JoinPoint.SYNCHRONIZATION_UNLOCK),
    /**
     * @see JoinPoint#ADVICE_EXECUTION
     */
    _ADVICE_EXECUTION(JoinPoint.ADVICE_EXECUTION),
    /**
     * Unknown join point.
     */
    _UNKNOWN(null);
    private static final Map<String, JoinPointType> SUPPORTED_JOIN_POINT_TYPES;
    private final String joinPointKind;

    static {
        SUPPORTED_JOIN_POINT_TYPES = EnumUtils.resolveValidEnums(
                            JoinPointType.class,
                            new EnumUtils.EnumKeyResolver<String, JoinPointType>() {
                                @Override
                                public String resolveKey(final JoinPointType enumValue) {
                                    return enumValue.joinPointKind;
                                }
                            }
                        );
    }

    private JoinPointType(final String joinPointKind) {
        this.joinPointKind = joinPointKind;
    }

    /**
     * resolve enum value from AspectJ join point.
     */
    static JoinPointType resolveFromJoinPoint(final JoinPoint joinPoint) {
        return EnumUtils.defaultIfNotFound(SUPPORTED_JOIN_POINT_TYPES, joinPoint.getKind(), _UNKNOWN);
    }

    /**
     * Creates MethodJoinPoint for all accepting join point types by calling {@link
     * UniverseBuilder#addMethodJoinpoint(java.lang.reflect.Method, JoinPointType)}.
     */
    static void buildUniverse(final Method method, final UniverseBuilder universeBuilder) {
        for (JoinPointType joinPointType : SUPPORTED_JOIN_POINT_TYPES.values()) {
            joinPointType.accept(method, universeBuilder);
        }
    }

    /**
     * Creates FieldJoinPoint for all accepting join point types by calling
     * {@link UniverseBuilder#addFieldJoinpoint(java.lang.reflect.Field, JoinPointType)}.
     */
    static void buildUniverse(final Field field, final UniverseBuilder universeBuilder) {
        for (JoinPointType joinPointType : SUPPORTED_JOIN_POINT_TYPES.values()) {
            joinPointType.accept(field, universeBuilder);
        }
    }

    /**
     * Creates ConstructorJoinPoint for all accepting join point types by calling {@link
     * UniverseBuilder#addConstructorJoinpoint(java.lang.reflect.Constructor, JoinPointType)}.
     */
    static void buildUniverse(final Constructor constructor, final UniverseBuilder universeBuilder) {
        for (JoinPointType joinPointType : SUPPORTED_JOIN_POINT_TYPES.values()) {
            joinPointType.accept(constructor, universeBuilder);
        }
    }

    /**
     * Overridden if the join point joinPointKind accepts {@link java.lang.reflect.Field}.
     *
     * @see UniverseBuilder#addFieldJoinpoint(java.lang.reflect.Field, JoinPointType)
     */
    protected void accept(final Field field, final UniverseBuilder universeBuilder) {
        // does not accept fields.
    }

    /**
     * Overridden if the join point joinPointKind accepts {@link java.lang.reflect.Constructor}.
     *
     * @see UniverseBuilder#addConstructorJoinpoint(java.lang.reflect.Constructor, JoinPointType)
     */
    protected void accept(final Constructor constructor, final UniverseBuilder universeBuilder) {
        // does not accept constructors.
    }

    /**
     * Overridden if the join point joinPointKind accepts {@link java.lang.reflect.Method}.
     *
     * @see UniverseBuilder#addMethodJoinpoint(java.lang.reflect.Method, JoinPointType)
     */
    protected void accept(final Method method, final UniverseBuilder universeBuilder) {
        // does not accept method.
    }

    protected final void addMethodJoinpoint(final Method method, final UniverseBuilder universeBuilder) {
        universeBuilder.addMethodJoinpoint(method, this);
    }

    protected final void addFieldJoinPoint(final Field field, final UniverseBuilder universeBuilder) {
        universeBuilder.addFieldJoinpoint(field, this);
    }

    protected final void addConstructorJoinPoint(final Constructor constructor, final UniverseBuilder universeBuilder) {
        universeBuilder.addConstructorJoinpoint(constructor, this);
    }

}

