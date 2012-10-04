/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.gradle.testing.testng

import org.gradle.integtests.fixtures.AbstractIntegrationSpec
import org.gradle.integtests.fixtures.JUnitTestExecutionResult
import org.gradle.integtests.fixtures.TestNGExecutionResult

public class TestNGProducesOldReportsIntegrationTest extends AbstractIntegrationSpec {

    def "produces old and new report"() {
        given:
        file("src/test/java/org/MixedMethodsTest.java") << """package org;
import org.testng.*;
import org.testng.annotations.*;
import static org.testng.Assert.*;

public class MixedMethodsTest {
    @Test public void passing() {
    }
    @Test public void failing() {
        fail("failing!");
    }
}
"""
        def buildFile = file('build.gradle')
        buildFile << """
apply plugin: 'java'
repositories { mavenCentral() }
dependencies { testCompile 'org.testng:testng:6.3.1' }

test {
    useTestNG()
}
"""
        when:
        executer.withTasks('test').runWithFailure()

        then:
        def junitResult = new JUnitTestExecutionResult(file("."));
        junitResult.assertTestClassesExecuted("org.MixedMethodsTest")

        def testNGResult = new TestNGExecutionResult(file("."))
        testNGResult.assertTestClassesExecuted("org.MixedMethodsTest")
    }

    def "can prevent generating the old report"() {
        given:
        file("src/test/java/org/SomeTest.java") << """package org;
import org.testng.annotations.*;

public class SomeTest {
    @Test public void passing() {}
}
"""
        def buildFile = file('build.gradle')
        buildFile << """
apply plugin: 'java'
repositories { mavenCentral() }
dependencies { testCompile 'org.testng:testng:6.3.1' }
test {
  useTestNG {
    useDefaultListeners = false
  }
}
"""
        when:
        executer.withTasks('test').run()

        then:
        new JUnitTestExecutionResult(file(".")).hasJUnitXmlResults()

        def result = new TestNGExecutionResult(file("."))
        !result.hasTestNGXmlResults()
        result.hasHtmlResults()
    }

    def "can prevent generating the old and new report"() {
        given:
        file("src/test/java/org/SomeTest.java") << """package org;
import org.testng.annotations.*;

public class SomeTest {
    @Test public void passing() {}
}
"""
        def buildFile = file('build.gradle')
        buildFile << """
apply plugin: 'java'
repositories { mavenCentral() }
dependencies { testCompile 'org.testng:testng:6.3.1' }
test {
  useTestNG {
    useDefaultListeners = false
    testReport = false
  }
}
"""
        when:
        executer.withTasks('test').run()

        then:
        new JUnitTestExecutionResult(file(".")).hasJUnitXmlResults()

        def result = new TestNGExecutionResult(file("."))
        !result.hasTestNGXmlResults()
        !result.hasHtmlResults()
    }
}