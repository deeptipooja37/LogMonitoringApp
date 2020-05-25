

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import monitoringHelperService.LogMonitoringService;

@ServerEndpoint(value = "/endpoint")
public class WebSocketConnection {

	static LogMonitoringService monitoringService;

	static{
			monitoringService = LogMonitoringService.getInstance();
	}
	@OnOpen
	public void onOpen(Session session){
		
		System.out.println("onOpen:"+session.getId());
		monitoringService.readAndPushMessage(session);
	}
	
	@OnClose
	public void onClose(Session session){
		System.out.println("onClose:"+session.getId());
	}
	
	@OnMessage
	public void OnMessage(int message, Session session){
		System.out.println("onMessage:"+message);
	}
	
	@OnError
	public void OnError(Throwable t){
		System.out.println("onClose:"+t.getMessage());
	}
}
