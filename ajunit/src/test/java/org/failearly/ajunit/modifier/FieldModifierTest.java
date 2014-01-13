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
package org.failearly.ajunit.modifier;

import org.junit.Test;

import java.lang.reflect.Modifier;

/**
 * Tests for {@link org.failearly.ajunit.modifier.FieldModifier}.
 */
public class FieldModifierTest extends ModifierBaseTest {

    @Test
    public void testStatic() throws Exception {
        assertModifierMatcher(FieldModifier.STATIC, Modifier.STATIC, NO_MODIFIERS);
    }

    @Test
    public void testFinal() throws Exception {
        assertModifierMatcher(FieldModifier.FINAL, Modifier.FINAL, NO_MODIFIERS);
    }

    @Test
    public void testTransient() throws Exception {
        assertModifierMatcher(FieldModifier.TRANSIENT, Modifier.TRANSIENT, NO_MODIFIERS);
    }

    @Test
    public void testVolatile() throws Exception {
        assertModifierMatcher(FieldModifier.VOLATILE, Modifier.VOLATILE, NO_MODIFIERS);
    }
}
