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
package org.failearly.ajunit.internal.util;

import org.failearly.ajunit.AjUniverseName;

import java.util.Arrays;
import java.util.List;

/**
 * Some utility methods.
 */
public abstract class AjUnitUtils {
    private AjUnitUtils() {
    }

    /**
     * Resolves the Universe name by evaluating the annotation {@link org.failearly.ajunit.AjUniverseName}.
     *
     * @param ajUnitObject the ajUnit object (either the aspect or test).
     * @return the universe name.
     *
     * @throws java.lang.IllegalArgumentException
     *
     * @see org.failearly.ajunit.AjUnitAspectBase
     * @see org.failearly.ajunit.internal.runner.AjUnitTestRunner
     */
    public static String resolveUniverseName(final Object ajUnitObject) {
        return resolveUniverseName(ajUnitObject.getClass());
    }

    /**
     * Resolves the Universe name by evaluating the annotation {@link org.failearly.ajunit.AjUniverseName}.
     * @param ajUnitObjectClass  the class object.
     * @return the universe name.
     *
     * @throws java.lang.IllegalArgumentException
     *
     * @see org.failearly.ajunit.AjUnitAspectBase
     * @see org.failearly.ajunit.internal.runner.AjUnitTestRunner
     */
    public static String resolveUniverseName(Class<?> ajUnitObjectClass) {
        final AjUniverseName universeName = AnnotationUtils.findClassAnnotation(ajUnitObjectClass, AjUniverseName.class);
        if (universeName == null) {
            AjAssert.throwSetupError(
                    MessageUtils.setupError("Missing annotation @AjUniverseName for class/aspect")
                            .arg(ajUnitObjectClass.getSimpleName())
            );
            // Just for keeping IDEA & Co happy.
            return "<unknown universe name>";
        }
        return universeName.value();
    }

    /**
     * Convert classes to class list.
     */
    public static List<Class<?>> toClassList(Class<?>... classes) {
        return Arrays.asList(classes);
    }
}