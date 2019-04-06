
		var LANGUAGE_CODE = "en";

		function loadProperties(type) {
			//alert("load "+type);
			$.cookie('Glangcode', type,{ expires: 30,path:'/',secure:false });
			//$.cookie('langcode', type,{ expires: 30,secure:false });
			jQuery.i18n.properties({
				name: 'strings', 
				path: '/js/i18n',
				mode: 'map', 
				language: type, 
				cache: false,
				encoding: 'UTF-8',
				callback: function () {    
					var arr = $("[i18nid]");		
					for (var i = 0; i < arr.length; i++) {
							var LangId=arr[i].getAttribute('i18nid');
							arr[i].innerText=$.i18n.prop(LangId);
					}
					
//					arr = $("[i18nid_placeholder]");		
//					for (i = 0; i < arr.length; i++) {
//							LangId=arr[i].getAttribute('i18nid_placeholder');
//							arr[i].setAttribute('placeholder',$.i18n.prop(LangId));
//					}
//					
//					arr = $("[i18nid_title]");		
//					for (i = 0; i < arr.length; i++) {
//							LangId=arr[i].getAttribute('i18nid_title');
//							arr[i].setAttribute('title',$.i18n.prop(LangId));
//					}
					Replacei18nAttr("placeholder");
					Replacei18nAttr("title");
					
				}
			});
		}

		function Replacei18nAttr(InAttrName)
		{
				var attriName="i18nid_"+InAttrName;
				var arr = $("["+attriName+"]");		
				if (arr.length>0)
				{
					for (var i = 0; i < arr.length; i++) {
						var LangId=arr[i].getAttribute(attriName);
						if(LangId!=undefined)
						{
							arr[i].setAttribute(InAttrName,$.i18n.prop(LangId));
						} 
						
					}
				}
				
		}

		function switchLang() {
			LANGUAGE_CODE = LANGUAGE_CODE == 'zh' ? 'en' : 'zh';
			loadProperties(LANGUAGE_CODE);
		}

		$(document).ready(function () {
			var LangcodeFromCookie=$.cookie('Glangcode');
			//alert('cookie:'+LangcodeFromCookie);
			LangcodeFromCookie=LangcodeFromCookie=='en'?'en':'zh';
			loadProperties(LangcodeFromCookie);
			
			
			//LANGUAGE_CODE = jQuery.i18n.normaliseLanguageCode({});   
			
		})
