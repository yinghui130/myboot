/**
  * Copyright 2018 bejson.com 
  */
package cn.customs.myboot.config;
import java.io.Serializable;

import lombok.Data;

/**
 * Auto-generated: 2018-12-01 11:11:22
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Send implements Serializable {

	private static final long serialVersionUID = 1L;
	private String fileType;
    private int mqccsid;
    private String mqchannel;
    private String mqhost;
    private String mqqueue;
    private String mqqueuemanager;
    private int mqreceivetimeout;
    private String mqusername;
    private String password;
    private int mqport;
}