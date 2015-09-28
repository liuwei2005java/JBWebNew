function dateChk1(str){ //hh:mm
	var reg = /^(\d{2})\:(\d{2})$/;
	var r = str.match(reg);
	if(r==null){
		return false; 
	}else{
		if(r[1] < 0 || r[1] >= 24){
			return false;
		}
		if(r[2] < 0 || r[2] >= 60){
			return false;
		}
	}
	return true; 
} 

function dateChk2(str){ //yyyy/mm/dd hh:mm
	var s = str.split(" ");
	if(str != null && str.length == 16 && dateChk(s[0]) && dateChk1(s[1]) && s[1] != "" && s[0] != "")
	{
		return true;
	}
	else
	{
		return false;
	}
} 

function dateChk(str){ //yyyy/mm/dd
	var reg = /^(\d{4})\/(\d{2})\/(\d{2})$/;
	var r = str.match(reg);
	if(r==null){
		return false; 
	}else{
		if(r[1] <= 1950 || r[1] > 2050){
			return false;
		}
		if(r[2] <= 0 || r[2] > 12){
			return false;
		}
		if(r[3] <= 0 || r[3] > 31){
			return false;
		}
	}
	return true; 
} 

function initIdx()
{
	frm.pageIndex.value = 1;
	frm.submit();
}

//�����ѯ
function initIdxExt(v)
{
	frm.pageIndex.value = 1;
	frm.queryOpt.value=v;
	frm.submit();
}

//��ѯʱָ��form.action������
function queryBtn(url){
  frm.pageIndex.value=1;
  frm.action=url;
  frm.submit();
}

//��ѯʱָ��form.action������
function queryBtnExt(url,v){
  frm.pageIndex.value=1;
  frm.queryOpt.value=v;
  frm.action=url;
  frm.submit();
}

var undefined;

//������Ϣ����ȡ�����������
function warningValue(msg, obj) {
        obj.value="";
	alert(msg);
	if ( obj.disabled != true && obj.type!="hidden") {
		obj.focus();
		//obj.select();
	}
}

//���ǰ��ո�
function trim ( str ) {
	regExp = /\S/
	
	if ( regExp.test(str) == false )
		return "";
	
	regExp = /(^\s*)(.*\S)(\s*$)/
	regExp.exec(str);
	
	return RegExp.$2;
}

//���������ʽ
function checkEmail( obj )
{	
	//var obj = eval( valueName );
	var aEmail =  obj.value;

	regExp1 = /(^[^@]+@[^@.]+\.[^@]+$)/
	regExp2 = /(\.\.)/
	
	if ( regExp1.test(aEmail) == false || regExp2.test(aEmail) == true ) {
		warningValue ( aEmail + " ����һ���Ϸ���email��ַ!", obj );
		return false;
	}
	
	return true;
}

//�������ڸ�ʽ
function checkDate( aYearValue, aMonthValue, aDayValue )
{
    if(aYearValue == null || aYearValue.length != 4)
    	return false;
    var yearValue = parseInt(aYearValue);
    var monthValue; 
    if( aMonthValue.substring(0,1) == "0")
    	monthValue = parseInt( aMonthValue.substring(1,2) );
    else monthValue = parseInt(aMonthValue);
    var dayValue;
    if( aDayValue.substring(0,1) == "0")
    	dayValue = parseInt( aDayValue.substring(1,2) );
    else dayValue = parseInt(aDayValue);
		
	if ( isNaN(yearValue) || isNaN(monthValue) || isNaN(dayValue) ) {
		return false;
	}
		
	monthValue--;
		
	if ( yearValue >= 1900 && yearValue < 2000 )
		yearValue = yearValue-1900;
	
	var dateValue = new Date( yearValue, monthValue, dayValue );
	//alert(dateValue);
	if ( dateValue.getDate() != dayValue || 
	     dateValue.getMonth() != monthValue ||
	     dateValue.getYear() != yearValue )
		return false;
	
	return true;
}

function parseDate( aDateStr, aDateSeq )
{
    var aYearValue = "";
    var aMonthValue = "";
    var aDayValue = "";
    var aHourValue = "";
    var aMinuteValue = "";
    var aSecondValue = "";

	if ( aDateSeq == "" ) {	
		aYearValue = aDateStr.substring(0,4);
		aMonthValue = aDateStr.substring(4,6);
		aDayValue = aDateStr.substring(6,8);
	}
	else if ( aDateStr.indexOf(" ") < 0 ) {
		var regPattern = "(" + "^[0-9]+"+")("+aDateSeq +")";
		regExp = new RegExp(regPattern);
		if ( regExp.exec(aDateStr) != null )
			aYearValue = RegExp.$1;

		regPattern = "("+aDateSeq+")("+"[0-9]+"+")("+aDateSeq+")"
		regExp = new RegExp(regPattern);
		if ( regExp.exec(aDateStr) != null )
			aMonthValue = RegExp.$2;

		regPattern = "("+aDateSeq+")("+"[0-9]+$"+")";
		regExp = new RegExp(regPattern);
		if ( regExp.exec(aDateStr) != null )
			aDayValue = RegExp.$2;
	}
	else {
		var regPattern = "(^[0-9]+)-([0-9]+)-([0-9]+) ([0-9]+):([0-9]+)";
		regExp = new RegExp(regPattern);
		if ( regExp.exec(aDateStr) != null ) {
			aYearValue = RegExp.$1;
			aMonthValue = RegExp.$2;
			aDayValue = RegExp.$3;
			aHourValue = RegExp.$4;
			aMinuteValue = RegExp.$5;
			aSecondValue = 0;
		}
	}
	
	if (aHourValue == "")
		return new Date( aYearValue, aMonthValue-1, aDayValue );
	else
		return new Date( aYearValue, aMonthValue-1, aDayValue, aHourValue, aMinuteValue, aSecondValue );
}

function formatDate( date, sep, showTime, showSecond )
{
	var yearValue = date.getYear().toString();
	var monthValue = (date.getMonth() + 1).toString();
	if (monthValue.length < 2)
		monthValue = "0" + monthValue;
	var dayValue = date.getDate().toString();
	if (dayValue.length < 2)
		dayValue = "0" + dayValue;
	
	var dateStr = yearValue + sep + monthValue + sep + dayValue;
	
	if (showTime) {
		dateStr = dateStr + " " + formatTime(date, ":", showSecond);
	}
	
	return dateStr;
}

function formatTime( date, sep, showSecond )
{
	var hourValue = date.getHours().toString();
	if (hourValue.length < 2)
		hourValue = "0" + hourValue;
	var minuteValue = date.getMinutes().toString();
	if (minuteValue.length < 2)
		minuteValue = "0" + minuteValue;
	var secondValue = date.getSeconds().toString();
	if (secondValue.length < 2)
		secondValue = "0" + secondValue;
	
	var dateStr = hourValue + sep + minuteValue;
	
	if (showSecond)
		dateStr = dateStr + sep + secondValue;
	
	return dateStr;
}


function checkDateStr( aDateStr, aDateSeq )
{
    var aYearValue="";
    var aMonthValue="";
    var aDayValue="";

	if ( aDateSeq == "" ) {	
		aYearValue = aDateStr.substring(0,4);
		aMonthValue = aDateStr.substring(4,6);
		aDayValue = aDateStr.substring(6,8);
	}
	else {
		var regPattern = "(" + "^[0-9]+"+")("+aDateSeq +")";
		regExp = new RegExp(regPattern);
		if ( regExp.exec(aDateStr) != null )
			aYearValue = RegExp.$1;

		regPattern = "("+aDateSeq+")("+"[0-9]+"+")("+aDateSeq+")"
		regExp = new RegExp(regPattern);
		if ( regExp.exec(aDateStr) != null )
			aMonthValue = RegExp.$2;

		regPattern = "("+aDateSeq+")("+"[0-9]+$"+")";
		regExp = new RegExp(regPattern);
		if ( regExp.exec(aDateStr) != null )
			aDayValue = RegExp.$2;
	}
	
	return checkDate ( aYearValue, aMonthValue, aDayValue );
}

