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
package org.failearly.ajunit.internal.transformer.annotation;

import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.transformer.TypedListTransformer;
import org.failearly.ajunit.internal.transformer.TypedTransformer;
import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * AnnotationTransformers contains factory methods for {@link java.lang.annotation.Annotation}
 * based {@link org.failearly.ajunit.internal.transformer.Transformer}s.
 */
public final class AnnotationTransformers {

    private static final Transformer ANNOTATION_ARRAY2LIST_TRANSFORMER = new TypedListTransformer<Annotation[], Annotation>(Annotation[].class, "AnnotationArray2List") {
        @Override
        protected List<Annotation> doTypedTransform(Annotation[] input) {
            return convert(input);
        }
    };
    private static final Transformer ANNOTATION_TYPE_TRANSFORMER = new TypedTransformer<Annotation,Class<? extends Annotation>>(Annotation.class, "AnnotationType") {
        @Override
        protected Class<? extends Annotation> doTypedTransform(Annotation input) {
            return input.annotationType();
        }
    };

    private AnnotationTransformers() {
    }

    /**
     * The returned Transformer converts an 1 dimensional array of annotations to a 1 dimensional list of annotation types:
     * <code>
     *      Annotation[] ==&gt; List&lt;Class&lt;? extends Annotation&gt;&gt;
     * </code>
     *
     * @see #toAnnotationType()
     */
    public static Transformer toAnnotationTypesDim1() {
        return StandardTransformers.toListDim1(toAnnotationType());
    }

    /**
     * The returned Transformer converts an 2 dimensional array of annotations ({@link java.lang.annotation.Annotation}) to a 2 dimensional list of annotations:
     * <code>
     *     Annotation[][] ==&gt; List&lt;List&lt;Annotation&gt;&gt;
     * </code>.
     *
     * @see java.lang.reflect.Method#getParameterAnnotations()
     */
    public static Transformer toAnnotationTypesDim2() {
        return StandardTransformers.toListDim2(toAnnotationType());
    }

    /**
     * The returned Transformer returns the class type of the annotation (which could not be resolved by calling {@link Object#getClass()}).
     *
     * @see java.lang.annotation.Annotation#annotationType()
     */
    public static Transformer toAnnotationType() {
        return ANNOTATION_TYPE_TRANSFORMER;
    }


}
