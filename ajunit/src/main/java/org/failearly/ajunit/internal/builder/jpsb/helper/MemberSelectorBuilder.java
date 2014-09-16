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

import org.failearly.ajunit.builder.types.StringMatcher;
import org.failearly.ajunit.internal.builder.Builder;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.modifier.ModifierPredicate;
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;
import org.failearly.ajunit.internal.transformer.member.MemberTransformers;
import org.failearly.ajunit.modifier.AccessModifier;
import org.failearly.ajunit.modifier.MethodModifier;
import org.failearly.ajunit.modifier.ModifierMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * MemberSelectorBuilder is responsible for ...
 */
abstract class MemberSelectorBuilder<T extends Builder> extends SelectorBuilderBase<T> {

    MemberSelectorBuilder(PredicateAdder<T> predicateAdder) {
        super(predicateAdder);
    }

    public T byName(String namePattern, StringMatcher matcherType) {
        return addPredicate(
                MemberTransformers.name(),
                AjUnitTypesPredicateFactory.createStringMatcherPredicate(namePattern, matcherType)
        );
    }

    public T byAnyOfAccessModifiers(AccessModifier... accessModifiers) {
        return addPredicate(
                MemberTransformers.modifier(),
                LogicalPredicates.or(
                        toPredicateList(accessModifiers)
                )
        );
    }

    public T byNoneOfAccessModifiers(AccessModifier... accessModifiers) {
        return addPredicate(
                MemberTransformers.modifier(),
                LogicalPredicates.nor(
                        toPredicateList(accessModifiers)
                )
        );
    }

    public T byAnyOfMethodModifiers(MethodModifier... methodModifiers) {
        return addPredicate(
                MemberTransformers.modifier(),
                LogicalPredicates.or(
                        toPredicateList(methodModifiers)
                )
        );
    }

    public T byNoneOfMethodModifiers(MethodModifier... methodModifiers) {
        return addPredicate(
                MemberTransformers.modifier(),
                LogicalPredicates.nor(
                        toPredicateList(methodModifiers)
                )
        );
    }

    private static List<Predicate> toPredicateList(ModifierMatcher... modifierMatchers) {
        final List<Predicate> predicates=new ArrayList<>(modifierMatchers.length);
        for (ModifierMatcher modifierMatcher : modifierMatchers) {
            predicates.add(ModifierPredicate.modifierPredicate(modifierMatcher));
        }
        return predicates;
    }

}
