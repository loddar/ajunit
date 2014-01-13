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
package org.failearly.ajunit.internal.transformer.clazz;

import org.failearly.ajunit.internal.transformer.Transformer;

/**
 * ClassTransformers provides factory methods for {@link java.lang.Class} related transformations.
 */
public abstract class ClassTransformers {

    private static final Transformer CLASS_PACKAGE_NAME_TRANSFORMER = new ClassPackageNameTransformer();
    private static final Transformer CLASS_MODIFIERS_TRANSFORMER = new ClassModifiersTransformer();
    private static final Transformer CLASS_NAME_TRANSFORMER = new ClassNameTransformer();
    private static final Transformer CLASS_ANNOTATIONS_TRANSFORMER = new ClassAnnotationsTransformer();

    private ClassTransformers() {
    }

    /**
     * The returned Transformer executes {@link Class#getSimpleName()}.
     */
    public static Transformer classNameTransformer() {
        return CLASS_NAME_TRANSFORMER;
    }

    /**
     * The returned Transformer executes {@link Class#getPackage()} and {@link Package#getName()}. The result is the package name or the empty string for the
     * default package.
     */
    public static Transformer packageNameTransformer() {
        return CLASS_PACKAGE_NAME_TRANSFORMER;
    }

    /**
     * The returned Transformer executes {@link Class#getModifiers()}.
     */
    public static Transformer classModifiers() {
        return CLASS_MODIFIERS_TRANSFORMER;
    }

    /**
     * The returned Transformer executes {@link Class#getAnnotations()}.
     */
    public static Transformer classAnnotations() {
        return CLASS_ANNOTATIONS_TRANSFORMER;
    }
}
