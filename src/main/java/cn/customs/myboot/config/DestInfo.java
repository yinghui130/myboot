package cn.customs.myboot.config;

public class DestInfo {

    private String appCode;
    private String workDir;
    private boolean sendFlag;
    private String mqHost;
    private String mqPort;
    private String mqQueue;
    private String mqChannel;
    private int mqCcsid;
    private int mqReceiveTimeout;
    private String mqUser;
    private String mqPassword;
    
    public void setAppCode(String appCode) {
         this.appCode = appCode;
     }
     public String getAppCode() {
         return appCode;
     }

    public void setWorkDir(String workDir) {
         this.workDir = workDir;
     }
     public String getWorkDir() {
         return workDir;
     }

    public void setSendFlag(boolean sendFlag) {
         this.sendFlag = sendFlag;
     }
     public boolean getSendFlag() {
         return sendFlag;
     }

    public void setMqHost(String mqHost) {
         this.mqHost = mqHost;
     }
     public String getMqHost() {
         return mqHost;
     }

    public void setMqPort(String mqPort) {
         this.mqPort = mqPort;
     }
     public String getMqPort() {
         return mqPort;
     }

    public void setMqQueue(String mqQueue) {
         this.mqQueue = mqQueue;
     }
     public String getMqQueue() {
         return mqQueue;
     }

    public void setMqChannel(String mqChannel) {
         this.mqChannel = mqChannel;
     }
     public String getMqChannel() {
         return mqChannel;
     }

    public void setMqCcsid(int mqCcsid) {
         this.mqCcsid = mqCcsid;
     }
     public int getMqCcsid() {
         return mqCcsid;
     }

    public void setMqReceiveTimeout(int mqReceiveTimeout) {
         this.mqReceiveTimeout = mqReceiveTimeout;
     }
     public int getMqReceiveTimeout() {
         return mqReceiveTimeout;
     }

    public void setMqUser(String mqUser) {
         this.mqUser = mqUser;
     }
     public String getMqUser() {
         return mqUser;
     }

    public void setMqPassword(String mqPassword) {
         this.mqPassword = mqPassword;
     }
     public String getMqPassword() {
         return mqPassword;
     }
}
