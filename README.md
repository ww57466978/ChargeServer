# Netty Server



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
   

    
