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

/**
 * ExtendedClassSelector adds some selectors for field's, method's return and parameter types.
 */
public interface ExtendedClassSelector<SB extends SelectorBuilder> extends ClassSelector<SB> {
    /**
     * Selects method, field or constructor joinpoints the method's return type, the field's type or the parameter's type
     * is a primitive type (excluding {@code void}).
     * <p/>
     * Examples:<br/>
     * <ul>
     * <li>{@code int getValue()}</li>
     * <li>{@code void setValue(int)}</li>
     * <li>{@code MyClass(int)}</li>
     * <li>{@code int myInt}</li>
     * </ul>
     *
     * @return itself
     * @see Class#isPrimitive()
     */
    SB byPrimitive();

    /**
     * Selects method, field or constructor joinpoints the method's return type, the field's type or the parameter's type
     * is an enum type.
     * <p/>
     * Examples:<br/>
     * <ul>
     * <li>{@code MyEnum getValue()}</li>
     * <li>{@code void setValue(MyEnum)}</li>
     * <li>{@code MyClass(MyEnum)}</li>
     * <li>{@code MyEnum myEnum}</li>
     * </ul>
     *
     * @return itself
     * @see Class#isEnum()
     * @see java.lang.Enum
     */
    SB byEnum();

    /**
     * Selects method, field or constructor joinpoints the method's return type, the field's type or the parameter's type
     * is an annotation type.
     * <p/>
     * Examples:<br/>
     * <ul>
     * <li>{@code MyAnnotation getValue()}</li>
     * <li>{@code void setValue(MyAnnotation)}</li>
     * <li>{@code MyClass(MyAnnotation)}</li>
     * <li>{@code MyAnnotation myAnnotation}</li>
     * </ul>
     *
     * @return itself
     * @see Class#isAnnotation()
     * @see java.lang.annotation.Annotation
     */
    SB byAnnotation();


    /**
     * Selects method, field or constructor joinpoints the method's return type, the field's type or the parameter's type
     * is an {@code interface} which includes annotation types.
     * <p/>
     * Examples:<br/>
     * <ul>
     * <li>{@code MyAnnotation getValue()}</li>
     * <li>{@code void setValue(Serializable)}</li>
     * <li>{@code MyClass(MyInterface)}</li>
     * <li>{@code MyInterface myInterface}</li>
     * </ul>
     *
     * @return itself
     * @see Class#isAnnotation()
     * @see Class#isInterface()
     */
    SB byInterface();

    /**
     * Selects method, field or constructor joinpoints the method's return type, the field's type or the parameter's type
     * is a wrapper type.
     * <p/>
     * <ul>
     * <li>extending {@link java.lang.Number} and is part of package {@code java.lang}</li>
     * <li>or is {@link java.lang.Boolean}</li>
     * <li>or is {@link java.lang.Void}</li>
     * <li>excluding {@link java.lang.Number}</li>
     * </ul>
     *
     * @return itself
     */
    SB byPrimitiveWrapperType();

    /**
     * Selects method, field or constructor joinpoints the method's return type, the field's type or the parameter's type
     * is a {@link java.util.Collection}.
     * <p/>
     * Examples:<br/>
     * <ul>
     * <li>{@code List getList()}</li>
     * <li>{@code void addAll(Set set)}</li>
     * <li>{@code MyClass(Queue)}</li>
     * <li>{@code Deque myDeque}</li>
     * </ul>
     * @return itself
     * @see #byMap()
     */
    SB byCollection();

    /**
     * Selects method, field or constructor joinpoints the method's return type, the field's type or the parameter's type
     * is a {@link java.util.Map}.
     * <p/>
     * Examples:<br/>
     * <ul>
     * <li>{@code Map getMap()}</li>
     * <li>{@code void addAll(Map set)}</li>
     * <li>{@code MyClass(HashMap)}</li>
     * <li>{@code ConcurrentMap myMap}</li>
     * </ul>
     * @return itself
     * @see #byCollection()
     */
    SB byMap();
}
