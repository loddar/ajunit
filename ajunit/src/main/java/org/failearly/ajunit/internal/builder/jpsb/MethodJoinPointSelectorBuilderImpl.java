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
package org.failearly.ajunit.internal.builder.jpsb;

import org.failearly.ajunit.builder.JoinPointSelectorBuilder;
import org.failearly.ajunit.builder.MethodJoinPointSelectorBuilder;
import org.failearly.ajunit.builder.StringMatcherType;
import org.failearly.ajunit.internal.builder.BuilderBase;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.predicate.string.StringPredicates;
import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.transformer.ajp.AjpTransformers;
import org.failearly.ajunit.internal.transformer.clazz.ClassTransformers;
import org.failearly.ajunit.internal.transformer.member.MemberTransformers;
import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.failearly.ajunit.modifier.AccessModifier;
import org.failearly.ajunit.modifier.MethodModifier;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * MethodJoinPointSelectorBuilderImpl is responsible for ...
 */
class MethodJoinPointSelectorBuilderImpl extends BuilderBase<JoinPointSelectorBuilderImpl,MethodJoinPointSelectorBuilderImpl> implements MethodJoinPointSelectorBuilder {

    private static final PredicateFactories<StringMatcherType,String> STRING_MATCHER_PREDICATES =new PredicateFactories<>();

    static {
        STRING_MATCHER_PREDICATES.addFactory(StringMatcherType.EQUALS, new PredicateFactory<String>() {
            @Override
            public Predicate createPredicate(String input) {
                return StandardPredicates.predicateEquals(input);
            }
        });
        STRING_MATCHER_PREDICATES.addFactory(StringMatcherType.STARTS_WITH, new PredicateFactory<String>() {
            @Override
            public Predicate createPredicate(String input) {
                return StringPredicates.startsWith(input);
            }
        });

        STRING_MATCHER_PREDICATES.addFactory(StringMatcherType.ENDS_WITH, new PredicateFactory<String>() {
            @Override
            public Predicate createPredicate(String input) {
                return StringPredicates.endsWith(input);
            }
        });

        STRING_MATCHER_PREDICATES.addFactory(StringMatcherType.CONTAINS, new PredicateFactory<String>() {
            @Override
            public Predicate createPredicate(String input) {
                return StringPredicates.contains(input);
            }
        });

        STRING_MATCHER_PREDICATES.addFactory(StringMatcherType.REGEX, new PredicateFactory<String>() {
            @Override
            public Predicate createPredicate(String input) {
                return StringPredicates.regex(input);
            }
        });

    }

    private final AjJoinPointType joinPointType;

    MethodJoinPointSelectorBuilderImpl(
            JoinPointSelectorBuilderImpl root,
            JoinPointSelectorBuilderImpl parent,
            CompositePredicate compositePredicate,
            AjJoinPointType joinPointType) {
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
        this.joinPointType = joinPointType;
    }

    @Override
    public JoinPointSelectorBuilder endMethod() {
        return super.end(JoinPointSelectorBuilderImpl.class);
    }

    @Override
    public MethodJoinPointSelectorBuilder anyMethod() {
        return addMethodPredicate(StandardPredicates.alwaysTrue());
    }

    @Override
    public MethodJoinPointSelectorBuilder byName(String methodNamePattern, StringMatcherType matcherType) {
        return addMethodPredicate(
                MemberTransformers.nameTransformer(),
                STRING_MATCHER_PREDICATES.createPredicate(matcherType, methodNamePattern)
        );
    }

    @Override
    public MethodJoinPointSelectorBuilder byAnyOfAccessModifiers(AccessModifier... accessModifiers) {
        return addMethodPredicate(
                MemberTransformers.modifierTransformer(),
                LogicalPredicates.or(
                        JoinPointSelectorUtils.toPredicates(accessModifiers)
                )
            );
    }

