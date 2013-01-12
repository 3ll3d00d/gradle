Preamble
========

[Cucumber][] is a commonly used BDD tool that was originally implemented in Ruby. It has
recently been re-implemented as [Cucumber-JVM][] which is an implementation written in,
and with support for, the common JVM languages (Java, Groovy, Scala, JRuby, Clojure, Jython) and
DI frameworks (guice, spring, pico etc).

[Cucumber-JVM][] provides 2 ways to execute features;

+ a cli interface
+ a JUnit runner

Both have problems when used from gradle;

+ the cli interface requires custom integration and has no ability to share
the existing gradle test execution & reporting infrastructure
+ Cucumber features don't fit the junit "test class owns 1-n test methods"
format that gradle supports so information is lost when the cucumber results are
translated to junit

Intent
======

Describe how gradle can promote cucumber-jvm to the status of a 1st class "test framework" as per
TestNG and JUnit.

Stories
=======

As a QA engineer, I want a large suite of integration tests to use all available compute resources so that
integration tests can be executed continuously and results are available in a more timely fashion.


As a build master, I want both unit and integration testing to be executed by gradle in a consistent
fashion so that I can minimise the build customisation required to run either type of test.


As a QA lead or release manager, I want to a single consolidated view on the health of our software.

Implementation Alternatives
===========================

Use the Cucumber-JVM CLI Directly
---------------------------------

Pros

+ Easy to implement

Cons

+ Unable to leverage the gradle test execution infrastructure
+ Requires custom configuration & replacement of existing test phase
+ Impossible to produce consistent reports

Use the Cucumber-JVM JUnit Runner
---------------------------------

Pros

+ Based on existing gradle junit support (requires some fixes to reporting)

Cons

+ Runner is declared using an annotation so every possible combination of cucumber execution options (tags etc) must be statically declared (can make, e.g., CI integration more difficult)
+ Step success/failure is hidden, available in the stdout only

Model Cucumber-JVM as a gradle test framework
---------------------------------------------

Pros

+ Provides consistent experience across all test frameworks
+ Enables cucumber to leverage all the gradle test execution infrastructure

Cons

+ gradle test framework is based on suite/class/method hence impedence mismatch to cucumber scenario/feature/step, particularly with respect to "test detection"
+ implementation effort

Design
======

Creating a New Test Framework
-----------------------------

 add class diagram to show how things link together


Differences between cucumber and class based frameworks
-------------------------------------------------------

 specify which bits of the junit/testng approach are not necessarily required because cucumber-jvm implements it

OR

 show how gradle will detect features and execute them directly rather than letting cucumber discover them

DSL Integration
---------------

 describe the cucumber-jvm configuration options, i.e. cli args


Parallel Execution
------------------

 fork by tag?


Invoking cucumber-jvm
---------------------

 what a typical invocation of cli looks like


Test Result Reporting
---------------------

 what formatter will we provide to cucumber-jvm
 how will it account for scenario - feature - step hierarchy
 talk about each possible setup (scenario outline etc)


[Cucumber]:     http://cukes.info/                       "Cucumber"
[Cucumber-JVM]: https://github.com/cucumber/cucumber-jvm "Cucumber-JVM"