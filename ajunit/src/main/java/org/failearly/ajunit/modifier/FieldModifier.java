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
 * FieldModifier contains the modifier available for fields excluding the access modifiers.
 *
 * @see java.lang.reflect.Modifier#FIELD_MODIFIERS
 * @see java.lang.reflect.Modifier#fieldModifiers()
 * @see org.failearly.ajunit.modifier.AccessModifier
 */
public enum FieldModifier implements ModifierMatcher {
    /**
     * @see java.lang.reflect.Modifier#STATIC
     */
    STATIC(AjModifier.STATIC),
    /**
     * @see java.lang.reflect.Modifier#FINAL
     */
    FINAL(AjModifier.FINAL),
    /**
     * @see java.lang.reflect.Modifier#VOLATILE
     */
    VOLATILE(AjModifier.VOLATILE),
    /**
     * @see java.lang.reflect.Modifier#TRANSIENT
     */
    TRANSIENT(AjModifier.TRANSIENT);

    private final ModifierMatcher modifierMatcher;
    FieldModifier(ModifierMatcher modifierMatcher) {
        this.modifierMatcher = modifierMatcher;
    }


    @Override
    public boolean match(int modifiers) {
        return this.modifierMatcher.match(modifiers);
    }
}

