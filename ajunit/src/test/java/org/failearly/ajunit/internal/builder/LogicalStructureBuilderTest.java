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
package org.failearly.ajunit.internal.builder;

import org.failearly.ajunit.internal.predicate.Predicate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * LogicalStructureBuilderTest is responsible for ...
 */
public class LogicalStructureBuilderTest {

    private static final Object ANY_VALUE=Boolean.TRUE;

    @Test
    public void topOnly() throws Exception {
        assertPredicateBuild(new TopBuilder(), true);
    }

    @Test
    public void topOnlyWithTrue() throws Exception {
        // arrange / given
        final TopBuilder topBuilder=new TopBuilder();

        // act / when
        topBuilder.alwaysFalse();

        // assert / then
        assertPredicateBuild(topBuilder, false);
    }

    @Test
    public void topOnlyWithTrueFalse() throws Exception {
        // arrange / given
        final TopBuilder topBuilder=new TopBuilder();

        // act / when
        topBuilder.alwaysFalse()
                  .alwaysTrue();

        // assert / then
        assertPredicateBuild(topBuilder, true);
    }

    @Test
    public void complexExpressionAndEndingAll() throws Exception {
        // arrange / given
        final TopBuilder topBuilder=new TopBuilder();

        // act / when
        final TopBuilder builder=topBuilder      // Or
                       .and()                    //    And
                            .alwaysTrue()        //       TRUE
                            .alwaysFalse()       //       FALSE
                       .end()
                       .or()                     //    Or
                            .xBuilder()          //       And
                                .alwaysFalse()   //           FALSE
                                .alwaysTrue()    //           TRUE
                            .endTop()
                            .yBuilder()          //       Or
                                .and()           //           And
                                    .alwaysTrue() //             FALSE
                                    .alwaysFalse()//             TRUE
                                .end()
                            .endTop()
                       .end();

        // assert / then
        assertThat("Correctly closed?", builder, sameInstance(topBuilder));
        assertPredicateBuild(topBuilder, false);
    }

    @Test
    public void topX() throws Exception {
        // arrange / given
        final TopBuilder topBuilder=new TopBuilder();

        // act / when
        topBuilder.xBuilder().alwaysTrue().alwaysFalse();

        // assert / then
        assertPredicateBuild(topBuilder, false);
    }

    @Test
    public void topXorX() throws Exception {
        // arrange / given
        final TopBuilder topBuilder=new TopBuilder();

        // act / when
        topBuilder.xBuilder()
                    .or()
                        .alwaysTrue()
                        .alwaysFalse();

        // assert / then
        assertPredicateBuild(topBuilder, true);
    }


    @Test
    public void topXorXAndFalse() throws Exception {
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
        assertPredicateBuild(topBuilder, false);
    }


    @Test
    public void topYAndX() throws Exception {
        // arrange / given
        final TopBuilder topBuilder=new TopBuilder();

        // act / when
        topBuilder.yBuilder()
                      .andX()
                        .alwaysTrue()
                        .alwaysFalse();

        // assert / then
        assertPredicateBuild(topBuilder, false);
    }

    @Test
    public void topYAndXOrX() throws Exception {
        // arrange / given
        final TopBuilder topBuilder=new TopBuilder();

        // act / when
        topBuilder.yBuilder()
                    .andX()
                        .alwaysTrue()
                        .alwaysFalse()
                    .endY()
                    .or()
                        .alwaysTrue()
                    .end();

        // assert / then
        assertPredicateBuild(topBuilder, true);
    }

    @Test
    public void multipleDone() throws Exception {
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
        assertPredicateBuild(topBuilder, false);
    }

    @Test(expected = IllegalStateException.class)
    public void multipleBuild() throws Exception {
        // arrange / given
        final TopBuilder topBuilder=new TopBuilder();

        // act / when
        topBuilder.build();
        topBuilder.build();
    }



    private static void assertPredicateBuild(
            final TopBuilder topBuilder,
            final boolean expectedPredicateResult) {

        final TopBuilder   builder=topBuilder.done();
        assertThat("Done returns ROOT builder?", builder, sameInstance(topBuilder));

        final Predicate predicate=topBuilder.build();
        assertThat("Predicate build evaluates to?", predicate.test(ANY_VALUE), is(expectedPredicateResult));
    }
}
