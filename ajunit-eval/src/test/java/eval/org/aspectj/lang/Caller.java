/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2013  Marko Umek (ajunit.contact(at)gmail.com)
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

import eval.org.aspectj.lang.subject.ConstructorCallTestSubject;
import eval.org.aspectj.lang.subject.FieldSetTestSubject;
import eval.org.aspectj.lang.subject.MethodCallTestSubject;

/**
 * Caller is responsible for executing an object in different this context.
 */
public final class Caller {
    public void call(MethodCallTestSubject methodCallTestSubject) {
        methodCallTestSubject.toBeCalled();
    }
    public static void staticCall(MethodCallTestSubject methodCallTestSubject) {
        methodCallTestSubject.toBeCalled();
    }

    public void callStatic(MethodCallTestSubject methodCallTestSubject) {
        methodCallTestSubject.toBeCalled("any value");
    }

    public void setField(final FieldSetTestSubject testSubject, final int newFieldValue) {
        testSubject.field = newFieldValue;
    }

    public ConstructorCallTestSubject createConstructorCallTestSubject() {
        return new ConstructorCallTestSubject();
    }
}
