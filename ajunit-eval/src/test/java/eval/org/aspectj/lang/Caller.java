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
package eval.org.aspectj.lang;

import eval.org.aspectj.lang.fixture.ConstructorCallTestFixture;
import eval.org.aspectj.lang.fixture.FieldSetTestFixture;
import eval.org.aspectj.lang.fixture.MethodCallTestFixture;

/**
 * Caller is responsible for executing an object in different this context.
 */
public final class Caller {
    public void call(MethodCallTestFixture methodCallTestFixture) {
        methodCallTestFixture.toBeCalled();
    }
    public static void staticCall(MethodCallTestFixture methodCallTestFixture) {
        methodCallTestFixture.toBeCalled();
    }

    public void callStatic(MethodCallTestFixture methodCallTestFixture) {
        methodCallTestFixture.toBeCalled("any value");
    }

    public void setField(final FieldSetTestFixture testFixture, final int newFieldValue) {
        testFixture.field = newFieldValue;
    }

    public ConstructorCallTestFixture createConstructorCallTestFixture() {
        return new ConstructorCallTestFixture();
    }
}
