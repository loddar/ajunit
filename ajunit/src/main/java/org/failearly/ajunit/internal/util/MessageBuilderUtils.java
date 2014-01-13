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
package org.failearly.ajunit.internal.util;

/**
 * MessageBuilderUtils is responsible for ...
 */
public abstract class MessageBuilderUtils {

    private MessageBuilderUtils() {}

    /**
     * Factory method for message builder with initial message.
     * @param message the initial message.
     * @return the created message builder.
     */
    public static MessageBuilder message(String message) {
        return new MessageBuilderImpl("ajUnit -").part(message);
    }

    /**
     * Factory method for message builder with initial message (without usual ajUnit -).
     * @param message the initial message.
     * @return the created message builder.
     */
    public static MessageBuilder assertMessage(String message) {
        return new MessageBuilderImpl(message);
    }
}
