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
 * MessageBuilder is responsible for ...
 */
public interface MessageBuilder {
    /**
     * Creates a part of the entire message including a space.
     * @param message
     * @return this.
     */
    MessageBuilder part(String message);

    /**
     * Adds a new line.
     * @return this.
     */
    MessageBuilder newline();

    /**
     * Adds a new line and then the message.
     * <code>
     *     mb.newline().part(message);
     * </code>
     * @param message the message.
     * @return this.
     */
    MessageBuilder line(String message);

    /**
     * Adds a new line and then the message, but also indent with one tab.
     * <code>
     *     mb.newline().part("\t*"+message);
     * </code>
     * @param message the message.
     * @return this.
     */
    MessageBuilder subLine(String message);

    /**
     * Adds an argument.
     * <code>
     *     mb.part(" '" + object + "'");
     * </code>
     * @param object
     * @return
     */
    MessageBuilder arg(Object object);

    /**
     * Adds a full stop sign.
     */
    MessageBuilder fullStop();

    /**
     * Build the message.
     */
    String build();
}
