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
package org.failearly.ajunit.internal.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

/**
 * DefaultClassCollector is a default implementation for {@link ClassCollector}.
 *
 * @see ClassUtils
 */
public class DefaultClassCollector implements ClassCollector, Iterable<Class<?>> {
    private final Collection<Class<?>> classCollection;
    /**
     * Creates a (none unique) instance.
     */
    public DefaultClassCollector() {
        this(new HashSet<Class<?>>());
    }

    public DefaultClassCollector(Collection<Class<?>> classCollection) {
        this.classCollection = classCollection;
    }

    @Override
    public void addClass(Class<?> aClass) {
        this.classCollection.add(aClass);
    }

    /**
     * @return the collected classes/interfaces.
     */
    public Collection<Class<?>> getClassCollection() {
        return Collections.unmodifiableCollection(this.classCollection);
    }

    /**
     * Returns an iterator over a set of elements.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Class<?>> iterator() {
        return classCollection.iterator();
    }
}
