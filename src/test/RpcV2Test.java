import com.hxb.smart.rpc.api.HelloService;
import com.hxb.smart.rpcv2.RpcFactory;
import com.hxb.smart.rpcv2.configuration.RpcConfig;
import com.hxb.smart.rpcv2.core.invoker.reference.InvokerProxyBean;
import com.hxb.smart.rpcv2.core.invoker.router.impl.RandomRouter;
import com.hxb.smart.rpcv2.core.net.NetType;
import com.hxb.smart.rpcv2.registry.impl.LocalServiceRegistry;
import com.hxb.smart.rpcv2.serializer.impl.HessianSerializer;
import org.junit.Test;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-13 16:02:45
 */
public class RpcV2Test {

    @Test
    public void testRpc(){
        RpcConfig config = RpcConfig.builder()
                .basePackage("com.hxb")
                .netType(NetType.NETTY)
                .port(9090)
                .registryCenterAddress("0.0.0.0")
                .router(new RandomRouter())
                .serviceRegistry(new LocalServiceRegistry())
                .serializer(new HessianSerializer())
                .build();
        RpcFactory.builder().config(config).build();
        
    }
}
