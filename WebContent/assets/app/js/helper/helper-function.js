(function($) {
    $.fn.shorten = function (settings) {
     
        var config = {
            showChars: 100,
            ellipsesText: "...",
            moreText: "more",
            lessText: "less"
        };
 
        if (settings) {
            $.extend(config, settings);
        }
         
        $(document).off("click", '.morelink');
         
        $(document).on({click: function () {
 
                var $this = $(this);
                if ($this.hasClass('less')) {
                    $this.removeClass('less');
                    $this.html(config.moreText);
                } else {
                    $this.addClass('less');
                    $this.html(config.lessText);
                }
                $this.parent().prev().toggle();
                $this.prev().toggle();
                return false;
            }
        }, '.morelink');
 
        return this.each(function () {
            var $this = $(this);
            if($this.hasClass("shortened")) return;
             
            $this.addClass("shortened");
            var content = $this.html();
            if (content.length > config.showChars) {
                var c = content.substr(0, config.showChars);
                var h = content.substr(config.showChars, content.length - config.showChars);
                var html = c + '<span class="moreellipses">' + config.ellipsesText + ' </span><span class="morecontent"><span>' + h + '</span> <a href="#" class="morelink">' + config.moreText + '</a></span>';
                $this.html(html);
                $(".morecontent span").hide();
            }
        });
         
    };
 
 })(jQuery);

function shortenCharcters(selector,count){
	
	$(selector).shorten({
	    "showChars" : parseInt(count)
	});	
}

Ajax={		
		ajaxHelper :function(method,url,data,dataType,contentType){
			var output = "";
			if(data != undefined ){
				$.ajax({
					url:url,
					data:JSON.stringify(data),
					type:method,
					dataType: dataType,
					contentType:contentType,
					async:false,
					success:function(data){
						return output = data;
					}
				}).done(function(data){
					return output = data;
				}).error(function(jqXHR,textStatus,errorThrown){
					redirectSessionExpire(jqXHR);					
				});	
			}else{
				$.ajax({
					url:url,
					type:method,
					dataType: dataType,
					contentType:contentType,
					async:false,
					success:function(data){
						return output = data;
					}
				}).done(function(data){
					return output = data;
				}).error(function(jqXHR,textStatus,errorThrown){
					redirectSessionExpire(jqXHR);
				});
			}
			return output;			
		},
		get:function(url,data,dataType,contentType){
			var output = this.ajaxHelper("GET",url,data,dataType,contentType);
			return output;
		},
		post:function(url,data,dataType,contentType){
			var output = this.ajaxHelper("POST",url,data,dataType,contentType);
			return output;

		},
		put:function(url,data,dataType,contentType){
			var output = this.ajaxHelper("PUT",url,data,dataType,contentType);
			return output;
		}
};

function ajaxhelper(url){
	if(_.isString(url)){
		var output = "";
		$.ajax({
			type : 'GET',
			url : url,		
			async : false,
			success : function(data, textStatus, jqXHR){
				output = data;
			},
			error : function(jqXHR, textStatus, errorThrown){
				redirectSessionExpire(jqXHR);
			}
		});	
		return output;
	}
	else{
		console.log("url is not a string "+url);
	}
}

function ajaxhelperwithdata(url,data){
	if(_.isString(url))	{
		if(_.isObject(data) && !_.isArray(data)){
			var output = "";
			$.ajax({
				type : 'GET',
				url : url,			
				data:data,
				async : false,
				success : function(data, textStatus, jqXHR){
					output = data;
				},
				error : function(jqXHR, textStatus, errorThrown){
					redirectSessionExpire(jqXHR);
				}
			});	
			return output;
		}
		else{
			console.log("data is not an object"+data);	
		}
	}
	else{
		console.log("url is not a String"+url);	
	}
}

function ajaxhelperwithqueryparamdata(url,data,type){
	$.ajax
	({
		type : type,
		url : url+"?id="+data,	
		dataType: "json",
		contentType:"application/json",
		async : false,
		success : function(data, textStatus, jqXHR) 
		{
			output = data;
		},
		error : function(jqXHR, textStatus, errorThrown) 
		{
			redirectSessionExpire(jqXHR);
		}
	});
	return output;
}