//����������Ƿ�ա����֡���С��Χ
function checkMlt ( obj, isNotNull, isNumber, minLength, maxLength, msg )
{
	//var obj = eval( valueName );
	var value = obj.value;
	
	if ( value.search("[^ \t]") == -1 )
	{
		if ( isNotNull == true )
		{
			warningValue ( msg + " ����Ϊ�ջ�ո�!", obj );
		 	return false;
		}
		else
			return true;
	}
	
	if ( isNumber == true )
	{
		if ( value.search("[^0-9]") != -1 )
		{
		 	warningValue ( msg + " ����Ϊ����!", obj );
		 	return false;
		 }
	}
	
	if ( minLength != 0 && maxLength == minLength )
	{
		if ( value.length != minLength )
		{
			warningValue ( msg + " ����Ϊ" + minLength + "λ!", obj );
		 	return false;
		 }
	}
	
	if ( minLength != 0 )
	{
		if ( value.length < minLength )
		{
			warningValue ( msg + " ��������" + minLength + "λ!", obj );
		 	return false;
		 }
	}
	
	if ( maxLength != 0 )
	{
		if ( value.length > maxLength )
		{
			warningValue ( msg + " ���ܶ���" + maxLength + "λ!", obj );
		 	return false;
		 }
	}
	
	return true;
}

//�ж��Ƿ�Ϊ���ָ����ͣ����ж��Ƿ����Ϊ�գ��Ƿ����Ϊ��
function checkNumber( obj, isNotNull, isNotZero, msg )
{
	//var obj = eval( valueName );
	var value = obj.value;
	var floatValue = new Number( value );

	if ( trim( value ) == "" )
	{
		if( isNotNull == true )
		{       
			warningValue( msg + " ����Ϊ��!", obj );
			return false;
		}
		else
			return true;
	}
	
	if ( isNaN( floatValue ) )
	{       
		warningValue( msg + value+" ����һ���Ϸ�����ֵ,����������!", obj );
		return false;
	}
	else
	{
		if ( isNotZero == true && floatValue == 0.0 )
		{
			warningValue( msg + " ����Ϊ��,����������!", obj );
			return false;
		}
	}
	
	return true;
}

//�ж�����ֵ�Ƿ�Ϊ��(����ո�Ҳ��Ϊ��)
function isNull( obj, msg )
{
	if ( ( obj.value.length < 1 ) )
	{
		if(obj.tagName == "INPUT" || obj.tagName == "TEXTAREA")
			warningValue( msg + "����Ϊ�գ�", obj );
		if(obj.tagName == "SELECT")
			warningValue( "��ѡ��" + msg + "��", obj );
		return false;
	}	
	else
	{  
	    var nSpace = 0;
		for( i = 0; i < obj.value.length; i++ )
		{   
			if( obj.value.charAt( i ) ==" " )
			{				    
		         nSpace++;			
			}
		}
		
		if ( obj.value.length.length == nSpace )	
		{
			if(obj.tagName == "INPUT" || obj.tagName == "TEXTAREA")
				warningValue( msg + "����Ϊ�գ�", obj );
			if(obj.tagName == "SELECT")
				warningValue( "��ѡ��" + msg + "��", obj );
				return false ;	
		}	
	}
	return true;
}

//�ж��Ƿ�Ϊ���ָ����ͣ����ж��Ƿ����Ϊ�գ��Ƿ����Ϊ�㣬�Լ�������ֵ�ֵ��
function checkRange( obj, isNotNull, isFloat, minValue, maxValue, msg )
{
	//var obj = eval( valueName );
	var value = obj.value;
	var floatValue = new Number( value );
	if ( trim( value ) == "" )
	{
		if( isNotNull == true )
		{
			warningValue( msg + " ����Ϊ��!", obj );
			return false;
		}
		else
			return true;
	}
	
	if ( isNaN( floatValue ) )
	{
		warningValue( msg + " ����һ���Ϸ�����ֵ!", obj );
		return false;
	}
	
	if ( isFloat == false && ( value.search( "\\." ) != -1 ) )
	{
		warningValue( msg + " ����������!", obj );
		return false;
	}
	
	if ( trim( minValue ) != "" )
	{
		var floatMinValue = new Number( minValue );
		if ( isNaN( floatMinValue ) )
		{
			warningValue( msg + " �趨����ֵ���޲���һ���Ϸ�����ֵ!", obj );
			return false;
		}
		else
		{
			if ( floatValue < floatMinValue )
			{
        		warningValue( msg + " ����С�� " + floatMinValue + "!", obj );
        		return false;
			}
		}
	}
	
	if ( trim( maxValue ) != "" )
	{
		var floatMaxValue = new Number( maxValue );
		if ( isNaN( floatMaxValue ) )
		{
			warningValue( msg + " �趨����ֵ���޲���һ���Ϸ�����ֵ!", obj );
			return false;
		}
		else
		{
			if ( floatValue > floatMaxValue )
			{
        		warningValue( msg + " ���ܴ��� " + floatMaxValue + "!", obj );
        		return false;
			}
		}
	}
	
	return true;
}

//�ж�Form�е�Ԫ�ص�ֵ�Ƿ���Ϲ淶
function checkValue( formId, callback )
{
	var objForm = document.getElementById( formId );
	var sFormID = formId;
	var chkRslt = true;
	for( var i=0; i < objForm.elements.length; i++)
	{
		if( objForm.elements[i].reqfv != undefined && objForm.elements[i].disabled != true )
		{
			
			/*if ( objForm.elements[i].id != "" )
				chkEltID = sFormID + "." + objForm.elements[i].id;
			else
				chkEltID = sFormID + "." + objForm.elements[i].name;*/
			chkEltID = objForm.elements[i];
			chkArg = objForm.elements[i].reqfv.split( ";" );
			switch ( chkArg[0].toUpperCase() )
			{
				case "EMAIL":
					chkRslt = checkEmail( chkEltID );
					break;
				case "REQUIRED":
					chkRslt = isNull( chkEltID, chkArg[1] );
					break;
				case "REQ_MLT":
					chkRslt = checkMlt( chkEltID, chkArg[1], chkArg[2], chkArg[3], chkArg[4], chkArg[5] );
					break;
				case "REQ_NUM":
					chkRslt = checkNumber( chkEltID, chkArg[1], chkArg[2], chkArg[3], chkArg[4]);
					break;
				case "REQ_RANGE":
					chkRslt = checkRange( chkEltID, chkArg[1], chkArg[2], chkArg[3], chkArg[4], chkArg[5] );
					break;
			}

		}
		if(!chkRslt)
			return chkRslt;
	}
	
	if ( callback != undefined )
		chkRslt = callback();

	return chkRslt;
}

//��ѡ����е� ��ֵ��Ҫ���ֵ��ͬ��ĳ��ѡ�� ��Ϊѡ��״̬
function selectoption( name, value )
{
	for ( i = 0; i<name.options.length; i++ ) 
	{
		if ( name.options[i].value == value )
		{
			name.options[i].selected = true;
		}
	}
}

