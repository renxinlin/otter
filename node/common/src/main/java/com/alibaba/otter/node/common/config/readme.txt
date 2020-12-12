这里的config不是元信息配置
二是值manager上配置的node信息




这套设计说明:
节点是在Manager上面管理的，但是Node节点实际上是需要与其他的Node节点及manager通讯的，
因此NodeList（Group内的其他节点）的信息在Node节点是需要相互知道的。 Otter采用的是类似于Lazy+cache的模式管理的。即：
  真正使用到的时候再考虑去Manager节点取过来；
  取过来以后暂存到本地内存，但是伴随着一个失效机制[失效机制的检查是不单独占用线程的 ]