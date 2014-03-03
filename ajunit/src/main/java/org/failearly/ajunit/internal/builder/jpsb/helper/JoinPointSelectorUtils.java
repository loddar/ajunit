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
package org.failearly.ajunit.internal.builder.jpsb.helper;

import org.failearly.ajunit.builder.StringMatcherType;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.predicate.string.StringPredicates;

/**
 * JoinPointSelectorUtilities is an utility class for shared functionality.
 */
final class JoinPointSelectorUtils {
    private static final PredicateFactories<StringMatcherType,String> STRING_MATCHER_PREDICATES =new PredicateFactories<>();

    static {
        STRING_MATCHER_PREDICATES.addFactory(StringMatcherType.EQUALS, new PredicateFactory<String>() {
            @Override
            public Predicate createPredicate(String input) {
                return StandardPredicates.predicateEquals(input);
            }
        });
        STRING_MATCHER_PREDICATES.addFactory(StringMatcherType.STARTS_WITH, new PredicateFactory<String>() {
            @Override
            public Predicate createPredicate(String input) {
                return StringPredicates.startsWith(input);
            }
        });

        STRING_MATCHER_PREDICATES.addFactory(StringMatcherType.ENDS_WITH, new PredicateFactory<String>() {
            @Override
            public Predicate createPredicate(String input) {
                return StringPredicates.endsWith(input);
            }
        });

        STRING_MATCHER_PREDICATES.addFactory(StringMatcherType.CONTAINS, new PredicateFactory<String>() {
            @Override
            public Predicate createPredicate(String input) {
                return StringPredicates.contains(input);
            }
        });

        STRING_MATCHER_PREDICATES.addFactory(StringMatcherType.REGEX, new PredicateFactory<String>() {
            @Override
            public Predicate createPredicate(String input) {
                return StringPredicates.regex(input);
            }
        });

    }

    private JoinPointSelectorUtils() {
    }

    /**
     * Create string matcher predicate.
     * @param pattern  the pattern String.
     * @param matcherType the matcher type.
     * @return the predicate.
     */
    static Predicate createStringMatcherPredicate(String pattern, StringMatcherType matcherType) {
        return STRING_MATCHER_PREDICATES.createPredicate(matcherType, pattern);
    }
}
