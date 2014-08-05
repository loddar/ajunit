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
package eval.org.aspectj.lang;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * JoinPointCollector is responsible for collecting the {@link org.aspectj.lang.JoinPoint}s.
 */
public abstract class JoinPointCollector {


    public static final Logger LOGGER= LoggerFactory.getLogger(JoinPointCollector.class);

    private static final Map<Class<? extends JoinPointCollector>, JoinPoint> JOIN_POINTS = new HashMap<>();
    private static final Map<Class<? extends JoinPointCollector>, String> THIS_CONTEXTS = new HashMap<>();
    private static final Map<Class<? extends JoinPointCollector>, String> TARGET_CONTEXTS = new HashMap<>();
    private static final Map<Class<? extends JoinPointCollector>, String> ENCLOSING_JP = new HashMap<>();
    private static int numCalledJoinPoints = 0;

    protected JoinPointCollector() {
    }

    public static JoinPoint getJoinPoint(Class<? extends JoinPointCollector> aspect) {
        return JOIN_POINTS.get(aspect);
    }

    public static String getThisContext(Class<? extends JoinPointCollector> aspect) {
        return THIS_CONTEXTS.get(aspect);
    }

    public static String getTargetContext(Class<? extends JoinPointCollector> aspect) {
        return TARGET_CONTEXTS.get(aspect);
    }



    public static int getNumCalledJoinPoints() {
        return numCalledJoinPoints;
    }

    public static void clear() {
        JOIN_POINTS.clear();
        THIS_CONTEXTS.clear();
        TARGET_CONTEXTS.clear();
        numCalledJoinPoints = 0;
    }


    protected void add(final JoinPoint joinPoint, JoinPoint.StaticPart enclosingJoinPoint) {
        LOGGER.info("Current join point is {}", joinPoint);
        JOIN_POINTS.put(this.getClass(), joinPoint);
        addContext("THIS", joinPoint.getThis(), THIS_CONTEXTS);
        addContext("TARGET", joinPoint.getTarget(), TARGET_CONTEXTS);
        addContext("ENCJP", enclosingJoinPoint, ENCLOSING_JP);
        numCalledJoinPoints += 1;
    }

    private void addContext(final String contextType, final Object context, final Map<Class<? extends JoinPointCollector>, String> contextMap) {
        if( context !=null ) {
            LOGGER.debug("Current context type {} context '{}'", contextType, context);
            contextMap.put(this.getClass(), context.toString());
        }
    }


}
