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
package org.failearly.ajunit.internal.transformer.clazz;

import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.transformer.TypedListTransformer;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * ClassTransformers provides factory methods for {@link java.lang.Class} related transformations.
 */
public final class ClassTransformers {

    private static final Transformer CLASS_PACKAGE_NAME_TRANSFORMER = new ClassTransformerBase<String>("ClassPackage") {
        @Override
        protected String doTypedTransform(Class input) {
            final Package thePackage = input.getPackage();
            if(thePackage==null) {
                return "";
            }
            return thePackage.getName();
        }
    };
    private static final Transformer CLASS_MODIFIERS_TRANSFORMER = new ClassTransformerBase<Integer>("ClassModifiers") {
        @Override
        protected Integer doTypedTransform(Class input) {
            return input.getModifiers();
        }
    };
    private static final Transformer CLASS_NAME_TRANSFORMER = new ClassTransformerBase<String>("ClassName") {
        @Override
        protected String doTypedTransform(Class input) {
            return input.getSimpleName();
        }
    };
    private static final Transformer CLASS_ANNOTATIONS_TRANSFORMER = new TypedListTransformer<Class, Annotation>(Class.class,"ClassAnnotations") {
        @Override
        protected List<Annotation> doTypedTransform(Class input) {
            return convert(input.getAnnotations());
        }
    };
    public static final Transformer ARRAY_DIMENSION_TRANSFORMER = new ArrayDimensionTransformer();
    public static final Transformer ARRAY_COMPONENT_TYPE_TRANSFORMER = new ArrayComponentTypeTransformer();


    private ClassTransformers() {
    }

    /**
     * The created Transformer executes {@link Class#getSimpleName()}.
     */
    public static Transformer className() {
        return CLASS_NAME_TRANSFORMER;
    }

    /**
     * The created Transformer executes {@link Class#getPackage()} and {@link Package#getName()}. The result is the package name or the empty string for the
     * default package.
     */
    public static Transformer packageName() {
        return CLASS_PACKAGE_NAME_TRANSFORMER;
    }

    /**
     * The created Transformer executes {@link Class#getModifiers()}.
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

    /**
     * The created Transformer returns the number of Array value. If the class object is not an array, the value will be 0.
     */
    public static Transformer countArrayDimension() {
        return ARRAY_DIMENSION_TRANSFORMER;
    }

    /**
     * The created Transformer returns the component type of an array. If the class object is not an array, {@code null} will be returned.
     */
    public static Transformer arrayComponentType() {
        return ARRAY_COMPONENT_TYPE_TRANSFORMER;
    }
}
