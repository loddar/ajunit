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

import org.failearly.ajunit.internal.universe.AjJoinPoint;

import java.util.HashMap;
import java.util.Map;

/**
 * AjContexts provides factory methods for {@link org.failearly.ajunit.aspect.AjUnitAspect.Context}.
 */
@SuppressWarnings("UnusedDeclaration")
public final class AjContexts {
    private AjContexts() {
    }

    /**
     * Factory method for {@link org.failearly.ajunit.aspect.AjUnitAspect.Context}.
     * @return new Context.
     */
    public static AjUnitAspect.Context context() {
        return new ContextImpl();
    }

    /**
     * Convenient factory method for {@link org.failearly.ajunit.aspect.AjUnitAspect.Context} with one named context.
     * @return new Context.
     */
    public static AjUnitAspect.Context context(String name1, Object val1) {
        return context().addValue(name1, val1);
    }

    /**
     * Convenient factory method for {@link org.failearly.ajunit.aspect.AjUnitAspect.Context} with two named context.
     * @return new Context.
     */
    public static AjUnitAspect.Context context(String name1, Object val1, String name2, Object val2) {
        return context()
                    .addValue(name1, val1)
                    .addValue(name2, val2);
    }

    /**
     * If the context is current implementation, the entire context will be stored in {@code ajJoinPoint}, otherwise it will
     * be ignored.
     * @param context a context implementation
     * @param ajJoinPoint the applied joinpoint.
     */
    public static void storeNamedContextValues(final AjUnitAspect.Context context, final AjJoinPoint ajJoinPoint) {
        if( context instanceof ContextImpl ) {
            ((ContextImpl)context).storeNamedContextValues(ajJoinPoint);
        }
    }

    private static final class ContextImpl implements AjUnitAspect.Context {
        private final Map<String, Object> contextMap=new HashMap<>();
        private ContextImpl() {}

        @Override
        public AjUnitAspect.Context addValue(final String name, Object value) {
            this.contextMap.put(name, value);
            return this;
        }


        private void storeNamedContextValues(final AjJoinPoint ajJoinPoint) {
            for (Map.Entry<String, Object> context : contextMap.entrySet()) {
                ajJoinPoint.addContext(context.getKey(), context.getValue());
            }
        }
    }
}
