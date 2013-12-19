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

import eval.org.aspectj.lang.subject.MethodCallTestSubject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * MethodCallTest contains tests for MethodCallAspect
 */
public class MethodCallTest extends AbstractAspectTest {

    private MethodCallTestSubject testSubject;
    private Caller caller;

    public MethodCallTest() {
        super("eval.org.aspectj.lang.MethodCallAspect", JoinPoint.METHOD_CALL, MethodSignature.class);
    }

    @Before
    public void setUp() throws Exception {
        testSubject = new MethodCallTestSubject();
        caller = new Caller();
    }

    @Test
    public void methodCall() throws Exception {
        // act / when
        caller.call(testSubject);

        // assert / then
        assertJoinPoint(new AdditionalAssert() {
            @Override
            public void additionalAssert(final JoinPoint joinPoint, final String thisContext, final String targetContext) {
                assertThat("This?", joinPoint.getThis(), sameInstance((Object) caller));
                assertThat("Target?", joinPoint.getTarget(), sameInstance((Object) testSubject));
                assertThat("#Arguments?", joinPoint.getArgs().length, is(0));
            }
        });
    }

    @Test
    public void staticMethodCall() throws Exception {
        // act / when
        caller.callStatic(testSubject);

        // assert / then
        assertJoinPoint(new AdditionalAssert() {
            @Override
            public void additionalAssert(final JoinPoint joinPoint, final String thisContext, final String targetContext) {
                assertThat("This?", joinPoint.getThis(), sameInstance((Object) caller));
                assertThat("Target?", joinPoint.getTarget(), nullValue());
                assertThat("#Arguments?", joinPoint.getArgs().length,   is(1));
            }
        });
    }

    @Test
    public void methodCallFromStatic() throws Exception {
        // arrange / given
        Caller.staticCall(testSubject);

        // assert / then
        assertJoinPoint(new AdditionalAssert() {
            @Override
            public void additionalAssert(final JoinPoint joinPoint, final String thisContext, final String targetContext) {
                assertThat("This?", joinPoint.getThis(), nullValue());
                assertThat("Target?", joinPoint.getTarget(), sameInstance((Object) testSubject));
                assertThat("#Arguments?", joinPoint.getArgs().length,   is(0));
            }
        });
    }
}
