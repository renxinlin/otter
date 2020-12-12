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

package com.alibaba.otter.shared.arbitrate.impl;

import com.alibaba.otter.shared.arbitrate.ArbitrateEventService;
import com.alibaba.otter.shared.arbitrate.impl.setl.ExtractArbitrateEvent;
import com.alibaba.otter.shared.arbitrate.impl.setl.LoadArbitrateEvent;
import com.alibaba.otter.shared.arbitrate.impl.setl.MainStemArbitrateEvent;
import com.alibaba.otter.shared.arbitrate.impl.setl.SelectArbitrateEvent;
import com.alibaba.otter.shared.arbitrate.impl.setl.TerminArbitrateEvent;
import com.alibaba.otter.shared.arbitrate.impl.setl.ToolArbitrateEvent;
import com.alibaba.otter.shared.arbitrate.impl.setl.TransformArbitrateEvent;

/**
 * 仲裁器事件service的默认实现
 *
 * 编程模型抽象(SEDA模型)
 *
 * 将并行化调度的串行/并行处理，进行隐藏，抽象了await/single的接口，整个调度称之为仲裁器。(有了这层抽象，不同的仲裁器实现可以解决同机房，异地机房的同步需求
 *
 *
 * await模拟object获取锁操作
 *
 * notify被唤醒后提交任务到thread pools
 *
 * single模拟object释放锁操作，触发下一个stage
 *
 *
 * 仲裁器算法 主要包括： 令牌生成(processId) +
 * 事件通知.
 * 每个stage都会有个block queue 接收上一个stage的single信号通知，当前stage会阻塞在该block queue上，直到有信号通知
 *   block queue + put/take方法，(纯内存机制)
 *   block queue + rpc + put/take方法 (两个stage对应的node不同，需要rpc调用，需要依赖负载均衡算法解决node节点的选择问题)
 *   block queue + zookeeper watcher ()
 * @author jianghang 2011-9-22 下午04:04:00
 * @version 4.0.0
 */
public class ArbitrateEventServiceImpl implements ArbitrateEventService {
    // todo 主线事件 暂时还不知道是干什么
    private MainStemArbitrateEvent  mainStemEvent;
    // otter通过select模块串行获取canal的批数据，注意是串行获取，每批次获取到的数据，就会有一个全局标识，otter里称之为processId.
    private SelectArbitrateEvent    selectEvent;
    private ExtractArbitrateEvent   extractEvent;
    private TransformArbitrateEvent transformEvent;
    // 将数据最后传递到Load时，会根据每批数据对应的processId，按照顺序进行串行加载
    private LoadArbitrateEvent      loadEvent;
    // ack事件
    private TerminArbitrateEvent    terminEvent;
    //
    private ToolArbitrateEvent      toolEvent;

    public MainStemArbitrateEvent mainStemEvent() {
        return mainStemEvent;
    }

    public SelectArbitrateEvent selectEvent() {
        return selectEvent;
    }

    public ExtractArbitrateEvent extractEvent() {
        return extractEvent;
    }

    public TransformArbitrateEvent transformEvent() {
        return transformEvent;
    }

    public LoadArbitrateEvent loadEvent() {
        return loadEvent;
    }

    public TerminArbitrateEvent terminEvent() {
        return terminEvent;
    }

    public ToolArbitrateEvent toolEvent() {
        return toolEvent;
    }

    // ================ setter / getter ====================

    public void setTransformEvent(TransformArbitrateEvent transformEvent) {
        this.transformEvent = transformEvent;
    }

    public void setMainStemEvent(MainStemArbitrateEvent mainStemEvent) {
        this.mainStemEvent = mainStemEvent;
    }

    public void setSelectEvent(SelectArbitrateEvent selectEvent) {
        this.selectEvent = selectEvent;
    }

    public void setExtractEvent(ExtractArbitrateEvent extractEvent) {
        this.extractEvent = extractEvent;
    }

    public void setLoadEvent(LoadArbitrateEvent loadEvent) {
        this.loadEvent = loadEvent;
    }

    public void setTerminEvent(TerminArbitrateEvent terminEvent) {
        this.terminEvent = terminEvent;
    }

    public void setToolEvent(ToolArbitrateEvent toolEvent) {
        this.toolEvent = toolEvent;
    }

}
