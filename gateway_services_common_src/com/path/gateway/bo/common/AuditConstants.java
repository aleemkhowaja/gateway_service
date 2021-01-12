package com.path.gateway.bo.common;

import java.util.HashMap;

public class AuditConstants
{
	 public static final HashMap<String, String> keyRef = new HashMap<String, String>();
	 
	 static
	 {
		 keyRef.put("atmAcquirerKey", "COMP_CODE:4,ACQUIRER_CODE:4");
		 keyRef.put("channelKey", "CHANNEL_ID:4");
		 keyRef.put("atmTerminalKey", "COMP_CODE:4,TERMINAL_ID:4,TERMINAL_CODE:16");
	 }
	
	 
	 public static final String atmAcquirerKey = "atmAcquirerKey";
	 public static final String channelKey = "channelKey";
	 public static final String atmTerminalKey = "atmTerminalKey";
    
}