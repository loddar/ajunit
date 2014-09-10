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
package org.failearly.ajunit.builder.method;

import org.failearly.ajunit.builder.*;
import org.failearly.ajunit.modifier.MethodModifier;

import java.lang.annotation.Annotation;

/**
 * MethodJoinPointSelector is the builder for method join point selector expression.
 * <br/><br/>
 * AspectJ pointcut definitions:
 * <code>
 * call(method signature)
 * execution(method signature)
 * </code>
 */
public interface MethodJoinPointSelector
        extends MemberSelector<MethodJoinPointSelector>, DeclaringClassSelector<MethodJoinPointSelector>, LogicalSelector<MethodJoinPointSelector> {

    /**
     * Select a method by method name (pattern).<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(* *.get*(..))</code></li>
     * <li><code>call(* *.set*(..))</code></li>
     * </ul>
     *
     * @param methodNamePattern the method name/name part or regular expression.
     * @param matcherType the matcher type
     * @return itself
     */
    MethodJoinPointSelector byName(String methodNamePattern, StringMatcher matcherType);

    /**
     * Select a method by any of {@link org.failearly.ajunit.modifier.MethodModifier}.<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(final * *.*(..))</code></li>
     * <li><code>call(static * *.*(..))</code></li>
     * </ul>
     * @param methodModifiers the supported method modifiers.
     * @return itself
     */
    MethodJoinPointSelector byAnyOfMethodModifiers(MethodModifier... methodModifiers);

    /**
     * Select a method by none of {@link org.failearly.ajunit.modifier.MethodModifier}.<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(!final * *.*(..))</code></li>
     * <li><code>call(!static * *.*(..))</code></li>
     * </ul>
     * @param methodModifiers the supported method modifiers.
     * @return itself
     */
    MethodJoinPointSelector byNoneOfMethodModifiers(MethodModifier... methodModifiers);

    /**
     * Select methods annotated by the {@code annotationClass}..<br/>
     * </br>
     * AspectJ pointcut definition examples: (TODO: examples)
     * <ul>
     * <li><code>execution(* *.*(..))</code></li>
     * <li><code>call(* *.*(..))</code></li>
     * </ul>
     * @param annotationClass an annotation class.
     * @return itself
     *
     * @see java.lang.reflect.Method#isAnnotationPresent(Class)
     */
    MethodJoinPointSelector byMethodAnnotation(Class<? extends Annotation> annotationClass);

    /**
     * The selector selects classes which has been annotated (depends on {@code logicalOperator}).
     * @param logicalOperator the logical operator to be used.
     * @param annotationClasses the annotation classes (at least one should be provided).
     * @return itself
     */
    @SuppressWarnings("all")
    MethodJoinPointSelector byMethodAnnotations(LogicalOperator logicalOperator, Class<? extends Annotation>... annotationClasses);

    /**
     * End the method execution/call expression(s) end return to join point selector builder. It's like ending a
     * <pre>(execution(expr1) && execution(expr2))</pre> or <pre>(call(expr1) && call(expr2))</pre>.
     *
     * @return the {@link JoinPointSelector}.
     * @see JoinPointSelector#methodExecute()
     * @see JoinPointSelector#methodCall()
     */
    JoinPointSelector endMethod();


    /**
     * Select methods by their return type.<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(void *.*(..))</code></li>
     * <li><code>call(java.util.List+ *.*())</code></li>
     * </ul>
     * @param logicalOperator the logical operator.
     * @return the new return type selector using the logical operator.
     *
     * @see java.lang.reflect.Method#getReturnType()
     */
    ReturnTypeSelector returnType(LogicalOperator logicalOperator);

    /**
     * Convenient selector builder for methods with return type <code>void</code>.
     * @return  itself
     *
     * @see ReturnTypeSelector#byVoid()
     */
    MethodJoinPointSelector byReturningVoid();

    /**
     * Convenient selector builder for methods with return type <code>returnType</code>.
     * @param returnType the expected return type.
     * @return  itself
     *
     * @see ReturnTypeSelector#byClass(Class)
     */
    MethodJoinPointSelector byReturning(Class<?> returnType);

    /**
     * Convenient selector builder for methods with any primitive return type (except {@code void}).
     * @return  itself
     * @see ReturnTypeSelector#byPrimitive()
     */
    MethodJoinPointSelector byReturningPrimitive();

    /**
     * Convenient selector builder for methods the return type implements {@link java.util.Collection} or {@link java.util.Map}.
     * @return  itself
     * @see ReturnTypeSelector#byCollection()
     */
    MethodJoinPointSelector byReturningCollection();

    /**
     * Convenient selector builder for methods the return type extends {@link java.lang.Enum}, thus <code>enum MyEnum</code>.
     * @return  itself
     * @see ReturnTypeSelector#byEnum()
     */
    MethodJoinPointSelector byReturningEnum();

    /**
     * Convenient selector builder for methods the return type is any array type.
     * Examples:<br/>
     * <ul>
     *     <li>{@code int[] getArray()}</li>
     *     <li>{@code int[][] getMatrix}</li>
     *     <li>{@code String[] getStringArray}</li>
     * </ul>
     * @return itself
     * @see ReturnTypeSelector#byArray()
     */
    MethodJoinPointSelector byReturningArray();

    /**
     * Select methods based by their declared exception types. The predefined inner logical operator is {@link MethodExceptionTypeSelector#or()}.<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(*.*(..) throws java.sql.SQLException)</code></li>
     * <li><code>call(*.*(..) throws !java.lang.Error+)</code></li>
     * </ul>
     *
     * @param listOperator the list logical operator is responsible, how the selectors of
     *                  {@link MethodExceptionTypeSelector} should be applied on the entire list.
     *
     * @return a method exception type selector.
     */
    MethodExceptionTypeSelector exceptionTypes(ListOperator listOperator);

    /**
     * Select methods based by their argument or parameter signature.<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(*.*())</code>: no arguments</li>
     * <li><code>execution(*.*(*,..))</code>: number of arguments &ge; 1 </li>
     * <li><code>execution(*.*(java.lang.String))</code>: using String as single argument</li>
     * <li><code>call(*.*(*)</code>: exactly one argument</li>
     * <li><code>call(*.*(..,MyClass,..)</code>: using MyClass argument</li>
     * </ul>
     *
     * @param logicalOperator  logical the logical operator.
     * @return a new method arguments selector.
     */
    MethodParametersSelector arguments(LogicalOperator logicalOperator);



}