function renderTemplate(templateselector,jsonData,outputSelector){
	var source   = $(templateselector).html();
	var template = Handlebars.compile(source);
	var templateHtml = template(jsonData);
	$(outputSelector).html(templateHtml);	
}

function checkfilesize(selector,type){
	var maxfilesize=0;
	switch(type){
	case "pr":
		maxfilesize = (30*1024*1024);
		break;
	case "lo":
		maxfilesize = (1*1024*1024);
		break;
	case "co":
		maxfilesize = (800*1024*1024);
		break;
	}
	var actualfilesize = $(selector)[0].files[0].size;
	if(actualfilesize>maxfilesize){
		return "no";
	}else{
		return "ok";
	}
}

function redirectSessionExpire(jqXHR){
	if(jqXHR.status==901){
		window.location.href=courseData.getContextPath()+"/sessionexpired";
	}else if(jqXHR.status==504){
		window.location.href=courseData.getContextPath()+"/sessionexpired";
	}	
}

Handlebars.registerHelper("math", function(lvalue, operator, rvalue, options) {
	lvalue = parseFloat(lvalue);
	rvalue = parseFloat(rvalue);

	return {
		"+": lvalue + rvalue,
		"-": lvalue - rvalue,
		"*": lvalue * rvalue,
		"/": lvalue / rvalue,
		"%": lvalue % rvalue
	}[operator];
});

Handlebars.registerHelper("twodecimal", function(timespent) {
	console.log("timespent:::"+timespent);
	if(timespent!=undefined)
	{
		timespent = parseFloat(timespent);  
		timespent=timespent.toFixed(2);
		return timespent+""+"Mins";
	}
	else
		return "0 Mins";
});

var rendered = Handlebars.compile(
		'{{#each values}}' + 
		'i+1 = {{math @index "+" 1}}',+
'{{/each}}');

