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
import org.failearly.ajunit.modifier.MethodModifier;

/**
 * MethodJoinPointSelectorBuilder is the builder for method join point selector expression.
 * <br/><br/>
 * AspectJ pointcut definitions:
 * <code>
 * call(method signature)
 * execution(method signature)
 * </code>
 */
public interface MethodJoinPointSelectorBuilder extends LogicalSelectorBuilder<MethodJoinPointSelectorBuilder> {

    /**
     * End the method execution/call expression(s) end return to join point selector builder. It's like ending a
     * <pre>(execution(expr1) && execution(expr2))</pre> or <pre>(call(expr1) && call(expr2))</pre>.
     *
     * @return the {@link org.failearly.ajunit.builder.JoinPointSelectorBuilder}.
     * @see JoinPointSelectorBuilder#methodExecute()
     * @see org.failearly.ajunit.builder.JoinPointSelectorBuilder#methodCall()
     */
    JoinPointSelectorBuilder endMethod();

    /**
     * Select a method by method name (pattern).<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(* *.get*(..))</code></li>
     * <li><code>call(* *.set*(..))</code></li>
     * </ul>
     *
     * @param methodNamePattern the method name/name part or regular expression.
     * @param matcherType the matcher type
     * @return itself
     */
    MethodJoinPointSelectorBuilder byName(String methodNamePattern, StringMatcherType matcherType);

    /**
     * Select a method by any of {@link org.failearly.ajunit.modifier.AccessModifier}.<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(public * *.*(..))</code></li>
     * <li><code>call(private * *.*(..))</code></li>
     * </ul>
     * @param accessModifiers the supported access modifiers.
     * @return itself
     */
    MethodJoinPointSelectorBuilder byAnyOfAccessModifiers(AccessModifier... accessModifiers);

    /**
     * Select a method by none of {@link org.failearly.ajunit.modifier.AccessModifier}.<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(!public * *.*(..))</code></li>
     * <li><code>call(!private * *.*(..))</code></li>
     * </ul>
     * @param accessModifiers the supported access modifiers.
     * @return itself
     */
    MethodJoinPointSelectorBuilder byNoneOfAccessModifiers(AccessModifier... accessModifiers);

    /**
     * Select a method by any of {@link org.failearly.ajunit.modifier.MethodModifier}.<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(final * *.*(..))</code></li>
     * <li><code>call(static * *.*(..))</code></li>
     * </ul>
     * @param methodModifiers the supported method modifiers.
     * @return itself
     */
    MethodJoinPointSelectorBuilder byAnyOfMethodModifiers(MethodModifier... methodModifiers);


//    MethodJoinPointSelectorBuilder byDeclaringClass(Class<?> clazz);
//    MethodJoinPointSelectorBuilder byDeclaringClassName(String className, StringMatcherType matcherType);
//
//    MethodJoinPointSelectorBuilder byReturnType(Class<?> clazz);
//    MethodJoinPointSelectorBuilder byReturnType(String className, StringMatcherType matcherType);

    /**
     * Accepts any method signature.<br/>
     * </br>
     * AspectJ pointcut definitions:
     * <ul>
     * <li><code>execution(* *(..))</code></li>
     * <li><code>call(* *(..))</code></li>
     * </ul>
     *
     * @return itself
     */
    MethodJoinPointSelectorBuilder anyMethod();
}
