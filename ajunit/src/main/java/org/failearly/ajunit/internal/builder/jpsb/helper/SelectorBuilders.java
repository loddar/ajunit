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

import org.failearly.ajunit.internal.builder.Builder;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.transformer.ajp.AjpTransformers;
import org.failearly.ajunit.internal.transformer.clazz.ClassTransformers;
import org.failearly.ajunit.internal.transformer.method.MethodTransformers;
import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;

/**
 * SelectorBuilders provides factory methods for {@link org.failearly.ajunit.internal.builder.jpsb.helper.SelectorBuilderBase}.
 */
public final class SelectorBuilders {
    private SelectorBuilders() {
    }

    /**
     * Creates a {@link org.failearly.ajunit.internal.builder.jpsb.helper.MethodSelectorBuilder} which contains
     * the actually selectors excluding the logical structure builders.
     *
     * @param predicateBuilder the predicate builder instance
     * @return the new builder selector instance.
     */
    public static <T extends Builder> MethodSelectorBuilder<T> createMethodSelectorBuilder(final T predicateBuilder) {
        return new MethodSelectorBuilder<>(createPredicateAdder(predicateBuilder, AjpTransformers.methodTransformer()));
    }

    public static <T extends Builder> ClassSelectorBuilder<T> createDeclaringClassSelectorBuilder(T predicateBuilder) {
        return new ClassSelectorBuilder<>(createPredicateAdder(predicateBuilder, AjpTransformers.declaringClassTransformer()));
    }

    public static <T extends Builder> ClassSelectorBuilder<T> createReturnTypeSelectorBuilder(T predicateBuilder) {
        return new ClassSelectorBuilder<>(createPredicateAdder(predicateBuilder, chain(
                AjpTransformers.methodTransformer(),
                MethodTransformers.methodReturnTypeTransformer()))
        );
    }


    public static <T extends Builder> ClassSelectorBuilder<T> createReturnComponentTypeSelectorBuilder(T predicateBuilder) {
        return new ClassSelectorBuilder<>(createPredicateAdder(predicateBuilder, chain(
                AjpTransformers.methodTransformer(),
                MethodTransformers.methodReturnTypeTransformer(),
                ClassTransformers.arrayComponentType()))
        );
    }

    public static <T extends Builder> ClassSelectorBuilder<T> createXxx(T predicateBuilder) {
        return createStandardSelectorBuilder(predicateBuilder);
    }


    public static <T extends Builder> ClassSelectorBuilder<T> createMethodExceptionTypeSelector(final T predicateBuilder) {
        return createStandardSelectorBuilder(predicateBuilder);
    }

    public static <T extends Builder> ClassSelectorBuilder<T>
    createMethodArgumentTypeSelectorBuilder(T predicateBuilder) {
        return createStandardSelectorBuilder(predicateBuilder);
    }

    private static <T extends Builder> ClassSelectorBuilder<T> createStandardSelectorBuilder(T predicateBuilder) {
        return new ClassSelectorBuilder<>(new PredicateAdder<>(predicateBuilder));
    }


    private static <T extends Builder> PredicateAdder<T> createPredicateAdder(final T predicateBuilder, final Transformer baseTransformer) {
        return new PredicateAdder<T>(predicateBuilder) {
            @Override
            protected T addPredicate(Predicate predicate) {
                return super.addPredicate(createPredicate(predicate));
            }

            private Predicate createPredicate(Predicate predicate) {
                return StandardPredicates.transformerPredicate(
                        baseTransformer,
                        predicate
                );
            }

            @Override
            protected T addTransformerPredicate(Transformer transformer, Predicate predicate) {
                return super.addPredicate(createTransformerPredicate(transformer, predicate));
            }

            private Predicate createTransformerPredicate(Transformer transformer, Predicate predicate) {
                return StandardPredicates.transformerPredicate(
                        chain(
                                baseTransformer,
                                transformer
                        ),
                        predicate
                );
            }
        };

    }


    private static Transformer chain(Transformer... transformers) {
        return StandardTransformers.transformerComposition(transformers);
    }

}
