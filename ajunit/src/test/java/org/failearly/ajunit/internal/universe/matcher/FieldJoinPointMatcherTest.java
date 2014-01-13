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
package org.failearly.ajunit.internal.universe.matcher;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.FieldSignature;
import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointMatcher;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * FieldJoinPointMatcherTest tests {@link FieldJoinPointMatcher}.
 */
public class FieldJoinPointMatcherTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldJoinPointMatcherTest.class);

    private final AjJoinPointMatcher matcher = JoinPointMatchers.fieldMatcher(AjJoinPointType.FIELD_GET);

    @Test
    public void sameFieldAndJoinPointTypes() throws Exception {
        // arrange / given
        final JoinPoint aspectJoinPoint = createAspectJoinPointMock(AjJoinPointType.FIELD_GET, "field");
        final AjJoinPoint ajJoinPoint = createAjUnitJoinPointMock(AjJoinPointType.FIELD_GET, "field");

        // act / when
        final boolean match = matcher.match(aspectJoinPoint, ajJoinPoint);

        // assert / then
        assertThat("Match?", match, is(true));
    }

    @Test
    public void sameFieldButDifferentJoinPointTypes() throws Exception {
        // arrange / given
        final JoinPoint aspectJoinPoint = createAspectJoinPointMock(AjJoinPointType.FIELD_GET, "field");
        final AjJoinPoint ajJoinPoint = createAjUnitJoinPointMock(AjJoinPointType.FIELD_SET, "field");

        // act / when
        final boolean match = matcher.match(aspectJoinPoint, ajJoinPoint);

        // assert / then
        assertThat("Match?", match, is(false));
    }

    @Test
    public void differentFields() throws Exception {
        // arrange / given
        final JoinPoint aspectJoinPoint = createAspectJoinPointMock(AjJoinPointType.FIELD_GET, "field");
        final AjJoinPoint ajJoinPoint = createAjUnitJoinPointMock(AjJoinPointType.FIELD_GET, "otherField");

        // act / when
        final boolean match = matcher.match(aspectJoinPoint, ajJoinPoint);

        // assert / then
        assertThat("Match?", match, is(false));
    }

    private AjJoinPoint createAjUnitJoinPointMock(AjJoinPointType joinPointType, String fieldName) {
        final AjJoinPoint ajJoinPoint = mock(AjJoinPoint.class);

        when(ajJoinPoint.getJoinPointType()).thenReturn(joinPointType);
        when(ajJoinPoint.getField()).thenReturn(resolveField(fieldName));

        LOGGER.debug("AjJoinPoint Mock - getJoinPointType() => {}", ajJoinPoint.getJoinPointType());
        LOGGER.debug("AjJoinPoint Mock - getField() => {}", ajJoinPoint.getField());

        return ajJoinPoint;
    }

    private JoinPoint createAspectJoinPointMock(AjJoinPointType joinPointType, String fieldName) {
        final JoinPoint joinPoint = mock(JoinPoint.class);
        final Signature fieldSignatureMock = createFieldSignatureMock(fieldName);

        when(joinPoint.getKind()).thenReturn(joinPointType.getJoinPointKind());
        when(joinPoint.getSignature()).thenReturn(fieldSignatureMock);

        LOGGER.debug("JoinPoint Mock - getKind() => {}", joinPoint.getKind());
        LOGGER.debug("JoinPoint Mock - getSignature() => {}", joinPoint.getSignature());

        return joinPoint;
    }

    private Signature createFieldSignatureMock(String fieldName) {
        final FieldSignature fieldSignature = mock(FieldSignature.class);
        when(fieldSignature.getField()).thenReturn(resolveField(fieldName));

        LOGGER.debug("FieldSignature Mock - getField() = {}", fieldSignature.getField());

        return fieldSignature;
    }


    private Field resolveField(String fieldName) {
        try {
            return AnyClass.class.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            LOGGER.error("No such field found {}", fieldName);
        }
        return null;
    }
}
