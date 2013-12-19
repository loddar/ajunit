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
package eval.org.aspectj.lang.subject;

/**
 * Test subject for {@link eval.org.aspectj.lang.PreInitializationTest}.
 */
public class PreInitializationTestSubject {
    public static final String DEFAULT = "default";
    public static final String UNKNOWN = "unknown";
    private String anyValue=UNKNOWN;

    public PreInitializationTestSubject() {
        this(DEFAULT);
    }

    public PreInitializationTestSubject(final String anyValue) {
        this.anyValue = anyValue;
    }

    public String getAnyValue() {
        return anyValue;
    }

    @Override
    public String toString() {
        return "PreInitializationTestSubject{" +
                "anyValue='" + anyValue + '\'' +
                '}';
    }
}