countryjson=

	[
	 {
		 "name": "Afghanistan",
		 "code": "AF"
	 },
	 {
		 "name": "Åland Islands",
		 "code": "AX"
	 },
	 {
		 "name": "Albania",
		 "code": "AL"
	 },
	 {
		 "name": "Algeria",
		 "code": "DZ"
	 },
	 {
		 "name": "American Samoa",
		 "code": "AS"
	 },
	 {
		 "name": "AndorrA",
		 "code": "AD"
	 },
	 {
		 "name": "Angola",
		 "code": "AO"
	 },
	 {
		 "name": "Anguilla",
		 "code": "AI"
	 },
	 {
		 "name": "Antarctica",
		 "code": "AQ"
	 },
	 {
		 "name": "Antigua and Barbuda",
		 "code": "AG"
	 },
	 {
		 "name": "Argentina",
		 "code": "AR"
	 },
	 {
		 "name": "Armenia",
		 "code": "AM"
	 },
	 {
		 "name": "Aruba",
		 "code": "AW"
	 },
	 {
		 "name": "Australia",
		 "code": "AU"
	 },
	 {
		 "name": "Austria",
		 "code": "AT"
	 },
	 {
		 "name": "Azerbaijan",
		 "code": "AZ"
	 },
	 {
		 "name": "Bahamas",
		 "code": "BS"
	 },
	 {
		 "name": "Bahrain",
		 "code": "BH"
	 },
	 {
		 "name": "Bangladesh",
		 "code": "BD"
	 },
	 {
		 "name": "Barbados",
		 "code": "BB"
	 },
	 {
		 "name": "Belarus",
		 "code": "BY"
	 },
	 {
		 "name": "Belgium",
		 "code": "BE"
	 },
	 {
		 "name": "Belize",
		 "code": "BZ"
	 },
	 {
		 "name": "Benin",
		 "code": "BJ"
	 },
	 {
		 "name": "Bermuda",
		 "code": "BM"
	 },
	 {
		 "name": "Bhutan",
		 "code": "BT"
	 },
	 {
		 "name": "Bolivia",
		 "code": "BO"
	 },
	 {
		 "name": "Bosnia and Herzegovina",
		 "code": "BA"
	 },
	 {
		 "name": "Botswana",
		 "code": "BW"
	 },
	 {
		 "name": "Bouvet Island",
		 "code": "BV"
	 },
	 {
		 "name": "Brazil",
		 "code": "BR"
	 },
	 {
		 "name": "British Indian Ocean Territory",
		 "code": "IO"
	 },
	 {
		 "name": "Brunei Darussalam",
		 "code": "BN"
	 },
	 {
		 "name": "Bulgaria",
		 "code": "BG"
	 },
	 {
		 "name": "Burkina Faso",
		 "code": "BF"
	 },
	 {
		 "name": "Burundi",
		 "code": "BI"
	 },
	 {
		 "name": "Cambodia",
		 "code": "KH"
	 },
	 {
		 "name": "Cameroon",
		 "code": "CM"
	 },
	 {
		 "name": "Canada",
		 "code": "CA"
	 },
	 {
		 "name": "Cape Verde",
		 "code": "CV"
	 },
	 {
		 "name": "Cayman Islands",
		 "code": "KY"
	 },
	 {
		 "name": "Central African Republic",
		 "code": "CF"
	 },
	 {
		 "name": "Chad",
		 "code": "TD"
	 },
	 {
		 "name": "Chile",
		 "code": "CL"
	 },
	 {
		 "name": "China",
		 "code": "CN"
	 },
	 {
		 "name": "Christmas Island",
		 "code": "CX"
	 },
	 {
		 "name": "Cocos (Keeling) Islands",
		 "code": "CC"
	 },
	 {
		 "name": "Colombia",
		 "code": "CO"
	 },
	 {
		 "name": "Comoros",
		 "code": "KM"
	 },
	 {
		 "name": "Congo",
		 "code": "CG"
	 },
	 {
		 "name": "Congo, The Democratic Republic of the",
		 "code": "CD"
	 },
	 {
		 "name": "Cook Islands",
		 "code": "CK"
	 },
	 {
		 "name": "Costa Rica",
		 "code": "CR"
	 },
	 {
		 "name": "Cote D\"Ivoire",
		 "code": "CI"
	 },
	 {
		 "name": "Croatia",
		 "code": "HR"
	 },
	 {
		 "name": "Cuba",
		 "code": "CU"
	 },
	 {
		 "name": "Cyprus",
		 "code": "CY"
	 },
	 {
		 "name": "Czech Republic",
		 "code": "CZ"
	 },
	 {
		 "name": "Denmark",
		 "code": "DK"
	 },
	 {
		 "name": "Djibouti",
		 "code": "DJ"
	 },
	 {
		 "name": "Dominica",
		 "code": "DM"
	 },
	 {
		 "name": "Dominican Republic",
		 "code": "DO"
	 },
	 {
		 "name": "Ecuador",
		 "code": "EC"
	 },
	 {
		 "name": "Egypt",
		 "code": "EG"
	 },
	 {
		 "name": "El Salvador",
		 "code": "SV"
	 },
	 {
		 "name": "Equatorial Guinea",
		 "code": "GQ"
	 },
	 {
		 "name": "Eritrea",
		 "code": "ER"
	 },
	 {
		 "name": "Estonia",
		 "code": "EE"
	 },
	 {
		 "name": "Ethiopia",
		 "code": "ET"
	 },
	 {
		 "name": "Falkland Islands (Malvinas)",
		 "code": "FK"
	 },
	 {
		 "name": "Faroe Islands",
		 "code": "FO"
	 },
	 {
		 "name": "Fiji",
		 "code": "FJ"
	 },
	 {
		 "name": "Finland",
		 "code": "FI"
	 },
	 {
		 "name": "France",
		 "code": "FR"
	 },
	 {
		 "name": "French Guiana",
		 "code": "GF"
	 },
	 {
		 "name": "French Polynesia",
		 "code": "PF"
	 },
	 {
		 "name": "French Southern Territories",
		 "code": "TF"
	 },
	 {
		 "name": "Gabon",
		 "code": "GA"
	 },
	 {
		 "name": "Gambia",
		 "code": "GM"
	 },
	 {
		 "name": "Georgia",
		 "code": "GE"
	 },
	 {
		 "name": "Germany",
		 "code": "DE"
	 },
	 {
		 "name": "Ghana",
		 "code": "GH"
	 },
	 {
		 "name": "Gibraltar",
		 "code": "GI"
	 },
	 {
		 "name": "Greece",
		 "code": "GR"
	 },
	 {
		 "name": "Greenland",
		 "code": "GL"
	 },
	 {
		 "name": "Grenada",
		 "code": "GD"
	 },
	 {
		 "name": "Guadeloupe",
		 "code": "GP"
	 },
	 {
		 "name": "Guam",
		 "code": "GU"
	 },
	 {
		 "name": "Guatemala",
		 "code": "GT"
	 },
	 {
		 "name": "Guernsey",
		 "code": "GG"
	 },
	 {
		 "name": "Guinea",
		 "code": "GN"
	 },
	 {
		 "name": "Guinea-Bissau",
		 "code": "GW"
	 },
	 {
		 "name": "Guyana",
		 "code": "GY"
	 },
	 {
		 "name": "Haiti",
		 "code": "HT"
	 },
	 {
		 "name": "Heard Island and Mcdonald Islands",
		 "code": "HM"
	 },
	 {
		 "name": "Holy See (Vatican City State)",
		 "code": "VA"
	 },
	 {
		 "name": "Honduras",
		 "code": "HN"
	 },
	 {
		 "name": "Hong Kong",
		 "code": "HK"
	 },
	 {
		 "name": "Hungary",
		 "code": "HU"
	 },
	 {
		 "name": "Iceland",
		 "code": "IS"
	 },
	 {
		 "name": "India",
		 "code": "IN"
	 },
	 {
		 "name": "Indonesia",
		 "code": "ID"
	 },
	 {
		 "name": "Iran, Islamic Republic Of",
		 "code": "IR"
	 },
	 {
		 "name": "Iraq",
		 "code": "IQ"
	 },
	 {
		 "name": "Ireland",
		 "code": "IE"
	 },
	 {
		 "name": "Isle of Man",
		 "code": "IM"
	 },
	 {
		 "name": "Israel",
		 "code": "IL"
	 },
	 {
		 "name": "Italy",
		 "code": "IT"
	 },
	 {
		 "name": "Jamaica",
		 "code": "JM"
	 },
	 {
		 "name": "Japan",
		 "code": "JP"
	 },
	 {
		 "name": "Jersey",
		 "code": "JE"
	 },
	 {
		 "name": "Jordan",
		 "code": "JO"
	 },
	 {
		 "name": "Kazakhstan",
		 "code": "KZ"
	 },
	 {
		 "name": "Kenya",
		 "code": "KE"
	 },
	 {
		 "name": "Kiribati",
		 "code": "KI"
	 },
	 {
		 "name": "Korea, Democratic People\"S Republic of",
		 "code": "KP"
	 },
	 {
		 "name": "Korea, Republic of",
		 "code": "KR"
	 },
	 {
		 "name": "Kuwait",
		 "code": "KW"
	 },
	 {
		 "name": "Kyrgyzstan",
		 "code": "KG"
	 },
	 {
		 "name": "Lao People\"S Democratic Republic",
		 "code": "LA"
	 },
	 {
		 "name": "Latvia",
		 "code": "LV"
	 },
	 {
		 "name": "Lebanon",
		 "code": "LB"
	 },
	 {
		 "name": "Lesotho",
		 "code": "LS"
	 },
	 {
		 "name": "Liberia",
		 "code": "LR"
	 },
	 {
		 "name": "Libyan Arab Jamahiriya",
		 "code": "LY"
	 },
	 {
		 "name": "Liechtenstein",
		 "code": "LI"
	 },
	 {
		 "name": "Lithuania",
		 "code": "LT"
	 },
	 {
		 "name": "Luxembourg",
		 "code": "LU"
	 },
	 {
		 "name": "Macao",
		 "code": "MO"
	 },
	 {
		 "name": "Macedonia, The Former Yugoslav Republic of",
		 "code": "MK"
	 },
	 {
		 "name": "Madagascar",
		 "code": "MG"
	 },
	 {
		 "name": "Malawi",
		 "code": "MW"
	 },
	 {
		 "name": "Malaysia",
		 "code": "MY"
	 },
	 {
		 "name": "Maldives",
		 "code": "MV"
	 },
	 {
		 "name": "Mali",
		 "code": "ML"
	 },
	 {
		 "name": "Malta",
		 "code": "MT"
	 },
	 {
		 "name": "Marshall Islands",
		 "code": "MH"
	 },
	 {
		 "name": "Martinique",
		 "code": "MQ"
	 },
	 {
		 "name": "Mauritania",
		 "code": "MR"
	 },
	 {
		 "name": "Mauritius",
		 "code": "MU"
	 },
	 {
		 "name": "Mayotte",
		 "code": "YT"
	 },
	 {
		 "name": "Mexico",
		 "code": "MX"
	 },
	 {
		 "name": "Micronesia, Federated States of",
		 "code": "FM"
	 },
	 {
		 "name": "Moldova, Republic of",
		 "code": "MD"
	 },
	 {
		 "name": "Monaco",
		 "code": "MC"
	 },
	 {
		 "name": "Mongolia",
		 "code": "MN"
	 },
	 {
		 "name": "Montserrat",
		 "code": "MS"
	 },
	 {
		 "name": "Morocco",
		 "code": "MA"
	 },
	 {
		 "name": "Mozambique",
		 "code": "MZ"
	 },
	 {
		 "name": "Myanmar",
		 "code": "MM"
	 },
	 {
		 "name": "Namibia",
		 "code": "NA"
	 },
	 {
		 "name": "Nauru",
		 "code": "NR"
	 },
	 {
		 "name": "Nepal",
		 "code": "NP"
	 },
	 {
		 "name": "Netherlands",
		 "code": "NL"
	 },
	 {
		 "name": "Netherlands Antilles",
		 "code": "AN"
	 },
	 {
		 "name": "New Caledonia",
		 "code": "NC"
	 },
	 {
		 "name": "New Zealand",
		 "code": "NZ"
	 },
	 {
		 "name": "Nicaragua",
		 "code": "NI"
	 },
	 {
		 "name": "Niger",
		 "code": "NE"
	 },
	 {
		 "name": "Nigeria",
		 "code": "NG"
	 },
	 {
		 "name": "Niue",
		 "code": "NU"
	 },
	 {
		 "name": "Norfolk Island",
		 "code": "NF"
	 },
	 {
		 "name": "Northern Mariana Islands",
		 "code": "MP"
	 },
	 {
		 "name": "Norway",
		 "code": "NO"
	 },
	 {
		 "name": "Oman",
		 "code": "OM"
	 },
	 {
		 "name": "Pakistan",
		 "code": "PK"
	 },
	 {
		 "name": "Palau",
		 "code": "PW"
	 },
	 {
		 "name": "Palestinian Territory, Occupied",
		 "code": "PS"
	 },
	 {
		 "name": "Panama",
		 "code": "PA"
	 },
	 {
		 "name": "Papua New Guinea",
		 "code": "PG"
	 },
	 {
		 "name": "Paraguay",
		 "code": "PY"
	 },
	 {
		 "name": "Peru",
		 "code": "PE"
	 },
	 {
		 "name": "Philippines",
		 "code": "PH"
	 },
	 {
		 "name": "Pitcairn",
		 "code": "PN"
	 },
	 {
		 "name": "Poland",
		 "code": "PL"
	 },
	 {
		 "name": "Portugal",
		 "code": "PT"
	 },
	 {
		 "name": "Puerto Rico",
		 "code": "PR"
	 },
	 {
		 "name": "Qatar",
		 "code": "QA"
	 },
	 {
		 "name": "Reunion",
		 "code": "RE"
	 },
	 {
		 "name": "Romania",
		 "code": "RO"
	 },
	 {
		 "name": "Russian Federation",
		 "code": "RU"
	 },
	 {
		 "name": "RWANDA",
		 "code": "RW"
	 },
	 {
		 "name": "Saint Helena",
		 "code": "SH"
	 },
	 {
		 "name": "Saint Kitts and Nevis",
		 "code": "KN"
	 },
	 {
		 "name": "Saint Lucia",
		 "code": "LC"
	 },
	 {
		 "name": "Saint Pierre and Miquelon",
		 "code": "PM"
	 },
	 {
		 "name": "Saint Vincent and the Grenadines",
		 "code": "VC"
	 },
	 {
		 "name": "Samoa",
		 "code": "WS"
	 },
	 {
		 "name": "San Marino",
		 "code": "SM"
	 },
	 {
		 "name": "Sao Tome and Principe",
		 "code": "ST"
	 },
	 {
		 "name": "Saudi Arabia",
		 "code": "SA"
	 },
	 {
		 "name": "Senegal",
		 "code": "SN"
	 },
	 {
		 "name": "Serbia and Montenegro",
		 "code": "CS"
	 },
	 {
		 "name": "Seychelles",
		 "code": "SC"
	 },
	 {
		 "name": "Sierra Leone",
		 "code": "SL"
	 },
	 {
		 "name": "Singapore",
		 "code": "SG"
	 },
	 {
		 "name": "Slovakia",
		 "code": "SK"
	 },
	 {
		 "name": "Slovenia",
		 "code": "SI"
	 },
	 {
		 "name": "Solomon Islands",
		 "code": "SB"
	 },
	 {
		 "name": "Somalia",
		 "code": "SO"
	 },
	 {
		 "name": "South Africa",
		 "code": "ZA"
	 },
	 {
		 "name": "South Georgia and the South Sandwich Islands",
		 "code": "GS"
	 },
	 {
		 "name": "Spain",
		 "code": "ES"
	 },
	 {
		 "name": "Sri Lanka",
		 "code": "LK"
	 },
	 {
		 "name": "Sudan",
		 "code": "SD"
	 },
	 {
		 "name": "Suriname",
		 "code": "SR"
	 },
	 {
		 "name": "Svalbard and Jan Mayen",
		 "code": "SJ"
	 },
	 {
		 "name": "Swaziland",
		 "code": "SZ"
	 },
	 {
		 "name": "Sweden",
		 "code": "SE"
	 },
	 {
		 "name": "Switzerland",
		 "code": "CH"
	 },
	 {
		 "name": "Syrian Arab Republic",
		 "code": "SY"
	 },
	 {
		 "name": "Taiwan, Province of China",
		 "code": "TW"
	 },
	 {
		 "name": "Tajikistan",
		 "code": "TJ"
	 },
	 {
		 "name": "Tanzania, United Republic of",
		 "code": "TZ"
	 },
	 {
		 "name": "Thailand",
		 "code": "TH"
	 },
	 {
		 "name": "Timor-Leste",
		 "code": "TL"
	 },
	 {
		 "name": "Togo",
		 "code": "TG"
	 },
	 {
		 "name": "Tokelau",
		 "code": "TK"
	 },
	 {
		 "name": "Tonga",
		 "code": "TO"
	 },
	 {
		 "name": "Trinidad and Tobago",
		 "code": "TT"
	 },
	 {
		 "name": "Tunisia",
		 "code": "TN"
	 },
	 {
		 "name": "Turkey",
		 "code": "TR"
	 },
	 {
		 "name": "Turkmenistan",
		 "code": "TM"
	 },
	 {
		 "name": "Turks and Caicos Islands",
		 "code": "TC"
	 },
	 {
		 "name": "Tuvalu",
		 "code": "TV"
	 },
	 {
		 "name": "Uganda",
		 "code": "UG"
	 },
	 {
		 "name": "Ukraine",
		 "code": "UA"
	 },
	 {
		 "name": "United Arab Emirates",
		 "code": "AE"
	 },
	 {
		 "name": "United Kingdom",
		 "code": "GB"
	 },
	 {
		 "name": "United States",
		 "code": "US"
	 },
	 {
		 "name": "United States Minor Outlying Islands",
		 "code": "UM"
	 },
	 {
		 "name": "Uruguay",
		 "code": "UY"
	 },
	 {
		 "name": "Uzbekistan",
		 "code": "UZ"
	 },
	 {
		 "name": "Vanuatu",
		 "code": "VU"
	 },
	 {
		 "name": "Venezuela",
		 "code": "VE"
	 },
	 {
		 "name": "Viet Nam",
		 "code": "VN"
	 },
	 {
		 "name": "Virgin Islands, British",
		 "code": "VG"
	 },
	 {
		 "name": "Virgin Islands, U.S.",
		 "code": "VI"
	 },
	 {
		 "name": "Wallis and Futuna",
		 "code": "WF"
	 },
	 {
		 "name": "Western Sahara",
		 "code": "EH"
	 },
	 {
		 "name": "Yemen",
		 "code": "YE"
	 },
	 {
		 "name": "Zambia",
		 "code": "ZM"
	 },
	 {
		 "name": "Zimbabwe",
		 "code": "ZW"
	 }
	 ];



