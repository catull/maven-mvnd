/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.mvndaemon.mvnd.it;

import javax.inject.Inject;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.mvndaemon.mvnd.assertj.TestClientOutput;
import org.mvndaemon.mvnd.client.Client;
import org.mvndaemon.mvnd.junit.MvndNativeTest;
import org.mvndaemon.mvnd.junit.MvndTestExtension;

@MvndNativeTest(projectDir = MvndTestExtension.TEMP_EXTERNAL)
public class CompletionNativeIT {

    @Inject
    Client client;

    @Test
    void completionBash() throws IOException, InterruptedException {
        final TestClientOutput output = new TestClientOutput();

        client.execute(output, "--completion", "bash").assertSuccess();

        output.assertContainsMatchingSubsequence("mvnd_opts=\"[^\"]*-1[^\"]*\"");
        output.assertContainsMatchingSubsequence("mvnd_long_opts=\"[^\"]*--purge[^\"]*\"");
        output.assertContainsMatchingSubsequence("mvnd_properties=\"[^\"]*-Dmvnd.debug[^\"]*\"");
    }

    protected boolean isNative() {
        return true;
    }
}
