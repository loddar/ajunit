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
import org.failearly.ajunit.builder.JoinPointSelector;
import org.failearly.ajunit.modifier.AccessModifier;

/**
 * MyAspectTest contains tests for MyAspect.
 */
public class InvalidGetterClassicAspectTest extends AjUnit4Test {
    @Override
    public void setup(AjUnitSetup ajUnitSetup) {
        ajUnitSetup.assignAspect("com.company.project.aspects.InvalidGetterClassicAspect");
        ajUnitSetup.addTestFixtureClass(ClassWithGetters.class);
    }

    @Override
    public void execute() {
        final ClassWithGetters classWithGetters = new ClassWithGetters();
        classWithGetters.getValue();
        classWithGetters.getReturningVoid();
        // classWithGetters.getWithParameter(0);
    }

    @Override
    public void assertPointcut(JoinPointSelector joinPointSelector) {
        joinPointSelector.methodCall()
                            .byAnyOfAccessModifiers(AccessModifier.PUBLIC)
                            .byReturningVoid()
                         .endMethod();
    }
}
