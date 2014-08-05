/*
 * ajUnit - Unit Testing AspectJ.
 *
 * Copyright (C) 2013-2014 marko (http://fail-early.com/contact)
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

import org.failearly.ajunit.builder.AbstractJoinPointSelectorTest;
import org.failearly.ajunit.builder.ListLogicalOperator;
import org.failearly.ajunit.builder.StringMatcherType;
import org.failearly.ajunit.builder.TestSubject6;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Tests for the logical operators on {@link MethodExceptionTypeSelector} and their aliases.
 *
 * @see MethodJoinPointSelector#byReturnType(org.failearly.ajunit.builder.LogicalOperator)
 * @see MethodExceptionTypeSelector#or()
 * @see MethodExceptionTypeSelector#and()
 * @see MethodExceptionTypeSelector#nor()
 * @see org.failearly.ajunit.builder.LogicalOperator
 */
@RunWith(Parameterized.class)
public abstract class MethodExceptionTypeSelectorLogicalOperatorTest extends AbstractJoinPointSelectorTest<MethodJoinPointSelector> {


    @SuppressWarnings("notused")
    @Parameterized.Parameter(0)
    public String testName;

    @Parameterized.Parameter(1)
    public List<String> expectedJoinPoints;

    @Parameterized.Parameter(2)
    public SelectorFragment<MethodExceptionTypeSelector> subSelect;

