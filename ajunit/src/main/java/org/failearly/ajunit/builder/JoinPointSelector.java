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
package org.failearly.ajunit.builder;

import org.failearly.ajunit.builder.generic.LogicalSelector;
import org.failearly.ajunit.builder.method.MethodJoinPointSelector;

/**
 * JoinPointSelector builds join point selectors.
 * <br><br>
 * <b>Remark:</b>The implicit logical operator is {@link org.failearly.ajunit.builder.types.LogicalOperator#OR}, because
 * a join point can't be of two different kinds. Any subsequent implicit logical operator will be {@link org.failearly.ajunit.builder.types.LogicalOperator#AND}.
 *
 * @see org.failearly.ajunit.AjUnitTest#assertPointcut(JoinPointSelector)
 */
public interface JoinPointSelector extends SelectorBuilder, LogicalSelector<JoinPointSelector> {
    /**
     * Select join points of type method execution.<br>
     * <br>
     * AspectJ pointcut definitions:
     * <ul>
     *     <li><code>execution(&lt;method signature&gt;)</code></li>
     * </ul>
     *
     * @return the method join point selector builder.
     */
    MethodJoinPointSelector methodExecute();

    /**
     * Select join points of type method execution.<br>
     * <br>
     * AspectJ pointcut definitions:
     * <ul>
     *     <li><code>call(&lt;method signature&gt;)</code></li>
     * </ul>
     *
     * @return the method join point selector builder.
     */
    MethodJoinPointSelector methodCall();

    /**
     * Does not select any join point. Just for initial setup - a placeholder.
     */
    void notYetSpecified();
}
