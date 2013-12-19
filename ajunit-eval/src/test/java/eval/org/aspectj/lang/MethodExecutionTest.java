/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2013  Marko Umek (ajunit.contact(at)gmail.com)
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
package eval.org.aspectj.lang;

import eval.org.aspectj.lang.subject.MethodExecutionTestSubject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * MethodExecutionTest contains tests for MethodExecutionAspect
 */
public class MethodExecutionTest extends AbstractAspectTest {

    private MethodExecutionTestSubject testSubject;

    public MethodExecutionTest() {
        super("eval.org.aspectj.lang.MethodExecutionAspect", JoinPoint.METHOD_EXECUTION, MethodSignature.class);
    }

    @Before
    public void setUp() throws Exception {
        testSubject = new MethodExecutionTestSubject();
    }

    @Test
    public void methodExecute() throws Exception {
        // act / when
       testSubject.executeMe(1, 2, 3);

        // assert / then
        assertJoinPoint(new AdditionalAssert() {
            @Override
            public void additionalAssert(final JoinPoint joinPoint, final String thisContext, final String targetContext) {
                assertThat("This?", joinPoint.getThis(), sameInstance((Object) testSubject));
                assertThat("Target?", joinPoint.getTarget(), sameInstance((Object) testSubject));
                assertThat("#Arguments?", joinPoint.getArgs().length,   is(3));
            }
        });
    }

    @Test
    public void staticMethodExecute() throws Exception {
        // act / when
       MethodExecutionTestSubject.executeMe();

        // assert / then
        assertJoinPoint(new AdditionalAssert() {
            @Override
            public void additionalAssert(final JoinPoint joinPoint, final String thisContext, final String targetContext) {
                assertThat("This?", joinPoint.getThis(), nullValue());
                assertThat("Target?", joinPoint.getTarget(), nullValue());
                assertThat("#Arguments?", joinPoint.getArgs().length, is(0));
            }
        });
    }
}
