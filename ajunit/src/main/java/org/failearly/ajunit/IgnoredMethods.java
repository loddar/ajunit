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
import org.failearly.ajunit.internal.transformer.ajp.AjpTransformers;
import org.failearly.ajunit.internal.transformer.member.MemberTransformers;
import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;

/**
 * IgnoredMethods provides factory methods for {@link org.failearly.ajunit.IgnoredMethod}.
 *
 * @see AjUnitSetup
 */
public final class IgnoredMethods {

    private static final IgnoredMethod JLO_ALL = new IgnoredMethod() {
        @Override
        public Predicate predicate() {
            return byDeclaringClassIsJavaLangObject();
        }
    };

    private static final IgnoredMethod JLO_STANDARD = new IgnoredMethod() {
        @Override
        public Predicate predicate() {
            return anyMethodPredicate("equals", "hashCode", "toString", "getClass");
        }
    };

    private static final IgnoredMethod JLO_SYNC_METHODS = new IgnoredMethod() {
        @Override
        public Predicate predicate() {
            return anyMethodPredicate("notify", "notifyAll", "wait");
        }
    };

    private static final IgnoredMethod JLO_CLONE = new IgnoredMethod() {
        @Override
        public Predicate predicate() {
            return anyMethodPredicate("clone");
        }
    };

    private static final IgnoredMethod JLO_TO_STRING = new IgnoredMethod() {
        @Override
        public Predicate predicate() {
            return anyMethodPredicate("toString");
        }
    };

    private static final IgnoredMethod JLO_HASH_CODE = new IgnoredMethod() {
        @Override
        public Predicate predicate() {
            return anyMethodPredicate("hashCode");
        }
    };

    private static final IgnoredMethod JLO_EQUALS = new IgnoredMethod() {
        @Override
        public Predicate predicate() {
            return anyMethodPredicate("equals");
        }
    };

    private static final IgnoredMethod JLO_GET_CLASS = new IgnoredMethod() {
        @Override
        public Predicate predicate() {
            return anyMethodPredicate("getClass");
        }
    };

    private static final IgnoredMethod JLO_FINALIZE = new IgnoredMethod() {
        @Override
        public Predicate predicate() {
            return anyMethodPredicate("finalize");
        }
    };

    private static final IgnoredMethod JLO_CONSTRUCTOR = new IgnoredMethod() {
        @Override
        public Predicate predicate() {
            return StandardPredicates.transformerPredicate(
                    StandardTransformers.transformerComposition(
                            AjpTransformers.constructorTransformer()
                    ),
                    StandardPredicates.predicateNotNull()
            );
        }
    };

    /**
     * All methods and constructors of {@link java.lang.Object}.
     */
    public static IgnoredMethod javaLangObject() {
        return JLO_ALL;
    }

    /**
     * Alias for {@link #javaLangObject()}.
     */
    public static IgnoredMethod jlo() {
        return JLO_ALL;
    }

    /**
     * Selected Methods: {@link Object#equals(Object)}, {@link Object#hashCode()}, {@link Object#toString()} and {@link Object#getClass()}.
     */
    public static IgnoredMethod jloStandardMethods() {
        return JLO_STANDARD;
    }

    /**
     * Selected Methods: {@link Object#notify()}, {@link Object#notifyAll()}, {@link Object#wait()},
     * {@link Object#wait(long, int)}  and {@link Object#wait(long)}.
     */
    public static IgnoredMethod jloSyncMethods() {
        return JLO_SYNC_METHODS;
    }

    /**
     * Selected Methods: {@link Object#clone()}.
     */
    public static IgnoredMethod jloClone() {
        return JLO_CLONE;
    }

    /**
     * Selected Methods: {@link Object#toString()}.
     */
    public static IgnoredMethod jloToString() {
        return JLO_TO_STRING;
    }

    /**
     * Selected Methods: {@link Object#hashCode()}.
     */
    public static IgnoredMethod jloHashCode() {
        return JLO_HASH_CODE;
    }

    /**
     * Selected Methods: {@link Object#equals(Object)}.
     */
    public static IgnoredMethod jloEquals() {
        return JLO_EQUALS;
    }

    /**
     * Selected Methods: {@link Object#getClass()}.
     */
    public static IgnoredMethod jloGetClass() {
        return JLO_GET_CLASS;
    }

    /**
     * Selected Methods: {@link Object#finalize()}.
     */
    public static IgnoredMethod jloFinalize() {
        return JLO_FINALIZE;
    }

    /**
     * Constructor of {@link Object}.
     */
    public static IgnoredMethod jloConstructor() {
        return JLO_CONSTRUCTOR;
    }

    private static Predicate anyMethodPredicate(String... names) {
        return LogicalPredicates.and()
                    .addPredicate(byDeclaringClassIsJavaLangObject())
                    .addPredicate(byMethodName(names));
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
