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
package org.failearly.ajunit.internal.transformer.list;

import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.util.AjAssert;

/**
 * ListTransformers provides factory methods for {@link java.util.List} related transformations.
 */
public abstract class ListTransformers {

    private static final ListSizeTransformer LIST_SIZE_TRANSFORMER = new ListSizeTransformer();

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
        AjAssert.parameter(position>=0, "position >= 0");
        return new GetListElementTransformer(position);
    }

    /**
     * The returned transformer returns the element from list at {@code position} from the end of list.
     *
     * @param position the position (from the first element) ({@code >=0}).
     */
    public static Transformer getElementListFromEndTransformer(final int position) {
        AjAssert.parameter(position>=0, "position >= 0");
        return new GetListElementFromEndTransformer(position);
    }
}
