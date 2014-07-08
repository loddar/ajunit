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
package org.failearly.ajunit.internal.transformer;

/**
 * Transformer is a functor which transforms the {@code input}. The Transformer reduces the number of necessary
 * {@link org.failearly.ajunit.internal.predicate.Predicate}s. There are bridges
 * {@link org.failearly.ajunit.internal.predicate.standard.DelegatingTransformerPredicate} and
 * {@link org.failearly.ajunit.internal.predicate.standard.SimpleTransformerPredicate}.
 *
 */
public interface Transformer {
    /**
     * Transforms the {@code input} object. If the input is {@code null} the output will be {@code null} too.
     * @param input any input (including {@code null}).
     * @return the transformed input or {@code null}.
     * @throws java.lang.ClassCastException if the class of the {@code input} is not the expected one.
     */
    Object transform(final Object input);
}
