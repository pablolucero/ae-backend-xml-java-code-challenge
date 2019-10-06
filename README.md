# Agile Engine code challenge
## Smart XML Analyzer

Exercise: https://agileengine.bitbucket.io/beKIvpUlPMtzhfAy/

## Intro
Imagine that you are writing a simple web crawler that locates a user-selected element on a web site with frequently changing information. You regularly face an issue that the crawler fails to find the element after minor page updates. After some analysis you decided to make your analyzer tolerant to minor website changes so that you don’t have to update the code every time.


It would be best to view the attached HTML page, imagining that you need to find the “Everything OK” button on every page.

To run the pre compiled binary version:

```
$ java -jar build\libs\smart-xml-analyzer-1.0-all.jar <input_origin_file_path> <input_other_sample_file_path> <target-element-id>
```

To build and run the application:

```
$ gradlew shadowJar
$ java -jar build\libs\smart-xml-analyzer-1.0-all.jar <input_origin_file_path> <input_other_sample_file_path> <target-element-id>
```

Where:

* `<input_origin_file_path>` - origin sample path to find the element with attribute id="make-everything-ok-button" and collect all the required information
* `<input_other_sample_file_path>` - path to diff-case HTML file to search a similar element
* `<target-element-id>` - optional target element id for collecting the initial information


Example for **sample-1-evil-gemini.html** file:
```
$ java -jar build\libs\smart-xml-analyzer-1.0-all.jar ./src/test/resources/samples/sample-0-origin.html ./src/test/resources/samples/sample-1-evil-gemini.html 
```


Output:

```
Target element ID: make-everything-ok-button.
Found 8 candidates within the scope element #page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default
Similarity level: 7%, CSS selector: #page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-heading
Similarity level: 7%, CSS selector: #page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-heading > div.pull-right > div.btn-group > button.btn.btn-default.btn-xs.dropdown-toggle
Similarity level: 15%, CSS selector: #page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-heading > div.pull-right > div.btn-group > ul.dropdown-menu.pull-right > li:nth-child(1) > a
Similarity level: 15%, CSS selector: #page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-heading > div.pull-right > div.btn-group > ul.dropdown-menu.pull-right > li:nth-child(2) > a
Similarity level: 15%, CSS selector: #page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-heading > div.pull-right > div.btn-group > ul.dropdown-menu.pull-right > li:nth-child(3) > a
Similarity level: 15%, CSS selector: #page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-heading > div.pull-right > div.btn-group > ul.dropdown-menu.pull-right > li:nth-child(5) > a
Similarity level: 53%, CSS selector: #page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-body > a.btn.btn-danger
Similarity level: 69%, CSS selector: #page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-body > a.btn.btn-success
Highest similarity: 69%, CSS selector: #page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-body > a.btn.btn-success
#page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-body > a.btn.btn-success
```