//���ѡ����е�������
function removeOptionAll( name )
{
	for ( i = 0; i < name.options.length; i++ )
	{
		name.remove( name.options[i] );
		i--;
	}
}

//���ѡ�����ѡ�е���
function removeOption( name )
{
	for ( i = 0; i < name.options.length; i++)
	{
		if ( name.options[i].selected == true )
			name.remove( name.selectedIndex );
	}
}

//����ѡ����е���
function addOption( name, value, text )
{
	var oOption = document.createElement( "OPTION" );
	oOption.text = text;
	oOption.value = value;
	name.add( oOption );
}

//��ѡ�е�ѡ���һ��ѡ����ƶ�������һ��ѡ���
function moveOption( srcObj, dstObj )
{
	var srcObj = document.getElementById( srcObj );
	var dstObj = document.getElementById( dstObj );
	var rcyCount = srcObj.options.length;
	var srcValue;
	var srcText;
	
	for ( i = 0; i < srcObj.options.length; i++ )
	{
		if ( srcObj.options[i].selected == true )
		{
			srcValue = srcObj.options[i].value;
			srcText = srcObj.options[i].text;
			
			srcObj.remove( i );
			addOption( dstObj, srcValue, srcText );
			
			i--;
		}
	}
}
//��ȡSelect������ѡ���ֵ������һ���ַ�������һ��ֵΪValue�����ŷָ����ڶ���ֵΪText�����ŷָ�������ֵ�� ||| �ָ�
function getOption( srcObj )
{
    var strValue = "";
    var strText = "";
    for ( var i = 0; i < srcObj.options.length; i++ )
    {
        if ( i != ( srcObj.options.length - 1 ) )
        {
            strValue += srcObj.options[i].value + ",";
            strText += srcObj.options[i].text + ",";
        }
        else
        {
            strValue += srcObj.options[i].value;
            strText += srcObj.options[i].text;
        }   
    }
    
    var strReturn = strValue + "|||" + strText;
    return strReturn;
}
//����ѡ����е�ѡ����
function setOption( srcObj, aryOption )
{
	var aryOptValue = aryOption[0];
	var aryOptText = aryOption[1];
	
	var srcObj = document.getElementById( srcObj );
	
	for ( i = 0; i < aryOptValue.length; i++ )
	{
		addOption( srcObj, aryOptValue[i], aryOptText[i] );
	}
}
//����ѡ����е�ѡ����
function setOptionByString( srcObj, strValue, strText )
{
    if(srcObj&&strValue&&strText){
        var aryOptValue = strValue.split( "," );
        var aryOptText = strText.split( "," );
        
        //var srcObj = document.getElementById( srcObj );
        
        for ( var i = 0; i < aryOptValue.length; i++ )
        {
            if(aryOptValue[i]!=""&&aryOptText[i]!="")
               addOption( srcObj, aryOptValue[i], aryOptText[i] );
        }
    }
}
//ѡ�������
function onSelChg( dstObj, aryOptions )
{
	var srcObj = event.srcElement;
	var dstObj = document.getElementById( dstObj );
	var selIndex = srcObj.selectedIndex;
	
	removeOptionAll( dstObj );
	
	setOption( dstObj.id, aryOptions[selIndex] );
}


//��һ������еĶ�ѡ��ִ��ȫѡ/ȫ��ѡ�Ĳ���
//tblObj:������
//childName:checkbox�����ID
function selectAll( tblObj , childName )
{
  var objCheck = event.srcElement ;
  var collCheck;
  
  if ( tblObj != null )
  	var collCheck = tblObj.children[1].all.namedItem(childName);
  else
  	var collCheck = document.all.namedItem(childName);
  	
  if(collCheck)
  {
	  if(!collCheck.length)
	  	collCheck.checked = (objCheck.checked==true)? true:false;
	  else
	  {
	  	for(var i=0;i < collCheck.length;i++ )
		{
			collCheck[i].checked = (objCheck.checked==true)? true:false;
		}
	  }  
  }
  else
  {
  	objCheck.disabled = true;
  }
}

//ȷ��һ������е��б�ѡ�еļ�¼
//tblObj:������
//childName:checkbox��ID
function isSelNothing( tblObj , chkId )
{
	var collCheck = tblObj.children[0].all.namedItem(chkId);
    if(!collCheck)
        return -1;
    if(collCheck.checked)
	{
		if(collCheck.checked == true)
		 return false
		else
		  return true
	}
	if( collCheck.length < 1 )
	{
		return -1;
	}
	var noSelect = true;
	if(collCheck.checked == true)
	{
			noSelect = false;
	}
	else
	{
		for(var i=0;i < collCheck.length;i++ )
		{
			if( collCheck[i].checked == true )
			{
				noSelect = false;			
			}
		
		}
	}
	return noSelect;	
}

//��ѡ�и�ѡ���ֵ�������,����һ���ַ������淵��
function getValues(tblObj , chkId){
	var collCheck = tblObj.children[1].all.namedItem(chkId);
	var s="";
	if(collCheck.checked==true){
	  s=collCheck.value+",";	
	  return s;
	}else{
	  for(var i=0;i < collCheck.length;i++ )
		{ 
			if( collCheck[i].checked == true )
			{
				s=s+collCheck[i].value+",";			
			}
		}
		return s;
	}
}

//��ѡ�и�ѡ���ֵ�������(��Ʒ�ϱ����),�����ж�״̬�Ƿ��������,����һ���ַ������淵��
function getValuesExt(tblObj , chkId){
	var collCheck = tblObj.children[1].all.namedItem(chkId);
	var status = tblObj.children[1].all.namedItem("status");
	var code = tblObj.children[1].all.namedItem("code");
	var s="";
	var c=0;
	if(collCheck.checked==true){
	  if(status.value!=2){
	    alert(code.value+",״̬������ϱ�");
	    return "";
	  }else{
	    //s=collCheck.value+",";
	    //return s;
		alert("�ϱ�ʱ,ѡ����Ʒ���������2!");
		return false;
	  }
	}else{
	  for(var i=0;i < collCheck.length;i++ )
		{ 
			if( collCheck[i].checked == true )
			{
			    if(status[i].value!=2){
			      alert(code[i].value+",״̬������ϱ�");
			      s="";
			      break;
			    }else{
				  s=s+collCheck[i].value+",";
				  c=c+1;
				}			
			}
		}
		if(c<2){
		  alert("�ϱ�ʱ,ѡ����Ʒ���������2!");
		  return false;
		}else{
		  return s;
		}
	}
}

//��ָ���ı����ñ�׼�ļ�麯���������ݼ�飬���ͨ����Ա������ύ������
function submitFrm( objForm , target )
{
	if(!checkValue(objForm.name))
		return;
	if(target)
		objForm.target = target;
		
	alert(objForm.target)
	objForm.submit();
}

//��ģʽ�Ի���ҳ���ȡ����(���ݷ��ص�selectԪ����)
function getReturnValueWithBox( objBox , sUrl , sFeatures , callback )
{
	if(!sFeatures)
		sFeatures = "dialogWidth:600px;dialogHeight:460px;center:yes;dialogHide: no ;edge:raised; resizable:no;status:no;unadorned:no;scroll:no ";
	var collOptions = objBox.options;
	var obj=new Object();
	obj.value = "";
	obj.name = "";
	for( var i=0 ; i<collOptions.length;i++ )
	{
		obj.value = obj.value + collOptions[i].value + ",";
		obj.name = obj.name + collOptions[i].innerText + ",";
	}
	var sReturnValue = window.showModalDialog( sUrl , obj , sFeatures );
	if (sReturnValue)
	{
		putItemToBox( sReturnValue , objBox );
		if (callback)
			callback();
	}
}

