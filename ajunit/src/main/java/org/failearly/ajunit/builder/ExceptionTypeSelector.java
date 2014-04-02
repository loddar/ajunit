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
 * The ExceptionTypeSelector is responsible for selecting joinpoints based on the types of the (declared) exception list.
 *
 * @see java.lang.reflect.Method#getExceptionTypes()
 * @see java.lang.reflect.Constructor#getExceptionTypes()
 */
public interface ExceptionTypeSelector<SB extends SelectorBuilder, CS extends ExceptionTypeSelector>
        extends ClassSelector<CS>, LogicalSelector<CS> {

    /**
     * Select method or constructor joinpoints the exception list contains <i>runtime or not checked exceptions</i>.
     * @return itself (current selector builder)
     *
     * @see java.lang.RuntimeException
     */
    CS byRuntimeException();

    /**
     * Select method or constructor joinpoints the exception list contains <i>error exceptions</i>.
     * @return itself (current selector builder)
     *
     * @see java.lang.Error
     */
    CS byError();

    /**
     * Select method or constructor joinpoints the exception list contains <i>checked exceptions</i>.
     * @return itself (current selector builder)
     *
     * @see java.lang.Exception
     */
    CS byCheckedException();

    /**
     * Terminates the exception list selector.
     * @return the parent selector.
     */
    SB endExceptionTypes();

}
