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
package org.failearly.ajunit.internal.transformer.field;

import org.failearly.ajunit.internal.transformer.TypedListTransformer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Executes {@link java.lang.reflect.Field#getDeclaredAnnotations()}.
 */
final class FieldDeclaredAnnotationsTransformer extends TypedListTransformer<Field,Annotation> {
    protected FieldDeclaredAnnotationsTransformer() {
        super(Field.class);
    }

    @Override
    protected List<Annotation> doTypedTransform(Field input) {
        return convert(input.getDeclaredAnnotations());
    }
}
