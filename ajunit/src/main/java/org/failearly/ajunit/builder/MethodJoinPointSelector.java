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

import org.failearly.ajunit.modifier.MethodModifier;

/**
 * MethodJoinPointSelector is the builder for method join point selector expression.
 * <br/><br/>
 * AspectJ pointcut definitions:
 * <code>
 * call(method signature)
 * execution(method signature)
 * </code>
 */
public interface MethodJoinPointSelector<MS extends MethodJoinPointSelector> extends MemberSelector<MS>, LogicalSelector<MS> {

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
    MS byName(String methodNamePattern, StringMatcherType matcherType);

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
    MS byAnyOfMethodModifiers(MethodModifier... methodModifiers);

    /**
     * Select a method by none of {@link org.failearly.ajunit.modifier.MethodModifier}.<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(!final * *.*(..))</code></li>
     * <li><code>call(!static * *.*(..))</code></li>
     * </ul>
     * @param methodModifiers the supported method modifiers.
     * @return itself
     */
    MS byNoneOfMethodModifiers(MethodModifier... methodModifiers);


//    MethodJoinPointSelector byAnyOfReturnTypes(Class<?>... classes);
//    MethodJoinPointSelector byNoneOfReturnTypes(Class<?>... classes);
//    MethodJoinPointSelector byReturnType(String className, StringMatcherType matcherType);

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
    MS anyMethod();

    /**
     * End the method execution/call expression(s) end return to join point selector builder. It's like ending a
     * <pre>(execution(expr1) && execution(expr2))</pre> or <pre>(call(expr1) && call(expr2))</pre>.
     *
     * @return the {@link JoinPointSelector}.
     * @see JoinPointSelector#methodExecute()
     * @see JoinPointSelector#methodCall()
     */
    JoinPointSelector endMethod();
}
