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
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package org.failearly.ajunit.internal.builder;

import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.util.AjAssert;

import java.util.LinkedList;
import java.util.List;

/**
 * RootBuilderBase is responsible for ...
 */
abstract class RootBuilderBase<R extends RootBuilder> extends BuilderBase<R,R> implements RootBuilder {
    private final List<Builder> builderStack=new LinkedList<>();
    protected RootBuilderBase() {
    }

    @Override
    public final void addBuilder(Builder builder) {
        this.builderStack.add(0, builder);
    }

    @Override
    public final Predicate build() {
        AjAssert.state(! builderStack.isEmpty(),"build() has been called twice.");
        cleanupAll();
        return doBuild();
    }

    private void cleanupAll() {
        Builder last=null;
        for (Builder builder : builderStack) {
            builder.cleanup();
            last=builder;
        }

        AjAssert.state(last == this, "The last builder must be the ROOT object.");
        builderStack.clear();
    }
}
