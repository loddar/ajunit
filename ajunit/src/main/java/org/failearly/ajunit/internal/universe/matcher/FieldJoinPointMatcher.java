/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2014  Marko Umek (ajunit.contact(at)gmail.com)
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

import org.aspectj.lang.reflect.FieldSignature;
import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointType;

/**
 * FieldJoinPointMatcher compares {@link java.lang.reflect.Field} objects.
 */
public class FieldJoinPointMatcher extends AjJoinPointMatcherBase<FieldSignature> {

    public FieldJoinPointMatcher(AjJoinPointType joinPointType) {
        super(joinPointType, FieldSignature.class);
    }

    @Override
    protected boolean doMatchSignature(FieldSignature signature, AjJoinPoint ajUnitJoinPoint) {
        return signature.getField().equals(ajUnitJoinPoint.getField());
    }
}