    @Override
    public MethodJoinPointSelectorBuilder byNoneOfAccessModifiers(AccessModifier... accessModifiers) {
        return addMethodPredicate(
                MemberTransformers.modifierTransformer(),
                LogicalPredicates.nor(
                        JoinPointSelectorUtils.toPredicates(accessModifiers)
                )
            );
    }

    @Override
    public MethodJoinPointSelectorBuilder byAnyOfMethodModifiers(MethodModifier... methodModifiers) {
        return addMethodPredicate(
                MemberTransformers.modifierTransformer(),
                LogicalPredicates.or(
                        JoinPointSelectorUtils.toPredicates(methodModifiers)
                )
        );
    }

    @Override
    public MethodJoinPointSelectorBuilder byNoneOfMethodModifiers(MethodModifier... methodModifiers) {
        return addMethodPredicate(
                MemberTransformers.modifierTransformer(),
                LogicalPredicates.nor(
                        JoinPointSelectorUtils.toPredicates(methodModifiers)
                )
        );
    }

    @Override
    public MethodJoinPointSelectorBuilder byDeclaringClass(Class<?> declaringClass) {
        return addDeclaringClassPredicate(
                StandardTransformers.identityTransformer(Class.class),
                StandardPredicates.predicateEquals(declaringClass)
        );
    }

    @Override
    public MethodJoinPointSelectorBuilder byDeclaringClassName(String classNamePattern, StringMatcherType matcherType) {
        return addDeclaringClassPredicate(
                ClassTransformers.classNameTransformer(),
                STRING_MATCHER_PREDICATES.createPredicate(matcherType, classNamePattern)
        );
    }

    @Override
    public MethodJoinPointSelectorBuilder byExtending(Class<?> baseClass) {
        return addDeclaringClassPredicate(
                StandardTransformers.identityTransformer(Class.class),
                StandardPredicates.predicateIsSubclass(baseClass)
        );
    }

    @Override
    public MethodJoinPointSelectorBuilder byImplementingAnyOf(Class<?>... interfaces) {
        return addDeclaringClassPredicate(
                StandardTransformers.identityTransformer(Class.class),
                LogicalPredicates.or(createImplementingInterfacePredicates(interfaces))
        );
    }

    @Override
    public MethodJoinPointSelectorBuilder byImplementingAllOf(Class<?>... interfaces) {
        return addDeclaringClassPredicate(
                StandardTransformers.identityTransformer(Class.class),
                LogicalPredicates.and(createImplementingInterfacePredicates(interfaces))
        );

    }

    private List<Predicate> createImplementingInterfacePredicates(Class<?>... interfaces) {
        final List<Predicate> predicates=new ArrayList<>(interfaces.length);
        for (Class<?> anInterface : interfaces) {
            predicates.add(StandardPredicates.predicateIsSubclass(anInterface));
        }
        return predicates;
    }

    private MethodJoinPointSelectorBuilder addMethodPredicate(Predicate predicate) {
        return addMethodPredicate(StandardTransformers.identityTransformer(Method.class), predicate);
    }

    private MethodJoinPointSelectorBuilder addMethodPredicate(Transformer transformer, Predicate predicate) {
        addPredicate(StandardPredicates.transformerPredicate(
                StandardTransformers.transformerComposition(
                        AjpTransformers.ajpJoinPointFilterTransformer(this.joinPointType),
                        AjpTransformers.methodTransformer(),
                        transformer
                ),
                predicate
        ));
        return this;
    }

    private MethodJoinPointSelectorBuilder addDeclaringClassPredicate(Transformer transformer, Predicate predicate) {
        addPredicate(StandardPredicates.transformerPredicate(
                StandardTransformers.transformerComposition(
                        AjpTransformers.ajpJoinPointFilterTransformer(this.joinPointType),
                        AjpTransformers.declaringClassTransformer(),
                        transformer
                ),
                predicate
        ));
        return this;
    }
}
