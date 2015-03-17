# Introduction #

The following steps guide you through using the Data Readiness tool to obtain a list of the distinct attribute values for all columns within a transaction file destined for the Vendavo pricemart.

# Details #

  * Start by downloading the executable archive at http://data-readiness.googlecode.com/files/Ready.jar
  * Assuming you have associated a Java Runtime Environment with .jar files, you will be able to execute the tool by simply double-clicking on the Ready.jar file
  * Once launched, click the "Distinct Attributes" tab

![http://dl.dropbox.com/u/7462894/images/attr-start.png](http://dl.dropbox.com/u/7462894/images/attr-start.png)

  * Select the file(s) to be processed by clicking on the Browse... button, and navigating the file dialog to choose your file(s)
  * Once you have selected the file(s), you then need to specify the column delimeter being used (the application will default a value based on an analysis of the header line, but you can override this if necessary)

![http://dl.dropbox.com/u/7462894/images/attr-select-delimiter.png](http://dl.dropbox.com/u/7462894/images/attr-select-delimiter.png)

  * Click the Start button to begin processing, during this time the status bar will be updated indicating how many lines within the file have been processed (and the Stop button will be enabled, if you wish to cancel the processing)

![http://dl.dropbox.com/u/7462894/images/attr-processing.png](http://dl.dropbox.com/u/7462894/images/attr-processing.png)

  * When processing has completed, the tabulated results will be displayed (note that you can click on the column headers to sort the data)

![http://dl.dropbox.com/u/7462894/images/attr-processed.png](http://dl.dropbox.com/u/7462894/images/attr-processed.png)

  * You can then select the columns that you wish to have all the distinct values extracted by selecting the corresponding rows in the table

![http://dl.dropbox.com/u/7462894/images/attr-select-rows.png](http://dl.dropbox.com/u/7462894/images/attr-select-rows.png)

  * When you are happy with your selections, click the Extract button and the distinct values will be written out to file (the name of the file will be shown in the status bar)