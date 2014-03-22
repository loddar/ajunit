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
import org.failearly.ajunit.internal.transformer.Transformer;

/**
 * SelectorBuilderBase is responsible for ...
 */
abstract class SelectorBuilderBase<T extends Builder> {
    private PredicateAdder<T> predicateAdder;

    SelectorBuilderBase(PredicateAdder<T> predicateAdder) {
        this.predicateAdder = predicateAdder;
    }

    protected final T addPredicate(Transformer transformer, Predicate predicate) {
        return predicateAdder.addTransformerPredicate(transformer, predicate);
    }


    protected final T addPredicate(Predicate predicate) {
        return predicateAdder.addPredicate(predicate);
    }
}
