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
package org.failearly.ajunit.builder;

/**
 * NamedDimension is used by {@link org.failearly.ajunit.builder.MethodJoinPointSelector#byReturningArrayType(NamedDimension)}.
 */
public enum NamedDimension {
    VECTOR(1),
    LINE(1),

    MATRIX(2),
    AREA(2),

    THREE_DIM(3),
    SPACE(3),

    FOUR_DIM(4),
    SPACE_TIME(4);

    public final int value;

    NamedDimension(int dimension) {
        this.value = dimension;
    }
}
