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
 */
package eval.org.aspectj.lang;

import eval.org.aspectj.lang.fixture.FieldGetTestFixture;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.FieldSignature;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * FieldGetTest contains tests for FieldGetAspect
 */
public class FieldGetTest extends AbstractAspectTest {

    private FieldGetTestFixture testFixture;

    public FieldGetTest() {
        super("eval.org.aspectj.lang.FieldGetAspect", JoinPoint.FIELD_GET, FieldSignature.class);
    }

    @Before
    public void setUp() throws Exception {
        testFixture = new FieldGetTestFixture();
    }

    @Test
    public void accessFieldViaGetter() throws Exception {
        // act / when
        testFixture.getField();

        // assert / then
        assertJoinPoint(new AdditionalAssert() {
            @Override
            public void additionalAssert(final JoinPoint joinPoint, final String thisContext, final String targetContext) {
                assertThat("This?", joinPoint.getThis(), sameInstance((Object) testFixture));
                assertThat("Target?", joinPoint.getTarget(), sameInstance((Object) testFixture));
                assertThat("#Arguments?", joinPoint.getArgs().length, is(0));
            }
        });
    }

    @Test
    public void directAccessField() throws Exception {
        // act / when
        final int val = testFixture.field2;

        // assert / then
        assertJoinPoint(new AdditionalAssert() {
            @Override
            public void additionalAssert(final JoinPoint joinPoint, final String thisContext, final String targetContext) {
                assertThat("This?", joinPoint.getThis(), sameInstance((Object) FieldGetTest.this));
                assertThat("Target?", joinPoint.getTarget(), sameInstance((Object) testFixture));
                assertThat("#Arguments?", joinPoint.getArgs().length, is(0));
            }
        });
    }

    @Test
    public void accessStaticFieldViaGetter() throws Exception {
        // act / when
        testFixture.getStaticField();

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
