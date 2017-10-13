# Java-MapReduce
This repository contains source code in java for finding Mutual Friends and analyzing Yelp DataSet using Hadoop MapReduce

## Question 1 - MutualFriend

<p align="justify">
Write a MapReduce program in Hadoop that implements a simple “Mutual/Common friend list of two friends". The key idea is that if two people are friend then they have a lot of mutual/common friends. This question will give any two Users as input, output the list of the user id of their mutual friends.
For example,Alice’s friends are Bob, Sam, Sara, Nancy Bob’s friends are Alice, Sam, Clara, Nancy Sara’s friends are Alice, Sam, Clara, NancyAs Alice and Bob are friend and so, their mutual friend list is [Sam, Nancy]
As Sara and Bob are not friend and so, their mutual friend list is empty
</p>

<p align="justify">
The input contains the adjacency list and has multiple lines in the following format:
<User><TAB><Friends>
Here, <User> is a unique integer ID corresponding to a unique user and <Friends> is a comma-separated list of unique IDs (<User> ID) corresponding to the friends of the user. Note that the friendships are mutual (i.e., edges are undirected): if A is friend with B then B is also friend with A. The data provided is consistent with that rule as there is an explicit entry for each side of each edge. So when you make the pair, always consider (A, B) or (B, A) for user A and B but not both.  
  Output: The output should contain one line per user in the following format:
<User_A>, <User_B><TAB><Mutual/Common Friend List>
where <User_A> & <User_B> are unique IDs corresponding to a user A and B (A and B are friend). < Mutual/Common Friend List > is a comma-separated list of unique IDs corresponding to mutual friend list of User A and B.
  </p>
 Please find the above output for the following pairs.
(0,4), (20, 22939), (1, 29826), (6222, 19272), (28041, 28056)

## Steps to run Question 1 :

File : MutualFriend.jar

Steps to run jar file:

1. From the terminal go inside MutualFriend directory
2. Delete output directory - "/user/krupali/output1" if it already exists
3. Type the following command: hadoop jar MutualFriend.jar MutualFriend /user/krupali/input/soc-LiveJournal1Adj.txt /user/krupali/output1
4. Output is at : /user/krupali/output1/part-r-00000 
5. Now, to get output for specific friends pair say 0,4 type the following command: hdfs dfs -cat /user/krupali/output1/part-r-00000 | grep "0,4<press Ctrl+v tab>"
You will get output as : 0,4	8,14,15,18,27,72,80,74,77
6. Similarly to get output for:
20,22939 	type the command: hdfs dfs -cat /user/krupali/output1/part-r-00000 | grep "20,22939<press Ctrl+v tab>" . Output : 20,22939	1,5
1,29826 	type the command: hdfs dfs -cat /user/krupali/output1/part-r-00000 | grep "1,29826<press Ctrl+v tab>". Output : 1,29826	
6222,19272	type the command: hdfs dfs -cat /user/krupali/output1/part-r-00000 | grep "6222,19272<press Ctrl+v tab>" . Output : 6222,19272	19263,19280,19281,19282
28041,28056	type the command: hdfs dfs -cat /user/krupali/output1/part-r-00000 | grep "28041,28056<press Ctrl+v tab>" . Output : 28041,28056	6245,28054,28061

Note: Since the output from reducer is seperated by TAB press Ctrl+v and tab to get tab key value when using grep command. Not following this note might not give you output.

## Question 2: 

Please answer this question by using dataset from Question 1.
Find friend pairs whose common friend number are within the top-10 in all the pairs. Please output them in decreasing order.
Output Format:
<User_A>, <User_B><TAB><Mutual/Common Friend Number>

## Steps to run Question 2:

File: TopTenFriends.jar

Steps to run jar file:

1. From the terminal go inside MutualFriend directory
2. Delete output directory - "/user/krupali/output2_1" and "/user/krupali/output2_2" if it already exists
3. Type the following command:  hadoop jar TopTenFriends.jar TopTenFriends /user/krupali/input/soc-LiveJournal1Adj.txt /user/krupali/output2_1 /user/krupali/output2_2
4. Output is at : /user/krupali/output2_2/part-r-00000




