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
package org.failearly.ajunit.internal.predicate.standard;

import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.PredicateBase;
import org.failearly.ajunit.internal.transformer.Transformer;

/**
 * SimpleTransformerPredicate is responsible for ...
 */
final class SimpleTransformerPredicate extends PredicateBase {
    private final Transformer transformer;
    private final Predicate predicate;

    SimpleTransformerPredicate(Transformer transformer, Predicate predicate) {
        super("TP");
        this.transformer = transformer;
        this.predicate = predicate;
    }

    @Override
    protected boolean doTest(Object object) {
        final Object transformed= transformer.transform(object);
        return transformed != null && predicate.test(transformed);
    }

    @Override
    protected String mkString(int level) {
        return super.getName()+"("
                    + mkString(transformer, level) + "==>"
                    + mkString(predicate, level)
                    + indent(")", level);
    }

}