function applypagination(selector,rowstobeShown){
	spanOffset=100;			
	var totalPages=selector.length;
	spanCount=0;
	if(totalPages>spanOffset){
		spanCount=totalPages/spanOffset;
		span=1;
		currentSpan=spanCount-span;
		$('.pagination').text("");
		$('.pagination').append('<input id="previouspages" rel="'+span+'" class="page hover" type="button" value="<<">');
		$("#previouspages").addClass('active');
		$("#previouspages").prop("disabled",true);
	}else{
		span=1;
		spanOffset=totalPages;
	}
	renderPageNavigation(span,spanOffset);			       
	function renderPageNavigation(span,spanOffset){
		var currentPage=0;
		var rowsShown = rowstobeShown;
		var rowsTotal=0;
		if(span>spanCount){
			rowsTotal=totalPages;  
		}else{
			rowsTotal = span*spanOffset;
		}
		var pageNum=0;
		var i=(span-1)*spanOffset/10;
		if(rowsTotal>rowsShown)	{
			var numPages = Math.ceil(rowsTotal/rowsShown);
			$('.pagination').append('<input id="previous" rel="'+currentPage+'" class="page hover" type="button" 					value="<">');
			for(i;i < numPages;i++) {
				pageNum = i + 1;
				$('.pagination').append('&nbsp;<a href="javascript:void(0)" rel="'+i+'"class="page 					hover">'+pageNum+'</a> ');
			}
			$('.pagination').append('&nbsp;<input id="next" rel="'+currentPage+'"class="page hover"type="button" value=">">');
			selector.hide();
			selector.slice(0, rowsShown).show();
			$('.pagination a:first').addClass('active');
			$('.pagination a').bind('click', function(){
				if($(this).text()==pageNum&&$('#nextpages').is(':disabled')){
					$('#next').addClass('active');
					$("#next").prop("disabled","disabled");						    		  
				}else{
					$("#next").removeClass('active');
					$("#next").removeAttr("disabled");
				}  
				if($(this).text()==1){
					$("#previous").addClass('active');
					$("#previous").prop("disabled","disabled");
				}else{
					$("#previous").removeClass('active');
					$("#previous").removeAttr("disabled");
				}  
				$('.pagination a').removeClass('active');
				$(this).addClass('active');
				var currPage = $(this).attr('rel');
				var startItem = currPage * rowsShown;
				var endItem = startItem + rowsShown;
				$("#previous").attr('rel',currPage);
				$("#next").attr('rel',currPage);
				selector.css('opacity','0.0').hide().slice(startItem, endItem).
				//css('display','block').
				show().
				animate({opacity:1}, 300);
				if($('#nextpages').length==0&& $('.pagination a:last').hasClass('active')){
					$('#next').addClass('active');
					$('#next').prop('disabled',true);
				}
			});
			if($("#previous").attr('rel')==0){
				$("#previous").addClass('active');
				$("#previous").prop("disabled",true);
			}				      
			if(totalPages>spanOffset){
				$('.pagination').append('&nbsp;<input id="nextpages" rel="'+span+'" class="page hover" type="button" value=">>">');
			}
			if(span>=spanCount){   
				$("#nextpages").addClass('active');
				$("#nextpages").prop("disabled",true);
			}  
			if(span<=1){
				$("#previouspages").addClass('active');
				$("#previouspages").prop("disabled",true);
			}						   						      
			/**
			 * 
			 * Next span button function 
			 */
			$('#nextpages').click(function(){
				++span;
				$('.pagination').text("");
				$('.pagination').append('<input id="previouspages" rel="'+span+'"class="page hover"	type="button" value="<<">');
				renderPageNavigation(span,spanOffset);
				$('.pagination a:first').trigger('click');	                        	                             
			});

			/**
			 * 
			 * previous span button function 
			 */
			$('#previouspages').click(function(){
				--span;			            	                        
				$('.pagination').text("");
				$('.pagination').append('<input id="previouspages" rel="'+span+'" class="page hover"				type="button" value="<<">');
				renderPageNavigation(span,spanOffset);
				$('.pagination a:last').trigger('click');                	   	
			});

			/**
			 * 
			 * Next button function 
			 */
			$('#next').click(function () {					    	  
				if ((currPage * numPages) <= rowsTotal)
					currPage++;
				{   
					var currPage = $(this).attr('rel');
					$("a[rel='" + currPage + "']").removeClass('active');
					currPage=parseInt(currPage)+1;
					$("a[rel='" + currPage + "']").addClass('active');
					$("#previous").attr('rel',currPage);
					$("#next").attr('rel',currPage);
					console.log(currPage);
					var startItem = currPage * rowsShown;
					var endItem = startItem + rowsShown;
					selector.css('opacity','0.0').hide().slice(startItem, endItem).
					//css('display','block').
					show().
					animate({opacity:1}, 300);
				}
				if($("#previous").attr('rel')==1){
					$("#previous").removeClass("active");
					$("#previous").removeAttr("disabled");
				}
				if($('#nextpages').is(':enabled')){
					if(currPage==(parseInt(numPages))){
						$('#nextpages').trigger('click');
					}
				}
				else if(!$('#nextpages').is(':enabled')){
					if(currPage==(parseInt(numPages)-1)){
						$(this).addClass('active');
						$(this).prop("disabled",true);
					}
				}else{
					$(this).removeClass('active');
					$(this).removeAttr("disabled");
				}
				if(!$('.pagination a:first').hasClass('active')){
					$("#previous").prop("disabled",false);
				}
			});

			/**
			 * 
			 * Previous button function 
			 */
			$('#previous').click(function () {
				if($(this).attr('rel')==1){		
					$(this).addClass('active');
					$(this).prop("disabled",true);					                		  
				}else if($('#previouspages').is(':enabled')&&$('.pagination a:first').hasClass('active')){
					$("#previouspages").trigger('click');
				}else{
					$(this).removeClass('active');
					$(this).removeAttr("disabled");
				}
				if($("#next").attr('rel')==(parseInt(numPages)-1)){
					$("#next").removeClass("active");
					$("#next").removeAttr("disabled");
				}else{
					$("#next").removeClass("active");
					$("#next").prop("disabled",false);
				}
				if (currPage > 1) currPage--;
				{
					var currPage = $(this).attr('rel');
					$("a[rel='" + currPage + "']").removeClass('active');
					currPage=parseInt(currPage)-1;
					$("a[rel='" + currPage + "']").addClass('active');
					$("#previous").attr('rel',currPage);
					$("#next").attr('rel',currPage);
					var startItem = currPage * rowsShown;
					var endItem = startItem + rowsShown;
					selector.css('opacity','0.0').hide().slice(startItem, endItem).
					//css('display','block').
					show().
					animate({opacity:1}, 300);
				}
				if($("#next").attr('rel')==(parseInt(numPages)-1)){
					$("#next").removeAttr("disabled");
				}					                	
			});
		}
		return "";
	}
}