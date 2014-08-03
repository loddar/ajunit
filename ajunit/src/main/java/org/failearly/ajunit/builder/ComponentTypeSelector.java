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
package org.failearly.ajunit.builder;

/**
 * ComponentTypeSelector provides methods for selecting the component type of any array.<br/>
 * This will be possible for:</br></br>
 *
 * <ul>
 *     <li>{@link ArrayTypeSelector#byComponentType()}</li>
 *     <li>TODO: specify byComponentType</li>
 * </ul>
 *
 *
 * @see ArrayTypeSelector
 * @see org.failearly.ajunit.builder.ReturnComponentTypeSelector
 * @see Class#getComponentType()
 */
public interface ComponentTypeSelector<PS extends SelectorBuilder, CS extends SelectorBuilder>  extends ExtendedClassSelector<CS> {

    PS endComponentType();
}
