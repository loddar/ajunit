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
package org.failearly.ajunit.internal.universe.matcher;

import org.aspectj.lang.JoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointMatcher;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Test for {@link org.failearly.ajunit.internal.universe.matcher.NullMatcher}.
 */
public class NullMatcherTest {
    @Test
    public void nullMatcherAlwaysReturnsFalse() throws Exception {
        // arrange / given
        final JoinPoint aspectJoinPoint = mock(JoinPoint.class);
        final AjJoinPoint ajJoinPoint = mock(AjJoinPoint.class);
        final AjJoinPointMatcher matcher = JoinPointMatchers.nullMatcher();

        // act / when
        final boolean match = matcher.match(aspectJoinPoint, ajJoinPoint);

        // assert / then
        assertThat("Match?", match, is(false));
    }
}