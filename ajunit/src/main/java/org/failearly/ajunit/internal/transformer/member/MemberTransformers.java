/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2014  Marko Umek (ajunit.contact(at)gmail.com)
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
package org.failearly.ajunit.internal.transformer.member;

import org.failearly.ajunit.internal.transformer.Transformer;

/**
 * MemberTransformers provides factory methods for {@link java.lang.reflect.Member} related transformations.
 */
public abstract class MemberTransformers {

    private static final MemberNameTransformer MEMBER_NAME_TRANSFORMER = new MemberNameTransformer();
    private static final MemberModifiersTransformer MEMBER_MODIFIERS_TRANSFORMER = new MemberModifiersTransformer();

    private MemberTransformers() {
    }

    /**
     * Resolves of {@link java.lang.reflect.Member} based reflection classes the name.
     * @see java.lang.reflect.Member#getName()
     */
    public static Transformer nameTransformer() {
        return MEMBER_NAME_TRANSFORMER;
    }

    public static Transformer modifierTransformer() {
        return MEMBER_MODIFIERS_TRANSFORMER;
    }
}
