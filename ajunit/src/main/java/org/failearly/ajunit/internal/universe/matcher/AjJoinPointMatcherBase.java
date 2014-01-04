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

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointMatcher;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.failearly.ajunit.internal.util.AjAssert;

/**
 * AjJoinPointMatcherBase is responsible for ...
 */
abstract class AjJoinPointMatcherBase<T extends Signature> implements AjJoinPointMatcher {

    private final AjJoinPointType joinPointType;
    private final Class<T> signatureClass;

    protected AjJoinPointMatcherBase(AjJoinPointType joinPointType, Class<T> signatureClass) {
        this.joinPointType = joinPointType;
        this.signatureClass = signatureClass;
    }

    @Override
    public final boolean match(JoinPoint aspectJoinPoint, AjJoinPoint ajUnitJoinPoint) {
        AjAssert.state(joinPointType.isSameKind(aspectJoinPoint),"Expect same join point type.");
        return isSameJoinPointType(ajUnitJoinPoint) && doMatchSignature(castSignature(aspectJoinPoint.getSignature()), ajUnitJoinPoint);
    }
    
    protected abstract boolean doMatchSignature(final T signature, final AjJoinPoint ajUnitJoinPoint);

    private T castSignature(Signature signature) {
        return signatureClass.cast(signature);
    }

    private boolean isSameJoinPointType(AjJoinPoint ajUnitJoinPoint) {
        return this.joinPointType==ajUnitJoinPoint.getJoinPointType();
    }
}
