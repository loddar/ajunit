ajunit
======

ajUnit - Unit Testing AspectJ.

Motivation
==========

    As you can see, the steps to identify join points and implement a pointcut dominate the process.
                                                                (Ramnivas Laddad - AspectJ in Action 2nd edition)


AspectJ is great tool. But it's difficult to develop an useful aspect. With [AJDT](http://www.eclipse.org/ajdt/) there is a tool which supports the development.
You can even see the potential selected join points - until you start using _cflow, cflowbelow or if_. And of course this supports only visual validation.

As Ramnivas explained in [AspectJ in Action](http://www.manning.com/laddad2/), an (IMHO the most) important part of a aspect it's the pointcut definition. So the _Where_ not the _What_
is in the center of developing an aspect.

Developing a pointcut is hard, but testing is even harder. A pointcut is not an executable statement. It has similarities to a SQL statement.
With a pointcut you can select the join points of your code base. But you can't execute the pointcut, cause it's done by AspectJ.

In a nutshell: Currently you can't write a simple test for a pointcut definition.

**ajUnit** is the attempt to close this gap.
