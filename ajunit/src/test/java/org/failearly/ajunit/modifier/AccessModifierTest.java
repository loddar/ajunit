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
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package org.failearly.ajunit.modifier;

import org.junit.Test;

import java.lang.reflect.Modifier;

/**
 * Tests for {@link AccessModifier}.
 */
public class AccessModifierTest extends ModifierBaseTest {

    @Test
    public void testPublic() throws Exception {
        assertModifierMatcher(AccessModifier.PUBLIC, Modifier.PUBLIC, NO_MODIFIERS);
    }

    @Test
    public void testProtected() throws Exception {
        assertModifierMatcher(AccessModifier.PROTECTED, Modifier.PROTECTED, NO_MODIFIERS);
    }

    @Test
    public void testPrivate() throws Exception {
        assertModifierMatcher(AccessModifier.PRIVATE, Modifier.PRIVATE, NO_MODIFIERS);
    }

    @Test
    public void testPackage() throws Exception {
        assertModifierMatcher(AccessModifier.PACKAGE, NO_MODIFIERS, Modifier.PUBLIC);
        assertModifierMatcher(AccessModifier.PACKAGE, NO_MODIFIERS, Modifier.PROTECTED);
        assertModifierMatcher(AccessModifier.PACKAGE, NO_MODIFIERS, Modifier.PRIVATE);
    }

}
