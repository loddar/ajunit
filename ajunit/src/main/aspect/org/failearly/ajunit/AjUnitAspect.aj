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

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * AjUnitAspect is the base aspect for all {@link @AspectJ} based aspects.
 * <br/></br>The actually aspect must
 * <ul>
 *     <li>implement pointcut definition {@link #pointcutUnderTest()} and ...</li>
 *     <li>annotated the aspect with {@link org.failearly.ajunit.AjUniverseName}.</li>
 * </ul>
 *
 * <br/></br>It contains also some predefined pointcut definitions, as starting points for defining your specific pointcut:
 * <ul>
 *     <li>{@link #notYetSpecified()} for none selecting any join points.</li>
 *     <li>{@link #allMethodExecution()} ()} for selecting all method {@code execution} join points.</li>
 *     <li>{@link #allMethodCall()} ()} for selecting all method {@code call} join points.</li>
 *     <li>{@link #allConstructorExecution()}} ()} for selecting all constructor {@code execution} join points.</li>
 *     <li>{@link #allConstructorCall()} ()} for selecting all constructor {@code call} join points.</li>
 * </ul>
 *
 * @see org.failearly.ajunit.AjUnitBeforeAspect
 */
@Aspect
public abstract class AjUnitAspect extends AjUnitAspectBase {

    /**
     * The pointcut definition to be tested by ajUnit.
     */
    @Pointcut("")
    protected abstract void pointcutUnderTest();

    /**
     * Selects all join points which will be executed within the implementation of {@link AjUnitTest#executeTestFixtures()}.
     */
    @Pointcut("cflowbelow(execution(protected void org.failearly.ajunit.AjUnitTest+.executeTestFixtures()))")
    private void applyBelowTestFixturesExecution() {}

    /**
     * Exclude all join points within ajUnit packages.
     */
    @Pointcut("! within(org.failearly.ajunit..*)")
    private void excludeAjUnit() {}

    /**
     * The actually pointcut definition to test.
     */
    @Pointcut("pointcutUnderTest() && applyBelowTestFixturesExecution() && excludeAjUnit()")
    protected void pointcutDefinition() {}


    /**
     * This pointcut definition does not select anything, has just a purpose as place holder.
     */
    @Pointcut("allMethodExecution() && allMethodCall()")
    protected void notYetSpecified() {}

    /**
     * Selects all method {@code execution} join points.
     */
    @Pointcut("execution(* *(..))")
    protected void allMethodExecution() {}

    /**
     * Selects all method {@code call} join points.
     */
    @Pointcut("call(* *(..))")
    protected void allMethodCall() {}

    /**
     * Selects all constructor {@code execution} join points.
     */
    @Pointcut("execution(*.new(..))")
    protected void allConstructorExecution() {}

    /**
     * Selects all constructor {@code call} join points.
     */
    @Pointcut("call(*.new(..))")
    protected void allConstructorCall() {}
}