    protected MethodExceptionTypeSelectorLogicalOperatorTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject6.class);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection logicalOperatorTests() {
        final List<Object> tests = new LinkedList<>();
        defineComplexOrTests(tests);
        defineComplexAndTests(tests);
        defineComplexNorTests(tests);
        return tests;
    }

    private static void defineComplexNorTests(List<Object> tests) {
        final List<String> expectedJoinPoints = toList(
                "public void org.failearly.ajunit.builder.TestSubject6.method1() throws java.lang.Throwable",
                "public void org.failearly.ajunit.builder.TestSubject6.method3() throws java.lang.RuntimeException",
                "public void org.failearly.ajunit.builder.TestSubject6.method4() throws org.failearly.ajunit.builder.AnyException",
                "public void org.failearly.ajunit.builder.TestSubject6.method5() throws org.failearly.ajunit.builder.AnyException,java.sql.SQLException",
                "protected void java.lang.Object.finalize() throws java.lang.Throwable"
        );
        addComplexExpressionNorTest(tests, "nor", expectedJoinPoints, new SelectorFragment<MethodExceptionTypeSelector>() {
            @Override
            public MethodExceptionTypeSelector select(MethodExceptionTypeSelector selectBuilder) {
                return selectBuilder
                        .nor()
                            .byCheckedException()
                            .byError()
                        .end();
            }
        });
        addComplexExpressionNorTest(tests, "noneOf", expectedJoinPoints, new SelectorFragment<MethodExceptionTypeSelector>() {
            @Override
            public MethodExceptionTypeSelector select(MethodExceptionTypeSelector selectBuilder) {
                return selectBuilder
                        .noneOf()
                            .byCheckedException()
                            .byError()
                        .end();
            }
        });
        addComplexExpressionNorTest(tests, "neitherNor", expectedJoinPoints, new SelectorFragment<MethodExceptionTypeSelector>() {
            @Override
            public MethodExceptionTypeSelector select(MethodExceptionTypeSelector selectBuilder) {
                return selectBuilder
                        .neitherNor()
                            .byCheckedException()
                            .byError()
                        .end();
            }
        });
        addComplexExpressionNorTest(tests, "complement", expectedJoinPoints, new SelectorFragment<MethodExceptionTypeSelector>() {
            @Override
            public MethodExceptionTypeSelector select(MethodExceptionTypeSelector selectBuilder) {
                return selectBuilder
                        .complement()
                            .byCheckedException()
                            .byError()
                        .end();
            }
        });
    }

    private static void defineComplexAndTests(List<Object> tests) {
        final List<String> expectedJoinPoints = toList(
                "public void org.failearly.ajunit.builder.TestSubject6.method5() throws org.failearly.ajunit.builder.AnyException,java.sql.SQLException"
        );
        addComplexExpressionAndTest(tests, "and", expectedJoinPoints, new SelectorFragment<MethodExceptionTypeSelector>() {
            @Override
            public MethodExceptionTypeSelector select(MethodExceptionTypeSelector selectBuilder) {
                return selectBuilder
                        .and()
                            .byCheckedException()
                            .byPackageName("java.sql", StringMatcherType.STARTS_WITH)
                        .end();

            }
        });
        addComplexExpressionAndTest(tests, "allOf", expectedJoinPoints, new SelectorFragment<MethodExceptionTypeSelector>() {
            @Override
            public MethodExceptionTypeSelector select(MethodExceptionTypeSelector selectBuilder) {
                return selectBuilder
                        .allOf()
                            .byCheckedException()
                            .byPackageName("java.sql", StringMatcherType.STARTS_WITH)
                        .end();

            }
        });
        addComplexExpressionAndTest(tests, "intersect", expectedJoinPoints, new SelectorFragment<MethodExceptionTypeSelector>() {
            @Override
            public MethodExceptionTypeSelector select(MethodExceptionTypeSelector selectBuilder) {
                return selectBuilder
                        .intersect()
                            .byCheckedException()
                            .byPackageName("java.sql", StringMatcherType.STARTS_WITH)
                        .end();

            }
        });
    }

    private static void defineComplexOrTests(List<Object> tests) {
        final List<String> expectedJoinPoints = toList(
                "public void org.failearly.ajunit.builder.TestSubject6.method5() throws org.failearly.ajunit.builder.AnyException,java.sql.SQLException",
                "public void org.failearly.ajunit.builder.TestSubject6.method4() throws org.failearly.ajunit.builder.AnyException",
                "public void org.failearly.ajunit.builder.TestSubject6.method3() throws java.lang.RuntimeException",
                "public void org.failearly.ajunit.builder.TestSubject6.method0() throws java.lang.Error"
        );
        addComplexExpressionOrTest(tests, "or", expectedJoinPoints, new SelectorFragment<MethodExceptionTypeSelector>() {
            @Override
            public MethodExceptionTypeSelector select(MethodExceptionTypeSelector selectBuilder) {
                return selectBuilder
                        .or()
                            .byRuntimeException()
                            .byError()
                        .end();
            }
        });
        addComplexExpressionOrTest(tests, "anyOf", expectedJoinPoints, new SelectorFragment<MethodExceptionTypeSelector>() {
            @Override
            public MethodExceptionTypeSelector select(MethodExceptionTypeSelector selectBuilder) {
                return selectBuilder
                        .anyOf()
                            .byRuntimeException()
                            .byError()
                        .end();
            }
        });
        addComplexExpressionOrTest(tests, "union", expectedJoinPoints, new SelectorFragment<MethodExceptionTypeSelector>() {
            @Override
            public MethodExceptionTypeSelector select(MethodExceptionTypeSelector selectBuilder) {
                return selectBuilder
                        .union()
                            .byRuntimeException()
                            .byError()
                        .end();
            }
        });
    }

    private static void addComplexExpressionOrTest(List<Object> tests, String logicalOperatorName, List<String> expectedJoinPoints, SelectorFragment<MethodExceptionTypeSelector> subSelect) {
        tests.add(new Object[]{"Complex OR (" + logicalOperatorName + ")", expectedJoinPoints, subSelect});
    }

    private static void addComplexExpressionAndTest(List<Object> tests, String logicalOperatorName, List<String> expectedJoinPoints, SelectorFragment<MethodExceptionTypeSelector> subSelect) {
        tests.add(new Object[]{"Complex AND (" + logicalOperatorName + ")", expectedJoinPoints, subSelect});
    }

    private static void addComplexExpressionNorTest(List<Object> tests, String logicalOperatorName, List<String> expectedJoinPoints, SelectorFragment<MethodExceptionTypeSelector> subSelect) {
        tests.add(new Object[]{"Complex NOR (" + logicalOperatorName + ")", expectedJoinPoints, subSelect});
    }

    @Test
    public void logicalOperatorTest() throws Exception {
        assertLogicalExpression(this.subSelect, expectedJoinPoints);
    }

    private void assertLogicalExpression(SelectorFragment<MethodExceptionTypeSelector> subSelect, List<String> expectedJoinPoints) {
        subSelect.select(
                selectorBuilder.byExceptionTypes(ListLogicalOperator.ANY_OF)
        ).endExceptionTypes();

        assertBuildJoinPointSelector(expectedJoinPoints);
    }

}
