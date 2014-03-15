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

import java.math.BigInteger;
import java.util.*;

/**
 * TestSubject4 for convenient return type tester.
 */
@SuppressWarnings("all")
public class TestSubject4 {
    // void
    public void setInt(int value) {
    }

    // primitiv
    public boolean getBoolean() {
        return false;
    }

    public int getInt() {
        return 0;
    }

    public byte getByte() {
        return 0;
    }

    public short getShort() {
        return 0;
    }

    public long getLong() {
        return 0;
    }

    public float getFloat() {
        return 0;
    }

    public double getDouble() {
        return 0;
    }

    // Wrapper
    public Boolean getBooleanWrapper() {
        return false;
    }

   public Integer getIntWrapper() {
        return 0;
    }

    public Byte getByteWrapper() {
        return 0;
    }

    public Short getShortWrapper() {
        return 0;
    }

    public Long getLongWrapper() {
        return 0L;
    }

    public Float getFloatWrapper() {
        return 0f;
    }

    public Double getDoubleWrapper() {
        return 0d;
    }

    // Not wrapper type, normal not primitive
    public Number getNumber() {
        return 0d;
    }

    public BigInteger getBigInteger() {
        return null;
    }

    public TestSubject4 getTestSubject4() {
        return null;
    }

    public String getString() {
        return null;
    }

    // Enum Type
    public LogicalOperator getEnum() { return null; }

    // collection test
    public Map<String, String> getMap() {
        return null;
    }

    public HashMap<String, String> getHashMap() {
        return null;
    }

    public Hashtable<String, String> getHashTable() {
        return null;
    }

    public Collection<String> getCollection() {
        return null;
    }

    public LinkedList<String> getList() {
        return null;
    }

    public HashSet<String> getSet() {
        return null;
    }

    public Vector<String> getVector() {
        return null;
    }

    // Array tester
    public int[] getIntArray() {
        return null;
    }

    public int[][] getIntMatrix() {
        return null;
    }

    public String[] getStringArray() {
        return null;
    }

    public String[][] getStringMatrix() {
        return null;
    }
}
