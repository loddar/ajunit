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

/**
 * MessageBuilder is responsible for ...
 */
public interface MessageBuilder {
    /**
     * Creates a part of the entire message including a space.
     * @param message the message.
     * @return this.
     */
    MessageBuilder part(String message);

    /**
     * Adds a single new line.
     * @return this.
     */
    MessageBuilder newline();

    /**
     * Adds new lines.
     * @return this.
     */
    MessageBuilder newlines(int num);

    /**
     * Adds a new line with text.
     * @param message the message on the next line
     * @return this.
     */
    MessageBuilder newline(String message);

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
     * @param argument the argument.
     * @return this.
     */
    MessageBuilder arg(Object argument);

    /**
     * Adds a full stop sign.
     */
    MessageBuilder fullStop();

    /**
     * Build the message.
     */
    String build();
}
