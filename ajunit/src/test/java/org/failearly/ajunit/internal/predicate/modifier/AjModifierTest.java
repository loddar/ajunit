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
package org.failearly.ajunit.internal.predicate.modifier;

import org.failearly.ajunit.modifier.ModifierMatcher;
import org.junit.Test;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * AjModifierTest is responsible for ...
 */
public class AjModifierTest {
    @Test
    public void matchMethodModifiers() throws Exception {
        final int modifiers = Modifier.methodModifiers();
        assertMatch(AjModifier.PUBLIC, modifiers, true);
        assertMatch(AjModifier.PROTECTED, modifiers, true);
        assertMatch(AjModifier.PRIVATE, modifiers, true);
        assertMatch(AjModifier.STATIC, modifiers, true);
        assertMatch(AjModifier.FINAL, modifiers, true);
        assertMatch(AjModifier.ABSTRACT, modifiers, true);
        assertMatch(AjModifier.SYNCHRONIZED, modifiers, true);
        assertMatch(AjModifier.STRICT, modifiers, true);
        assertMatch(AjModifier.NATIVE, modifiers, true);

        assertMatch(AjModifier.VOLATILE, modifiers, false);
        assertMatch(AjModifier.TRANSIENT, modifiers, false);

        assertMatch(AjModifier.PACKAGE, modifiers, false);
    }

    @Test
    public void matchConstructorModifiers() throws Exception {
        final int modifiers = Modifier.constructorModifiers();
        assertMatch(AjModifier.PUBLIC, modifiers, true);
        assertMatch(AjModifier.PROTECTED, modifiers, true);
        assertMatch(AjModifier.PRIVATE, modifiers, true);

        assertMatch(AjModifier.SYNCHRONIZED, modifiers, false);
        assertMatch(AjModifier.STRICT, modifiers, false);
        assertMatch(AjModifier.NATIVE, modifiers, false);
        assertMatch(AjModifier.FINAL, modifiers, false);
        assertMatch(AjModifier.ABSTRACT, modifiers, false);
        assertMatch(AjModifier.STATIC, modifiers, false);
        assertMatch(AjModifier.VOLATILE, modifiers, false);
        assertMatch(AjModifier.TRANSIENT, modifiers, false);

        assertMatch(AjModifier.PACKAGE, modifiers, false);
    }

    @Test
    public void matchFieldModifiers() throws Exception {
        final int modifiers = Modifier.fieldModifiers();
        assertMatch(AjModifier.PUBLIC, modifiers, true);
        assertMatch(AjModifier.PROTECTED, modifiers, true);
        assertMatch(AjModifier.PRIVATE, modifiers, true);
        assertMatch(AjModifier.STATIC, modifiers, true);
        assertMatch(AjModifier.FINAL, modifiers, true);
        assertMatch(AjModifier.VOLATILE, modifiers, true);
        assertMatch(AjModifier.TRANSIENT, modifiers, true);

        assertMatch(AjModifier.SYNCHRONIZED, modifiers, false);
        assertMatch(AjModifier.STRICT, modifiers, false);
        assertMatch(AjModifier.ABSTRACT, modifiers, false);
        assertMatch(AjModifier.NATIVE, modifiers, false);

        assertMatch(AjModifier.PACKAGE, modifiers, false);
    }

    @Test
    public void matchClassModifiers() throws Exception {
        final int modifiers = Modifier.classModifiers();
        assertMatch(AjModifier.PUBLIC, modifiers, true);
        assertMatch(AjModifier.PROTECTED, modifiers, true);
        assertMatch(AjModifier.PRIVATE, modifiers, true);
        assertMatch(AjModifier.FINAL, modifiers, true);
        assertMatch(AjModifier.ABSTRACT, modifiers, true);
        assertMatch(AjModifier.STATIC, modifiers, true);
        assertMatch(AjModifier.STRICT, modifiers, true);

        assertMatch(AjModifier.SYNCHRONIZED, modifiers, false);
        assertMatch(AjModifier.NATIVE, modifiers, false);
        assertMatch(AjModifier.VOLATILE, modifiers, false);
        assertMatch(AjModifier.TRANSIENT, modifiers, false);

        assertMatch(AjModifier.PACKAGE, modifiers, false);
    }

    @Test
    public void matchInterfaceModifiers() throws Exception {
        final int modifiers = Modifier.interfaceModifiers();
        assertMatch(AjModifier.PUBLIC, modifiers, true);
        assertMatch(AjModifier.PROTECTED, modifiers, true);
        assertMatch(AjModifier.PRIVATE, modifiers, true);
        assertMatch(AjModifier.ABSTRACT, modifiers, true);
        assertMatch(AjModifier.STATIC, modifiers, true);
        assertMatch(AjModifier.STRICT, modifiers, true);

        assertMatch(AjModifier.FINAL, modifiers, false);
        assertMatch(AjModifier.SYNCHRONIZED, modifiers, false);
        assertMatch(AjModifier.NATIVE, modifiers, false);
        assertMatch(AjModifier.VOLATILE, modifiers, false);
        assertMatch(AjModifier.TRANSIENT, modifiers, false);

        assertMatch(AjModifier.PACKAGE, modifiers, false);
    }

    @Test
    public void packageModifier() throws Exception {
        assertMatch(AjModifier.PACKAGE, 0, true);

        assertMatch(AjModifier.PACKAGE, Modifier.ABSTRACT, true);
        assertMatch(AjModifier.PACKAGE, Modifier.FINAL, true);
        assertMatch(AjModifier.PACKAGE, Modifier.STATIC, true);
        assertMatch(AjModifier.PACKAGE, Modifier.STRICT, true);
        assertMatch(AjModifier.PACKAGE, Modifier.TRANSIENT, true);
        assertMatch(AjModifier.PACKAGE, Modifier.VOLATILE, true);
        assertMatch(AjModifier.PACKAGE, Modifier.SYNCHRONIZED, true);
        assertMatch(AjModifier.PACKAGE, Modifier.NATIVE, true);

        assertMatch(AjModifier.PACKAGE, Modifier.PRIVATE, false);
        assertMatch(AjModifier.PACKAGE, Modifier.PROTECTED, false);
        assertMatch(AjModifier.PACKAGE, Modifier.PUBLIC, false);

        // The modifier value is (PUBLIC | PROTECTED | PRIVATE) and matches if none of these bits has been set.
        assertMatch(AjModifier.PACKAGE, AjModifier.PACKAGE.getModifierValue(), false);
    }

    @Test
    public void ajModifierAreValid() throws Exception {
        final Set<Integer> modifiers = new HashSet<>();
        for (AjModifier ajModifier : AjModifier.values()) {
            assertThat("Modifier " + ajModifier + " value shared by other?", modifiers.add(ajModifier.getModifierValue()), is(true));
        }
    }

    @Test
    public void matchesItself() throws Exception {
        for (AjModifier ajModifier : AjModifier.values()) {
            if (ajModifier != AjModifier.PACKAGE) {
                assertThat("Matches itself?", ajModifier.match(ajModifier.getModifierValue()), is(true));
            }
        }

    }

    private static void assertMatch(ModifierMatcher ajModifier, int modifiers, boolean match) {
        assertThat(
                ajModifier + " matches " + Integer.toBinaryString(modifiers) + "?",
                ajModifier.match(modifiers),
                is(match)
        );
    }

}
