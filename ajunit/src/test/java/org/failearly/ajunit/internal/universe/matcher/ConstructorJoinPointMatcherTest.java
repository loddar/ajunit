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
import org.aspectj.lang.reflect.ConstructorSignature;
import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointMatcher;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * ConstructorJoinPointMatcherTest tests {@link ConstructorJoinPointMatcher}.
 */
public class ConstructorJoinPointMatcherTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConstructorJoinPointMatcherTest.class);

    private final AjJoinPointMatcher matcher = new ConstructorJoinPointMatcher(AjJoinPointType.CONSTRUCTOR_EXECUTION);

    @Test
    public void sameConstructorAndJoinPointTypes() throws Exception {
        // arrange / given
        final JoinPoint aspectJoinPoint = createAspectJoinPointMock(AjJoinPointType.CONSTRUCTOR_EXECUTION);
        final AjJoinPoint ajJoinPoint = createAjUnitJoinPointMock(AjJoinPointType.CONSTRUCTOR_EXECUTION);

        // act / when
        final boolean match = matcher.match(aspectJoinPoint, ajJoinPoint);

        // assert / then
        assertThat("Match?", match, is(true));
    }

    @Test
    public void sameConstructorButDifferentJoinPointTypes() throws Exception {
        // arrange / given
        final JoinPoint aspectJoinPoint = createAspectJoinPointMock(AjJoinPointType.CONSTRUCTOR_EXECUTION);
        final AjJoinPoint ajJoinPoint = createAjUnitJoinPointMock(AjJoinPointType.CONSTRUCTOR_CALL);

        // act / when
        final boolean match = matcher.match(aspectJoinPoint, ajJoinPoint);

        // assert / then
        assertThat("Match?", match, is(false));
    }

    @Test
    public void differentConstructors() throws Exception {
        // arrange / given
        final JoinPoint aspectJoinPoint = createAspectJoinPointMock(AjJoinPointType.CONSTRUCTOR_EXECUTION);
        final AjJoinPoint ajJoinPoint = createAjUnitJoinPointMock(AjJoinPointType.CONSTRUCTOR_EXECUTION, int.class);

        // act / when
        final boolean match = matcher.match(aspectJoinPoint, ajJoinPoint);

        // assert / then
        assertThat("Match?", match, is(false));
    }

    private AjJoinPoint createAjUnitJoinPointMock(AjJoinPointType joinPointType, Class<?>... parameterClasses) {
        final AjJoinPoint ajJoinPoint = mock(AjJoinPoint.class);

        when(ajJoinPoint.getJoinPointType()).thenReturn(joinPointType);
        when(ajJoinPoint.getConstructor()).thenReturn(resolveConstructor(parameterClasses));

        LOGGER.debug("AjJoinPoint Mock - getJoinPointType() => {}", ajJoinPoint.getJoinPointType());
        LOGGER.debug("AjJoinPoint Mock - getConstructor() => {}", ajJoinPoint.getConstructor());

        return ajJoinPoint;
    }

    private JoinPoint createAspectJoinPointMock(AjJoinPointType joinPointType, Class<?>... parameterClasses) {
        final JoinPoint joinPoint = mock(JoinPoint.class);
        final Signature constructorSignatureMock = createConstructorSignatureMock(parameterClasses);

        when(joinPoint.getKind()).thenReturn(joinPointType.getJoinPointKind());
        when(joinPoint.getSignature()).thenReturn(constructorSignatureMock);

        LOGGER.debug("JoinPoint Mock - getKind() => {}", joinPoint.getKind());
        LOGGER.debug("JoinPoint Mock - getSignature() => {}", joinPoint.getSignature());

        return joinPoint;
    }

    private Signature createConstructorSignatureMock(Class<?>... parameterClasses) {
        final ConstructorSignature constructorSignature = mock(ConstructorSignature.class);
        when(constructorSignature.getConstructor()).thenReturn(resolveConstructor(parameterClasses));

        LOGGER.debug("ConstructorSignature Mock - getConstructor() = {}", constructorSignature.getConstructor());

        return constructorSignature;
    }


    private Constructor resolveConstructor(Class<?>... parameterClasses) {
        try {
            return AnyClass.class.getDeclaredConstructor(parameterClasses);
        } catch (NoSuchMethodException e) {
            LOGGER.error("No such constructor found");
        }
        return null;
    }
}
