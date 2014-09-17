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
package any.company.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.failearly.ajunit.AjUnitAnnotationAspect;
import org.failearly.ajunit.AjUniverseName;

/**
 * MissingBaseAspectAspect is responsible for ...
 */
@Aspect
@AjUniverseName("testUniverse")
public class CorrectAjUnitClassicAspect extends AjUnitAnnotationAspect {

    @Pointcut("allMethodExecution()")
    protected void pointcutUnderTest() {}

    @Before("pointcutDefinition()")
    public void beforeSelectedJoinPoint(JoinPoint.StaticPart thisJoinPointStaticPart, JoinPoint.EnclosingStaticPart thisEnclosingJoinPointStaticPart) {
        super.applyJoinPoint(thisJoinPointStaticPart, thisEnclosingJoinPointStaticPart);
    }
}
