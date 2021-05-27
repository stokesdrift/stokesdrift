# Stokes Drift: Drift Server

Simple jruby rack server based on undertow. 
Provides a way to run rack apps within a java server.

To build:
* `gradle`
* `gem build stokesdrift.gemspec`

To publish:
* `gradle publish`
* `gem push *.gem`

## Testing out ##
run `STOKES_DRIFT_OPTS="-r src/test/resources/examples" gradle run`

## Dependencies:

* Undertow (web processes) - http://undertow.io/documentation/servlet/deployment.html
* Weld
* Jruby Rack
