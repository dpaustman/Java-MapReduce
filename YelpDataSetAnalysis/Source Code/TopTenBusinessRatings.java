
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

//Calculate top ten business ratings
public class TopTenBusinessRatings {

	public static class Rating_Map extends Mapper<LongWritable, Text, Text, DoubleWritable> {
		private Text key_business_id = new Text();
		private DoubleWritable value_rating = new DoubleWritable();

		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, Text, DoubleWritable>.Context context)
						throws IOException, InterruptedException {

			String[] line = value.toString().split("::");
			if (line.length == 4) {
				key_business_id.set(line[2]);
				value_rating.set(Double.parseDouble(line[3]));
				context.write(key_business_id, value_rating);
			}

		}
	}

	public static class Rating_Reduce extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

		HashMap<String, Double> map = new HashMap<String, Double>();
		ValueComparator bvc = new ValueComparator(map);
		TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(bvc);

		@Override
		protected void reduce(Text key, Iterable<DoubleWritable> values,
				Reducer<Text, DoubleWritable, Text, DoubleWritable>.Context context)
						throws IOException, InterruptedException {
			int count = 0;
			double sum = 0;
			for (DoubleWritable value : values) {
				sum += value.get();
				count++;

			}

			Double avgRating = sum / count;
			map.put(key.toString(), avgRating);
		}

		// Defining ValueComparator explicitly so that we can sort the map in
		// Descending Order
		class ValueComparator implements Comparator<String> {

			Map<String, Double> base;

			public ValueComparator(Map<String, Double> base) {
				this.base = base;
			}

			public int compare(String a, String b) {
				Double valueA = (Double) base.get(a);
				Double valueB = (Double) base.get(b);
				if (valueA >= valueB) {
					return -1;
				} else {
					return 1;
				}
			}
		}

		//we can use one more set of map-reduce phase here(as done in Question2 and add that to job chaining)  instead of cleanup method 
		//but I wanted to learn using cleanup method to get Top 10 ratings so I have used cleanup method
		@Override
		protected void cleanup(Reducer<Text, DoubleWritable, Text, DoubleWritable>.Context context)
				throws IOException, InterruptedException {

			sorted_map.putAll(map);

			int countOfRating = 1;
			for (Entry<String, Double> entry : sorted_map.entrySet()) {
				if (countOfRating>10) {
					break;
				}
				context.write(new Text(entry.getKey()), new DoubleWritable(entry.getValue()));
				countOfRating++;
			}
		}

	}

	public static class BusinessRating_Map extends Mapper<LongWritable, Text, Text, Text> {
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String[] line = value.toString().split("\t");
			if (line.length == 2) {
				context.write(new Text(line[0]), new Text("ratings\t" + line[1]));
			}
		}
	}

	public static class BusinessData_Map extends Mapper<LongWritable, Text, Text, Text> {

		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String[] line = value.toString().split("::");
			if (line.length == 3) {
				context.write(new Text(line[0]), new Text("business\t" + line[1] + "\t" + line[2]));
			}
		}

	}

	public static class Final_Reducer extends Reducer<Text, Text, Text, Text>

	{

		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {

			String businessDetails = null;
			String ratings = null;
			int count = 0;
			int bcount = 1;
			int rcount = 1;
			for (Text t : values) {
				String[] details = t.toString().split("\t");
				if (details[0].equals("business") && bcount == 1) {
					businessDetails = details[1] + "\t" + details[2];
					count++;
					bcount++;
				} else if (details[0].equals("ratings") && rcount == 1) {
					ratings = details[1];
					count++;
					rcount++;
				}
			}
			if (count == 2) {
				context.write(key, new Text(businessDetails + "\t" + ratings));
			}

		}
	}

	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		// get all args
		if (otherArgs.length != 4) {
			System.err.println("Usage: TopTenBusinessRatings <inputfile review.csv HDFS path > <outputfile1 HDFS path> <inputfile business.csv HDFS path> <outputfile2 HDFS path>");
			System.exit(2);
		}
		// create a job with name "wordcount"
		@SuppressWarnings("deprecation")
		Job job = new Job(conf, "TopTenBusinessRatings phase 1");
		job.setJarByClass(TopTenBusinessRatings.class);
		job.setMapperClass(Rating_Map.class);
		job.setReducerClass(Rating_Reduce.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(DoubleWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DoubleWritable.class);

		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		// set the HDFS path for the output1 (this output file will contain business_id as key and average rating as value)
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		boolean mapreduce = job.waitForCompletion(true);

		if (mapreduce) {
			Configuration conf1 = new Configuration();
			Job job2 = Job.getInstance(conf1, "TopTenBusinessRatings phase 2");
			job2.setJarByClass(TopTenBusinessRatings.class);
			job2.setOutputKeyClass(Text.class);
			job2.setOutputValueClass(Text.class);
			job2.setInputFormatClass(TextInputFormat.class);
			//sending output of MapReduce phase1 as an input to MapReduce phase2 
			MultipleInputs.addInputPath(job2, new Path(otherArgs[1]), TextInputFormat.class, BusinessRating_Map.class);
			//taking business.csv fields as input 
			MultipleInputs.addInputPath(job2, new Path(otherArgs[2]), TextInputFormat.class, BusinessData_Map.class);
			job2.setReducerClass(Final_Reducer.class);
			// set the HDFS path for the output2 (this output file will contain business_id, address, category and top ten average rating as output)
			FileOutputFormat.setOutputPath(job2, new Path(otherArgs[3]));

			System.exit(job2.waitForCompletion(true) ? 0 : 1);
		}
	}

}
