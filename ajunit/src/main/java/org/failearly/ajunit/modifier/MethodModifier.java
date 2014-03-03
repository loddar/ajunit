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
package org.failearly.ajunit.modifier;

import org.failearly.ajunit.internal.predicate.modifier.AjModifier;

/**
 * MethodModifier contains the modifier available for methods excluding the access modifiers.
 *
 * @see java.lang.reflect.Modifier#METHOD_MODIFIERS
 * @see java.lang.reflect.Modifier#methodModifiers()
 * @see org.failearly.ajunit.modifier.AccessModifier
 */
public enum MethodModifier implements ModifierMatcher {
    /**
     * @see java.lang.reflect.Modifier#STATIC
     */
    STATIC(AjModifier.STATIC),
    /**
     * @see java.lang.reflect.Modifier#FINAL
     */
    FINAL(AjModifier.FINAL),
    /**
     * @see java.lang.reflect.Modifier#ABSTRACT
     */
    ABSTRACT(AjModifier.ABSTRACT),
    /**
     * @see java.lang.reflect.Modifier#STRICT
     */
    STRICT(AjModifier.STRICT),
    /**
     * @see java.lang.reflect.Modifier#SYNCHRONIZED
     */
    SYNCHRONIZED(AjModifier.SYNCHRONIZED),
    /**
     * @see java.lang.reflect.Modifier#NATIVE
     */
    NATIVE(AjModifier.NATIVE);

    private final ModifierMatcher modifierMatcher;
    MethodModifier(ModifierMatcher modifierMatcher) {
        this.modifierMatcher = modifierMatcher;
    }


    @Override
    public boolean match(int modifiers) {
        return this.modifierMatcher.match(modifiers);
    }
}

