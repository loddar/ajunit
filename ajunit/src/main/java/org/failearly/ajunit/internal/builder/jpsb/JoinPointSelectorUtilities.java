/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
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
package org.failearly.ajunit.internal.builder.jpsb;

import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.modifier.ModifierPredicate;
import org.failearly.ajunit.modifier.ModifierMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * JoinPointSelectorUtilities is an utility class for shared functionality.
 */
final class JoinPointSelectorUtils {
    private JoinPointSelectorUtils() {
    }

    /**
     * Convert {@link org.failearly.ajunit.modifier.ModifierMatcher} to {@link org.failearly.ajunit.internal.predicate.Predicate}s.
     * @param modifierMatchers modifier matchers.
     * @return Predicate List
     */
    static List<Predicate> toPredicates(ModifierMatcher... modifierMatchers) {
        final List<Predicate> predicates=new ArrayList<>(modifierMatchers.length);
        for (ModifierMatcher modifierMatcher : modifierMatchers) {
            predicates.add(ModifierPredicate.modifierPredicate(modifierMatcher));
        }
        return predicates;
    }
}
