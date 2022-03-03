package com.qzl.xiaorpc;

public class Client {
    public static void main(String[] args) {
        RpcClient client = new RpcClient();
        CalcService calcService = client.getProxy(CalcService.class);
        System.out.println(calcService.add(1, 23));
        System.out.println(calcService.minus(23, 4));
    }
}
