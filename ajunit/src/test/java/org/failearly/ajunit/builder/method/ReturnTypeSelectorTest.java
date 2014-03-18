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

import java.io.Serializable;
import java.util.Map;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link org.failearly.ajunit.builder.MethodJoinPointSelector}.
 *
 * @see org.failearly.ajunit.builder.MethodJoinPointSelector#byReturnType(org.failearly.ajunit.builder.LogicalOperator)
 */
public abstract class ReturnTypeSelectorTest extends AbstractJoinPointSelectorTest<MethodJoinPointSelector> {

    protected ReturnTypeSelectorTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject4.class);
    }

    @Test
    public void endReturnType() throws Exception {
        // act / when
        final MethodJoinPointSelector instance = selectorBuilder.byReturnType(LogicalOperator.OR).endReturnType();

        // assert / then
        assertThat("endReturnType() returns correct selector builder?", instance, sameInstance(selectorBuilder));
    }

    @Test
    public void byReturningVoid() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                    .byClass(void.class)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject4
                "public void org.failearly.ajunit.builder.TestSubject4.setAnyValue(int)",
                // java.lang.Object
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "public final native void java.lang.Object.notify()",
                "public final native void java.lang.Object.notifyAll()",
                "protected void java.lang.Object.finalize() throws java.lang.Throwable",
                "private static native void java.lang.Object.registerNatives()"
        );
    }

    @Test
    public void byReturningClassName() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                    .byClassName("TestSub", StringMatcherType.STARTS_WITH)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject4
                "public org.failearly.ajunit.builder.TestSubject4 org.failearly.ajunit.builder.TestSubject4.getTestSubject4()",
                "public org.failearly.ajunit.builder.TestSubject3 org.failearly.ajunit.builder.TestSubject4.getTestSubject3()",
                "public org.failearly.ajunit.builder.TestSubject2 org.failearly.ajunit.builder.TestSubject4.getTestSubject2()",
                "public org.failearly.ajunit.builder.TestSubject1 org.failearly.ajunit.builder.TestSubject4.getTestSubject1()"
        );
    }

    @Test
    public void byImplementingAnyOf() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                    .byImplementingAnyOf(AnyInterface.class, Map.class)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public org.failearly.ajunit.builder.TestSubject3 org.failearly.ajunit.builder.TestSubject4.getTestSubject3()",
                "public org.failearly.ajunit.builder.TestSubject1 org.failearly.ajunit.builder.TestSubject4.getTestSubject1()",
                "public java.util.Map org.failearly.ajunit.builder.TestSubject4.getMap()",
                "public java.util.HashMap org.failearly.ajunit.builder.TestSubject4.getHashMap()",
                "public java.util.Hashtable org.failearly.ajunit.builder.TestSubject4.getHashTable()"
        );
    }

    @Test
    public void byImplementingNoneOf() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                    .byImplementingNoneOf(Serializable.class)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject4.setAnyValue(int)",
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
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "public boolean java.lang.Object.equals(java.lang.Object)",
                "public native int java.lang.Object.hashCode()",
                "public final native void java.lang.Object.notify()",
                "public final native void java.lang.Object.notifyAll()",
                "protected void java.lang.Object.finalize() throws java.lang.Throwable",
                "protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException",
                "private static native void java.lang.Object.registerNatives()"
        );
    }

    @Test
    public void byExtending() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                .byExtending(AbstractBaseClass.class)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public org.failearly.ajunit.builder.TestSubject1 org.failearly.ajunit.builder.TestSubject4.getTestSubject1()"
        );
    }

    @Test
    public void byNotExtending() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                .byNotExtending(Object.class)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject4.setAnyValue(int)",
                "public boolean org.failearly.ajunit.builder.TestSubject4.getBoolean()",
                "public byte org.failearly.ajunit.builder.TestSubject4.getByte()",
                "public short org.failearly.ajunit.builder.TestSubject4.getShort()",
                "public int org.failearly.ajunit.builder.TestSubject4.getInt()",
                "public long org.failearly.ajunit.builder.TestSubject4.getLong()",
                "public float org.failearly.ajunit.builder.TestSubject4.getFloat()",
                "public double org.failearly.ajunit.builder.TestSubject4.getDouble()",
                // java.lang.Object
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "public boolean java.lang.Object.equals(java.lang.Object)",
                "public native int java.lang.Object.hashCode()",
                "public final native void java.lang.Object.notify()",
                "public final native void java.lang.Object.notifyAll()",
                "protected void java.lang.Object.finalize() throws java.lang.Throwable",
                "private static native void java.lang.Object.registerNatives()"
        );
    }

    @Test
    public void byImplementingAllOf() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                .byImplementingAllOf(AnyInterface.class, Serializable.class)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public org.failearly.ajunit.builder.TestSubject3 org.failearly.ajunit.builder.TestSubject4.getTestSubject3()"
        );
    }

    @Test
    public void byPackageName() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                    .byPackageName("org.failearly", StringMatcherType.STARTS_WITH)
                    .byPackageName("java.lang", StringMatcherType.CONTAINS)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public org.failearly.ajunit.builder.TestSubject4 org.failearly.ajunit.builder.TestSubject4.getTestSubject4()",
                "public org.failearly.ajunit.builder.TestSubject3 org.failearly.ajunit.builder.TestSubject4.getTestSubject3()",
                "public org.failearly.ajunit.builder.TestSubject2 org.failearly.ajunit.builder.TestSubject4.getTestSubject2()",
                "public org.failearly.ajunit.builder.TestSubject1 org.failearly.ajunit.builder.TestSubject4.getTestSubject1()",
                "public java.lang.Number org.failearly.ajunit.builder.TestSubject4.getNumber()",
                "public java.lang.Float org.failearly.ajunit.builder.TestSubject4.getFloatWrapper()",
                "public java.lang.Double org.failearly.ajunit.builder.TestSubject4.getDoubleWrapper()",
                "public java.lang.Long org.failearly.ajunit.builder.TestSubject4.getLongWrapper()",
                "public java.lang.Boolean org.failearly.ajunit.builder.TestSubject4.getBooleanWrapper()",
                "public java.lang.Integer org.failearly.ajunit.builder.TestSubject4.getIntWrapper()",
                "public java.lang.Short org.failearly.ajunit.builder.TestSubject4.getShortWrapper()",
                "public java.lang.Byte org.failearly.ajunit.builder.TestSubject4.getByteWrapper()",
                "public java.lang.String org.failearly.ajunit.builder.TestSubject4.getString()",
                "public org.failearly.ajunit.builder.LogicalOperator org.failearly.ajunit.builder.TestSubject4.getEnum()",
                // java.lang.Object
                "public java.lang.String java.lang.Object.toString()",
                "public final native java.lang.Class java.lang.Object.getClass()",
                "protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException"
        );
    }
    @Test
    public void byInterface() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                    .byInterface()
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.util.Map org.failearly.ajunit.builder.TestSubject4.getMap()",
                "public java.util.Collection org.failearly.ajunit.builder.TestSubject4.getCollection()"
       );
    }
    @Test
    public void byAnnotation() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                    .byAnnotation()
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
        );
    }

    @Test
    public void byArray() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                    .byArray()
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.lang.String[] org.failearly.ajunit.builder.TestSubject4.getStringArray()",
                "public int[] org.failearly.ajunit.builder.TestSubject4.getIntArray()"
        );
    }

    @Test
    public void byPrimitive() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                    .byPrimitive()
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public boolean org.failearly.ajunit.builder.TestSubject4.getBoolean()",
                "public byte org.failearly.ajunit.builder.TestSubject4.getByte()",
                "public short org.failearly.ajunit.builder.TestSubject4.getShort()",
                "public int org.failearly.ajunit.builder.TestSubject4.getInt()",
                "public long org.failearly.ajunit.builder.TestSubject4.getLong()",
                "public float org.failearly.ajunit.builder.TestSubject4.getFloat()",
                "public double org.failearly.ajunit.builder.TestSubject4.getDouble()",

                "public boolean java.lang.Object.equals(java.lang.Object)",
                "public native int java.lang.Object.hashCode()"
        );
    }

    @Test
    public void byPrimitiveWrapperType() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                    .byPrimitiveWrapperType()
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.lang.Float org.failearly.ajunit.builder.TestSubject4.getFloatWrapper()",
                "public java.lang.Double org.failearly.ajunit.builder.TestSubject4.getDoubleWrapper()",
                "public java.lang.Long org.failearly.ajunit.builder.TestSubject4.getLongWrapper()",
                "public java.lang.Boolean org.failearly.ajunit.builder.TestSubject4.getBooleanWrapper()",
                "public java.lang.Integer org.failearly.ajunit.builder.TestSubject4.getIntWrapper()",
                "public java.lang.Short org.failearly.ajunit.builder.TestSubject4.getShortWrapper()",
                "public java.lang.Byte org.failearly.ajunit.builder.TestSubject4.getByteWrapper()"
        );
    }

    @Test
    public void byEnum() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                    .byEnum()
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public org.failearly.ajunit.builder.LogicalOperator org.failearly.ajunit.builder.TestSubject4.getEnum()"
        );
    }

    @Test
    public void byCollection() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                    .byCollection()
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.util.Collection org.failearly.ajunit.builder.TestSubject4.getCollection()",
                "public java.util.LinkedList org.failearly.ajunit.builder.TestSubject4.getList()",
                "public java.util.HashSet org.failearly.ajunit.builder.TestSubject4.getSet()",
                "public java.util.Vector org.failearly.ajunit.builder.TestSubject4.getVector()"
        );
    }
    @Test
    public void byMap() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                    .byMap()
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.util.Map org.failearly.ajunit.builder.TestSubject4.getMap()",
                "public java.util.HashMap org.failearly.ajunit.builder.TestSubject4.getHashMap()",
                "public java.util.Hashtable org.failearly.ajunit.builder.TestSubject4.getHashTable()"
        );
    }
}
