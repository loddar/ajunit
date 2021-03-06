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
package org.failearly.ajunit.internal.transformer;

import java.util.Arrays;
import java.util.List;

/**
 * TypedListTransformer is a base class which could be used for one input value and a {@link java.util.List} of the output values.
 */
public abstract class TypedListTransformer<I,O> extends TypedTransformer<I,List<O>> {
    protected TypedListTransformer(Class<I> inputClass, String name) {
        super(inputClass, name);
    }

    /**
     * Utility method for converting an array to  a list object.
     * @param input any object array
     * @return the list object
     */
    protected final List<O> convert(O[] input) {
        return Arrays.asList(input);
    }
}
