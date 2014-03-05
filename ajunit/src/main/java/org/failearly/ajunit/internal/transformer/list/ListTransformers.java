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
package org.failearly.ajunit.internal.transformer.list;

import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.util.AjAssert;

import java.util.List;

/**
 * ListTransformers provides factory methods for {@link java.util.List} related transformations.
 */
public abstract class ListTransformers {

    private static final Transformer LIST_SIZE_TRANSFORMER = new ListTransformerBase<Integer>() {
        @Override
        protected Integer doTypedTransform(final List input) {
            return input.size();
        }
    };

    private ListTransformers() {
    }

    /**
     * The returned Transformer executes {@link java.util.List#size()}.
     */
    public static Transformer sizeTransformer() {
        return LIST_SIZE_TRANSFORMER;
    }


    /**
     * The returned transformer returns the element from list at {@code position}.
     *
     * @param position the position (from the first element) ({@code >=0}).
     */
    public static Transformer getElementListTransformer(final int position) {
        checkPositions(position);
        return new GetListElementTransformer(position);
    }

    /**
     * The returned transformer returns the element from list at {@code position} from the end of list.
     *
     * @param position the position (from the first element) ({@code >=0}).
     */
    public static Transformer getElementListFromEndTransformer(final int position) {
        checkPositions(position);
        return new GetListElementFromEndTransformer(position);
    }

    /**
     * The returned transformer returns a new list which contains the values from the input list at given {@code positions}.
     * If the input list is smaller then the max value of {@code positions} then the transformer returns {@code null}.
     *
     * @param positions the positions (from the first element) ({@code >=0}).
     *
     * @see java.util.List#get(int)
     */
    public static Transformer getElementsFromList(int... positions) {
        checkPositions(positions);
        return new GetElementsFromListTransformer(positions);
    }

    private static void checkPositions(int... positions) {
        for (int position : positions) {
            AjAssert.parameter(position>=0, "position >= 0");
        }
    }
}
