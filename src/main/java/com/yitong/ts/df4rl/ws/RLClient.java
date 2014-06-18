package com.yitong.ts.df4rl.ws;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.dynamic.DynamicClientFactory;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

/**
 * Webservice客户端<br/>
 * create:2014-06-18
 *
 * @author puxiwu
 */
public class RLClient {

    private RLClient() {
    }

    private String code;       //鉴权Code
    private String password;   //鉴权密码

    private DynamicClientFactory clientFactory;


    public void setCode(String code) {
        this.code = code;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 根据指定的url创建客户端
     *
     * @param wsdlUrl wsdl地址
     * @return 客户端
     */
    public static RLClient createClient(String wsdlUrl) {
        RLClient rlClient = new RLClient();
        rlClient.clientFactory = JaxWsDynamicClientFactory.newInstance();
        Client client = rlClient.clientFactory.createClient(wsdlUrl);
        client.getOutInterceptors().add();
        return rlClient;
    }

}
