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
package org.failearly.ajunit.builder.generic;

import org.failearly.ajunit.builder.SelectorBuilder;
import org.failearly.ajunit.modifier.AccessModifier;

/**
 * MemberSelector provides selector methods based on {@link java.lang.reflect.Member}.
 */
public interface MemberSelector<SB extends SelectorBuilder> {
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

}
