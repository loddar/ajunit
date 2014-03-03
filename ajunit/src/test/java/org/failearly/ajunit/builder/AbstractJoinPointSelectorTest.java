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
import org.failearly.ajunit.internal.universe.AjJoinPointStringBuilder;
import org.failearly.ajunit.internal.universe.AjJoinPointStringBuilderBase;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.failearly.ajunit.internal.universe.AjUniverse;
import org.failearly.ajunit.internal.universe.impl.AjUniversesHolder;
import org.failearly.ajunit.internal.util.AjUnitUtils;
import org.failearly.ajunit.test.helper.StandardJoinPointVisitor;
import org.junit.After;
import org.junit.Before;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

/**
 * Base class for all {@link org.failearly.ajunit.builder.JoinPointSelector} Tests.
 */
public abstract class AbstractJoinPointSelectorTest<T extends SelectorBuilder> {
    private static final String UNIVERSE_NAME = "AbstractJoinPointSelectorTest$Universe";
    private AjUniverse ajUniverse;
    private final AjJoinPointType expectedJoinPointType;
    private Set<AjJoinPointType> joinPointTypes;

    protected T selectorBuilder;
    private JoinPointSelectorBuilderImpl joinPointSelectorBuilder;
    private final List<Class<?>> testFixtureClasses;

    protected AbstractJoinPointSelectorTest(AjJoinPointType expectedJoinPointType, Class<?>... testFixtureClasses) {
        this(expectedJoinPointType,AjUnitUtils.toClassList(testFixtureClasses));
    }

    private AbstractJoinPointSelectorTest(AjJoinPointType expectedJoinPointType, List<Class<?>> testFixtureClasses) {
        this.expectedJoinPointType = expectedJoinPointType;
        this.testFixtureClasses = testFixtureClasses;
    }

    protected static List<Class<?>> toClassList(Class<?> class1, Class<?>... classes) {
        final List<Class<?>> classList=new ArrayList<>(classes.length+1);
        classList.add(class1);
        classList.addAll(AjUnitUtils.toClassList(classes));
        return classList;
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
        joinPointSelectorBuilder = new JoinPointSelectorBuilderImpl(joinPointTypes);
        selectorBuilder=createSelectorBuilderUnderTest(this.joinPointSelectorBuilder);
    }

    protected abstract T createSelectorBuilderUnderTest(JoinPointSelector joinPointSelector);

    protected final void assertJoinPointType() {
        assertThat("Join Point Type?", this.joinPointTypes, containsInAnyOrder(this.expectedJoinPointType));
    }

    protected final void assertBuildJoinPointSelector(String... expectedJoinPoints) {
        final StandardJoinPointVisitor joinPointVisitor = new StandardJoinPointVisitor(this.joinPointSelectorBuilder.build(), JOIN_POINT_STRING_BUILDER);
        ajUniverse.visitJoinPoints(joinPointVisitor);
        assertThat("Matching join points?", joinPointVisitor.getSelectedJoinPoints(), containsInAnyOrder(expectedJoinPoints));
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
}
