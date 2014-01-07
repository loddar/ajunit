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

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for enum related operations.
 */
public abstract class EnumUtils {

    /**
     * Prefix for invalid/internal(not public) enum values.
     * @see #isValidEnumValue(Enum)
     */
    public static final String INVALID_ENUM_VALUE_PREFIX = "_";

    public interface EnumKeyResolver<T, E extends Enum<?>> {
        public T resolveKey(E enumValue);
    }

    /**
     * Default implementation using toLongString.
     */
    public static final EnumKeyResolver DEFAULT_ENUM_KEY_RESOLVER =new EnumKeyResolver<String,Enum<?>>() {
        @Override
        public String resolveKey(final Enum<?> enumValue) {
            return enumValue.toString();
        }
    };

    private EnumUtils() {
    }

    /**
     * Resolve all valid enumeration constants from {@code enumType} by using a customized
     * {@link EnumUtils.EnumKeyResolver}.
     */
    public static <T, E extends Enum<?>> Map<T, E> resolveValidEnums(
            final Class<E> enumType, final EnumKeyResolver<T,E> enumKeyResolver) {
        final Map<T,E> result=new HashMap<>();

        for (E anEnum : enumType.getEnumConstants()) {
            if( isValidEnumValue(anEnum) ) {
                result.put(enumKeyResolver.resolveKey(anEnum), anEnum);
            }
        }
        return result;
    }

    /**
     * Convenient method for resolving valid enums using {@link #DEFAULT_ENUM_KEY_RESOLVER}.
     * @see #resolveValidEnums(Class, EnumUtils.EnumKeyResolver).
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<?>> Map<String, E> resolveValidEnums(final Class<E> enumType) {
        return resolveValidEnums(enumType, DEFAULT_ENUM_KEY_RESOLVER);
    }


    /**
     * Lookup for a enum value by using {@code value}, if not found return {@code defaultValue}.
     */
    public static <T, E extends Enum<?>> E defaultIfNotFound(final Map<T, E> enumMap, final T value, final E defaultValue) {
        return ObjectUtils.defaultIfNull(enumMap.get(value), defaultValue);
    }

    /**
     * Returns {@code true} if the value indicates a valid enumeration value.
     */
    public static boolean isValidEnumValue(final Enum<?> anEnum) {
        return ! anEnum.name().startsWith(INVALID_ENUM_VALUE_PREFIX);
    }

}
