/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2013  Marko Umek (ajunit.contact(at)gmail.com)
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
package eval.org.aspectj.lang.subject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ExceptionHandlerTestSubject is responsible for ...
 */
public class ExceptionHandlerTestSubject {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerTestSubject.class);
    
    private int value=100;

    public void divideBy(int divisor) {
        try {
            value /= divisor;
        }
        catch (Exception ex) {
            LOGGER.info("Exception caught {}", ex.getMessage());
        }
    }
}
