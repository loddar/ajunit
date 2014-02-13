/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
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
 * The ajUnit message builder implementation.
 */
final class MessageBuilderImpl implements MessageBuilder {

    private final StringBuilder stringBuilder;

    MessageBuilderImpl(String initialMessage) {
        stringBuilder=new StringBuilder(initialMessage);
    }

    public MessageBuilderImpl() {
        this("");
    }

    @Override
    public MessageBuilder part(String message) {
        stringBuilder.append(" ")
                     .append(message);
        return this;
    }

    @Override
    public MessageBuilder newline() {
        stringBuilder.append("\n");
        return this;
    }

    @Override
    public MessageBuilder line(String message) {
        stringBuilder.append("\n- ").append(message);
        return this;
    }

    @Override
    public MessageBuilder subLine(String message) {
        stringBuilder.append("\n\t* ").append(message);
        return this;
    }

    @Override
    public MessageBuilder arg(Object object) {
        stringBuilder.append(" '").append(object).append("'");
        return this;
    }

    @Override
    public MessageBuilder fullStop() {
        stringBuilder.append(".");
        return this;
    }

    @Override
    public String build() {
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}
