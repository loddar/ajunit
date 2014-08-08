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
 *     <li>Dynamic by overriding {@link #toString0()}.</li>
 * </ul>
 * <br>
 */
public abstract class Named {
    private final String name;

    /**
     * Uses {@link Class#getSimpleName()} for name.
     */
    protected Named() {
        name = this.getClass().getSimpleName();
    }

    protected Named(String name) {
        this.name = name;
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
     */
    protected String toString0() {
        return name;
    }

    @Override
    public final String toString() {
        return this.toString0();
    }
}
