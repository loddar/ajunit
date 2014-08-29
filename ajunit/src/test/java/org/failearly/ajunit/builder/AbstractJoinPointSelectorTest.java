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
package org.failearly.ajunit.builder;

import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorImpl;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.universe.AjJoinPointStringBuilder;
import org.failearly.ajunit.internal.universe.AjJoinPointStringBuilderBase;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.failearly.ajunit.internal.universe.AjUniverse;
import org.failearly.ajunit.internal.universe.impl.AjUniversesHolder;
import org.failearly.ajunit.internal.util.AjUnitUtils;
import org.failearly.ajunit.test.helper.StandardJoinPointVisitor;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * Base class for all {@link org.failearly.ajunit.builder.JoinPointSelector} Tests.
 */
public abstract class AbstractJoinPointSelectorTest<T extends SelectorBuilder> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJoinPointSelectorTest.class);

    private static final String UNIVERSE_NAME = "AbstractJoinPointSelectorTest$Universe";
    private AjUniverse ajUniverse;
    private final AjJoinPointType expectedJoinPointType;
    private Set<AjJoinPointType> joinPointTypes;

    protected T selectorBuilder;
    private JoinPointSelectorImpl joinPointSelectorBuilder;
    private final List<Class<?>> testFixtureClasses;

    protected AbstractJoinPointSelectorTest(AjJoinPointType expectedJoinPointType, Class<?>... testFixtureClasses) {
        this(expectedJoinPointType, AjUnitUtils.toClassList(testFixtureClasses));
    }

    private AbstractJoinPointSelectorTest(AjJoinPointType expectedJoinPointType, List<Class<?>> testFixtureClasses) {
        this.expectedJoinPointType = expectedJoinPointType;
        this.testFixtureClasses = testFixtureClasses;
    }

    @Before
    public void createUniverse() throws Exception {
        ajUniverse=AjUniversesHolder.createUniverseByClasses(UNIVERSE_NAME,testFixtureClasses);
    }

    @After
    public void dropUniverse() throws Exception {
        AjUniversesHolder.dropUniverse(UNIVERSE_NAME);
    }

    @Before
    public final void setUp() throws Exception {
        joinPointTypes = new HashSet<>();
        joinPointSelectorBuilder = new JoinPointSelectorImpl(joinPointTypes);
        selectorBuilder=createSelectorBuilderUnderTest(this.joinPointSelectorBuilder);
        doAdditionalSetup(selectorBuilder);
    }

    protected void doAdditionalSetup(T selectorBuilder) {}

    protected abstract T createSelectorBuilderUnderTest(JoinPointSelector joinPointSelector);

    protected final void assertJoinPointType() {
        assertThat("Join Point Type?", this.joinPointTypes, Matchers.containsInAnyOrder(this.expectedJoinPointType));
    }

    protected final void assertBuildJoinPointSelector(String... expectedJoinPoints) {
        final Predicate predicate=this.joinPointSelectorBuilder.build();
        LOGGER.info("Created predicate is\n{}", predicate);
        final StandardJoinPointVisitor joinPointVisitor = new StandardJoinPointVisitor(predicate, JOIN_POINT_STRING_BUILDER);
        ajUniverse.visitJoinPoints(joinPointVisitor);
        assertThat("Matching join points?", joinPointVisitor.getSelectedJoinPoints(), Matchers.containsInAnyOrder(expectedJoinPoints));
    }

    protected final void assertBuildJoinPointSelector(List<String> expectedJoinPoints) {
        assertBuildJoinPointSelector(toArray(expectedJoinPoints));
    }

    protected final void assertEndSpecificJoinPointBuilder(String methodName, JoinPointSelector joinPointSelector) {
        assertThat(methodName + " returns same created JoinPointSelector?",
                joinPointSelector, sameInstance((JoinPointSelector)this.joinPointSelectorBuilder));
    }

    private static final AjJoinPointStringBuilder JOIN_POINT_STRING_BUILDER = new AjJoinPointStringBuilderBase() {
        private String value;

        @Override
        public AjJoinPointStringBuilder setMethod(Method method) {
            value = method.toString();
            return this;
        }

        @Override
        public String build() {
            return value;
        }
    };

    protected static List<String> toList(String... expectedJoinPoints) {
        return Arrays.asList(expectedJoinPoints);
    }

    protected static String[] toArray(Collection<String> stringCollection) {
        return stringCollection.toArray(new String[stringCollection.size()]);
    }

    protected interface SelectorFragment<T extends SelectorBuilder> {
        T select(T selectBuilder);
    }
}
