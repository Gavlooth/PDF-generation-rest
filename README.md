# PDF FILL FORM REST API 

Small utility for filling a pdf form over REST API
Place the pdf fillable files on /resources/pdf-forms
and make a json post request to *your-server*/fill-form/*pdf-file-name*
where *your-server* = the ring server address
*pdf-file-name* the full name on the file under /resources/pdf-forms 
including the extension

## Running

	$ lein deps
	$ lein ring server-headless

## Deploying

	$ lein ring uberjar
	$ lein -jar target/uberjar/apycare-rest-standalone.jar
