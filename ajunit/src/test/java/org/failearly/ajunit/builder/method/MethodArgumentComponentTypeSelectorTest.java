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
package org.failearly.ajunit.builder.method;

import org.failearly.ajunit.builder.AbstractJoinPointSelectorTest;
import org.failearly.ajunit.builder.ListLogicalOperator;
import org.failearly.ajunit.builder.LogicalOperator;
import org.failearly.ajunit.builder.TestSubject9;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.junit.Test;

/**
 * MethodArgumentComponentTypeSelectorTest is responsible for ...
 */
public abstract class MethodArgumentComponentTypeSelectorTest extends AbstractJoinPointSelectorTest<MethodJoinPointSelector> {

    private MethodArgumentComponentTypeSelector argumentComponentTypeSelector;

    protected MethodArgumentComponentTypeSelectorTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject9.class);
    }

    @Override
    protected void doAdditionalSetup(MethodJoinPointSelector selectorBuilder) {
        this.argumentComponentTypeSelector = selectorBuilder
                                                    .arguments(LogicalOperator.OR)
                                                        .argumentTypes(ListLogicalOperator.ANY_OF)
                                                            .componentType();
    }

    @Test
    public void byClass() throws Exception {
        // act / when
        argumentComponentTypeSelector
                    .byClass(String.class)
                .endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject9.method2(java.lang.String[])"
        );
    }


}
