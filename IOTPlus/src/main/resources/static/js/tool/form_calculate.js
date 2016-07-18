(function($) {
	$.calculate = function(form, rule) {
		if (!rule) return;
		form = $(form);
		
		var oSerialize = form.data("serialize") || {};
		
		var oCal = {}, isNum = function(value) {
			return 	typeof value == "number" && (value || value === 0);
		};
		
		$.each(oSerialize, function(name) {
			oSerialize[name] = 0;	
		});
		form.find(":input").each(function() {
			var val, name = this.name, type = this.type;
			if (name) {
				val = $(this).val();
				if (/radio|checkbox/.test(type)) {
					if (oSerialize[name]) return;
					if (this.checked && !this.disabled) {
						oSerialize[name] = val * 1 || val;	
					} else {
						oSerialize[name] = 0;
					}
				} else {
				
					if (!val || this.disabled) {
						val = 0;	
					} 
					oSerialize[name] = val * 1 || val;	
				}
			}
		});
		form.data("serialize", oSerialize);
		oCal = $.extend({}, oSerialize);
		$.each(rule, function(id, fun) {
			var value = $.isFunction(fun)? fun.call(oCal): fun;
			if (!isNum(value)) value = 0;
			oCal[id] = value;
		});
		
		$.each(rule, function(id, fun) {
			var eleResult = /^\W|\[|\:/.test(id)? $(id) : $("#" + id), value = oCal[id] || ($.isFunction(fun)? fun.call(oCal): fun) || 0;
			if (isNum(value) && eleResult.length) {
				!oCal[id] && (oCal[id] = value);
				value = String(Math.round(value * 100) / 100).replace(/\.00/, "");
				eleResult.each(function() {
					if (/^input$/i.test(this.tagName)) {
						$(this).val(value);	
					} else {
						$(this).html(value);
					}
				});				
			}
		});
	};
})(jQuery);