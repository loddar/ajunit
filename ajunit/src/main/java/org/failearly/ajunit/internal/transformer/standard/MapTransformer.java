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
import org.failearly.ajunit.internal.transformer.TypedListTransformer;

import java.util.LinkedList;
import java.util.List;

/**
 * Applies the given transformer on each element of the input list. Transformations which results in {@code null} will be ignored.
 */
final class MapTransformer  extends TypedListTransformer<List,Object> {
    private final Transformer transformer;

    MapTransformer(Transformer transformer) {
        super(List.class, "Map");
        this.transformer = transformer;
    }

    @Override
    protected List<Object> doTypedTransform(List inputList) {
        final List<Object> result=new LinkedList<>();
        for (Object input : inputList) {
            final Object transformed = transformer.transform(input);
            if(transformed!=null) {
                result.add(transformed);
            }
        }
        return result;
    }

    @Override
    protected String toString0() {
        return super.getName() + "(" + transformer + ")";
    }
}
