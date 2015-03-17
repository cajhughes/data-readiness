# Introduction #

The following steps guide you through using the Data Readiness tool to obtain a quick assessment of the quality of data within a transaction file destined for the Vendavo pricemart.

# Details #

  * Start by downloading the executable archive at http://data-readiness.googlecode.com/files/Ready.jar
  * Assuming you have associated a Java Runtime Environment with .jar files, you will be able to execute the tool by simply double-clicking on the Ready.jar file
  * Once launched, the tool presents the initial interface:

![http://dl.dropbox.com/u/7462894/images/start.png](http://dl.dropbox.com/u/7462894/images/start.png)

  * Select the file to be assessed by clicking on the Browse... button, and navigating the file dialog to choose your file
  * Once you have selected the file, you then need to specify the column delimiter being used (the application will default a value based on an analysis of the header line, but you can override this if necessary)

![http://dl.dropbox.com/u/7462894/images/select-delimiter.png](http://dl.dropbox.com/u/7462894/images/select-delimiter.png)

  * Following your choice of delimiter, the Quantity and Price fields will be enabled and pre-filled with potential options based on the header row in the transaction file
  * Choose the columns to be used as the source for the VQuantity column within the pricemart, and a pricepoint column

![http://dl.dropbox.com/u/7462894/images/select-price.png](http://dl.dropbox.com/u/7462894/images/select-price.png)

  * When you are happy with your selections, click the Start button to begin processing, during this time the status bar will be updated indicating how many lines within the file have been processed (and the Stop button will be enabled, if you wish to cancel the processing)

![http://dl.dropbox.com/u/7462894/images/processing.png](http://dl.dropbox.com/u/7462894/images/processing.png)

  * When processing has completed, the tabulated results will be displayed

![http://dl.dropbox.com/u/7462894/images/processed.png](http://dl.dropbox.com/u/7462894/images/processed.png)

  * Finally, if you want to extract the lines from the original file that relate to the contents of one of the resulting cells in the table, select the appropriate cell and click the Extract button

![http://dl.dropbox.com/u/7462894/images/cell-extract.png](http://dl.dropbox.com/u/7462894/images/cell-extract.png)