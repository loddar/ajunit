/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
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
package org.failearly.ajunit.internal.universe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * AjJoinPointStringBuilderBase is responsible for ...
 */
public abstract class AjJoinPointStringBuilderBase implements AjJoinPointStringBuilder {

    @Override
    public AjJoinPointStringBuilder setMethod(Method method) {
        return this;
    }

    @Override
    public AjJoinPointStringBuilder setJoinPointType(AjJoinPointType joinPointType) {
        return this;
    }

    @Override
    public AjJoinPointStringBuilder setNumberOfApplications(int numApplications) {
        return this;
    }

    @Override
    public AjJoinPointStringBuilder setField(Field field) {
        return this;
    }

    @Override
    public AjJoinPointStringBuilder setConstructor(Constructor<?> constructor) {
        return this;
    }
}
