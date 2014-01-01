/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2014  Marko Umek (ajunit.contact(at)gmail.com)
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
package org.failearly.ajunit.internal.universe.matcher;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointMatcher;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * MethodJoinPointMatcherTest tests {@link MethodJoinPointMatcher}.
 */
public class MethodJoinPointMatcherTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodJoinPointMatcherTest.class);

    private final AjJoinPointMatcher matcher = new MethodJoinPointMatcher(AjJoinPointType.METHOD_EXECUTION);

    @Test
    public void sameMethodAndJoinPointTypes() throws Exception {
        // arrange / given
        final JoinPoint aspectJoinPoint = createAspectJoinPointMock(AjJoinPointType.METHOD_EXECUTION, "anyMethod");
        final AjJoinPoint ajJoinPoint = createAjUnitJoinPointMock(AjJoinPointType.METHOD_EXECUTION, "anyMethod");

        // act / when
        final boolean match = matcher.match(aspectJoinPoint, ajJoinPoint);

        // assert / then
        assertThat("Match?", match, is(true));
    }

    @Test
    public void sameMethodButDifferentJoinPointTypes() throws Exception {
        // arrange / given
        final JoinPoint aspectJoinPoint = createAspectJoinPointMock(AjJoinPointType.METHOD_EXECUTION, "anyMethod");
        final AjJoinPoint ajJoinPoint = createAjUnitJoinPointMock(AjJoinPointType.METHOD_CALL, "anyMethod");

        // act / when
        final boolean match = matcher.match(aspectJoinPoint, ajJoinPoint);

        // assert / then
        assertThat("Match?", match, is(false));
    }

    @Test
    public void differentMethods() throws Exception {
        // arrange / given
        final JoinPoint aspectJoinPoint = createAspectJoinPointMock(AjJoinPointType.METHOD_EXECUTION, "anyMethod");
        final AjJoinPoint ajJoinPoint = createAjUnitJoinPointMock(AjJoinPointType.METHOD_EXECUTION, "otherMethod");

        // act / when
        final boolean match = matcher.match(aspectJoinPoint, ajJoinPoint);

        // assert / then
        assertThat("Match?", match, is(false));
    }

    private AjJoinPoint createAjUnitJoinPointMock(AjJoinPointType joinPointType, String methodName) {
        final AjJoinPoint ajJoinPoint = mock(AjJoinPoint.class);

        when(ajJoinPoint.getJoinPointType()).thenReturn(joinPointType);
        when(ajJoinPoint.getMethod()).thenReturn(resolveMethod(methodName));

        LOGGER.debug("AjJoinPoint Mock - getJoinPointType() => {}", ajJoinPoint.getJoinPointType());
        LOGGER.debug("AjJoinPoint Mock - getMethod() => {}", ajJoinPoint.getMethod());

        return ajJoinPoint;
    }

    private JoinPoint createAspectJoinPointMock(AjJoinPointType joinPointType, String methodName) {
        final JoinPoint joinPoint = mock(JoinPoint.class);
        final Signature methodSignatureMock = createMethodSignatureMock(methodName);

        when(joinPoint.getKind()).thenReturn(joinPointType.getJoinPointKind());
        when(joinPoint.getSignature()).thenReturn(methodSignatureMock);

        LOGGER.debug("JoinPoint Mock - getKind() => {}", joinPoint.getKind());
        LOGGER.debug("JoinPoint Mock - getSignature() => {}", joinPoint.getSignature());

        return joinPoint;
    }

    private Signature createMethodSignatureMock(String methodName) {
        final MethodSignature methodSignature = mock(MethodSignature.class);
        when(methodSignature.getMethod()).thenReturn(resolveMethod(methodName));

        LOGGER.debug("MethodSignature Mock - getMethod() = {}", methodSignature.getMethod());

        return methodSignature;
    }


    private Method resolveMethod(String methodName) {
        try {
            return AnyClass.class.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
           LOGGER.error("No such method found {}", methodName);
        }
        return null;
    }
}
