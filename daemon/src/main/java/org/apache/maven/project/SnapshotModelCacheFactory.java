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
package org.apache.maven.project;

import org.apache.maven.api.di.Inject;
import org.apache.maven.api.di.Named;
import org.apache.maven.api.di.Priority;
import org.apache.maven.api.di.Singleton;
import org.apache.maven.api.services.model.ModelCache;
import org.apache.maven.api.services.model.ModelCacheFactory;
import org.apache.maven.internal.impl.model.DefaultModelCacheFactory;

import static org.mvndaemon.mvnd.common.Environment.MVND_NO_MODEL_CACHE;

@Singleton
@Named
@Priority(10)
public class SnapshotModelCacheFactory implements ModelCacheFactory {

    private final ModelCacheFactory factory;
    private final ModelCache globalCache;

    @Inject
    public SnapshotModelCacheFactory(DefaultModelCacheFactory factory) {
        this.factory = factory;
        this.globalCache = factory.newInstance();
    }

    @Override
    public ModelCache newInstance() {
        boolean noModelCache =
                Boolean.parseBoolean(MVND_NO_MODEL_CACHE.asOptional().orElse(MVND_NO_MODEL_CACHE.getDefault()));
        ModelCache reactorCache = factory.newInstance();
        ModelCache globalCache = noModelCache ? reactorCache : this.globalCache;
        return new SnapshotModelCache(globalCache, reactorCache);
    }
}
