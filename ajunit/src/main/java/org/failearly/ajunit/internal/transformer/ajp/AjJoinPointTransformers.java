/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2014  Marko Umek (ajunit.contact(at)gmail.com)
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
package org.failearly.ajunit.internal.transformer.ajp;

import org.failearly.ajunit.internal.transformer.Transformer;

/**
 * AjJoinPointTransformers is a utility class which provides factory methods for {@link org.failearly.ajunit.internal.universe.AjJoinPoint} transformers.
 */
public final class AjJoinPointTransformers {

    private static final AjpMethodTransformer AJP_METHOD_TRANSFORMER = new AjpMethodTransformer();
    private static final AjpFieldTransformer AJP_FIELD_TRANSFORMER = new AjpFieldTransformer();
    private static final AjpConstructorTransformer AJP_CONSTRUCTOR_TRANSFORMER = new AjpConstructorTransformer();
    private static final AjpDeclaringClassTransformer AJP_DECLARING_CLASS_TRANSFORMER = new AjpDeclaringClassTransformer();

    private AjJoinPointTransformers() {
    }

    public static Transformer methodTransformer() {
        return AJP_METHOD_TRANSFORMER;
    }

    public static Transformer fieldTransformer() {
        return AJP_FIELD_TRANSFORMER;
    }

    public static Transformer constructorTransformer() {
        return AJP_CONSTRUCTOR_TRANSFORMER;
    }

    public static Transformer declaringClassTransformer() {
        return AJP_DECLARING_CLASS_TRANSFORMER;
    }
}
