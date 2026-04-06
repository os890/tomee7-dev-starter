/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.os890.tomee7;

import java.util.HashMap;
import java.util.Map;

import jakarta.ejb.embeddable.EJBContainer;

/**
 * Utility class for starting an embedded TomEE (OpenEJB) container
 * during development. Provides convenience methods that configure
 * and launch the container so that classpath beans, EJBs and CDI
 * components are discovered automatically.
 *
 * <p>This is a development helper — the container runs in the same
 * JVM and blocks the calling thread until it is shut down.</p>
 */
public class EmbeddedStarter {

    /**
     * Starts an embedded TomEE container with the given project-stage
     * and HTTP port, scanning the full classpath.
     *
     * @param projectStage the Faces/DeltaSpike project stage
     *                     (e.g. {@code "Development"}, {@code "Production"})
     * @param context      the web-application context path (e.g. {@code "/"})
     * @param httpPort     the HTTP port the embedded server listens on
     */
    public static void startTomEE(String projectStage, String context, Integer httpPort) {
        startTomEE(projectStage, context, httpPort, null);
    }

    /**
     * Starts an embedded TomEE container with the given project-stage,
     * HTTP port, and optional package filter.
     *
     * <p>When {@code additionalInclude} is non-null it is passed as the
     * {@code openejb.container.additional.include} property so that only
     * matching packages are scanned.</p>
     *
     * @param projectStage    the Faces/DeltaSpike project stage
     * @param context         the web-application context path
     * @param httpPort        the HTTP port the embedded server listens on
     * @param additionalInclude optional comma-separated list of packages
     *                          to include during classpath scanning;
     *                          {@code null} means scan everything
     */
    public static void startTomEE(String projectStage, String context, Integer httpPort, String additionalInclude) {
        System.setProperty("faces.PROJECT_STAGE", projectStage);

        Map<String, Object> properties = new HashMap<>();
        properties.put(EJBContainer.PROVIDER, "tomee-embedded");
        properties.put("openejb.embedded.remotable", "true");
        properties.put("httpejbd.port", String.valueOf(httpPort));
        properties.put("openejb.jul.forceReload", "true");

        if (additionalInclude != null) {
            properties.put("openejb.container.additional.include" /*not a bug - just a strange naming*/,
                    additionalInclude);
            properties.put("tomee.embedded.filter-container-classes", "true");
        }

        try (EJBContainer container = EJBContainer.createEJBContainer(properties)) {
            container.getContext().bind("inject", context);
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to start embedded container", e);
        }
    }
}
