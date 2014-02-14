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
package org.failearly.ajunit;

import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.runner.SuppressedJoinPointFactory;
import org.failearly.ajunit.internal.transformer.ajp.AjpTransformers;
import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;
import org.failearly.ajunit.internal.util.AjAssert;

/**
 * AjSuppressedJoinPoints provides factory methods for {@link SuppressedJoinPoint}.
 *
 * @see AjUnitSetup#enableSuppressedJoinPoints(SuppressedJoinPoint)
 */
public final class AjSuppressedJoinPoints extends SuppressedJoinPointFactory {

    private static final Predicate PREDICATE_JLO_STANDARD = anyMethodPredicate("equals", "hashCode", "toString", "getClass");

    private static final Predicate PREDICATE_JLO_SYNC_METHODS = anyMethodPredicate("notify", "notifyAll", "wait");

    private static final Predicate PREDICATE_JLO_CLONE = anyMethodPredicate("clone");

    private static final Predicate PREDICATE_JLO_TO_STRING = anyMethodPredicate("toString");

    private static final Predicate PREDICATE_JLO_HASH_CODE = anyMethodPredicate("hashCode");

    private static final Predicate PREDICATE_JLO_EQUALS = anyMethodPredicate("equals");

    private static final Predicate PREDICATE_JLO_GET_CLASS = anyMethodPredicate("getClass");

    private static final Predicate PREDICATE_JLO_FINALIZE = anyMethodPredicate("finalize");

    private static final Predicate PREDICATE_JLO_CONSTRUCTOR = LogicalPredicates.and(
            byDeclaringClass(Object.class),
            StandardPredicates.transformerPredicate(
                    StandardTransformers.transformerComposition(
                            AjpTransformers.constructorTransformer()
                    ),
                    StandardPredicates.predicateNotNull()
            )
    );

    /**
     * All methods and constructors of {@link java.lang.Object}.
     */
    public static SuppressedJoinPoint javaLangObject() {
        return new SuppressedJoinPoint() {
            @Override
            public Predicate predicate() {
                return PREDICATE_JAVA_LANG_OBJECT;
            }
        };
    }

    /**
     * Alias for {@link #javaLangObject()}.
     */
    public static SuppressedJoinPoint jlo() {
        return javaLangObject();
    }

    /**
     * Selected Methods: {@link Object#equals(Object)}, {@link Object#hashCode()}, {@link Object#toString()} and {@link Object#getClass()}.
     */
    public static SuppressedJoinPoint jloStandardMethods() {
        return new SuppressedJoinPoint() {
            @Override
            public Predicate predicate() {
                return PREDICATE_JLO_STANDARD;
            }
        };
    }

    /**
     * Selected Methods: {@link Object#notify()}, {@link Object#notifyAll()}, {@link Object#wait()},
     * {@link Object#wait(long, int)}  and {@link Object#wait(long)}.
     */
    public static SuppressedJoinPoint jloSyncMethods() {
        return new SuppressedJoinPoint() {
            @Override
            public Predicate predicate() {
                return PREDICATE_JLO_SYNC_METHODS;
            }
        };
    }

    /**
     * Selected Methods: {@link Object#clone()}.
     */
    public static SuppressedJoinPoint jloClone() {
        return new SuppressedJoinPoint() {
            @Override
            public Predicate predicate() {
                return PREDICATE_JLO_CLONE;
            }
        };
    }

    /**
     * Selected Methods: {@link Object#toString()}.
     */
    public static SuppressedJoinPoint jloToString() {
        return new SuppressedJoinPoint() {
            @Override
            public Predicate predicate() {
                return PREDICATE_JLO_TO_STRING;
            }
        };
    }

    /**
     * Selected Methods: {@link Object#hashCode()}.
     */
    public static SuppressedJoinPoint jloHashCode() {
        return new SuppressedJoinPoint() {
            @Override
            public Predicate predicate() {
                return PREDICATE_JLO_HASH_CODE;
            }
        };
    }

    /**
     * Selected Methods: {@link Object#equals(Object)}.
     */
    public static SuppressedJoinPoint jloEquals() {
        return new SuppressedJoinPoint() {
            @Override
            public Predicate predicate() {
                return PREDICATE_JLO_EQUALS;
            }
        };
    }

    /**
     * Selected Methods: {@link Object#getClass()}.
     */
    public static SuppressedJoinPoint jloGetClass() {
        return new SuppressedJoinPoint() {
            @Override
            public Predicate predicate() {
                return PREDICATE_JLO_GET_CLASS;
            }
        };
    }

    /**
     * Selected Methods: {@link Object#finalize()}.
     */
    public static SuppressedJoinPoint jloFinalize() {
        return new SuppressedJoinPoint() {
            @Override
            public Predicate predicate() {
                return PREDICATE_JLO_FINALIZE;
            }
        };
    }

    /**
     * Constructor of {@link Object}.
     */
    public static SuppressedJoinPoint jloConstructor() {
        return new SuppressedJoinPoint() {
            @Override
            public Predicate predicate() {
                return PREDICATE_JLO_CONSTRUCTOR;
            }
        };
    }

    /**
     * For enabling a specific AspectJ method starting with {@link #ASPECTJ_PREFIX}. Usually all AspectJ generated methods and fields will be ignored.
     * @param methodName the name without {@link #ASPECTJ_PREFIX}.
     */
    public static SuppressedJoinPoint ajcMethod(final String methodName) {
        AjAssert.parameter(! methodName.startsWith(ASPECTJ_PREFIX), methodName+" starts with " + ASPECTJ_PREFIX + ". Remove the prefix " + ASPECTJ_PREFIX);
        return new SuppressedJoinPoint() {
            @Override
            public Predicate predicate() {
                return byMethodName(ASPECTJ_PREFIX + methodName);
            }
        };
    }
}
