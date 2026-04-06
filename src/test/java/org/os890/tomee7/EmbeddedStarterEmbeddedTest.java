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

import javax.naming.Context;

import jakarta.ejb.embeddable.EJBContainer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration tests that boot an embedded OpenEJB (TomEE) container
 * and verify the container starts and can serve JNDI lookups.
 *
 * <p>These tests run in the {@code tomee-embedded} Maven profile
 * which places {@code openejb-core} on the classpath.</p>
 */
class EmbeddedStarterEmbeddedTest {

    /**
     * Verifies that an embedded EJB container can be created
     * and shut down without errors.
     */
    @Test
    void containerStartsAndStops() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(EJBContainer.PROVIDER, "openejb");

        try (EJBContainer container = EJBContainer.createEJBContainer(properties)) {
            assertNotNull(container, "EJBContainer should be created");
        }
    }

    /**
     * Verifies that the JNDI {@link Context} is available from
     * the embedded container.
     */
    @Test
    void jndiContextIsAvailable() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(EJBContainer.PROVIDER, "openejb");

        try (EJBContainer container = EJBContainer.createEJBContainer(properties)) {
            Context ctx = container.getContext();
            assertNotNull(ctx, "JNDI context should be available");
        }
    }

    /**
     * Verifies that the {@link EmbeddedStarter} class is loadable
     * when the embedded runtime is on the classpath.
     */
    @Test
    void embeddedStarterClassIsLoadable() {
        assertDoesNotThrow(() -> Class.forName("org.os890.tomee7.EmbeddedStarter"),
                "EmbeddedStarter should be loadable");
    }
}
