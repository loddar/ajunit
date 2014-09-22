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
package com.company.project.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.failearly.ajunit.aspect.annotation.AjUnitBeforeAnnotationAspect;

/**
 * InvalidGetterAnnotationAspect provides the pointcut under test.
 */
@Aspect
public class InvalidGetterAnnotationAspect extends AjUnitBeforeAnnotationAspect {
    @Pointcut("call(public (void || *[] || *[][] || java.util.Collection+ || java.util.Map+) get*()) || call(public * get*(*,..)) || call(public * get*() throws java.lang.Throwable+)" )
    protected void pointcutUnderTest() {}
}
