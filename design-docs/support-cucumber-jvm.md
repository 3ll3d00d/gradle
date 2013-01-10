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



Design
======

[Cucumber]:     http://cukes.info/                       "Cucumber"
[Cucumber-JVM]: https://github.com/cucumber/cucumber-jvm "Cucumber-JVM"