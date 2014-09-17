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
package com.company.project.aspects;

import org.failearly.ajunit.AjUnit4Test;
import org.failearly.ajunit.AjUnitSetup;
import org.failearly.ajunit.AjUniverseName;
import org.failearly.ajunit.builder.JoinPointSelector;
import org.failearly.ajunit.builder.types.ListOperator;
import org.failearly.ajunit.builder.types.NumberComparator;
import org.failearly.ajunit.modifier.AccessModifier;

/**
 * MyAspectTest contains tests for MyAspect.
 */
@AjUniverseName("InvalidGetterAspect")
public class InvalidGetterAspectTest extends AjUnit4Test {
    @Override
    public void setup(AjUnitSetup ajUnitSetup) {
        ajUnitSetup.assignAspect("com.company.project.aspects.InvalidGetterAspect");
        ajUnitSetup.addTestFixtureClass(ClassWithGetters.class);
    }

    @Override
    public void execute() throws Exception {
        final ClassWithGetters classWithGetters = new ClassWithGetters();
        // Valid
        classWithGetters.getValue();

        // Not valid getters
        classWithGetters.getReturningVoid();
        classWithGetters.getAnyArray();
        classWithGetters.getAnyArray2Dim();
        classWithGetters.getAnyCollection();
        classWithGetters.getAnyList();
        classWithGetters.getWithParameter(0);
        classWithGetters.getValueWithException();
    }

    @Override
    public void assertPointcut(JoinPointSelector joinPointSelector) {
        joinPointSelector.methodCall()
                            .byAnyOfAccessModifiers(AccessModifier.PUBLIC)
                            .anyOf()
                                .byReturningVoid()
                                .byReturningArray()
                                .byReturningCollection()
                                .parameters()
                                    .byNumberOfParameters(0, NumberComparator.GREATER_THEN)
                                .endParameters()
                                .exceptionTypes(ListOperator.AT_LEAST_ONE)
                                    .byExtending(java.lang.Throwable.class)
                                .endExceptionTypes()
                            .end()
                         .endMethod();
    }
}
