
		var LANGUAGE_CODE = "zh";

		function loadProperties(type) {
			jQuery.i18n.properties({
				name: 'strings', 
				path: 'js/i18n', 
				mode: 'map', 
				language: type, 
				cache: false,
				encoding: 'UTF-8',
				callback: function () {    
					var arr = $("[i18nid]");
					for (var i = 0; i < arr.length; i++) {
						var tempID = arr[i].id;
						arr[i].innerHTML=$.i18n.prop(arr[i].getAttribute('i18nid'));
						//$('#' + tempID).html($.i18n.prop(arr[i].getAttribute('i18nid')));
					}
				}
			});
		}

		function switchLang() {
			LANGUAGE_CODE = LANGUAGE_CODE == 'zh' ? 'en' : 'zh';
			loadProperties(LANGUAGE_CODE);
		}

		$(document).ready(function () {
			$.cookie('langcode', 'en');
			var LangcodeFromCookie=$.cookie('langcode');
			if(LangcodeFromCookie!='')
			{
				loadProperties(LangcodeFromCookie);
			}else
			{
				loadProperties(LANGUAGE_CODE);
			}
			//LANGUAGE_CODE = jQuery.i18n.normaliseLanguageCode({});   
			
		})
