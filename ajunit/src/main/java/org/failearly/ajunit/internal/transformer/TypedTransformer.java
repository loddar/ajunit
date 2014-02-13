/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
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
package org.failearly.ajunit.internal.transformer;

/**
 * TypedTransformer is base class for type safe transformation.
 */
public abstract class TypedTransformer<I, O> extends TransformerBase {


    private final Class<I> inputClass;

    protected TypedTransformer(Class<I> inputClass) {
        this.inputClass = inputClass;
    }

    @Override
    protected final Object doTransform(Object input) {
        return doTypedTransform(doCast(input));
    }

    private I doCast(Object input) {
        return this.inputClass.cast(input);
    }

    /**
     * Does the actually (type safe) transformation.
     * @param input {@code not null} value.
     * @return {@code null} or output of type {@code O}.
     */
    protected abstract O doTypedTransform(final I input);
}