function putItemToBox( obj , objBox )
{
	if(obj.value)
	{
		var valueArr = obj.value.split(",")
		var nameArr = obj.name.split(",")
		for( var i = 0; i < valueArr.length ; i++ )
		{
			if(!checkIsExist(valueArr[i] , objBox))
			{
				if(nameArr[i]!="")
				{
					var oOption = document.createElement("OPTION");
					objBox.options.add(oOption);
					oOption.value = valueArr[i];
					oOption.innerText = nameArr[i];
				}
			}
		}
	}
}


function getReturnValueWithBox_2( objBox , sUrl , sFeatures , callback )
{
	if(!sFeatures)
		sFeatures = "dialogWidth:600px;dialogHeight:460px;center:yes;dialogHide: no ;edge:raised; resizable:no;status:no;unadorned:no;scroll:no ";
	var collOptions = objBox.options;
	var arrObj=new Array();
	for( var i=0 ; i<collOptions.length;i++ )
	{
		arrObj[i] = collOptions[i].cloneNode
	}
	var sReturnValue = window.showModalDialog( sUrl , arrObj , sFeatures );
	if (sReturnValue)
	{
		putItemToBox_2( sReturnValue , objBox );
		if (callback)
			callback();
	}
}

function putItemToBox_2( arrObj , objBox )
{
	for(var i=0;i<arrObj.length;i++)
	{
		if(!checkIsExist(arrObj[i].value , objBox))
		{
			var oOption = document.createElement("OPTION");
			objBox.options.add(oOption);
			oOption.value = arrObj[i].value;
			oOption.text = arrObj[i].text;
			for(var j=0;j<arrObj[i].attributes.length;j++)
			{
				oOption.setAttribute(arrObj[i].attributes[j].name,arrObj[i].attributes[j].value)
			}
		}
	}
}



//��ģʽ�Ի���ҳ���ȡ����(��ѡģʽ)
function getReturnValue_M( spanObj , sUrl , sFeatures , callback )
{
	if(!sFeatures)
		sFeatures = "dialogWidth:600px;dialogHeight:460px;center:yes;dialogHide: no ;edge:raised; resizable:no;status:no;unadorned:no;scroll=no ";
	var objArr = new Array();
	var objColl = spanObj.all;
	var obj = new Object();
	for(var i=0;i<objColl.length;i++)
	{
		if(objColl[i].mapId && !objColl[i].mapName)
		{
			if(objColl[i].tagName == "SELECT")
			{
				obj.value = objColl[i].value;
			}
			if(objColl[i].tagName == "INPUT" || objColl[i].tagName=="TEXTAREA")
			{
				obj.value = objColl[i].value;
			}
			if(objColl[i].tagName == "SPAN" ||objColl[i].tagName == "DIV"  )
			{
				obj.value = objColl[i].innerText;
			}
		}
		if(objColl[i].mapName)
		{
			if(objColl[i].tagName == "SELECT")
			{
				obj.name = objColl[i].value;
			}
			if(objColl[i].tagName == "INPUT" || objColl[i].tagName=="TEXTAREA")
			{
				obj.name = objColl[i].value;
			}
			if(objColl[i].tagName == "SPAN" ||objColl[i].tagName == "DIV"  )
			{
				obj.name = objColl[i].innerText;
			}
		}
		
	}
	var sReturnValue = window.showModalDialog( sUrl , obj , sFeatures );
	if (sReturnValue)
	{
    	for(var i=0;i<objColl.length;i++)
    	{
    		if(objColl[i].mapId && !objColl[i].mapName)
    		{
    			if(objColl[i].tagName == "SELECT")
    			{
    				objColl[i].value = sReturnValue.value;
    			}
    			if(objColl[i].tagName == "INPUT" || objColl[i].tagName=="TEXTAREA")
    			{
    				objColl[i].value = sReturnValue.value;
    			}
    			if(objColl[i].tagName == "SPAN" ||objColl[i].tagName == "DIV"  )
    			{
    				objColl[i].innerText = sReturnValue.value;
    			}
    		}
    		if(objColl[i].mapName)
    		{
     			if(objColl[i].tagName == "SELECT")
    			{
    				objColl[i].value = sReturnValue.name ;
    			}
    			if(objColl[i].tagName == "INPUT" || objColl[i].tagName=="TEXTAREA")
    			{
    				objColl[i].value = sReturnValue.name ;
    			}
    			if(objColl[i].tagName == "SPAN" ||objColl[i].tagName == "DIV"  )
    			{
    				objColl[i].innerText = sReturnValue.name ;
    			}    			

    		}
    		
    	}
    	if (callback)
    		callback();
    }
	
}

//��ģʽ�Ի���ҳ���ȡ����(��ѡģʽ)
function getReturnValue_S( spanObj , sUrl , sFeatures , callback )
{
	if(!sFeatures)
		sFeatures = "dialogWidth:600px;dialogHeight:460px;center:yes;dialogHide: no ;edge:raised; resizable:no;status:no;unadorned:no;scroll:yes; ";
	
	var objArr = new Array();
	var objColl = spanObj.all;
	var j = 0;
	for(var i=0;i<objColl.length;i++)
	{
		if( objColl[i].mapId )
		{
			var obj = new Object();
			obj.id = objColl[i].id;
			obj.mapId = objColl[i].mapId;
			obj.text = "";
			if(objColl[i].tagName == "SPAN")
			{
				obj.value = objColl[i].innerText;	
			}
			if(objColl[i].tagName == "INPUT")
			{
				obj.value = objColl[i].value;			
			}
			if(objColl[i].tagName == "SELECT")
			{
				obj.value = objColl[i].value;
				obj.text = objColl[i].options[objColl[i].selectedIndex].value;	
			}
			if(objColl[i].tagName == "TEXTAREA")
			{
				obj.value = objColl[i].innerText;	
			}								
			objArr[j] = obj;
			j++;
		}	
	}
	var sReturnValue = window.showModalDialog( sUrl , objArr , sFeatures );
	if(sReturnValue)
	{
		setReturnObj ( spanObj, sReturnValue );
		if ( callback )
			callback();
	}
}

function getReturnObj( obj, returnObj )
{
	var valueColl = obj.all;
	for( var i = 0 ; i < valueColl.length ; i++ )
	{
		 if(valueColl[i].mapId)
		 {
	 		 for( var j = 0 ; j < returnObj.length ; j++ )
			 {
			 	if( returnObj[j].mapId == valueColl[i].mapId )
			 	{
			 			if( valueColl[i].tagName == "SPAN" )
			 				returnObj[j].value =  valueColl[i].innerText;
			 			if( valueColl[i].tagName == "INPUT" )
			 				returnObj[j].value =  valueColl[i].value;
			 			if( valueColl[i].tagName == "SELECT" )
			 			{
			 				if(valueColl[i].value)
			 				{
				 				returnObj[j].value =  valueColl[i].value;
				 				returnObj[j].text =  valueColl[i].options[valueColl[i].selectedIndex].text;			 				
			 				}
			 			}
			 			if( valueColl[i].tagName == "TEXTAREA" )
			 				returnObj[j].value =  valueColl[i].innerText;
			 				
			 	}
			 }
		 }
	}
	return returnObj;
}

function onclickRadio()
{
	var obj=event.srcElement;
	var parentObj = obj.parentNode;
	while(parentObj.tagName!="TR")
	{
		parentObj = parentObj.parentNode
	}
	parentObj.bg
	returnObj = getReturnObj( parentObj , returnObj )
}

