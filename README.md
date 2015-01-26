
# Stokes Drift #

Coming soon, under construction, all that sort of jazz

## Target feature set ##

* Ability to create services that have a start / stop runtime
* Ability to create jobs running off quartz
* Application configuration built off etcd / consul / yaml
** ability to merge files based on environment deployments
* Ability to package up application to be used in a deployed runtime
* Ability to cleanly work with docker / containers
* Target JRuby and eventually Clojure envs ability to load rack or ring apps
** Pool the web application pieces
** singleton services

Neccessary evils:
* Configuration - need to break out a separate jar / maven published component
** java fork of confd
** Config listeners for embedding
** Default to yaml files
* Registry - in progress
* Logging - Use gelf and figure out async logging approach
* IOC injection / CDI support, build objects / services based on this
* Console screen that can be activated via config (servlet to run ruby code / etc...)


Dependencies:
* Undertow (web processes) - http://undertow.io/documentation/servlet/deployment.html
* Weld
* Jruby Rack


TODO
* Fix the test resource pathing via gradle build
* Rename packages to stokesdrift vs stokes_drift
* Startup and including of the resources inclusion
* Fix issue with running in a gem
** Publish gem after
* Add service approach
* Add configuration options
** java fork of confd
* Add plugin model based on CDI
* Add logging and gelf support
* Add newrelic support



## Testing out ##
run `STOKES_DRIFT_OPTS="-r src/test/resources/examples" gradle run`