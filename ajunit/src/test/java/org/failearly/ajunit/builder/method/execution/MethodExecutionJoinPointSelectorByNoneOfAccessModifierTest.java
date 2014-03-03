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
package org.failearly.ajunit.builder.method.execution;

import org.failearly.ajunit.builder.JoinPointSelector;
import org.failearly.ajunit.builder.MethodExecutionJoinPointSelector;
import org.failearly.ajunit.builder.method.MethodJoinPointSelectorByNoneOfAccessModifierTest;
import org.failearly.ajunit.internal.universe.AjJoinPointType;

/**
 * Tests for {@link org.failearly.ajunit.builder.MethodExecutionJoinPointSelector}.
 */
public final class MethodExecutionJoinPointSelectorByNoneOfAccessModifierTest
        extends MethodJoinPointSelectorByNoneOfAccessModifierTest<MethodExecutionJoinPointSelector> {

    public MethodExecutionJoinPointSelectorByNoneOfAccessModifierTest() {
        super(AjJoinPointType.METHOD_EXECUTION);
    }

    @Override
    protected MethodExecutionJoinPointSelector createSelectorBuilderUnderTest(JoinPointSelector joinPointSelector) {
        return joinPointSelector.methodExecute();
    }
}
