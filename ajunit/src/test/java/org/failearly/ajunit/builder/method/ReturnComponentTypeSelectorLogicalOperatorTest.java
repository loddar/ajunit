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

import org.failearly.ajunit.builder.AbstractJoinPointSelectorTest;
import org.failearly.ajunit.builder.ReturnComponentTypeSelector;
import org.failearly.ajunit.builder.TestSubject5;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Tests for {@link org.failearly.ajunit.builder.ReturnComponentTypeSelector}.
 *
 * @see org.failearly.ajunit.builder.ReturnTypeSelector#byComponentType()
 */
@RunWith(Parameterized.class)
public abstract class ReturnComponentTypeSelectorLogicalOperatorTest extends AbstractJoinPointSelectorTest<ReturnComponentTypeSelector> {

    @SuppressWarnings("notused")
    @Parameterized.Parameter(0)
    public String testName;

    @Parameterized.Parameter(1)
    public List<String> expectedJoinPoints;

    @Parameterized.Parameter(2)
    public SubSelect<ReturnComponentTypeSelector> subSelect;

    protected ReturnComponentTypeSelectorLogicalOperatorTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject5.class);
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
                "public int[] org.failearly.ajunit.builder.TestSubject5.getIntArray()",
                "public int[][] org.failearly.ajunit.builder.TestSubject5.getIntMatrix()"
        );
        norTest(tests, "nor", expectedJoinPoints, new SubSelect<ReturnComponentTypeSelector>() {
            @Override
            public ReturnComponentTypeSelector subSelect(ReturnComponentTypeSelector selectBuilder) {
                return selectBuilder.nor()
                        .byExtending(Object.class)
                        .end();
            }
        });
       norTest(tests, "neitherNor", expectedJoinPoints, new SubSelect<ReturnComponentTypeSelector>() {
            @Override
            public ReturnComponentTypeSelector subSelect(ReturnComponentTypeSelector selectBuilder) {
                return selectBuilder.neitherNor()
                        .byExtending(Object.class)
                        .end();
            }
        });
       norTest(tests, "noneOf", expectedJoinPoints, new SubSelect<ReturnComponentTypeSelector>() {
            @Override
            public ReturnComponentTypeSelector subSelect(ReturnComponentTypeSelector selectBuilder) {
                return selectBuilder.noneOf()
                        .byExtending(Object.class)
                        .end();
            }
        });
       norTest(tests, "complement", expectedJoinPoints, new SubSelect<ReturnComponentTypeSelector>() {
            @Override
            public ReturnComponentTypeSelector subSelect(ReturnComponentTypeSelector selectBuilder) {
                return selectBuilder.complement()
                        .byExtending(Object.class)
                        .end();
            }
        });
    }

    private static void defineComplexAndTests(List<Object> tests) {
        final List<String> expectedJoinPoints = toList(
                "public java.util.LinkedList[][][] org.failearly.ajunit.builder.TestSubject5.getLinkedList()"
        );
        andTest(tests, "and", expectedJoinPoints, new SubSelect<ReturnComponentTypeSelector>() {
            @Override
            public ReturnComponentTypeSelector subSelect(ReturnComponentTypeSelector selectBuilder) {
                return selectBuilder.and()
                            .byExtending(Object.class)
                            .byCollection()
                        .end();
            }
        });
       andTest(tests, "allOf", expectedJoinPoints, new SubSelect<ReturnComponentTypeSelector>() {
            @Override
            public ReturnComponentTypeSelector subSelect(ReturnComponentTypeSelector selectBuilder) {
                return selectBuilder.allOf()
                            .byExtending(Object.class)
                            .byCollection()
                        .end();
            }
        });
       andTest(tests, "intersect", expectedJoinPoints, new SubSelect<ReturnComponentTypeSelector>() {
            @Override
            public ReturnComponentTypeSelector subSelect(ReturnComponentTypeSelector selectBuilder) {
                return selectBuilder.intersect()
                            .byExtending(Object.class)
                            .byCollection()
                        .end();
            }
        });

    }

    private static void defineComplexOrTests(List<Object> tests) {
        final List<String> expectedJoinPoints = toList(
                "public java.lang.String[] org.failearly.ajunit.builder.TestSubject5.getStringArray()",
                "public java.lang.String[][] org.failearly.ajunit.builder.TestSubject5.getStringMatrix()",
                "public int[] org.failearly.ajunit.builder.TestSubject5.getIntArray()",
                "public int[][] org.failearly.ajunit.builder.TestSubject5.getIntMatrix()",
                "public org.failearly.ajunit.builder.AnyAnnotation[] org.failearly.ajunit.builder.TestSubject5.getAnnotationVector()",
                "public org.failearly.ajunit.builder.AnyEnum[] org.failearly.ajunit.builder.TestSubject5.getEnumVector()",
                "public org.failearly.ajunit.builder.AnyInterface[] org.failearly.ajunit.builder.TestSubject5.getAnyInterface()",
                "public java.lang.Integer[][] org.failearly.ajunit.builder.TestSubject5.getIntegerMatrix()",
                "public java.util.HashMap[][][] org.failearly.ajunit.builder.TestSubject5.getHashMap()",
                "public java.util.LinkedList[][][] org.failearly.ajunit.builder.TestSubject5.getLinkedList()"
        );
        orTest(tests, "or", expectedJoinPoints, new SubSelect<ReturnComponentTypeSelector>() {
            @Override
            public ReturnComponentTypeSelector subSelect(ReturnComponentTypeSelector selectBuilder) {
                return selectBuilder.or()
                        .byExtending(Object.class)
                        .byPrimitive()
                        .end();
            }
        });
        orTest(tests, "anyOf", expectedJoinPoints, new SubSelect<ReturnComponentTypeSelector>() {
            @Override
            public ReturnComponentTypeSelector subSelect(ReturnComponentTypeSelector selectBuilder) {
                return selectBuilder.anyOf()
                        .byExtending(Object.class)
                        .byPrimitive()
                        .end();
            }
        });
        orTest(tests, "union", expectedJoinPoints, new SubSelect<ReturnComponentTypeSelector>() {
            @Override
            public ReturnComponentTypeSelector subSelect(ReturnComponentTypeSelector selectBuilder) {
                return selectBuilder.union()
                        .byExtending(Object.class)
                        .byPrimitive()
                        .end();
            }
        });
    }

    private static void orTest(List<Object> tests, String logicalOperatorName, List<String> expectedJoinPoints, SubSelect<ReturnComponentTypeSelector> subSelect) {
        tests.add(new Object[]{"Complex OR (" + logicalOperatorName + ")", expectedJoinPoints, subSelect});
    }

    private static void andTest(List<Object> tests, String logicalOperatorName, List<String> expectedJoinPoints, SubSelect<ReturnComponentTypeSelector> subSelect) {
        tests.add(new Object[]{"Complex AND (" + logicalOperatorName + ")", expectedJoinPoints, subSelect});
    }

    private static void norTest(List<Object> tests, String logicalOperatorName, List<String> expectedJoinPoints, SubSelect<ReturnComponentTypeSelector> subSelect) {
        tests.add(new Object[]{"Complex NOR (" + logicalOperatorName + ")", expectedJoinPoints, subSelect});
    }

    @Test
    public void logicalOperatorTest() throws Exception {
        assertLogicalExpression(this.subSelect, this.expectedJoinPoints);
    }

    private void assertLogicalExpression(SubSelect<ReturnComponentTypeSelector> subSelect, List<String> expectedJoinPoints) {
        subSelect.subSelect(
                selectorBuilder
        ).endComponentType();

        assertBuildJoinPointSelector(expectedJoinPoints);
    }
}
