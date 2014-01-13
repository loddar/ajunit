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
package org.failearly.ajunit.internal.transformer.standard;

import org.failearly.ajunit.internal.transformer.Transformer;

import java.util.Arrays;

/**
 * StandardTransformers provides factory methods for standard transformers.
 */
public abstract class StandardTransformers {

    public static final Transformer NULL_TRANSFORMER = new NullTransformer();

    private StandardTransformers() {}

    /**
     * Provides function composition of transformers: <code>(Tn ... T2&sdot;T1)(input)</code>.
     *
     * @param transformers the transformer to be compose.
     * @return the composed transformer.
     */
    public static Transformer transformerComposition(final Transformer... transformers) {
        return new TransformerComposition(Arrays.asList(transformers));
    }

    /**
     * Returns the (type safe) Transformer which returns the {@code input}.
     * FOR TESTING PURPOSES ONLY.
     */
    public static <T> Transformer identityTransformer(Class<T> clazz) {
        return new IdentityTransformer<>(clazz);
    }

    /**
     * Returns a Transformer, which returns always {@code null} on any {@code input}.
     * FOR TESTING PURPOSES ONLY.
     */
    public static Transformer nullTransformer() {
        return NULL_TRANSFORMER;
    }

}
