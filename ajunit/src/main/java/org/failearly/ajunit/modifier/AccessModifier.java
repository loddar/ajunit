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
package org.failearly.ajunit.modifier;

import org.failearly.ajunit.internal.predicate.modifier.AjModifier;

/**
 * AccessModifier defines the available access modifiers.
 * <br/>
 * Remark: It also include a explicit PACKAGE modifier, even though no keyword is defined.
 *
 * @see java.lang.reflect.Modifier
 */
public enum AccessModifier implements ModifierMatcher {
    /**
     * @see java.lang.reflect.Modifier#PUBLIC
     */
    PUBLIC(AjModifier.PUBLIC),
    /**
     * @see java.lang.reflect.Modifier#PROTECTED
     */
    PROTECTED(AjModifier.PROTECTED),
    /**
     * None of {@link #PUBLIC}, {@link #PROTECTED} or {@link #PRIVATE} modifier.
     */
    PACKAGE(AjModifier.PACKAGE),
    /**
     * @see java.lang.reflect.Modifier#PRIVATE
     */
    PRIVATE(AjModifier.PRIVATE);

    private final ModifierMatcher modifierMatcher;
    AccessModifier(ModifierMatcher modifierMatcher) {
        this.modifierMatcher = modifierMatcher;
    }


    @Override
    public boolean match(int modifiers) {
        return this.modifierMatcher.match(modifiers);
    }
}
