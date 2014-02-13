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

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link EnumUtils}.
 */
public class EnumUtilsTest {
    /**
     * The enum for the tests.
     */
    private enum MyEnum {
        VALID_1,
        VALID_2 {
            @Override
            public String toString() {
                return "valid2";
            }
        },
        @SuppressWarnings("unused")
        _INVALID,
        _UNKNOWN
    }

    @Test
    public void testResolveValidEnums() throws Exception {
        // act / when
        final Map<String,MyEnum> validEnums = EnumUtils.resolveValidEnums(MyEnum.class);

        // assert / then
        assertThat("#Valid enum value(s)?", validEnums.keySet(), hasSize(2));
        assertThat("Valid enum values does not start with _?",
                validEnums.keySet(), containsInAnyOrder("VALID_1", "valid2"));
        assertThat("Valid enum values does not start with _?",
                validEnums.values(), containsInAnyOrder(MyEnum.VALID_1, MyEnum.VALID_2));
    }

    @Test
    public void testDefaultIfNotFound() throws Exception {
        // arrange / given
        final Map<String,MyEnum> validEnums = EnumUtils.resolveValidEnums(MyEnum.class);

        // assert / then
        assertThat("Unexpected value uses default?",
                EnumUtils.defaultIfNotFound(validEnums, "anyUnknownValue", MyEnum._UNKNOWN),
                is(MyEnum._UNKNOWN));
        assertThat("Valid enum ignores default?",
                EnumUtils.defaultIfNotFound(validEnums, "VALID_1", MyEnum._UNKNOWN),
                is(MyEnum.VALID_1));
        assertThat("Valid enum ignores default?",
                EnumUtils.defaultIfNotFound(validEnums, "valid2", MyEnum._UNKNOWN),
                is(MyEnum.VALID_2));
        assertThat("Invalid enum uses default?",
                EnumUtils.defaultIfNotFound(validEnums, "_INVALID", MyEnum._UNKNOWN),
                is(MyEnum._UNKNOWN));
    }

    @Test
    public void invalidEnumValuesStartWithUnderline() throws Exception {
        assertThat("Invalid?", EnumUtils.isValidEnumValue(MyEnum._UNKNOWN), is(false));
        assertThat("Valid?", EnumUtils.isValidEnumValue(MyEnum.VALID_1), is(true));
    }
}
