## CTL-JVM

__CTL-JVM__ is an adaptation of [Cucumber-JVM](https://github.com/cucumber/cucumber-jvm) that enables the development of [ISO 19105](http://www.isotc211.org/Outreach/Overview/Factsheet_19105.pdf) 
abstract and executable test suites for network services. Abstract test suites are 
specified in [Gherkin](https://github.com/cucumber/cucumber/wiki/Gherkin). Executable tests suites are implemented in Java, using adapted 
Cucumber-JVM tooling and a library based in the semantics of the [OGC Compliance Test Language](http://portal.opengeospatial.org/files/?artifact_id=33085). 

## INSPIRE and CTL-JVM

Check out our [INSPIRE test suites](https://github.com/fjlopez/ctl-jvm/tree/master/inspire). These test suites are available in:
* English
* Spanish

## Building CTL-JVM

CTL-JVM is built with [Maven](http://maven.apache.org/). 

```
mvn clean install
```

## Bugs and Feature requests

You can register bugs and feature requests in the 
[Github Issue Tracker](https://github.com/fjlopez/ctl-jvm/issues).

You're most likely going to paste code and output, so familiarise yourself with
[Github Flavored Markdown](http://github.github.com/github-flavored-markdown/) 
to make sure it remains readable.

## Bugs, feature requests and fixes

We appreciate that. We follow [Cucumber-JVM rules](https://raw.github.com/cucumber/cucumber-jvm/master/CONTRIBUTING.md), thus, before you do it, please:
* Feature request? Then don't expect it to be implemented unless you or someone else sends a [pull request](https://help.github.com/articles/using-pull-requests).
* Reporting a bug? Bugs with [pull requests](https://help.github.com/articles/using-pull-requests) with a failing test that reproduces the bug get fixed quicker. 
* Merging a [Pull requests](https://help.github.com/articles/using-pull-requests)? It won't be merged if you don't have a test to go with it.

## Etiquette
* Put \`\`\` on a line above and below your code/output. See [GFM](https://help.github.com/articles/github-flavored-markdown)'s *Fenced Code Blocks* for details.
