/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
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
package eval.org.aspectj.lang;

import eval.org.aspectj.lang.fixture.InitializationTestFixture;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * ConstructorExecutionTest tests {@link ConstructorCallAspect}.
 */
public class InitializationTest extends AbstractAspectTest {

    public InitializationTest() {
        super("eval.org.aspectj.lang.InitializationAspect", JoinPoint.INITIALIZATION, ConstructorSignature.class);
    }

    @Test
    public void createByDefaultConstructor() throws Exception {
        // act / when
        final InitializationTestFixture testFixture = new InitializationTestFixture();

        // assert / then
        assertJoinPoint(new AdditionalAssert() {
            @Override
            public void additionalAssert(final JoinPoint joinPoint, final String thisContext, final String targetContext) {
                assertThat("This?", joinPoint.getThis(), sameInstance((Object) testFixture));
                assertThat("Target?", joinPoint.getTarget(), sameInstance((Object) testFixture));
                assertThat("#Arguments?", joinPoint.getArgs().length, is(0));
                assertThat("This context?", thisContext, is("InitializationTestFixture{anyValue='default'}"));
                assertThat("Target context?", targetContext, is("InitializationTestFixture{anyValue='default'}"));
                assertThat("Constructor?", ((ConstructorSignature)joinPoint.getSignature()).getConstructor().toString(),
                        is("public eval.org.aspectj.lang.fixture.InitializationTestFixture()"));
            }
        });
    }


    @Test
    public void createByConstructorWithParameter() throws Exception {
        // act / when
        final InitializationTestFixture testFixture = new InitializationTestFixture("MY_VALUE");

        // assert / then
        assertJoinPoint(new AdditionalAssert() {
            @Override
            public void additionalAssert(final JoinPoint joinPoint, final String thisContext, final String targetContext) {
                assertThat("This?", joinPoint.getThis(), sameInstance((Object) testFixture));
                assertThat("Target?", joinPoint.getTarget(), sameInstance((Object) testFixture));
                assertThat("#Arguments?", joinPoint.getArgs().length, is(1));
                assertThat("Argument value?", joinPoint.getArgs()[0], is((Object)"MY_VALUE"));
                assertThat("This context?", thisContext, is("InitializationTestFixture{anyValue='MY_VALUE'}"));
                assertThat("Target context?", targetContext, is("InitializationTestFixture{anyValue='MY_VALUE'}"));
                assertThat("Constructor?", ((ConstructorSignature)joinPoint.getSignature()).getConstructor().toString(),
                        is("public eval.org.aspectj.lang.fixture.InitializationTestFixture(java.lang.String)"));
            }
        });
    }
}
