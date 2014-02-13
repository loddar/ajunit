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
package org.failearly.ajunit.modifier;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * ModifierBaseTest is responsible for ...
 */
public class ModifierBaseTest {
    protected static final int NO_MODIFIERS = 0;

    protected static void assertModifierMatcher(ModifierMatcher matcher, int matchingBitMap, int notMatchingBitMap) {
        assertThat(matcher+" does match?", matcher.match(matchingBitMap), is(true));
        assertThat(matcher+" does not match?", matcher.match(notMatchingBitMap), is(false));
    }
}
