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

import jakarta.enterprise.inject.spi.CDI;

import org.junit.jupiter.api.Test;
import org.os890.cdi.addon.dynamictestbean.EnableTestBeans;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * CDI-based test for {@link EmbeddedStarter} that verifies the CDI
 * container boots correctly and the bean manager is available.
 *
 * <p>Uses the
 * <a href="https://github.com/os890/dynamic-cdi-test-bean-addon">
 * Dynamic CDI Test Bean Addon</a> for automatic CDI SE bootstrap.</p>
 */
@EnableTestBeans
class EmbeddedStarterCdiTest {

    /**
     * Verifies that the CDI container starts and a
     * {@link jakarta.enterprise.inject.spi.BeanManager} is available.
     */
    @Test
    void cdiContainerBoots() {
        assertNotNull(CDI.current().getBeanManager(),
                "BeanManager should be available from CDI container");
    }

    /**
     * Verifies that the {@link EmbeddedStarter} class is loadable
     * from the CDI container classpath.
     */
    @Test
    void embeddedStarterClassIsAvailable() {
        assertNotNull(EmbeddedStarter.class,
                "EmbeddedStarter class should be on classpath");
    }
}
