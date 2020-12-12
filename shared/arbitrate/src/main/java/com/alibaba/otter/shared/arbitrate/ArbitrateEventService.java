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

package com.alibaba.otter.shared.arbitrate;

import com.alibaba.otter.shared.arbitrate.impl.setl.ExtractArbitrateEvent;
import com.alibaba.otter.shared.arbitrate.impl.setl.LoadArbitrateEvent;
import com.alibaba.otter.shared.arbitrate.impl.setl.MainStemArbitrateEvent;
import com.alibaba.otter.shared.arbitrate.impl.setl.SelectArbitrateEvent;
import com.alibaba.otter.shared.arbitrate.impl.setl.TerminArbitrateEvent;
import com.alibaba.otter.shared.arbitrate.impl.setl.ToolArbitrateEvent;
import com.alibaba.otter.shared.arbitrate.impl.setl.TransformArbitrateEvent;

/**
 * 仲裁器事件处理的service，使用者可关注相应的await/single两个方法
 * 
 * @author jianghang 2011-8-9 下午04:39:49
 */
public interface ArbitrateEventService {
    /**
     * 主线仲裁 竞争 判断是否为活动节点使用
     * @return
     */
    public MainStemArbitrateEvent mainStemEvent();

    /**
     * select 仲裁
     * @return
     */
    public SelectArbitrateEvent selectEvent();

    public ExtractArbitrateEvent extractEvent();

    public TransformArbitrateEvent transformEvent();

    public LoadArbitrateEvent loadEvent();

    /**
     * load 完毕后signal 向select ack
     * @return
     */
    public TerminArbitrateEvent terminEvent();

    /**
     * 辅助工具节点
     * @return
     */
    public ToolArbitrateEvent toolEvent();
}
