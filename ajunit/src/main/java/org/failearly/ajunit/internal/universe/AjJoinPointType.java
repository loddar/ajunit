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
package org.failearly.ajunit.internal.universe;

import org.aspectj.lang.JoinPoint;
import org.failearly.ajunit.internal.universe.matcher.JoinPointMatchers;
import org.failearly.ajunit.internal.util.EnumUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * AjJoinPointType defines the supported join points of AJUnit and bind them to the JoinPoints of AspectJ.
 *
 * @see org.aspectj.lang.JoinPoint#getKind()
 */
public enum AjJoinPointType {
    /**
     * @see org.aspectj.lang.JoinPoint#METHOD_EXECUTION
     */
    METHOD_EXECUTION(JoinPoint.METHOD_EXECUTION) {
        @Override
        public void accept(final Method method, final AjUniverse universe) {
            universe.addJoinpoint(this, method);
        }

        @Override
        public AjJoinPointMatcher getJoinPointMatcher() {
            return JoinPointMatchers.methodMatcher(this);
        }
    },
    /**
     * @see JoinPoint#METHOD_CALL
     */
    METHOD_CALL(JoinPoint.METHOD_CALL) {
        @Override
        public void accept(final Method method, final AjUniverse universe) {
            universe.addJoinpoint(this, method);
        }

        @Override
        public AjJoinPointMatcher getJoinPointMatcher() {
            return JoinPointMatchers.methodMatcher(this);
        }
    },
    /**
     * @see JoinPoint#CONSTRUCTOR_EXECUTION
     */
    CONSTRUCTOR_EXECUTION(JoinPoint.CONSTRUCTOR_EXECUTION) {
        @Override
        public void accept(final Constructor constructor, final AjUniverse universe) {
            universe.addJoinpoint(this, constructor);
        }

        @Override
        public AjJoinPointMatcher getJoinPointMatcher() {
            return JoinPointMatchers.constructorMatcher(this);
        }
    },
    /**
     * @see JoinPoint#CONSTRUCTOR_CALL
     */
    CONSTRUCTOR_CALL(JoinPoint.CONSTRUCTOR_CALL) {
        @Override
        public void accept(final Constructor constructor, final AjUniverse universe) {
            universe.addJoinpoint(this, constructor);
        }

        @Override
        public AjJoinPointMatcher getJoinPointMatcher() {
            return JoinPointMatchers.constructorMatcher(this);
        }
    },
    /**
     * @see JoinPoint#FIELD_GET
     */
    FIELD_GET(JoinPoint.FIELD_GET) {
        @Override
        public void accept(final Field field, final AjUniverse universe) {
            universe.addJoinpoint(this, field);
        }

        @Override
        public AjJoinPointMatcher getJoinPointMatcher() {
            return JoinPointMatchers.fieldMatcher(this);
        }
    },
    /**
     * @see JoinPoint#FIELD_SET
     */
    FIELD_SET(JoinPoint.FIELD_SET) {
        @Override
        public void accept(final Field field, final AjUniverse universe) {
            universe.addJoinpoint(this, field);
        }

        @Override
        public AjJoinPointMatcher getJoinPointMatcher() {
            return JoinPointMatchers.fieldMatcher(this);
        }
    },
// NOT YET IMPLEMENTED JOIN POINT TYPES.
    /**
     * @see JoinPoint#PREINITIALIZATION
     */
    @SuppressWarnings("unused")
    _PREINITIALIZATION(JoinPoint.PREINITIALIZATION),
    /**
     * @see JoinPoint#INITIALIZATION
     */
    @SuppressWarnings("unused")
    _INITIALIZATION(JoinPoint.INITIALIZATION),
    /**
     * @see JoinPoint#EXCEPTION_HANDLER
     */
    @SuppressWarnings("unused")
    _EXCEPTION_HANDLER(JoinPoint.EXCEPTION_HANDLER),
    /**
     * @see JoinPoint#STATICINITIALIZATION
     */
    @SuppressWarnings("unused")
    _STATICINITIALIZATION(JoinPoint.STATICINITIALIZATION),
    /**
     * @see JoinPoint#ADVICE_EXECUTION
     */
    @SuppressWarnings("unused")
    _ADVICE_EXECUTION(JoinPoint.ADVICE_EXECUTION),
    /**
     * @see JoinPoint#SYNCHRONIZATION_LOCK
     */
    @SuppressWarnings("unused")
    _SYNCHRONIZATION_LOCK(JoinPoint.SYNCHRONIZATION_LOCK),
    /**
     * @see JoinPoint#SYNCHRONIZATION_UNLOCK
     */
    @SuppressWarnings("unused")
    _SYNCHRONIZATION_UNLOCK(JoinPoint.SYNCHRONIZATION_UNLOCK),
    /**
     * Unknown join point.
     */
    _UNKNOWN("<unknown>");
    public static final Map<String, AjJoinPointType> SUPPORTED_JOIN_POINT_TYPES;
    private final String joinPointKind;

    static {
        SUPPORTED_JOIN_POINT_TYPES = EnumUtils.resolveValidEnums(
                            AjJoinPointType.class,
                            new EnumUtils.EnumKeyResolver<String, AjJoinPointType>() {
                                @Override
                                public String resolveKey(final AjJoinPointType enumValue) {
                                    return enumValue.joinPointKind;
                                }
                            }
                        );
    }

    private AjJoinPointType(final String joinPointKind) {
        this.joinPointKind = joinPointKind;
    }

    /**
     * resolve enum value from AspectJ join point.
     */
    public static AjJoinPointType resolveFromJoinPoint(final JoinPoint joinPoint) {
        return EnumUtils.defaultIfNotFound(SUPPORTED_JOIN_POINT_TYPES, joinPoint.getKind(), _UNKNOWN);
    }

    /**
     * Overridden if the join point joinPointKind accepts {@link java.lang.reflect.Field}.
     */
    public void accept(final Field field, final AjUniverse universe) {
        // does not accept fields.
    }

    /**
     * Overridden if the join point joinPointKind accepts {@link java.lang.reflect.Constructor}.
     */
    public void accept(final Constructor constructor, final AjUniverse universe) {
        // does not accept constructors.
    }

    /**
     * Overridden if the join point joinPointKind accepts {@link java.lang.reflect.Method}.
     */
    public void accept(final Method method, final AjUniverse universe) {
        // does not accept method.
    }

    /**
     * Returns the associated {@link AjJoinPointMatcher}.
     * The default implementation returns the {@link org.failearly.ajunit.internal.universe.matcher.NullMatcher}.
     *
     * @see org.failearly.ajunit.AjUnitClassicAspect
     */
    public AjJoinPointMatcher getJoinPointMatcher() {
        return JoinPointMatchers.nullMatcher();
    }

    /**
     * Returns {@code true} if {@link org.aspectj.lang.JoinPoint#getKind()} is equal to {@link #joinPointKind}.
     */
    public boolean isSameKind(JoinPoint joinPoint) {
        return this.joinPointKind.equals(joinPoint.getKind());
    }

    /**
     * Returns AspectJs Join Point Kind.
     * @see org.aspectj.lang.JoinPoint#getKind()
     */
    public String getJoinPointKind() {
        return this.joinPointKind;
    }


    @Override
    public final String toString() {
        return this.joinPointKind;
    }
}

