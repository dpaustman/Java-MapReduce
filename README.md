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
<br/>20,22939 	type the command: hdfs dfs -cat /user/krupali/output1/part-r-00000 | grep "20,22939<press Ctrl+v tab>" . 
<br/>Output : 20,22939	1,5
<br/>1,29826 	type the command: hdfs dfs -cat /user/krupali/output1/part-r-00000 | grep "1,29826<press Ctrl+v tab>". 
<br/>Output : 1,29826	
<br/>6222,19272	type the command: hdfs dfs -cat /user/krupali/output1/part-r-00000 | grep "6222,19272<press Ctrl+v tab>" . 
<br/>Output : 6222,19272	19263,19280,19281,19282
<br/>28041,28056	type the command: hdfs dfs -cat /user/krupali/output1/part-r-00000 | grep "28041,28056<press Ctrl+v tab>" . 
<br/>Output : 28041,28056	6245,28054,28061

Note: Since the output from reducer is seperated by TAB press Ctrl+v and tab to get tab key value when using grep command. Not following this note might not give you output.

## Question 2: Top Ten Mutual Friends

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
========================================================================
## Question 3: Yelp DataSet Analysis

In this question, you will apply Hadoop map-reduce to derive some statistics from Yelp Dataset.
------------------ Data set Info -------------------------------------------------------------- 
The dataset files are as follows and columns are separated using ‘::’ business.csv.
review.csv.
user.csv.
Dataset Description.

  The dataset comprises of three csv files, namely user.csv, business.csv and review.csv.
Business.csv file contain basic information about local businesses. Business.csv file contains the following columns "business_id"::"full_address"::"categories"
'business_id': (a unique identifier for the business) 'full_address': (localized address),
'categories': [(localized category names)]
review.csv file contains the star rating given by a user to a business. Use user_id to associate this review with others by the same user. Use business_id to associate this review with others of the same business.
review.csv file contains the following columns "review_id"::"user_id"::"business_id"::"stars"
'review_id': (a unique identifier for the review)
'user_id': (the identifier of the reviewed business),
'business_id': (the identifier of the authoring user),
'stars': (star rating, integer 1-5), the rating given by the user to a business
user.csv file contains aggregate information about a single user across all of Yelp user.csv file contains the following columns "user_id"::"name"::"url"
user_id': (unique user identifier),
'name': (first name, last initial, like 'Matt J.'), this column has been made anonymous to preserve privacy
'url': url of the user on yelp
NB: :: is Column separator in the files.
List the business_id, full address and categories of the Top 10 businesses using the average ratings.
This will require you to use review.csv and business.csv files.
  Please use ```diff reduce side join ```and ```diff job chaining technique  ``` to answer this problem.

<b>Sample output:
businessid      full address  categories                                avg rating </b>
xdf12344444444, CA 91711      List['Local Services', 'Carpet Cleaning'] 5.0



## Steps to run Question 3 :

File: TopTenBusinessRatings.jar

Steps to run jar file:

1. From the terminal go inside YelpDataSetAnalysis folder
2. Delete output directory - "/user/krupali/output3_1" and "/user/krupali/output3_2" if it already exists
3. Type the following command:  hadoop jar TopTenBusinessRatings.jar TopTenBusinessRatings /user/krupali/input/review.csv /user/krupali/output3_1 /user/krupali/input/business.csv /user/krupali/output3_2
4. Output is at : //user/krupali/output3_2/part-r-00000


## Question 4:

Use Yelp Dataset
List the 'user id' and 'rating' of users that reviewed businesses located in “Palo Alto”
Required files are 'business' and 'review'.

Please use ```diff  In Memory Join technique  ```to answer this problem. Hint: Please load all data in business.csv file into the distributed cache.

Sample output
 
User id                Rating
0WaCdhr3aXb0G0niwTMGTg 4.0
## Steps to run Question 4 :
File: Question4.jar

Steps to run jar file:

1. From the terminal go inside YelpDataSetAnalysis folder
2. Delete output directory - "/user/krupali/output4" if it already exists
3. Type the following command: hadoop jar Question4.jar Question4 /user/krupali/input/business.csv /user/krupali/input/review.csv /user/krupali/output4
4. Output is at : /user/krupali/output4/part-r-00000
 



