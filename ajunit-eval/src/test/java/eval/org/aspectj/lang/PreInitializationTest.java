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

import eval.org.aspectj.lang.fixture.PreInitializationTestFixture;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * PreInitializationTest tests {@link eval.org.aspectj.lang.PreInitializationAspect}.
 */
public class PreInitializationTest extends AbstractAspectTest {

    public PreInitializationTest() {
        super("eval.org.aspectj.lang.PreInitializationAspect", JoinPoint.PREINITIALIZATION, ConstructorSignature.class);
    }

    @Test
    public void createByDefaultConstructor() throws Exception {
        // act / when
        final PreInitializationTestFixture testFixture=new PreInitializationTestFixture();

        // assert / then
        assertJoinPoint(new AdditionalAssert() {
            @Override
            public void additionalAssert(final JoinPoint joinPoint, final String thisContext, final String targetContext) {
                assertThat("This?", joinPoint.getThis(), nullValue());
                assertThat("Target?", joinPoint.getTarget(), nullValue());
                assertThat("#Arguments?", joinPoint.getArgs().length, is(0));
                assertThat("Constructor?", ((ConstructorSignature)joinPoint.getSignature()).getConstructor().toString(),
                        is("public eval.org.aspectj.lang.fixture.PreInitializationTestFixture()"));
            }
        });
    }


    @Test
    public void createByConstructorWithParameter() throws Exception {
        // act / when
        final PreInitializationTestFixture testFixture=new PreInitializationTestFixture("MY_VALUE");

        // assert / then
        assertJoinPoint(new AdditionalAssert() {
            @Override
            public void additionalAssert(final JoinPoint joinPoint, final String thisContext, final String targetContext) {
                assertThat("This?", joinPoint.getThis(), nullValue());
                assertThat("Target?", joinPoint.getTarget(), nullValue());
                assertThat("#Arguments?", joinPoint.getArgs().length, is(1));
                assertThat("Argument value?", joinPoint.getArgs()[0], is((Object) "MY_VALUE"));
                assertThat("Constructor?", ((ConstructorSignature)joinPoint.getSignature()).getConstructor().toString(),
                        is("public eval.org.aspectj.lang.fixture.PreInitializationTestFixture(java.lang.String)"));
            }
        });
    }
}
