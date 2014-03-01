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
 * AjJoinPointStringBuilders contains {@link org.failearly.ajunit.internal.universe.AjJoinPointStringBuilder} implementations for
 * {@link AjJoinPoint#toString()} and {@link AjJoinPoint#toShortString()}.
 */
public final class AjJoinPointStringBuilders {

    private static final AjJoinPointStringBuilder longBuilder = new AjJoinPointStringBuilderBase() {
        private String joinPointKind ="";
        private String joinPoint="";
        private String numberOfApplications="";

        @Override
        public AjJoinPointStringBuilder setMethod(Method method) {
            this.joinPoint = "method="+method.toString();
            return this;
        }

        @Override
        public AjJoinPointStringBuilder setField(Field field) {
            this.joinPoint = "field="+field.toString();
            return this;
        }

        @Override
        public AjJoinPointStringBuilder setConstructor(Constructor<?> constructor) {
            this.joinPoint = "constructor="+constructor.toString();
            return this;
        }

        @Override
        public AjJoinPointStringBuilder setJoinPointType(AjJoinPointType joinPointType) {
            this.joinPointKind = joinPointType.getJoinPointKind();
            return this;
        }

        @Override
        public AjJoinPointStringBuilder setNumberOfApplications(int numApplications) {
            numberOfApplications = Integer.toString(numApplications);
            return this;
        }

        @Override
        public String build() {
            final StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append(joinPointKind)
                         .append("{#apply=").append(numberOfApplications)
                         .append(", ").append(joinPoint)
                         .append("}");
            return stringBuilder.toString();
        }
    };

    private static final AjJoinPointStringBuilder shortBuilder = new AjJoinPointStringBuilderBase() {
        private String joinPointKind ="";
        private String joinPoint="";
        private String numberOfApplications="";

        @Override
        public AjJoinPointStringBuilder setMethod(Method method) {
            this.joinPoint = method.toString();
            return this;
        }

        @Override
        public AjJoinPointStringBuilder setField(Field field) {
            this.joinPoint = field.toString();
            return this;
        }

        @Override
        public AjJoinPointStringBuilder setConstructor(Constructor<?> constructor) {
            this.joinPoint = constructor.toString();
            return this;
        }

        @Override
        public AjJoinPointStringBuilder setJoinPointType(AjJoinPointType joinPointType) {
            this.joinPointKind = joinPointType.getJoinPointKind();
            return this;
        }

        @Override
        public AjJoinPointStringBuilder setNumberOfApplications(int numApplications) {
            numberOfApplications = Integer.toString(numApplications);
            return this;
        }

        @Override
        public String build() {
            final StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append(joinPointKind)
                    .append("(")
                    .append(joinPoint)
                    .append(")#a=").append(numberOfApplications);
            return stringBuilder.toString();
        }
    };

    private AjJoinPointStringBuilders() {
    }

    public static AjJoinPointStringBuilder toLongStringBuilder() {
        return longBuilder;
    }

    public static AjJoinPointStringBuilder toShortStringBuilder() {
        return shortBuilder;
    }

}
