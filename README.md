# ChargeServer
the server for client of chaegedevice



基于netty构建一个充电设备的服务端


selector.select()

pipeline.fireChannelRead()

     new DefaultChannelPipeline(){
         tail = new TailContext(this);
            head = new HeadContext(this);
        
            head.next = tail;
            tail.prev = head;
        
            ChannelInitializer(){
                initChannel(ctx){
                    ...
                    finally{
                        remove(ctx)    
                    }
                  }
            }
     }
   

    