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
package org.failearly.ajunit.internal.transformer.standard;

import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.transformer.TransformerBase;

import java.util.LinkedList;
import java.util.List;

/**
 * TransformerComposition provides chaining functionality: <code>Tn(...T2(T1(input)))</code>.
 */
final class TransformerComposition extends TransformerBase {
    private final List<Transformer> transformers=new LinkedList<>();

    TransformerComposition(final List<Transformer> transformers) {
        super("Composition");
        this.transformers.addAll(transformers);
    }




    @Override
    protected Object doTransform(final Object input) {
        if( this.transformers.isEmpty() ) {
            return input;
        }

        return applyChain(input);
    }

    private Object applyChain(final Object input) {
        Object output=input;
        for (final Transformer transformer : transformers) {
            output=transformer.transform(output);
            if(output==null) {
                break;
            }
        }
        return output;
    }

    @Override
    protected String mkString(int level) {
        final StringBuilder stringBuilder=new StringBuilder(super.getName());
        stringBuilder.append("(");
        if(! transformers.isEmpty()) {
            for (Transformer transformer : transformers) {
                stringBuilder.append(mkString(transformer, level)).append(",");
            }

            stringBuilder.setLength(stringBuilder.length() - 1);
        }
        stringBuilder.append(indent(")", level));
        return stringBuilder.toString();
    }
}
