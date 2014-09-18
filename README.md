
# Stokes Drift #

Coming soon, under construction, all that sort of jazz


## Target feature set ##

* Ability to create services that have a start / stop runtime
* Ability to create jobs running off quartz
* Application configuration built off etcd / consul / yaml
** ability to merge files based on environment deployments
* Ability to package up application to be used in a deployed runtime
* Ability to cleanly work with docker / containers
* Target JRuby and Clojure envs ability to load rack or ring apps
** Pool the web application pieces


Neccessary evils:
* Configuration - need to break out a separate jar / maven published component
** java fork of confd
** Config listeners for embedding
** Default to yaml files
* Registry
* Logging
* IOC injection / CDI support, build objects / services based on this



Dependencies:
* Undertow (web processes) - http://undertow.io/documentation/servlet/deployment.html
* Quartz (scheduling) -
* Delta Spike (cdi)  - https://deltaspike.apache.org/index.html