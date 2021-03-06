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
 * MessageBuilders provides factory methods for {@link org.failearly.ajunit.internal.util.MessageBuilder}.
 */
public final class MessageBuilders {

    private MessageBuilders() {}

    /**
     * Factory method for message builder with initial message: <i>ajUnit - {@code message}</i>.
     * @param message the initial message.
     * @return the created message builder.
     */
    public static MessageBuilder message(String message) {
        return createMessageBuilder("ajUnit -").part(message);
    }

    /**
     * Factory method for Setup Error messages with initial message: <i>ajUnit - Setup Error: {@code message}</i>.
     * @param message the initial message.
     * @return the created message builder.
     */
    public static MessageBuilder setupError(String message) {
        return createMessageBuilder("ajUnit - Setup Error:").part(message);
    }

    private static MessageBuilder createMessageBuilder(String message) {
        return new MessageBuilderImpl(message);
    }
}
