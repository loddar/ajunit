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

import org.failearly.ajunit.internal.util.AjAssert;

import java.util.ArrayList;
import java.util.List;

/**
 * AbstractGetElementsFromListTransformer is the base implementation for picking elements from a list of values.
 * The only difference is the calculation of the {@link #index(int, int)}.
 */
abstract class AbstractGetElementsFromListTransformer extends ListTransformerBase<List> {
    private final int[] positions;
    private final int maxPosition;

    AbstractGetElementsFromListTransformer(String name, int... positions) {
        super(name);
        checkPositions(positions);
        this.positions=positions;
        this.maxPosition = max(positions);
    }

    private static int max(int[] values) {
        int maxValue=Integer.MIN_VALUE;
        for (int value : values) {
            maxValue = Math.max(value, maxValue);
        }
        return maxValue;
    }

    private static void checkPositions(int... positions) {
        for (int position : positions) {
            AjAssert.parameter(position >= 0, "position >= 0");
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    protected List doTypedTransform(List input) {
        if(maxPosition < input.size()) {
            return pickPositionsFromInput(input);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private List pickPositionsFromInput(List input) {
        final int size=input.size();
        final int lastPositionOfList = size-1;
        final List result=new ArrayList(positions.length);
        for (int position : positions) {
            result.add(input.get(index(position, lastPositionOfList)));
        }
        return result;
    }

    /**
     * Calculate the index.
     * @param position one position of {@link #positions}
     * @param lastPositionOfList the last position of the list to be transformed.
     */
    abstract int index(int position, int lastPositionOfList);
}
