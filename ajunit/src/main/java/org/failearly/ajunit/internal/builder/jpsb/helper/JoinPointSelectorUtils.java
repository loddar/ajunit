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

import org.failearly.ajunit.builder.DimensionComparator;
import org.failearly.ajunit.builder.LogicalOperator;
import org.failearly.ajunit.builder.StringMatcherType;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.number.IntegerPredicates;
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.predicate.string.StringPredicates;

/**
 * JoinPointSelectorUtilities is an utility class for shared functionality.
 */
public final class JoinPointSelectorUtils {
    private static final PredicateFactories<StringMatcherType, String> STRING_MATCHER_PREDICATES = new PredicateFactories<>();
    private static final PredicateFactories<LogicalOperator, Void> LOGICAL_OPERATOR_PREDICATES = new PredicateFactories<>();
    private static final PredicateFactories<DimensionComparator, Integer> DIMENSION_COMPARATOR_PREDICATES = new PredicateFactories<>();

    static {
        createStringMatcherPredicates();
        createLogicalOperatorPredicates();
        createDimensionComparatorPredicates();
    }

    public static final int NO_ARRAY = 0;

    private static void createLogicalOperatorPredicates() {
        LOGICAL_OPERATOR_PREDICATES.addFactory(LogicalOperator.AND, new PredicateFactory<Void>() {
            @Override
            public Predicate createPredicate(Void input) {
                return LogicalPredicates.and();
            }
        });
        LOGICAL_OPERATOR_PREDICATES.addFactory(LogicalOperator.OR, new PredicateFactory<Void>() {
            @Override
            public Predicate createPredicate(Void input) {
                return LogicalPredicates.or();
            }
        });
        LOGICAL_OPERATOR_PREDICATES.addFactory(LogicalOperator.NOR, new PredicateFactory<Void>() {
            @Override
            public Predicate createPredicate(Void input) {
                return LogicalPredicates.nor();
            }
        });
        LOGICAL_OPERATOR_PREDICATES.addFactory(LogicalOperator.ALL_OF, new PredicateFactory<Void>() {
            @Override
            public Predicate createPredicate(Void input) {
                return LogicalPredicates.and();
            }
        });
        LOGICAL_OPERATOR_PREDICATES.addFactory(LogicalOperator.ANY_OF, new PredicateFactory<Void>() {
            @Override
            public Predicate createPredicate(Void input) {
                return LogicalPredicates.or();
            }
        });
        LOGICAL_OPERATOR_PREDICATES.addFactory(LogicalOperator.NONE_OF, new PredicateFactory<Void>() {
            @Override
            public Predicate createPredicate(Void input) {
                return LogicalPredicates.nor();
            }
        });
        LOGICAL_OPERATOR_PREDICATES.addFactory(LogicalOperator.INTERSECT, new PredicateFactory<Void>() {
            @Override
            public Predicate createPredicate(Void input) {
                return LogicalPredicates.and();
            }
        });
        LOGICAL_OPERATOR_PREDICATES.addFactory(LogicalOperator.UNION, new PredicateFactory<Void>() {
            @Override
            public Predicate createPredicate(Void input) {
                return LogicalPredicates.or();
            }
        });
        LOGICAL_OPERATOR_PREDICATES.addFactory(LogicalOperator.COMPLEMENT, new PredicateFactory<Void>() {
            @Override
            public Predicate createPredicate(Void input) {
                return LogicalPredicates.nor();
            }
        });
    }

    private static void createStringMatcherPredicates() {
        STRING_MATCHER_PREDICATES.addFactory(StringMatcherType.EQUALS, new PredicateFactory<String>() {
            @Override
            public Predicate createPredicate(String input) {
                return StandardPredicates.equalsPredicate(input);
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

    private static void createDimensionComparatorPredicates() {
        DIMENSION_COMPARATOR_PREDICATES.addFactory(DimensionComparator.EQUALS, new PredicateFactory<Integer>() {
            @Override
            public Predicate createPredicate(Integer dimension) {
                return StandardPredicates.equalsPredicate(dimension);
            }
        });
        DIMENSION_COMPARATOR_PREDICATES.addFactory(DimensionComparator.GREATER_THEN, new PredicateFactory<Integer>() {
            @Override
            public Predicate createPredicate(Integer dimension) {
                return IntegerPredicates.greaterThen(dimension);
            }
        });
        DIMENSION_COMPARATOR_PREDICATES.addFactory(DimensionComparator.LESS_THEN, new PredicateFactory<Integer>() {
            @Override
            public Predicate createPredicate(Integer dimension) {
                return LogicalPredicates.and(
                        IntegerPredicates.greaterThen(NO_ARRAY),
                        IntegerPredicates.lessThen(dimension)
                );
            }
        });
        DIMENSION_COMPARATOR_PREDICATES.addFactory(DimensionComparator.MAX, new PredicateFactory<Integer>() {
            @Override
            public Predicate createPredicate(Integer dimension) {
                return LogicalPredicates.and(
                        IntegerPredicates.greaterThen(NO_ARRAY),
                        IntegerPredicates.lessEqualThen(dimension)
                );
            }
        });
        DIMENSION_COMPARATOR_PREDICATES.addFactory(DimensionComparator.MIN, new PredicateFactory<Integer>() {
            @Override
            public Predicate createPredicate(Integer dimension) {
                return IntegerPredicates.greaterEqualThen(dimension);
            }
        });
    }

    private JoinPointSelectorUtils() {
    }

    /**
     * Create string matcher predicate.
     *
     * @param pattern     the pattern String.
     * @param matcherType the matcher type.
     * @return the predicate.
     */
    public static Predicate createStringMatcherPredicate(String pattern, StringMatcherType matcherType) {
        return STRING_MATCHER_PREDICATES.createPredicate(matcherType, pattern);
    }

    /**
     * Create a logical predicate.
     *
     * @param logicalOperator the logical operator.
     * @return the logical predicate.
     */
    public static CompositePredicate createLogicalOperatorPredicate(LogicalOperator logicalOperator) {
        return LOGICAL_OPERATOR_PREDICATES.createCompositePredicate(logicalOperator);
    }

    /**
     * Creates a value operator predicate.
     *
     * @param dimension           the value
     * @param dimensionComparator the value comparator
     * @return the predicate.
     */
    public static Predicate createDimensionComparatorPredicate(int dimension, DimensionComparator dimensionComparator) {
        return DIMENSION_COMPARATOR_PREDICATES.createPredicate(dimensionComparator, dimension);
    }
}
