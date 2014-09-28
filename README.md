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

1. Install [Java 8 JDK](http://www.oracle.com/technetwork/java/javase/downloads)
2. Install [Maven 3](http://maven.apache.org/download.cgi)
3. Download [ajUnit JARs](https://github.com/loddar/ajunit/tree/master/dist/org/failearly/ajunit/ajunit/0.1.0).
    * ajunit-0.1.0.jar
    * ajunit-0.1.0-sources.jar
    * ajunit-0.1.0-javadoc.jar
    * ajunit-0.1.0.pom
4. Install the JARs into your local maven repository by using 
    
        cd <path to jars>
        mvn install:install-file -Dfile=ajunit-0.1.0.jar -Dsources=ajunit-0.1.0-sources.jar  -Djavadoc=ajunit-0.1.0-javadoc.jar -pomFile=ajunit-0.1.0.pom
    

I suggest also to play a little bit with the ajUnit examples ...    

5. Download or clone [ajunit-examples](https://github.com/loddar/ajunit-examples).
6. Execute _mvn test_ on the parent project ajunit-examples 


TDD with ajUnit
===============

After the setup, your actually work starts. I recommend following steps:

1. Extend your test fixture class. 
For example define a method which should be weaved by the aspect and one which should not. This could be done by using your IDE quickly, 
if you call it in _execute()_ and leave the work to your IDE creating the method/constructor.
   
2. Call the new code within _execute()_. 

3. Define the join point selector.

4. Execute the test class. This should fail.

5. Extend the pointcut: _pointcutUnderTest()_.

6. Execute the test class again. If the test gets green you're done. If the test fails, correct the pointcut until it gets green.

7. If the your pointcut definition is not yet complete, start again with 1.

8. If your pointcut definition is not nice, you can start make it more concise/beautiful by extracting it into sub pointcuts, ... .

Glossary
========

### ajUnit Test 

Any (JUnit4) test which extends AjUnit4Test. This test class tests only one ajUnit aspect. An ajUnit test has three parts:

* _setup(AjUnitSetup ajUnitSetup)_
    - Assign the ajUnit aspect and 
    - add the test fixture classes.
    
* _execute()_
    - Executes the test fixture class constructors, methods, fields, ..., so that the ajUnit aspect's advice records the applied join points.
    
    
* _assertPointcut(JoinPointSelector joinPointSelector)_
    - Builds the appropriate _join point selector_. 
    - The created join point selector checks if the join points of your test fixture classes has been addressed by the ajUnit aspect.

### ajUnit Aspect 

The (concrete) aspect which contains the _pointcut under test_. This aspect must be assigned to the ajUnit test. 

The aspect must extend one of these abstract aspects:
 
* _AjUnitBeforeAnnotationAspect_ or _AjUnitAnnotationAspect_
* _AjUnitBeforeClassicAspect_ or _AjUnitClassicAspect_


### Test Fixture Classes

The aspect needs something to be applied on. These classes should be somehow artificial, not doing any real stuff, just doing enough, to be selected by
the pointcut under test.

If you test a database SQL query, your SQL query needs something to be applied on. These are almost some artificial test data which has been inserted by a tool
like [DbUnit](http://dbunit.sourceforge.net/).

The pointcut under test compares to the SQL query and the test fixture classes compares to test data.


### ajUnit Universe

The ajUnit test and aspect uses the ajUnit Universe.

1. The universe contains all potential join points. So by adding the test fixture classes, ajUnit adds
them by reflection. The ajUnit Universe is an artificial codebase.

2. When your are executing the test fixture classes, ajUnit records if the your test aspect (pointcut under test) has been executed.

3. The join point selector selects the join points within the ajUnit Universe.

4. An ajUnit Universe has a identifier (the aspect's full qualified class name), so it's possible to use the same test fixture classes for two or more tests.
    **But be careful: Doing this means that your tests are not independent.** 
 


Dependencies
============

* Java 8
* AspectJ 1.8
* JUnit 4
* SLF4J



Road map
========

Version | Planned | Content
------- | ------- | ----------------------------------------------------
0.2     | Q4/2014 | Constructor execution and call pointcut.  
0.3     | Q4/2014 | Initializer pointcuts.  
0.4     | 2015 | Field get and set pointcut. 
0.5     | 2015 | Exception handler pointcuts.  


Social
======

[Twitter](https://twitter.com/failearly)  
[LinkedIn](https://www.linkedin.com/in/markoumek)


Training Video
==============

tbd


License
=======

![GPL v3](http://www.gnu.org/graphics/gplv3-127x51.png)
