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

import org.failearly.ajunit.internal.transformer.TypedTransformer;

/**
* IdentityTransformer returns the input.
*/
final class IdentityTransformer<T> extends TypedTransformer<T,T> {
    public IdentityTransformer(Class<T> clazz) {
        super(clazz, "Identity");
    }

    @Override
    protected T doTypedTransform(T input) {
        return input;
    }
}
