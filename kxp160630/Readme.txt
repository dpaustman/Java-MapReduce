Input for Question 1 and Question 2 : soc-LiveJournal1Adj.txt
Inputs for Question 3 and Question 4 : review.csv and business.csv

Copy all the input files at particular HDFS location. Here in the steps I assume that all the input files are inside directory : /user/kxp160630/input in HDFS

Here in the steps below all my output are in the HDFS path : /user/kxp160630/

All the jar files are inside directory : kxp160630/



========================================================================
Question 1:

File : MutualFriend.jar

Steps to run jar file:

1. From the terminal go inside kxp160630/Question1 folder
2. Delete output directory - "/user/kxp160630/output1" if it already exists
3. Type the following command: hadoop jar MutualFriend.jar MutualFriend /user/kxp160630/input/soc-LiveJournal1Adj.txt /user/kxp160630/output1
4. Output is at : /user/kxp160630/output1/part-r-00000 
5. Now, to get output for specific friends pair say 0,4 type the following command: hdfs dfs -cat /user/kxp160630/output1/part-r-00000 | grep "0,4<press Ctrl+v tab>"
You will get output as : 0,4	8,14,15,18,27,72,80,74,77
6. Similarly to get output for:
20,22939 	type the command: hdfs dfs -cat /user/kxp160630/output1/part-r-00000 | grep "20,22939<press Ctrl+v tab>" . Output : 20,22939	1,5
1,29826 	type the command: hdfs dfs -cat /user/kxp160630/output1/part-r-00000 | grep "1,29826<press Ctrl+v tab>". Output : 1,29826	
6222,19272	type the command: hdfs dfs -cat /user/kxp160630/output1/part-r-00000 | grep "6222,19272<press Ctrl+v tab>" . Output : 6222,19272	19263,19280,19281,19282
28041,28056	type the command: hdfs dfs -cat /user/kxp160630/output1/part-r-00000 | grep "28041,28056<press Ctrl+v tab>" . Output : 28041,28056	6245,28054,28061

Note: Since the output from reducer is seperated by TAB press Ctrl+v and tab to get tab key value when using grep command. Not following this note might not give you output.

========================================================================

Question 2:

File: TopTenFriends.jar

Steps to run jar file:

1. From the terminal go inside kxp160630/Question2 folder
2. Delete output directory - "/user/kxp160630/output2_1" and "/user/kxp160630/output2_2" if it already exists
3. Type the following command:  hadoop jar TopTenFriends.jar TopTenFriends /user/kxp160630/input/soc-LiveJournal1Adj.txt /user/kxp160630/output2_1 /user/kxp160630/output2_2
4. Output is at : /user/kxp160630/output2_2/part-r-00000

========================================================================

Question 3:

File: TopTenBusinessRatings.jar

Steps to run jar file:

1. From the terminal go inside kxp160630/Question3 folder
2. Delete output directory - "/user/kxp160630/output3_1" and "/user/kxp160630/output3_2" if it already exists
3. Type the following command:  hadoop jar TopTenBusinessRatings.jar TopTenBusinessRatings /user/kxp160630/input/review.csv /user/kxp160630/output3_1 /user/kxp160630/input/business.csv /user/kxp160630/output3_2
4. Output is at : /user/kxp160630/output3_2/part-r-00000

========================================================================

Question 4:

File: Question4.jar

Steps to run jar file:

1. From the terminal go inside kxp160630/Question4 folder
2. Delete output directory - "/user/kxp160630/output4" if it already exists
3. Type the following command: hadoop jar Question4.jar Question4 /user/kxp160630/input/business.csv /user/kxp160630/input/review.csv /user/kxp160630/output4
4. Output is at : /user/kxp160630/output4/part-r-00000

