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
package org.failearly.ajunit.internal.universe.matcher;

import org.aspectj.lang.reflect.MethodSignature;
import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointType;

/**
 * MethodJoinPointMatcher compares {@link java.lang.reflect.Method} objects.
 */
final class MethodJoinPointMatcher extends AjJoinPointMatcherBase<MethodSignature> {

    MethodJoinPointMatcher(AjJoinPointType joinPointType) {
        super(joinPointType, MethodSignature.class);
    }

    @Override
    protected boolean doMatchSignature(MethodSignature signature, AjJoinPoint ajUnitJoinPoint) {
        return signature.getMethod().equals(ajUnitJoinPoint.getMethod());
    }
}
