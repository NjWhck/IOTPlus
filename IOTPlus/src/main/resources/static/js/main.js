/**
 * Created by JGala 2016/6/18
 */
var sinDevStompClient=null;
var binDevStompClient=null;
var sensorStompClient=null;
var zoneSinDevStatMap=new Map();
var zoneBinDevStatMap=new Map();
var zoneGsMap=new Map();
var zoneSensorStatMap=new Map();

/*初始化要用到的Map*/
function initDevStatMap(){
	var zoneTabs=$('.tab-pane');
	alert("zoneSize:"+zoneTabs.size());
	for(var j=0;j<zoneTabs.size()-1;j++){
		var zoneTab=zoneTabs[j];
		alert("zoneTab:"+zoneTab);
		var zoneName=$(zoneTab).attr('id'); //attention:$(zoneTab) 才是jquery对象
		var sinTabs=$('#sinDevClm',zoneTab).find('.panel');
		var sinDevStatMap=new Map();
		for(var i=0;i<sinTabs.size()-1;i++){
			var tabPane=sinTabs[i];
			var panelHeading=$(tabPane).find(".panel-heading")[0];
			var tag=$(panelHeading).find("span")[0];
			alert("tag:"+tag);
		    var devName=$(panelHeading).find("h3")[0].innerHTML;
		    alert("h3:"+$(panelHeading).find("h3")[0]);
		    sinDevStatMap.put(devName,tag);
		}
		zoneSinDevStatMap.put(zoneName,sinDevStatMap);
		
		var binTabs=$('#binDevClm',zoneTab).find('.panel');
		var binDevStatMap=new Map();
		for(var i=0;i<binTabs.size()-1;i++){
			var tabPane=binTabs[i];
			var panelHeading=$(tabPane).find(".panel-heading")[0];
			var tag=$(panelHeading).find("span")[0];
		    var devName=$(panelHeading).find("h3")[0].innerHTML;
		    binDevStatMap.put(devName,tag);
		}
		zoneBinDevStatMap.put(zoneName,binDevStatMap);
	}
}
/*初始化传感器中的仪表盘*/
function initGS(){
	var zoneTabs=$('.tab-pane');
	for(var j=0;j<zoneTabs.size()-1;j++){
		var zoneTab=zoneTabs[i];
		var zoneName=$(zoneTab).attr('id');
		var sensorStatMap=new Map();
		var gsMap=new Map();
		
		var gses=$(".gs",zoneTab);
		for(var i=0;i<gses.size()-1;i++){
			var gs=gses[i];
			var id = $(gs).attr('id');
			var form = $(gs).prev();
	        var unit = $("input[name='unit']",form).val();
	        var max = $("input[name='upvalue']",form).val();
	        var min =  $("input[name='downvalue']",form).val();
	        var value = min;
	        var name =  $("input[name='name']",form).val();
	        var title=name.split("传感器")[0];
	        
	        var panel=$(gs).closest(".panel");
	        var panelHeading=$(panel).find(".panel-heading")[0];
	        var statSpan=$(panelHeading).find("span")[0];
	        
	        var g = new JustGage({
		          id: id, 
		          value: value, 
		          min: min,
		          max: max,
		          title: title,
		          label: unit,
					levelColors: [
					  "#222222",
					  "#555555",
					  "#CCCCCC"
					]
		        });
		     gsMap.put(name,g);
		     sensorStatMap.put(name,statSpan);
		  }
		zoneGsMap.put(zoneName,gsMap);
		zoneSensorStatMap.put(zoneName,sensorStatMap);
	}
}

