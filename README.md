ajunit
======

ajUnit - Unit Testing AspectJ.

Motivation
==========

    As you can see, the steps to identify join points 
    and implement a pointcut dominate the process.
        (Ramnivas Laddad - AspectJ in Action 2nd edition)


AspectJ is great tool. But it's difficult to develop an useful aspect. With [AJDT](http://www.eclipse.org/ajdt/) there is a tool which supports the development.
You can even see the potential selected join points - until you start using _cflow, cflowbelow or if_. And of course this supports only visual validation.

As Ramnivas explained in [AspectJ in Action](http://www.manning.com/laddad2/), an (IMHO the most) important part of a aspect it's the pointcut definition. 
So the _Where_ not the _What_ is in the center of developing an aspect.

Developing a pointcut is hard, but testing is even harder. A pointcut is not an executable statement. It has similarities to a SQL statement.
With a pointcut you can select the join points of your code base. But you can't execute a pointcut, because it's an instruction for AspectJ's aspect weaver.

In a nutshell: Currently you can't write a simple test for a pointcut definition.

**ajUnit** is the attempt to close this gap.


First Steps
===========

tbd.


Roadmap
=======

Version | Planned | Content
------- | ------- | -------------------------------------------------
0.1     | Q3/2014 | Initial version. Method execution and call pointcut.
0.2     | Q4/2014 | Constructor execution and call pointcut.  
0.3     | Q4/2014 | Initializer pointcuts.  
0.4     | Q4/2014 | Field get and set pointcut. 
0.5     | Q4/2014 | Exception handler pointcuts.  


License
=======

![GPL v3](http://www.gnu.org/graphics/gplv3-127x51.png)
