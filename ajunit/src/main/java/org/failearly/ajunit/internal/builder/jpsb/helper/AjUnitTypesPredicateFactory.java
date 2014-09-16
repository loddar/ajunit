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

import org.failearly.ajunit.builder.types.*;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.collection.CollectionPredicates;
import org.failearly.ajunit.internal.predicate.number.IntegerPredicates;
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.predicate.string.StringPredicates;
import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.transformer.list.ListTransformers;

/**
 * AjUnitEnumerationTypesPredicateFactory provides predicate factory methods for ajUnit types (enumerations).
 *
 * @see org.failearly.ajunit.builder.types.LogicalOperator
 * @see org.failearly.ajunit.builder.types.ListOperator
 * @see org.failearly.ajunit.builder.types.NumberComparator
 * @see org.failearly.ajunit.builder.types.DimensionComparator
 * @see org.failearly.ajunit.builder.types.StringMatcher
 * @see org.failearly.ajunit.builder.types.Position
 */
@SuppressWarnings({"Convert2Lambda", "Anonymous2MethodRef"})
public final class AjUnitTypesPredicateFactory {
    private static final PredicateFactories<StringMatcher, String> STRING_MATCHER_PREDICATES = new PredicateFactories<>();
    private static final PredicateFactories<LogicalOperator, Void> LOGICAL_OPERATOR_PREDICATES = new PredicateFactories<>();
    private static final PredicateFactories<DimensionComparator, Integer> DIMENSION_COMPARATOR_PREDICATES = new PredicateFactories<>();
    private static final PredicateFactories<NumberComparator, Integer> NUMBER_COMPARATOR_PREDICATES = new PredicateFactories<>();
    private static final PredicateFactories<ListOperator, CompositePredicate> LIST_OPERATOR_PREDICATES = new PredicateFactories<>();

    static {
        createStringMatcherPredicates();
        createLogicalOperatorPredicates();
        createDimensionComparatorPredicates();
        createNumberComparatorPredicates();
        createListLogicalOperatorPredicates();
    }

    private static void createNumberComparatorPredicates() {
        NUMBER_COMPARATOR_PREDICATES.addFactory(NumberComparator.EQUALS, new PredicateFactory<Integer>() {
            @Override
            public Predicate createPredicate(Integer input) {
                return StandardPredicates.equalsTo(input);
            }
        });
        NUMBER_COMPARATOR_PREDICATES.addFactory(NumberComparator.LESS_THEN, new PredicateFactory<Integer>() {
            @Override
            public Predicate createPredicate(Integer input) {
                return IntegerPredicates.lessThen(input);
            }
        });
        NUMBER_COMPARATOR_PREDICATES.addFactory(NumberComparator.LESS_EQUALS_THEN, new PredicateFactory<Integer>() {
            @Override
            public Predicate createPredicate(Integer input) {
                return IntegerPredicates.lessEqualThen(input);
            }
        });
        NUMBER_COMPARATOR_PREDICATES.addFactory(NumberComparator.GREATER_THEN, new PredicateFactory<Integer>() {
            @Override
            public Predicate createPredicate(Integer input) {
                return IntegerPredicates.greaterThen(input);
            }
        });
        NUMBER_COMPARATOR_PREDICATES.addFactory(NumberComparator.GREATER_EQUALS_THEN, new PredicateFactory<Integer>() {
            @Override
            public Predicate createPredicate(Integer input) {
                return IntegerPredicates.greaterEqualThen(input);
            }
        });
    }

    private static void createListLogicalOperatorPredicates() {
        LIST_OPERATOR_PREDICATES.addFactory(ListOperator.AT_LEAST_ONE, new PredicateFactory<CompositePredicate>() {
            @Override
            public Predicate createPredicate(CompositePredicate predicate) {
                return CollectionPredicates.atLeastOne(predicate);
            }
        });
        LIST_OPERATOR_PREDICATES.addFactory(ListOperator.EACH, new PredicateFactory<CompositePredicate>() {
            @Override
            public Predicate createPredicate(CompositePredicate predicate) {
                return CollectionPredicates.each(predicate);
            }
        });
        LIST_OPERATOR_PREDICATES.addFactory(ListOperator.NONE, new PredicateFactory<CompositePredicate>() {
            @Override
            public Predicate createPredicate(CompositePredicate predicate) {
                return CollectionPredicates.none(predicate);
            }
        });
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
    }

    private static void createStringMatcherPredicates() {
        STRING_MATCHER_PREDICATES.addFactory(StringMatcher.EQUALS, new PredicateFactory<String>() {
            @Override
            public Predicate createPredicate(String input) {
                return StandardPredicates.equalsTo(input);
            }
        });
        STRING_MATCHER_PREDICATES.addFactory(StringMatcher.STARTS_WITH, new PredicateFactory<String>() {
            @Override
            public Predicate createPredicate(String input) {
                return StringPredicates.startsWith(input);
            }
        });
        STRING_MATCHER_PREDICATES.addFactory(StringMatcher.ENDS_WITH, new PredicateFactory<String>() {
            @Override
            public Predicate createPredicate(String input) {
                return StringPredicates.endsWith(input);
            }
        });
        STRING_MATCHER_PREDICATES.addFactory(StringMatcher.CONTAINS, new PredicateFactory<String>() {
            @Override
            public Predicate createPredicate(String input) {
                return StringPredicates.contains(input);
            }
        });
        STRING_MATCHER_PREDICATES.addFactory(StringMatcher.REGEX, new PredicateFactory<String>() {
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
                return StandardPredicates.equalsTo(dimension);
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

    private AjUnitTypesPredicateFactory() {
    }

    /**
     * Create string matcher predicate.
     *
     * @param pattern     the pattern String.
     * @param matcherType the matcher type.
     * @return the predicate.
     */
    public static Predicate createStringMatcherPredicate(String pattern, StringMatcher matcherType) {
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
     * Creates a dimension operator predicate.
     *
     * @param dimension           the dimension value
     * @param dimensionComparator the dimension comparator
     * @return the predicate.
     */
    public static Predicate createDimensionComparatorPredicate(int dimension, DimensionComparator dimensionComparator) {
        return DIMENSION_COMPARATOR_PREDICATES.createPredicate(dimensionComparator, dimension);
    }

    /**
     * Creates a number operator predicate.
     *
     * @param number           the number value
     * @param numberComparator the number comparator
     * @return the predicate.
     */
    public static Predicate createNumberComparatorPredicate(int number, NumberComparator numberComparator) {
        return NUMBER_COMPARATOR_PREDICATES.createPredicate(numberComparator, number);
    }

    /**
     * Create a list logical operator (composite) predicate.
     * @param listOperator the list logical operator.
     * @param compositePredicate the inner composite predicate to use.
     * @return the composite predicate.
     */
    public static CompositePredicate createListLogicalOperator(ListOperator listOperator, CompositePredicate compositePredicate) {
        return LIST_OPERATOR_PREDICATES.createCompositePredicate(listOperator, compositePredicate);
    }

    public static Transformer createArgumentPositionTransformer(Position relativeTo, int... positions) {
        if(relativeTo==Position.LAST) {
            return  ListTransformers.getElementsFromListEnd(positions);
        }

        return  ListTransformers.getElementsFromListStart(positions);
    }
}
