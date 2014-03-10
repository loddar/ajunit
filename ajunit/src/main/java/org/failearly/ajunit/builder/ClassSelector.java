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

/**
 * ClassSelector provides methods based on {@link java.lang.Class} object.
 */
public interface ClassSelector<SB extends SelectorBuilder> extends SelectorBuilder {
    /**
     * Select joinpoints by it's class type.<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(* com.company.MyClass.*(..))</code></li>
     * <li><code>call(* com.company.MyClass.*(..))</code></li>
     * </ul>
     *
     * @param classType the class object.
     * @return itself
     */
    SB byClass(Class<?> classType);

    /**
     * Select joinpoints by it's class name (pattern).<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(* **.My*.*(..))</code></li>
     * <li><code>call(* **.*Class.*(..))</code></li>
     * </ul>
     *
     * @param classNamePattern the class name (pattern).
     * @param matcherType  the matcher type
     * @return itself
     */
    SB byClassName(String classNamePattern, StringMatcherType matcherType);

    /**
     * Select joinpoints by class inheritance (extending the specified baseClass).<br/>
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
     * Select joinpoints by class inheritance (<i>not</i> extending the specified baseClass).<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(* !(com.company.MyClass+).*(..))</code></li>
     * <li><code>call(* !(com.company.MyBaseClass+).*(..))</code></li>
     * </ul>
     *
     * @param baseClass the base class or the class itself.
     * @return itself
     */
    SB byNotExtending(Class<?> baseClass);

    /**
     * Select joinpoints by classes implementations (any of the specified interfaces).<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(* com.company.MyInterface+.*(..)) || execution(* com.company.OtherInterface+.*(..))</code></li>
     * <li><code>call(* com.company.MyInterface+.*(..)) || call(* com.company.OtherInterface+.*(..))</code></li>
     * </ul>
     *
     * @param interfaces the interface(s).
     * @return itself
     */
    SB byImplementingAnyOf(Class<?>... interfaces);

    /**
     * Select joinpoints by classes implementations (all of the specified interfaces).<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(* com.company.MyInterface+.*(..)) && execution(* com.company.OtherInterface+.*(..))</code></li>
     * <li><code>call(* com.company.MyInterface+.*(..)) && call(* com.company.OtherInterface+.*(..))</code></li>
     * </ul>
     *
     * @param interfaces the interface(s).
     * @return itself
     */
    SB byImplementingAllOf(Class<?>... interfaces);

    /**
     * Select joinpoints by classes implementations (none of the specified interfaces).<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>! execution(* com.company.MyInterface+.*(..)) && ! execution(* com.company.OtherInterface+.*(..))</code></li>
     * <li><code>! call(* com.company.MyInterface+.*(..)) && ! call(* com.company.OtherInterface+.*(..))</code></li>
     * </ul>
     *
     * @param interfaces the interface(s).
     * @return itself
     */
    SB byImplementingNoneOf(Class<?>... interfaces);

    /**
     * Select joinpoints by package name (pattern).<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(* com.my.company..*(..))</code></li>
     * <li><code>call(* org.spring..*(..))</code></li>
     * </ul>
     *
     * @param packageNamePattern the package name (pattern).
     * @param matcherType  the matcher type
     * @return itself
     */
    SB byPackageName(String packageNamePattern, StringMatcherType matcherType);
}
