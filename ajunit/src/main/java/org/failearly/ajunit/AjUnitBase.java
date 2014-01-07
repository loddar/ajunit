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
package org.failearly.ajunit;

import org.failearly.ajunit.internal.universe.AjJoinPointVisitor;
import org.failearly.ajunit.internal.universe.AjUniverse;
import org.failearly.ajunit.internal.universe.impl.AjUniversesHolder;
import org.failearly.ajunit.internal.util.AnnotationUtils;

import java.util.Arrays;

/**
 * AjUnitBase the base class for {@link org.failearly.ajunit.AjUnitAspect} and  {@link org.failearly.ajunit.AjUnitTest}.
 */
public abstract class AjUnitBase {

    private final AjUniverse ajUniverse;

    protected AjUnitBase() {
        ajUniverse = AjUniversesHolder.findUniverse(resolveUniverseName(this));
    }

    protected AjUnitBase(final Class<?>... testFixtureClasses) {
        ajUniverse = AjUniversesHolder.createUniverseByClasses(resolveUniverseName(this), Arrays.asList(testFixtureClasses));
    }

    protected AjUnitBase(final String... classNames) throws ClassNotFoundException {
        ajUniverse = AjUniversesHolder.createUniverseByClassNames(resolveUniverseName(this), Arrays.asList(classNames));
    }

    protected final void increaseAspectInstances() {
        ajUniverse.increaseAspectInstances();
    }

    protected final void dropUniverse() {
        AjUniversesHolder.dropUniverse(ajUniverse.getUniverseName());
    }

    protected final void visitJoinPoints(AjJoinPointVisitor joinPointVisitor) {
        ajUniverse.visitJoinPoints(joinPointVisitor);
    }

    private static String resolveUniverseName(final AjUnitBase ajUnitObject) {
        final AjUniverseName universeName = AnnotationUtils.findClassAnnotation(
                ajUnitObject.getClass(),
                AjUniverseName.class);
        if (universeName == null) {
            throw new IllegalArgumentException("ajUnit - Aspect/TestClass: Missing annotation @AjUniverseName");
        }
        return universeName.value();
    }
}
