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
package org.failearly.ajunit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.failearly.ajunit.internal.universe.impl.AjUniversesHolder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for ajUnit aspect tests.
 */
public class AjUnitAspectTest {

    private static final String UNIVERSE_NAME_1 = AjUnitAspect1.class.getName();
    private static final String UNIVERSE_NAME_2 = AjUnitAspect2.class.getName();

    private static class AjUnitAspect1 extends AjUnitAspectBase {
        void simulateAdvice(JoinPoint.StaticPart jointPoint) {
            applyJoinPoint(jointPoint, null);
        }
    }

    private static class AjUnitAspect2 extends AjUnitAspectBase {
        void simulateAdvice(JoinPoint.StaticPart jointPoint) {
            applyJoinPoint(jointPoint, null);
        }
    }

    @Before
    public void setUp() throws Exception {
        AjUniversesHolder.buildUniverseByClasses(UNIVERSE_NAME_1, Arrays.asList(AnyClass.class));
    }

    @After
    public void tearDown() throws Exception {
        AjUniversesHolder.dropUniverse(UNIVERSE_NAME_1);
    }

    @Test
    public void onlyOneActiveUniverse() throws Exception {
        // arrange / given
        final JoinPoint.StaticPart joinPoint=joinpoint();

        // act / when
        new AjUnitAspect1().simulateAdvice(joinPoint);

        // assert / then
        verifyJoinPointInteractions(joinPoint);
    }

    @Test
    public void twoActiveUniverse() throws Exception {
        // arrange / given
        AjUniversesHolder.buildUniverseByClasses(UNIVERSE_NAME_2, Arrays.asList(AnyClass.class));
        final JoinPoint.StaticPart joinPoint1=joinpoint();
        final JoinPoint.StaticPart joinPoint2=joinpoint();

        // act / when
        new AjUnitAspect1().simulateAdvice(joinPoint1);
        new AjUnitAspect2().simulateAdvice(joinPoint2);

        // assert / then
        verifyJoinPointInteractions(joinPoint1);
        verifyJoinPointInteractions(joinPoint2);
        AjUniversesHolder.dropUniverse(UNIVERSE_NAME_2);
    }

    @Test
    public void oneActiveAndOneInActiveUniverse() throws Exception {
        // arrange / given
        final JoinPoint.StaticPart joinPoint1=joinpoint();
        final JoinPoint.StaticPart joinPoint2=joinpoint();

        // act / when
        new AjUnitAspect1().simulateAdvice(joinPoint1);
        new AjUnitAspect2().simulateAdvice(joinPoint2);

        // assert / then
        verifyJoinPointInteractions(joinPoint1);
        verifyJoinPointInteractions(joinPoint2);
    }

    @Test
    public void twoAspectInstance() throws Exception {
        // arrange / given
        final JoinPoint.StaticPart joinPoint=joinpoint();

        // act / when
        new AjUnitAspect1().simulateAdvice(joinPoint);
        new AjUnitAspect1().simulateAdvice(joinPoint);

        // assert / then
        assertThat("#Aspect instances?", AjUniversesHolder.findUniverse(UNIVERSE_NAME_1).getNumberOfAspectInstances(), is(2));
    }

    private static void verifyJoinPointInteractions(JoinPoint.StaticPart joinPoint) {
        Mockito.verify(joinPoint, VerificationModeFactory.atLeastOnce()).getKind();
        Mockito.verify(joinPoint, VerificationModeFactory.atLeastOnce()).getSignature();
        Mockito.verifyNoMoreInteractions(joinPoint);
    }

    private static JoinPoint.StaticPart joinpoint() throws NoSuchMethodException {
        final JoinPoint.StaticPart joinPointStaticPart=Mockito.mock(JoinPoint.StaticPart.class);
        Mockito.when(joinPointStaticPart.getKind()).thenReturn(JoinPoint.METHOD_EXECUTION);
        final MethodSignature methodSignature = getMethodSignature();
        Mockito.when(joinPointStaticPart.getSignature()).thenReturn(methodSignature);
        return joinPointStaticPart;
    }

    private static MethodSignature getMethodSignature() throws NoSuchMethodException {
        final MethodSignature methodSignature= Mockito.mock(MethodSignature.class);
        Mockito.when(methodSignature.getMethod()).thenReturn(method());
        return methodSignature;
    }

    private static Method method() throws NoSuchMethodException {
        return AnyClass.class.getDeclaredMethod("anyMethod");
    }

    @SuppressWarnings("all")
    private static class AnyClass {
        void anyMethod() {
        }
    }
}
