package com.projects.Weblogs.parser;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;




public class LogPrepMapper extends Mapper <LongWritable, Text, NullWritable, Text>
{
	private static String LOG_PATTERN = "^(\\S+) (\\S+) (\\S+) \\[(.+?)\\] \"([^\"]*)\" (\\S+) (\\S+) \"([^\"]*)\" \"([^\"]*)\"";
	private static int NUM_FIELDS = 9;
	private Pattern pattern  = null;
	MultipleOutputs <NullWritable, Text> mos = null;

	public void setup(Context context)
    {
		mos = new MultipleOutputs <NullWritable, Text> (context);
		pattern = Pattern.compile(LOG_PATTERN);
    }
	
	protected void cleanup(Context context) throws IOException, InterruptedException
	{
		mos.close();
	}
	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException 
	{
		String formattedValue = value.toString().replaceAll("\t", " ").trim();				
		
		Matcher matcher = pattern.matcher(formattedValue);
		if (matcher.matches() && NUM_FIELDS == matcher.groupCount())
		{
			String requestString = matcher.group(5);													
			String separateReqCategory = MapperUtility.getSeparateReqCategories(requestString);
			
			StringBuffer valueBuffer = new StringBuffer();
			valueBuffer.append(matcher.group(1)).append(ParamUtil.DELIMITER_TAB);					
			valueBuffer.append(matcher.group(2)).append(ParamUtil.DELIMITER_TAB);					
			valueBuffer.append(matcher.group(3)).append(ParamUtil.DELIMITER_TAB);					
			valueBuffer.append(matcher.group(4)).append(ParamUtil.DELIMITER_TAB);					
			valueBuffer.append(matcher.group(5)).append(ParamUtil.DELIMITER_TAB);					
			
			valueBuffer.append(separateReqCategory).append(ParamUtil.DELIMITER_TAB);				
			valueBuffer.append(matcher.group(6)).append(ParamUtil.DELIMITER_TAB);					
			valueBuffer.append(matcher.group(7)).append(ParamUtil.DELIMITER_TAB);					
			valueBuffer.append(matcher.group(8)).append(ParamUtil.DELIMITER_TAB);					
			valueBuffer.append(matcher.group(9));													
			
//			context.write(NullWritable.get(), new Text (valueBuffer.toString()));
			mos.write("ParsedRecords", NullWritable.get(), new Text (valueBuffer.toString()));
		}
		else
		{
			mos.write("BadRecords", NullWritable.get(), value);
		}
	}
}	
	