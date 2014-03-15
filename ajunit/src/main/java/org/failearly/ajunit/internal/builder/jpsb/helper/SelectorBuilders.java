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
package org.failearly.ajunit.internal.builder.jpsb.helper;

import org.failearly.ajunit.internal.builder.Builder;
import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.transformer.ajp.AjpTransformers;
import org.failearly.ajunit.internal.transformer.clazz.ClassTransformers;
import org.failearly.ajunit.internal.transformer.method.MethodTransformers;
import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;
import org.failearly.ajunit.internal.universe.AjJoinPointType;

/**
 * SelectorBuilders provides factory methods for {@link org.failearly.ajunit.internal.builder.jpsb.helper.SelectorBuilderBase}.
 */
public class SelectorBuilders {
    public static <T extends Builder> MethodSelectorBuilder<T> createMethodSelectorBuilder(T predicateBuilder, AjJoinPointType joinPointType) {
        return new MethodSelectorBuilder<>(predicateBuilder, joinPointType);
    }

    public static <T extends Builder> ClassSelectorBuilder<T>  createDeclaringClassSelectorBuilder(T predicateBuilder, AjJoinPointType joinPointType) {
        return createClassSelectorBuilderHelper(predicateBuilder, joinPointType, AjpTransformers.declaringClassTransformer());
    }

    public static <T extends Builder> ClassSelectorBuilder<T>  createReturnTypeSelectorBuilder(T predicateBuilder, AjJoinPointType joinPointType) {
        return createClassSelectorBuilderHelper(predicateBuilder, joinPointType,
                                chain(
                                    AjpTransformers.methodTransformer(),
                                    MethodTransformers.methodReturnTypeTransformer()
                                )
                            );
    }


    public static <T extends Builder> ClassSelectorBuilder<T> createReturnComponentTypeSelectorBuilder(T predicateBuilder, AjJoinPointType joinPointType) {
        return createClassSelectorBuilderHelper(predicateBuilder, joinPointType,
                chain(
                        AjpTransformers.methodTransformer(),
                        MethodTransformers.methodReturnTypeTransformer(),
                        ClassTransformers.arrayComponentType()
                )
        );
    }

    private static <T extends Builder> ClassSelectorBuilder<T>
    createClassSelectorBuilderHelper(T predicateBuilder, AjJoinPointType joinPointType, Transformer ajJoinPointTransformer) {
        return new ClassSelectorBuilder<>(predicateBuilder, joinPointType, ajJoinPointTransformer);
    }


    private static Transformer chain(Transformer... transformers) {
        return StandardTransformers.transformerComposition(transformers);
    }

}
