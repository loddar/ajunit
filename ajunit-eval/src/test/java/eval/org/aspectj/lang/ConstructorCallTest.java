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
package eval.org.aspectj.lang;

import eval.org.aspectj.lang.fixture.ConstructorCallTestFixture;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * ConstructorCallTest tests {@link eval.org.aspectj.lang.ConstructorCallAspect}.
 */
public class ConstructorCallTest extends AbstractAspectTest {

    private Caller caller=new Caller();

    public ConstructorCallTest() {
        super("eval.org.aspectj.lang.ConstructorCallAspect", JoinPoint.CONSTRUCTOR_CALL, ConstructorSignature.class);
    }

    @Test
    public void createByDefaultConstructor() throws Exception {
        // act / when
        final ConstructorCallTestFixture testFixture = caller.createConstructorCallTestFixture();

        // assert / then
        assertJoinPoint(new AdditionalAssert() {
            @Override
            public void additionalAssert(final JoinPoint joinPoint, final String thisContext, final String targetContext) {
                assertThat("This?", joinPoint.getThis(), sameInstance((Object) caller));
                assertThat("Target?", joinPoint.getTarget(), nullValue());
                assertThat("#Arguments?", joinPoint.getArgs().length, is(0));
            }
        });
    }
}
