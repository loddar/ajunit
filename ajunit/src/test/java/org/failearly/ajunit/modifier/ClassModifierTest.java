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

import org.junit.Test;

import java.lang.reflect.Modifier;

/**
 * Tests for {@link org.failearly.ajunit.modifier.ClassModifier}.
 */
public class ClassModifierTest extends ModifierBaseTest {

    @Test
    public void testStatic() throws Exception {
        assertModifierMatcher(ClassModifier.STATIC, Modifier.STATIC, NO_MODIFIERS);
    }

    @Test
    public void testFinal() throws Exception {
        assertModifierMatcher(ClassModifier.FINAL, Modifier.FINAL, NO_MODIFIERS);
    }

    @Test
    public void testAbstract() throws Exception {
        assertModifierMatcher(ClassModifier.ABSTRACT, Modifier.ABSTRACT, NO_MODIFIERS);
    }

    @Test
    public void testStrict() throws Exception {
        assertModifierMatcher(ClassModifier.STRICT, Modifier.STRICT, NO_MODIFIERS);
    }


}
