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
package org.failearly.ajunit.internal.transformer.standard;

import org.failearly.ajunit.internal.transformer.Transformer;

import java.util.Arrays;

/**
 * StandardTransformers provides factory methods for standard transformers.
 */
public final class StandardTransformers {

    @SuppressWarnings("all")
    private static final Transformer NULL_TRANSFORMER = new Transformer() {
        @Override
        public Object transform(Object input) {
            return null;
        }
    };
    private static final ArraysAsListTransformer TO_LIST_DIM_1_TRANSFORMER = new ArraysAsListTransformer();
    private static final Transformer TO_LIST_DIM_2_TRANSFORMER = named("ToListDim2", compose(
            toListDim1(),
            map(toListDim1())
    ));

    private StandardTransformers() {}

    /**
     * Provides function composition of transformers: <code>(Tn ... T2&sdot;T1)(input)</code>.
     *
     * @param transformers the transformer to be compose.
     * @return the composed transformer.
     */
    public static Transformer compose(final Transformer... transformers) {
        return new TransformerComposition(Arrays.asList(transformers));
    }


    /**
     * Applies given transformer on each element on the list. So the expected input of the returned transformer is a {@link java.util.List}.
     * @param transformer the transformer to be applied
     * @return the mapping transformer
     */
    public static Transformer map(Transformer transformer) {
        return new MapTransformer(transformer);
    }

    /**
     * Returns the (type safe) Transformer which returns the {@code input}.
     * FOR TESTING PURPOSES ONLY.
     */
    public static <T> Transformer identity(Class<T> clazz) {
        return new IdentityTransformer<>(clazz);
    }

    /**
     * Returns a Transformer, which returns always {@code null} on any {@code input}.
     * FOR TESTING PURPOSES ONLY.
     */
    public static Transformer nullify() {
        return NULL_TRANSFORMER;
    }


    /**
     * Not yet used. The returned transformer calls the named method of specified class.
     * @param clazz the class
     * @param methodName the method name
     * @return the reflection transformer
     */
    public static Transformer reflection(Class<?> clazz, String methodName) {
        return new ReflectionTransformer(clazz, methodName);
    }

    /**
     * Returns a transformer which converts any 1 dimensional array into a 1 dimensional list.
     * @return the converting transformer
     *
     * @see java.util.Arrays#asList(Object[])
     */
    public static Transformer toListDim1() {
        return TO_LIST_DIM_1_TRANSFORMER;
    }

    /**
     * Returns a transformer which converts any 1 dimensional array into a 1 dimensional list and implies the additional Transformer.
     *
     * @param transformer the additional transformer to be applied.
     *
     * @return the converting transformer
     *
     * @see java.util.Arrays#asList(Object[])
     */
    public static Transformer toListDim1(Transformer transformer) {
        return named("toListDim1("+transformer.getClass().getSimpleName()+")",compose(
                    toListDim1(),
                    map(transformer)
               ));
    }


    /**
     * Returns a transformer which converts any 2 dimensional array into a 2 dimensional list.
     * @return the converting transformer
     *
     * @see java.util.Arrays#asList(Object[])
     */
    public static Transformer toListDim2() {
        return TO_LIST_DIM_2_TRANSFORMER;
    }

    /**
     * Returns a transformer which converts any 12dimensional array into a 2 dimensional list and applies the additional Transformer.
     *
     * @param transformer the additional transformer to be applied.
     *
     * @return the converting transformer
     *
     * @see java.util.Arrays#asList(Object[])
     */
    public static Transformer toListDim2(Transformer transformer) {
        return named("toListDim2("+transformer.getClass().getSimpleName()+")", compose(
                toListDim1(),
                map(toListDim1(transformer))
        ));
    }

    /**
     * Gives a transformer a name.
     * @param name the name of the transformer.
     * @param transformer the transformer to be applied
     * @return  the named transformer.
     */
    public static Transformer named(String name, Transformer transformer) {
        return new NamedTransformer(name, transformer);
    }
}
