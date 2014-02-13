/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
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

import org.failearly.ajunit.modifier.AccessModifier;

/**
 * MethodJoinPointSelectorBuilder is the builder for method join point selector expression.
 * <br/><br/>
 * AspectJ pointcut definitions:
 * <code>
 *     call(method signature)
 *     execution(method signature)
 * </code>
 *
 * @see
 */
public interface MethodJoinPointSelectorBuilder extends LogicalSelectorBuilder<MethodJoinPointSelectorBuilder> {

    JoinPointSelectorBuilder endMethod();

    MethodJoinPointSelectorBuilder byAnyOfAccessModifiers(AccessModifier... accessModifier);
    MethodJoinPointSelectorBuilder byNoneOfAccessModifiers(AccessModifier... accessModifier);
    MethodJoinPointSelectorBuilder byName(String methodName);
    MethodJoinPointSelectorBuilder byDeclaringClass(Class<?> clazz);
    MethodJoinPointSelectorBuilder byDeclaringClassName(String className);

    MethodJoinPointSelectorBuilder byReturnType(Class<?> clazz);
    MethodJoinPointSelectorBuilder byReturnType(String className);

    /**
     * Accepts any method signature.<br/>
     * </br>
     * AspectJ pointcut definitions:
     * <ul>
     *     <li><code>execution(* *(..))</code></li>
     *     <li><code>call(* *(..))</code></li>
     * </ul>
     * @return itself
     */
    MethodJoinPointSelectorBuilder anyMethod();
}
