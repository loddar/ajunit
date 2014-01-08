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
package org.failearly.ajunit.internal.builder;

import org.failearly.ajunit.internal.predicate.Predicate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * LogicalStructureBuilderTest is responsible for ...
 */
public class LogicalStructureBuilderTest {

    private static final Object ANY_VALUE=Boolean.TRUE;

    @Test
    public void rootOnly() throws Exception {
        assertPredicateBuild(new TopBuilder(), true, "Or()");
    }

    @Test
    public void rootOnlyWithTrue() throws Exception {
        // arrange / given
        final TopBuilder topBuilder=new TopBuilder();

        // act / when
        topBuilder.alwaysFalse();

        // assert / then
        assertPredicateBuild(topBuilder, false, "Or(FALSE)");
    }

    @Test
    public void rootOnlyWithTrueFalse() throws Exception {
        // arrange / given
        final TopBuilder topBuilder=new TopBuilder();

        // act / when
        topBuilder.alwaysFalse()
                  .alwaysTrue();

        // assert / then
        assertPredicateBuild(topBuilder, true, "Or(FALSE,TRUE)");
    }

    @Test
    public void rootX() throws Exception {
        // arrange / given
        final TopBuilder topBuilder=new TopBuilder();

        // act / when
        topBuilder.xBuilder().alwaysTrue().alwaysFalse();

        // assert / then
        assertPredicateBuild(topBuilder, false, "Or(And(TRUE,FALSE))");
    }

    @Test
    public void rootXorX() throws Exception {
        // arrange / given
        final TopBuilder topBuilder=new TopBuilder();

        // act / when
        topBuilder.xBuilder()
                    .or()
                        .alwaysTrue()
                        .alwaysFalse();

        // assert / then
        assertPredicateBuild(topBuilder, true, "Or(And(Or(TRUE,FALSE)))");
    }


    @Test
    public void rootXorXAndFalse() throws Exception {
        // arrange / given
        final TopBuilder topBuilder=new TopBuilder();

        // act / when
        topBuilder.xBuilder()
                       .or()
                            .alwaysTrue()
                            .alwaysFalse()
                       .end()
                       .alwaysFalse();

        // assert / then
        assertPredicateBuild(topBuilder, false, "Or(And(Or(TRUE,FALSE),FALSE))");
    }


    @Test
    public void rootYAndX() throws Exception {
        // arrange / given
        final TopBuilder topBuilder=new TopBuilder();

        // act / when
        topBuilder.yBuilder()
                    .andX()
                        .alwaysTrue()
                        .alwaysFalse();

        // assert / then
        assertPredicateBuild(topBuilder, false, "Or(Or(And(TRUE,FALSE)))");
    }

    @Test
    public void rootYAndXOrX() throws Exception {
        // arrange / given
        final TopBuilder topBuilder=new TopBuilder();

        // act / when
        topBuilder.yBuilder()
                .andX()
                .alwaysTrue()
                .alwaysFalse()
                .endX()
                .or()
                .alwaysTrue()
                .end();

        // assert / then
        assertPredicateBuild(topBuilder, true, "Or(Or(And(TRUE,FALSE),Or(TRUE)))");
    }

    @Test
    public void rootXOrXdoneYdone() throws Exception {
        // arrange / given
        final TopBuilder topBuilder=new TopBuilder();

        // act / when
        topBuilder.xBuilder()
                      .or()
                        .alwaysFalse()
                   .done()
                   .yBuilder()
                        .alwaysFalse()
                   .done();

        // assert / then
        assertPredicateBuild(topBuilder, false, "Or(And(Or(FALSE)),Or(FALSE))");
    }

    private static void assertPredicateBuild(TopBuilder topBuilder, boolean expectedPredicateResult, String expectedExpression) {
        final Predicate predicate=topBuilder.build();
        assertThat("Predicate build evaluates to?", predicate.evaluate(ANY_VALUE), is(expectedPredicateResult));
        assertThat("Expression build?", predicate.toString(), is(expectedExpression));
    }
}
