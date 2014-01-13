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

import java.util.List;

/**
 * GetListElementFromEndTransformer is responsible for ...
 */
final class GetListElementFromEndTransformer extends ListTransformerBase<Object> {
    private final int position;

    GetListElementFromEndTransformer(final int position) {
        this.position = position;
    }

    @Override
    protected Object doTypedTransform(final List list) {
        final int lastPositionOfCurrentList = list.size() - 1;
        if( position > lastPositionOfCurrentList ) {
            return null;
        }

        return list.get(lastPositionOfCurrentList - position);
    }
}
