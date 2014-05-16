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
package org.failearly.ajunit.internal.predicate.modifier;

import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.TypedPredicate;
import org.failearly.ajunit.modifier.ModifierMatcher;

/**
 * ModifierPredicate executes a {@link org.failearly.ajunit.modifier.ModifierMatcher} on modifier bit map (the input value).
 *
 * @see org.failearly.ajunit.internal.predicate.modifier.AjModifier
 */
public final class ModifierPredicate extends TypedPredicate<Integer> {

    private final ModifierMatcher modifierMatcher;

    private ModifierPredicate(ModifierMatcher modifierMatcher) {
        super(Integer.class,"Modifier("+modifierMatcher+")");
        this.modifierMatcher = modifierMatcher;
    }

    /**
     * The returned predicates evaluates to {@code true} if the {@code modifierMatcher} matches.
     */
    public static Predicate modifierPredicate(ModifierMatcher modifierMatcher) {
        return new ModifierPredicate(modifierMatcher);
    }


    @Override
    protected boolean doTypedTest(final Integer modifiers) {
        return modifierMatcher.match(modifiers);
    }
}
