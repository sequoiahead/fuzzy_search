# html analyzer
# Usage
_java -jar htmlnlz-1.0-SNAPSHOT.jar \<originalFilePath> \<otherFilePath>_

# Example output
#### input/sample-1-evil-gemini.html
INFO [main] (Application.java37) - Xpath is: html[lang="en"] > body[] > div[id="wrapper"] > div[id="page-wrapper"] > div[class="row"] > div[class="col-lg-8"] > div[class="panel panel-default"] > div[class="panel-body"] > a[class="btn btn-success" href="#check-and-ok" title="Make-Button" rel="done" onclick="javascript:window.okDone(); return false;"]

#### input/sample-2-container-and-clone.html
INFO [main] (Application.java37) - Xpath is: html[lang="en"] > body[] > div[id="wrapper"] > div[id="page-wrapper"] > div[class="row"] > div[class="col-lg-8"] > div[class="panel panel-default"] > div[class="panel-body"] > div[class="some-container"] > a[class="btn test-link-ok" href="#ok" title="Make-Button" rel="next" onclick="javascript:window.okComplete(); return false;"]

#### input/sample-3-the-escape.html
INFO [main] (Application.java37) - Xpath is: html[lang="en"] > body[] > div[id="wrapper"] > div[id="page-wrapper"] > div[class="row"] > div[class="col-lg-8"] > div[class="panel panel-default"] > div[class="panel-footer"] > a[class="btn btn-success" href="#ok" title="Do-Link" rel="next" onclick="javascript:window.okDone(); return false;"]

#### input/sample-4-the-mash.html
INFO [main] (Application.java37) - Xpath is: html[lang="en"] > body[] > div[id="wrapper"] > div[id="page-wrapper"] > div[class="row"] > div[class="col-lg-8"] > div[class="panel panel-default"] > div[class="panel-footer"] > a[class="btn btn-success" href="#ok" title="Make-Button" rel="next" onclick="javascript:window.okFinalize(); return false;"]
