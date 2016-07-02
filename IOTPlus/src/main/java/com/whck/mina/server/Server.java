package com.whck.mina.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.whck.mina.codec.BinDeviceParamsMessageDecoder;
import com.whck.mina.codec.BinDeviceParamsMessageEncoder;
import com.whck.mina.codec.DeviceStateMessageDecoder;
import com.whck.mina.codec.DeviceStateMessageEncoder;
import com.whck.mina.codec.FileRequestMessageDecoder;
import com.whck.mina.codec.FileResponseMessageEncoder;
import com.whck.mina.codec.FileSegmentMessageDecoder;
import com.whck.mina.codec.FileSegmentMessageEncoder;
import com.whck.mina.codec.RealDataMessageDecoder;
import com.whck.mina.codec.RealDataMessageEncoder;
import com.whck.mina.codec.SinDeviceParamsMessageDecoder;
import com.whck.mina.codec.SinDeviceParamsMessageEncoder;
import com.whck.mina.handler.ProtocolHandler;
import com.whck.mina.message.BinDeviceParamsMessage;
import com.whck.mina.message.DeviceStateMessage;
import com.whck.mina.message.FileRequestMessage;
import com.whck.mina.message.FileResponseMessage;
import com.whck.mina.message.FileSegmentMessage;
import com.whck.mina.message.RealDataMessage;
import com.whck.mina.message.SinDeviceParamsMessage;

//@Component
//public class Server implements CommandLineRunner{
//	private SocketAcceptor acceptor;
//	
//	@Autowired
//	private ProtocolHandler handler;
//	@Override
//	public void run(String... args) throws Exception {
//		acceptor = new NioSocketAcceptor();
//		System.out.println("Server Started........");
//        acceptor.setHandler(handler);
//        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
//    
//        //添加编解码工厂
//        DemuxingProtocolCodecFactory protoCodecFactory=new DemuxingProtocolCodecFactory();
//        protoCodecFactory.addMessageDecoder(new RealDataMessageDecoder(RealDataMessage.COMMAND));
//        protoCodecFactory.addMessageEncoder(RealDataMessage.class, new RealDataMessageEncoder());
//       
//        protoCodecFactory.addMessageDecoder(new DeviceStateMessageDecoder(DeviceStateMessage.COMMAND));
//        protoCodecFactory.addMessageEncoder(DeviceStateMessage.class, new DeviceStateMessageEncoder());
//       
//        protoCodecFactory.addMessageDecoder(new BinDeviceParamsMessageDecoder(BinDeviceParamsMessage.COMMAND));
//        protoCodecFactory.addMessageEncoder(BinDeviceParamsMessage.class, new BinDeviceParamsMessageEncoder());
//       
//        protoCodecFactory.addMessageDecoder(new SinDeviceParamsMessageDecoder(SinDeviceParamsMessage.COMMAND));
//        protoCodecFactory.addMessageEncoder(SinDeviceParamsMessage.class, new SinDeviceParamsMessageEncoder());
//       
//        protoCodecFactory.addMessageDecoder(new FileRequestMessageDecoder(FileRequestMessage.COMMAND));
//        protoCodecFactory.addMessageEncoder(FileResponseMessage.class, new FileResponseMessageEncoder());
//       
//        protoCodecFactory.addMessageDecoder(new FileSegmentMessageDecoder(FileSegmentMessage.COMMAND));
//        protoCodecFactory.addMessageEncoder(FileSegmentMessage.class, new FileSegmentMessageEncoder());
//       
//        acceptor.getFilterChain().addLast("codec",  
//                new ProtocolCodecFilter(protoCodecFactory));
////        acceptor.getSessionConfig().setReadBufferSize(64);
////        acceptor.getSessionConfig().setReceiveBufferSize(64);
//        try {
//            acceptor.bind(new InetSocketAddress(5000));  //外部配置
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//	}
//}
