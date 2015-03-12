#!/bin/bash

if [ -z "$JRUBY_HOME" ]; then
  echo "NO JRUBY_HOME SET"
  exit 0
fi

if [ -z "$APP_ROOT"]; then
  echo "NO APP_ROOT SET"
  exit 0
fi

export PATH=$JRUBY_HOME/bin:$PATH

# TODO configurable stuff
if [ -z "$JAVA_OPTS" ]; then
   export JAVA_OPTS="-XX:ReservedCodeCacheSize=256m -Xmx2048m -Xms512m -XX:+UseG1GC"
fi

export JAVA_OPTS="${JAVA_OPTS} -XX:+TieredCompilation"
export JAVA_OPTS="${JAVA_OPTS} -XX:+UseCodeCacheFlushing"
# export JAVA_OPTS="${JAVA_OPTS} -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintClassHistogram -XX:+PrintTenuringDistribution -XX:+PrintGCApplicationStoppedTime"
export JAVA_OPTS="${JAVA_OPTS} -Djruby.thread.pooling=true"
export JAVA_OPTS="${JAVA_OPTS} -Djruby.compile.positionless=true"
export JAVA_OPTS="${JAVA_OPTS} -Djruby.compile.mode=FORCE"
export JAVA_OPTS="${JAVA_OPTS} -Djruby.native.enabled=false"
export JAVA_OPTS="${JAVA_OPTS} -Djruby.thread.pool.min=10"
export JAVA_OPTS="${JAVA_OPTS} -XX:CompileCommand=dontinline,org.jruby.runtime.invokedynamic.InvokeDynamicSupport::invocationFallback"
export JAVA_OPTS="${JAVA_OPTS} -Djruby.management.enabled=false"
export JAVA_OPTS="${JAVA_OPTS} -Djava.net.preferIPv4Stack=true -Djava.awt.headless=true"
export JAVA_OPTS="${JAVA_OPTS} -Dfile.encoding=UTF-8"

SCRIPT_DIR=$( dirname "${BASH_SOURCE[0]}" )
echo "Running Stokesdrift from: ${SCRIPT_DIR}/../"
echo "Stokes drift home: ${STOKESDRIFT_DIR}"
echo "Stokes drift lib: ${STOKESDRIFT_LIB_DIR}"

if [ -z "$STOKESDRIFT_DIR" ]; then
  export STOKESDRIFT_DIR=$( cd "${SCRIPT_DIR}/../" && pwd )
fi

if [ -z "$STOKESDRIFT_LIB_DIR" ]; then
  export STOKESDRIFT_LIB_DIR="${STOKESDRIFT_DIR}/lib"
fi

for i in $STOKESDRIFT_LIB_DIR/*.jar; do
    CLASSPATH=$CLASSPATH:$i
done
export CLASSPATH=`echo $CLASSPATH | cut -c2-`

if [ -z "$JAVA_OPTS" ]; then
  JAVA_OPTS=""
fi



java $JAVA_OPTS org.stokesdrift.Server -r $APP_ROOT

#         ROOT_PATH(new String[]{ "-r","-root_path" }, null),
#        CONFIG_FILE(new String[]{ "-c","-config_file" }, "drift_config.yml"),