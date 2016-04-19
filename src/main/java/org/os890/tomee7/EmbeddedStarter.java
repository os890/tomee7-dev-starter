/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.os890.tomee7;

import org.apache.tomee.embedded.Configuration;
import org.apache.tomee.embedded.Container;

import java.io.File;

@SuppressWarnings("unused")
public class EmbeddedStarter {
    /*
     //without TOMEE-1784 the following is needed for init openjpa
     static {
       Agent.getInstrumentation();
       PersistenceBootstrap.bootstrap(ClassLoader.getSystemClassLoader());
     }
     */
    public static void startTomEE(String projectStage, String context, Integer httpPort) {
        startTomEE(projectStage, context, httpPort, null);
    }

    public static void startTomEE(String projectStage, String context, Integer httpPort, String excludedPackages) {
        System.setProperty("faces.PROJECT_STAGE", projectStage);

        if (excludedPackages != null) {
            try (final Container container = new Container(
                    new Configuration()
                            .http(httpPort)
                            .property("openejb.jul.forceReload", "true")
                            .property("tomee.embedded.filter-container-classes", "true")
                            .property("openejb.container.additional.include" /*not a bug - just a strange naming*/, excludedPackages))
            .deployClasspathAsWebApp(context, new File("src/main/webapp"), true)) {
                container.await();
            }
        } else {
            try (final Container container = new Container(
                    new Configuration()
                            .http(httpPort)
                            .property("openejb.jul.forceReload", "true"))
                    .deployClasspathAsWebApp(context, new File("src/main/webapp"), true)) {
                container.await();
            }
        }
    }
}
