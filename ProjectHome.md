# Introduction #

The Data Readiness tool serves two purposes related to the validation and analysis of a transaction file prior to being loaded into a pricemart.

## Data Quality ##

It can be used to perform a quick data quality analysis, by scanning the values of specified columns within the transaction file to output a 3x3 grid showing the number of occurrences of positive, zero and negative values for a pricepoint relative to the VQuantity - see DataQualityUsage.

![http://dl.dropbox.com/u/7462894/images/quality-result.png](http://dl.dropbox.com/u/7462894/images/quality-result.png)

## Distinct Attributes ##

It can also be used to obtain an output file which lists all of the distinct values for all of the columns present within the transaction file - see DistinctAttributeUsage.