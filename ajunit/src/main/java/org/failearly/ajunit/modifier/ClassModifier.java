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
 * ClassModifier contains the modifier available for classes and interfaces excluding the access modifiers.
 *
 * @see java.lang.reflect.Modifier#CLASS_MODIFIERS
 * @see java.lang.reflect.Modifier#INTERFACE_MODIFIERS
 * @see java.lang.reflect.Modifier#classModifiers()
 * @see java.lang.reflect.Modifier#interfaceModifiers()
 * @see org.failearly.ajunit.modifier.AccessModifier
 */
public enum ClassModifier implements ModifierMatcher {
    /**
     * @see java.lang.reflect.Modifier#STATIC
     */
    STATIC(AjModifier.STATIC),
    /**
     * Not available for interfaces.
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
    STRICT(AjModifier.STRICT);

    private final ModifierMatcher modifierMatcher;
    ClassModifier(ModifierMatcher modifierMatcher) {
        this.modifierMatcher = modifierMatcher;
    }


    @Override
    public boolean match(int modifiers) {
        return this.modifierMatcher.match(modifiers);
    }
}
