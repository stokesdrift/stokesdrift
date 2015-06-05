#!/bin/bash

if [ -z "$JRUBY_HOME" ]; then
  echo "NO JRUBY_HOME SET"
  JRUBY_EXEC=`which jruby`
  JRUBY_BIN=`dirname $JRUBY_EXEC`
  export JRUBY_HOME="${JRUBY_HOME}/.."
  echo "Setting JRUBY_HOME to ${JRUBY_HOME}"
fi

if [ -z "$APP_ROOT" ]; then
  echo "NO APP_ROOT SET"
  export APP_ROOT=`pwd`
  echo "Setting APP_ROOT to ${APP_ROOT}"
fi

export PATH=$JRUBY_HOME/bin:$PATH

# TODO configurable stuff
if [ -z "$JAVA_OPTS" ]; then
   export JAVA_OPTS="-XX:ReservedCodeCacheSize=256m -Xmx2048m -Xms512m -XX:+UseG1GC"
fi

export JAVA_OPTS="${JAVA_OPTS} -XX:+TieredCompilation"
export JAVA_OPTS="${JAVA_OPTS} -XX:+UseCodeCacheFlushing"
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
echo "Gem path environment: ${GEM_PATH}"

if [ -z "$STOKESDRIFT_DIR" ]; then
  export STOKESDRIFT_DIR=$( cd "${SCRIPT_DIR}/../" && pwd )
fi

if [ -z "$STOKESDRIFT_LIB_DIR" ]; then
  export STOKESDRIFT_LIB_DIR="${STOKESDRIFT_DIR}/lib"
fi
echo $STOKESDRIFT_LIB_DIR
JAR_LIST=`ls $STOKESDRIFT_LIB_DIR/*.jar`

for i in $JAR_LIST; do
    CLASSPATH=$CLASSPATH:$i
done
# export CLASSPATH=`echo $CLASSPATH | cut -c2-`
export CLASSPATH

if [ -z "$JAVA_OPTS" ]; then
  JAVA_OPTS=""
fi
echo "CLASSPATH=${CLASSPATH}"
java $JAVA_OPTS org.stokesdrift.Server -r $APP_ROOT

#         ROOT_PATH(new String[]{ "-r","-root_path" }, null),
#        CONFIG_FILE(new String[]{ "-c","-config_file" }, "drift_config.yml"),