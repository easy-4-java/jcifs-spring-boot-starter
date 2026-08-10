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

import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import jcifs.io.CopyStreamProcessListener;
import jcifs.smb.SmbFile2Config;

/**
 * Samba客户端的配置
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
@ConfigurationProperties(SmbProperties.PREFIX)
public class SmbProperties extends SmbFile2Config {

	public static final String PREFIX = "smb";
	
	/** 默认连接超时时间 ：30秒 */
	public static final int DEFAULT_CONNECT_TIMEOUT = 30 * 1000;
	/** 默认数据连接读取/发送数据超时时间 ：30秒 */
	public static final int DEFAULT_READ_TIMEOUT = 30 * 1000;
	/** 匿名账号：anonymous */
	public static final String ANONYMOUS_LOGIN = "anonymous";
	/** 默认缓存大小： 8M */
	public static final int DEFAULT_BUFFER_SIZE = 8 * 1024 * 1024;
	/** 默认FileChannel缓存大小： 2M */
	public static final int DEFAULT_CHANNEL_SIZE = 2 * 1024 * 1024;


	/** 【共享文件服务器】域名 */
	protected String domain;
	/** 【共享文件服务器】地址 */
	protected String host;
	/** 【共享文件服务器】用户名 */
	protected String username = ANONYMOUS_LOGIN;
	/** 【共享文件服务器】密码 ：注意事项：登陆服务器的密码不支持强密码（如密码含有 &……%￥# 等字符，都当成分隔符处理）*/
	protected String password;
	/** 【共享文件服务器】根共享目录*/
	protected String sharedDir;
	/** 启用或禁用用户交互（例如弹出一个验证对话框）的上下文中对此 URL 进行检查 */
	protected boolean allowUserInteraction = true;
	/** 启用或禁用数据流方式上传/下载时是否在缓冲发送/接收完成自动刷新缓存区；大文件上传下载时比较有用;默认false */
	protected boolean autoFlush = false;
	/** 数据流方式上传/下载时缓存区达到可自动刷新的最小阀值；仅当 autoflush 为true 时才有效；默认与默认缓存区大小相同即 8M */
	protected int autoFlushBlockSize = DEFAULT_BUFFER_SIZE;
	/** 内部缓冲区大小;默认 8M */
	protected int bufferSize = DEFAULT_BUFFER_SIZE;
	/** 文件通道读取缓冲区大小;默认 2M */
	protected int channelReadBufferSize = DEFAULT_CHANNEL_SIZE;
	/** 文件通道写出缓冲区大小;默认 2M */
	protected int channelWriteBufferSize = DEFAULT_CHANNEL_SIZE;
	/** 连接超时时间，单位为秒，默认30秒 */
	protected int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
	/** 进行存储时/检索操作时数据处理进度监听对象 */
	protected CopyStreamProcessListener copyStreamProcessListener;
	/** TCP进行存储时/检索操作时数据处理进度监听对象类路径 */
	protected String copyStreamProcessListenerName;
	/** 从数据读取的超时时间，单位（毫秒）；默认 30000 毫秒 */
	protected int readTimeout = DEFAULT_READ_TIMEOUT;
	/** 是否本地备份上传的文件：该方式有助于提高文件服务可用性，为用户下载文件省去网络开销 */
	protected boolean localBackupAble = false;
	/** 本地备份路径 ;默认userdir,如果开启了本地备份功能，建议指定该目录地址 */
	protected String localBackupDir = SystemUtils.getUserDir().getAbsolutePath();
	/** 是否使用Log4j记录命令信息,默认打印出命令，如果开启日志则关闭打印;默认 false */
	protected boolean logDebug = false;
	/** 启用或禁用在条件允许情况下允许协议使用缓存 */
	protected boolean usecaches = false;
	
}
