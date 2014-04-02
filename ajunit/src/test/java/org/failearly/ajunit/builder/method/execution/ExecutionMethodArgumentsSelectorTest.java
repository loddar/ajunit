/*
 * ajUnit - Unit Testing AspectJ.
 *
 * Copyright (C) 2013-2014 marko (http://fail-early.com/contact)
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
package org.failearly.ajunit.builder.method.execution;

import org.failearly.ajunit.builder.JoinPointSelector;
import org.failearly.ajunit.builder.MethodJoinPointSelector;
import org.failearly.ajunit.builder.method.MethodArgumentsSelectorTest;
import org.failearly.ajunit.internal.universe.AjJoinPointType;

/**
 * Tests for {@link org.failearly.ajunit.builder.MethodJoinPointSelector#byArguments(org.failearly.ajunit.builder.LogicalOperator)}.
 */
public final class ExecutionMethodArgumentsSelectorTest extends MethodArgumentsSelectorTest {

    public ExecutionMethodArgumentsSelectorTest() {
        super(AjJoinPointType.METHOD_EXECUTION);
    }

    @Override
    protected MethodJoinPointSelector createSelectorBuilderUnderTest(JoinPointSelector joinPointSelector) {
        return joinPointSelector.methodExecute();
    }
}
