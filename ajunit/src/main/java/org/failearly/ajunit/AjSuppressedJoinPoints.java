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
package org.failearly.ajunit;

import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.predicate.string.StringPredicates;
import org.failearly.ajunit.internal.transformer.ajp.AjpTransformers;
import org.failearly.ajunit.internal.transformer.member.MemberTransformers;
import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;
import org.failearly.ajunit.internal.util.AjAssert;

/**
 * AjSuppressedJoinPoints provides factory methods for {@link SuppressedJoinPoint}.
 *
 * @see AjUnitSetup#enableSuppressedJoinPoints(SuppressedJoinPoint)
 */
public final class AjSuppressedJoinPoints {

    private static final String ASPECTJ_PREFIX = "ajc$";

    private static final SuppressedJoinPoint JLO_ALL = new SuppressedJoinPoint() {
        @Override
        public Predicate predicate() {
            return byDeclaringClassIsJavaLangObject();
        }
    };

    private static final SuppressedJoinPoint JLO_STANDARD = new SuppressedJoinPoint() {
        @Override
        public Predicate predicate() {
            return anyMethodPredicate("equals", "hashCode", "toString", "getClass");
        }
    };

    private static final SuppressedJoinPoint JLO_SYNC_METHODS = new SuppressedJoinPoint() {
        @Override
        public Predicate predicate() {
            return anyMethodPredicate("notify", "notifyAll", "wait");
        }
    };

    private static final SuppressedJoinPoint JLO_CLONE = new SuppressedJoinPoint() {
        @Override
        public Predicate predicate() {
            return anyMethodPredicate("clone");
        }
    };

    private static final SuppressedJoinPoint JLO_TO_STRING = new SuppressedJoinPoint() {
        @Override
        public Predicate predicate() {
            return anyMethodPredicate("toString");
        }
    };

    private static final SuppressedJoinPoint JLO_HASH_CODE = new SuppressedJoinPoint() {
        @Override
        public Predicate predicate() {
            return anyMethodPredicate("hashCode");
        }
    };

    private static final SuppressedJoinPoint JLO_EQUALS = new SuppressedJoinPoint() {
        @Override
        public Predicate predicate() {
            return anyMethodPredicate("equals");
        }
    };

    private static final SuppressedJoinPoint JLO_GET_CLASS = new SuppressedJoinPoint() {
        @Override
        public Predicate predicate() {
            return anyMethodPredicate("getClass");
        }
    };

    private static final SuppressedJoinPoint JLO_FINALIZE = new SuppressedJoinPoint() {
        @Override
        public Predicate predicate() {
            return anyMethodPredicate("finalize");
        }
    };

    private static final SuppressedJoinPoint JLO_CONSTRUCTOR = new SuppressedJoinPoint() {
        @Override
        public Predicate predicate() {
            return LogicalPredicates.and(
                    byDeclaringClassIsJavaLangObject(),
                    StandardPredicates.transformerPredicate(
                            StandardTransformers.transformerComposition(
                                    AjpTransformers.constructorTransformer()
                            ),
                            StandardPredicates.predicateNotNull()
                    )
            );
        }
    };

    private static final SuppressedJoinPoint AJC$METHODS = new SuppressedJoinPoint() {
        @Override
        public Predicate predicate() {
            return StandardPredicates.transformerPredicate(
                    StandardTransformers.transformerComposition(
                            AjpTransformers.methodTransformer(),
                            MemberTransformers.nameTransformer()
                    ),
                    StringPredicates.startsWith(ASPECTJ_PREFIX)
            );
        }
    };

    private static final SuppressedJoinPoint AJC$FIELDS = new SuppressedJoinPoint() {
        @Override
        public Predicate predicate() {
            return StandardPredicates.transformerPredicate(
                    StandardTransformers.transformerComposition(
                            AjpTransformers.fieldTransformer(),
                            MemberTransformers.nameTransformer()
                    ),
                    StringPredicates.startsWith(ASPECTJ_PREFIX)
            );
        }
    };

    /**
     * All methods and constructors of {@link java.lang.Object}.
     */
    public static SuppressedJoinPoint javaLangObject() {
        return JLO_ALL;
    }

    /**
     * Alias for {@link #javaLangObject()}.
     */
    public static SuppressedJoinPoint jlo() {
        return JLO_ALL;
    }

    /**
     * Selected Methods: {@link Object#equals(Object)}, {@link Object#hashCode()}, {@link Object#toString()} and {@link Object#getClass()}.
     */
    public static SuppressedJoinPoint jloStandardMethods() {
        return JLO_STANDARD;
    }

    /**
     * Selected Methods: {@link Object#notify()}, {@link Object#notifyAll()}, {@link Object#wait()},
     * {@link Object#wait(long, int)}  and {@link Object#wait(long)}.
     */
    public static SuppressedJoinPoint jloSyncMethods() {
        return JLO_SYNC_METHODS;
    }

    /**
     * Selected Methods: {@link Object#clone()}.
     */
    public static SuppressedJoinPoint jloClone() {
        return JLO_CLONE;
    }

    /**
     * Selected Methods: {@link Object#toString()}.
     */
    public static SuppressedJoinPoint jloToString() {
        return JLO_TO_STRING;
    }

    /**
     * Selected Methods: {@link Object#hashCode()}.
     */
    public static SuppressedJoinPoint jloHashCode() {
        return JLO_HASH_CODE;
    }

    /**
     * Selected Methods: {@link Object#equals(Object)}.
     */
    public static SuppressedJoinPoint jloEquals() {
        return JLO_EQUALS;
    }

    /**
     * Selected Methods: {@link Object#getClass()}.
     */
    public static SuppressedJoinPoint jloGetClass() {
        return JLO_GET_CLASS;
    }

    /**
     * Selected Methods: {@link Object#finalize()}.
     */
    public static SuppressedJoinPoint jloFinalize() {
        return JLO_FINALIZE;
    }

    /**
     * Constructor of {@link Object}.
     */
    public static SuppressedJoinPoint jloConstructor() {
        return JLO_CONSTRUCTOR;
    }

    /**
     * For enabling a specific AspectJ method starting with {@value #ASPECTJ_PREFIX}. Usually all AspectJ generated methods and fields will be ignored.
     * @param methodName the name without {@value #ASPECTJ_PREFIX}.
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

    /**
     * Suppress any method generated by AspectJ (starting with <code>ajc$</code>). Internal use only.
     */
    static SuppressedJoinPoint ajcMethods() {
        return AJC$METHODS;
    }

    /**
     * Suppress any field generated by AspectJ (starting with <code>ajc$</code>). Internal use only.
     */
    static SuppressedJoinPoint ajcFields() {
        return AJC$FIELDS;
    }

    private static Predicate anyMethodPredicate(String... names) {
        return LogicalPredicates.and(
                byDeclaringClassIsJavaLangObject(),
                byMethodName(names)
        );
    }

    private static Predicate byDeclaringClassIsJavaLangObject() {
        return StandardPredicates.transformerPredicate(
                AjpTransformers.declaringClassTransformer(),
                StandardPredicates.predicateEquals(Object.class)
        );
    }

    private static Predicate byMethodName(String... names) {
        final CompositePredicate byAnyMethodName = LogicalPredicates.or();
        for (String name : names) {
            byAnyMethodName.addPredicate(StandardPredicates.predicateEquals(name));
        }

        return StandardPredicates.transformerPredicate(
                    StandardTransformers.transformerComposition(
                        AjpTransformers.methodTransformer(),
                        MemberTransformers.nameTransformer()
                    ),
               byAnyMethodName
        );
    }
}
