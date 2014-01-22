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
package org.failearly.ajunit.builder;

/**
 * JoinPointSelectorBuilder is responsible for ...
 */
public interface JoinPointSelectorBuilder  {
    /**
     * Select join points of type method execution.<br/>
     * </br>
     * AspectJ pointcut definitions:
     * <ul>
     *     <li><code>execution(<method signature>)</code></li>
     * </ul>
     *
     * @return the method join point selector builder.
     */
    MethodJoinPointSelectorBuilder methodExecute();

    /**
     * Select join points of type method execution.<br/>
     * </br>
     * AspectJ pointcut definitions:
     * <ul>
     *     <li><code>call(<method signature>)</code></li>
     * </ul>
     *
     * @return the method join point selector builder.
     */
    MethodJoinPointSelectorBuilder methodCall();

    /**
     * Does not select any join point. Just for initial setup - a placeholder.
     */
    void notYetSpecified();
}
