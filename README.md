# Purchase Analystic

This program is build with Java. 
Read two input file and output sorted department number, total order has made, first time order and the ratio of first time order.
Using Order Object store info read from order_product.csv. Put productID and department number which are read from order_product.csv to a hashmap. 
Then add department number to Order Object.
Calculate each department's total sale number and total first time order, then stored in salesnumber object.
Creat another hashmap, put department number as key, salesnumber as value.
Sorted department number, using hashmap find each department's salenumber then calculate the ration and output to report.csv.
