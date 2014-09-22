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
package org.failearly.ajunit.aspect.annotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.failearly.ajunit.AjUnitAspectBase;

/**
 * AjUnitAnnotationAspect is the base aspect for all annotation based aspects - a class using {@link @AspectJ} annotation.
 * <br/></br>The actually aspect must
 * <ul>
 *     <li>implement pointcut definition {@link #pointcutUnderTest()}</li>
 *     <li>and define an (before, after or around) advice which calls
 *          {@link org.failearly.ajunit.AjUnitAspectBase#applyJoinPoint(org.aspectj.lang.JoinPoint.StaticPart, org.aspectj.lang.JoinPoint.StaticPart)}</li>
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
 * @see org.failearly.ajunit.aspect.annotation.AjUnitBeforeAnnotationAspect
 */
@SuppressWarnings("EmptyMethod")
@Aspect
public abstract class AjUnitAnnotationAspect extends AjUnitAspectBase {

    /**
     * The pointcut definition to be tested by ajUnit.
     */
    @Pointcut("")
    protected abstract void pointcutUnderTest();

    /**
     * Selects all join points which will be executed below {@link org.failearly.ajunit.internal.runner.AjUnitTestRunner#doExecute()}. The implementation of
     * {@link org.failearly.ajunit.AjUnitTest#execute()} will be executed.
     *
     * @see org.failearly.ajunit.AjUnitTest#execute()
     */
    @Pointcut("cflowbelow(execution(void org.failearly.ajunit.AjUnitTest.execute()))")
    private void cflowbelowExecutingAjUnitTestExecute() {}

    /**
     * Exclude all join points within ajUnit packages.
     */
    @Pointcut("! within(org.failearly.ajunit..*)")
    private void excludeAjUnit() {}

    /**
     * The actually pointcut definition to test.
     */
    @Pointcut("pointcutUnderTest() && cflowbelowExecutingAjUnitTestExecute() && excludeAjUnit()")
    protected void pointcutDefinition() {}


    /**
     * This pointcut definition does not select anything, has just a purpose as place holder.
     */
    @Pointcut("")
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