function setReturnObj( obj, returnObj )
{
	var valueColl = obj.all;
	for( var i = 0 ; i < valueColl.length ; i++ )
	{
		 if(valueColl[i].mapId)
		 {
	 		 for( var j = 0 ; j < returnObj.length ; j++ )
			 {
			 	if( returnObj[j].mapId == valueColl[i].mapId )
			 	{
 					setTagValue( returnObj[j] , valueColl[i] )	 				
			 	}
			 }
		 }
	}
	return returnObj;
}

function setTagValue( srcObj , tagObj)
{
	if( tagObj.tagName == "SPAN" || tagObj.tagName == "TEXTAREA" ||  tagObj.tagName == "DIV")
	{
		if(tagObj.mapText)
			tagObj.innerText =  srcObj.text;
		else
			tagObj.innerText =  srcObj.value;
											
	}
	if( tagObj.tagName == "INPUT"   )
	{
		if(tagObj.mapText)
			tagObj.value =  srcObj.text;
		else
			tagObj.value =  srcObj.value;
	}
	if( tagObj.tagName == "SELECT")
	{
		var collopt = tagObj.options
		if(srcObj.value)
		{
			if(tagObj.mapText)
			{

			tagObj.value =  srcObj.text;
			}
			else
			{
				
			
				tagObj.value =  srcObj.value;	
			}
				
		}

	}
}

function addItemToBox( objTbl , objBox , sChkId )
{
	var collChk = objTbl.children[1].all.namedItem(sChkId);
	if(!collChk)
	{
		alert("�Ҳ���" + sChkId + "Ԫ�ؼ��ϣ�����Ԫ�������Ƿ���ȷ!")
	}	
	if(collChk.tagName == "INPUT")
	{
		if( collChk.checked )
		{
			var objTr = objTbl.children[1].rows[0];
			var oOption = createOptionItem( objTr , objBox );	
		}	
		return true;	
	}
	for( var i = 0 ; i < collChk.length ; i++ )
	{
		
		if( collChk[i].checked )
		{
			var objTr = objTbl.children[1].rows[i];
			var oOption = createOptionItem( objTr , objBox );	
		}
	}
}

function createOptionItem( objTr , objBox )
{
	var collObj = objTr.all;
	var obj = new Object();
	for( var i = 0 ; i < collObj.length ; i++ )
	{
		if(collObj[i].mapId)
		{
			if(collObj[i].tagName == "INPUT" || collObj[i].tagName=="TEXTAREA")
				obj.value = collObj[i].value;
			if(collObj[i].tagName == "SPAN" || collObj[i].tagName == "DIV" )
				obj.value = collObj[i].innerText;
		}
		if(collObj[i].mapName)
		{
			if(collObj[i].tagName == "INPUT" || collObj[i].tagName=="TEXTAREA")
				obj.name = collObj[i].value;
			if(collObj[i].tagName == "SPAN" || collObj[i].tagName == "DIV" )
				obj.name = collObj[i].innerText;
		}		
		if(collObj[i].endowmentInsuranceBase)
		{
			if(collObj[i].tagName == "INPUT" || collObj[i].tagName=="TEXTAREA")
				obj.endowmentInsuranceBase = collObj[i].value;
			if(collObj[i].tagName == "SPAN" || collObj[i].tagName == "DIV" )
				obj.endowmentInsuranceBase = collObj[i].innerText;		
		}
		if(collObj[i].idlenessInsureBase)
		{
			if(collObj[i].tagName == "INPUT" || collObj[i].tagName=="TEXTAREA")
				obj.idlenessInsureBase = collObj[i].value;
			if(collObj[i].tagName == "SPAN" || collObj[i].tagName == "DIV" )
				obj.idlenessInsureBase = collObj[i].innerText;		
		}	
		if(collObj[i].workHurtInsureBase)
		{
			if(collObj[i].tagName == "INPUT" || collObj[i].tagName=="TEXTAREA")
				obj.workHurtInsureBase = collObj[i].value;
			if(collObj[i].tagName == "SPAN" || collObj[i].tagName == "DIV" )
				obj.workHurtInsureBase = collObj[i].innerText;		
		}		
		if(collObj[i].medicareBase)
		{
			if(collObj[i].tagName == "INPUT" || collObj[i].tagName=="TEXTAREA")
				obj.medicareBase = collObj[i].value;
			if(collObj[i].tagName == "SPAN" || collObj[i].tagName == "DIV" )
				obj.medicareBase = collObj[i].innerText;		
		}
		if(collObj[i].accumulationFundBase)
		{
			if(collObj[i].tagName == "INPUT" || collObj[i].tagName=="TEXTAREA")
				obj.accumulationFundBase = collObj[i].value;
			if(collObj[i].tagName == "SPAN" || collObj[i].tagName == "DIV" )
				obj.accumulationFundBase = collObj[i].innerText;		
		}																		
	}
	if(checkIsExist(obj.value,objBox))
		return false;
	var oOption = document.createElement("OPTION");
	objBox.options.add(oOption);
	oOption.value = obj.value;
	oOption.innerText = obj.name;
	oOption.endowmentInsuranceBase = obj.endowmentInsuranceBase;
	oOption.idlenessInsureBase = obj.idlenessInsureBase;
	oOption.workHurtInsureBase = obj.workHurtInsureBase;
	oOption.medicareBase = obj.medicareBase;
	oOption.accumulationFundBase = obj.accumulationFundBase;
}

function checkIsExist( id ,objBox )
{
	var collOptions = objBox.options;
	for(var i=0;i<collOptions.length;i++)
	{
		if( collOptions[i].value == id )
			return true;
	}
	return false;
}

function getReturnValueFromListBox( objBox )
{
	var collOptions = objBox.options;
	var obj = new Object();
	obj.value = "";
	obj.name = "";
	for(var i=0;i<collOptions.length;i++)
	{
	
		obj.value =obj.value + collOptions[i].value 
		obj.name = obj.name + collOptions[i].innerText;
		if( i < collOptions.length - 1 )
		{
			obj.value += ",";
			obj.name += ",";
		}
	}
	return obj;
}

function getReturnValueFromListBox_2( objBox )
{
	var collOptions = objBox.options;
	var objArr = new Array();
	for(var i=0;i < collOptions.length;i++)
	{
		//var attrColl = collOptions[i].attributes;
		/*var obj = new Object();
		obj.value = collOptions[i].value;
		obj.name = collOptions[i].text;*/
		objArr[i] =collOptions[i];
	}
	return objArr;
}

function deleteItemFromBox( objBox )
{
	var collOptions = objBox.options;
	for(var i=0;i<collOptions.length;i++)
	{
		if(collOptions[i].selected)
		{
			collOptions.remove(i);
		}
	}
}
//ɾ��ѡ��checkbox����
function deleteItem(itemObjId)
{
	var boxes = document.all.namedItem(itemObjId);
	if (boxes) {
		if ( !boxes.length) {
			if (boxes.checked) {
				var tr = boxes.parentNode;
				while (tr.tagName != "TR")
					tr = tr.parentNode;
				tr.removeNode(true);
			}
		}
		else {
			for (var i = boxes.length - 1; i >= 0; i--) {
				if (boxes[i].checked) {
					var tr = boxes[i].parentNode;
					while (tr.tagName != "TR")
						tr = tr.parentNode;
					tr.removeNode(true);
				}
			}
		}
	}
}

function gotoPage(sUrl)
{
	window.location = sUrl;
}

