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
package eval.org.aspectj.lang.fixture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * StaticInitializationTestFixture is responsible for ...
 */
public class StaticInitializationTestFixture {

    public static final Logger LOGGER= LoggerFactory.getLogger(StaticInitializationTestFixture.class);

    public static final  Object ANY_CONSTANT = null;

    static {
        LOGGER.debug("Class StaticInitializationTestFixture static block executed");
    }
}
