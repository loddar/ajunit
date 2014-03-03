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

import org.failearly.ajunit.modifier.AccessModifier;

/**
 * MemberSelector provides selector methods based on {@link java.lang.reflect.Member}.
 */
public interface MemberSelector<SB extends SelectorBuilder> extends SelectorBuilder {
    /**
     * Select a method by any of {@link org.failearly.ajunit.modifier.AccessModifier}.<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(public * *.*(..))</code></li>
     * <li><code>call(private * *.*(..))</code></li>
     * </ul>
     *
     * @param accessModifiers the supported access modifiers.
     * @return itself
     */
    SB byAnyOfAccessModifiers(AccessModifier... accessModifiers);

    /**
     * Select a method by none of {@link org.failearly.ajunit.modifier.AccessModifier}.<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(!public * *.*(..))</code></li>
     * <li><code>call(!private * *.*(..))</code></li>
     * </ul>
     *
     * @param accessModifiers the supported access modifiers.
     * @return itself
     */
    SB byNoneOfAccessModifiers(AccessModifier... accessModifiers);

    /**
     * Select a method by it's declaring class.<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(* com.company.MyClass.*(..))</code></li>
     * <li><code>call(* com.company.MyClass.*(..))</code></li>
     * </ul>
     *
     * @param declaringClass the declaring class.
     * @return itself
     */
    SB byDeclaringClass(Class<?> declaringClass);

    /**
     * Select a method by it's declaring class name (pattern).<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(* **.My*.*(..))</code></li>
     * <li><code>call(* **.*Class.*(..))</code></li>
     * </ul>
     *
     * @param classNamePattern the declaring class.
     * @param matcherType  the matcher type
     * @return itself
     */
    SB byDeclaringClassName(String classNamePattern, StringMatcherType matcherType);


    /**
     * Select a method by class inheritance (extending the specified baseClass).<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(* com.company.MyClass+.*(..))</code></li>
     * <li><code>call(* com.company.MyBaseClass+.*(..))</code></li>
     * </ul>
     *
     * @param baseClass the base class or the class itself.
     * @return itself
     */
    SB byExtending(Class<?> baseClass);

    /**
     * Select a method by class implementation (any of the specified interfaces).<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(* com.company.MyInterface+.*(..)) || execution(* com.company.OtherInterface+.*(..))</code></li>
     * <li><code>call(* com.company.MyInterface+.*(..)) || call(* com.company.OtherInterface+.*(..))</code></li>
     * </ul>
     *
     * @param interfaces the base class or the class itself.
     * @return itself
     */
    SB byImplementingAnyOf(Class<?>... interfaces);

    /**
     * Select a method by class implementation (all of the specified interfaces).<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(* com.company.MyInterface+.*(..)) && execution(* com.company.OtherInterface+.*(..))</code></li>
     * <li><code>call(* com.company.MyInterface+.*(..)) && call(* com.company.OtherInterface+.*(..))</code></li>
     * </ul>
     *
     * @param interfaces the base class or the class itself.
     * @return itself
     */
    SB byImplementingAllOf(Class<?>... interfaces);

    /**
     * Select a method by it's declaring classes package (pattern).<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(* **.My*.*(..))</code></li>
     * <li><code>call(* **.*Class.*(..))</code></li>
     * </ul>
     *
     * @param packageNamePattern the declaring class.
     * @param matcherType  the matcher type
     * @return itself
     */
    SB byPackageName(String packageNamePattern, StringMatcherType matcherType);
}
