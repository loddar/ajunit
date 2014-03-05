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
package org.failearly.ajunit.internal.predicate.collection;

import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link org.failearly.ajunit.internal.predicate.collection.CollectionPredicates}.
 */
public class CollectionPredicatesTest {

    @Test
    public void anyOf() throws Exception {
        // arrange / given
        final Predicate predicate = CollectionPredicates.anyOf(StandardPredicates.booleanIdentity());

        // assert / then
        assertThat("{false,false}->false?", predicate.evaluate(toBooleanList(false, false)), is(false));
        assertThat("{false,true}->true?", predicate.evaluate(toBooleanList(false, true)), is(true));
        assertThat("{true,false}->true?", predicate.evaluate(toBooleanList(true, false)), is(true));
        assertThat("{true,false}->true?", predicate.evaluate(toBooleanList(true, true)), is(true));
        assertThat("{}->false?", predicate.evaluate(toBooleanList()), is(false));
    }

    @Test
    public void allOf() throws Exception {
        // arrange / given
        final Predicate predicate = CollectionPredicates.allOf(StandardPredicates.booleanIdentity());

        // assert / then
        assertThat("{false,false}->false?", predicate.evaluate(toBooleanList(false, false)), is(false));
        assertThat("{false,true}->false?", predicate.evaluate(toBooleanList(false, true)), is(false));
        assertThat("{true,false}->false?", predicate.evaluate(toBooleanList(true, false)), is(false));
        assertThat("{true,true}->true?", predicate.evaluate(toBooleanList(true, true)), is(true));
        assertThat("{}->false?", predicate.evaluate(toBooleanList()), is(false));
    }

    @Test
    public void allOfAnyOf() throws Exception {
        // arrange / given
        final Predicate predicate = CollectionPredicates.allOf(
                                        CollectionPredicates.anyOf(
                                            StandardPredicates.booleanIdentity()
                                        )
                                    );

        // assert / then
        assertThat("{true,true}->true?", predicate.evaluate(
                        toCollectionsOfBooleanList(
                            toBooleanList(false,true),
                            toBooleanList(true, true)

                        )
                ), is(true));
        assertThat("{true,false}->false?", predicate.evaluate(
                        toCollectionsOfBooleanList(
                            toBooleanList(true, false),
                            toBooleanList(false)
                        )
                ), is(false));
    }


    @Test
    public void anyOfAllOf() throws Exception {
        // arrange / given
        final Predicate predicate = CollectionPredicates.anyOf(
                                        CollectionPredicates.allOf(
                                            StandardPredicates.booleanIdentity()
                                        )
                                    );

        // assert / then
        assertThat("{true,true}->true?", predicate.evaluate(
                        toCollectionsOfBooleanList(
                            toBooleanList(true, true),
                            toBooleanList(true)

                        )
                ), is(true));
        assertThat("{false,true}->true?", predicate.evaluate(
                        toCollectionsOfBooleanList(
                            toBooleanList(false),
                            toBooleanList(true)
                        )
                ), is(true));
        assertThat("{false,false}->false?", predicate.evaluate(
                        toCollectionsOfBooleanList(
                                toBooleanList(false),
                                toBooleanList(false)
                        )
                ), is(false));
    }




    private static Collection<Boolean> toBooleanList(Boolean... values) {
        return Arrays.asList(values);
    }

    @SafeVarargs
    private static Collection toCollectionsOfBooleanList(Collection<Boolean>... booleanList) {
        return Arrays.asList(booleanList);
    }
}
