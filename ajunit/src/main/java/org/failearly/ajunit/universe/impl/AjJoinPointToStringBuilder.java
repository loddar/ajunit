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
package org.failearly.ajunit.universe.impl;

import org.failearly.ajunit.universe.AjJoinPoint;

import java.lang.reflect.Member;

public final class AjJoinPointToStringBuilder {
    public static String toShortString(final AjJoinPoint ajJoinPoint) {
        final StringBuilder stringBuilder=new StringBuilder("AJP{");
        stringBuilder.append("t=").append(ajJoinPoint.getJoinPointType());
        addProperty(stringBuilder, "#", ajJoinPoint.getNumApplications());
        addShortProperty(stringBuilder, "m", ajJoinPoint.getMethod());
        addShortProperty(stringBuilder, "f", ajJoinPoint.getField());
        addShortProperty(stringBuilder, "c", ajJoinPoint.getConstructor());
        return stringBuilder.append("}").toString();
    }

    public static String toLongString(final AjJoinPoint ajJoinPoint) {
        final StringBuilder stringBuilder=new StringBuilder("AjJoinPoint{");
        stringBuilder.append("type=").append(ajJoinPoint.getJoinPointType());
        addProperty(stringBuilder, " #apply", ajJoinPoint.getNumApplications());
        addProperty(stringBuilder, " method", ajJoinPoint.getMethod());
        addProperty(stringBuilder, " field", ajJoinPoint.getField());
        addProperty(stringBuilder, " constructor", ajJoinPoint.getConstructor());
        return stringBuilder.append("}").toString();
    }

    private static void addProperty(final StringBuilder stringBuilder, String type, Object object) {
        if( object!=null ) {
            stringBuilder.append(",").append(type).append("=").append(object.toString());
        }
    }
   private static void addShortProperty(final StringBuilder stringBuilder, String type, Member member) {
        if( member!=null ) {
            stringBuilder.append(",").append(type).append("=")
                    .append(member.getDeclaringClass())
                    .append(".")
                    .append(member.getName());
        }
    }

}