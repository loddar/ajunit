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
package org.failearly.ajunit.internal.predicate.modifier;

import java.lang.reflect.Modifier;

/**
 * AjModifier contains all possible modifiers.
 *
 * @see java.lang.reflect.Modifier
 */
public enum AjModifier implements org.failearly.ajunit.modifier.ModifierMatcher {
    PUBLIC(Modifier.PUBLIC),
    PROTECTED(Modifier.PROTECTED),
    PACKAGE(Modifier.PUBLIC | Modifier.PROTECTED | Modifier.PRIVATE) {
        @Override
        public boolean match(int modifiers) {
            return ! super.match(modifiers);
        }
    },
    PRIVATE(Modifier.PRIVATE),
    STATIC(Modifier.STATIC),
    FINAL(Modifier.FINAL),
    SYNCHRONIZED(Modifier.SYNCHRONIZED),
    VOLATILE(Modifier.VOLATILE),
    TRANSIENT(Modifier.TRANSIENT),
    NATIVE(Modifier.NATIVE),
    ABSTRACT(Modifier.ABSTRACT),
    STRICT(Modifier.STRICT);

    private final int modifierValue;

    AjModifier(int modifierValue) {
        this.modifierValue = modifierValue;
    }

    @Override
    public boolean match(int modifiers) {
        return (modifiers & this.modifierValue) != 0;
    }

    public int getModifierValue() {
        return modifierValue;
    }


    @Override
    public String toString() {
        return name() + "("+ Integer.toBinaryString(modifierValue) +")";
    }
}
