/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2014 Marko Umek (ajunit.contact(at)fail-early.com)
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
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package eval.org.aspectj.lang;

import eval.org.aspectj.lang.fixture.MethodCallTestFixture;
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

    private MethodCallTestFixture testFixture;
    private Caller caller;

    public MethodCallTest() {
        super("eval.org.aspectj.lang.MethodCallAspect", JoinPoint.METHOD_CALL, MethodSignature.class);
    }

    @Before
    public void setUp() throws Exception {
        testFixture = new MethodCallTestFixture();
        caller = new Caller();
    }

    @Test
    public void methodCall() throws Exception {
        // act / when
        caller.call(testFixture);

        // assert / then
        assertJoinPoint(new AdditionalAssert() {
            @Override
            public void additionalAssert(final JoinPoint joinPoint, final String thisContext, final String targetContext) {
                assertThat("This?", joinPoint.getThis(), sameInstance((Object) caller));
                assertThat("Target?", joinPoint.getTarget(), sameInstance((Object) testFixture));
                assertThat("#Arguments?", joinPoint.getArgs().length, is(0));
            }
        });
    }

    @Test
    public void staticMethodCall() throws Exception {
        // act / when
        caller.callStatic(testFixture);

        // assert / then
        assertJoinPoint(new AdditionalAssert() {
            @Override
            public void additionalAssert(final JoinPoint joinPoint, final String thisContext, final String targetContext) {
                assertThat("This?", joinPoint.getThis(), sameInstance((Object) caller));
                assertThat("Target?", joinPoint.getTarget(), nullValue());
                assertThat("#Arguments?", joinPoint.getArgs().length, is(1));
            }
        });
    }

    @Test
    public void methodCallFromStatic() throws Exception {
        // arrange / given
        Caller.staticCall(testFixture);

        // assert / then
        assertJoinPoint(new AdditionalAssert() {
            @Override
            public void additionalAssert(final JoinPoint joinPoint, final String thisContext, final String targetContext) {
                assertThat("This?", joinPoint.getThis(), nullValue());
                assertThat("Target?", joinPoint.getTarget(), sameInstance((Object) testFixture));
                assertThat("#Arguments?", joinPoint.getArgs().length, is(0));
            }
        });
    }
}
