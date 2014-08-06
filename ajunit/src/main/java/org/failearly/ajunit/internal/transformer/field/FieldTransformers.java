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
package org.failearly.ajunit.internal.transformer.field;

import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.transformer.TypedListTransformer;
import org.failearly.ajunit.internal.transformer.TypedTransformer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * FieldTransformers provides factory methods for {@link java.lang.reflect.Field} related transformations.
 */
public final class FieldTransformers {

    private static final Transformer FIELD_TYPE_TRANSFORMER = new TypedTransformer<Field, Class<?>>(Field.class,"FieldType") {
        @Override
        protected Class<?> doTypedTransform(Field input) {
            return input.getType();
        }
    };
    private static final Transformer FIELD_DECLARED_ANNOTATIONS_TRANSFORMER = new TypedListTransformer<Field, Annotation>(Field.class,"FieldAnnotations") {
        @Override
        protected List<Annotation> doTypedTransform(Field input) {
            return convert(input.getDeclaredAnnotations());
        }
    };

    private FieldTransformers() {
    }

    /**
     * The returned Transformer executes {@link java.lang.reflect.Field#getType()}.
     */
    public static Transformer fieldTypeTransformer() {
        return FIELD_TYPE_TRANSFORMER;
    }

    /**
     * The returned Transformer executes {@link java.lang.reflect.Field#getDeclaredAnnotations()}.
     */
    public static Transformer fieldDeclaredAnnotationsTransformer() {
        return FIELD_DECLARED_ANNOTATIONS_TRANSFORMER;
    }
}
