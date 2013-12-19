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

import eval.org.aspectj.lang.subject.FieldSetTestSubject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.FieldSignature;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * FieldSetTest contains tests for FieldSetAspect.
 */
public class FieldSetTest extends AbstractAspectTest {

    public static final int NEW_FIELD_VALUE = 26;
    private Caller caller=new Caller();
    private FieldSetTestSubject testSubject;

    public FieldSetTest() {
        super("eval.org.aspectj.lang.FieldSetAspect", JoinPoint.FIELD_SET, FieldSignature.class);
    }

    @Before
    public void setUp() throws Exception {
        testSubject = new FieldSetTestSubject();
    }

    @Test
    public void setFieldViaSetter() throws Exception {
        // act / when
        testSubject.setField(NEW_FIELD_VALUE);

        // assert / then
        assertJoinPoint(new AdditionalAssert() {
            @Override
            public void additionalAssert(final JoinPoint joinPoint, final String thisContext, final String targetContext) {
                assertThat("This?", joinPoint.getThis(), sameInstance((Object) testSubject));
                assertThat("Target?", joinPoint.getTarget(), sameInstance((Object) testSubject));
                assertThat("#Arguments==1?", joinPoint.getArgs().length,   is(1));
                assertThat("Argument value?",(Integer)joinPoint.getArgs()[0],   is(NEW_FIELD_VALUE));
            }
        });
    }

    @Test
    public void setPublicField() throws Exception {
        // act / when
        caller.setField(testSubject, NEW_FIELD_VALUE);

        // assert / then
        assertJoinPoint(new AdditionalAssert() {
            @Override
            public void additionalAssert(final JoinPoint joinPoint, final String thisContext, final String targetContext) {
                assertThat("This?", joinPoint.getThis(), sameInstance((Object) caller));
                assertThat("Target?", joinPoint.getTarget(), sameInstance((Object) testSubject));
                assertThat("#Arguments==1?", joinPoint.getArgs().length,   is(1));
                assertThat("Argument value?", (Integer)joinPoint.getArgs()[0],   is(NEW_FIELD_VALUE));
            }
        });
    }


}
