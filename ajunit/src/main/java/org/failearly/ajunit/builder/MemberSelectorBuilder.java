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
 * MemberSelectorBuilder is responsible for TODO
 */
public interface MemberSelectorBuilder<B extends SelectorBuilder> {
    /**
     * select by any method, field or constructor with access modifier {@code public}, {@code protected}, <i>package private</i> or {@code private}.
     * @param accessModifiers the access modifiers {@link org.failearly.ajunit.modifier.AccessModifier}.
     *
     * @see java.lang.reflect.Modifier
     */
    B byAnyOfAccessModifiers(AccessModifier... accessModifiers);

    /**
     * select by any method, field or constructor with out given access modifiesr {@code public}, {@code protected}, <i>package private</i> or {@code private}.
     * @param accessModifiers the access modifiers {@link org.failearly.ajunit.modifier.AccessModifier}.
     *
     * @see java.lang.reflect.Modifier
     */
    B byNoneOfAccessModifiers(AccessModifier... accessModifiers);

    /**
     * TODO
     * @param clazz
     * @return
     */
    B byDeclaringClass(Class<?> clazz);
    B byDeclaringClassName(String className);
    B byExtending(Class<?> clazz);
    B byImplementing(Class<?>... interfaces);
}
