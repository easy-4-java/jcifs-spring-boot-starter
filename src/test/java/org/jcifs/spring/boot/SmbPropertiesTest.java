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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link SmbProperties}.
 *
 * <p>Verifies the {@code smb} configuration prefix, the public constants and the
 * default values that the constructor assigns to the inherited fields.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@DisplayName("SmbProperties Tests")
class SmbPropertiesTest {

    private SmbProperties properties;

    @BeforeEach
    void setUp() {
        properties = new SmbProperties();
    }

    @Test
    @DisplayName("Configuration prefix is 'smb'")
    void testPrefix() {
        assertThat(SmbProperties.PREFIX).isEqualTo("smb");
    }

    @Test
    @DisplayName("Public timeout/buffer constants have expected values")
    void testConstants() {
        assertThat(SmbProperties.DEFAULT_CONNECT_TIMEOUT).isEqualTo(30 * 1000);
        assertThat(SmbProperties.DEFAULT_READ_TIMEOUT).isEqualTo(30 * 1000);
        assertThat(SmbProperties.ANONYMOUS_LOGIN).isEqualTo("anonymous");
        assertThat(SmbProperties.DEFAULT_BUFFER_SIZE).isEqualTo(8 * 1024 * 1024);
        assertThat(SmbProperties.DEFAULT_CHANNEL_SIZE).isEqualTo(2 * 1024 * 1024);
    }

    @Test
    @DisplayName("Default username is the anonymous login")
    void testDefaultUsername() {
        assertThat(properties.getUsername()).isEqualTo(SmbProperties.ANONYMOUS_LOGIN);
    }

    @Test
    @DisplayName("Default buffer and channel sizes match the constants")
    void testDefaultBufferSizes() {
        assertThat(properties.getBufferSize()).isEqualTo(SmbProperties.DEFAULT_BUFFER_SIZE);
        assertThat(properties.getChannelReadBufferSize()).isEqualTo(SmbProperties.DEFAULT_CHANNEL_SIZE);
        assertThat(properties.getChannelWriteBufferSize()).isEqualTo(SmbProperties.DEFAULT_CHANNEL_SIZE);
        assertThat(properties.getAutoFlushBlockSize()).isEqualTo(SmbProperties.DEFAULT_BUFFER_SIZE);
    }

    @Test
    @DisplayName("Default timeouts match the constants")
    void testDefaultTimeouts() {
        assertThat(properties.getConnectTimeout()).isEqualTo(SmbProperties.DEFAULT_CONNECT_TIMEOUT);
        assertThat(properties.getReadTimeout()).isEqualTo(SmbProperties.DEFAULT_READ_TIMEOUT);
    }

    @Test
    @DisplayName("Default flags match the documented values")
    void testDefaultFlags() {
        assertThat(properties.isAllowUserInteraction()).isTrue();
        assertThat(properties.isAutoFlush()).isFalse();
        assertThat(properties.isLocalBackupAble()).isFalse();
        assertThat(properties.isLogDebug()).isFalse();
        assertThat(properties.isUsecaches()).isFalse();
    }

    @Test
    @DisplayName("Default localBackupDir resolves to the JVM working directory")
    void testDefaultLocalBackupDir() {
        assertThat(properties.getLocalBackupDir())
                .isEqualTo(org.apache.commons.lang3.SystemUtils.getUserDir().getAbsolutePath());
    }

    @Test
    @DisplayName("Connection settings can be overridden via the inherited setters")
    void testSetters() {
        properties.setDomain("DOMAIN");
        properties.setHost("smb.example.com");
        properties.setUsername("alice");
        properties.setPassword("secret");
        properties.setSharedDir("share");
        properties.setConnectTimeout(5000);
        properties.setReadTimeout(7000);
        properties.setBufferSize(2048);
        properties.setChannelReadBufferSize(1024);
        properties.setChannelWriteBufferSize(1024);
        properties.setAutoFlushBlockSize(512);
        properties.setAllowUserInteraction(false);
        properties.setAutoFlush(true);
        properties.setLocalBackupAble(true);
        properties.setLocalBackupDir("/tmp/backup");
        properties.setLogDebug(true);
        properties.setUsecaches(true);

        assertThat(properties.getDomain()).isEqualTo("DOMAIN");
        assertThat(properties.getHost()).isEqualTo("smb.example.com");
        assertThat(properties.getUsername()).isEqualTo("alice");
        assertThat(properties.getPassword()).isEqualTo("secret");
        assertThat(properties.getSharedDir()).isEqualTo("share");
        assertThat(properties.getConnectTimeout()).isEqualTo(5000);
        assertThat(properties.getReadTimeout()).isEqualTo(7000);
        assertThat(properties.getBufferSize()).isEqualTo(2048);
        assertThat(properties.getChannelReadBufferSize()).isEqualTo(1024);
        assertThat(properties.getChannelWriteBufferSize()).isEqualTo(1024);
        assertThat(properties.getAutoFlushBlockSize()).isEqualTo(512);
        assertThat(properties.isAllowUserInteraction()).isFalse();
        assertThat(properties.isAutoFlush()).isTrue();
        assertThat(properties.isLocalBackupAble()).isTrue();
        assertThat(properties.getLocalBackupDir()).isEqualTo("/tmp/backup");
        assertThat(properties.isLogDebug()).isTrue();
        assertThat(properties.isUsecaches()).isTrue();
    }

    @Test
    @DisplayName("Copy-stream listener hooks are mutable")
    void testListenerHooks() {
        properties.setCopyStreamProcessListenerName("org.example.MyListener");
        assertThat(properties.getCopyStreamProcessListenerName()).isEqualTo("org.example.MyListener");
        assertThat(properties.getCopyStreamProcessListener()).isNull();
    }
}
