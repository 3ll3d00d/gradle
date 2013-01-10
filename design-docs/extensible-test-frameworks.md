Preamble
========

The Gradle java plugin introduces the concept of a verification, or *check*, build phase
as part of it's make-verify-publish build cycle.

Gradle currently only provides out of the box support for the 2 most common (unit) test
frameworks on the JVM; [JUnit][] and [TestNG][]. It also provides a number of useful
execution time features that are not, or need not be, test framework specific. For example;

+ partitioning tests into n slices and distributed them across worker VMs for parallel execution
+ a standard human readable (HTML) test report format
+ a gradle DSL for describing what tests to execute in a given Test task invocation

Unfortunately the Test task is not currently truly open and extensible yet there are a number of
test frameworks that are in common use but not explicitly supported by gradle. This requires gradle users
to roll their own support for their chosen test framework(s) without access to the built in features.

Examples (of how it is closed) include;

+ the relevant classes (Test, TestFramework etc) are not explicitly part of gradle's
public API so may be subject to change
+ the Test task itself is extensible (i.e. it's not final) but adding a custom
TestFramework is impossible (as Test.testFramework is set in a private method)
+ how to go about implementing support for a new test framework is undocumented
+ tests are assumed to follow the typical junit model of a test being defined by a
single method in a test class and the HTML reporting is modelled on this approach

Intent
======

Describe the features that would enable gradle to allow users to implement support for their
chosen test framework(s) while leveraging gradle's test execution infrastructure.

Stories
=======

As a build master, I would like to use the test framework(s) of our choice in the
verification phase so that
+ I can present a consistent view (of test results) to release management irrespective
of which framework a specific build uses
+ unit, integration and performance test phases can be handled consistently

As a build master, I would like to use the test framework(s) of our choice without
having to reimplement fundamental test execution requirements (such as forking & reporting)
so that I can minimise the cost of introducing new frameworks.

As a gradle user, I would like support for "new" test frameworks to be achieved through
plugins alone so that the community can provide support for any chosen framework and so that the gradle
core is not bloated by support by each and every test framework.

Design
======

[junit]:  https://github.com/KentBeck/junit   "JUnit"
[testng]: http://testng.org/doc/index.html    "TestNG"