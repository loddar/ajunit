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
package org.failearly.ajunit.internal.transformer.ajp;

import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointType;

/**
 * AjpJoinPointTypeFilter is a filter of {@link org.failearly.ajunit.internal.universe.AjJoinPoint},
 * which uses the {@link org.failearly.ajunit.internal.universe.AjJoinPointType} as filter criteria.
 */
final class AjpJoinPointTypeFilter extends AjpTransformerBase<AjJoinPoint> {
    private final AjJoinPointType joinPointType;
    AjpJoinPointTypeFilter(AjJoinPointType joinPointType) {
        this.joinPointType = joinPointType;
    }

    @Override
    protected AjJoinPoint doTypedTransform(final AjJoinPoint input) {
        if( ! input.getJoinPointType().equals(this.joinPointType)) {
            return null;
        }

        return input;
    }
}
