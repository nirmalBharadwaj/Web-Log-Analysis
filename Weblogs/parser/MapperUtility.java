package com.projects.Weblogs.parser;

public class MapperUtility {
	
	static  String process_time(String  time){
	   	 String time_process = time;
	   	 
	   	 return time_process;
	    }
		
		static String getSeparateReqCategories(String requestString)
		{
			String requestStringTokens [] = requestString.split(" ");
			
			String separateReqCategory = null;
			if (requestStringTokens.length == 3)
				separateReqCategory = getProcessedRequest(requestStringTokens[1]);
			else
				separateReqCategory = getProcessedDefaultRequest();
			return separateReqCategory;
		}
		
		  static String getProcessedRequest(String request)						//	/a/b/c/d?param
		{
			StringBuffer separateReqCategoryBuffer = new StringBuffer();
			
			String requestParamTokens [] = request.split("\\?");						//reqParam = /a/b?aaa
			String ParamString = "-";													//paramString = aaa=1&bbb=2
			boolean paramFlag = false;
			if (requestParamTokens.length == 2)											//? one time
			{
				paramFlag = true;
				ParamString = requestParamTokens[1];
			}
			else if (requestParamTokens.length > 2)										//? More than one time
			{
				paramFlag = true;
				StringBuffer paramStrBuff = new StringBuffer();
				for (int cnt = 1; cnt < requestParamTokens.length; cnt++)
				{
					paramStrBuff.append(requestParamTokens[cnt]);
					if (cnt < requestParamTokens.length - 1)
						paramStrBuff.append(ParamUtil.DELIMITER_QUESTIONMARK);
				}
				ParamString = paramStrBuff.toString();
			}
			
			String requestTokens [] = null;
			if (paramFlag)
				requestTokens = requestParamTokens[0].split("/");			//Request = /a/b/c	(case for /a/b/c?param)
			else
				requestTokens = request.split("/");							//Request = /a/b/c	(case for /a/b/c)
			
			
			int requestTokensLen = requestTokens.length;
			if (requestTokensLen == 0)													// for /
			{
				separateReqCategoryBuffer.append("/").append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH);
			}
			else if (requestTokensLen == 1)										//situation never come		(for a/)
			{
				separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH);
			}
			else if (requestTokensLen == 2)										// for /abc.html
			{
				separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(requestTokens[1]);
			}
			else if (requestTokensLen == 3)										//for /a/abc.html
			{
				separateReqCategoryBuffer.append(requestTokens[1]).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(requestTokens[2]);
			}
			else if (requestTokensLen == 4)										// for /a/b/abc.html
			{
				separateReqCategoryBuffer.append(requestTokens[1]).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(requestTokens[2]).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(requestTokens[3]);
			}
			else if (requestTokensLen == 5)
			{
				separateReqCategoryBuffer.append(requestTokens[1]).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(requestTokens[2]).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(requestTokens[3]).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(requestTokens[4]);
			}
			else if (requestTokensLen == 6)
			{
				separateReqCategoryBuffer.append(requestTokens[1]).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(requestTokens[2]).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(requestTokens[3]).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(requestTokens[4]).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(requestTokens[5]);
			}
			else if (requestTokensLen > 6)
			{
				separateReqCategoryBuffer.append(requestTokens[1]).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(requestTokens[2]).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(requestTokens[3]).append(ParamUtil.DELIMITER_TAB);
				StringBuffer requestTokensBuffer = new StringBuffer();
				for (int cnt = 4; cnt < requestTokensLen - 1; cnt++)
				{
					requestTokensBuffer.append(requestTokens[cnt]).append("/");
				}
				separateReqCategoryBuffer.append(requestTokensBuffer).append(ParamUtil.DELIMITER_TAB);
				separateReqCategoryBuffer.append(requestTokens[requestTokensLen - 1]);
			}
			
			
//			separateReqCategoryBuffer.append(requestStringTokens[2]).append(DELIMITER_TAB);
			separateReqCategoryBuffer.append(ParamUtil.DELIMITER_TAB).append(ParamString);
			return separateReqCategoryBuffer.toString();
		}
		
		 static String getProcessedDefaultRequest()
		{
			StringBuffer separateReqCategoryBuffer = new StringBuffer();
			
			separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);				//cat1
			separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);				//cat2
			separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);				//cat3
			separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);				//cat4
			separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH).append(ParamUtil.DELIMITER_TAB);				//page
			
			separateReqCategoryBuffer.append(ParamUtil.DEFAULT_VALUE_DASH);										//param

			return separateReqCategoryBuffer.toString();
		}
		
}
