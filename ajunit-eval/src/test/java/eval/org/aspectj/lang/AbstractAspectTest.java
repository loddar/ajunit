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

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.After;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * AbstractAspectTest is responsible for ...
 */
public abstract class AbstractAspectTest {

    protected interface AdditionalAssert {
        void additionalAssert(JoinPoint joinPoint, final String thisContext, final String targetContext);
    }

    private final Class<? extends JoinPointCollector> aspectClass;
    private final String expectedJoinPointKind;
    private final Class<? extends Signature> expectedSignatureClass;

    private final AdditionalAssert defaultAdditionalAssert = new AdditionalAssert() {
        @Override
        public void additionalAssert(final JoinPoint joinPoint, final String thisContext, final String targetContext) {
            doAdditionalAsserts(joinPoint);
        }
    };

    protected AbstractAspectTest(
            final String aspectClassName,
            final String expectedJoinPointKind,
            final Class<? extends Signature> expectedSignatureClass) {
        this.aspectClass = loadAspectClass(aspectClassName);
        this.expectedJoinPointKind = expectedJoinPointKind;
        this.expectedSignatureClass = expectedSignatureClass;
    }


    private static Class<? extends JoinPointCollector> loadAspectClass(final String aspectClassName) {
        try {
            return (Class<? extends JoinPointCollector>)Class.forName(aspectClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unknown aspect class " + aspectClassName, e );
        }
    }


    @After
    public void clearCollectedJoinPoints() throws Exception {
        JoinPointCollector.clear();
    }

    protected final void assertJoinPoint() {
        this.assertJoinPoint(defaultAdditionalAssert);
    }

    protected final void assertJoinPoint(final AdditionalAssert additionalAssert) {
        final JoinPoint joinPoint = JoinPointCollector.getJoinPoint(aspectClass);

        assertThat("JoinPoint aspect has been executed?", joinPoint, notNullValue());
        assertThat("JoinPoint kind?", joinPoint.getKind(), is(expectedJoinPointKind));
        assertThat("Signature type?", joinPoint.getSignature(), instanceOf(expectedSignatureClass));
        assertThat("#Called Join Points?", JoinPointCollector.getNumCalledJoinPoints(), is(1));

        additionalAssert.additionalAssert(joinPoint,
                JoinPointCollector.getThisContext(aspectClass),
                JoinPointCollector.getTargetContext(aspectClass));
    }

    protected final void assertJoinPointNotExecuted() {
        final JoinPoint joinPoint = JoinPointCollector.getJoinPoint(aspectClass);

        assertThat("JoinPoint aspect should not be executed?", joinPoint, nullValue());
    }

    protected void doAdditionalAsserts(final JoinPoint joinPoint) {
        // to be overloaded
    }

}
