/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2014 Marko Umek (ajunit.contact(at)fail-early.com)
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
package org.failearly.ajunit.internal.runner;

import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.predicate.string.StringPredicates;
import org.failearly.ajunit.internal.transformer.ajp.AjpTransformers;
import org.failearly.ajunit.internal.transformer.member.MemberTransformers;
import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;

/**
 * SuppressedJoinPointFactory provides support for {@link org.failearly.ajunit.SuppressedJoinPoint} creation.
 *
 * @see org.failearly.ajunit.AjSuppressedJoinPoints
 */
public abstract class SuppressedJoinPointFactory {

    protected static final String ASPECTJ_PREFIX = "ajc$";

    protected static final Predicate PREDICATE_JAVA_LANG_OBJECT = byDeclaringClass(Object.class);

    static final Predicate PREDICATE_AJC_METHODS = StandardPredicates.transformerPredicate(
            StandardTransformers.transformerComposition(
                    AjpTransformers.methodTransformer(),
                    MemberTransformers.nameTransformer()
            ),
            StringPredicates.startsWith(ASPECTJ_PREFIX)
    );

    static final Predicate PREDICATE_AJC_FIELDS = StandardPredicates.transformerPredicate(
            StandardTransformers.transformerComposition(
                    AjpTransformers.fieldTransformer(),
                    MemberTransformers.nameTransformer()
            ),
            StringPredicates.startsWith(ASPECTJ_PREFIX)
    );

    static final Predicate PREDICATE_ALL_SUPPRESSED_JOINPOINTS=LogicalPredicates.or(
            PREDICATE_JAVA_LANG_OBJECT,
            PREDICATE_AJC_METHODS,
            PREDICATE_AJC_FIELDS
    );

    protected SuppressedJoinPointFactory() {
    }

    protected static Predicate anyMethodPredicate(String... names) {
        return LogicalPredicates.and(
                PREDICATE_JAVA_LANG_OBJECT,
                byMethodName(names)
        );
    }

    protected static Predicate byDeclaringClass(Class<?> clazz) {
        return StandardPredicates.transformerPredicate(
                AjpTransformers.declaringClassTransformer(),
                StandardPredicates.predicateEquals(clazz)
        );
    }

    protected static Predicate byMethodName(String... names) {
        return StandardPredicates.transformerPredicate(
                    StandardTransformers.transformerComposition(
                            AjpTransformers.methodTransformer(),
                            MemberTransformers.nameTransformer()
                    ),
                anyNameEquals(names)
        );
    }

    private static CompositePredicate anyNameEquals(String[] names) {
        final CompositePredicate byAnyMethodName = LogicalPredicates.or();
        for (String name : names) {
            byAnyMethodName.addPredicate(StandardPredicates.predicateEquals(name));
        }
        return byAnyMethodName;
    }
}
