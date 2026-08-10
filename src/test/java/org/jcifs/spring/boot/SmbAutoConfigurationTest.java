/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.jcifs.spring.boot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

import jcifs.context.BaseContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link SmbAutoConfiguration}.
 *
 * <p>{@code baseContext()} initialises the {@link
 * jcifs.context.SingletonContext} which can only happen once per JVM, so it is
 * exercised through a single dedicated test. The remaining assertions use the
 * {@link ApplicationContextRunner} bound to {@link SmbProperties} only.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@DisplayName("SmbAutoConfiguration Tests")
class SmbAutoConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner();

    @Test
    @DisplayName("Auto-configuration class can be instantiated")
    void testInstantiation() {
        SmbAutoConfiguration configuration = new SmbAutoConfiguration();
        assertThat(configuration).isNotNull();
    }

    @Test
    @DisplayName("baseContext() returns the initialised singleton BaseContext")
    void testBaseContextBean() throws Exception {
        SmbAutoConfiguration configuration = new SmbAutoConfiguration();
        BaseContext context = configuration.baseContext();
        assertThat(context).isNotNull();
    }

    @Test
    @DisplayName("Properties bind to the 'smb' prefix via EnableConfigurationProperties")
    void testPropertyBinding() {
        runner.withUserConfiguration(PropertiesConfig.class)
                .withPropertyValues("smb.host=smb.example.com", "smb.username=alice", "smb.connectTimeout=5000")
                .run(context -> {
                    assertThat(context).hasSingleBean(SmbProperties.class);
                    SmbProperties properties = context.getBean(SmbProperties.class);
                    assertThat(properties.getHost()).isEqualTo("smb.example.com");
                    assertThat(properties.getUsername()).isEqualTo("alice");
                    assertThat(properties.getConnectTimeout()).isEqualTo(5000);
                });
    }

    @Test
    @DisplayName("Default property values are applied when nothing is configured")
    void testDefaultPropertyBinding() {
        runner.withUserConfiguration(PropertiesConfig.class)
                .run(context -> {
                    SmbProperties properties = context.getBean(SmbProperties.class);
                    assertThat(properties.getUsername()).isEqualTo(SmbProperties.ANONYMOUS_LOGIN);
                    assertThat(properties.getBufferSize()).isEqualTo(SmbProperties.DEFAULT_BUFFER_SIZE);
                });
    }

    /** Minimal configuration that only enables {@link SmbProperties} binding. */
    @Configuration
    @EnableConfigurationProperties(SmbProperties.class)
    static class PropertiesConfig {
    }
}
