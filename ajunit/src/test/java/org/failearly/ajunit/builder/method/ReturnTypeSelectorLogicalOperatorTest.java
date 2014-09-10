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
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Tests for the logical operators on {@link ReturnTypeSelector} and their aliases.
 *
 * @see MethodJoinPointSelector#returnType(org.failearly.ajunit.builder.LogicalOperator)
 * @see ReturnTypeSelector#or()
 * @see ReturnTypeSelector#and()
 * @see ReturnTypeSelector#nor()
 * @see org.failearly.ajunit.builder.LogicalOperator
 */
@RunWith(Parameterized.class)
public abstract class ReturnTypeSelectorLogicalOperatorTest extends AbstractJoinPointSelectorTest<MethodJoinPointSelector> {


    @SuppressWarnings("notused")
    @Parameterized.Parameter(0)
    public String testName;

    @Parameterized.Parameter(1)
    public LogicalOperator logicalOperator;

    @Parameterized.Parameter(2)
    public List<String> expectedJoinPoints;

    @Parameterized.Parameter(3)
    public SelectorFragment<ReturnTypeSelector> subSelect;

    protected ReturnTypeSelectorLogicalOperatorTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject4.class);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection logicalOperatorTests() {
        final List<Object> tests = new LinkedList<>();
        addOrTest(tests, LogicalOperator.OR);
        addAndTest(tests, LogicalOperator.AND);
        addNorTest(tests, LogicalOperator.NOR);

        defineComplexOrTests(tests);
        defineComplexAndTests(tests);
        defineComplexNorTests(tests);
        return tests;
    }

    private static void defineComplexNorTests(List<Object> tests) {
        final List<String> expectedJoinPoints = toList(
                "public org.failearly.ajunit.builder.TestSubject4 org.failearly.ajunit.builder.TestSubject4.getTestSubject4()",
                "public org.failearly.ajunit.builder.TestSubject1 org.failearly.ajunit.builder.TestSubject4.getTestSubject1()",
                "public java.util.Map org.failearly.ajunit.builder.TestSubject4.getMap()",
                "public java.util.Collection org.failearly.ajunit.builder.TestSubject4.getCollection()",
                "protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException"
        );
        addComplexExpressionNorTest(tests, "nor", expectedJoinPoints, new SelectorFragment<ReturnTypeSelector>() {
            @Override
            public ReturnTypeSelector select(ReturnTypeSelector selectBuilder) {
                return selectBuilder
                        .nor()
                            .byImplementingAnyOf(Serializable.class)
                            .byClass(void.class)
                            .byPrimitive()
                            .byPrimitiveWrapperType()
                        .end();
            }
        });
        addComplexExpressionNorTest(tests, "noneOf", expectedJoinPoints, new SelectorFragment<ReturnTypeSelector>() {
            @Override
            public ReturnTypeSelector select(ReturnTypeSelector selectBuilder) {
                return selectBuilder
                        .noneOf()
                            .byImplementingAnyOf(Serializable.class)
                            .byClass(void.class)
                            .byPrimitive()
                            .byPrimitiveWrapperType()
                        .end();
            }
        });
        addComplexExpressionNorTest(tests, "neitherNor", expectedJoinPoints, new SelectorFragment<ReturnTypeSelector>() {
            @Override
            public ReturnTypeSelector select(ReturnTypeSelector selectBuilder) {
                return selectBuilder
                        .neitherNor()
                            .byImplementingAnyOf(Serializable.class)
                            .byClass(void.class)
                            .byPrimitive()
                            .byPrimitiveWrapperType()
                        .end();
            }
        });
        addComplexExpressionNorTest(tests, "complement", expectedJoinPoints, new SelectorFragment<ReturnTypeSelector>() {
            @Override
            public ReturnTypeSelector select(ReturnTypeSelector selectBuilder) {
                return selectBuilder
                        .complement()
                            .byImplementingAnyOf(Serializable.class)
                            .byClass(void.class)
                            .byPrimitive()
                            .byPrimitiveWrapperType()
                        .end();
            }
        });
    }

    private static void defineComplexAndTests(List<Object> tests) {
        final List<String> expectedJoinPoints = toList(
                "public org.failearly.ajunit.builder.LogicalOperator org.failearly.ajunit.builder.TestSubject4.getEnum()",
                "public org.failearly.ajunit.builder.TestSubject3 org.failearly.ajunit.builder.TestSubject4.getTestSubject3()",
                "public org.failearly.ajunit.builder.TestSubject2 org.failearly.ajunit.builder.TestSubject4.getTestSubject2()"
        );
        addComplexExpressionAndTest(tests, "and", expectedJoinPoints, new SelectorFragment<ReturnTypeSelector>() {
            @Override
            public ReturnTypeSelector select(ReturnTypeSelector selectBuilder) {
                return selectBuilder
                        .byPackageName("org.failearly.ajunit.builder", StringMatcher.EQUALS)
                        .and()
                            .byImplementingAnyOf(Serializable.class)
                            .byNotExtending(Number.class)
                            .byImplementingNoneOf(Collection.class, Map.class)
                        .end();

            }
        });
        addComplexExpressionAndTest(tests, "allOf", expectedJoinPoints, new SelectorFragment<ReturnTypeSelector>() {
            @Override
            public ReturnTypeSelector select(ReturnTypeSelector selectBuilder) {
                return selectBuilder
                        .byPackageName("org.failearly.ajunit.builder", StringMatcher.EQUALS)
                        .allOf()
                            .byImplementingAnyOf(Serializable.class)
                            .byNotExtending(Number.class)
                            .byImplementingNoneOf(Collection.class, Map.class)
                        .end();

            }
        });
        addComplexExpressionAndTest(tests, "intersect", expectedJoinPoints, new SelectorFragment<ReturnTypeSelector>() {
            @Override
            public ReturnTypeSelector select(ReturnTypeSelector selectBuilder) {
                return selectBuilder
                        .byPackageName("org.failearly.ajunit.builder", StringMatcher.EQUALS)
                        .intersect()
                            .byImplementingAnyOf(Serializable.class)
                            .byNotExtending(Number.class)
                            .byImplementingNoneOf(Collection.class, Map.class)
                        .end();

            }
        });
    }

    private static void defineComplexOrTests(List<Object> tests) {
        final List<String> expectedJoinPoints = toList(
                "public java.util.HashMap org.failearly.ajunit.builder.TestSubject4.getHashMap()",
                "public java.util.Map org.failearly.ajunit.builder.TestSubject4.getMap()",
                "public java.util.Hashtable org.failearly.ajunit.builder.TestSubject4.getHashTable()",
                "public org.failearly.ajunit.builder.TestSubject3 org.failearly.ajunit.builder.TestSubject4.getTestSubject3()",
                "public org.failearly.ajunit.builder.TestSubject1 org.failearly.ajunit.builder.TestSubject4.getTestSubject1()"
        );
        addComplexExpressionOrTest(tests, "or", expectedJoinPoints, new SelectorFragment<ReturnTypeSelector>() {
            @Override
            public ReturnTypeSelector select(ReturnTypeSelector selectBuilder) {
                return selectBuilder
                        .or()
                            .byImplementingAnyOf(AnyInterface.class)
                            .byMap()
                        .end();
            }
        });
        addComplexExpressionOrTest(tests, "anyOf", expectedJoinPoints, new SelectorFragment<ReturnTypeSelector>() {
            @Override
            public ReturnTypeSelector select(ReturnTypeSelector selectBuilder) {
                return selectBuilder
                        .anyOf()
                            .byImplementingAnyOf(AnyInterface.class)
                            .byMap()
                        .end();
            }
        });
        addComplexExpressionOrTest(tests, "union", expectedJoinPoints, new SelectorFragment<ReturnTypeSelector>() {
            @Override
            public ReturnTypeSelector select(ReturnTypeSelector selectBuilder) {
                return selectBuilder
                        .union()
                            .byImplementingAnyOf(AnyInterface.class)
                            .byMap()
                        .end();
            }
        });
    }

    private static void addComplexExpressionOrTest(List<Object> tests, String logicalOperatorName, List<String> expectedJoinPoints, SelectorFragment<ReturnTypeSelector> subSelect) {
        tests.add(new Object[]{"Complex OR (" + logicalOperatorName + ")", LogicalOperator.AND, expectedJoinPoints, subSelect});
    }

    private static void addComplexExpressionAndTest(List<Object> tests, String logicalOperatorName, List<String> expectedJoinPoints, SelectorFragment<ReturnTypeSelector> subSelect) {
        tests.add(new Object[]{"Complex AND (" + logicalOperatorName + ")", LogicalOperator.AND, expectedJoinPoints, subSelect});
    }

    private static void addComplexExpressionNorTest(List<Object> tests, String logicalOperatorName, List<String> expectedJoinPoints, SelectorFragment<ReturnTypeSelector> subSelect) {
        tests.add(new Object[]{"Complex NOR (" + logicalOperatorName + ")", LogicalOperator.AND, expectedJoinPoints, subSelect});
    }

    private static void addOrTest(List<Object> tests, LogicalOperator logicalOperator) {
        final List<String> expectedJoinPoints = toList(
                "public void org.failearly.ajunit.builder.TestSubject4.setAnyValue(int)",
                "public int org.failearly.ajunit.builder.TestSubject4.getInt()",
                // java.lang.Object
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "public final native void java.lang.Object.notify()",
                "public final native void java.lang.Object.notifyAll()",
                "protected void java.lang.Object.finalize() throws java.lang.Throwable",
                "private static native void java.lang.Object.registerNatives()",
                "public native int java.lang.Object.hashCode()"
        );
        tests.add(new Object[]{logicalOperator.toString(), logicalOperator, expectedJoinPoints, new SelectorFragment<ReturnTypeSelector>() {
            @Override
            public ReturnTypeSelector select(ReturnTypeSelector selectBuilder) {
                return selectBuilder
                        .byClass(void.class)
                        .byClass(int.class);
            }
        }});
    }

    private static void addAndTest(List<Object> tests, LogicalOperator logicalOperator) {
        final List<String> expectedJoinPoints = toList(
                "public org.failearly.ajunit.builder.TestSubject3 org.failearly.ajunit.builder.TestSubject4.getTestSubject3()",
                "public org.failearly.ajunit.builder.TestSubject2 org.failearly.ajunit.builder.TestSubject4.getTestSubject2()",
                "public org.failearly.ajunit.builder.LogicalOperator org.failearly.ajunit.builder.TestSubject4.getEnum()"
        );
        tests.add(new Object[]{logicalOperator.toString(), logicalOperator, expectedJoinPoints, new SelectorFragment<ReturnTypeSelector>() {
            @Override
            public ReturnTypeSelector select(ReturnTypeSelector selectBuilder) {
                return selectBuilder
                        .byImplementingAnyOf(Serializable.class)
                        .byPackageName("org.failearly.ajunit.builder", StringMatcher.EQUALS);
            }
        }});
    }

    private static void addNorTest(List<Object> tests, LogicalOperator logicalOperator) {
        final List<String> expectedJoinPoints = toList(
                "public boolean org.failearly.ajunit.builder.TestSubject4.getBoolean()",
                "public byte org.failearly.ajunit.builder.TestSubject4.getByte()",
                "public short org.failearly.ajunit.builder.TestSubject4.getShort()",
                "public int org.failearly.ajunit.builder.TestSubject4.getInt()",
                "public long org.failearly.ajunit.builder.TestSubject4.getLong()",
                "public float org.failearly.ajunit.builder.TestSubject4.getFloat()",
                "public double org.failearly.ajunit.builder.TestSubject4.getDouble()",
                "public org.failearly.ajunit.builder.TestSubject4 org.failearly.ajunit.builder.TestSubject4.getTestSubject4()",
                "public org.failearly.ajunit.builder.TestSubject1 org.failearly.ajunit.builder.TestSubject4.getTestSubject1()",
                "public java.util.Map org.failearly.ajunit.builder.TestSubject4.getMap()",
                "public java.util.Collection org.failearly.ajunit.builder.TestSubject4.getCollection()",
                // java.lang.Object
                "public boolean java.lang.Object.equals(java.lang.Object)",
                "public native int java.lang.Object.hashCode()",
                "protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException"
        );
        tests.add(new Object[]{logicalOperator.toString(), logicalOperator, expectedJoinPoints, new SelectorFragment<ReturnTypeSelector>() {
            @Override
            public ReturnTypeSelector select(ReturnTypeSelector selectBuilder) {
                return selectBuilder
                        .byImplementingAnyOf(Serializable.class)
                        .byClass(void.class);
            }
        }});
    }

    @Test
    public void logicalOperatorTest() throws Exception {
        assertLogicalExpression(this.logicalOperator, this.subSelect, expectedJoinPoints);
    }

    private void assertLogicalExpression(LogicalOperator logicalOperator, SelectorFragment<ReturnTypeSelector> subSelect, List<String> expectedJoinPoints) {
        subSelect.select(
                selectorBuilder.returnType(logicalOperator)
        ).endReturnType();

        assertBuildJoinPointSelector(expectedJoinPoints);
    }

}
