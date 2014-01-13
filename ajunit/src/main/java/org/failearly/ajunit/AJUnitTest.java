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
package org.failearly.ajunit;

import org.failearly.ajunit.builder.JoinPointSelectorBuilder;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorBuilderImpl;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.universe.AjUniverse;
import org.failearly.ajunit.internal.universe.impl.AjUniversesHolder;
import org.failearly.ajunit.internal.util.AjUnitUtils;
import org.failearly.ajunit.internal.util.ClassUtils;
import org.failearly.ajunit.internal.util.MessageBuilder;
import org.failearly.ajunit.internal.util.MessageBuilderUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * AjUnitTest the base class for all ajUnit test testFixtureClasses.
 */
public abstract class AjUnitTest {

    /**
     * Internal use only. Only for actually test class implementation.
     */
    protected final void doPointcutTest() {
        String universeName=null;
        try {
            // arrange / given
            universeName = resolveUniverseName();
            assertAssociatedAspect(universeName);
            final AjUniverse universe = setupUniverse(universeName);
            final Predicate joinPointSelector = createJoinPointSelector(universe);

            // act / when
            executeTestFixtures();

            // assert / then
            assertPointcutDefinition(universe, joinPointSelector);
        } finally {
            // Cleanup
            if( universeName!=null ) {
                AjUniversesHolder.dropUniverse(universeName);
            }
        }

    }

    private void assertPointcutDefinition(AjUniverse universe, final Predicate joinPointSelector) {
    }

    private void assertAssociatedAspect(String universeName) {
        final String aspectClassName = getAssociatedAspect();
        final Class<?> aspectClass=ClassUtils.loadClass(aspectClassName, false);
        doAssertAspectExtendsBaseAspectClass(aspectClass);
        doAssertAspectsUniverse(aspectClass, universeName);
    }

    private void doAssertAspectsUniverse(Class<?> aspectClass, String universeName) {
        final String aspectsUniverseName=AjUnitUtils.resolveUniverseName(aspectClass);
        if( ! universeName.equals(aspectsUniverseName) ) {
            doFail(
                    MessageBuilderUtils.message("Aspect").arg(aspectClass.getCanonicalName()).part("has wrong universe name:").arg(aspectsUniverseName).fullStop()
                            .line("Please use universe name:").arg(universeName).fullStop()
            );
        }
    }

    private void doAssertAspectExtendsBaseAspectClass(Class<?> aspectClass) {
        if( ! AjUnitAspectBase.class.isAssignableFrom(aspectClass) )  {
            doFail(MessageBuilderUtils.message("Test aspect")
                    .arg(aspectClass.getCanonicalName())
                    .part("does not extend AjUnitAspectBase!")
                    .line("Please extend your aspect from one of the provided base aspects:")
                        .subLine("AjUnitAspect or AjUnitClassicAspect")
                        .subLine("AjUnitBeforeAspect or AjUnitBeforeClassicAspect")
                        .subLine("AjUnitAfterAspect or AjUnitAfterClassicAspect")
                        .subLine("AjUnitAroundAspect or AjUnitAroundClassicAspect")
            );
        }
    }

    /**
     * Provide the associated aspect as full qualified class name.
     * <br/></br>
     * The associated aspect should comply with following conditions:
     * <ul>
     *     <li>The aspect must be inherit from {@code org.failearly.ajunit.AjUnitAspect} or one of
     *          the provided sub aspects.</li>
     *     <li>The aspect must be annotated with {@link org.failearly.ajunit.AjUniverseName}.</li>
     * </ul>
     *
     * @return the aspect's full qualified class name.
     */
    protected String getAssociatedAspect() {
        doFail(MessageBuilderUtils.message("No associated aspect defined.")
                .line("Create an aspect and ...")
                .line("override getAssociatedAspect() providing the full qualified class name of the aspect."));
        return null;
    }

    private AjUniverse setupUniverse(String universeName) {
        final AjUniverseSetupImpl ajUnitTestSetup = new AjUniverseSetupImpl();
        initializeTest(ajUnitTestSetup);

        assertUniverseSetup(ajUnitTestSetup);

        return AjUniversesHolder.createUniverseByClasses(universeName, ajUnitTestSetup.testFixtureClasses);
    }

    private String resolveUniverseName() {
        return AjUnitUtils.resolveUniverseName(this);
    }

    private void assertUniverseSetup(AjUniverseSetupImpl ajUnitTestSetup) {
        final List<Class<?>> testFixtureClasses = ajUnitTestSetup.testFixtureClasses;
        if( testFixtureClasses.isEmpty() ) {
            doFail(MessageBuilderUtils.message("Missing test fixture class(es).")
                        .line("Apply addTestFixtureClass(<class> or <class name>) for every test fixture class.")
                );
        }
    }

    /**
     * Adds the test fixture classes to the ajUnit universe.
     * @param ajUniverseSetup
     */
    protected void initializeTest(AjUniverseSetup ajUniverseSetup) {
        doFail(MessageBuilderUtils.message("Missing implementation of initializeTest(AjUniverseSetup)!"));
    }

    private void doFail(MessageBuilder message) {
        doFail(message.build());
    }

    /**
     * Internal use only. Only for actually test class implementation.
     */
    protected abstract void doFail(String message);

    private Predicate createJoinPointSelector(AjUniverse universe) {
        final JoinPointSelectorBuilderImpl joinPointBuilder=new JoinPointSelectorBuilderImpl();
        setupJoinPointSelector(joinPointBuilder);
        if( ! joinPointBuilder.anyPredicateDefined() ) {
            doFail(MessageBuilderUtils.message("Missing valid implementation of setupJoinPointSelector(JoinPointSelectorBuilder)."));
        }
        return joinPointBuilder.build();
    }

    /**
     * Setup the pointcut predicate.
     * @param joinPointSelectorBuilder
     */
    protected void setupJoinPointSelector(JoinPointSelectorBuilder joinPointSelectorBuilder) {
        doFail(MessageBuilderUtils.message("Missing setupJoinPointSelector.").line("Please override setupJoinPointSelector(JoinPointSelectorBuilder)"));
    }


    /**
     * Within this method the test fixture classes should be called, so that the aspect could be applied.
     */
    protected abstract void executeTestFixtures();

    private static class AjUniverseSetupImpl implements AjUniverseSetup {
        private final List<Class<?>> testFixtureClasses =new ArrayList<>();

        public AjUniverseSetupImpl() {
        }

        @Override
        public AjUniverseSetup addTestFixtureClass(Class<?> clazz) {
            testFixtureClasses.add(clazz);
            return this;
        }

        @Override
        public AjUniverseSetup addTestFixtureClass(String className) {
            testFixtureClasses.add(ClassUtils.loadClass(className, false));
            return this;
        }

    }
}
