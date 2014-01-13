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

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.InitializerSignature;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * StaticInitializationTest is responsible for ...
 */
public class StaticInitializationTest extends AbstractAspectTest {
    public StaticInitializationTest() {
        super("eval.org.aspectj.lang.StaticInitializationAspect", JoinPoint.STATICINITIALIZATION, InitializerSignature.class);
    }

    @Test
    public void accessClass() throws Exception {
        // act / when
        Class<?> myClass =Class.forName("eval.org.aspectj.lang.fixture.StaticInitializationTestFixture");
        assertThat(myClass, notNullValue());

        // assert / then
        assertJoinPoint(new AdditionalAssert() {
            @Override
            public void additionalAssert(final JoinPoint joinPoint, final String thisContext, final String targetContext) {
                assertThat("This?", joinPoint.getThis(), nullValue());
                assertThat("Target?", joinPoint.getTarget(), nullValue());
                assertThat("#Arguments?", joinPoint.getArgs().length, is(0));
                assertThat("Constructor?", ((InitializerSignature)joinPoint.getSignature()).getInitializer().toString(),
                        is("public eval.org.aspectj.lang.fixture.StaticInitializationTestFixture()"));
            }
        });
    }
    @Test
    public void accessInterface() throws Exception {
        // act / when
        Class<?> myClass =Class.forName("eval.org.aspectj.lang.fixture.StaticInitializationInterfaceTestFixture");
        assertThat(myClass, notNullValue());

        // assert / then
        assertJoinPoint(new AdditionalAssert() {
            @Override
            public void additionalAssert(final JoinPoint joinPoint, final String thisContext, final String targetContext) {
                assertThat("This?", joinPoint.getThis(), nullValue());
                assertThat("Target?", joinPoint.getTarget(), nullValue());
                assertThat("#Arguments?", joinPoint.getArgs().length, is(0));
                assertThat("No Constructor?", ((InitializerSignature)joinPoint.getSignature()).getInitializer(), nullValue());
            }
        });
    }
}