function clickTr(opt)
{
	var objTr=event.srcElement;
	while(objTr.tagName!="TR")
	{
		objTr=objTr.parentNode;
	}
	
	var objTbl = objTr
	while(objTbl.tagName!="TABLE")
	{
		objTbl=objTbl.parentNode;
	}
	
	for ( var i=0; i < objTbl.children[1].children.length ; i++) //>
	{
			objTbl.children[1].children[i].className = "";
	}
	
	objTr.className = "FOCUS";
	
	//var resId = objTr.children[0].children[0].innerText;
	//location = basePath + "/servlet/actionservlet?actionId=loadAssetCard&resId=" + resId;
}

function selectUser( objSpan , sUrl , sFeature, callback )
{
	if(!sUrl)
		sUrl = basePath + "/sysmanage/usermanage/publicUserSelect.vm";
	if(!sFeature)
		sFeature = sFeatures = "dialogWidth:600px;dialogHeight:510px;center:yes;dialogHide: no ;edge:raised; resizable:no;status:no;unadorned:no;scroll:yes; ";
	getReturnValue_S( objSpan, sUrl, sFeature, callback );
}

function resizeTbl( oTbl )
{
	//alert(oTbl.getBoundingClientRect().bottom)
}

function changeSubSelItem( childName )
{
	var objParent = event.srcElement;
	var objChild = document.all.namedItem(childName)
	if(!objParent.subItem)
	{
		var arrObj = new Array();
		for(var i=0;i< objChild.options.length;i++)
		{
			arrObj[i] = objChild.options[i]
		}
		objParent.subItem = arrObj;
	}
			
	var parentCode = objParent.value;
	var collOpt = objChild.options;
	for( var i = collOpt.length - 1 ; i > 0  ; i-- )
	{
		collOpt.remove(i);
	}
	for( var i = 0 ; i < objParent.subItem.length  ; i++ )
	{
		if(objParent.subItem[i].parentCode == parentCode)
		{
			var oOption = document.createElement("OPTION");
			objChild.options.add(oOption);
			oOption.value = objParent.subItem[i].value;
			oOption.text = objParent.subItem[i].text;
		}
	}
}

function checkObjValue( objName , objValue , objInfo )
{
	var obj = document.all.namedItem(objName);
	if( obj.value == objValue )
	{
		alert("��������" + objInfo);
		obj.focus();
	}
}

function regTemplate( objTable )
{
	if (!window[objTable.id + "Template"]) {
		window[objTable.id + "Template"] = objTable.tBodies[0].cloneNode(true);
		while (objTable.tBodies[0].children.length > 0)
			objTable.tBodies[0].children[objTable.tBodies[0].children.length - 1].removeNode(true);
	}
}

function copyTemplateRows( objTable, objTBody )
{
	for (var i = 0; i < objTBody.children.length; i++)
	{
		var ObjTR = objTBody.children[i].cloneNode ( true );
		ObjTR.style.display = "";
		objTable.tBodies[0].insertAdjacentElement ( "beforeEnd", ObjTR );
	}
}

function showTblBoay(oTbl)
{
	oTbl.tBodies[0].style.display = (oTbl.tBodies[0].style.display=="")?"none":"";
}

function selectClassTree(objSpan , schemaId , schemaName , sFeature, callback)
{
	var	sUrl = basePath + "/lib/public/selectClassTree.vm?schemaId=" + schemaId + "&schemaName="+schemaName;
	if(!sFeature)
		sFeature = "dialogWidth:400px;dialogHeight:300px;center:yes;dialogHide: no ;edge:raised; resizable:no;status:no;unadorned:no;scroll:yes ";
	getReturnValue_S( objSpan, sUrl, sFeature, callback );
}

//����ļ�ѡ���
function addFileItem(objTbl,schemaId , fileName){
    
    //if(objTbl.style.display=="none")
   // {
        //objTbl.style.display="";
  //  }
     
           
    var fileItemId = document.createElement("input");
    fileItemId.name="fileItemId";
    fileItemId.id = "fileItemId";
    fileItemId.type="checkbox";

            
        
    var fileItemName = document.createElement("input");
    fileItemName.name = "_"+schemaId+"_attachment_name";
    fileItemName.id =  "_"+schemaId+"_attachment_name";
    fileItemName.type = "text";
    fileItemName.className = "text";
    fileItemName.size = "20";
    fileItemName.reqfv= "required;�ļ���";
    if(fileName)
    {
    	fileItemName.value = fileName;
    	fileItemName.readOnly = true;
    }
            
    
    
    var fileItem = document.createElement("input");
    fileItem.id = "fileInput";
    fileItem.name = "fileInput";
    fileItem.type = "file";
    fileItem.className = "text";
    fileItem.style.width="80%";
    fileItem.reqfv= "required;�ļ�·��";
 

    var fileItemDescription = document.createElement("input");
    fileItemDescription.name="_"+schemaId+"_attachment_description";
    fileItemDescription.id = "_"+schemaId+"_attachment_description";
    fileItemDescription.type = "text";
    fileItemDescription.className = "text";
    fileItemDescription.size= "15";
 
 
    var tdFileItemId = document.createElement("td");
    tdFileItemId.className = "td_view";
    tdFileItemId.align="left";
    tdFileItemId.width="2%";
    tdFileItemId.appendChild(fileItemId);
     
    var tdFileItemName = document.createElement("td");
    tdFileItemName.className = "";
    tdFileItemName.align="left";
    tdFileItemName.width="15%";
    tdFileItemName.appendChild(fileItemName);
    
    var tdFileItem = document.createElement("td");
    tdFileItem.className = "";
    tdFileItem.align="left";
    tdFileItem.width="68%";
    tdFileItem.appendChild(fileItem);      
        
    var tdFileItemDescription  = document.createElement("td");
    tdFileItemDescription.className = "";
    tdFileItemDescription.align="left";
    tdFileItemDescription.width="15%";
    tdFileItemDescription.appendChild(fileItemDescription);
     
 
     
            
    var tr = document.createElement("tr");
    tr.appendChild(tdFileItemId);
    tr.appendChild(tdFileItemName);
    tr.appendChild(tdFileItem);
    tr.appendChild(tdFileItemDescription);
   
    
            
    objTbl.children[1].appendChild(tr);
    fileItemName.focus();
    

}

function deleteRec(frm_delete,tableList,checkName)
{
	if(isSelNothing(tableList,checkName) == -1)
	{
		alert ( "û�п��Բ��������ݣ�" );
		return false;
	}
	if(isSelNothing(tableList,checkName))
	{
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
	}
	if(confirm("��ȷʵҪ����ѡ��������"))
	{
		frm.add.value = 'do';
		frm_delete.submit();
		//return true;
	}
	else
	{
		return false;
	}
}