/*websocket*/
function initConn(){
	  var panels=$('.tab-pane');
	  for(var i=0;i<panels.size()-1;i++){
		  zoneName=$(panels[i]).attr('id');
		  sinDevConnect(zoneName);
		  binDevConnect(zoneName);
		  sensorConnect(zoneName);
	  }
}
function sinDevConnect(zoneName) {
    var socket = new SockJS('/sindevdata');
    sinDevStompClient = Stomp.over(socket);
    sinDevStompClient.connect({}, function(frame) {
        /*初始化数据请求*/
        sinDevStompClient.send("/real/sindevmsg",{},JSON.stringify({'zoneName':zoneName}));
        sinDevStompClient.subscribe('/sindevice/update/'+zoneName, function(data){
            refreshSinDevices(data);
        });
    });
    
}
function binDevConnect(zoneName) {
    var socket = new SockJS('/bindevdata');
    binDevStompClient = Stomp.over(socket);
    binDevStompClient.connect({}, function(frame) {
        /*初始化数据请求*/
        binDevStompClient.send("/real/bindevmsg",{},JSON.stringify({'zoneName':zoneName}));
        binDevStompClient.subscribe('/bindevice/update/'+zoneName, function(data){
            refreshBinDevices(data);
        });
    });
    
}

function sensorConnect(zoneName) {
    var socket = new SockJS('/sensordata');
    sensorStompClient = Stomp.over(socket);
    sensorStompClient.connect({}, function(frame) {
        /*初始化数据请求*/
        sensorStompClient.send("/real/sensormsg", {}, JSON.stringify({'zoneName':zoneName}));
        sensorStompClient.subscribe('/sensor/update/'+zoneName, function(data){
            refreshSensors(data);
        });
    });
}
function refreshSinDevices(devices){
	for(var i=0;i<devices.size();i++){
		var device=devices[i];
	}
}
function refreshBinDevices(devices){
	for(var i=0;i<devices.size();i++){
		var device=devices[i];
	}
}
function refreshSensors(sensors){
	g.refresh();
}
/*websocket end*/

function removeSensor(tar){
	var tabPane=tar.closest(".tab-pane");
    var zoneName=$(tabPane).attr("id");
    var panelHeading=$(tabPane).find(".panel-heading");
    var sensorName=$(panelHeading).find("h3").innerHTML;
    var panelBody=$(tabPane).find(".panel-body");

    $.ajax({
        type:"GET",
        url:"/sindevice/remove/"+zoneName+"/"+sensorName,
        dataType:"text",	
        success:function(){
        	zoneSensorStatMap.get(zoneName).removeByKey(sensorName);
            tabPane.remove();
        },
        error:function(){
        	alert("删除传感器失败");
        }
    })
}
function removeSinDev(tar){
	var tabPane=tar.closest(".tab-pane");
    var zoneName=$(tabPane).attr("id");
    var panelHeading=$(tabPane).find(".panel-heading");
    var devName=$(panelHeading).find("h3").innerHTML;
    var panelBody=$(tabPane).find(".panel-body");

    $.ajax({
        type:"GET",
        url:"/sindevice/remove/"+zoneName+"/"+devName,
        dataType:"text",	
        success:function(){
        	zoneSinDevStatMap.get(zoneName).removeByKey(devName);
            tabPane.remove();
        },
        error:function(){
        	alert("删除单点设备失败");
        }
    })
}
function removeBinDev(tar){
	var tabPane=tar.closest(".tab-pane");
    var zoneName=$(tabPane).attr("id");
    var panelHeading=$(tabPane).find(".panel-heading");
    var devName=$(panelHeading).find("h3").innerHTML;
    var panelBody=$(tabPane).find(".panel-body");

    $.ajax({
        type:"GET",
        url:"/bindevice/remove/"+zoneName+"/"+devName,
        dataType:"text",	
        success:function(){
        	zoneBinDevStatMap.get(zoneName).removeByKey(devName);
            tabPane.remove();
        },
        error:function(){
        	alert("删除删除电机设备失败");
        }
    })
}
function startSinDev(tar){
	var tabPane=tar.closest(".tab-pane");
    var zoneName=$(tabPane).attr("id");
    var panelHeading=$(tabPane).find(".panel-heading");
    var devName=$(panelHeading).find("h3").innerHTML;
    var panelBody=$(tabPane).find(".panel-body");
    var statSpan=$(panelHeading).find("span")[0];
    $.ajax({
        type:"GET", 
        timeout:12000,
        url:"/sindevice/operation/"+zoneName+"/"+devName+"/"+"start",
        dataType:"text",	
        beforeSend:function(){
        	statSpan.innerHTML("命令发送中....");
        },
        success:function(result){
            var msg="";
            if(result=="success")
                msg="手动模式运行";
            else if(result=="disconnected")
                msg="设备离线";
            statSpan.innerHTML(msg);
        },
        error:function(){
        	statSpan.innerHTML("访问出错");
        },
        complete:function(XMLHttpRequest,status){
        	statSpan.innerHTML("访问超时");
        }
    })
}
function stopSinDev(tar){
	var tabPane=tar.closest(".tab-pane");
    var zoneName=$(tabPane).attr("id");
    var panelHeading=$(tabPane).find(".panel-heading");
    var devName=$(panelHeading).find("h3").innerHTML;
    var panelBody=$(tabPane).find(".panel-body");
    var statSpan=$(panelHeading).find("span")[0];
    $.ajax({
        type:"GET",
        timeout:12000,
        url:"/sindevice/operation/"+zoneName+"/"+devName+"/"+"stop",
        dataType:"text",	
        beforeSend:function(){
        	statSpan.innerHTML("命令发送中....");
        },
        success:function(result){
            var msg="";
            if(reslut=="success")
                msg="已停止运行";
            else if(result=="disconnected")
                msg="设备离线，操作失败";
            statSpan.innerHTML(msg);
        },
        error:function(){
        	statSpan.innerHTML("访问出错");
        },
        complete:function(XMLHttpRequest,status){
        	statSpan.innerHTML("访问超时");
        }
    })
}

