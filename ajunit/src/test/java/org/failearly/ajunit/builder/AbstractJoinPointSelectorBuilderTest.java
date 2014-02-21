/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
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
package org.failearly.ajunit.builder;

import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorBuilderImpl;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.failearly.ajunit.internal.universe.AjUniverse;
import org.failearly.ajunit.internal.universe.impl.AjUniversesHolder;
import org.failearly.ajunit.internal.util.AjUnitUtils;
import org.failearly.ajunit.test.helper.StandardJoinPointVisitor;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

/**
 * AbstractJoinPointSelectorBuilderTest is responsible for ...
 */
public abstract class AbstractJoinPointSelectorBuilderTest<T extends SelectorBuilder> {
    private static final String UNIVERSE_NAME = "AbstractJoinPointSelectorBuilderTest$Universe";
    private static AjUniverse ajUniverse;
    private final AjJoinPointType expectedJoinPointType;
    private Set<AjJoinPointType> joinPointTypes;

    protected T selectorBuilder;
    private JoinPointSelectorBuilderImpl joinPointSelectorBuilder;

    @BeforeClass
    public static void createUniverse() throws Exception {
        ajUniverse=AjUniversesHolder.createUniverseByClasses(UNIVERSE_NAME, AjUnitUtils.toClassList(TestSubject.class));
    }

    @AfterClass
    public static void dropUniverse() throws Exception {
        AjUniversesHolder.dropUniverse(UNIVERSE_NAME);
    }

    protected AbstractJoinPointSelectorBuilderTest(AjJoinPointType expectedJoinPointType) {
        this.expectedJoinPointType = expectedJoinPointType;
    }

    @Before
    public final void setUp() throws Exception {
        joinPointTypes = new HashSet<>();
        joinPointSelectorBuilder = new JoinPointSelectorBuilderImpl(joinPointTypes);
        selectorBuilder=createSelectorBuilderUnderTest(this.joinPointSelectorBuilder);
    }

    protected abstract T createSelectorBuilderUnderTest(JoinPointSelectorBuilder joinPointSelectorBuilder);

    protected final void assertJoinPointType() {
        assertThat("Join Point Type?", this.joinPointTypes, containsInAnyOrder(this.expectedJoinPointType));
    }

    protected final void assertBuildJoinPointSelector(String... expectedJoinPoints) {
        final StandardJoinPointVisitor joinPointVisitor = new StandardJoinPointVisitor(this.joinPointSelectorBuilder.build());
        ajUniverse.visitJoinPoints(joinPointVisitor);
        assertThat("Matching join points?", joinPointVisitor.getSelectedJoinPoints(), containsInAnyOrder(expectedJoinPoints));
    }

    protected final void assertEndSpecificJoinPointBuilder(String methodName, JoinPointSelectorBuilder joinPointSelectorBuilder) {
        assertThat(methodName + " returns same created JoinPointSelectorBuilder?",
                    joinPointSelectorBuilder, sameInstance((JoinPointSelectorBuilder)this.joinPointSelectorBuilder));
    }

}