//�ж��Ƿ�ѡ������Ҫ����������,��������Ӧ�Ĳ���
function deleteRecExt(frm_delete,tableList,checkName,agr)
{
	if(isSelNothing(tableList,checkName) == -1)
	{
		alert ( "û�п��Բ��������ݣ�" );
		return false;
	}
	if(isSelNothing(tableList,checkName))
	{
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
	}
	if(confirm("��ȷʵҪ����ѡ��������"))
	{
		if(agr==1)
		  frm.opt.value="ɾ��";
		if(agr==2)
		  frm.opt.value="��ֹ����";
		if(agr==3)
		  frm.opt.value="�ָ�����";
		if(agr==4)
		  frm.opt.value="nouse";
		if(agr==5)
		  frm.opt.value="use";
		if(agr==6)
		  frm.opt.value="estakey";//(�����̹���)����Ĭ��key
		if(agr==7)
		  frm.opt.value="returnContract";//���ص�����ͬ
		if(agr==8)
		  frm.opt.value="delGoods";//���ػ����¼
		if(agr==9)
		  frm.opt.value="retFile";//��ͬ�鵵
        if(agr==10)
		  frm.opt.value="emptyKey";//(�����̹���)����keyδ����״̬
		if(agr==11)
          frm.opt.value="estakey";//(��Ա����)����Ĭ��key
		if(agr==12)
          frm.opt.value="emptyKey";//(��Ա����)����keyδ����״̬
		if(agr==13)
          frm.opt.value="estakey";//(ϵͳ�û�����)����Ĭ��key
		if(agr==14)
          frm.opt.value="emptyKey";//(ϵͳ�û�����)����keyδ����״̬
		if(agr==15)
          frm.opt.value="concelExeResult";//������ִͬ�н��
    if(agr==16)
          frm.opt.value="repairContract";//������ִͬ�н��
    if(agr==18)
          frm.opt.value="Auditing";//����
    if(agr==19)
          frm.opt.value="blacklist";//�ڻ�
		frm.submit();
		//return true;
	}
	else
	{
		return false;
	}
}

//�ж��Ƿ�ѡ������Ҫ����������,ֻ��ѡ��һ����¼,������ӡ
function printRec(frm_delete,tableList,checkName)
{
	if(isSelNothing(tableList,checkName) == -1)
	{
		alert ( "û�п��Բ��������ݣ�" );
		return false;
	}
	if(isSelNothing(tableList,checkName))
	{
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
	}
	var collCheck = tableList.children[1].all.namedItem(checkName);
	var printedFlag=tableList.children[1].all.namedItem("printedFlag");
	var printUserCode=tableList.children[1].all.namedItem("printedUserCode");
	var printBalance=tableList.children[1].all.namedItem("printBalance");
	var printValue="";//�жϼ�¼�Ƿ��Ѿ���ӡ
	var userCodeValue="";//�ж��Ƿ��н��״����Ƿ�Ϊ��
	var userId="";
	var c=0;
	if(collCheck.length>1){//�ж���ѡ���ǵڼ�����¼(����ѯ�ļ�¼����һ��)
	  for(i=0;i<collCheck.length;i++){
	    if(collCheck[i].checked==true){
		  printValue=printedFlag[i].value;
		  userCodeValue=printUserCode[i].value;
		  userId=collCheck[i].value;
		  /*if(parseInt(printBalance[i].value)<=0){
		      alert("�ʽ�����,���ܴ�ӡȷ�ϵ�!");
			  return false;
		  }*/
	      c++;
	    }
	  }
	}else{//����ѯ�ļ�¼ֻ��һ��
	  printValue=printedFlag.value;
	  userCodeValue=printUserCode.value;
	  userId=collCheck.value;
	  /*if(parseInt(printBalance.value)<=0){
		  alert("�ʽ�����,���ܴ�ӡȷ�ϵ�!");
		  return false;
	  }*/
	}
    if(c > 1 )
	{ 
	  alert("һ��ֻ�ܲ���һ������!");
	  return false;
	}
	if(userCodeValue==null||userCodeValue==""){
	  alert("��Աû�в��������̴���,�������ÿ���!");
	  return false;
	}
	else{
	  //alert(printValue);
	  //if(printValue!=null&&printValue!=""){
	  //	if(confirm("��ѡ���Ѿ����ù�����,�Ƿ��ٴ����ÿ��")){//�Ѿ���ӡ��,ѯ���Ƿ������ӡ
	  //    if(confirm("��ȷʵҪ���ÿ�����")){
	  //	    frm_delete.opt.value="print";
	  //      frm.submit();
	  //    }else{
	  //      return false;
	  //    }
	  //   }else{
	  //	   return false;
	  //	 }
	  //}else{//����û�д�ӡ��
	  //	if(confirm("��ȷʵҪ���ÿ�����")){
	  //	  frm_delete.opt.value="print";
	  //	  alert("123");
	      //result=openDialog("confirmList.jsp?userId="+userId+"","_blank","350","300");
		  result=PopWindow("confirmList.jsp?userId="+userId+"&opt=print",350,300);
		  //frm.submit();
	  //  }else{
	  //    return false;
	  //  }
	  //}
	}
}

//�ж��Ƿ�ѡ������Ҫ����������,ֻ��ѡ��һ����¼,������ӡ
function produceKey(frm_delete,tableList,checkName,agr)
{
	if(isSelNothing(tableList,checkName) == -1)
	{
		alert ( "û�п��Բ��������ݣ�" );
		return false;
	}
	if(isSelNothing(tableList,checkName))
	{
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
	}
	var collCheck = tableList.children[1].all.namedItem(checkName);
	var printUserCode=tableList.children[1].all.namedItem("printedUserCode");
	if(collCheck.length>1){//�ж���ѡ���ǵڼ�����¼(����ѯ�ļ�¼����һ��)
	    for(i=0;i<collCheck.length;i++){
	        if(collCheck[i].checked==true){
		        if(printUserCode[i].value==null||printUserCode[i].value==""){
					alert("��Աû�в��������̴���,��������key!");
				    return false;
				}
	        }
	    }
	}else{//����ѯ�ļ�¼ֻ��һ��
	    if(printUserCode.value==null||printUserCode.value==""){
	        alert("��Աû�в��������̴���,��������key!");
	        return false;
	    }
	}
	if(confirm("��ȷʵҪ������������")){
		if(agr==1)
		    frm_delete.opt.value="estakey";
		else if(agr==2)
            frm_delete.opt.value="emptyKey";
	    frm.submit();
	 }else{
	    return false;
     }
}
		
//ɾ������е���
function deleteRows( tbl_itemList,checkId )
{
    var collCheck = tbl_itemList.children[1].all.namedItem(checkId );
    if(isSelNothing( tbl_itemList,checkId  ) == -1 )
    {
        alert("û�п���ɾ��������");
        return false;
    }   
    if(isSelNothing( tbl_itemList , checkId ))
    {
                
        alert("��ѡ����ϣ��ɾ��������");
        return false;
    }
    if(confirm("��ȷʵҪɾ��ָ��������"))
    {
        if(collCheck.length)
        {
            for( var i=collCheck.length - 1 ; i > -1 ;i-- )
            {
                if(collCheck[i].checked)
                {
                    tbl_itemList.children[1].deleteRow(i);
                            
                }
            }
        }
        else
        {
            tbl_itemList.children[1].deleteRow(0);
        }
                
    }
}
   
//����Ҫ������Ϣʱ����ȡ��Ϣ�Ľ�����
function getMsgReceiver(  objName , objTbody )
{
	if(!objTbody)
		objTbody = workPlanContent.tBodies[0];
	var strReceiver = "";
	var objColl = objTbody.all.namedItem(objName);
	if(objColl.length)
	{
		var objStr = "";
		for( i = 0 ; i < objColl.length;i++ )
		{
			if(strReceiver.indexOf(objColl[i].value) < 0 )
					strReceiver += objColl[i].value + ","  ;
				
		}
	}
	else
	{
		strReceiver = objColl.value ;
	}
	return strReceiver ;
}