function binDevForward(tar){
	var tabPane=tar.closest(".tab-pane");
    var zoneName=$(tabPane).attr("id");
    var panelHeading=$(tabPane).find(".panel-heading");
    var devName=$(panelHeading).find("h3").innerHTML;
    var panelBody=$(tabPane).find(".panel-body");
    var statSpan=$(panelHeading).find("span")[0];
    $.ajax({
        type:"GET",
        timeout:12000,
        url:"/bindevice/operation/"+zoneName+"/"+devName+"/"+"forward",
        dataType:"text",	
        beforeSend:function(){
        	statSpan.innerHTML("命令发送中....");
        },
        success:function(result){
            var msg="";
            if(reslut=="success")
                msg="正转";
            else if(result=="disconnected")
                msg="设备离线，操作失败";
            statSpan.innerHTML(msg);
        },
        error:function(){
        	statSpan.innerHTML("访问出错");
        },
        complete:function(XMLHttpRequest,status){
        	statSpan.innerHTML("访问超时");
        }
    })
}
function binDevBackward(tar){
	var tabPane=tar.closest(".tab-pane");
    var zoneName=$(tabPane).attr("id");
    var panelHeading=$(tabPane).find(".panel-heading");
    var devName=$(panelHeading).find("h3").innerHTML;
    var panelBody=$(tabPane).find(".panel-body");
    var statSpan=$(panelHeading).find("span")[0];
    $.ajax({
        type:"GET",
        timeout:12000,
        url:"/bindevice/operation/"+zoneName+"/"+devName+"/"+"backward",
        dataType:"text",	
        beforeSend:function(){
        	statSpan.innerHTML("命令发送中....");
        },
        success:function(result){
            var msg="";
            if(reslut=="success")
                msg="正转";
            else if(result=="disconnected")
                msg="设备离线，操作失败";
            statSpan.innerHTML(msg);
        },
        error:function(){
        	statSpan.innerHTML("访问出错");
        },
        complete:function(XMLHttpRequest,status){
        	statSpan.innerHTML("访问超时");
        }
    })
}
function binDevStop(tar){
	var tabPane=tar.closest(".tab-pane");
    var zoneName=$(tabPane).attr("id");
    var panelHeading=$(tabPane).find(".panel-heading");
    var devName=$(panelHeading).find("h3").innerHTML;
    var panelBody=$(tabPane).find(".panel-body");
    var statSpan=$(panelHeading).find("span")[0];
    $.ajax({
        type:"GET",
        timeout:12000,
        url:"/bindevice/operation/"+zoneName+"/"+devName+"/"+"stop",
        dataType:"text",	
        beforeSend:function(){
        	statSpan.innerHTML("命令发送中....");
        },
        success:function(result){
            var msg="";
            if(reslut=="success")
                msg="正转";
            else if(result=="disconnected")
                msg="设备离线，操作失败";
            statSpan.innerHTML(msg);
        },
        error:function(){
        	statSpan.innerHTML("访问出错");
        },
        complete:function(XMLHttpRequest,status){
        	statSpan.innerHTML("访问超时");
        }
    })
}
/* Add sensor、sindevice、bindevice*/
function addEventInit(){
	$(".a_setting").each(function(){
		$(this).click(function() {
			var strings = ($(this).attr('id')).split('_');
			var modalId=strings[0];
			var zoneName=strings[1];
			var modal=$('#'+modalId);
			var input=$(".modal-body > form input[name='zoneName']",modal).val(zoneName);
			modal.modal();
		});
	});
	$("#btnAddSensor").click(function(){
		var zoneName=$("#addSensorForm input[name='zoneName']").val();
		var name=$("#addSensorForm input[name='name']").val();
		var unit=$("#addSensorForm input[name='unit']").val();
		var loadingModal=$("#loadingModal");
		alert("zoneName:"+zoneName);
		$.ajax({
			type:"post",
			url:"/sensor/add",
			dataType:"json",
			data:{
				zoneName:zoneName,
				name:name,
				unit:unit
			},
			beforeSend:function(){
				loadingModal.modal();
			},
			complete:function(){
				loadingModal.modal('hide');
			},
			success:function(){
			//	alert('Success');
			},
			error:function(){
			//	alert('Failed');
			}
		});
	//	$("#addSensorForm").submit();
	});
	$("#btnAddSinDevice").click(function(){
		$("#addSinDevForm").submit();
	});
	$("#btnAddBinDevice").click(function(){
		$("#addBinDevForm").submit();
	});
}
function validatorInit(){
	$('#zoneAddForm').bootstrapValidator({
		message: '内容不合法',
		feedbackIcons: {
			valid: 'fa fa-ok',
			invalid: 'fa fa-remove',
			validating: 'fa fa-refresh'
		},
		fields: {
			zoneName: {
				message: '区域编号无效',
				validators: {
					notEmpty: {
						message: '区域编号不能位空'
					},
					stringLength: {
						min: 11,
						max: 11,
						message: '区域编号为11个字符'
					},
					regexp: {
						regexp: /^[a-zA-Z0-9_\.]+$/,
						message: '区域编号只能由字母、数字、点和下划线组成'
					},
					remote: {
						url: '/zone/exist',
						message: '区域编号被占用'
					}
				}
			},
			zoneAlias: {
				validators: {
					notEmpty: {
						message: '区域名称不能位空'
					},
					stringLength: {
						min: 1,
						max: 40,
						message: '区域名称为1_40个字符'
					}
				}
			}
		}
	});
	$('#addSensorForm').bootstrapValidator({
		fields: {
			name: {
				message: '输入无效',
				validators: {
					notEmpty: {
						message: '采集点名称不能位空'
					},
					stringLength: {
						min: 1,
						max: 40,
						message: '采集点名称为1-40个字符'
					},
					remote: {
						url: '/sensor/exist',
						type: 'POST',
						delay: 2000,
						message: '采集点名称被占用'
					}
				}
			},
			unit: {
				validators: {
					notEmpty: {
						message: '单位不能位空'
					},
					stringLength: {
						min: 1,
						max: 20,
						message: '单位为1_20个字符'
					}
				}
			}
		}
	});
	$('#addSinDevForm').bootstrapValidator({
		fields: {
			name: {
				message: '输入无效',
				validators: {
					notEmpty: {
						message: '控制器名称不能位空'
					},
					stringLength: {
						min: 1,
						max: 40,
						message: '控制器名称为1-40个字符'
					},
					remote: {
						url: '/sindevice/exist',
						type: 'POST',
						delay: 2000,
						message: '控制器名称被占用'
					}
				}
			}
		}
	});
	$('#addBinDevForm').bootstrapValidator({
		fields: {
			name: {
				message: '输入无效',
				validators: {
					notEmpty: {
						message: '控制器名称不能位空'
					},
					stringLength: {
						min: 1,
						max: 40,
						message: '控制器名称为1-40个字符'
					},
					remote: {
						url: '/bindevice/exist',
						type: 'POST',
						delay: 2000,
						message: '控制器名称被占用'
					}
				}
			}
		}
	});
}
///*backup*/
//
///*Sin Device operations*/
//function reloadSinDev(tar){
//    var tabPane=tar.closest(".tab-pane");
//    var zoneName=tabPane.attr("id");
//    var panelHeading=tabPane.find(".panel-heading");
//    var devName=panelHeading.find("h3")[0].innerHTML;
//
//    var panelBody=tabPane.find(".panel-body");
//
//    $.ajax({
//        type:"GET",
//        url:"/sindevice/reload/"+zoneName+"/"+devName,
//        dataType:"text",
//        success:function(device){
//            var msg="";
//            if(device.online==0)
//                msg="已停止运行";
//            else
//                if(device.ctrlMode==0)
//                    msg="自动模式运行";
//                else
//                    msg="手动模式运行";
//            panelHeading.find("span").innerHTML(msg);
//            var sensors=device.sensors;
//            var content="";
//            for(var i=0;i<sensors.size();i++){
//                var sensor=sensors[i];
//                var state=sensor.state==0?"离线":"在线";
//                content+="<tr>" +
//                            "<td>"+(i+1)+"</td>" +
//                            "<td>"+sensor.name+"</td>" +
//                            "<td>"+sensor.value+"</td>" +
//                            "<td>"+state+"</td>" +
//                        "</tr>";
//            }
//            panelBody.innerHTML(content);
//        },
//        error:function(){
//        	alert("刷新失败");
//        }
//    })
//}

