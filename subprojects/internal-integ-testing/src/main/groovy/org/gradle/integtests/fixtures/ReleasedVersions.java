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

package org.gradle.integtests.fixtures;

import org.gradle.api.Transformer;
import org.gradle.integtests.fixtures.executer.GradleDistribution;
import org.gradle.integtests.fixtures.executer.IntegrationTestBuildContext;
import org.gradle.integtests.fixtures.versions.VersionsInfo;
import org.gradle.test.fixtures.file.TestDirectoryProvider;
import org.gradle.util.CollectionUtils;

import java.util.List;

import static org.gradle.util.GradleVersion.version;

/**
 * by Szczepan Faber, created at: 1/12/12
 */
public class ReleasedVersions {

    private final static List<String> VERSIONS = new VersionsInfo().getVersions();
    private final IntegrationTestBuildContext buildContext = new IntegrationTestBuildContext();
    private final TestDirectoryProvider testDirectoryProvider;

    public ReleasedVersions(TestDirectoryProvider testDirectoryProvider) {
        this.testDirectoryProvider = testDirectoryProvider;
    }

    /**
     * @return last released version. Never returns the RC.
     */
    public GradleDistribution getLast() {
        for (String v : VERSIONS) {
            if (!version(v).isSnapshot()) {
                return buildContext.distribution(v);
            }
        }
        throw new RuntimeException("Unable to get the last version");
    }

    public List<GradleDistribution> getAll() {
        return CollectionUtils.collect(VERSIONS, new Transformer<GradleDistribution, String>() {
            public GradleDistribution transform(String original) {
                return buildContext.distribution(original);
            }
        });
    }
}
