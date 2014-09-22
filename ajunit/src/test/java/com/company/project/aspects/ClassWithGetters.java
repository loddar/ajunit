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
package com.company.project.aspects;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * ClassWithGetters is provides invalid getters for {@link InvalidGetterAnnotationAspectTest}.
 */
public class ClassWithGetters {
    public int getValue() {
        return 0;
    }

    public int getValueWithException() throws Exception {
        return 0;
    }

    public void getReturningVoid() {}

    public int getWithParameter(int val) {
        return val;
    }

    public int[] getAnyArray() {
        return null;
    }
    public String[][] getAnyArray2Dim() {
        return null;
    }

    public Collection<String> getAnyCollection() {
        return null;
    }

    public List<String> getAnyList() {
        return null;
    }

    protected void getAnyThing(int p) throws Exception {}

    public HashMap<String, String> getMap() {
        return null;
    }
}
