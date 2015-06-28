package com.mycompany.server;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.nio.charset.Charset;

public class GlobalListener extends Thread {

    private String ip;
    private Integer port;
    private String user;
    private Integer timeout;

    public String getIp() {
        return ip;
    }

    private void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    private void setPort(Integer port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    private void setUser(String user) {
        this.user = user;
    }

    public Integer getTimeout() {
        return timeout;
    }

    private void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public GlobalListener(String ip,Integer port,String user,Integer timeout) throws IllegalArgumentException {
        if (ip == null) throw new IllegalArgumentException("IP can't be null");
        if (port == null) throw new IllegalArgumentException("PORT can't be null");
        if (user == null) throw new IllegalArgumentException("USER can't be null");
        this.setIp(ip);
        this.setPort(port);
        this.setUser(user);
        this.setTimeout(timeout);
        this.setDaemon(true);
        this.setPriority(NORM_PRIORITY);
        this.registerCameraInIBSO();
    }

    private void registerCameraInIBSO() {
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost("http://srv-gw.kbl.nsk.su:8080/BankToSite/FromSiteToClon");
        HttpParams params = new BasicHttpParams();
        params.setParameter("Action", "LBB_EXPORT_DATA_LBB_SITEGT.FUNCTION");
        params.setParameter("Content-Type", "application/json");
        request.setParams(params);
        JSONObject json = new JSONObject();
        json.put("ip",this.getIp());
        json.put("port",this.getPort());
        json.put("user",this.getUser());
        request.setEntity(new StringEntity(json.toJSONString(),Charset.forName("UTF-8")));
        try {
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try
        {
            ServerSocket server = new ServerSocket(this.getPort(),0, InetAddress.getByName(this.getIp()));
            System.out.println("server is started");
            //слушаем порт
            while(true)
            {
                new Sender(server.accept(),"test.jpg",this.getTimeout());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
