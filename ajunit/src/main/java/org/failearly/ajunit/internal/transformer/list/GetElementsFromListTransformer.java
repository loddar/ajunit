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

import java.util.LinkedList;
import java.util.List;

/**
 * GetElementsFromListTransformer is responsible for ...
 */
final class GetElementsFromListTransformer extends ListTransformerBase<List> {
    private final int[] positions;
    private final int maxPosition;

    GetElementsFromListTransformer(int... positions) {
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

    @Override
    @SuppressWarnings("unchecked")
    protected List doTypedTransform(List input) {
        if(maxPosition < input.size()) {
            return pickPositionsFromInput(input);
        }
        return null;

    }

    private List pickPositionsFromInput(List input) {
        final int size=input.size();
        final List result=new LinkedList();
        for (int position : positions) {
            if(position<size) {
                result.add(input.get(position));
            }
        }
        return result;
    }
}
