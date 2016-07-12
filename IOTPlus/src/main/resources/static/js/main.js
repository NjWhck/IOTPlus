/**
 * Created by JGala 2016/6/18
 */
var sinDevStompClient=null;
var binDevStompClient=null;
var sensorStompClient=null;
var sinDevParamStompClient=null;
var binDevParamStompClient=null;
var zoneSinDevStatMap=new Map();
var zoneBinDevStatMap=new Map();
var zoneSinDevStatImgMap=new Map();
var zoneBinDevStatImgMap=new Map();
var zoneGsMap=new Map();
var zoneSensorStatMap=new Map();

/*初始化要用到的Map*/
function initDevStatMap(){
	var zoneTabs=$('.tab-pane');
	for(var j=0;j<zoneTabs.size()-1;j++){
		var zoneTab=zoneTabs[j];
		var zoneName=$(zoneTab).attr('id'); 
		var sinTabs=$('#sinDevClm',zoneTab).find('.panel');
		var sinDevStatMap=new Map();
		var sinDevStatImgMap=new Map();
		for(var i=0;i<sinTabs.size()-1;i++){
			var tabPane=sinTabs[i];
			var panelHeading=$(tabPane).find(".panel-heading")[0];
			var panelBody=$(tabPane).find(".panel-body")[0];
			var tag=$(panelHeading).find("button")[0];
			var statImg=$(panelBody).find(".fengye")[0];
		    var devName=$($(panelHeading).find("h3")[0]).html();
		    sinDevStatMap.put(devName,tag);
		    sinDevStatImgMap.put(devName.statImg);
		}
		zoneSinDevStatMap.put(zoneName,sinDevStatMap);
		zoneSinDevStatMap.put(zoneName,sinDevStatImgMap);
		
		var binTabs=$('#binDevClm',zoneTab).find('.panel');
		var binDevStatMap=new Map();
		var binDevStatImgMap=new Map();
		for(var i=0;i<binTabs.size()-1;i++){
			var tabPane=binTabs[i];
			var panelHeading=$(tabPane).find(".panel-heading")[0];
			var panelBody=$(tabPane).find(".panel-body")[0];
			var tag=$(panelHeading).find("button")[0];
			$(tag).
			var statImg=$(panelBody).find(".fengye")[0];
		    var devName=$($(panelHeading).find("h3")[0]).html();
		    binDevStatMap.put(devName,tag);
		    binDevStatImgMap.put(devName,statImg);
		}
		zoneBinDevStatMap.put(zoneName,binDevStatMap);
		zoneBinDevStatMap.put(zoneName,binDevStatImgMap);
	}
}
/*初始化传感器中的仪表盘*/
function initGS(){
	var zoneTabs=$('.tab-pane');
	for(var j=0;j<zoneTabs.size()-1;j++){
		var zoneTab=zoneTabs[j];
		var zoneName=$(zoneTab).attr('id');
		var sensorStatMap=new Map();
		var gsMap=new Map();
		
		var gses=$(".gs",zoneTab);
		for(var i=0;i<gses.size();i++){
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
					  "#0080FF",
					  "#EA7500",
					  "#FF0000"
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
		  sinDeviceParamConnect(zoneName);
		  binDeviceParamConnect(zoneName);
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
function sinDeviceParamConnect(zoneName){
	var socket = new SockJS('/sindevparamdata');
	sinDevParamStompClient = Stomp.over(socket);
	sinDevParamStompClient.connect({}, function(frame) {
        /*初始化数据请求*/
		setInterval(function getParams(){
			sinDevParamStompClient.send("/real/sindevparammsg", {}, JSON.stringify({'zoneName':zoneName}));
		},15000);
    });
}
function binDeviceParamConnect(zoneName){
	var socket = new SockJS('/bindevparamdata');
	binDevParamStompClient = Stomp.over(socket);
	binDevParamStompClient.connect({}, function(frame) {
        /*初始化数据请求*/
		setInterval(function getParams(){
			binDevParamStompClient.send("/real/bindevparammsg", {}, JSON.stringify({'zoneName':zoneName}));
		},15000);
    });
}
function refreshSinDevices(data){
	var devices=eval('('+data.body+')');
	for(var i=0;i<devices.length;i++){
		var device=devices[i];
		var zoneName=device.zoneName;
		var name=device.name;
		var online=device.onLine;
		var mode=device.mode;
		var sinDevStatMap=zoneSinDevStatMap.get(zoneName);
		var statSpan=sinDevStatMap.get(name);
		var sinDevStatImgMap=zoneSinDevStatImgMap.get(zoneName);
		var statImg=sinDevStatImgMap.get(name);
		if(0==online){
			$(statSpan).attr('class','btn btn-dafault');
			$(statSpan).html("已停止运行");
			$(statImg).removeAttr('style');
		}else{
				$(statImg).attr('style','animation:mymove 6s linear infinite');
			if(0==mode){
				$(statSpan).attr('class','btn btn-success');
				$(statSpan).html("手动模式运行");
			}else{
				$(statSpan).attr('class','btn btn-info');
				$(statSpan).html("自动模式运行");
			}
		}
	}
}
function refreshBinDevices(data){
	var devices=eval('('+data.body+')');
	for(var i=0;i<devices.length;i++){
		var device=devices[i];
		var zoneName=device.zoneName;
		var name=device.name;
		var online=device.onLine;
		var mode=device.mode;
		var binDevStatMap=zoneBinDevStatMap.get(zoneName);
		var statSpan=binDevStatMap.get(name);
		var binDevStatImgMap=zoneBinDevStatImgMap.get(zoneName);
		var statImg=binDevStatImgMap.get(name);
		if(0==online){
			$(statSpan).attr('class','btn btn-dafault');
			$(statSpan).html("已停止运行");
			$(statImg).removeAttr('style');
		}else{
				$(statImg).attr('style','animation:mymove 6s linear infinite');
			if(0==mode){
				$(statSpan).attr('class','btn btn-success');
				$(statSpan).html("手动模式运行");
			}else{
				$(statSpan).attr('class','btn btn-info');
				$(statSpan).html("自动模式运行");
			}
		}
	}
}
function refreshSensors(data){
	var sensors=eval('('+data.body+')');
	for(var i=0;i<sensors.length;i++){
		var sensor=sensors[i];
		var zoneName=sensor.zoneName;
		var name=sensor.name;
		var online=sensor.online;
		var value=sensor.value;
		var statSpanMap=zoneSensorStatMap.get(zoneName);
		var statSpan=statSpanMap.get(name);
		if(0==value){
			$(statSpan).attr('class','label label-default');
			$(statSpan).html('离线');
		}
		else{
			$(statSpan).attr('class','label label-success');
			$(statSpan).html('在线');
		}
		var gsMap=zoneGsMap.get(zoneName);
		var gs=gsMap.get(name);
		gs.refresh(value);
	}
}

/*websocket end*/

function removeSensor(tar){
	var tabPane=tar.closest(".tab-pane");
    var zoneName=$(tabPane).attr("id");
    var panelHeading=$(tabPane).find(".panel-heading");
    var sensorName=$($(panelHeading).find("h3")[0]).html();
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
    var devName=$($(panelHeading).find("h3")[0]).html();
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
    var devName=$($(panelHeading).find("h3")[0]).html();
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
function changeSinDevState(tar){
	var curMode=$(tar).html();
	var tabPane=tar.closest(".tab-pane");
    var zoneName=$(tabPane).attr("id");
    var panelHeading=$(tabPane).find(".panel-heading");
    var devName=$($(panelHeading).find("h3")[0]).html();
    
    var url="/sindevice/operation/"+zoneName+"/"+devName+"/";
    if(curMode.indexOf("自动")>=0){
    	url+="Manual";
    }else{
    	url+="auto";
    }
    $.ajax({
        type:"GET", 
        timeout:10000,
        url:url,
        dataType:"text",	
        beforeSend:function(){
        	statSpan.html("命令发送中....");
        },
        error:function(){
        	alert("访问出错");
        },
        complete:function(XMLHttpRequest,status){
        	if(status=="timeout")
        		alert("请求超时");
        }
    })
}
function changeBinDevState(tar){
	var curMode=$(tar).html();
	var tabPane=tar.closest(".tab-pane");
    var zoneName=$(tabPane).attr("id");
    var panelHeading=$(tabPane).find(".panel-heading");
    var devName=$($(panelHeading).find("h3")[0]).html();
    
    var url="/bindevice/operation/"+zoneName+"/"+devName+"/";
    if(curMode.indexOf("自动")>=0){
    	url+="Manual";
    }else{
    	url+="auto";
    }
    $.ajax({
        type:"GET", 
        timeout:10000,
        url:url,
        dataType:"text",	
        beforeSend:function(){
        	statSpan.html("命令发送中....");
        },
        error:function(){
        	alert("访问出错");
        },
        complete:function(XMLHttpRequest,status){
        	if(status=="timeout")
        		alert("请求超时");
        }
    })
}
function startSinDev(tar){
	var tabPane=tar.closest(".tab-pane");
    var zoneName=$(tabPane).attr("id");
    var panelHeading=$(tabPane).find(".panel-heading");
    var devName=$($(panelHeading).find("h3")[0]).html();
    var panelBody=$(tabPane).find(".panel-body");
    var statSpan=$(panelHeading).find("button")[0];
    $.ajax({
        type:"GET", 
        timeout:10000,
        url:"/sindevice/operation/"+zoneName+"/"+devName+"/"+"start",
        dataType:"text",	
        beforeSend:function(){
        	statSpan.html("命令发送中....");
        },
        success:function(result){
            var msg="";
            if(result=="success")
                msg="手动模式运行";
            else if(result=="disconnected")
                msg="设备离线";
            $(statSpan).html(msg);
        },
        error:function(){
        	$(statSpan).html("访问出错");
        },
        complete:function(XMLHttpRequest,status){
        	$(statSpan).html("访问超时");
        }
    })
}
function stopSinDev(tar){
	var tabPane=tar.closest(".tab-pane");
    var zoneName=$(tabPane).attr("id");
    var panelHeading=$(tabPane).find(".panel-heading");
    var devName=$($(panelHeading).find("h3")[0]).html();
    var panelBody=$(tabPane).find(".panel-body");
    var statSpan=$(panelHeading).find("button")[0];
    $.ajax({
        type:"GET",
        timeout:12000,
        url:"/sindevice/operation/"+zoneName+"/"+devName+"/"+"stop",
        dataType:"text",	
        beforeSend:function(){
        	$(statSpan).html("命令发送中....");
        },
        success:function(result){
            var msg="";
            if(reslut=="success")
                msg="已停止运行";
            else if(result=="disconnected")
                msg="设备离线，操作失败";
            $(statSpan).html(msg);
        },
        error:function(){
        	$(statSpan).html("访问出错");
        },
        complete:function(XMLHttpRequest,status){
        	$(statSpan).html("访问超时");
        }
    })
}

function binDevForward(tar){
	var tabPane=tar.closest(".tab-pane");
    var zoneName=$(tabPane).attr("id");
    var panelHeading=$(tabPane).find(".panel-heading");
    var devName=$($(panelHeading).find("h3")[0]).html();
    var panelBody=$(tabPane).find(".panel-body");
    var statSpan=$(panelHeading).find("button")[0];
    $.ajax({
        type:"GET",
        timeout:12000,
        url:"/bindevice/operation/"+zoneName+"/"+devName+"/"+"forward",
        dataType:"text",	
        beforeSend:function(){
        	$(statSpan).html("命令发送中....");
        },
        success:function(result){
            var msg="";
            if(reslut=="success")
                msg="正转";
            else if(result=="disconnected")
                msg="设备离线，操作失败";
            $(statSpan).html(msg);
        },
        error:function(){
        	$(statSpan).html("访问出错");
        },
        complete:function(XMLHttpRequest,status){
        	$(statSpan).html("访问超时");
        }
    })
}
function binDevBackward(tar){
	var tabPane=tar.closest(".tab-pane");
    var zoneName=$(tabPane).attr("id");
    var panelHeading=$(tabPane).find(".panel-heading");
    var devName=$($(panelHeading).find("h3")[0]).html();
    var panelBody=$(tabPane).find(".panel-body");
    var statSpan=$(panelHeading).find("button")[0];
    $.ajax({
        type:"GET",
        timeout:12000,
        url:"/bindevice/operation/"+zoneName+"/"+devName+"/"+"backward",
        dataType:"text",	
        beforeSend:function(){
        	$(statSpan).html("命令发送中....");
        },
        success:function(result){
            var msg="";
            if(reslut=="success")
                msg="正转";
            else if(result=="disconnected")
                msg="设备离线，操作失败";
            $(statSpan).html(msg);
        },
        error:function(){
        	$(statSpan).html("访问出错");
        },
        complete:function(XMLHttpRequest,status){
        	$(statSpan).html("访问超时");
        }
    })
}
function binDevStop(tar){
	var tabPane=tar.closest(".tab-pane");
    var zoneName=$(tabPane).attr("id");
    var panelHeading=$(tabPane).find(".panel-heading");
    var devName=$($(panelHeading).find("h3")[0]).html();
    var panelBody=$(tabPane).find(".panel-body");
    var statSpan=$(panelHeading).find("button")[0];
    $.ajax({
        type:"GET",
        timeout:12000,
        url:"/bindevice/operation/"+zoneName+"/"+devName+"/"+"stop",
        dataType:"text",	
        beforeSend:function(){
        	$(statSpan).html("命令发送中....");
        },
        success:function(result){
            var msg="";
            if(reslut=="success")
                msg="正转";
            else if(result=="disconnected")
                msg="设备离线，操作失败";
            $(statSpan).html(msg);
        },
        error:function(){
        	$(statSpan).html("访问出错");
        },
        complete:function(XMLHttpRequest,status){
        	$(statSpan).html("访问超时");
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
}
function validatorInit(){
	$('#zoneAddForm').bootstrapValidator({
		message: '内容不合法',
		feedbackIcons: {
			valid: 'fa fa-ok',
			invalid: 'fa fa-remove',
			validating: 'fa fa-refresh'
		},
		submitHandler: function(validator, form, submitButton) {
			$.ajax({
            	type:"post",
            	url:"/test"+form.attr('action'),
            	data: form.serialize(),
            	dataType:"text",
            	
        		success:function(data) {
		            if (data == 'true') {
		                window.location.reload();
		            } else {
		                $('#zoneAddErrors').html('数据中心编号已存在').removeClass('hide');
		//            $('#zoneAddForm').bootstrapValidator('disableSubmitButtons', false);
		            }
        		},
				 error:function(){
		            	$("#sensorAddModal").modal('hide');
		            	alert("错误:添加传感器失败");
		            }
            });
        
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
		message: '内容不合法',
		feedbackIcons: {
			valid: 'fa fa-ok',
			invalid: 'fa fa-remove',
			validating: 'fa fa-refresh'
		},
		submitHandler: function(validator, form, submitButton) {
            $.ajax({
            	type:"post",
            	url:"/test"+form.attr('action'),
            	data: form.serialize(),
            	dataType:"text",
            	
        		success:function(data) {
		        	var result=data;
		            if (data== 'true') {
		            	$("#sensorAddModal").modal('hide');
		                window.location.reload();
		            } else {
		                $('#sensorAddErrors').html('传感器名称已存在').removeClass('hide');
		     //           $('#addSensorForm').bootstrapValidator('disableSubmitButtons', false);
		            }
        		},
	            error:function(){
	            	$("#sensorAddModal").modal('hide');
	            	alert("添加传感器失败");
	            }
            });
        },
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
		message: '内容不合法',
		feedbackIcons: {
			valid: 'fa fa-ok',
			invalid: 'fa fa-remove',
			validating: 'fa fa-refresh'
		},
		submitHandler: function(validator, form, submitButton) {
			$.ajax({
            	type:"post",
            	url:"/test"+form.attr('action'),
            	data: form.serialize(),
            	dataType:"text",
            	
        		success:function(data) {
        			var result=data;
		            if (data== 'true') {
		            	$("#sensorAddModal").modal('hide');
		                window.location.reload();
		            } else {
		            	  $('#sinDevAddErrors').html('控制器名称已存在').removeClass('hide');
		//              $('#addSinDevForm').bootstrapValidator('disableSubmitButtons', false);
		            }
        		},
				 error:function(){
		            	$("#sinDevAddModal").modal('hide');
		            	alert("错误:添加控制器失败");
		            }
            });
          
        },
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
					}
				}
			}
		}
	});
	$('#addBinDevForm').bootstrapValidator({
		message: '内容不合法',
		feedbackIcons: {
			valid: 'fa fa-ok',
			invalid: 'fa fa-remove',
			validating: 'fa fa-refresh'
		},
		submitHandler: function(validator, form, submitButton) {
			$.ajax({
            	type:"post",
            	url:"/test"+form.attr('action'),
            	data: form.serialize(),
            	dataType:"text",
            	
        		success:function(data) {
        			var result=data;
		            if (data== 'true') {
		            	$("#sensorAddModal").modal('hide');
		                window.location.reload();
		            } else {
		            	 $('#binDevAddErrors').html('控制器名称已存在').removeClass('hide');
		//             $('#addBinDevForm').bootstrapValidator('disableSubmitButtons', false);
		            }
        		},
				 error:function(){
		            	$("#binDevAddModal").modal('hide');
		            	alert("错误:添加控制器失败");
		            }
            });
        },
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
//    var devName=panelHeading.find("h3").html();
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
//            panelHeading.find("span").html(msg);
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
//            panelBody.html(content);
//        },
//        error:function(){
//        	alert("刷新失败");
//        }
//    })
//}

