# NettyTree  搭建一个基于Netty的通信框架
### NIO：非阻塞式IO
![](https://i.imgur.com/kfLrsEX.png)

### BIO：阻塞式IO

![](https://i.imgur.com/pexdC02.png)

<pre>
  1) TCP粘包、拆包
  2）编解码技术
     1）Java序列化
     2）业界主流的编解码框架 
           Thrift Protobuf
  3) Websocket
  5）Netty协议栈功能设计
  6）Netty源码分析
        ByteBuf工作原理
        Channel, Unsafe
        ChannelPipline, ChannelHandler
        EventLoop, EventLoopGroup
        Future, Promise
  7) Netty逻辑架构
  8）Netty中的多线程编程
  9）Netty与RPC
  10）Netty的可靠性
</pre>

<pre>
Reactor模型：基于事件驱动，适合处理海量I/O事件

　　　　1）单线程模型，所有的IO操作都在一个NIO线程上完成
　　　　   存在性能和可靠性上的问题
　　　　2）多线程模型，有一组NIO线程处理IO操作
　　　　   有一个专门的NIO线程-Acceptor线程用于监听服务端，接收客户端的TCP连接请求；
　　　　   有一个NIO线程池，负责消息的读取、发送、编码、解码；
　　　　   一个NIO线程能负责N条链路，一条链路只能由一个线程负责（防止发生并发操作问题）
　　　　3）主从多线程模型
　　　　   添加主线程池用于处理客户端的连接请求，一旦链路建立成功（经过握手、认证等过程）
          ，就将链路注册到从线程池的IO线程上，由IO线程负责后续的IO操作
</pre>

![](https://i.imgur.com/GsuJ9Wd.png)

Netty是典型的Reactor模型结构

![](https://i.imgur.com/OEGTY5Q.png)