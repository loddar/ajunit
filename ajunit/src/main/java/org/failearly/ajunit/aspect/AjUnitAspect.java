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
package org.failearly.ajunit.aspect;

import org.aspectj.lang.JoinPoint;

/**
 * AjUnitAspect is responsible for ...
 */
public interface AjUnitAspect {
    /**
     * <i>Apply the join point</i>, means to find the associated ajUnit join point and then mark it as applied.
     *
     * @param thisJoinPointStaticPart  the current join point.
     * @param thisEnclosingJoinPointStaticPart the enclosing join point ({@code thisEnclosingJoinPointStaticPart})
     * @param context context builder.
     *
     * @see AjContexts
     */
    void applyJoinPoint(
            JoinPoint.StaticPart thisJoinPointStaticPart,
            JoinPoint.StaticPart thisEnclosingJoinPointStaticPart,
            Context context);

    /**
     * Context is responsible for collecting (named) context values associated with current joint point.
     */
    public interface Context {
        /**
         * Add a (named) context value.
         * @param name the context name.
         * @param value the context value.
         * @return itself.
         */
        Context addValue(String name, Object value);
    }
}
