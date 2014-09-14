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
package org.failearly.ajunit.internal;

/**
 * Named gives any derived class a name. There are two possibilities:<br>
 * <br>
 * <ul>
 *     <li>Fixed by providing the name by constructor.</li>
 *     <li>Dynamic by overriding {@link #mkString(int)}.</li>
 * </ul>
 * <br>
 */
public abstract class Named {
    public static final String INDENT = "  ";
    public static final int FIRST_LEVEL = 0;
    private final String name;

    /**
     * Uses {@link Class#getSimpleName()} for name.
     */
    protected Named() {
        name = simpleClassName(this.getClass());
    }

    protected Named(String name) {
        this(name, true);
    }

    protected Named(String name, boolean useClassName) {
        this.name = name + useClassName(useClassName);
    }

    private String useClassName(boolean useClassName) {
        if( ! useClassName )
            return "";
        return "@" + simpleClassName(this.getClass());
    }

    /**
     * @return The origin name.
     */
    public final String getName() {
        return name;
    }

    /**
     * Used by {@link #toString()}. Override if there is a more sophisticated name. If not overridden, it returns {@link #name}.
     * @return the name.
     * @param level
     */
    protected String mkString(int level) {
        return name;
    }

    @Override
    public final String toString() {
        return this.mkString(FIRST_LEVEL);
    }


    /**
     * Helper method for indenting the toString or mkString result.
     * @param object the current (named?) object.
     * @param level current level.
     * @return indented of (named?) object.
     */
    protected static String mkString(Object object, int level) {
        final int nextLevel = level + 1;
        if( object instanceof Named ) {
            return indent(nextLevel) + ((Named)object).mkString(nextLevel);
        }

        return indent(nextLevel) + object;
    }

    protected static String indent(String str, int level) {
        return indent(level) + str;
    }

    private static String indent(int level) {
        String result="\n";
        for (int i = 0; i < level; i++) {
            result += INDENT;
        }
        return result;
    }

    protected static String simpleClassName(Class<?> thisClass) {
        String className=thisClass.getSimpleName();

        if( thisClass.isAnonymousClass() ) {
            className = thisClass.getSuperclass().getSimpleName();
        }

        if( className.isEmpty() ) {
            className = thisClass.getName();
        }

        return className.substring(className.lastIndexOf(".")+1);
    }

}
