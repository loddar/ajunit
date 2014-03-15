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
import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;
import org.failearly.ajunit.internal.universe.AjJoinPointType;

/**
 * SelectorBuilderBase is responsible for ...
 */
abstract class SelectorBuilderBase<T extends Builder> {
    private final T predicateBuilder;
    private final Transformer ajJoinPointTransformer;
    protected final AjJoinPointType joinPointType;

    SelectorBuilderBase(T predicateBuilder, AjJoinPointType joinPointType, Transformer ajJoinPointTransformer) {
        this.predicateBuilder = predicateBuilder;
        this.joinPointType = joinPointType;
        this.ajJoinPointTransformer = ajJoinPointTransformer;
    }

    protected final T addPredicate(Transformer transformer, Predicate predicate) {
        predicateBuilder.addPredicate(createTransformingPredicate(transformer, predicate));
        return predicateBuilder;
    }


    protected final T addPredicate(Predicate predicate) {
        predicateBuilder.addPredicate(createPredicate(predicate));
        return predicateBuilder;
    }


    private Predicate createTransformingPredicate(Transformer transformer, Predicate predicate) {
        return StandardPredicates.transformerPredicate(
                StandardTransformers.transformerComposition(
                        AjpTransformers.ajpJoinPointFilterTransformer(this.joinPointType),
                        this.ajJoinPointTransformer,
                        transformer
                ),
                predicate
        );
    }

    private Predicate createPredicate(Predicate predicate) {
        return StandardPredicates.transformerPredicate(
                StandardTransformers.transformerComposition(
                        AjpTransformers.ajpJoinPointFilterTransformer(this.joinPointType),
                        this.ajJoinPointTransformer
                ),
                predicate
        );
    }

    protected final Predicate transformerPredicate(Transformer transformer, Predicate predicate) {
        return StandardPredicates.transformerPredicate(transformer, predicate);
    }
}
