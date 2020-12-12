/*
 * Copyright (C) 2010-2101 Alibaba Group Holding Limited.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.otter.node.etl.common.pipe;

import com.alibaba.otter.node.etl.common.pipe.exception.PipeException;

/**
 * S.E.T.L模块之间的数据交互工具
 *
 *
 *
 *
 *
 *
 * 注意:SEDA解决了数据流转的模块解耦问题 pipe解决了数据传输问题[一般同机房基于内存 异地基于rpc]
 *
 *
 *
 *
 *
 * 其方式是拉取
 * 而不是推送
 * 并且为了解决带宽用了batch方式 参见tcp流量传递
 *
 *
 * 有了一层SEDA调度模型的抽象，S/E/T/L模块之间互不感知，那几个模块之间的数据传递，需要有一个机制来处理，这里抽象了一个pipe(管道)的概念.
 *
 *  原理：
 *
 *  stage | pipe | stage
 *
 *  基于pipe实现：
 *
 *    in memory (两个stage经过仲裁器调度算法选择为同一个node时，直接使用内存传输)
 *    rpc call (<1MB)
 *    file(gzip) + http多线程下载
 *    在pipe中，通过对数据进行TTL控制，解决TCP协议中的丢包问题控制.
 * @author jianghang 2011-10-10 下午04:48:44
 * @version 4.0.0
 */
public interface Pipe<T, KEY extends PipeKey> {

    /**
     * 向管道中添加数据
     * 
     * @param data
     */
    public KEY put(T data) throws PipeException;

    /**
     * 通过key获取管道中的数据
     * 
     * @param key
     * @return
     */
    public T get(KEY key) throws PipeException;
}