function selectTr()
{
	var objTr=event.srcElement;
	while(objTr.tagName!="TR")
	{
		objTr=objTr.parentNode;
	}
	
	var objRadio = objTr.all.namedItem("selectRadio");
	if(objRadio){
		objRadio.checked = true;
		var valueColl = objTr.all;
		for( var i = 0 ; i < valueColl.length ; i++ )
		{
			 if(valueColl[i].mapId)
			 {
		 		 for( var j = 0 ; j < returnObj.length ; j++ )
					 {
					 	if( returnObj[j].mapId == valueColl[i].mapId )
					 	{
					 		
					 			if( valueColl[i].tagName == "SPAN" )
					 				returnObj[j].value =  valueColl[i].innerText;
					 			if( valueColl[i].tagName == "INPUT" )
					 				returnObj[j].value =  valueColl[i].value;
					 	}
					 }
			 }
		}
	}
	var objTbl = objTr
	while(objTbl.tagName!="TABLE")
	{
		objTbl=objTbl.parentNode;
	}
	
	for ( var i=0; i < objTbl.children[1].children.length ; i++) //>
	{
			objTbl.children[1].children[i].className = "";
	}
	
	objTr.className = "hilite";
}

function dblClickTr()
{
	selectOk();
}

function selectSummary( objSpan )
{
	var	sUrl = basePath + "public/selectSummary.jsp";
	var	sFeatures = "dialogWidth:500px;dialogHeight:460px;center:yes;dialogHide: no ;edge:raised; resizable:no;status:no;unadorned:no;scroll:no; ";
	getReturnValue_S( objSpan, sUrl, sFeatures, null );
}

function LTrim(str)
{
    var i;
    for(i=0;i<str.length;i++)
    {
        if(str.charAt(i)!=" "&&str.charAt(i)!="��")break;
    }
    str=str.substring(i,str.length);
    return str;
}
function RTrim(str)
{
    var i;
    for(i=str.length-1;i>=0;i--)
    {
        if(str.charAt(i)!=" "&&str.charAt(i)!="��")break;
    }
    str=str.substring(0,i+1);
    return str;
}
function Trim(str)
{
    return LTrim(RTrim(str));
}

//��֤ʱ���ʽ,ֻ��֤ʱ���(HH:MM)
function checkHMFormat(val,msg){
    var str=val.value;
    var reg=/^(\d{1,2}):(\d{1,2})$/;
    var r=str.match(reg);
    if(r==null){
       alert(msg+"ʱ���ʽ����,����������!��ʽӦΪHH:MM");
       val.focus();
       val.value="";
    }
    else{
      var d=new Date("2005","02","10",r[1],r[2],"10");
        if(!(d.getHours()==r[1]&&d.getMinutes()==r[2])){
           alert(msg+"ʱ���ʽ����,����������!��ʽӦΪHH:MM");
           val.focus();
           val.value="";
        }
   }
  }
  
 //���û���ȷ�ϲ���
 function userConfirm(){
   if(confirm("��ȷʵҪ������Щ������"))
   { 
     return true;
   }else{
     return false;
   }
 }
 
 //��֤����ֵ�Ƿ�Ϊ�����Ҳ���Ϊ��
 function chkNum(v)
{
	if(v == null || v == "" || isNaN(v))
	{
		return false;
	}
	else
	{
		return true;
	}
}

//��֤�ַ�������
function chkLen(v,c){
  if(v==null||v==""||c==null||c==""){
    return false;
  }else if(Trim(v).length<parseInt(c)){
    return false;
  }else{
    return true;
  }
}

//��֤������ĸ�ʽ
function chkYear(yAgr,msg){
  var y=yAgr.value;
  if(y==null||y==""){
    alert(msg+",����Ϊ��,������");
    yAgr.value="";
    yAgr.focus();
    return false;
  }else if(isNaN(y)){
    alert(msg+",����Ϊ����,����������");
    yAgr.value="";
    yAgr.focus();
    return false;
  }else if(y.length!=4){
    alert(msg+",��ʽ����,����Ϊ4λ,����������");
    yAgr.value="";
    yAgr.focus();
    return false;
  }else if(y<=1900){
    alert(msg+",�������1900,����������");
    yAgr.value="";
    yAgr.focus();
    return false;
  }else{
    return true;
  }
}

//��֤�����·ݵĸ�ʽ
function chkMonth(vAgr,msg){
  var v=vAgr.value;
  if(v==null||Trim(v)==""){
    alert(msg+",����Ϊ��,������");
    vAgr.value="";
    vAgr.focus();
    return false;
  }else if(isNaN(v)){
    alert(msg+",����Ϊ����,����������");
    vAgr.value="";
    vAgr.focus();
    return false;
  }else if(v.length>2){
    alert(msg+",��ʽ����,����Ϊ2λ,����������");
    vAgr.value="";
    vAgr.focus();
    return false;
  }else if(parseInt(v,10)<1||parseInt(v,10)>12){
    alert(msg+",��ʽ����,������1-12֮��,����������");
    vAgr.value="";
    vAgr.focus();
    return false;
  }else{
    return true;
  }
}

//��֤�����յĸ�ʽ
function chkDate(vAgr,msg){
  var v=vAgr.value;
  if(v==null||Trim(v)==""){
    alert(msg+",����Ϊ��,������");
    vAgr.value="";
    vAgr.focus();
    return false;
  }else if(isNaN(v)){
    alert(msg+",����Ϊ����,����������");
    vAgr.value="";
    vAgr.focus();
    return false;
  }else if(v.length>2){
    alert(msg+",��ʽ����,����Ϊ2λ,����������");
    vAgr.value="";
    vAgr.focus();
    return false;
  }else if(parseInt(v,10)<1||parseInt(v,10)>31){
    alert(msg+",��ʽ����,������1-31֮��,����������");
    vAgr.value="";
    vAgr.focus();
    return false;
  }else{
    return true;
  }
}

//���ƾ���
function isFormat(num, dig){ 
    var s, p; 
    if (typeof num != "number") num = 0; 
        dig = (typeof dig == "number")?((dig<0)?0:dig):2; 
        var d = Math.pow(10, dig); 
        //alert(accMul(num,d));
        //var n = "" + Math.round(num*d)/d; 
        var n = "" + Math.round(accMul(num,d))/d; 
        var ary = n.split("."); 
        if (ary.length == 1){ 
           s = ary[0]; 
           p = "" 
       } 
       else{ 
           s = ary[0]; 
           p = ary[1]; 
       } 
       if (dig>0){ 
           var i = 0; 
           var x = dig - p.length; 
           p = "." + p; 
           while (i<x){ 
           p += "0"; 
           i++ 
       } 
     }
     return s + p; 
}

function accMul(arg1,arg2) 
{ 
     var m=0,s1=arg1.toString(),s2=arg2.toString(); 
     try{m+=s1.split(".")[1].length}catch(e){} 
     try{m+=s2.split(".")[1].length}catch(e){} 
     return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m) 
} 


//����ֻ����������
function isNumber(str)
{ 
   var number_chars = "1234567890"; 
   var i; 
   for (i=0;i<str.length;i++) 
   { 
       if (number_chars.indexOf(str.charAt(i))==-1) return false; 
    } 
    return true; 
}

/*
�������ƣ�PopWindow 
�������ܣ������´��� 
����������pageUrl,�´��ڵ�ַ;WinWidth,���ڵĿ�WinHeight,���ڵĸ� 
*/ 
function PopWindow(pageUrl,WinWidth,WinHeight) 
{ 
  var iTop=(window.screen.height-WinHeight)/2
  var iLeft=(window.screen.width-WinWidth)/2
  var popwin=window.open(pageUrl,"_blank","scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=yes,top="+iTop+",left="+iLeft+",width="+WinWidth+",height="+WinHeight); 
  return false; 
}

//�����´���,������
function openWin(pageUrl,WinWidth,WinHeight){
  var popwin=window.open(pageUrl,"hidden","scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=yes,directories=no,width="+WinWidth+",height="+WinHeight); 
  return false;
}
