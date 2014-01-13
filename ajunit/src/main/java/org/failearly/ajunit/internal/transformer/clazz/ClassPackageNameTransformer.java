/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2014 Marko Umek (ajunit.contact(at)fail-early.com)
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
package org.failearly.ajunit.internal.transformer.clazz;

/**
 * Executes {@link Class#getPackage()} and {@link Package#getName()} in one step.
 */
final class ClassPackageNameTransformer extends ClassTransformerBase<String> {
    @Override
    protected String doTypedTransform(Class input) {
        final Package thePackage = input.getPackage();
        if(thePackage==null) {
            return "";
        }
        return thePackage.getName();
    }
}
