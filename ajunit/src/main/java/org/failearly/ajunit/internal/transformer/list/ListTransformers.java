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

import java.util.List;

/**
 * ListTransformers provides factory methods for {@link java.util.List} related transformations.
 */
public final class ListTransformers {

    private static final Transformer LIST_SIZE_TRANSFORMER = new ListTransformerBase<Integer>("ListSize") {
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
    public static Transformer size() {
        return LIST_SIZE_TRANSFORMER;
    }


    /**
     * The returned transformer returns a new list which contains the values from the input list at given {@code positions} from the start of the list.
     *
     * If the input list is smaller then the max value of {@code positions} then the transformer returns {@code null}.
     *
     * @param positions the positions (from the first element) ({@code >=0}).
     *
     * @see java.util.List#get(int)
     */
    public static Transformer getElementsFromListStart(int... positions) {
        return new GetElementsFromListStartTransformer(positions);
    }

    /**
     * The returned transformer returns a new list which contains the values from the input list at given {@code positions} from the end of the list.
     *
     * If the input list is smaller then the max value of {@code positions} then the transformer returns {@code null}.
     *
     * @param positions the positions (from the last element) ({@code >=0}).
     *
     * @see java.util.List#get(int)
     */
    public static Transformer getElementsFromListEnd(int... positions) {
        return new GetElementsFromListEndTransformer(positions);
    }
}