///*Bin Device operations*/
//function reloadBinDev(tar){
//    var tabPane=tar.closest(".tab-pane");
//    var zoneName=tabPane.attr("id");
//    var panelHeading=tabPane.find(".panel-heading");
//    var devName=panelHeading.find("h3").innerHTML;
//    var panelBody=tabPane.find(".panel-body");
//
//    $.ajax({
//        type:"GET",
//        url:"/bindevice/reload/"+zoneName+"/"+devName,
//        dataType:"text",	
//        success:function(device){
//            var msg="";
//            if(device.online==0)
//                msg="已停止运行";
//            else
//                if(device.ctrlMode==0)
//                    msg="自动模式运行";
//                else
//                    msg="手动模式运行";
//            panelHeading.find("span").innerHTML(msg);
//            var sensors=device.sensors;
//            var content="";
//            for(var i=0;i<sensors.size();i++){
//                var sensor=sensors[i];
//                var state=sensor.state==0?"离线":"在线";
//                content+="<tr>" +
//                            "<td>"+(i+1)+"</td>" +
//                            "<td>"+sensor.name+"</td>" +
//                            "<td>"+sensor.value+"</td>" +
//                            "<td>"+state+"</td>" +
//                        "</tr>";
//            }
//            panelBody.innerHTML(content);
//        },
//        error:function(){
//        	alert("刷新失败");
//        }
//    })
//}

