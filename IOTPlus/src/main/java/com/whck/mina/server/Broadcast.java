package com.whck.mina.server;

import java.util.Iterator;
import java.util.Map;
import org.apache.mina.core.session.IoSession;
import com.whck.mina.handler.ProtocolHandler;
import com.whck.mina.message.AbstractMessage;

public class Broadcast {
	public static boolean broadcast(AbstractMessage message){
		Map<String,IoSession> sessions=ProtocolHandler.sessions;
		if(sessions.size()==0)
			return false;
		for(Iterator<IoSession> it=sessions.values().iterator(); it.hasNext();){
			it.next().write(message);
		}
		return true;
	}
}
